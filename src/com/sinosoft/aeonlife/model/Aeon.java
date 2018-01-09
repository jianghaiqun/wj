/**
 * Project Name:FrontTrade
 * File Name:Aeon.java
 * Package Name:com.finance.model
 * Date:2016年6月3日上午9:55:24
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.model;

import java.util.Date;

/**
 * ClassName:Aeon <br/>
 * Function:TODO 百年保险的csv对象. <br/>
 * Date:2016年6月3日 上午9:55:24 <br/>
 *
 * @author:chouweigao 
 */
public class Aeon {
	String aeonOrderSn = "";
	String aeonName = "";
	String aeonCardNum = "";
	String aeonPhone = "";
	String aeonMail = "";
	String aeonAdd = "";
	String aeonAmount = "";
	String aeonPolicyNo = "";
	String aeonProductName = "";
	Date aeonStartDate = null;
	Date aeonEndDate = null;
	String aeonPolicyPath = "";
	//承保结果
	String aeonPolicyStauts = "";
	//退保结果
	String aeonPolicyResult = "";
	//退保成功/失败原因
	String aeonMsg= "";
	//退保时间
	Date cancelDate = null;
	//渠道
	String channels = null;
	
	//资产同步 总资产金额
	String total = "";
	
	public String getTodayincome() {
	
		return todayincome;
	}





	
	public void setTodayincome(String todayincome) {
	
		this.todayincome = todayincome;
	}

	//资产同步 本金
	String principal ="";
	//资产同步 收益
	String income = "";
	//当日收益
	String todayincome = "";
	// 手续费
	String fee = "";
	// 还款类型 1：犹豫期外不收手续费 2：犹豫期外收手续费 4：犹豫期内不收手续费。
	String returnType = "";
	
	public String getTotal() {
	
		return total;
	}




	
	public void setTotal(String total) {
	
		this.total = total;
	}




	
	public String getPrincipal() {
	
		return principal;
	}




	
	public void setPrincipal(String principal) {
	
		this.principal = principal;
	}




	
	public String getIncome() {
	
		return income;
	}




	
	public void setIncome(String income) {
	
		this.income = income;
	}




	public String getChannels() {
	
		return channels;
	}



	
	public void setChannels(String channels) {
	
		this.channels = channels;
	}



	public Date getCancelDate() {
	
		return cancelDate;
	}


	
	public void setCancelDate(Date cancelDate) {
	
		this.cancelDate = cancelDate;
	}


	public String getAeonMsg() {
	
		return aeonMsg;
	}

	
	public void setAeonMsg(String aeonMsg) {
	
		this.aeonMsg = aeonMsg;
	}

	public String getAeonPolicyResult() {
	
		return aeonPolicyResult;
	}
	
	public void setAeonPolicyResult(String aeonPolicyResult) {
	
		this.aeonPolicyResult = aeonPolicyResult;
	}

	public String getAeonPolicyStauts() {
	
		return aeonPolicyStauts;
	}

	
	public void setAeonPolicyStauts(String aeonPolicyStauts) {
	
		this.aeonPolicyStauts = aeonPolicyStauts;
	}

	public String getAeonOrderSn() {
	
		return aeonOrderSn;
	}
	
	public void setAeonOrderSn(String aeonOrderSn) {
	
		this.aeonOrderSn = aeonOrderSn;
	}
	
	public String getAeonName() {
	
		return aeonName;
	}
	
	public void setAeonName(String aeonName) {
	
		this.aeonName = aeonName;
	}
	
	public String getAeonCardNum() {
	
		return aeonCardNum;
	}
	
	public void setAeonCardNum(String aeonCardNum) {
	
		this.aeonCardNum = aeonCardNum;
	}
	
	public String getAeonPhone() {
	
		return aeonPhone;
	}
	
	public void setAeonPhone(String aeonPhone) {
	
		this.aeonPhone = aeonPhone;
	}
	
	public String getAeonMail() {
	
		return aeonMail;
	}
	
	public void setAeonMail(String aeonMail) {
	
		this.aeonMail = aeonMail;
	}
	
	public String getAeonAdd() {
	
		return aeonAdd;
	}
	
	public void setAeonAdd(String aeonAdd) {
	
		this.aeonAdd = aeonAdd;
	}
	
	public String getAeonAmount() {
	
		return aeonAmount;
	}
	
	public void setAeonAmount(String aeonAmount) {
	
		this.aeonAmount = aeonAmount;
	}
	
	public String getAeonPolicyNo() {
	
		return aeonPolicyNo;
	}
	
	public void setAeonPolicyNo(String aeonPolicyNo) {
	
		this.aeonPolicyNo = aeonPolicyNo;
	}
	
	public String getAeonProductName() {
	
		return aeonProductName;
	}
	
	public void setAeonProductName(String aeonProductName) {
	
		this.aeonProductName = aeonProductName;
	}
	
	public Date getAeonStartDate() {
	
		return aeonStartDate;
	}
	
	public void setAeonStartDate(Date aeonStartDate) {
	
		this.aeonStartDate = aeonStartDate;
	}
	
	public Date getAeonEndDate() {
	
		return aeonEndDate;
	}
	
	public void setAeonEndDate(Date aeonEndDate) {
	
		this.aeonEndDate = aeonEndDate;
	}
	
	public String getAeonPolicyPath() {
	
		return aeonPolicyPath;
	}
	
	public void setAeonPolicyPath(String aeonPolicyPath) {
	
		this.aeonPolicyPath = aeonPolicyPath;
	}
	
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}

