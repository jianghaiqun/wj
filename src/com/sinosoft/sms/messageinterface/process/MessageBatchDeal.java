package com.sinosoft.sms.messageinterface.process;

import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LIMessageInteractSchema;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*******************************************************************************
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @author : zhouxiang
 * @version : 1.00
 * @date : 2012-08-08
 * @direction: 短信接口服务批量数据处理类
 ******************************************************************************/

public class MessageBatchDeal {
	// public CErrors mErrors = new CErrors();
	// private TransferData inputData = new TransferData();
	private Mapx<String, Object> inputData = new Mapx<String, Object>();
	// private GlobalInput globalInput = new GlobalInput();
	private String servicecode = "";
	private String smsSQl = "";

	public boolean submitData(Mapx<String, Object> mMap, String tServicecode) throws ParseException {
		if (!getInputData(mMap, tServicecode)) {
			return false;
		}

		setLIMessageInteract();

		return true;
	}

	private boolean getInputData(Mapx<String, Object> mMap, String tServicecode) {
		// this.inputData = (TransferData) vData.getObjectByObjectName("TransferData", 0);

		this.inputData = (Mapx<String, Object>) mMap.get("TransferData");
		// this.globalInput = (GlobalInput) vData.getObjectByObjectName("GlobalInput", 0);
		this.servicecode = tServicecode;

		return true;
	}

	private boolean setLIMessageInteract() throws ParseException {
		LIMessageInteractSchema tLIMessageInteractSchema = new LIMessageInteractSchema();
		StringBuffer tSQL = new StringBuffer("");

		tSQL.append("select m.messtopic,m.sendway,m.senddate,m.sendtime,m.dealorder ");
		tSQL.append("from LIMessageService m ");
		tSQL.append("where servicecode ='");
		tSQL.append(servicecode);
		tSQL.append("' ");
		DataTable dt = new QueryBuilder(tSQL.toString()).executeDataTable();
		// 流水id
		// String messageid = PubFun1.CreateMaxNo("MESSAGEID", 20);
		String messageid = NoUtil.getMaxNo("MESSAGEID", 20);
		String mobilenum = (String) inputData.get(MessageCode.MOBILENUM);
		String senddata = (String) inputData.get(MessageCode.SENDDATA);
		String customername = (String) inputData.get(MessageCode.CUSTOMERNAME);
		String servicebussno = (String) inputData.get(MessageCode.SERVICEBUSSNO);
		// String managecom = (String) inputData.get(MessageCode.MANAGECOM);
		String managecom = "wjcom";
		String fixdate = "";
		String fixtime = "";
		if (dt.getString(0, 2) == null || dt.getString(0, 2).equals("")) {
			fixdate = (String) inputData.get(MessageCode.FIXEDDATE);
		} else {
			fixdate = dt.getString(0, 2);
		}
		if (dt.getString(0, 3) == null || dt.getString(0, 3).equals("")) {
			fixtime = (String) inputData.get(MessageCode.FIXEDTIME);
		} else {
			fixtime = dt.getString(0, 3);
		}

		String messtopic = dt.getString(0, 0);
		String sendway = dt.getString(0, 1);
		String dealorder = dt.getString(0, 4);

		tLIMessageInteractSchema.setSystemCode(MessageCode.SYS_SYSTEMCODE);
		tLIMessageInteractSchema.setServiceCode(servicecode);
		tLIMessageInteractSchema.setMessageId(messageid);
		tLIMessageInteractSchema.setMakeDate(PubFun.StringToDate(PubFun.getCurrentDate()));
		tLIMessageInteractSchema.setMakeTime(PubFun.getCurrentTime());
		tLIMessageInteractSchema.setMobileNum(mobilenum);
		tLIMessageInteractSchema.setCustomerName(customername);
		tLIMessageInteractSchema.setMessTopic(messtopic);
		tLIMessageInteractSchema.setSendData(senddata);
		tLIMessageInteractSchema.setDataType("0");
		tLIMessageInteractSchema.setSendWay(sendway);
		tLIMessageInteractSchema.setServiceBussNo(servicebussno);
		tLIMessageInteractSchema.setFixedDate(PubFun.StringToDate(fixdate));
		tLIMessageInteractSchema.setFixedTime(fixtime);
		tLIMessageInteractSchema.setUnitCode(managecom);
		tLIMessageInteractSchema.setSendCode("Sys");
		tLIMessageInteractSchema.setDealOrder(dealorder);
		tLIMessageInteractSchema.setCheckFlag("1");
		tLIMessageInteractSchema.setAnswerMatch("1");
		tLIMessageInteractSchema.setReplyFlag("1");

		smsSQl = " insert into LIMessageInteract ( SystemCode,ServiceCode," + " MessageId,ServiceBussNo,MakeDate, MakeTime,MobileNum,CustomerName," + " MessTopic,SendData,DataType,SendWay,FixedDate,FixedTime,UnitCode,SendCode,DealOrder," + " CheckFlag,AnswerMatch,ReplyFlag ) values ( 'LIS'," + "'" + servicecode + "','" + messageid + "','" + servicebussno + "',date'" + PubFun.getCurrentDate()
				+ "','" + PubFun.getCurrentTime() + "','" + mobilenum + "','" + customername + "','" + messtopic + "','" + senddata + "','0','" + sendway + "'," + "date'" + fixdate + "','" + fixtime + "','" + managecom + "','WJ','" + dealorder + "','1','1','1')";
		Transaction trans = new Transaction();
		// VData tData = new VData();
		// MMap map = new MMap();
		// map.put(tLIMessageInteractSchema, "INSERT");
		// tData.add(map);

		trans.add(tLIMessageInteractSchema, Transaction.INSERT);

		if (!DataLISSubmit(trans)) {
			return false;
		}
		if (!DataSubmit()) {
			return false;
		}

		return true;
	}

	private boolean DataLISSubmit(Transaction trans) {
		if (!trans.commit()) {

			return false;
		}

		return true;
	}

	public boolean DataSubmit() {
		// 因PubSubmit提交Schema不生效改用sql直接提交
		GetDBdata get = new GetDBdata();
		try {
			get.execUpdateSQL(smsSQl, "DefaultSMS");
		} catch (Exception e) {
			return false;
		}

		return true;

	}

}
