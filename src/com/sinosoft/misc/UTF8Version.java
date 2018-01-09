package com.sinosoft.misc;

import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 从GBK版本转成UTF版本
 */
public class UTF8Version {
	private static final Logger logger = LoggerFactory.getLogger(UTF8Version.class);
	static Pattern p1 = Pattern.compile("charset\\s*\\=\\s*gbk", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	static Pattern p2 = Pattern.compile("charset\\s*\\=\\s*gb2312", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private String target = null;

	private ArrayList list = new ArrayList();

	private long lastConvertTime = 0;// 最后转换时间

	private String currentBase;// 当前转换的目录基准路径

	private String currentDest;// 当前转换的目标目录

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		UTF8Version uv = new UTF8Version();
		uv.setTarget("D:/ZvingDev/WorkSpace/ZCMS_UTF8/");

		uv.addDir("D:/ZvingDev/WorkSpace/ZCMS/Java", "Java");
		uv.addDir("D:/ZvingDev/WorkSpace/ZCMS/UI", "UI");
		//uv.addDir("F:/WorkSpace_Product/ZCMS/UI/wwwroot/ZCMSDemo", "UI/wwwroot/ZCMSDemo");
		uv.addDir("D:/ZvingDev/WorkSpace/Framework/Java", "Java");

		uv.convert();
		logger.info("扫描改动共耗时:" + (System.currentTimeMillis() - t));
	}

	/**
	 * 设置目标目录
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 增加一个待转换的目录，并将该目录下的所有文件转换到目标目录下去
	 */
	public void addDir(String src, String dest) {
		list.add(new String[] { src, dest });
	}

	/**
	 * 清除源目录中已经删除，但目标目录中还存在的文件
	 */
	private void cleanDeleted(String path) {
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; fs != null && i < fs.length; i++) {

		}
	}

	/**
	 * 转换目录为UTF8格式
	 */
	public void convert() {
		long current = System.currentTimeMillis();
		File f = new File(target + "/convert.lock");
		if (f.exists()) {
			lastConvertTime = Long.parseLong(FileUtil.readText(f).trim());
		}
		for (int i = 0; i < list.size(); i++) {
			String[] arr = (String[]) list.get(i);
			currentBase = StringUtil.replaceEx(arr[0], "\\", "/");
			currentDest = arr[1];
			convertDir(arr[0]);
		}
		FileUtil.writeText(target + "/convert.lock", "" + current);
		cleanDeleted(target);
	}

	private void convertDir(String src) {
		File f = new File(src);
		File[] fs = f.listFiles();

		for (int i = 0; fs != null && i < fs.length; i++) {
			f = fs[i];
			String path = StringUtil.replaceEx(f.getAbsolutePath(), "\\", "/");
			path = path.substring(currentBase.length());
			new File(target + currentDest).mkdirs();
			String dest = target + currentDest + path;
			String name = f.getName().toLowerCase();
			if (name.equals(".svn")) {
				continue;
			}
			if (f.isDirectory()) {
				FileUtil.mkdir(dest);
				if (name.equals("classes")) {
					continue;
				}
				if (name.equals("classes")) {
					continue;
				}
				if (dest.indexOf("wwwroot") >= 0 && currentBase.indexOf("wwwroot") < 0) {
					continue;
				}
				if (dest.indexOf("WEB-INF/cache") >= 0) {
					continue;
				}
				if (dest.indexOf("WEB-INF/backup") >= 0) {
					continue;
				}
				if (f.getName().equals(".svn")) {
					continue;
				}
				if (f.getName().equals("classes")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("tools")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("test")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("project")) {
					continue;
				}
				if (dest.indexOf("WEB-INF/data/index") >= 0) {
					continue;
				}
				if (f.getName().equals("logs")) {
					continue;
				}
				convertDir(f.getAbsolutePath());
				continue;
			}
			if (f.isFile()) {
				File destFile = new File(dest);
				if (destFile.exists() && f.lastModified() < lastConvertTime) {// 文件不存在则需要复制
					continue;
				}
			}
			if (dest.indexOf("wwwroot") > 0 && dest.endsWith(".shtml")) {
				continue;
			}
			if (name.endsWith(".java") || name.endsWith(".xml")) {
				String txt = FileUtil.readText(f, "GBK");
				if (name.endsWith(".xml")) {// xml文件大部分是utf-8
					if (txt.indexOf("UTF-8") > 0) {
						txt = FileUtil.readText(f, "UTF-8");
					}
				}
				if (name.equals("web.xml")) {
					txt = StringUtil.replaceEx(txt, "<page-encoding>GBK</page-encoding>",
							"<page-encoding>UTF-8</page-encoding>");
				}
				if (name.equals("constant.java")) {// 需要特殊处理Constant.java
					txt = StringUtil.replaceEx(txt, "GlobalCharset = \"GBK\";", "GlobalCharset = \"UTF-8\";");
				}
				try {
					txt = new String(StringUtil.GBKToUTF8(txt), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
				FileUtil.writeText(dest, txt, "UTF-8", name.endsWith(".java"));
			} else if (name.endsWith(".html") || name.endsWith(".shtml") || name.endsWith(".htm")
					|| name.endsWith(".jsp") || name.endsWith(".js") || name.endsWith(".css")) {
				byte[] bs = FileUtil.readByte(f);
				if (StringUtil.isUTF8(bs)) {
					FileUtil.copy(f, dest);
				} else {
					String txt = null;
					try {
						txt = new String(bs, "GBK");
					} catch (UnsupportedEncodingException e1) {
						logger.error(e1.getMessage(), e1);
					}
					txt = p1.matcher(txt).replaceAll("charset=utf-8");
					txt = p2.matcher(txt).replaceAll("charset=utf-8");
					txt = StringUtil.replaceEx(txt, "\"GBK\"", "\"UTF-8\"");// Search/Result.jsp
					try {
						txt = new String(StringUtil.GBKToUTF8(txt), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					}
					FileUtil.writeText(dest, txt, "UTF-8");
				}
			} else if (name.equalsIgnoreCase("charset.config")) {
				String txt = FileUtil.readText(f, "UTF-8");
				txt = StringUtil.replaceEx(txt, "GBK", "UTF-8");
				FileUtil.writeText(dest, txt, "UTF-8");
			} else {
				FileUtil.copy(f, dest);
			}
			logger.info(dest);
		}
	}
}
