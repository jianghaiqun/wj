package com.baifendian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsPoster {
	private static final Logger logger = LoggerFactory.getLogger(HttpsPoster.class);

	/**
	 * 获得KeyStore.
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return 密钥库
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String password, String keyStorePath)
			throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());

		// 实例化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		// 获得信任库
		KeyStore trustStore = getKeyStore(password, trustStorePath);
		// 初始化信任库
		trustManagerFactory.init(trustStore);
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(),
				trustManagerFactory.getTrustManagers(), null);
		// 获得SSLSocketFactory
		return ctx;
	}

	/**
	 * 根据已有的信任库初始化HttpsURLConnection.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @throws Exception
	 */
	public static void initHttpsURLConnection(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 声明SSL上下文
		SSLContext sslContext = null;
		// 实例化主机名验证接口
		HostnameVerifier hnv = new MyHostnameVerifier();
		try {
			sslContext = getSSLContext(password, keyStorePath, trustStorePath);
		} catch (GeneralSecurityException e) {
			logger.error(e.getMessage(), e);
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}

	/**
	 * 初始化HttpsURLConnection，将可信的域名加入到信任环境
	 * 
	 * 请尽可能使用这个方法，它会下载服务器证书并自动加入到环境中，不受证书变化的影响
	 * 
	 * @param dir
	 *            证书所在路径
	 * @param host
	 *            主机地址
	 * @param port
	 *            端口
	 * @param password
	 *           系統keystore证书密码，默认是"changeit"，不包括引号
	 * @throws Exception
	 */
	public static void initHttpsURLConnection(String dir, String host,
			int port, String password) throws Exception {
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		
		// 如果证书颂给的名称与所通信的域名不一致的话，那么需要重写校验方法
		HostnameVerifier hv = new MyHostnameVerifier();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// 信任管理器工厂
		TrustManagerFactory tmf = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		File file = new File(dir + host + ".cer");
		file = makeSureFile(file);
		KeyStore ks = getKeyStore(file, password);
		tmf.init(ks);

		SSLContext context = SSLContext.getInstance("SSL");
		X509TrustManager defaultTrustManager = (X509TrustManager) tmf
				.getTrustManagers()[0];
		SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
		context.init(null, new TrustManager[] { tm }, null);

		// 尝试使用socket对目标主机进行通信
		SSLSocketFactory factory = context.getSocketFactory();
		SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
		socket.setSoTimeout(1000);
		try {
			// 如果直接通信没问题的话,就不会报错,也不必获取证书
			// 如果报错的话,很有可能没有证书
			socket.startHandshake();
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
			//System.out.println("SSL证书缺失，将自动安装");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
				}
				socket = null;
			}
			X509Certificate[] chain = tm.getChain();
			if (chain != null) {
				//System.out.println("服务器返回：" + chain.length + " 个证书");
				OutputStream out = null;
				for (int i = 0; i < chain.length; i++) {
					try {
						X509Certificate x509Cert = chain[i];
						String alias = host + (i > 0 ? i + "" : "");
						ks.setCertificateEntry(alias, x509Cert);

						String certFile = dir + alias + ".cer";
						out = new FileOutputStream(certFile);
						ks.store(out, password.toCharArray());
						out.close();

						System.setProperty("javax.net.ssl.trustStore", certFile);
						//System.out.println("第" + (i + 1) + "个证书安装成功");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					} finally {
						try {
							if (out != null) {
								out.close();
							}
							out = null;
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}

	/**
	 * 确保文件存在
	 * 
	 * @param file
	 * @return
	 */
	private static File makeSureFile(File file) {
		if (file.isFile() == false) {
			char SEP = File.separatorChar;
			File dir = new File(System.getProperty("java.home") + SEP + "lib"
					+ SEP + "security");
			file = new File(dir, file.getName());
			if (file.isFile() == false) {
				file = new File(dir, "cacerts");
			}
		}
		return file;
	}

	/**
	 * 获取keystore
	 * 
	 * @param file
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static KeyStore getKeyStore(File file, String password)
			throws Exception {
		InputStream in = new FileInputStream(file);
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		char[] passphrase = password.toCharArray();
		ks.load(in, passphrase);
		in.close();
		return ks;
	}

	/**
	 * 发送请求.
	 * 
	 * @param url
	 *            请求的地址
	 * @param post_str
	 *            请求的数据
	 * @return
	 */
	public static String post(String url, String post_str) {
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",
					String.valueOf(post_str.getBytes().length));
			urlCon.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			urlCon.getOutputStream().write(post_str.getBytes());
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 实现用于主机名验证的基接口。 在握手期间，如果 URL
	 * 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
	 */
	static class MyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			if ("localhost".equals(hostname)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 
	 * 保存信任证书
	 *
	 */
	public static class SavingTrustManager implements X509TrustManager {
		private final X509TrustManager tm;
		private X509Certificate[] chain;

		public SavingTrustManager(X509TrustManager tm) {
			this.tm = tm;
		}

		public X509TrustManager getTM() {
			return tm;
		}

		public X509Certificate[] getChain() {
			return chain;
		}

		public X509Certificate[] getAcceptedIssuers() {
			throw new UnsupportedOperationException();
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			throw new UnsupportedOperationException();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}

}
