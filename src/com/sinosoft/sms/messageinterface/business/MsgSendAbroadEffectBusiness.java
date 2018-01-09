/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * 保单生效提醒+事项提醒短信
 *
 */
public class MsgSendAbroadEffectBusiness extends MessageBusinessBL {

	private String mobilenum;
	private String unitcode;
	private String productName;
	private String svaliDate;
	private String evaliDate;
	private String company;
	private String reportPhone;

	protected boolean checkData() {
		this.mobilenum = super.getInput(MessageCode.MOBILENUM);
		this.unitcode = super.getInput(MessageCode.MANAGECOM);
		this.productName = super.getInput("ProductName");
		this.svaliDate = super.getInput("SvaliDate");
		this.evaliDate = super.getInput("EvaliDate");
		this.company = super.getInput("Company");
		this.reportPhone = super.getInput("ReportPhone");
		return true;
	}

	protected boolean dealData() {
		String senddata = this.productName + ";" + this.svaliDate + ";"
				+ this.evaliDate + ";" + this.company + ";" + this.reportPhone;
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();
		return true;
	}
}
