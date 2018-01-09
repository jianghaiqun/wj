package com.sinosoft.framework.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SystemTask extends AbstractTask {
	protected static final Logger logger = LoggerFactory.getLogger(SystemTask.class);
	protected boolean isRunning = false;
	protected String cronExpression;

	public String getType() {
		return "General";
	}

	public abstract void execute();

	public boolean isRunning() {
		return this.isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}
}