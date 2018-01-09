package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class FullTextTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "IndexMaintenance";

	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}

	public void execute(final long id) {
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

	public String getCode() {
		return "IndexMaintenance";
	}

	public String getName() {
		return "索引维护任务";
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
		return "IndexMaintenance";
	}

	 

}
