package com.sinosoft.zas.client;

import com.sinosoft.zas.ClientConfig;
import com.sinosoft.zas.UserData;
import com.sinosoft.zas.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;

public class ServiceTicketValidator {
	private static final Logger logger = LoggerFactory.getLogger(ServiceTicketValidator.class);

	protected String ticket;
	protected String response;
	protected UserData user;
	protected String pgt;
	protected static String cn;
	protected static PublicKey pk;
	protected static Cipher encryptCipher;
	protected static Cipher decryptCipher;

	public UserData getUser() {
		return this.user;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void validate() throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(ClientConfig.getServerURL() + "ServiceValidate.jsp");
		sb.append("?");
		sb.append("ServiceID=" + ClientConfig.getServiceID() + "&ST=" + this.ticket);
		if (ClientConfig.isNeedNewLogin()) {
			sb.append("&NeedNewLogin=true");
		}
		this.response = Util.getURLContent(sb.toString());
		parseResponse();
	}

	protected static void initCert() {
		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			String password = ClientConfig.getPassword();
			byte[] bs = Util.base64Decode(password.replaceAll("\\s*", ""));
			ByteArrayInputStream fin = new ByteArrayInputStream(bs);
			X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(fin);
			fin.close();
			String dn = cert.getIssuerDN().getName();
			int index1 = dn.indexOf("CN=") + 3;
			int index2 = dn.indexOf(",", index1);
			if (index2 < 0) {
				index2 = dn.length();
			}
			cn = dn.substring(index1, index2);
			pk = cert.getPublicKey();
			encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			encryptCipher.init(1, pk);
			decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			decryptCipher.init(2, pk);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("初始化Proxy证书时发生错误!");
		}
	}

	public static String encrypt(String str) throws ServletException {
		if (cn == null)
			initCert();
		try {
			byte[] code = (byte[]) null;
			try {
				byte[] bs = str.getBytes("UTF-8");

				code = new byte[((bs.length - 1) / 117 + 1) * 128];
				int indexBS = 0;
				int indexCode = 0;
				while (bs.length - indexBS > 117) {
					indexCode += encryptCipher.doFinal(bs, indexBS, 117, code, indexCode);
					indexBS += 117;
				}
				encryptCipher.doFinal(bs, indexBS, bs.length - indexBS, code, indexCode);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return Util.base64Encode(code);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		throw new ServletException("加密时发生错误!");
	}

	public static String decrypt(String str) throws ServletException {
		if (cn == null)
			initCert();
		try {
			byte[] code = Util.base64Decode(str);
			byte[] bs = new byte[code.length];
			int indexBS = 0;
			int indexCode = 0;
			while (code.length - indexCode > 128) {
				indexBS += decryptCipher.doFinal(code, indexCode, 128, bs, indexBS);
				indexCode += 128;
			}
			indexBS += decryptCipher.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
			return new String(bs, 0, indexBS, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		throw new ServletException("解密时发生错误");
	}

	protected void parseResponse() {
		HashMap<String,String> map = Util.parseXML(this.response);
		this.user = new UserData();
		Object[] ks = map.keySet().toArray();
		try {
			for (int i = 0; i < map.size(); i++) {
				String name = ks[i].toString();
				String value = (String) map.get(ks[i]);
				if (name.equals("UserName")) {
					if ((ClientConfig.getMode() == 4) && (value.length() > 50)) {
						value = decrypt(value);
					}
					this.user.setUserName(value);
				} else if (name.equals("Status")) {
					if (!value.equals("OK"))
						throw new ServletException("检验Ticket失败!");
				} else if ((name.equals("PGT")) && (ClientConfig.isProxyEnable())) {
					if (ClientConfig.getMode() == 4) {
						value = decrypt(value);
					}
					this.pgt = value;
				} else {
					this.user.setValue(name, value);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getProxyGrantingTicket() {
		return this.pgt;
	}
}
