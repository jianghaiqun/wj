package com.sinosoft.framework.schedule;

import com.sinosoft.framework.extend.IExtendItem;
import com.sinosoft.framework.utility.Mapx;

public abstract class AbstractTaskManager implements IExtendItem {
	public abstract Mapx getUsableTasks();

	public abstract Mapx getConfigEnableTasks();

	public abstract String getTaskCronExpression(String paramString);

	public abstract void execute(String paramString);

	public abstract boolean isRunning(String paramString);

	public abstract String getCode();

}