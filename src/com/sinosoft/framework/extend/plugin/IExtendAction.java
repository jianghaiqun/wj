package com.sinosoft.framework.extend.plugin;

import com.sinosoft.framework.extend.ExtendException;

public abstract interface IExtendAction {
	public abstract Object execute(Object[] paramArrayOfObject) throws ExtendException;
}