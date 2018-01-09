package com.sinosoft.sms.messageinterface.process;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LIMessageSentSchema;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import org.jdom.Element;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口解析服务处理类
 ******************************************************************************/

public class MessageInfo {
	private Map mPublicMap = null;
	private Map mMessageMap = null;
	// private TransferData inputData = new TransferData();

	private Mapx<String, Object> inputData = new Mapx<String, Object>();
	// private GlobalInput globalInput = new GlobalInput();
	private String servicecode = "";
	private LIMessageSentSchema mLIMessageSentSchema = new LIMessageSentSchema();

	public static String[] publicInfo = { MessageCode.SYSTEMCODE, MessageCode.SEVICECODE };

	public static String[] messageInfo = { MessageCode.MESSAGEID, MessageCode.MOBILENUM, MessageCode.MESSTOPIC, MessageCode.SENDDATA, MessageCode.DATATYPE, MessageCode.SENDWAY, MessageCode.FIXEDDATE, MessageCode.FIXEDTIME, MessageCode.MANAGECOM, MessageCode.DEALORDER, MessageCode.ANSWERMATCH };

	public MessageInfo() {
		mPublicMap = new HashMap();
		mMessageMap = new HashMap();
	}

	public boolean init(Mapx<String, Object> mMap, String tServiceCode) {
		if (getInputData(mMap, tServiceCode)) {
			this.setPublicInfo(tServiceCode);
			this.setMessageInfo(mMap);
		}
		return true;
	}

	private boolean getInputData(Mapx<String, Object> mMap, String tServiceCode) {
		// this.globalInput = (GlobalInput) vData.getObjectByObjectName(
		// "GlobalInput",
		// 0);
		// this.inputData = (TransferData) vData.getObjectByObjectName(
		// "TransferData",
		// 0);
		this.inputData = (Mapx<String, Object>) mMap.get("TransferData");
		this.servicecode = tServiceCode;
		return true;
	}

	public void setPublicInfo(String tServiceCode) {
		mPublicMap.put(MessageCode.SYSTEMCODE, MessageCode.SYS_SYSTEMCODE);
		mPublicMap.put(MessageCode.SEVICECODE, tServiceCode);
	}

	public void setMessageInfo(Mapx<String, Object> mMap) {

		StringBuffer tSQL = new StringBuffer("");
		String fixeddate = "";
		String fixedtime = "";

		tSQL.append("select m.messtopic,m.sendway,m.senddate,m.sendtime,m.dealorder ");
		tSQL.append("from LIMessageService m ");
		tSQL.append("where servicecode ='");
		tSQL.append(servicecode);
		tSQL.append("' ");
		DataTable dt = new QueryBuilder(tSQL.toString()).executeDataTable();

		// 流水id需修改
		String messageid = NoUtil.getMaxNo("MESSAGEID", 20) + "";

		String mobilenum = (String) inputData.get(MessageCode.MOBILENUM);
		String senddata = (String) inputData.get(MessageCode.SENDDATA);
		String managecom = (String) inputData.get(MessageCode.MANAGECOM);
		if (dt.getString(0, 2) == null || dt.getString(0, 2).equals("")) {
			fixeddate = (String) inputData.get(MessageCode.FIXEDDATE);
		} else {
			fixeddate = dt.getString(0, 2);
		}
		if (dt.getString(0, 3) == null || dt.getString(0, 3).equals("")) {
			fixedtime = (String) inputData.get(MessageCode.FIXEDTIME);
		} else {
			fixedtime = dt.getString(0, 3);
		}
		mMessageMap.put(MessageCode.MESSAGEID, messageid);
		mMessageMap.put(MessageCode.MOBILENUM, mobilenum);
		mMessageMap.put(MessageCode.MESSTOPIC, dt.getString(0, 0));
		mMessageMap.put(MessageCode.SENDDATA, senddata);
		mMessageMap.put(MessageCode.DATATYPE, "0");
		mMessageMap.put(MessageCode.SENDWAY, dt.getString(0, 1));
		mMessageMap.put(MessageCode.FIXEDDATE, fixeddate);
		mMessageMap.put(MessageCode.FIXEDTIME, fixedtime);
		mMessageMap.put(MessageCode.MANAGECOM, managecom);
		mMessageMap.put(MessageCode.DEALORDER, dt.getString(0, 4));
		mMessageMap.put(MessageCode.ANSWERMATCH, "1");
	}

	public String getPublicInfo(String Key) {
		String tValue = (String) mPublicMap.get(Key);
		return tValue;
	}

	public String getMessageInfo(String Key) {
		String tValue = (String) mMessageMap.get(Key);
		return tValue;
	}

	public Element getPublicElement() {
		Element tPubInfo = new Element(MessageCode.PUBLICDATA);
		Element elem;
		for (int i = 0; i < publicInfo.length; i++) {
			elem = new Element(publicInfo[i]);
			String aaa=this.getPublicInfo(publicInfo[i]);
			elem.setText(this.getPublicInfo(publicInfo[i]));
			tPubInfo.addContent(elem);
		}
		return tPubInfo;
	}

	public Element getMessageElement() {
		Element tMsgInfo = new Element(MessageCode.MESSAGEDATA);
		Element elem;
		for (int i = 0; i < messageInfo.length; i++) {
			elem = new Element(messageInfo[i]);
			elem.setText(this.getMessageInfo(messageInfo[i]));

			if (messageInfo[i].endsWith("MobileNum")) {
				Element felem;
				felem = new Element(MessageCode.MOBILENUMS);
				felem.addContent(elem);
				tMsgInfo.addContent(felem);
			} else {
				tMsgInfo.addContent(elem);
			}

		}
		return tMsgInfo;
	}

	public LIMessageSentSchema getLIMessageSentSchema() {
		String customername = (String) inputData.get(MessageCode.CUSTOMERNAME);
		String bussno = (String) inputData.get(MessageCode.SERVICEBUSSNO);

		mLIMessageSentSchema.setMessageId(getMessageInfo(MessageCode.MESSAGEID));
		mLIMessageSentSchema.setServiceCode(servicecode);
		mLIMessageSentSchema.setComCode(getMessageInfo(MessageCode.MANAGECOM));
		mLIMessageSentSchema.setSendData(getMessageInfo(MessageCode.SENDDATA));
		mLIMessageSentSchema.setMobileNum(getMessageInfo(MessageCode.MOBILENUM));
		mLIMessageSentSchema.setCustomerName(customername);
		//mLIMessageSentSchema.setOperator(User.getUserName());
		mLIMessageSentSchema.setOperator("SYS");
		mLIMessageSentSchema.setServiceBussNo(bussno);

		return mLIMessageSentSchema;
	}

}
