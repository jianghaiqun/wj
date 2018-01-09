package com.sinosoft.platform.extend;

import com.sinosoft.framework.User;
import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.actions.PrivExtendAction;
import com.sinosoft.platform.service.AdminUserName;

public class MenuPrivCheck extends PrivExtendAction {
	public int getPrivFlag(String priv) throws ExtendException {
		if (AdminUserName.getValue().equals(User.getUserName())) {
			return 1;
		}
		return 0;
	}
}