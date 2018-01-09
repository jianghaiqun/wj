/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author wangcaiyun
 *
 */
public class MsgClaimsRefuseBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String claimsItems;
	private String returnDesc;
	private String claimsNo;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.claimsItems = super.getInput(MessageCode.CLAIMS_ITEMS);
	    this.returnDesc = super.getInput(MessageCode.CLAIMS_RETURN_DESC);
	    this.claimsNo = super.getInput(MessageCode.CLAIMS_NO);
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.claimsItems+";"+this.returnDesc+";"+this.claimsNo;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
