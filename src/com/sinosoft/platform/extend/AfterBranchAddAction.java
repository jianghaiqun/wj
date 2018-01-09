package com.sinosoft.platform.extend;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;
import com.sinosoft.schema.ZDBranchSchema;

public abstract class AfterBranchAddAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.Platform.AfterBranchAdd";

	public Object execute(Object[] args) throws ExtendException {
		ZDBranchSchema branch = (ZDBranchSchema) args[0];
		execute(branch);
		return null;
	}

	public abstract void execute(ZDBranchSchema paramZDBranchSchema) throws ExtendException;
}