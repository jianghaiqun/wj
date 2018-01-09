package com.sinosoft.framework.menu;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.extend.menu.Menu;
import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.extend.plugin.PluginManager;
import com.sinosoft.framework.utility.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class MenuManager {
	private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);

	private static ArrayList<Menu> menus;
	private static long lastTime = 0L;
	private static Object mutex = new Object();

	public static ArrayList<Menu> getMenus() {
		synchronized (mutex) {
			File p;
			if ((Config.isDebugMode()) && (System.currentTimeMillis() - lastTime > 5000L)) {
				String dir = Config.getContextRealPath() + "WEB-INF/classes/plugins/";
				p = new File(dir);
				if (p.exists()) {
					menus = new ArrayList();
					for (File f : p.listFiles()) {
						PluginConfig pluginConfig = new PluginConfig();
						String xml = FileUtil.readText(f, "UTF-8");
						try {
							pluginConfig.parse(xml);

							PluginConfig pc = PluginManager.getPluginConfig(pluginConfig.getID());
							if ((pc == null) || (pc.isEnabling()))
								for (Menu m : pluginConfig.getMenus().values())
									menus.add(m);
						} catch (PluginException e) {
							logger.error(e.getMessage(), e);

						}
					}
				}
				lastTime = System.currentTimeMillis();
			}
			if (menus == null) {
				menus = new ArrayList();
				for (PluginConfig pc : PluginManager.getAllPluginConfig()) {
					if (!pc.isEnabling()) {
						continue;
					}
					for (Menu m : pc.getMenus().values()) {
						menus.add(m);
					}
				}
			}
			return menus;
		}
	}

	public static Menu getMenu(String id) {
		for (Menu m : getMenus()) {
			if (m.getID().equals(id)) {
				return m;
			}
		}
		return null;
	}
}