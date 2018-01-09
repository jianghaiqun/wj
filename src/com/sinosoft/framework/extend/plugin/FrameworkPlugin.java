package com.sinosoft.framework.extend.plugin;

public class FrameworkPlugin extends AbstractPlugin {
	public static final String ID = "com.sinosoft.framework";

	public void install() throws PluginException {
		throw new PluginException("本插件不需要手工安装。");
	}

	public void start() throws PluginException {
	}

	public void stop() throws PluginException {
		throw new PluginException("本插件不能停止运行。");
	}

	public void uninstall() throws PluginException {
		throw new PluginException("本插件不能卸载。");
	}
}