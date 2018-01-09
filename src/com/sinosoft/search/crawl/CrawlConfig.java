package com.sinosoft.search.crawl;

import com.sinosoft.framework.script.ScriptEngine;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.RegexParser;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCWebGatherSchema;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.List;

public class CrawlConfig {
	private static final Logger logger = LoggerFactory.getLogger(CrawlConfig.class);

	public static final int TYPE_DOCUMENT = 1;

	public static final int TYPE_CUSTOMTABLE = 1;

	private int type;

	private int language;

	private String name;

	private long ID;

	private boolean proxyFlag;

	private String proxyHost;

	private int proxyPort;

	private String proxyUserName;

	private String proxyPassword;

	private boolean copyImageFlag;

	private boolean cleanLinkFlag;

	private long catalogID;

	private String script;

	private String[] urlLevels;

	private RegexParser[] filterBlocks;

	private Mapx templateMap;

	private int maxPageCount = Integer.MAX_VALUE;

	private int maxListCount = Integer.MAX_VALUE;

	private int threadCount;

	private int maxLevel = Integer.MAX_VALUE;// 最大层级,下标从0开始

	private int timeout;

	private int retryTimes;

	private boolean filterFlag;

	private String filterExpr;

	private String publishDateFormat;

	public static String getWebGatherDir() {
		return "WEB-INF/data";
	}

	public void parse(ZCWebGatherSchema wg) {
		this.type = "D".equals(wg.getType()) ? TYPE_DOCUMENT : TYPE_CUSTOMTABLE;
		this.name = wg.getName();
		this.ID = wg.getID();
		this.proxyFlag = "1".equals(wg.getProxyFlag()) ? true : false;
		this.proxyHost = wg.getProxyHost();
		this.proxyPassword = wg.getProxyPassword();
		this.proxyPort = (int) wg.getProxyPort();
		this.proxyUserName = wg.getProxyUserName();
		parseXML(wg.getConfigXML());
	}

	public void parseXML(String xml) {
		SAXReader reader = new SAXReader(false);
		Document doc;
		try {
			doc = reader.read(new StringReader(xml));
			Element root = doc.getRootElement();
			List configs = root.elements("config");
			for (int i = 0; i < configs.size(); i++) {
				Element conf = (Element) configs.get(i);
				String key = conf.attributeValue("key");
				String value = conf.attributeValue("value");
				if (key.equals("CopyImage") && "1".equals(value)) {
					this.copyImageFlag = true;
				}
				if (key.equals("CleanLink") && "1".equals(value)) {
					this.cleanLinkFlag = true;
				}
				if (key.equals("PublishDateFormat")) {
					this.publishDateFormat = value;
				}
				if (key.equals("FilterFlag") && "1".equals(value)) {
					this.filterFlag = true;
				}
				if (key.equals("CatalogID") && StringUtil.isNotEmpty(value)) {
					this.catalogID = Long.parseLong(value);
				}
				if (key.equals("MaxPageCount") && StringUtil.isNotEmpty(value)) {
					this.maxPageCount = Integer.parseInt(value);
				}
				if (key.equals("MaxListCount") && StringUtil.isNotEmpty(value)) {
					this.maxListCount = Integer.parseInt(value);
				}
				if (key.equals("ThreadCount") && StringUtil.isNotEmpty(value)) {
					this.threadCount = Integer.parseInt(value);
				}
				if (key.equals("TimeOut") && StringUtil.isNotEmpty(value)) {
					this.timeout = Integer.parseInt(value);
				}
				if (key.equals("RetryTimes") && StringUtil.isNotEmpty(value)) {
					this.retryTimes = Integer.parseInt(value);
				}
			}
			Element escript = root.element("script");
			this.language = "java".equals(escript.attribute("language")) ? ScriptEngine.LANG_JAVA
					: ScriptEngine.LANG_JAVASCRIPT;
			this.script = escript.getText();

			Element eFilterExpr = root.element("filterExpr");
			this.filterExpr = eFilterExpr.getText();

			List urls = root.elements("urls");
			this.urlLevels = new String[urls.size()];
			for (int i = 0; i < urls.size(); i++) {
				Element url = (Element) urls.get(i);
				String level = url.attributeValue("level");
				String content = url.getText();
				this.urlLevels[Integer.parseInt(level) - 1] = content;
			}

			List blocks = root.elements("filterBlock");
			if (blocks != null) {
				filterBlocks = new RegexParser[blocks.size()];
				for (int i = 0; i < blocks.size(); i++) {
					Element block = (Element) blocks.get(i);
					String content = block.getText();
					filterBlocks[i] = new RegexParser(content);
				}
			}

			List templates = root.elements("template");
			templateMap = new Mapx();
			for (int i = 0; i < templates.size(); i++) {
				Element template = (Element) templates.get(i);
				String code = template.attributeValue("code");
				String content = template.getText();
				templateMap.put(code, new RegexParser(content));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public RegexParser getTemplate(String code) {
		return (RegexParser) templateMap.get(code);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCopyImageFlag() {
		return copyImageFlag;
	}

	public int getLanguage() {
		return language;
	}

	public String getName() {
		return name;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public String getScript() {
		return script;
	}

	public String[] getUrlLevels() {
		return urlLevels;
	}

	public boolean isProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(boolean proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public long getCatalogID() {
		return catalogID;
	}

	public boolean isCleanLinkFlag() {
		return cleanLinkFlag;
	}

	public int getMaxPageCount() {
		return maxPageCount;
	}

	public void setMaxPageCount(int count) {
		maxPageCount = count;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public int getTimeout() {
		return timeout;
	}

	public long getID() {
		return ID;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public String getFilterExpr() {
		return filterExpr;
	}

	public void setFilterExpr(String filterExpr) {
		this.filterExpr = filterExpr;
	}

	public boolean isFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setUrlLevels(String[] urlLevels) {
		this.urlLevels = urlLevels;
	}

	public RegexParser[] getFilterBlocks() {
		return filterBlocks;
	}

	public String getPublishDateFormat() {
		return publishDateFormat;
	}

	public int getMaxListCount() {
		return maxListCount;
	}
}
