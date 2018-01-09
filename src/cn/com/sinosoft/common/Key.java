package cn.com.sinosoft.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *   Key
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF7B18E3CA7A2C555E2B9749EA6B332EA
 * ============================================================================
 */

public class Key {
	private static final Logger logger = LoggerFactory.getLogger(Key.class);

	public static String readKeyFile(String keyFilePath) {
//		System.out.println("*********"+keyFilePath);

		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(keyFilePath), "UTF-8"));
			String line;
			for (int i = 0; i < 4; i ++) {
				line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				if (i == 0) {
					BASE64Encoder bASE64Encoder = new BASE64Encoder();
					String baseString = bASE64Encoder.encode(line.getBytes());
					String test = bASE64Encoder.encode(line.getBytes());
					if (!StringUtils.equals(StringUtils.substring(baseString, 8), "L3d3dy5zaG9weHgubmV0")) {
						throw new ExceptionInInitializerError();
					}
				}
//				System.out.println(line);
				stringBuffer.append(line);
			}
			if (stringBuffer.length() != 71) {
				throw new ExceptionInInitializerError();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(bufferedReader != null) {
				try{
					bufferedReader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
//		System.out.println("--------"+stringBuffer.toString());
		return stringBuffer.toString();
	}
	
	public static void writeKeyFile(String keyFilePath) {
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(keyFilePath); 
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(128);
			java.security.Key key = keyGenerator.generateKey();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(key);
			objectOutputStream.close();
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
        }
	}
	
	public static String encrypt(String content, String keyFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(keyFilePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			java.security.Key key = (java.security.Key) objectInputStream.readObject();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(content.getBytes("UTF-8"));
			return cipherText.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String decrypt(String content, String keyFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(keyFilePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			java.security.Key key = (java.security.Key) objectInputStream.readObject();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(content.getBytes("UTF-8"));
			return cipherText.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}