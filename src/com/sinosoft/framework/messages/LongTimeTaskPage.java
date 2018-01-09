package com.sinosoft.framework.messages;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.utility.StringUtil;

public class LongTimeTaskPage extends Ajax {
	public void getInfo() {
		long id = 0;
		if (StringUtil.isNotEmpty($V("TaskID"))) {
			id = Long.parseLong($V("TaskID"));
		}
		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if (ltt != null && ltt.isAlive()) {
			$S("CurrentInfo", StringUtil.isNotEmpty(ltt.getCurrentInfo()) ? ltt.getCurrentInfo() + "..." : "");
			$S("Messages", ltt.getMessages());
			$S("Percent", "" + ltt.getPercent());
		} else {
			$S("CompleteFlag", "1");
			if (ltt != null) {
				String errors = ltt.getAllErrors();
				if (StringUtil.isNotEmpty(errors)) {
					$S("CurrentInfo", errors);
					$S("ErrorFlag", "1");
				} else {
					$S("CurrentInfo", "任务己完成!");
				}
			} else {
				$S("CurrentInfo", "任务己完成!");
			}
			LongTimeTask.removeInstanceById(id);
		}
	}

	public void stop() {
		long id = Long.parseLong($V("TaskID"));
		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if (ltt != null) {
			ltt.stopTask();
		}
	}

	public void stopComplete() {
		long id = Long.parseLong($V("TaskID"));
		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if (ltt == null || !ltt.isAlive()) {
			LongTimeTask.removeInstanceById(id);
		} else {
			Response.setStatus(0);
		}
	}
}
