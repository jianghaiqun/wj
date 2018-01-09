package com.sinosoft.framework.schedule;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.extend.IExtendItem;
import com.sinosoft.framework.extend.IExtendService;

public class SystemTaskService implements IExtendService<SystemTask> {
	public static final String ID = "com.sinosoft.framework.schedule.SystemTaskService";

	public static SystemTaskService getInstance() {
		return (SystemTaskService) ExtendManager.findExtendServiceByClass(SystemTaskService.class.getName()).getInstance();
	}

	public void register(IExtendItem item) {
		CronManager.getInstance().registerSystemTask((SystemTask) item);
	}

	private SystemTaskManager getSystemTaskManager() {
		return (SystemTaskManager) CronManager.getInstance().getManagers().get("SYSTEM");
	}

	public SystemTask get(String id) {
		return getSystemTaskManager().getTask(id);
	}

	public SystemTask remove(String id) {
		return getSystemTaskManager().removeTask(id);
	}

	public List<SystemTask> getAll() {
		Object[] obj = getSystemTaskManager().getAllTask();
		List<SystemTask> list = new ArrayList<SystemTask>();
		for (Object o : obj) {
			SystemTask atm = (SystemTask) o;
			list.add(atm);
		}
		return list;
	}
}