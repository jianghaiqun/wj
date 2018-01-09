/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author cuishigang
 *
 */
public class MsgClaimsCheckAPIBusiness extends MessageBusinessBL {
	
	private String mobilenum;
	private String unitcode;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
		return true;
	}
	
	protected boolean dealData() {
	    super.addMobileNum(this.mobilenum);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
