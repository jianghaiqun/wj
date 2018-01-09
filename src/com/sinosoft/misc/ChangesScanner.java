package com.sinosoft.misc;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扫描指定的日期之后有哪些文件修改过了，并复制到指定目录
 * 
 */
public class ChangesScanner {
	private String target;
	private long date;
	private String prefix;

	public static void main(String[] args) {
		ChangesScanner cs = new ChangesScanner("2010-04-27", "F:/LastModified/");
		cs.scan();
	}

	public ChangesScanner(String date, String path) {
		this.target = path;
		this.date = DateUtil.parseDateTime(date).getTime();
	}

	public void scan() {
		FileUtil.delete(this.target);
		prefix = Config.getContextRealPath();
		if (prefix.endsWith("/")) {
			prefix = prefix.substring(0, prefix.length() - 1);
		}
		prefix = prefix.substring(0, prefix.lastIndexOf('/'));
		File f = new File(prefix);
		scanOneDir(f);

		// 获取Framework的更新
		String txt = FileUtil.readText(prefix + "/.project");
		Pattern p = Pattern.compile("<location>(.*?)<\\/location>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(txt);
		int lastIndex = 0;
		while (m.find(lastIndex)) {
			String location = m.group(1);
			if (location.toLowerCase().indexOf("framework") > 0) {
				scanOneDir(new File(location));
				break;
			}
			lastIndex = m.end();
		}
	}

	public void scanOneDir(File f) {
		if (f.isFile()) {
			if (f.lastModified() > date) {
				if (f.getName().endsWith(".shtml")) {
					return;
				}
				String path = f.getAbsolutePath();
				path = path.replace('\\', '/');
				path = path.substring(0, path.lastIndexOf('/'));
				path = path.substring(prefix.length());
				path = target + path + "/";
				File tf = new File(path);
				if (!tf.exists()) {
					tf.mkdirs();
				}
				FileUtil.copy(f, path + f.getName());
			}
		} else {
			if (f.getName().equals(".svn")) {
				return;
			}
			if (f.getName().equals("classes")) {
				return;
			}
			if (f.getAbsolutePath().indexOf("WEB-INF/data") >= 0) {
				return;
			}
			if (f.getAbsolutePath().indexOf("WEB-INF/index") >= 0) {
				return;
			}
			if (f.getName().equals("logs")) {
				return;
			}
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				scanOneDir(fs[i]);
			}
		}
	}
}
