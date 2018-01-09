package com.sinosoft.misc;

import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDUserSchema;

public class RestorePassword extends SystemTask {

	public void execute() {
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName("demo");
		user.fill();
		user.setPassword(StringUtil.md5Hex("demo"));
		user.setStatus("1");
		user.update();
	}

	public String getCronExpression() {
		return "* * * * *";
	}

	public String getID() {
		return "com.sinosoft.misc.RestorePassword";
	}

	public String getName() {
		return "定时恢复密码，演示站专用";
	}

	public static void main(String[] args) {
		RestorePassword rp = new RestorePassword();
		rp.execute();
	}
}
