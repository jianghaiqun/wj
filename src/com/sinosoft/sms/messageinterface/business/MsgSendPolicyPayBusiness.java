package com.sinosoft.sms.messageinterface.business;

public class MsgSendPolicyPayBusiness extends MessageBusinessBL{

	private String orderSn;//订单号
	private String mobileNo;//手机号
	private String mail;//邮箱
	private String unitcode;
	@Override
	protected boolean checkData() {
		this.orderSn = super.getInput("orderSn");
		this.mobileNo = super.getInput("MobileNum");
		this.mail = super.getInput("appntEmail");
		this.unitcode = super.getInput("UnitCode");
		return true;
	}

	@Override
	protected boolean dealData() {
		String senddata = this.orderSn+";"+this.mail+";";
	    super.addMobileNum(this.mobileNo);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();
	    return true;
	}

}
