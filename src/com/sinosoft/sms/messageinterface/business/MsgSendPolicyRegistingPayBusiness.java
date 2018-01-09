package com.sinosoft.sms.messageinterface.business;

public class MsgSendPolicyRegistingPayBusiness extends MessageBusinessBL{

	private String orderSn;//订单号
	private String mobileNo;//手机号
	private String unitcode;
	private String username;
	private String password;
	@Override
	protected boolean checkData() {
		this.orderSn = super.getInput("orderSn");
		this.unitcode = super.getInput("UnitCode");
		this.mobileNo = super.getInput("MobileNum");
		this.username = super.getInput("username");
		this.password = super.getInput("password");
		return true;
	}

	@Override
	protected boolean dealData() {
		String senddata = this.orderSn+";"+this.username+";"+this.password+";";
	    super.addMobileNum(this.mobileNo);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
	    return true;
	}

}
