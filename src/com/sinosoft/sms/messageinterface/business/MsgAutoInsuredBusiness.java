/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

/**
 * @author wangcaiyun
 *
 */
public class MsgAutoInsuredBusiness extends MessageBusinessBL {
	private String mobilenum;
	private String memberMsg;
	private String activityDesc;
	  private String unitcode;
	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.activityDesc = super.getInput("activityDesc");
	    this.unitcode = super.getInput("UnitCode");
	    this.memberMsg = super.getInput("memberMsg");
	    return true;
	  }

	  protected boolean dealData() {
	    String senddata =this.activityDesc+";"+memberMsg;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }
}
