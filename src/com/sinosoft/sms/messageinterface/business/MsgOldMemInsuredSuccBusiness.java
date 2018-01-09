/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

/*******************************************************************************
 * @author : 王彩云
 * @version : 1.00
 * @date : 2015-02-03
 * @direction: 淘宝老会员投保成功通知
 ******************************************************************************/

public class MsgOldMemInsuredSuccBusiness extends MessageBusinessBL {
	private String mobilenum;
	private String unitcode;
	private String productName;
	private String policyNo;
	private String points;

	protected boolean checkData() {
		this.mobilenum = super.getInput("MobileNum");
		this.unitcode = super.getInput("UnitCode");
		this.productName = super.getInput("productName");
		this.policyNo = super.getInput("policyNo");
		this.points = super.getInput("points");
		return true;
	}

	protected boolean dealData() {
		//String senddata = this.productName + ";" + this.policyNo + ";"
		//		+ this.points;
		String senddata = ";";
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();

		return true;
	}
}
