package com.sinosoft.cms.seo;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class TimingSeo extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.cms.seo.TimingSeo";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "抓取数据-百度");
		map.put("2", "抓取数据-360");
		map.put("3", "抓取数据-全部");
		return map;
	}

	public void execute(long id) {
		try {
			if ("1".equals(id + "")) {
				InitSeo.executeTiming("1");
			}
			if ("2".equals(id + "")) {
				InitSeo.executeTiming("2");
			}
			if ("3".equals(id + "")) {
				InitSeo.executeTiming("1");
				InitSeo.executeTiming("2");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("type:" + id + "定时计划执行异常 message:" + e.getMessage(), e);
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "SEO获取数据";
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
		return "com.sinosoft.cms.seo.TimingSeo";
	}

}
