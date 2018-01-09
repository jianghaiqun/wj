package com.sinosoft.framework;

import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.XMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 统一配置文件解析器
 */
public class ConfigLoader {
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	private static boolean Loaded = false;

	private static Object mutex = new Object();

	private static XMLLoader loader = new XMLLoader();

	public static void load() {
		try {
			String path = Config.getClassesPath();
			load(path);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void load(String path) {
		if (!Loaded)
			synchronized (mutex) {
				if (!Loaded) {
					loader.clear();
					File f = new File(path);
					if (!f.exists()) {
						return;
					}
					String file = path + "charset.config";
					if (new File(file).exists()) {
						String txt = FileUtil.readText(file, "UTF-8");
						Mapx map = StringUtil.splitToMapx(txt, "\n", "=");
					}
					logger.info("获取文件字符集完成!");

					loader.load(Config.getClassesPath());
					logger.info("sax加载xml完成!");

					XMLLoader.NodeData data = loader.getNodeData("framework.application.config", "name", "ComplexDeployMode");
					Config.ComplexDepolyMode = (data != null) && ("true".equals(data.getBody()));
				}
				Loaded = true;
			}
	}

	public static void reload() {
		Loaded = false;
		load();
	}

	public static XMLLoader.NodeData getNodeData(String path, String attrName, String attrValue) {
		return loader.getNodeData(path, attrName, attrValue);
	}

	public static XMLLoader.NodeData[] getNodeDataList(String path) {
		return loader.getNodeDataList(path);
	}
}