/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * 发票寄送提醒短信
 *
 */
public class MsgSendBillSendBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String memberName;
	private String company;
	private String wayBillNo;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.memberName = super.getInput(MessageCode.MEMBERNAME);
	    this.company = super.getInput(MessageCode.LOGISTICS_COMPANY);
	    this.wayBillNo = super.getInput(MessageCode.WAY_BILL_NO);
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.memberName+";"+this.company+";"+this.wayBillNo;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
