package com.sinosoft.datachannel;

import java.util.List;
import java.util.Map;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.inter.TBSDAction;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class TBSDCancelContManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.TBSDCancelContManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "淘宝SD退保");
		return map;
	}

	public void execute(long id) {
		if ("1".equals(id + "")) {
			final TBSDAction tbsdAction = new TBSDAction();
			final List<Map<String, String>> params = tbsdAction.getToCanceledOrders();
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					tbsdAction.dealCancel(params);
				}
			});
			t.start();
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "淘宝SD退保定时任务";
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
		return "com.sinosoft.datachannel.TBSDCancelContManager";
	}
}
