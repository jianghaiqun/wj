/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author wangcaiyun
 *
 */
public class MsgClaimsPostingBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String claimsItems;
	private String claimsPostInfo;
	private String claimsNo;
	
	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
	    this.unitcode = super.getInput(MessageCode.MANAGECOM);
	    this.claimsItems = super.getInput(MessageCode.CLAIMS_ITEMS);
	    this.claimsPostInfo = super.getInput(MessageCode.CLAIMS_POST_INFO);
	    this.claimsNo = super.getInput(MessageCode.CLAIMS_NO);
		return true;
	}
	
	protected boolean dealData() {
		String senddata = this.claimsItems+";"+this.claimsPostInfo+";"+this.claimsNo;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
		return true;
	}
}
