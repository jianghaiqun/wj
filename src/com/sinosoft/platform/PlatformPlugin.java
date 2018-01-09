package com.sinosoft.platform;

import com.sinosoft.framework.extend.plugin.FrameworkPlugin;
import com.sinosoft.platform.pub.PlatformUtil;

public class PlatformPlugin extends FrameworkPlugin {
	public void start() {
		PlatformUtil.loadDBConfig();
	}
}