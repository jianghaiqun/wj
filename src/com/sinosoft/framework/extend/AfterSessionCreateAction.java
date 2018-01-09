package com.sinosoft.framework.extend;

import javax.servlet.http.HttpSession;

public abstract class AfterSessionCreateAction implements IExtendAction {
	public static final String Type = "AfterSessionCreate";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		HttpSession session = (HttpSession) args[0];
		execute(session);
	}

	public abstract void execute(HttpSession session);
}
