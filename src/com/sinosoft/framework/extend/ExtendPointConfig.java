package com.sinosoft.framework.extend;

import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;

public class ExtendPointConfig {
	private boolean enable;
	private PluginConfig pluginConfig;
	private String id;
	private String description;
	private String className;
	private boolean UIFlag;
	private Class<?> clazz;

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
			if (nd.getTagName().equalsIgnoreCase("UIFlag")) {
				this.UIFlag = "true".equals(nd.getBody().trim());
			}
		}
		if (ObjectUtil.empty(this.id))
			throw new PluginException("extendPoint's id is empty!");
	}

	public String getID() {
		return this.id;
	}

	public boolean getUIFlag() {
		return this.UIFlag;
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

	public void setUIFlag(boolean uIFlag) {
		this.UIFlag = uIFlag;
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

	public boolean isChild(Class<?> cls) {
		if (this.className == null) {
			return false;
		}
		if (this.clazz == null) {
			try {
				this.clazz = Class.forName(this.className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return cls.isAssignableFrom(this.clazz);
	}

	public Class<?> getParentClass() throws PluginException {
		if (this.className == null) {
			return null;
		}
		if (this.clazz == null) {
			try {
				this.clazz = Class.forName(this.className);
			} catch (ClassNotFoundException e) {
				throw new PluginException("ExtendPoint's class not found:" + this.className);
			}
		}
		return this.clazz;
	}
}