package com.sinosoft.platform.extend;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class AfterLoginAction implements IExtendAction {
	public static final String ID = "com.sinosoft.platform.AfterLogin";

	public Object execute(Object[] args) throws ExtendException {
		return execute();
	}

	public abstract Object execute();
}