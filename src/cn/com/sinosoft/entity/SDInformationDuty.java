package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 
 * 实体类：责任信息 
 *
 */



@Entity
public class SDInformationDuty extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3584856828290751493L;
	
	private String	dutySn	;//	责任编码	
	private String	informationSn	;//	订单明细表编号	
	private String	orderSn	;//	订单编号	
	private String	dutyFullName	;//	责任全称	
	private String	dutyName	;//	责任简称	
	private String	coverage	;//	保障内容	
	private String	riskCode	;//	险种编码	
	private String	premium	;//	保费	
	private String	showAmnt	;//	显示值	
	private String	amt	;//	计算值保额	
	private String	mainRiskFlag	;//	是否主险标志	
	private String	supplierDutyCode	;//	保险公司责任/险别编码	
	private String dutyEnName;//责任英文名
	private String enCoverage;//责任英文说明
	private String orderFlag;//排序
	private String isDisplay;// 是否显示
	private String discountRates;// 折扣
	private String discountPrice;// 折后价
	private SDInformation sdinformation;//订单明细表信息
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public SDInformation getSdinformation() {
		return sdinformation;
	}
	public void setSdinformation(SDInformation sdinformation) {
		this.sdinformation = sdinformation;
	}
	public String getDutySn() {
		return dutySn;
	}
	public void setDutySn(String dutySn) {
		this.dutySn = dutySn;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getDutyFullName() {
		return dutyFullName;
	}
	public void setDutyFullName(String dutyFullName) {
		this.dutyFullName = dutyFullName;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getShowAmnt() {
		return showAmnt;
	}
	public void setShowAmnt(String showAmnt) {
		this.showAmnt = showAmnt;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getMainRiskFlag() {
		return mainRiskFlag;
	}
	public void setMainRiskFlag(String mainRiskFlag) {
		this.mainRiskFlag = mainRiskFlag;
	}
	public String getSupplierDutyCode() {
		return supplierDutyCode;
	}
	public void setSupplierDutyCode(String supplierDutyCode) {
		this.supplierDutyCode = supplierDutyCode;
	}
	public String getDutyEnName() {
		return dutyEnName;
	}
	public void setDutyEnName(String dutyEnName) {
		this.dutyEnName = dutyEnName;
	}
	public String getEnCoverage() {
		return enCoverage;
	}
	public void setEnCoverage(String enCoverage) {
		this.enCoverage = enCoverage;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getDiscountRates() {
		return discountRates;
	}
	public void setDiscountRates(String discountRates) {
		this.discountRates = discountRates;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}