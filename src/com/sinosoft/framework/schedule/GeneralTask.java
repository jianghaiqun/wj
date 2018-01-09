package com.sinosoft.framework.schedule;

public abstract class GeneralTask extends AbstractTask {
	protected boolean isRunning = false;

	public String getType() {
		return "General";
	}

	public abstract void execute();

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
