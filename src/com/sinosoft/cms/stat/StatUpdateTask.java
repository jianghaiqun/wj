package com.sinosoft.cms.stat;

import java.util.Date;

import com.sinosoft.cms.stat.impl.GlobalStat;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.DateUtil;

public class StatUpdateTask extends SystemTask {
	private long LastUpdateTime = System.currentTimeMillis();

	public void execute() {
		long current = System.currentTimeMillis();
		VisitHandler.init(current);
		GlobalStat.dealVisitHistory(current);
		if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(LastUpdateTime)))) {
			VisitHandler.changePeriod(AbstractStat.PERIOD_DAY, current);// 新的一天
		} else {
			VisitHandler.update(System.currentTimeMillis(), false, false);
		}
		LastUpdateTime = current;
	}

	public String getID() {
		return "com.sinosoft.cms.stat.StatUpdateTask";
	}

	public String getName() {
		return "定时更新CMS统计信息";
	}

	public String getCronExpression() {
		return "*/3 * * * *";
	}

}
