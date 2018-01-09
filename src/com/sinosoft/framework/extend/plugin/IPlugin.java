package com.sinosoft.framework.extend.plugin;

public abstract interface IPlugin {
	public abstract void install() throws PluginException;

	public abstract void start() throws PluginException;

	public abstract void stop() throws PluginException;

	public abstract void uninstall() throws PluginException;

	public abstract PluginConfig getConfig();
}