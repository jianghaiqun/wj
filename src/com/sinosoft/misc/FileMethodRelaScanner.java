package com.sinosoft.misc;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileMethodRelaScanner {
	private static final Logger logger = LoggerFactory.getLogger(FileMethodRelaScanner.class);

	public static void main(String[] args) {
		String path = Config.getContextRealPath();
		File f = new File(path);
		Mapx map = new Mapx();
		dealFile(f, map);

		Document doc = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(Constant.GlobalCharset);
		Element root = doc.addElement("files");
		Object[] ks = map.keyArray();
		Object[] vs = map.valueArray();
		for (int i = 0; i < map.size(); i++) {
			String key = (String) ks[i];
			String[] arr = (String[]) vs[i];
			Element ele = root.addElement("file");
			ele.addAttribute("path", key.substring(key.indexOf("\\UI\\") + 4).replaceAll("\\\\", "/"));
			for (int j = 0; j < arr.length; j++) {
				Element m = ele.addElement("method");
				m.addAttribute("class", arr[j].substring(0, arr[j].lastIndexOf(".")));
				m.addAttribute("method", arr[j].substring(arr[j].lastIndexOf(".") + 1));
			}
		}
		StringWriter sw = new StringWriter();
		try {
			XMLWriter output = new XMLWriter(sw, format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		FileUtil.writeText("H:/method.xml", sw.toString());
	}

	public static void dealFile(File parent, Mapx map) {
		if (parent.isFile()) {
			return;
		}
		NameFileFilter nff =
				new NameFileFilter(new String[] { ".svn", "Editor", "UserFiles", "WEB-INF", "Preview", "Template", "Test",
						"Upload", "Template" });
		File[] fs = parent.listFiles((FileFilter) new NotFileFilter(nff));
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile()) {
				if (f.getName().toLowerCase().endsWith(".jsp")) {
					String[] arr = getMethods(FileUtil.readText(f));
					map.put(f.getPath(), arr);
				}
			} else {
				dealFile(f, map);
			}
		}
	}

	static Pattern p1 = Pattern.compile("Server\\.sendRequest\\(\\\"([\\w\\.]*?)\\\"", Pattern.DOTALL);

	static Pattern p2 = Pattern.compile("Server\\.getOneValue\\(\\\"([\\w\\.]*?)\\\"", Pattern.DOTALL);

	public static String[] getMethods(String str) {
		Matcher m = p1.matcher(str);
		int lastIndex = 0;
		ArrayList list = new ArrayList();
		while (m.find(lastIndex)) {
			list.add(m.group(1));
			lastIndex = m.end();
		}
		m = p2.matcher(str);
		lastIndex = 0;
		while (m.find(lastIndex)) {
			list.add(m.group(1));
			lastIndex = m.end();
		}
		String[] arr = new String[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).toString();
		}
		return arr;
	}
}
