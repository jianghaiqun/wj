package com.sinosoft.datachannel;

import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.schema.ZCDBGatherSchema;
import com.sinosoft.schema.ZCDBGatherSet;

public class DBSyncTask extends SystemTask {
	private boolean isRunning = false;
	private static Object mutex = new Object();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		if (!isRunning) {
			synchronized (mutex) {
				if (!isRunning) {
					new Thread() {
						public void run() {
							isRunning = true;
							UserData ud = new UserData();
							ud.setUserName("SYSTEM");
							ud.setLogin(true);
							ud.setManager(true);
							User.setCurrent(new UserData());
							long t = System.currentTimeMillis();
							try {
								ZCDBGatherSet gatherSet = new ZCDBGatherSchema().query();
								for (int i = 0; i < gatherSet.size(); i++) {
									if ("N".equals(gatherSet.get(i).getStatus())) {
										continue;
									}
									FromDB.executeGather(gatherSet.get(i), false, null);
								}
							} finally {
								isRunning = false;
							}
							logger.info("数据库采集任务耗时：{}毫秒", (System.currentTimeMillis() - t));
						}
					}.start();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getID()
	 */
	public String getID() {
		return "com.sinosoft.datachannel.DBSyncTask";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "数据库采集任务";
	}

}
