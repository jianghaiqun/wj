/**
 * 
 */
package com.sinosoft.sms.messageinterface.business;

/**
 * @author wangcaiyun
 * 积分兑换福禄礼品
 */
public class MsgSendIntegralExchange23 extends MessageBusinessBL {
	private String mobilenum;
	private String unitcode;
	private String pointnum; // 积分数目
	private String giftprice; // 兑换金额
	private String title; // 标题
	
	protected boolean checkData() {
		this.mobilenum = super.getInput("MobileNum");
		this.unitcode = super.getInput("UnitCode");
		this.pointnum = super.getInput("pointnum");
		this.giftprice = super.getInput("giftprice");
		this.title = super.getInput("title");
		return true;
	}

	protected boolean dealData() {
		String senddata = this.pointnum + ";" + this.giftprice + ";" + this.title;
		super.addMobileNum(this.mobilenum);
		super.addSendData(senddata);
		super.addManageCom(this.unitcode);
		super.addResult();

		return true;
	}
}
