package com.sinosoft.framework.schedule;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.framework.extend.AbstractExtendService;
import com.sinosoft.framework.extend.IExtendItem;

public class CronTaskManagerService extends AbstractExtendService<AbstractTaskManager> {
	public static CronTaskManagerService getInstance() {
		return (CronTaskManagerService) findInstance(CronTaskManagerService.class);
	}

	public void register(IExtendItem item) {
		CronManager.getInstance().registerCronTaskManager((AbstractTaskManager) item);
	}

	public AbstractTaskManager get(String id) {
		return CronManager.getInstance().getCronTaskManager(id);
	}

	public AbstractTaskManager remove(String id) {
		return CronManager.getInstance().removeCronTaskManager(id);
	}

	public List<AbstractTaskManager> getAll() {
		Object[] obj = CronManager.getInstance().getManagers().valueArray();
		List<AbstractTaskManager> list = new ArrayList<AbstractTaskManager>();
		for (Object o : obj) {
			AbstractTaskManager atm = (AbstractTaskManager) o;
			list.add(atm);
		}
		return list;
	}
}