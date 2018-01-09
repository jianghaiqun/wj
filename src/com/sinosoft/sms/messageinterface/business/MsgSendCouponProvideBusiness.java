package com.sinosoft.sms.messageinterface.business;



/*******************************************************************************
 * @author   : zhangjing
 * @version  : 1.00
 * @date     : 2013-10-17
 * @direction: 优惠券发放通知
 ******************************************************************************/

public class MsgSendCouponProvideBusiness extends MessageBusinessBL
{
	  private String mobilenum;
	  private String unitcode;
	  private String parvalue;
	  private String endtime;
	  private String couponsn;
	  private String url;

	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.unitcode = super.getInput("UnitCode");
	    this.parvalue = super.getInput("ParValue");
	    this.endtime = super.getInput("EndTime");
	    this.couponsn = super.getInput("Couponsn");
	    this.url = super.getInput("Url");
	    return true;
	  }

	  protected boolean dealData() {
	    String senddata =this.parvalue+";"+this.endtime+";"+this.couponsn+";"+this.url;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }

}
