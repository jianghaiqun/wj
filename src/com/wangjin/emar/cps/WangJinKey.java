package com.wangjin.emar.cps;

import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WangJinKey {

	private MessageDigest md = null;
	private static WangJinKey md5 = null;
	private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/**
	 * Constructor is private so you must use the getInstance method
	 */
	private WangJinKey() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
	}

	/**
	 * This returns the singleton instance
	 */
	public static WangJinKey getInstance() throws NoSuchAlgorithmException {
		if (md5 == null) {
			md5 = new WangJinKey();
		}
		return (md5);
	}

	private static String hashCode(String dataToHash) throws NoSuchAlgorithmException {
		return getInstance().hashData(dataToHash.getBytes());
	}

	private String hashData(byte[] dataToHash) {
		return hexStringFromBytes((calculateHash(dataToHash))).toLowerCase();
	}

	private byte[] calculateHash(byte[] dataToHash) {
		md.update(dataToHash, 0, dataToHash.length);
		return (md.digest());
	}

	private String hexStringFromBytes(byte[] b) {
		String hex = "";
		int msb;
		int lsb = 0;
		int i;
		for (i = 0; i < b.length; i++) {
			msb = ((int) b[i] & 0x000000FF) / 16;
			lsb = ((int) b[i] & 0x000000FF) % 16;
			hex = hex + hexChars[msb] + hexChars[lsb];
		}
		return (hex);
	}

	public static String getkey() throws Exception {

		return hashCode(getTime() + hashCode(getSalt()));
	}

	private static String getTime() throws Exception {
		URL url = new URL("http://www.bjtime.cn");// 取得资源对象
		URLConnection uc = url.openConnection();// 生成连接对象
		uc.connect(); // 发出连接
		long ld = uc.getDate(); // 取得网站日期时间（时间戳）
		Date date = new Date(ld); // 转换为标准时间对象
		String fmt = "yyyyMMddHHmm";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateStr = sdf.format(date);
		return dateStr.substring(0, 11);
	}

	private static String getSalt() throws Exception {
		StringBuffer salt = new StringBuffer();
		String time = getTime();
		if (Integer.valueOf(time.substring(0, 5)) % 4 == 0) {
			salt.append("s%-");
		} else if (Integer.valueOf(time.substring(0, 5)) % 4 == 1) {
			salt.append("Dt^*");
		} else if (Integer.valueOf(time.substring(0, 5)) % 4 == 2) {
			salt.append(":}*(J");
		} else if (Integer.valueOf(time.substring(0, 5)) % 4 == 3) {
			salt.append("U)UJI");
		}

		if (Integer.valueOf(time.substring(5, 9)) % 4 == 0) {
			salt.append("(*B|");
		} else if (Integer.valueOf(time.substring(5, 9)) % 4 == 1) {
			salt.append("^(*H");
		} else if (Integer.valueOf(time.substring(5, 9)) % 4 == 2) {
			salt.append("^%Y");
		} else if (Integer.valueOf(time.substring(5, 9)) % 4 == 3) {
			salt.append("RU*U");
		}

		if (Integer.valueOf(time.substring(9, 11)) % 3 == 0) {
			salt.append("*&(");
		} else if (Integer.valueOf(time.substring(9, 11)) % 3 == 1) {
			salt.append("J!JN");
		} else if (Integer.valueOf(time.substring(9, 11)) % 3 == 2) {
			salt.append("*(&HO");
		}
		return salt.toString();
	}

}
