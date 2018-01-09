package com.sinosoft.framework.schedule;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTaskManager extends AbstractTaskManager {
	private static final Logger logger = LoggerFactory.getLogger(SystemTaskManager.class);

	public static final String ID = "SYSTEM";
	Mapx taskMap = new Mapx();

	public synchronized void add(SystemTask gt) {
		if (StringUtil.isEmpty(gt.getCronExpression())) {
			logger.error("{}'s cron expression not exist", gt.getClass().getName());
			return;
		}
		String id = new String(gt.getID());
		
		if (taskMap.containsKey(id)) {
			throw new RuntimeException(gt.getClass().getName() + "'s ID is conflict with " + ((SystemTask) this.taskMap.get(id)).getClass().getName());
		}
		this.taskMap.put(new String(gt.getID()), gt);
	}

	public synchronized void execute(final String id) {
		new Thread() {
			public void run() {
				SystemTask gt = (SystemTask) SystemTaskManager.this.taskMap.get(id);
				if (gt != null) {
					if ((!gt.isRunning()) && (!gt.isRunning())) {
						gt.setRunning(true);
						gt.execute();
						gt.setRunning(false);
					}
				} else
					throw new RuntimeException("Task not found:" + id);
			}
		}.start();
	}

	public boolean isRunning(String id) {
		SystemTask gt = (SystemTask) this.taskMap.get(id);
		if (gt != null) {
			return gt.isRunning();
		}
		throw new RuntimeException("Task not found:" + id);
	}

	public String getID() {
		return "com.sinosoft.framework.schedule.SystemTaskManager";
	}

	public String getName() {
		return "System Task";
	}

	public SystemTask getTask(String id) {
		return (SystemTask) this.taskMap.get(id);
	}

	public SystemTask removeTask(String id) {
		return (SystemTask) this.taskMap.remove(id);
	}

	public Object[] getAllTask() {
		return this.taskMap.valueArray();
	}

	public Mapx getUsableTasks() {
		Mapx map = new Mapx();
		for (Object gt1 : this.taskMap.values()) {
			SystemTask gt = (SystemTask) gt1;
			map.put(gt.getID(), gt.getName());
		}
		return map;
	}

	public String getTaskCronExpression(String id) {
		SystemTask gt = (SystemTask) this.taskMap.get(id);
		return gt.getCronExpression();
	}

	public Mapx getConfigEnableTasks() {
		return null;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}
}