/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * 通用短信
 *
 */
public class MsgSendAll extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String messages;
	private String datatype;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.messages = super.getInput(MessageCode.ROOTDATA);
		return true;
	}
	
	protected boolean dealData() {
		String Messages = this.messages;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(Messages+";");
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
