package com.sinosoft.framework.schedule;

import com.sinosoft.framework.utility.Mapx;

public class GeneralTaskManager extends AbstractTaskManager {
	Mapx taskMap = new Mapx();

	public synchronized void add(GeneralTask gt) {
		taskMap.put(new Long(gt.getID()), gt);
	}

	public synchronized void execute(String id) {
		GeneralTask gt = (GeneralTask) taskMap.get(new Long(id));
		if (gt != null) {
			if (!gt.isRunning()) {
				gt.execute();
			}
		} else {
			throw new RuntimeException("未找到ID对应的任务!");
		}
	}

	public boolean isRunning(String id) {
		GeneralTask gt = (GeneralTask) taskMap.get(new Long(id));
		if (gt != null) {
			return gt.isRunning();
		} else {
			throw new RuntimeException("未找到ID对应的任务!");
		}
	}

	public String getCode() {
		return "SYSTEM";
	}

	public String getName() {
		/* ${_ZVING_LICENSE_CODE_} */
		return "系统任务";
	}

	public Mapx getUsableTasks() {
		Mapx map = new Mapx();
		Object[] vs = taskMap.valueArray();
		for (int i = 0; i < taskMap.size(); i++) {
			GeneralTask gt = (GeneralTask) vs[i];
			map.put(new Long(gt.getID()), gt.getName());
		}
		return map;
	}

	public String getTaskCronExpression(String id) {
		GeneralTask gt = (GeneralTask) taskMap.get(new Long(id));
		return gt.getCronExpression();
	}

	public Mapx getConfigEnableTasks() {
		return null;
	}

	@Override
	public String getID() {
		return "com.sinosoft.framework.schedule.GeneralTaskManager";
	}
}
