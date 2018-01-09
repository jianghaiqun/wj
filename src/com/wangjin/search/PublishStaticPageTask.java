package com.wangjin.search;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class PublishStaticPageTask extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.search.PublishStaticPageTask";
	
	public boolean isRunning(long id) {
		return false;
	}
	
	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "定时更新全部关键词静态页面");
		return map;
	}
	
	public void execute(long id) {
		if ("-1".equals(id + "")) {	
			UpdateStaticPage.doUpdate();
		}
		
	}
	
	public String getCode() {
		return CODE;
	}
	
	public String getName() {
		return "定时更新全部关键词静态页面";
	}
	
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	public String getID() {
		return "com.wangjin.search.PublishStaticPageTask";
	}
	
	

}
