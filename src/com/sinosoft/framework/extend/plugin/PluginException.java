package com.sinosoft.framework.extend.plugin;

public class PluginException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public PluginException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}