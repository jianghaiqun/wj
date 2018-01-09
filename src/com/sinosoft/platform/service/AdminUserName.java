package com.sinosoft.platform.service;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.ObjectUtil;

public class AdminUserName extends FixedConfigItem {
	public static final String ID = "System.AdminUserName";

	public AdminUserName() {
		super("System.AdminUserName", "ShortText", "Text", "配置系统管理员的用户名");
	}

	public static String getValue() {
		String v = Config.getValue("System.AdminUserName");
		if (ObjectUtil.empty(v)) {
			v = "admin";
		}
		return v;
	}
}