package com.sinosoft.framework.extend.plugin;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class PluginManager {
	private static final Logger logger = LoggerFactory.getLogger(PluginManager.class);

	private static ArrayList<PluginConfig> configList = new ArrayList<PluginConfig>();
	private static Mapx statusMap = new Mapx();
	private static Object mutex = new Object();

	public static void init() {
		synchronized (mutex) {
			if (configList.size() == 0)
				load();
		}
	}

	private static void load() {
		// File statusFile = new File(Config.getClassesPath() +
		// "/plugins/status.config");
		// if (statusFile.exists()) {
		// statusMap = LangLoader.readProperties(statusFile);
		// }
		// 加载主配置文件 -- 框架级插件
		InputStream is = ExtendManager.class.getClassLoader().getResourceAsStream("plugins/com.sinosoft.framework.plugin");
		PluginConfig pc = new PluginConfig();
		try {
			pc.parse(FileUtil.readText(is, "UTF-8"));
			configList.add(pc);
		} catch (PluginException e) {
			logger.error("Load plugin config file com.sinosoft.framework.plugin failed:" + e.getMessage(), e);
		}

		File parent = new File(Config.getClassesPath() + "plugins");
		if (parent.exists()) {
			for (File f : parent.listFiles()) {
				if (!f.getName().toLowerCase().endsWith(".plugin")) {
					continue;
				}
				pc = new PluginConfig();
				try {
					pc.parse(FileUtil.readText(f, "UTF-8"));
				} catch (PluginException e) {
					logger.error("Load plugin config file " + f.getName() + " failed:" + e.getMessage(), e);
				}
				if ("false".equals(statusMap.get(pc.getID()))) {
					pc.setEnabling(false);
				}
				boolean flag = true;
				for (PluginConfig c : configList) {
					if (c.getID().equals(pc.getID())) {
						if (!c.getID().equals("com.sinosoft.framework")) {
							logger.info("Plugin ID '{}' is duplication!" ,c.getID());
						}
						flag = false;
						break;
					}
				}
				if (flag) {
					configList.add(pc);
				}
			}

		}

		computeRela();
	}

	private static void computeRela() {
		for (PluginConfig pc : configList) {
			if (!pc.isEnabling()) {
				continue;
			}

			boolean requiredFlag = true;
			String need;
			for (Object pluginID : pc.getRequiredPlugins().keySet()) {
				PluginConfig c = getPluginConfig(pluginID + "");
				if ((c == null) || (!c.isEnabling())) {
					if (c == null) {
						logger.warn("Plugin {} needed by {} is not found!", pluginID, pc.getID());
					}
					requiredFlag = false;
					break;
				}

				String v = c.getVersion();
				need = (String) pc.getRequiredPlugins().get(pluginID);
				if (!isVersionCompatible(need, v)) {
					Object[] argArr = {pluginID, v, need, pc.getID()};
					logger.warn("Plugin {}'s version is {}, but {} is needed by {}!", argArr);
				}
			}
			if (!requiredFlag) {
				setDisable(pc);
			} else {
				requiredFlag = true;
				for (Object extendPointID : pc.getRequiredExtendPoints().keyArray()) {
					boolean flag = false;
					for (PluginConfig c : configList) {
						if (c.getExtendPoints().containsKey(extendPointID)) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						logger.warn("ExtendPoint {} needed by {} is not found!", extendPointID, pc.getID());
						requiredFlag = false;
						break;
					}
				}
				if (!requiredFlag)
					setDisable(pc);
			}
		}
	}

	private static boolean isVersionCompatible(String need, String version) {
		if (need.indexOf("-") > 0) {
			String[] arr = StringUtil.splitEx(need, "-");
			if (arr.length != 2) {
				return false;
			}
			String start = arr[0];
			String end = arr[1];
			if (start.endsWith(".x")) {
				start = start.substring(0, start.length() - 2);
			}
			if (end.endsWith(".x")) {
				end = end.substring(0, end.length() - 2);
			}
			double s = Double.parseDouble(start);
			double e = Double.parseDouble(end);
			double v = Double.parseDouble(version);
			return (s <= v) && (e >= v);
		}
		if (need.endsWith(".x")) {
			return version.startsWith(need.substring(0, need.length() - 1));
		}
		return version.equals(need);
	}

	private static void setDisable(PluginConfig pc) {
		if (!pc.isEnabling()) {
			return;
		}
		pc.setEnabling(true);
		for (PluginConfig c : configList) {
			for (Object pluginID : c.getRequiredPlugins().keyArray()) {
				if (pc.getID().equals(pluginID)) {
					setDisable(c);
					break;
				}
			}
			for (Object extendPointID : c.getRequiredExtendPoints().keyArray())
				if (pc.getExtendPoints().containsKey(extendPointID)) {
					setDisable(c);
					break;
				}
		}
	}

	public static boolean disable(String pluginID) throws PluginException {
		synchronized (mutex) {
			init();
		}
		return true;
	}

	public static boolean enable(String pluginID) throws PluginException {
		synchronized (mutex) {
			init();
		}
		return true;
	}

	public static PluginConfig getPluginConfig(String pluginID) {
		init();
		for (PluginConfig c : configList) {
			if (c.getID().equals(pluginID)) {
				return c;
			}
		}
		return null;
	}

	public static PluginConfig getPluginConfig(Class<? extends AbstractPlugin> clazz) {
		init();
		for (PluginConfig c : configList) {
			if (c.getClassName().equals(clazz.getName())) {
				return c;
			}
		}
		return null;
	}

	public static ArrayList<PluginConfig> getAllPluginConfig() {
		init();
		return configList;
	}
}