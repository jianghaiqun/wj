package com.sinosoft.misc;

import com.sinosoft.framework.utility.CharsetConvert;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 1.3发版工具类
 */
public class VersionBuilder {
	private static final Logger logger = LoggerFactory.getLogger(VersionBuilder.class);
	public static void main(String[] args) {
		String prefix = "F:/Workspace_Product/ZCMS1.3UTF8/";
		// 删除不需要的文件夹
		FileUtil.delete(prefix + "UI/Test");
		FileUtil.delete(prefix + "UI/Project");
		FileUtil.delete(prefix + "UI/WEB-INF/classes/framework.xml");
		FileUtil.delete(prefix + "UI/WEB-INF/classes/com/zving/project");
		File[] fs = new File(prefix + "UI/wwwroot/ZCMSDemo").listFiles();
		String[] names = new String[] { "cache", "include", "template", "SpryAssets", "js", "images", "upload",
				"index.shtml", "form.shtml" };
		for (int i = 0; i < fs.length; i++) {
			boolean flag = false;
			for (int j = 0; j < names.length; j++) {
				if (names[j].equalsIgnoreCase(fs[i].getName())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				FileUtil.delete(fs[i]);
			}
		}
		generateWar(prefix + "UI/", prefix + "zcms_1.3_final_utf8.war");

		// 以下打包GBK版war文件
		FileUtil.delete(prefix + "GBK");
		FileUtil.copy(prefix + "UI", prefix + "GBK");
		String txt = FileUtil.readText(prefix + "GBK/WEB-INF/classes/charset.config", "UTF-8");
		txt = StringUtil.replaceEx(txt, "UTF-8", "GBK");
		FileUtil.writeText(prefix + "GBK/WEB-INF/classes/charset.config", txt, "UTF-8");
		CharsetConvert.dirUTF8ToGBK(prefix + "GBK");
		generateWar(prefix + "GBK/", prefix + "zcms_1.3_final_gbk.war");
	}

	public static void generateWar(String src, String dest) {
		try {
			File[] fs = new File(src).listFiles();
			String[] arr = new String[fs.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = fs[i].getAbsolutePath();
			}
			ZipUtil.zipBatch(arr, dest);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
