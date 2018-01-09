package com.sinosoft.zas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.HashMap;

public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	static BASE64Encoder encoder = new BASE64Encoder();

	static BASE64Decoder decoder = new BASE64Decoder();

	private static int TRANSACTION_ID_LENGTH = 32;

	private static char[] cs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

	public static String base64Encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return encoder.encode(b).replaceAll("\\s", "");
	}

	public static byte[] base64Decode(String s) {
		if (s != null) {
			try {
				return decoder.decodeBuffer(s);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static String getURLContent(String url) throws IOException {
		return getURLContent(url, true);
	}

	public static String getURLContent(String url, boolean first) throws IOException {
		BufferedReader r = null;
		try {
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			uc.setRequestProperty("Connection", "close");
			r = new BufferedReader(new InputStreamReader(uc.getInputStream()));

			StringBuffer buf = new StringBuffer();
			String line;
			while ((line = r.readLine()) != null) {
				buf.append(line + "\n");
			}
			return buf.toString();
		} catch (Exception e) {
			String str1;
			if (first) {
				logger.error("getURLContent()发生异常，重试一次" + e.getMessage(), e);
				str1 = getURLContent(url, false);
				return str1;
			}
		} finally {
			try {
				if (r != null)
					r.close();
			} catch (IOException localIOException3) {
			}
		}
		return null;
	}

	private static String toPrintable(byte[] b) {
		char[] out = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % cs.length;
			if (index < 0) {
				index += cs.length;
			}
			out[i] = cs[index];
		}
		return new String(out);
	}

	public static String getTransactionId() {
		byte[] b = new byte[TRANSACTION_ID_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(b);
		return toPrintable(b);
	}

	public static HashMap<String,String> parseXML(String xml) {
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName("var");
			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);
				String name = element.getAttribute("name");
				String value = element.getChildNodes().item(0).getNodeValue();
				map.put(name, value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	public static void main(String[] args) {
		/*String fileName = "F:/Workspace_Platform/ZASClient/UI/WEB-INF/zas_client.xml";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			HashMap<String,String> map = parseXML(sb.toString());
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
