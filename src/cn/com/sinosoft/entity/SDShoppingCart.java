package cn.com.sinosoft.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class SDShoppingCart extends BaseEntity {

	private static final long serialVersionUID = -2133685783834557166L;
	private String memberId;//会员id
	private String orderSn;//订单号
	private BigDecimal totleAmount;//保费
	private String productId;
	private String productName;
	private String productLog;//产品logo
	private String startDate;//订单起保日期
	private String endDate;//订单止保日期
	private String appntName;//投保人
	private String appntEmail;//投保人邮箱
	private String firstInsuredName;//第一被保人
	private String allInsuredName;//所有被保人
	private String isEffective;//保障期限是否有效；N=否；Y=是；
	private String isPayFag;//支付完成标记
	private String recognizeeMul;//份数
	private String detailPath;
	private String orderSnKid;
	private String productTotalPrice;//原价
	private String discountRates;//保险公司折扣
	private String riskType;//险种中类别

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public BigDecimal getTotleAmount() {
		return totleAmount;
	}

	public void setTotleAmount(BigDecimal totleAmount) {
		this.totleAmount = totleAmount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAppntName() {
		return appntName;
	}

	public void setAppntName(String appntName) {
		this.appntName = appntName;
	}

	public String getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}

	public String getIsPayFag() {
		return isPayFag;
	}

	public void setIsPayFag(String isPayFag) {
		this.isPayFag = isPayFag;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLog() {
		return productLog;
	}

	public void setProductLog(String productLog) {
		this.productLog = productLog;
	}

	public String getFirstInsuredName() {
		return firstInsuredName;
	}

	public void setFirstInsuredName(String firstInsuredName) {
		this.firstInsuredName = firstInsuredName;
	}

	public String getAllInsuredName() {
		return allInsuredName;
	}

	public void setAllInsuredName(String allInsuredName) {
		this.allInsuredName = allInsuredName;
	}

	public String getRecognizeeMul() {
		return recognizeeMul;
	}

	public void setRecognizeeMul(String recognizeeMul) {
		this.recognizeeMul = recognizeeMul;
	}

	public String getAppntEmail() {
		return appntEmail;
	}

	public void setAppntEmail(String appntEmail) {
		this.appntEmail = appntEmail;
	}

	public String getDetailPath() {
		return detailPath;
	}

	public void setDetailPath(String detailPath) {
		this.detailPath = detailPath;
	}

	public String getOrderSnKid() {
		return orderSnKid;
	}

	public void setOrderSnKid(String orderSnKid) {
		this.orderSnKid = orderSnKid;
	}

	public String getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(String productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getDiscountRates() {
		return discountRates;
	}

	public void setDiscountRates(String discountRates) {
		this.discountRates = discountRates;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

}
