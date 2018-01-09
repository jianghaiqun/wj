/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * 会员生日祝福提醒短信
 *
 */
public class MsgSendMemBirthdayBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String memberName;
	private String pointMultiple;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.memberName = super.getInput(MessageCode.MEMBERNAME);
	    this.pointMultiple = super.getInput("PointMultiple");
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.memberName+";"+this.pointMultiple;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
