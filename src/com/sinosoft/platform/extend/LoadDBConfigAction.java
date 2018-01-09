package com.sinosoft.platform.extend;

import com.sinosoft.framework.extend.actions.AfterConfigInitAction;
import com.sinosoft.platform.pub.PlatformUtil;

public class LoadDBConfigAction extends AfterConfigInitAction {
	public Object execute(Object[] args) {
		PlatformUtil.loadDBConfig();
		return null;
	}
}