package com.sinosoft.datachannel;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class LotteryManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.LotteryManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "十一抽奖活动");
		return map;
	}

	public void execute(long id) {
		if ("-1".equals(id + "")) {
			ActivityService activityService=new ActivityService();
			activityService.execute();

		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "抽奖活动定时任务";
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
		return "com.sinosoft.datachannel.LotteryManager";
	}

}
