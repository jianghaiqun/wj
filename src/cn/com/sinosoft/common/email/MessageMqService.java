/**
 * Project Name:wj
 * File Name:MessageMqService.java
 * Package Name:cn.com.sinosoft.common.email
 * Date:2017年5月16日下午3:48:39
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 */
package cn.com.sinosoft.common.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.finance.activemq.producer.QueueSender;
import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName: MessageMqService <br/>
 * Function: TODO 邮件/短信发送类. <br/>
 * date: 2017年5月16日 下午2:32:04 <br/>
 * 
 * @author guozc
 * @version
 */
@Service("messageMqService")
public class MessageMqService {

	@Autowired
	private QueueSender queueSender;

	/**
	 * sendMessage:发送消息到email系统. <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param channel
	 *            渠道
	 * @param data
	 *            自定义参数
	 */
	public void sendMessage(String actionId, String channel, Map<String, Object> data) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("version", "1.0");
		param.put("actionId", actionId);
		if (StringUtil.isEmpty(channel)) {
			param.put("channel", "kxb");
		} else {
			param.put("channel", channel);
		}
		param.put("data", data);
		queueSender.sendMessage(JSONObject.toJSONString(param));
	}

	/**
	 * sendGeneralSms:发送短信(需要模板). <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param channel
	 *            渠道编码
	 * @param mobile
	 *            接收手机号码
	 * @param sendData
	 *            发送参数，参数用“;”分隔
	 */
	public void sendSms(String actionId, String channel, String mobile, String sendData) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(MessageConstant.PARAM_SUBACTIONID_NAME, actionId);
		data.put(MessageConstant.PARAM_MOBILE_NAME, mobile);
		data.put(MessageConstant.PARAM_SENDDATA_NAME, sendData);
		sendMessage("wj00000", channel, data);
	}

	/**
	 * sendGeneralSms:发送通用短信(不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param channel
	 *            渠道编码
	 * @param mobile
	 *            接收手机号码
	 * @param sendData
	 *            发送数据
	 */
	public void sendGeneralSms(String channel, String mobile, String sendData) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(MessageConstant.PARAM_MOBILE_NAME, mobile);
		data.put(MessageConstant.PARAM_SENDDATA_NAME, sendData);
		sendMessage("wj00210", channel, data);
	}

	/**
	 * sendMail:(发送邮件，需要模板). <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param channel
	 *            渠道编码
	 * @param data
	 *            邮件模板数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendMail(String actionId, String channel, Map<String, Object> freemarkerData) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(MessageConstant.PARAM_SUBACTIONID_NAME, actionId);
		data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT,
				freemarkerData.get(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT));
		data.put(MessageConstant.PARAM_FREEMARKER_DATA, freemarkerData);
		data.put(MessageConstant.PARAM_RECEIVER_NAME, freemarkerData.get(MessageConstant.PARAM_RECEIVER_NAME));
		data.put(MessageConstant.PARAM_SUBJECT_NAME, freemarkerData.get(MessageConstant.PARAM_SUBJECT_NAME));
		Object attachments = freemarkerData.get(MessageConstant.PARAM_ATTACHMENTS);
		if (attachments != null) {
			data.put(MessageConstant.PARAM_ATTACHMENTS, new ArrayList<Map<String, Object>>((List) attachments));
		}
		freemarkerData.remove(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT);
		freemarkerData.remove(MessageConstant.PARAM_RECEIVER_NAME);
		freemarkerData.remove(MessageConstant.PARAM_ATTACHMENTS);
		freemarkerData.remove(MessageConstant.PARAM_SUBJECT_NAME);
		sendMessage("wj00001", channel, data);
	}

	/**
	 * sendGeneralMail:(发送邮件，不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param channel
	 *            渠道编码
	 * @param receiver
	 *            接收邮件(多个用英文逗号分隔)
	 * @param ccAddress
	 *            抄送地址(多个用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachments
	 *            附件 [{"name":"附件名称","path":"附件地址"}]
	 */
	public void sendGeneralMail(String channel, String receiver, String ccAddress, String subject, String content,
			List<Map<String, Object>> attachments) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(MessageConstant.PARAM_RECEIVER_NAME, receiver);
		data.put(MessageConstant.PARAM_SUBJECT_NAME, subject);
		data.put(MessageConstant.PARAM_CONTENT_NAME, content);
		if (StringUtil.isNotEmpty(ccAddress)) {
			data.put(MessageConstant.PARAM_CC_NAME, ccAddress);
		}
		if (attachments != null && attachments.size() > 0) {
			data.put(MessageConstant.PARAM_ATTACHMENTS, attachments);
		}
		sendMessage("wj00001", channel, data);
	}

}
