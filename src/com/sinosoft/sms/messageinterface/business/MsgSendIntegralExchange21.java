package com.sinosoft.sms.messageinterface.business;

/*******************************************************************************
 * @direction:积分兑换礼品
 ******************************************************************************/

public class MsgSendIntegralExchange21 extends MessageBusinessBL {
	private String mobilenum;
	private String unitcode;
	private String pointnum; // 积分数目
	private String giftprice; // 兑换金额
	private String title; // 标题
	private String password; // 密码

	protected boolean checkData() {
		this.mobilenum = super.getInput("MobileNum");
		this.unitcode = super.getInput("UnitCode");
		this.pointnum = super.getInput("pointnum");
		this.giftprice = super.getInput("giftprice");
		this.title = super.getInput("title");
		this.password = super.getInput("password");
		return true;
	}

	protected boolean dealData() {
		String senddata = this.pointnum + ";" + this.giftprice + ";" + this.title + ";" + this.password;
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();

		return true;
	}

}
