package com.sinosoft.framework.license;

import com.sinosoft.framework.utility.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
public class SystemInfo {
	private static final Logger logger = LoggerFactory.getLogger(SystemInfo.class);

	public final static String getMacAddress() {
		String os = System.getProperty("os.name").toLowerCase();
		try {
			String cmd = "ipconfig /all";
			if (os.indexOf("windows") < 0) {
				cmd = "ifconfig";
			}
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream is = proc.getInputStream();
			String output = FileUtil.readText(is, "GBK");
			Pattern p = Pattern.compile("([0-9a-zA-z]{2}[\\:\\-]){5}[0-9a-zA-z]{2}", Pattern.DOTALL);
			Matcher m = p.matcher(output);
			int lastIndex = 0;
			StringBuffer sb = new StringBuffer();
			while (m.find(lastIndex)) {
				if (lastIndex != 0) {
					sb.append(",");
				}
				sb.append(m.group(0));
				lastIndex = m.end();
			}
			return sb.toString();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}
}