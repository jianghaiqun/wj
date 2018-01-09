package com.sinosoft.datachannel;

import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.schema.ZCInnerDeploySchema;
import com.sinosoft.schema.ZCInnerDeploySet;
import com.sinosoft.schema.ZCInnerGatherSchema;
import com.sinosoft.schema.ZCInnerGatherSet;

public class InnerSyncTask extends SystemTask {
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
								ZCInnerGatherSet gatherSet = new ZCInnerGatherSchema().query();
								for (int i = 0; i < gatherSet.size(); i++) {
									if ("N".equals(gatherSet.get(i).getStatus())) {
										continue;
									}
									InnerSyncService.executeGather(gatherSet.get(i), null);
								}
								ZCInnerDeploySet deploySet = new ZCInnerDeploySchema().query();
								for (int i = 0; i < deploySet.size(); i++) {
									if ("N".equals(deploySet.get(i).getStatus())) {
										continue;
									}
									InnerSyncService.executeDeploy(deploySet.get(i), null);
								}
							} finally {
								isRunning = false;
							}
							logger.info("网站群任务耗时：{}毫秒", (System.currentTimeMillis() - t));
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
		return "com.sinosoft.datachannel.InnerSyncTask";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "网站群任务";
	}

}
