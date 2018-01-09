package cn.com.sinosoft.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 提供字符串的加解密和签名编码的工具类 <br>
 * 
 * <p>
 * Copyright: Copyright (c) 2011-7-5 下午01:47:05
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * @author kongjp@c-platform.com
 * @version 1.0.0
 */
public class SzPayUtil {
	private static final Logger logger = LoggerFactory.getLogger(SzPayUtil.class);

	/** 加密算法 */
	private  String Algorithm = "DESede";

	/** 密钥 */
	private  byte[] keyBytes = "bamjgipetdlcrwqfzokxuysh".getBytes();

	public  String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**对指定密文解密
	 * @param encodedCode
	 * @return
	 */
	public  String decryptIt(String encodedCode) {
		byte[] encoded = decryptMode(keyBytes, hex2byte(encodedCode));
		return new String(encoded);
	}

	/**使用desede算法解密
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public  byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		}
		catch (java.security.NoSuchAlgorithmException e1) {
			logger.error(e1.getMessage(), e1);
		}
		catch (javax.crypto.NoSuchPaddingException e2) {
			logger.error(e2.getMessage(), e2);
		}
		catch (java.lang.Exception e3) {
			logger.error(e3.getMessage(), e3);
		}
		return null;
	}

	/**对源串进行加密
	 * @param sourceCode
	 * @return
	 */
	public  String encryptIt(String sourceCode) {
		byte[] encoded = encryptMode(keyBytes, sourceCode.getBytes());
		return byte2hex(encoded);
	}

	/**使用desede算法加密
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public  byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		}
		catch (java.security.NoSuchAlgorithmException e1) {
			logger.error(e1.getMessage(), e1);
		}
		catch (javax.crypto.NoSuchPaddingException e2) {
			logger.error(e2.getMessage(), e2);
		}
		catch (java.lang.Exception e3) {
			logger.error(e3.getMessage(), e3);
		}
		return null;
	}

	public  byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}
	
	/**执行签名的方法
	 * @param baseStr
	 * @param keyString
	 * @return
	 * @throws Exception
	 */
	public  String doSign(String baseStr, String keyString) throws Exception {
		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		byte[] bytes = mac.doFinal(baseStr.getBytes("UTF-8"));
		return new String(Base64.encode(bytes));
	}
	

	/** Des 加密
	 * @param sourceCode
	 * @return
	 */
	public  String DesEncrpty(String sourceCode, String secKey) {
		byte[] encoded = encryptMode(secKey.getBytes(), sourceCode.getBytes());
		return byte2hex(encoded);
	}
	
	/**Des 解密
	 * @param encodedCode
	 * @return
	 */
	public  String DesDcrpty(String encodedCode, String secKey) {
		byte[] encoded = decryptMode(secKey.getBytes(), hex2byte(encodedCode));
		return new String(encoded);
	}
	
	public  String getNonce() {
		return RandomStringUtils.random(6, false, true);
	}

	public  String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

}
