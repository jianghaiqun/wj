package com.sinosoft.misc;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注意：FreeBSD上 <z:select>${Value}</z:select>中的${会神秘消失，必须在${Value}前面加空格<br>
 * 本类即扫描所有未加空格的写法，然后手工修改之。
 * 
 */
public class SelectControlConvert {
	private static final Logger logger = LoggerFactory.getLogger(SelectControlConvert.class);
	static Pattern pattern = Pattern.compile("<z\\:[^>]*?> (\\$\\{.*?\\})<\\/z\\:.*?>", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	public static void main(String[] args) {
		long t = System.currentTimeMillis();

		File f = new File(Config.getContextRealPath());
		dealPath(f);

		logger.info(String.valueOf(System.currentTimeMillis() - t));
	}

	public static void dealPath(File parent) {
		if (parent.isFile()) {
			if (!parent.getName().toLowerCase().endsWith(".jsp")) {
				return;
			}
			String txt = FileUtil.readText(parent);
			int lastIndex = 0;
			Matcher m = pattern.matcher(txt);
			while (m.find(lastIndex)) {
				lastIndex = m.end();
				logger.info(parent.getName());
				logger.info(m.group(0));
				logger.info("\n\n");
				String tag = m.group(0);
				tag = StringUtil.replaceEx(tag, ">$", "> $");
				txt = StringUtil.replaceEx(txt, m.group(0), tag);
				String fileName = parent.getAbsolutePath();
				fileName = fileName.replaceAll("UI", "Patch");
				String path = fileName.substring(0, fileName.lastIndexOf("\\"));
				FileUtil.mkdir(path);
				FileUtil.writeText(fileName, txt);
			}

		} else {
			File[] fs = parent.listFiles();
			for (int i = 0; i < fs.length; i++) {
				dealPath(fs[i]);
			}
		}
	}
}
