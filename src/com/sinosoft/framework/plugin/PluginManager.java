package com.sinosoft.framework.plugin;

public class PluginManager {
	public static final int INSTALL_SUCCESS = 0;
	public static final int INSTALL_FAIL = 0;

	public static final int UNINSTALL_SUCCESS = 0;
	public static final int UNINSTALL_FAIL = 0;

	public static void onStartup() {

	}

	public static void install(String pluginFileName) {

	}

	public static IPlugin getPlugin(String pluginID) {
		return null;
	}

	/**
	 * 暂停使用，所有插件相关的菜单/扩展/定时任务/缓存都不再显示和运行
	 */
	public static void pause(String pluginID) {

	}

	public static void reuse(String pluginID) {

	}

	public static void uninstall(String pluginID) {

	}
}
