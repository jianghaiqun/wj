package com.sinosoft.cmcore.template;

import com.sinosoft.framework.utility.FileUtil;

public class TemplateManager {
	public static TemplateBase findTemplate(String fileName) {
		return null;
	}

	public static String getFileText(String fileName) {
		return FileUtil.readText(fileName);
	}
}
