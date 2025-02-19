package com.sinosoft.misc;

import java.io.File;

import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 去掉JSP页面中多余的行.(文件从服务器上传来传去时可能会有出现这种现象.)
 * 
 */
public class TrimEmptyLine {

	public static void main(String[] args) {
		String path = "F:/Workspace_Platform/Platform/UI/Search/";
		File[] fs = new File(path).listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (!f.getName().endsWith("jsp")) {
				continue;
			}
			String content = FileUtil.readText(f);
			String[] arr = content.split("\\n");
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < arr.length; j++) {
				if (StringUtil.isEmpty(arr[j].trim())) {
					if (j < arr.length - 1 && StringUtil.isEmpty(arr[j + 1].trim())) {
						sb.append(arr[j].trim());
						sb.append("\n");
					}
				} else {
					sb.append(StringUtil.rightTrim(arr[j]));
					sb.append("\n");
				}
			}
			FileUtil.writeText(f.getAbsolutePath(), sb.toString());
		}
	}
}
