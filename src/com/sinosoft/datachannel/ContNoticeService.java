package com.sinosoft.datachannel;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.forward.GotoMail;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCSendEmailLogSchema;
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

public class ContNoticeService {
	private static final Logger logger = LoggerFactory.getLogger(ContNoticeService.class);

	private String fixeddate = "";// 定时日期
	private String fixedtime = "";// 定时时间

	public void execute() {
		logger.info("定时发送开始！");
		// 短信隔天八点发送
		Date tDate = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");// 定时使用
		String temp1 = formatter1.format(tDate);
		temp1=PubFun.calSDate(temp1, 1, "D");
		SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");// 定时使用
		String temp2 = formatter2.format(tDate);
		fixeddate = temp1;
		fixedtime = "08:00:00";

		DataTable dt = new QueryBuilder("select contno,AppntMobile,AppntEmail from fccont a where CValiDate =? and CValiDateTime <= ? and contno not in (select ServiceBussNo from LIMessageSen b)", temp1, temp2).executePagedDataTable(1, 0);
		for (int i = 0; i < dt.getRowCount(); i++) {

			//sendBySMS(dt.getString(i, 0),dt.getString(i, 1));

			sendByMail(dt.getString(i, 0),dt.getString(i, 2));
		}

		logger.info("定时发送结束！");
	}

	private void sendBySMS(String contNo, String appntMobile) {
		
		String mobile = appntMobile;// 电话
		String tContNo = contNo;// 姓名
		CallMessageService tCallMessageService = new CallMessageService();
		Mapx<String, Object> mMap = new Mapx<String, Object>();
		Mapx<String, Object> tTransferData = new Mapx<String, Object>();

		tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
		tTransferData.put(MessageCode.SERVICEBUSSNO, tContNo);
		tTransferData.put(MessageCode.FIXEDDATE, fixeddate);
		tTransferData.put(MessageCode.FIXEDTIME, fixedtime);
		tTransferData.put(MessageCode.MANAGECOM, "86");
		mMap.put("TransferData", tTransferData);
		String tServiceCode = "a0013";

		if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
			logger.error("发送失败！");
		}
		
		
		
	}

	private void sendByMail(String contNo, String email) {
		String mailName = "a0013";//邮件模版编码
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(mailName);
		String subject = mailConfig.getSubject();
		String templateFilePath = mailConfig.getTemplateFilePath();
		String toMail = email;
		Map<String, Object> data =new HashMap<String, Object>();
		DataTable dt = new QueryBuilder("select a.contno,a.AppntName,a.InsuredName,a.CValiDate,b.productName,b.insuranceCompany,b.ordersn from fccont a,orders b where a.orderNo=b.b.ordersn and a.contNo =? ", contNo).executePagedDataTable(1, 0);
    	String tContNo=dt.getString(0, 0);
    	String appntName=dt.getString(0, 1);
    	String insuredName=dt.getString(0, 2);
    	String CValiDate=dt.getString(0, 3);
    	String productName=dt.getString(0, 4);
    	String insuranceCompany=dt.getString(0, 5);
    	String ordersn=dt.getString(0, 6);
    	
    	data.put("ContNo", tContNo);
    	data.put("AppntName", appntName);
    	data.put("InsuredName", insuredName);
    	data.put("CValiDate", CValiDate);
    	data.put("ProductName", productName);
    	data.put("InsuranceCompany", insuranceCompany);
    	data.put("OrderSn", ordersn);
    	
		GotoMail t = new GotoMail();
		try {
			t.deal(java.net.URLEncoder.encode(subject,"utf8"),templateFilePath, data, toMail);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		writelog(subject,toMail,(String)data.get("MemberName"),(String)data.get("ContNo"));

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
}
