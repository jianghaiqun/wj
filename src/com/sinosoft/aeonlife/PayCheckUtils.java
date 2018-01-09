package com.sinosoft.aeonlife;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 对账模块用到的解压解密验签等工具类
 * @author FANFANGMING374
 */
public class PayCheckUtils {
	
	private static final Log logger = LogFactory.getLog(PayCheckUtils.class);
	
	/**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	/**
	 * 压缩指定文件或目录，当为目录时递归压缩其下子目录和子文件
	 * @param filePath 欲压缩的文件或目录
	 * @param zipPath 指定压缩后的文件名
	 * @param dirFlag 是否要生成第一级目录
	 */
	public static void zipMultiFile(String filePath, String zipPath, boolean dirFlag) throws Exception {
		ZipOutputStream zipOut = null;
		try {
			File file = new File(filePath);
			File zipFile = new File(zipPath);
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File fileSec : files) {
					if (dirFlag) {
						recursionZip(zipOut, fileSec, file.getName() + File.separator);
					} else {
						recursionZip(zipOut, fileSec, "");
					}
				}
			} else {
				recursionZip(zipOut, file, "");
			}
			zipOut.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new Exception("压缩文件抛异常" + ex.getMessage());
		} finally {
			try {
				if (zipOut != null)
					zipOut.close();
			} catch (IOException e) {
				throw new Exception("压缩文件关闭流抛异常" + e.getMessage());
			}
		}
	}
	
	private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileSec : files) {
				recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
			}
		} else {
			byte[] buf = new byte[1024];
			InputStream input = new FileInputStream(file);
			zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
			int len;
			while ((len = input.read(buf)) != -1) {
				zipOut.write(buf, 0, len);
			}
			input.close();
		}
	}
	
	/**
     * 解压ZIP文件
     * @param zipFile  要解压的ZIP文件路径
     * @param destination  解压目标路径
     * @throws IOException
     */
	public static void unzip(String zipFile, String destPath) throws Exception {
		ZipFile zip = null;
		ZipEntry entry = null;
		byte[] buffer = new byte[8192];
		int length = -1;
		InputStream input = null;
		BufferedOutputStream bos = null;
		File file = null;
		try {
			zip = new ZipFile(zipFile);
			Enumeration en = zip.entries();
			while (en.hasMoreElements()) {
				entry = (ZipEntry) en.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				input = zip.getInputStream(entry);
				file = new File(destPath, entry.getName());
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				bos = new BufferedOutputStream(new FileOutputStream(file));
				while (true) {
					length = input.read(buffer);
					if (length == -1) {
						break;
					}
					bos.write(buffer, 0, length);
				}
				bos.close();
				input.close();
			}
			zip.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new Exception("解压缩文件抛异常" + ex.getMessage());
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (input != null)
					input.close();
				if (zip != null)
					zip.close();
			} catch (IOException e) {
				throw new Exception("解压缩文件关闭流抛异常" + e.getMessage());
			}
		}
		
	}
	
	/**
	 * 3DES算法加解密文件
	 * @param sourceFilePath 用作加密或解密的源文件路径名
	 * @param targetFilePath 加解密后输出的目标文件路径名
	 * @param keyGenStr 生成密钥字符串
	 * @param opMode 加解密模式区分(1:加密  2:解密)
	 * @throws Exception
	 */
	public static void encryptOrDecryptBy3DES(String sourceFilePath, String targetFilePath, String keyGenStr, int opMode) throws Exception {
		FileInputStream fis = null;
		CipherInputStream cis = null;
		FileOutputStream fos = null;
		try {
			// 生成密钥
//			if(StringUtils.isEmpty(keyGenStr) || keyGenStr.length() < 24) {
//	            throw new Exception("传入的3DES key长度必须大于24");
//	        }
//			SecretKeySpec keySpec = new SecretKeySpec(keyGenStr.getBytes("UTF-8"), 0, 24, "DESede");
			SecretKeySpec keySpec = new SecretKeySpec(build3DesKey(keyGenStr), "DESede");
			// 创建并初始化密码器
			Cipher cp = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			if (opMode == 1) {
				cp.init(Cipher.ENCRYPT_MODE, keySpec);
			} else if (opMode == 2) {
				cp.init(Cipher.DECRYPT_MODE, keySpec);
			} else {
				throw new Exception("加解密模式区分输入值非法！");
			}
			
			fis = new FileInputStream(sourceFilePath);
			cis = new CipherInputStream(fis, cp);// 创建CipherInputStream对象
			fos = new FileOutputStream(targetFilePath);// 建立文件输出流
			// 如果是加密操作
			if (opMode == 1) {
				int b = 0;
				while ((b = cis.read()) != -1) {
					fos.write((byte) b);
				}
			} else {// 如果是解密操作
				int b = 0;
				while ((b = cis.read()) != -1) {
					fos.write(b);
				}
			}
			cis.close();
			fis.close();
			fos.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new Exception("加解密3DES文件抛异常" + ex.getMessage());
		} finally {
			try {
				if (cis != null)
					cis.close();
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				throw new Exception("加解密3DES文件关闭流抛异常" + e.getMessage());
			}
		}
	}
	
	/**
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 生成密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] build3DesKey(String keyStr) {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = null;
		try {
			temp = keyStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			keyStr.getBytes();// 将字符串转成字节数组
		} 

		// 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	/**
	 * 计算文件的MD5
	 */
	public static String getMd5ByFile(File file) throws Exception {
		String value = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			MappedByteBuffer byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new Exception("计算文件MD5抛异常" + ex.getMessage());
		} finally {
			try {
				if (null != fis)
					fis.close();
			} catch (IOException e) {
				throw new Exception("计算文件MD5关闭流抛异常" + e.getMessage());
			}
		}
		return value;
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * @param data 已加密数据
	 * @param privateKey 私钥(BASE64编码)
	 * @return 签名
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		try {
			byte [] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(data);
			return new BASE64Encoder().encode(signature.sign());
//			return Base64Utils.encode(signature.sign());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new Exception("对账文件生成数字签名抛异常" + ex.getMessage());
		}
	}

	/**
	 * 校验数字签名
	 * @param data 已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign 数字签名
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte [] keyBytes = base64Decoder.decodeBuffer(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(base64Decoder.decodeBuffer(sign));
	}
	
	/**
	 * 读取文件内容
	 * @param fileName 文件全路径名
	 * @return String形式文件内容
	 */
	public static String readFile(String fileName) {
		String output = null;
		File file = new File(fileName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
//					BufferedReader br = new BufferedReader(new FileReader(file));
//					StringBuffer sb = new StringBuffer();
//					String text;
//					while ((text = br.readLine()) != null) {
//						sb.append(text + "/n");
//					}
//					output = sb.toString();
					output = IOUtils.toString(new BufferedReader(new FileReader(file)));
				} catch (IOException ioEx) {
					logger.info("Read File " + fileName + " error!", ioEx);
				}
			} else {
				logger.info("File " + fileName + " is a directory, not a file!");
			}
		} else {
			logger.info("File " + fileName + " does not exist!");
		}
		return output;
	}
	
	
	public static void stream2file(InputStream inStream, String filePath) {
		File file = new File(filePath);
		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = inStream.read(tmp)) != -1) {
				fOutStream.write(tmp, 0, l);
			}
			fOutStream.flush();
			fOutStream.close();
		} catch (Exception ex) {
			logger.info("从流读取并写入文件" + filePath + "时发生异常 " + ex.getMessage());
		} finally {
			try {
				if (fOutStream != null)
					fOutStream.close();
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				logger.info("从流读取并写入文件后关闭流发生异常");
			}
		}
	}
	
	public static void string2file(String content, String filePath, String charset) {
		File file = new File(filePath);
		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(file);
			fOutStream.write(content.getBytes(charset));
			fOutStream.flush();
			fOutStream.close();
		} catch (Exception ex) {
			logger.info("将string写入文件" + filePath + "时发生异常 " + ex.getMessage());
		} finally {
			try {
				if (fOutStream != null)
					fOutStream.close();
			} catch (IOException e) {
				logger.info("将string写入文件后关闭流发生异常");
			}
		}
	}
	
	public static String getFileNameFromHttpResponse(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						//filename = new String(param.getValue().toString().getBytes(), "utf-8");
						//filename = URLDecoder.decode(param.getValue(),"utf-8");
						filename = param.getValue();
					} catch (Exception ex) {
						logger.info("从HttpResponse中取对账文件的文件名抛异常 " + ex.getMessage());
					}
				}
			}
		}
		return filename;
	}
	
}
