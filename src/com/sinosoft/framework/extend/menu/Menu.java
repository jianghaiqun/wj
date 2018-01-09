package com.sinosoft.framework.extend.menu;

import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;

public class Menu {
	public static final String Type_Backend = "Backend";
	public static final String Type_Frontend = "Frontend";
	private String ID;
	private String parentID;
	private String description;
	private String name;
	private String icon;
	private String order;
	private String URL;
	private String type;
	private PluginConfig config;

	public void parse(PluginConfig pc, XMLLoader.NodeData parent) throws PluginException {
		this.config = pc;
		for (XMLLoader.NodeData nd : parent.getChildrenDataList()) {
			if (nd.getTagName().equalsIgnoreCase("id")) {
				this.ID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("parentId")) {
				this.parentID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("description")) {
				this.description = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("name")) {
				this.name = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("icon")) {
				this.icon = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("URL")) {
				this.URL = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("order")) {
				this.order = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("type")) {
				this.type = nd.getBody().trim();
			}
		}
		if (ObjectUtil.empty(this.ID)) {
			throw new PluginException("menu's id is empty!");
		}
		if (ObjectUtil.empty(this.name))
			throw new PluginException("menu's name is empty!");
	}

	public PluginConfig getPluginConfig() {
		return this.config;
	}

	public String getID() {
		return this.ID;
	}

	public String getParentID() {
		return this.parentID;
	}

	public String getName() {
		return this.name;
	}

	public String getName(String language) {
		if (this.name == null) {
			return null;
		}
		return this.name;
	}

	public String getURL() {
		return this.URL;
	}

	public String getType() {
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}

	public String getDescription(String language) {
		if (this.description == null) {
			return null;
		}
		return this.description;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getOrder() {
		return this.order;
	}

	public void setPluginConfig(PluginConfig pc) {
		this.config = pc;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public void setParentID(String parentId) {
		this.parentID = parentId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setURL(String uRL) {
		this.URL = uRL;
	}

	public void setType(String type) {
		this.type = type;
	}
}