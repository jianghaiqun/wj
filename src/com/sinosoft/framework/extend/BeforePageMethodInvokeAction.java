package com.sinosoft.framework.extend;

public abstract class BeforePageMethodInvokeAction implements IExtendAction {
	public static final String Type = "BeforePageMethodInvoke";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		execute((String) args[0]);
	}

	public abstract void execute(String method);
}
