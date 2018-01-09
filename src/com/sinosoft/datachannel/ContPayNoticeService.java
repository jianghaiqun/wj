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

public class ContPayNoticeService {
	private static final Logger logger = LoggerFactory.getLogger(ContPayNoticeService.class);
	private String fixeddate = "";// 定时日期
	private String fixedtime = "";// 定时时间

	public void execute() {
		logger.info("定时发送开始！");
		// 短信隔天八点发送
		Date tDate = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");// 定时使用
		String temp1 = formatter1.format(tDate);
		temp1=PubFun.calSDate(temp1, 1, "D");
		temp1 = temp1.substring(0, 10).replace("-", "");
		fixeddate = temp1;
		fixedtime = "08:00:00";

		DataTable dt = new QueryBuilder("select a.ordersn,a.createdate,a.productTotalPrice,a.memberid from orders a where  a.paymentStatus='0' and date_format(a.createdate,'%Y%m%d')=? and ordersn not in (select b.ServiceBussNo from SCSendEmailLog b where date_format(b.MakeDate,'%Y%m%d')=date_format(a.createdate,'%Y%m%d'))", temp1).executePagedDataTable(1, 0);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ordersn=dt.getString(i, 0);
			String createdate=dt.getString(i, 1);
			String productTotalPrice=dt.getString(i, 2);
			String memberid=dt.getString(i, 3);
			memberSchema tmemberSchema=new memberSchema();
			tmemberSchema.setid(memberid);
			tmemberSchema.fill();
			
			sendBySMS(tmemberSchema);

			sendByMail(ordersn,createdate,productTotalPrice,tmemberSchema);
		}

		logger.info("定时发送结束！");
	}

	private void sendBySMS(memberSchema tmemberSchema) {
//		DataTable dt = new QueryBuilder("select a.applicantMobile,a.applicantName from informationappnt a ,information b,orderitem c,orders d where a.information_id=b.id and b.orderitem_id=c.id and c.order_id=d.id and  d.OrderSn=?", orderSn).executePagedDataTable(1, 0);
//		String mobile = dt.getString(0, 0);// 电话
//		String mobile = dt.getString(0, 0);// 电话
//		String tContNo = orderSn;
		String mobile =tmemberSchema.getmobileNO();
		String name="";
		if(StringUtil.isNotEmpty(tmemberSchema.getrealName())){
			name=tmemberSchema.getrealName();
		}else if(StringUtil.isNotEmpty(tmemberSchema.getusername())){
			name=tmemberSchema.getusername();
		}else{
			name=mobile;
		}
		
		CallMessageService tCallMessageService = new CallMessageService();
		Mapx<String, Object> mMap = new Mapx<String, Object>();
		Mapx<String, Object> tTransferData = new Mapx<String, Object>();

		tTransferData.put(MessageCode.MOBILENUM, mobile);// 存储信息
		//tTransferData.put(MessageCode.SERVICEBUSSNO, tContNo);
		tTransferData.put(MessageCode.MEMBERNAME, name);
		tTransferData.put(MessageCode.FIXEDDATE, fixeddate);
		tTransferData.put(MessageCode.FIXEDTIME, fixedtime);
		tTransferData.put(MessageCode.MANAGECOM, "86");
		mMap.put("TransferData", tTransferData);
		String tServiceCode = "WF0008";

		if (!tCallMessageService.CallMessageService(mMap, tServiceCode)) {
			logger.error("发送失败！");
		}
		
		
		
	}

	private void sendByMail(String orderSn, String createdate, String productTotalPrice, memberSchema tmemberSchema) {
		String mailName = "a0017";//邮件模版编码
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(mailName);
		String subject = mailConfig.getSubject();
		String templateFilePath = mailConfig.getTemplateFilePath();
		Map<String, Object> data =new HashMap<String, Object>();
		DataTable dt = new QueryBuilder("select a.applicantName from informationappnt a ,information b,orderitem c,orders d where a.information_id=b.id and b.orderitem_id=c.id and c.order_id=d.id and  d.OrderSn=?", orderSn).executePagedDataTable(1, 0);
		String toMail = tmemberSchema.getemail();
    	String appntName=dt.getString(0, 0);
    	String name="";
		if(StringUtil.isNotEmpty(tmemberSchema.getrealName())){
			name=tmemberSchema.getrealName();
		}else if(StringUtil.isNotEmpty(tmemberSchema.getusername())){
			name=tmemberSchema.getusername();
		}else{
			name=toMail;
		}
		data.put("MemberName", name);
    	data.put("OrderSn", orderSn);
    	data.put("AppntName", appntName);
    	data.put("CreateDate", createdate);
    	data.put("ProductTotalPrice", productTotalPrice);
		GotoMail t = new GotoMail();
		try {
			t.deal(java.net.URLEncoder.encode(subject,"utf8"),templateFilePath, data, toMail);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		writelog(subject,toMail,(String)data.get("MemberName"),(String)data.get("OrderSn"));

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
