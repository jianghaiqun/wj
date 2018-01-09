package com.sinosoft.platform.pub;

import com.sinosoft.framework.schedule.AbstractTaskManager;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDScheduleSchema;
import com.sinosoft.schema.ZDScheduleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可通过界面配置的任务管理器，会将任务执行计划保存在数据库ZDSchedule表中
 * 
 */
public abstract class ConfigEanbleTaskManager extends AbstractTaskManager {
	protected static final Logger logger = LoggerFactory.getLogger(ConfigEanbleTaskManager.class);
	protected static ZDScheduleSet set;
	private static Object mutex = new Object();
	protected static long LastTime;

	public Mapx getUsableTasks() {
		synchronized (mutex) {
			if (set == null || System.currentTimeMillis() - LastTime > 60000) {
				set = new ZDScheduleSchema().query();
				LastTime = System.currentTimeMillis();
			}
		}
		Mapx map = new Mapx();
		for (int i = 0; i < set.size(); i++) {
			ZDScheduleSchema s = set.get(i);
			if (s.getTypeCode().equals(this.getCode()) && "Y".equals(s.getIsUsing())) {
				map.put(new Long(s.getSourceID()), "");
			}
		}
		return map;
	}

	public String getTaskCronExpression(String id) {
		synchronized (mutex) {
			if (set == null || System.currentTimeMillis() - LastTime > 60000) {
				set = new ZDScheduleSchema().query();
				LastTime = System.currentTimeMillis();
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZDScheduleSchema s = set.get(i);
			if (s.getTypeCode().equals(this.getCode()) && StringUtil.isNotEmpty(id) && id.equals(s.getSourceID() + "")) {
				return s.getCronExpression();
			}
		}
		return null;
	}
}
