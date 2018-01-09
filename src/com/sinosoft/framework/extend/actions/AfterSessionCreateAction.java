package com.sinosoft.framework.extend.actions;

import javax.servlet.http.HttpSession;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class AfterSessionCreateAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.framework.AfterSessionCreate";

	public Object execute(Object[] args) throws ExtendException {
		HttpSession session = (HttpSession) args[0];
		execute(session);
		return null;
	}

	public abstract void execute(HttpSession paramHttpSession) throws ExtendException;
}