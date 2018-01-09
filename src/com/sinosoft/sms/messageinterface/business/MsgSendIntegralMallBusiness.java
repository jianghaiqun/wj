package com.sinosoft.sms.messageinterface.business;



/*******************************************************************************
 * @author   : zhangjing
 * @version  : 1.00
 * @date     : 2014-10-30
 * @direction: 积分商城兑换电话卡
 ******************************************************************************/

public class MsgSendIntegralMallBusiness extends MessageBusinessBL
{
	  private String mobilenum;
	  private String unitcode;
	  private String point;
	  private String mobilemsg;
	  private String mobilepd;

	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.unitcode = super.getInput("UnitCode");
	    this.point = super.getInput("point");
	    this.mobilemsg = super.getInput("mobilemsg");
	    this.mobilepd = super.getInput("mobilepd");
	    return true;
	  }

	  protected boolean dealData() {
	    String senddata =this.point+";"+this.mobilemsg+";"+this.mobilepd;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }

}
