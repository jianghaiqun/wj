package com.sinosoft.platform.extend;

import com.sinosoft.framework.extend.actions.AfterPluginInitAction;
import com.sinosoft.platform.service.CodeService;

public class InitCodeAction extends AfterPluginInitAction {
	public void execute() {
		CodeService.init();
	}
}