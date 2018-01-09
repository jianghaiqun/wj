package com.sinosoft.framework.schedule;

import com.sinosoft.framework.extend.IExtendItem;

public abstract class AbstractTask implements IExtendItem {
	public abstract String getType();

	public abstract String getCronExpression();
	
	
}