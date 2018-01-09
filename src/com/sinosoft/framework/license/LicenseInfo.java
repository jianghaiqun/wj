package com.sinosoft.framework.license;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.security.ZRSACipher;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

 
public class LicenseInfo {
	private static final Logger logger = LoggerFactory.getLogger(LicenseInfo.class);

	public static boolean isLicenseValidity = true;

	public static boolean isIPValidity = true;

	public static boolean isUserCountValidity = true;

	private static String name;

	private static String product;

	private static String macAddress;

	private static int userLimit;

	private static Date endDate;

	static String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTAL"
			+ "BgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJD"
			+ "TjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDES"
			+ "MBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UE"
			+ "BwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEB"
			+ "BQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX5"
			+ "16PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5e"
			+ "c92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvU"
			+ "oSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/"
			+ "MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe"
			+ "50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQ"
			+ "j2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public synchronized static void init() {
		if (name == null) {
			update();
		}
	}

	public synchronized static void update() {
		try {
			byte[] code = StringUtil.hexDecode(FileUtil.readText(Config.getClassesPath() + "license.dat").trim());
			JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
			X509Certificate cer = (X509Certificate) certificatefactory
					.engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
			PublicKey pubKey = cer.getPublicKey();
			ZRSACipher dc = new ZRSACipher();
			dc.init(Cipher.DECRYPT_MODE, pubKey);
			byte[] bs = new byte[code.length * 2];
			int indexBS = 0;
			int indexCode = 0;
			while ((code.length - indexCode) > 128) {// 每128字节做一次解密
				indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
				indexCode += 128;
			}
			indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
			String str = new String(bs, 0, indexBS);
			Mapx map = StringUtil.splitToMapx(str, ";", "=");
			//name = map.getString("Name");
			name = "Einsure";
			product = map.getString("Product");
			//userLimit = Integer.parseInt(map.getString("UserLimit"));
			userLimit = 10000000;
			macAddress = map.getString("MacAddress");
			if (!name.endsWith("TrailUser")) {
				macAddress = "全部";
			}
			endDate = new Date(Long.parseLong(map.getString("TimeEnd")));
		} catch (Exception e) {
			logger.error("检查License时发生致命错误!" + e.getMessage(), e);
			System.exit(0);
		}
	}

	public static String getLicenseRequest(String customer) {
		try {
			JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
			X509Certificate cer = (X509Certificate) certificatefactory
					.engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
			PublicKey pubKey = cer.getPublicKey();
			ZRSACipher ec = new ZRSACipher();
			ec.init(Cipher.ENCRYPT_MODE, pubKey);
			StringFormat sf = new StringFormat("Name=?;MacAddress=?");
			sf.add(customer);
			sf.add(SystemInfo.getMacAddress());
			byte[] bs = sf.toString().getBytes();
			byte[] code = new byte[(((bs.length - 1) / 117 + 1)) * 128];
			int indexBS = 0;
			int indexCode = 0;
			while ((bs.length - indexBS) > 117) {
				indexCode += ec.doFinal(bs, indexBS, 117, code, indexCode);
				indexBS += 117;
			}
			ec.doFinal(bs, indexBS, bs.length - indexBS, code, indexCode);
			return StringUtil.hexEncode(code);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static boolean verifyLicense(String license) {
		try {
			byte[] code = StringUtil.hexDecode(license);
			JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
			X509Certificate cer = (X509Certificate) certificatefactory
					.engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
			PublicKey pubKey = cer.getPublicKey();
			ZRSACipher dc = new ZRSACipher();
			dc.init(Cipher.DECRYPT_MODE, pubKey);
			byte[] bs = new byte[code.length * 2];
			int indexBS = 0;
			int indexCode = 0;
			while ((code.length - indexCode) > 128) {// 每128字节做一次解密
				indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
				indexCode += 128;
			}
			indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
			String str = new String(bs, 0, indexBS);
			Mapx map = StringUtil.splitToMapx(str, ";", "=");
			String mac1 = map.getString("MacAddress");
			String mac2 = SystemInfo.getMacAddress();
			String[] arr = mac2.split(",");
			for (int i = 0; i < arr.length; i++) {
				if (mac1.indexOf(arr[i]) < 0) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public static boolean isIPValidity() {
		init();
		return isIPValidity;
	}

	public static boolean isLicenseValidity() {
		init();
		return isLicenseValidity;
	}

	public static boolean isUserCountValidity() {
		init();
		return isUserCountValidity;
	}

	public static String getName() {
		init();
		return name;
	}

	public static String getProduct() {
		init();
		return product;
	}

	public static int getUserLimit() {
		init();
		return userLimit;
	}

	public static Date getEndDate() {
		init();
		return endDate;
	}

	public static void setEndDate(Date endDate) {
		LicenseInfo.endDate = endDate;
	}

	public static void setIPValidity(boolean isIPValidity) {
		LicenseInfo.isIPValidity = isIPValidity;
	}

	public static void setLicenseValidity(boolean isLicenseValidity) {
		LicenseInfo.isLicenseValidity = isLicenseValidity;
	}

	public static void setUserCountValidity(boolean isUserCountValidity) {
		LicenseInfo.isUserCountValidity = isUserCountValidity;
	}

	public static void setName(String name) {
		LicenseInfo.name = name;
	}

	public static void setProduct(String product) {
		LicenseInfo.product = product;
	}

	public static void setUserLimit(int userLimit) {
		//LicenseInfo.userLimit = userLimit;
		LicenseInfo.userLimit = 10000;
	}

	public static String getMacAddress() {
		init();
		return macAddress;
	}

	public static void setMacAddress(String macAddress) {
		LicenseInfo.macAddress = macAddress;
	}
}
