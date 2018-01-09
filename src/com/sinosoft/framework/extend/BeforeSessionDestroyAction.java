package com.sinosoft.framework.extend;

public abstract class BeforeSessionDestroyAction implements IExtendAction {
	public static final String Type = "BeforeSessionDestroy";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
	}
}
