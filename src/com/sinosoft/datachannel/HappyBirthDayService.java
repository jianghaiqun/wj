package com.sinosoft.datachannel;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.forward.GotoMail;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCSendEmailLogSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import com.sinosoft.sms.messageinterface.CallMessageService;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HappyBirthDayService {
	private static final Logger logger = LoggerFactory.getLogger(HappyBirthDayService.class);
	private String fixeddate="";//定时日期
	private String fixedtime="";//定时时间

	public void execute() {
		logger.info("定时发送开始！");
		Date tDate = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");//定时使用
		String temp2 = formatter1.format(tDate);
		fixeddate=PubFun.calSDate(temp2, 1, "D");;
		fixedtime="08:00:00";
		String temp1 = fixeddate.substring(5, 10).replace("-", "");
		//temp1="0913";
		// 会员祝福发送
		sendToMember(temp1);

		// 投保人祝福发送
		sendToAppnt(temp1);

		// 被保人祝福发送
		sendToInsured(temp1);

		logger.info("定时发送结束！");
	}

	private void sendToMember(String temp) {
		memberSchema member = new memberSchema();
		memberSet tmemberSet = member.query(new QueryBuilder("where date_format(birthday,'%m%d') ='" + temp + "'"));
		for (int j = 0; j < tmemberSet.size(); j++) {
			member = tmemberSet.get(j);
			if (StringUtil.isNotEmpty(member.getmobileNO()) && "Y".equals(member.getisMobileNOBinding())) {
				// 手机短信发送
				String mobile = member.getmobileNO();
				String name = "";
				if (StringUtil.isNotEmpty(member.getrealName())) {
					name = member.getrealName();
				} else if(StringUtil.isNotEmpty(member.getusername())){
					name = member.getusername();
				}else{
					name=member.getmobileNO();
				}
				CallMessageService tCallMessageService = new CallMessageService();
				Mapx<String, Object> mMap = new Mapx<String, Object>();
				Mapx<String, Object> tTransferData = new Mapx<String, Object>();

				tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
				tTransferData.put(MessageCode.MEMBERNAME, name);
				tTransferData.put(MessageCode.FIXEDDATE, fixeddate);
				tTransferData.put(MessageCode.FIXEDTIME, fixedtime);
				tTransferData.put(MessageCode.MANAGECOM, "86");
				mMap.put("TransferData", tTransferData);
				String tServiceCode = "WF0007";

				if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
					logger.error("发送失败！");
				}
			} else {
				// 邮件发送
				String mailName = "a0015";//邮件模版编码
				MailConfig mailConfig = TemplateConfigUtil.getMailConfig(mailName);
				String subject = mailConfig.getSubject();
				String templateFilePath = mailConfig.getTemplateFilePath();
				String toMail = member.getemail();
				Map<String, Object> data =new HashMap<String, Object>();
				String name = "";
				if (StringUtil.isNotEmpty(member.getrealName())) {
					name = member.getrealName();
				} else if(StringUtil.isNotEmpty(member.getusername())){
					name = member.getusername();
				}else{
					name=member.getemail();
				}
				data.put("MemberName", name);
				GotoMail t = new GotoMail();
				try {
					t.deal(java.net.URLEncoder.encode(subject,"utf8"),templateFilePath, data, toMail);
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
				writelog(subject,toMail,name,member.getid());
			}

		}
	}

	private void writelog(String subject ,String toEmail,String userName,String serviceBussNo){
		SCSendEmailLogSchema tSCSendEmailLogSchema=new SCSendEmailLogSchema();
		tSCSendEmailLogSchema.setId(NoUtil.getMaxID("EmailLogID")+"");
		tSCSendEmailLogSchema.setSubject(subject);
		tSCSendEmailLogSchema.setEmail(toEmail);
		tSCSendEmailLogSchema.setUserName(userName);
		tSCSendEmailLogSchema.setServiceBussNo(serviceBussNo);
		try {
			tSCSendEmailLogSchema.setMakeDate(PubFun.StringToDate(PubFun.getCurrentDate()));
			tSCSendEmailLogSchema.setMakeTime(PubFun.getCurrentTime());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		tSCSendEmailLogSchema.insert();
	}
	private void sendToAppnt(String temp) {
		DataTable dt = new QueryBuilder("select distinct applicantmobile, applicantname from InformationAppnt where  date_format(applicantbirthday,'%m%d') =?", temp).executePagedDataTable(1, 0);
		for (int j = 0; j < dt.getRowCount(); j++) {
			String mobile = dt.getString(j, 0);// 电话
			String name = dt.getString(j, 1);// 姓名
			CallMessageService tCallMessageService = new CallMessageService();
			Mapx<String, Object> mMap = new Mapx<String, Object>();
			Mapx<String, Object> tTransferData = new Mapx<String, Object>();

			tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
			tTransferData.put(MessageCode.MEMBERNAME, name);
			tTransferData.put(MessageCode.FIXEDDATE, fixeddate);
			tTransferData.put(MessageCode.FIXEDTIME, fixedtime);
			tTransferData.put(MessageCode.MANAGECOM, "86");
			mMap.put("TransferData", tTransferData);
			String tServiceCode = "WF0007";

			if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
				logger.error("发送失败！");
			}
		}
	}

	private void sendToInsured(String temp) {
		DataTable dt1 = new QueryBuilder("select distinct recognizeetel, recognizeename from InformationInsured where  date_format(recognizeebirthday,'%m%d') =?", temp).executePagedDataTable(1, 0);
		for (int j = 0; j < dt1.getRowCount(); j++) {
			String mobile = dt1.getString(j, 0);// 电话
			String name = dt1.getString(j, 1);// 姓名
			CallMessageService tCallMessageService = new CallMessageService();
			Mapx<String, Object> mMap = new Mapx<String, Object>();
			Mapx<String, Object> tTransferData = new Mapx<String, Object>();

			tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
			tTransferData.put(MessageCode.MEMBERNAME, name);
			tTransferData.put(MessageCode.FIXEDDATE, fixeddate);
			tTransferData.put(MessageCode.FIXEDTIME, fixedtime);
			tTransferData.put(MessageCode.MANAGECOM, "86");
			mMap.put("TransferData", tTransferData);
			String tServiceCode = "WF0007";

			if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
				logger.error("发送失败！");
			}
		}
	}

}
