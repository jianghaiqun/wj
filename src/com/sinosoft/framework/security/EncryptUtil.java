package com.sinosoft.framework.security;

import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	public static final String DEFAULT_KEY = "27jrWz3sxrVbR+pnyg6j";

	public static String encrypt3DES(String str, String password) {
		String strResult = null;
		try {
			// �����Կ
			byte[] key = password.getBytes();
			byte[] encodeString = str.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			Z3DESCipher cipher = new Z3DESCipher();
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] cipherByte = cipher.doFinal(encodeString);
			strResult = StringUtil.base64Encode(cipherByte);
		} catch (java.security.NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (javax.crypto.NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (java.lang.Exception e) {
			logger.error(e.getMessage(), e);
		}
		return strResult;
	}

	public static String decrypt3DES(String str, String password) {
		String strResult = null;
		try {
			// �����Կ
			byte[] key = password.getBytes();
			byte[] src = StringUtil.base64Decode(str);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			Z3DESCipher cipher = new Z3DESCipher();
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] cipherByte = cipher.doFinal(src);
			strResult = new String(cipherByte);
		} catch (java.security.NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (javax.crypto.NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (java.lang.Exception e) {
			logger.error(e.getMessage(), e);
		}
		return strResult;
	}
}
