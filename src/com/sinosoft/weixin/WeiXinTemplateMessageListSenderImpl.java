package com.sinosoft.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.framework.utility.StringUtil;

public class WeiXinTemplateMessageListSenderImpl implements WeiXinMessageListSender {

	@Override
	public boolean send(List<WeiXinMessage> list) {

		if (list == null || list.size() == 0) {
			return false;
		}
		String token = WeiXinCommon.ajaxtoken();
		if (StringUtil.isEmpty(token)) {
			return false;
		}

		for (WeiXinMessage weiXinMessage : list) {
			if (!(weiXinMessage instanceof WeiXinTemplateMessage)) {
				break;
			}
			WeiXinTemplateMessage msg = ((WeiXinTemplateMessage) weiXinMessage);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("touser", msg.getToOpenid());
			param.put("template_id", msg.getTemplateId());
			param.put("url", msg.getUrl());
			// 微信推送 拼装参数
			Map<String, Object> dataParam = new HashMap<String, Object>();
			dataParam.put("first", msg.getFirst());
			dataParam.put("keyword1", msg.getWord1());
			dataParam.put("keyword2", msg.getWord2());
			dataParam.put("keyword3", msg.getWord3());
			dataParam.put("keyword4", msg.getWord4());
			dataParam.put("remark", msg.getRemark());
			param.put("data", dataParam);
			WeiXinCommon.ajaxSendInfoToUser(token, msg.getToOpenid(), param);
		}
		return true;

	}
}
