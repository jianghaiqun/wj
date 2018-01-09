package cn.com.sinosoft.util;

import com.sinosoft.webservice.ProductWebservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

	private static InputStream ins = ProductWebservice.class.getResourceAsStream("/productInterface.properties");
	private static Properties p = new Properties();
	static {
		try {
			p.load(ins);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static String getProperty(String tmp){
		return p.getProperty(tmp);
	}
	public static String htmlToString(String input) {
		if (input == null) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			} else if (c == '\'') {
				filtered.append("\\'");
			} else if (c == '\\') {
				filtered.append("\\\\");
			} else if (c == '/') {
				filtered.append("\\/");
			} else if (c == '\t') {
				filtered.append("\\t");
			} else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			} else if (c == '\r') {
				filtered.append("\\n");
			} else if (c == '\f') {
				filtered.append("\\f");
			} else {
				filtered.append(c);
			}
			prevChar = c;
		}
		return filtered.toString().replaceAll("\\\\/", "/");
	}

}