package com.sinosoft.platform.extend;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class AfterBranchDeleteAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.Platform.AfterBranchDelete";

	public Object execute(Object[] args) throws ExtendException {
		String[] ids = (String[]) args[0];
		execute(ids);
		return null;
	}

	public abstract void execute(String[] paramArrayOfString);
}