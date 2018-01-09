package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class FullTextTaskCreate extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.cms.dataservice.FullTextTaskCreate";
	
	Mapx runningMap = new Mapx();
	
	public String getID() {
		return "com.sinosoft.cms.dataservice.FullTextTaskCreate";
	}
	public String getName() {
		return  "索引创建任务";
	}
	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null) {
			dt = new QueryBuilder("select id,name from ZCFullText where siteid=?", Application.getCurrentSiteID())
					.executeDataTable();
		} else {
			dt = new QueryBuilder("select id,name from ZCFullText").executeDataTable();
		}
		return dt.toMapx(0, 1);
	}
	public void execute(String paramString) {

		execute(Long.parseLong(paramString));
	}
	public boolean isRunning(String paramString) {
		return isRunning(Long.parseLong(paramString));
	}
	public String getCode() {
		return CODE;
	}

	public void execute(final long id) {
		String file = Config.getContextRealPath() + "WEB-INF/data/index/" + 1025 + "/time.lock";
		FileUtil.delete(file);
		runningMap.put(new Long(id), 1);
		new Thread() {
			public void run() {
				try {
					SearchAPI.update(id);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					runningMap.remove(new Long(id));
				}
			}
		}.start();
	}
	
	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}
}
