package com.sinosoft.platform.service;

import com.sinosoft.framework.extend.AbstractExtendService;

public class ConfigService extends AbstractExtendService<FixedConfigItem> {
	public static ConfigService getInstance() {
		return (ConfigService) findInstance(ConfigService.class);
	}
}