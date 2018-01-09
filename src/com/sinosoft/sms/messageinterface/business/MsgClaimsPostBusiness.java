/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author wangcaiyun
 *
 */
public class MsgClaimsPostBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String claimsItems;
	private String claimsSendAddress;
	private String claimsNo;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.claimsItems = super.getInput(MessageCode.CLAIMS_ITEMS);
	    claimsSendAddress = super.getInput(MessageCode.CLAIMS_SEND_ADDRESS);
	    this.claimsNo = super.getInput(MessageCode.CLAIMS_NO);
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.claimsItems+";"+this.claimsSendAddress+";"+this.claimsNo;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
