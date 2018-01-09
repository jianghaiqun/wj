package com.sinosoft.framework.extend;

import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendItemConfig {
	private static final Logger logger = LoggerFactory.getLogger(ExtendItemConfig.class);

	private boolean enable;
	private PluginConfig pluginConfig;
	private String id;
	private String description;
	private String extendServiceID;
	private String className;
	private IExtendItem instance = null;
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
			if (nd.getTagName().equalsIgnoreCase("extendService")) {
				this.extendServiceID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("class")) {
				this.className = nd.getBody().trim();
			}
		}
		if (ObjectUtil.empty(this.id))
			throw new PluginException("extendItem's id is empty!");
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

	public String getExtendServiceID() {
		return this.extendServiceID;
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

	public void setExtendServiceID(String extendServiceID) {
		this.extendServiceID = extendServiceID;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public IExtendItem getInstance() {
		try {
			if (this.instance == null) {
				synchronized (mutex) {
					if (this.instance == null) {
						Class clazz = Class.forName(this.className);
						try {
							this.instance = ((IExtendItem) clazz.newInstance());
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException("ExtendItem " + this.className + " must implements IExtendItem");
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