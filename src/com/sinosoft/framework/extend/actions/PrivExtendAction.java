package com.sinosoft.framework.extend.actions;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class PrivExtendAction implements IExtendAction {
	public static String ExtendPointID = "com.sinosoft.framework.PrivCheck";

	public Object execute(Object[] args) throws ExtendException {
		return Integer.valueOf(getPrivFlag((String) args[0]));
	}

	public abstract int getPrivFlag(String paramString) throws ExtendException;
}