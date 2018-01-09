package com.sinosoft.framework.plugin;

public class DefaultPluginUninstaller implements IPluginUninstaller {
	private String pluginID;

	public DefaultPluginUninstaller(String pluginID) {
		this.pluginID = pluginID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.plugin.IPluginUninstaller#uninstall()
	 */
	public int uninstall() {
		return 0;
	}

}
