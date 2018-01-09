package com.sinosoft.framework.extend;

import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;

public class ExtendServiceConfig {
	private boolean enable;
	private PluginConfig pluginConfig;
	private String id;
	private String description;
	private String className;
	private String itemClassName;
	private IExtendService<?> instance = null;
	private static Object mutex = new Object();

	public void init(PluginConfig pc, XMLLoader.NodeData parent) throws PluginException {
		this.pluginConfig = pc;
		for (XMLLoader.NodeData nd : parent.getChildrenDataList()) {
			if (nd.getTagName().equalsIgnoreCase("id")) {
				this.id = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("description")) {
				this.description = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("class")) {
				this.className = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("itemClass")) {
				this.itemClassName = nd.getBody().trim();
			}
		}
		if (ObjectUtil.empty(this.id))
			throw new PluginException("extendPoint's id is empty!");
	}

	public String getID() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String getClassName() {
		return this.className;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public PluginConfig getPluginConfig() {
		return this.pluginConfig;
	}

	public String getItemClassName() {
		return this.itemClassName;
	}

	public void setItemClassName(String itemClassName) {
		this.itemClassName = itemClassName;
	}

	public IExtendService<?> getInstance() {
		try {
			if (this.instance == null) {
				synchronized (mutex) {
					if (this.instance == null) {
						Class clazz = Class.forName(this.className);
						try {
							this.instance = ((IExtendService) clazz.newInstance());
						} catch (Exception e) {
							throw new RuntimeException("ExtendService " + this.className + " must extends IExtendService");
						}
					}
				}
			}
			return this.instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}