package com.sinosoft.platform.extend;

import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class AfterInstallAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.platform.AfterInstall";

	public Object execute(Object[] args) throws ExtendException {
		DBConn conn = (DBConn) args[0];
		execute(conn);
		return null;
	}

	public abstract void execute(DBConn paramDBConn) throws ExtendException;
}