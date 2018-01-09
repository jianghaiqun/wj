package com.sinosoft.datachannel;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.service.MailService;
import cn.com.sinosoft.service.impl.MailServiceImpl;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import com.sinosoft.sms.messageinterface.CallMessageService;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NExpReminderService {
	private static final Logger logger = LoggerFactory.getLogger(NExpReminderService.class);
	public void execute() {
		logger.info("定时发送开始！");
		Date tDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		Date newDate = PubFun.calDate(tDate, 30, "D", null);
		String temp1 = formatter.format(newDate);
		temp1 = temp1.substring(0, 10).replace("-", "");
		// 到期前30天
		// 非车险保单到期提醒邮件
		sendMail(temp1);

		newDate = PubFun.calDate(tDate, 14, "D", null);
		temp1 = formatter.format(tDate);
		temp1 = temp1.substring(0, 10).replace("-", "");
		// 到期前14天
		// 非车险保单到期提醒邮件
		sendMail(temp1);

		newDate = PubFun.calDate(tDate, 2, "D", null);
		temp1 = formatter.format(tDate);
		temp1 = temp1.substring(0, 10).replace("-", "");
		// 到期前2天
		// 非车险保单到期提醒邮件
		sendMail(temp1);
		// 非车险保单到期提醒短信
		sendSMS(temp1);
		logger.info("定时发送结束！");
	}

	private void sendSMS(String temp) {
		memberSchema member = new memberSchema();
		memberSet tmemberSet = member.query(new QueryBuilder("where date_format(birthday,'%m%d') ='" + temp + "'"));
		for (int j = 0; j < tmemberSet.size(); j++) {
			member = tmemberSet.get(j);
			// 手机短信发送
			String mobile = member.getmobileNO();
			String name = "";
			if (StringUtil.isNotEmpty(member.getrealName())) {
				name = member.getrealName();
			} else {
				name = member.getusername();
			}
			CallMessageService tCallMessageService = new CallMessageService();
			Mapx<String, Object> mMap = new Mapx<String, Object>();
			Mapx<String, Object> tTransferData = new Mapx<String, Object>();

			tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
			tTransferData.put(MessageCode.MEMBERNAME, name);
			mMap.put("TransferData", tTransferData);
			String tServiceCode = "";

			if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
				logger.error("发送失败！");
			}

		}

	}

	private void sendMail(String temp) {
		memberSchema member = new memberSchema();
		memberSet tmemberSet = member.query(new QueryBuilder("where date_format(birthday,'%m%d') ='" + temp + "'"));
		for (int j = 0; j < tmemberSet.size(); j++) {
			member = tmemberSet.get(j);
			// 邮件发送
			String mailName = "";// 邮件模版编码
			MailConfig mailConfig = TemplateConfigUtil.getMailConfig(mailName);
			String subject = mailConfig.getSubject();
			String templateFilePath = mailConfig.getTemplateFilePath();
			String toMail = member.getemail();
			Map<String, Object> data = new HashMap<String, Object>();
			MailService tMailService = new MailServiceImpl();
			tMailService.sendMail(subject, templateFilePath, data, toMail);

		}
	}

}
