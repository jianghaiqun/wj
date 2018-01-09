package com.sinosoft.framework.extend.plugin;

import com.sinosoft.framework.extend.ExtendActionConfig;
import com.sinosoft.framework.extend.ExtendItemConfig;
import com.sinosoft.framework.extend.ExtendPointConfig;
import com.sinosoft.framework.extend.ExtendServiceConfig;
import com.sinosoft.framework.extend.menu.Menu;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.XMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PluginConfig {
	private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);

	private Mapx<String, ExtendPointConfig> extendPoints = new Mapx<String, ExtendPointConfig>();
	private Mapx<String, ExtendServiceConfig> extendServices = new Mapx<String, ExtendServiceConfig>();
	private Mapx<String, String> requiredExtendPoints = new Mapx<String, String>();
	private Mapx<String, String> requiredExtendServices = new Mapx<String, String>();
	private Mapx<String, String> requiredPlugins = new Mapx<String, String>();
	private Mapx<String, ExtendActionConfig> extendActions = new Mapx<String, ExtendActionConfig>();
	private Mapx<String, ExtendItemConfig> extendItems = new Mapx<String, ExtendItemConfig>();
	private Mapx<String, Menu> menus = new Mapx<String, Menu>();
	private String ID;
	private String Name;
	private String ClassName;
	private String author;
	private String provider;
	private String targetProject;
	private String version;
	private String description;
	private ArrayList<String> pluginFiles = new ArrayList<String>();
	private boolean enabling;
	private boolean running;

	public Mapx<String, ExtendPointConfig> getExtendPoints() {
		return this.extendPoints;
	}

	public Mapx<String, ExtendServiceConfig> getExtendServices() {
		return this.extendServices;
	}

	public Mapx<String, String> getRequiredExtendPoints() {
		return this.requiredExtendPoints;
	}

	public Mapx<String, String> getRequiredPlugins() {
		return this.requiredPlugins;
	}

	public Mapx<String, ExtendActionConfig> getExtendActions() {
		return this.extendActions;
	}

	public Mapx<String, ExtendItemConfig> getExtendItems() {
		return this.extendItems;
	}

	public Mapx<String, Menu> getMenus() {
		return this.menus;
	}

	public String getID() {
		return this.ID;
	}

	public String getClassName() {
		return this.ClassName;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getVersion() {
		return this.version;
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

	public String getName() {
		return this.Name;
	}

	public String getName(String language) {
		if (this.Name == null) {
			return null;
		}
		return this.Name;
	}

	public ArrayList<String> getPluginFiles() {
		return this.pluginFiles;
	}

	public boolean isEnabling() {
		return this.enabling;
	}

	public void setEnabling(boolean enabling) {
		this.enabling = enabling;
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getProvider() {
		return this.provider;
	}

	public String getTargetProject() {
		return this.targetProject;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void setClassName(String className) {
		this.ClassName = className;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 解析插件配置文件
	 * 
	 * @param xml
	 * @throws PluginException
	 */
	public void parse(String xml) throws PluginException {
		XMLLoader loader = new XMLLoader();
		try {
			loader.load(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

		this.enabling = true;

		this.ID = loader.getNodeBody("plugin.id");
		this.Name = loader.getNodeBody("plugin.name");
		this.ClassName = loader.getNodeBody("plugin.class");
		this.description = loader.getNodeBody("plugin.description");
		this.version = loader.getNodeBody("plugin.version");
		this.author = loader.getNodeBody("plugin.author");
		this.provider = loader.getNodeBody("plugin.provider");
		this.targetProject = loader.getNodeBody("plugin.targetProject");

		if (ObjectUtil.empty(this.ID)) {
			throw new PluginException("id is empty!");
		}
		if (ObjectUtil.empty(this.Name)) {
			throw new PluginException("name is empty!");
		}
		if (ObjectUtil.empty(this.version)) {
			throw new PluginException("version is empty!");
		}

		XMLLoader.NodeData[] nds = loader.getNodeDataList("plugin.files.*");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				if (nd.getTagName().equalsIgnoreCase("directory")) {
					this.pluginFiles.add("[D]" + nd.getBody());
				}
				if (nd.getTagName().equalsIgnoreCase("file")) {
					this.pluginFiles.add(nd.getBody());
				}
			}

		}

		nds = loader.getNodeDataList("plugin.required.plugin");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				this.requiredPlugins.put(nd.getBody(), (String) nd.getAttributes().get("version"));
			}

		}

		nds = loader.getNodeDataList("plugin.extendPoint");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				ExtendPointConfig ep = new ExtendPointConfig();
				ep.init(this, nd);
				this.extendPoints.put(ep.getID(), ep);
			}

		}

		nds = loader.getNodeDataList("plugin.extendService");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				ExtendServiceConfig ep = new ExtendServiceConfig();
				ep.init(this, nd);
				this.extendServices.put(ep.getID(), ep);
			}

		}

		nds = loader.getNodeDataList("plugin.extendItem");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				ExtendItemConfig ei = new ExtendItemConfig();
				ei.init(this, nd);
				this.extendItems.put(ei.getID(), ei);
				this.requiredExtendServices.put(ei.getExtendServiceID(), "Y");
			}

		}

		nds = loader.getNodeDataList("plugin.menu");
		if (ObjectUtil.notEmpty(nds)) {
			for (XMLLoader.NodeData nd : nds) {
				Menu menu = new Menu();
				menu.parse(this, nd);
				this.menus.put(menu.getID(), menu);
			}

		}

		nds = loader.getNodeDataList("plugin.extendAction");
		if (ObjectUtil.notEmpty(nds))
			for (XMLLoader.NodeData nd : nds) {
				ExtendActionConfig eac = new ExtendActionConfig();
				eac.init(this, nd);
				this.extendActions.put(eac.getID(), eac);
				this.requiredExtendPoints.put(eac.getExtendPointID(), "Y");
			}
	}
}