package com.sinosoft.misc;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SourceCodeStat {

	private static final Logger logger = LoggerFactory.getLogger(SourceCodeStat.class);
	public static void main(String[] args) {
		computeZving();
	}

	public static void computeZving() {
		String prefix = Config.getContextRealPath();
		prefix = prefix.substring(0, prefix.length() - 3);
		logger.info(prefix);
		// 计算Java
		String javaPath = prefix + "Java/com/zving";
		File javaFile = new File(javaPath);
		int lineCount = computeLineCount(javaFile);

		String uiPath = prefix + "UI";
		File uiFile = new File(uiPath);
		lineCount += computeLineCount(uiFile);
		lineCount += computeLineCount(new File("F:/Workspace_Platform/Framework/Java"));
		logger.info(String.valueOf(lineCount));
	}

	public static int computeLineCount(File parent) {
		String name = parent.getName();
		if (name.startsWith(".")) {
			return 0;
		}
		if (parent.isFile()) {
			if (parent.getAbsolutePath().indexOf("wwwroot") > 0) {
				return 0;
			}
			if (!name.endsWith(".jsp") && !name.endsWith(".js") && !name.endsWith(".java")) {
				return 0;
			}
			String txt = FileUtil.readText(parent);
			int count = txt.split("\\n").length;
			logger.info(count + "\t" + parent.getAbsolutePath());
			return count;
		} else {
			if (name.equals("schema")) {
				return 0;
			}
			File[] fs = parent.listFiles();
			int count = 0;
			for (int i = 0; i < fs.length; i++) {
				count += computeLineCount(fs[i]);
			}
			return count;
		}
	}
}
