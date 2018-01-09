package com.sinosoft.framework.plugin;

public abstract class AbstractPlugin implements IPlugin {
	public String[] getExtendClasses() {
		return null;
	}

	public String[] getCronTaskClases() {
		return null;
	}

	public String[] getCacheClasses() {
		return null;
	}

	public IPluginInstaller getInstaller() {
		return new DefaultPluginInstaller(this.getPluginID());
	}

	public IPluginUninstaller getUninstaller() {
		return new DefaultPluginUninstaller(this.getPluginID());
	}

	public void onPause() {

	}

	public void onReuse() {

	}
}
