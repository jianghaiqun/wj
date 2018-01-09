package com.sinosoft.framework.extend.actions;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class BeforeUIMethodInvokeAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.framework.BeforeUIMethodInvoke";

	public Object execute(Object[] args) throws ExtendException {
		execute((String) args[0]);
		return null;
	}

	public abstract void execute(String paramString) throws ExtendException;
}