/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

/*******************************************************************************
 * @author   : 王彩云
 * @version  : 1.00
 * @date     : 2015-02-03
 * @direction: 淘宝新会员投保成功通知
 ******************************************************************************/

public class MsgNewMemInsuredSuccBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String productName;
	private String policyNo;
	private String points;
	private String loginId;
	private String password;

	protected boolean checkData() {
		this.mobilenum = super.getInput("MobileNum");
		this.unitcode = super.getInput("UnitCode");
		this.productName = super.getInput("productName");
		this.policyNo = super.getInput("policyNo");
		this.points = super.getInput("points");
		this.loginId = super.getInput("loginId");
		this.password = super.getInput("password");
		return true;
	}

	protected boolean dealData() {
//		String senddata = this.productName + ";" + this.policyNo + ";"
//				+ this.points + ";" + loginId + ";" + password;
		String senddata = password;
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();

		return true;
	}
}
