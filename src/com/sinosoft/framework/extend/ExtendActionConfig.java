package com.sinosoft.framework.extend;

import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public class ExtendActionConfig {
	private boolean enable;
	private PluginConfig pluginConfig;
	private String id;
	private String description;
	private String extendPointID;
	private String className;
	private IExtendAction instance = null;
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
			if (nd.getTagName().equalsIgnoreCase("extendPoint")) {
				this.extendPointID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("class")) {
				this.className = nd.getBody().trim();
			}
		}
		if (ObjectUtil.empty(this.id))
			throw new PluginException("extendAction's id is empty!");
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public PluginConfig getPluginConfig() {
		return this.pluginConfig;
	}

	public String getID() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String getExtendPointID() {
		return this.extendPointID;
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

	public void setExtendPointID(String extendPointID) {
		this.extendPointID = extendPointID;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public IExtendAction getInstance() {
		try {
			if (this.instance == null) {
				synchronized (mutex) {
					if (this.instance == null) {
						Class clazz = Class.forName(this.className);
						ExtendPointConfig ep = ExtendManager.findExtendPoint(this.extendPointID);
						if (ep.isChild(clazz)) {
							throw new RuntimeException("ExtendAction " + this.className + " must extends " + ep.getClassName());
						}
						this.instance = ((IExtendAction) clazz.newInstance());
					}
				}
			}
			return this.instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}