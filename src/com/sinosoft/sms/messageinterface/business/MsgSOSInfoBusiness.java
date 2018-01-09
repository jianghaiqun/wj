package com.sinosoft.sms.messageinterface.business;



/*******************************************************************************
 * @author   : cuishg
 * @version  : 1.00
 * @date     : 2015-04-21
 * @direction: SOS活动短信
 ******************************************************************************/

public class MsgSOSInfoBusiness extends MessageBusinessBL
{
	  private String mobilenum;
	  private String appntname;
	  private String unitcode;
	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.appntname = super.getInput("appntname");
	    this.unitcode = super.getInput("UnitCode");
	    return true;
	  }

	  protected boolean dealData() {
	    String senddata =this.appntname;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }

}
