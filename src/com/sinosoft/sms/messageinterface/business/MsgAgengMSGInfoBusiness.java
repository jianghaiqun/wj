package com.sinosoft.sms.messageinterface.business;



/*******************************************************************************
 * @author   : cuishg
 * @version  : 1.00
 * @date     : 2015-04-21
 * @direction: SOS活动短信
 ******************************************************************************/

public class MsgAgengMSGInfoBusiness extends MessageBusinessBL
{
	  private String mobilenum;
	  private String validatecode;
	  private String unitcode;
	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.validatecode = super.getInput("validatecode");
	    this.unitcode = super.getInput("UnitCode");
	    return true;
	  }

	  protected boolean dealData() {
	    String senddata =this.validatecode;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }

}
