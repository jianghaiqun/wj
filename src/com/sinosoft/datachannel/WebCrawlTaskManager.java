package com.sinosoft.datachannel;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.schema.ZCWebGatherSchema;
import com.sinosoft.search.crawl.CrawlConfig;
import com.sinosoft.search.crawl.Crawler;

public class WebCrawlTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "WebCrawl";

	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null) {
			dt = new QueryBuilder("select id,name from ZCWebGather where siteid=?", Application.getCurrentSiteID()).executeDataTable();
		} else {
			dt = new QueryBuilder("select id,name from ZCWebGather").executeDataTable();
		}
		return dt.toMapx(0, 1);
	}

	public void execute(long id) {
		runningMap.put(new Long(id), 1);
		final ZCWebGatherSchema wg = new ZCWebGatherSchema();
		wg.setID(id);
		if (wg.fill()) {
			if ("N".equals(wg.getStatus())) {
				return;// 停用的任务不自动执行
			}
			Thread thread = new Thread() {
				public void run() {
					try {
						CrawlConfig cc = new CrawlConfig();
						cc.parse(wg);
						Crawler crawler = new Crawler();
						crawler.setConfig(cc);
						crawler.crawl();
					} finally {
						runningMap.remove(new Long(wg.getID()));
					}
				}
			};
			thread.start();
		} else {
			logger.error("没有找到对应的抓取任务.任务ID:{}", id);
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "Web采集任务";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return isRunning(Long.parseLong(paramString));
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.WebCrawlTaskManager";
	}

}
