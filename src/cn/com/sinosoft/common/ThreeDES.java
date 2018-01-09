package cn.com.sinosoft.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 解密
 */
public class ThreeDES {
	private static final Logger logger = LoggerFactory.getLogger(ThreeDES.class);

	private static String Algorithm = "DESede";// 加密算法的名称
	private static Cipher c;// 密码器
	private static byte[] cipherByte;
	private static SecretKey deskey;// 密钥
	private static String keyString = "L1P9879DESZWFCTCJOTY09RYWIF10H5Y";// 测试机获得密钥的参数
//	private static String keyString = "A1F9879DESJEIWBCJOTY08DYQWF10H5Y";// 正式机获得密钥的参数
															

	//对base64编码的string解码成byte数组
	
	public byte[] deBase64(String parm) throws IOException {
		BASE64Decoder dec = new BASE64Decoder();
		byte[] dnParm = dec.decodeBuffer(parm);

		return dnParm;
	}

	// 把密钥参数转为byte数组
	public byte[] dBase64(String parm) throws IOException {
		BASE64Decoder dec = new BASE64Decoder();
		byte[] dnParm = dec.decodeBuffer(parm);
		return dnParm;
	}

	/**
	 * 对 Byte 数组进行解密
	 * 
	 * @param buff
	 *            要解密的数据
	 * @return 返回加密后的 String
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 */
	public String createDecryptor(String buff) throws NoSuchPaddingException, NoSuchAlgorithmException, 

IOException, InvalidKeyException, InvalidKeySpecException {
		this.getKey(keyString);
		BASE64Decoder dec = new BASE64Decoder();
		byte[] dnParm = dec.decodeBuffer(buff);
		try {
			c.init(Cipher.DECRYPT_MODE, deskey);// 初始化密码器，用密钥deskey,进入解密模式
			cipherByte = c.doFinal(dnParm);
		} catch (java.security.InvalidKeyException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (javax.crypto.BadPaddingException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return (new String(cipherByte, "UTF-8"));
	}
	
	  /**
	   * 对 String 进行加密
	   * @param str 要加密的数据
	   * @return 返回加密后的 byte 数组
	 * @throws Exception 
	   */
	  public String createEncryptor(String str) throws Exception {
		  String sRes = null;
		  this.getKey(keyString);
	    try {
	      c.init(Cipher.ENCRYPT_MODE, deskey);
	      byte data[] = str.getBytes("UTF8");
	      cipherByte = c.doFinal(data);
	       sRes = (new sun.misc.BASE64Encoder()).encodeBuffer(cipherByte);
	    } catch (java.security.InvalidKeyException ex) {
			logger.error(ex.getMessage(), ex);
	    } catch (javax.crypto.BadPaddingException ex) {
			logger.error(ex.getMessage(), ex);
	    } catch (javax.crypto.IllegalBlockSizeException ex) {
			logger.error(ex.getMessage(), ex);
	    }
	    return sRes;
	  }

	public void getKey(String key) throws IOException, InvalidKeyException, InvalidKeySpecException {
		byte[] dKey = dBase64(key);
		try {
			deskey = new javax.crypto.spec.SecretKeySpec(dKey, Algorithm);
			c = Cipher.getInstance(Algorithm);
		} catch (NoSuchPaddingException ex) {
		} catch (NoSuchAlgorithmException ex) {
		}
	}

}