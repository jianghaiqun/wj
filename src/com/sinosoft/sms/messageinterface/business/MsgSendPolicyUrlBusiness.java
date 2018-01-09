package com.sinosoft.sms.messageinterface.business;

/*******************************************************************************
 * @author : 高海隽
 * @version : 1.00
 * @date : 2015-02-03
 * @direction: 淘宝电子保单下载短信通知
 ******************************************************************************/

public class MsgSendPolicyUrlBusiness extends MessageBusinessBL {
	private String mobilenum;
	private String unitcode;
	private String electronicPath;

	protected boolean checkData() {
		this.mobilenum = super.getInput("MobileNum");
		this.unitcode = super.getInput("UnitCode");
		this.electronicPath = super.getInput("electronicPath");
		return true;
	}

	protected boolean dealData() {
		String senddata = this.electronicPath;
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();

		return true;
	}
}
