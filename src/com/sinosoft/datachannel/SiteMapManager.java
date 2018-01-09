package com.sinosoft.datachannel;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class SiteMapManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.SiteMapManager";

	public boolean isRunning(long id) {
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "主站sitemap自动更新");
		map.put("0", "wap站sitemap自动更新");
		return map;
	}

	public void execute(long id) {
		if ("1".equals(id + "")) {
			SiteMapFile siteMap = new SiteMapFile();
			siteMap.proXML();
		} else if ("0".equals(id + "")) {
			SiteMapFile siteMap = new SiteMapFile();
			siteMap.proWapXML();
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "sitemap自动更新";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.SiteMapManager";
	}
}
