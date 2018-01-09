package com.sinosoft.zas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ClientConfig {
	private static final Logger logger = LoggerFactory.getLogger(ClientConfig.class);

	public static final int TYPE_B_S = 1;
	public static final int TYPE_C_S = 2;
	public static final int MODE_COMMON = 3;
	public static final int MODE_TRUST = 4;
	public static final int ENCRYPTTYPE_RSA = 5;
	public static final int ENCRYPTTYPE_3DES = 6;
	public static final int ENCRYPTTYPE_AES = 7;
	public static final int ENCRYPTTYPE_SHA1 = 8;
	public static final int ENCRYPTTYPE_MD5 = 9;
	private static String path;
	private static String ServiceID;
	private static String ZASServerURL;
	private static int Mode;
	private static int EncryptType;
	private static String Password;
	private static String ProxyCallbackURL;
	private static boolean ProxyEnable;
	private static boolean NeedNewLogin;

	static {
		parse();
	}

	public static void parse() {
		path = ClientConfig.class.getResource("ClientConfig.class").getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			if (path.startsWith("/"))
				path = path.substring(1);
			else if (path.startsWith("file:/")) {
				path = path.substring(6);
			}

			path = path.replaceAll("%20", " ");
		} else if (path.startsWith("file:/")) {
			path = path.substring(5);
		}

		String xml = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "WEB-INF/zas_client.xml"), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			xml = sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		HashMap<String,String> map = Util.parseXML(xml);
		Object[] ks = map.keySet().toArray();
		for (int j = 0; j < map.size(); j++) {
			String name = ks[j].toString();
			String value = (String) map.get(ks[j]);
			if (name.equals("ServiceID")) {
				ServiceID = value;
			} else if (name.equals("ZASServerURL")) {
				if (!value.endsWith("/")) {
					value = value + "/";
				}
				ZASServerURL = value;
			} else if (name.equals("EncryptType")) {
				if ("3DES".equalsIgnoreCase(value))
					EncryptType = 6;
				else if ("AES".equalsIgnoreCase(value))
					EncryptType = 7;
				else if ("RSA".equalsIgnoreCase(value))
					EncryptType = 5;
				else if ("SHA1".equalsIgnoreCase(value))
					EncryptType = 8;
				else if ("MD5".equalsIgnoreCase(value))
					EncryptType = 9;
				else
					throw new RuntimeException("不支持的加密模式：" + value);
			} else if (name.equals("Mode")) {
				if ("Common".equals(value)) {
					Mode = 3;
				}
				if ("Trust".equals(value))
					Mode = 4;
			} else if (name.equals("ProxyEnable")) {
				ProxyEnable = "true".equals(value);
			} else if (name.equals("ProxyCallbackURL")) {
				ProxyCallbackURL = value;
			} else if (name.equals("NeedNewLogin")) {
				NeedNewLogin = "true".equals(value);
			} else if (name.equals("Password")) {
				Password = value;
			} else if (name.equals("ServiceID")) {
				ServiceID = value;
			}
		}
	}

	public static String getProxyCallbackURL() {
		return ProxyCallbackURL;
	}

	public static String getServerURL() {
		return ZASServerURL;
	}

	public static String getZASServerURL() {
		return ZASServerURL;
	}

	public static int getMode() {
		return Mode;
	}

	public static int getEncryptType() {
		return EncryptType;
	}

	public static String getPassword() {
		return Password;
	}

	public static boolean isProxyEnable() {
		return ProxyEnable;
	}

	public static boolean isNeedNewLogin() {
		return NeedNewLogin;
	}

	public static String getServiceID() {
		return ServiceID;
	}
}
