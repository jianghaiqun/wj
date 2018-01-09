package com.sinosoft.datachannel;

import com.sinosoft.email.AttachMail;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class MailParseManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.MailParseManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "自动解析邮件");
		return map;
	}

	public void execute(long id) {
		AttachMail am = new AttachMail();
		am.fetchMail();
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "自动解析邮件";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return CODE;
	}

}
