package com.sinosoft.cms.dataservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.weixin.*;

public class SendWeiXinTemplateMessageUI extends Page {

	private String getActivityTemplateId() {

		// 模板id
		QueryBuilder qbZDcode = new QueryBuilder(
				"SELECT Memo FROM ZDCode WHERE CodeType = 'WX.MessageTemplate' AND CodeValue = 'activityNotice'");
		String templateId = qbZDcode.executeString();
		if (StringUtil.isEmpty(templateId)) {
			templateId = "uqlbO-zZD83JhOjZgsO7ufrDMZZOhijLHm6oWKE2nZs";
		}
		return templateId;
	}

	private WeiXinTemplateMessage buildWeiXinTemplateMessage(String openid, String templateId) {

		WeiXinTemplateMessage message = new WeiXinTemplateMessage();
		message.setTemplateId(templateId)
				.setToOpenid(openid)
				.setSendDateTime(new Date())
				.setFirst(WeiXinTemplateMessage.buildValueMap("又一重磅新品重疾险即将上线！重疾+轻症（轻症多次赔付）刷新市场底价！\n" +
						"预约新品抢限量9折优惠，再送重疾绿通服务！\n", "#6699FF"))
				.setWord1(WeiXinTemplateMessage.buildValueMap("开心保新品预约"))
				.setWord2(WeiXinTemplateMessage.buildValueMap("开心保会员"))
				.setWord3(WeiXinTemplateMessage.buildValueMap("10月12日正式开启预约，抢9折限量优惠\n"))
				.setRemark(WeiXinTemplateMessage.buildValueMap("预约通道即将开启，12号请关注开心保微信预约活动！", "#6699FF"));
		return message;
	}

	public void send() {

		String openid = $V("openid");
		String templateId = getActivityTemplateId();
		WeiXinTemplateMessage message = buildWeiXinTemplateMessage(openid, templateId);
		WeiXinMessageSender sender = new WeiXinTemplateMessageSenderImpl();
		try {
			if (sender.send(message)) {
				Response.setMessage("发送成功");
			} else {
				Response.setError("发送失败,请确认openid已经关注公众号");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("发送失败");

		}

	}

	public DataTable queryOpenid(int pageSize, int pageIndex) {

		QueryBuilder qb = new QueryBuilder("select DISTINCT openid from wxbind");
		return qb.executePagedDataTable(pageSize, pageIndex);
	}

	public void send2All() {

		String templateId = getActivityTemplateId();
		int pageSize = 500;
		int pageIndex = 0;
		DataTable dt;
		List<WeiXinMessage> list = new ArrayList<WeiXinMessage>();
		WeiXinMessageListSender sender = new WeiXinTemplateMessageListSenderImpl();
		try {
			do {
				dt = queryOpenid(pageSize, pageIndex++);
				for (int i = 0; i < dt.getRowCount(); i++) {
					String openid = dt.getString(i, 0);
					list.add(buildWeiXinTemplateMessage(openid, templateId));
				}
				sender.send(list);
				list.clear();
			} while (dt.getRowCount() == pageSize);
			Response.setMessage("已经发送");
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("发送失败");
		}

	}

}
