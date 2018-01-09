package com.sinosoft.framework.plugin;

public class DefaultPluginInstaller implements IPluginInstaller {
	private String pluginID;

	public DefaultPluginInstaller(String pluginID) {
		this.pluginID = pluginID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.plugin.IPluginInstaller#install()
	 */
	public int install() {
		IPlugin plugin = PluginManager.getPlugin(pluginID);
		PluginUtil.installMenu(plugin);
		PluginUtil.installTable(plugin);
		PluginUtil.installCronTask(plugin);
		PluginUtil.installCache(plugin);
		PluginUtil.installExtend(plugin);
		PluginUtil.installFile(plugin);
		return PluginManager.INSTALL_SUCCESS;
	}

}
