package com.sinosoft.framework.extend.actions;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class AfterPluginInitAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.framework.AfterPluginInit";

	public Object execute(Object[] args) throws ExtendException {
		execute();
		return null;
	}

	public abstract void execute();
}