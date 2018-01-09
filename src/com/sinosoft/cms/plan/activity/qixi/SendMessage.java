package com.sinosoft.cms.plan.activity.qixi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sinosoft.inter.ActionUtil;

/**
*
 * Created by dongsheng on 2017/7/12.
 */
public class SendMessage implements SendMessageHandler {

	private Map<String, String> data;

	public SendMessage(String mobile, String msgData) {

		data = new HashMap<String, String>();
		data.put(mobile, msgData);
	}

	public SendMessage() {

		data = new HashMap<String, String>();
	}

	public void addMsg(String mobile, String msgData) {

		if (StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(msgData)) {
			data.put(mobile, msgData);
		}
	}

	@Override
	public boolean sendMessage() {

		for (Map.Entry<String, String> entry : data.entrySet()) {
			boolean b = ActionUtil.sendGeneralSms(entry.getKey(), entry.getValue());
			if (!b)
				return false;
		}
		return true;
	}
}
