/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author wangcaiyun
 *
 */
public class MsgClaimsCompleBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String claimsItems;
	private String claimsMoney;
	private String claimsNo;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.claimsItems = super.getInput(MessageCode.CLAIMS_ITEMS);
	    this.claimsMoney = super.getInput(MessageCode.CLAIMS_MONEY);
	    this.claimsNo = super.getInput(MessageCode.CLAIMS_NO);
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.claimsItems+";"+this.claimsMoney+";"+this.claimsNo;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
	
}
