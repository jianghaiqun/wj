package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * 实体类：险种信息
 */



@Entity
public class SDInformationRiskType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3611930807224519669L;
	
	
	private String	orderSn	;//	订单编号
	private String	informationSn	;//	订单明细表编号
	private String	recognizeeSn	;//	被保人编号
	private String	applicantSn	;//	投保人编码
	private String	policyNo	;//	保单号
	private String	applyPolicyNo	;//	投保单号
	private String	riskCode	;//	险种编码
	private String	riskName	;//	险种名称
	private String	amnt	;//	保险金额
	private String	timePrem	;//	保费
	private String	mult	;//	份数
	private Date	svaliDate	;//	生效日期
	private Date	evaliDate	;//	失效日期
	private String	periodFlag	;//	交费年期/年龄标志
	private String	period	;//	交费年期/年龄
	private String	electronicCout	;//	电子保单保险公司路径
	private String	electronicPath	;//	电子保单物理路径
	private String	insurerFlag	;//	保险公司返回结果标记
	private String	insureMsg	;//	保险公司返回描述
	private String	insureDate	;//	保险公司调用时间
	private String	balanceStatus	;//	结算状态
	private String	balanceFlag	;//	结算标记
	private String	balanceMsg	;//	结算描述
	private String	balanceDate	;//	结算时间
	private String	appStatus	;//	承保标记	
	private String	changeStatus;//	变更标记
	private String	noticeNo	;//	财务通知单号	
	private String	validateCode	;//	保单验证码	
	private String	insuranceTransCode	;//	保险公司返回交易流水号	
	private String	insuranceBankCode	;//	保险公司返回银行编码	
	private String	insuranceBankSeriNO	;//	保险公司返回银行交易流水号	
	private String	insuranceBRNO	;//	保险公司返回系列号	
	private String	insuranceTELLERNO	;//	保险公司反回商户号	
    private String  productPrice;//原价
    private SDOrder sdorder;
	private SDInformationInsured sdinformationinsured;
	private String couponValue;// 保单优惠券优惠金额
	private String integralValue;// 保单积分抵值金额
	private String activityValue;// 保单活动优惠金额
	private String payPrice;// 保单支付金额
	private String policyNoOld;// 原保单号
	
	@OneToOne(fetch = FetchType.LAZY)
	public SDInformationInsured getSdinformationinsured() {
		return sdinformationinsured;
	}
	public void setSdinformationinsured(SDInformationInsured sdinformationinsured) {
		this.sdinformationinsured = sdinformationinsured;
	}
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDOrder getSdorder() {
		return sdorder;
	}
	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getRecognizeeSn() {
		return recognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
	}
	public String getApplicantSn() {
		return applicantSn;
	}
	public void setApplicantSn(String applicantSn) {
		this.applicantSn = applicantSn;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getApplyPolicyNo() {
		return applyPolicyNo;
	}
	public void setApplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getAmnt() {
		return amnt;
	}
	public void setAmnt(String amnt) {
		this.amnt = amnt;
	}
	public String getTimePrem() {
		return timePrem;
	}
	public void setTimePrem(String timePrem) {
		this.timePrem = timePrem;
	}
	public String getMult() {
		return mult;
	}
	public void setMult(String mult) {
		this.mult = mult;
	}
	
	public Date getSvaliDate() {
		return svaliDate;
	}
	public void setSvaliDate(Date svaliDate) {
		this.svaliDate = svaliDate;
	}
	public Date getEvaliDate() {
		return evaliDate;
	}
	public void setEvaliDate(Date evaliDate) {
		this.evaliDate = evaliDate;
	}
	public String getPeriodFlag() {
		return periodFlag;
	}
	public void setPeriodFlag(String periodFlag) {
		this.periodFlag = periodFlag;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getElectronicCout() {
		return electronicCout;
	}
	public void setElectronicCout(String electronicCout) {
		this.electronicCout = electronicCout;
	}
	public String getElectronicPath() {
		return electronicPath;
	}
	public void setElectronicPath(String electronicPath) {
		this.electronicPath = electronicPath;
	}
	public String getInsurerFlag() {
		return insurerFlag;
	}
	public void setInsurerFlag(String insurerFlag) {
		this.insurerFlag = insurerFlag;
	}
	public String getInsureMsg() {
		return insureMsg;
	}
	public void setInsureMsg(String insureMsg) {
		this.insureMsg = insureMsg;
	}
	public String getInsureDate() {
		return insureDate;
	}
	public void setInsureDate(String insureDate) {
		this.insureDate = insureDate;
	}
	public String getBalanceStatus() {
		return balanceStatus;
	}
	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}
	public String getBalanceFlag() {
		return balanceFlag;
	}
	public void setBalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
	}
	public String getBalanceMsg() {
		return balanceMsg;
	}
	public void setBalanceMsg(String balanceMsg) {
		this.balanceMsg = balanceMsg;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}
	public String getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getInsuranceTransCode() {
		return insuranceTransCode;
	}
	public void setInsuranceTransCode(String insuranceTransCode) {
		this.insuranceTransCode = insuranceTransCode;
	}
	public String getInsuranceBankCode() {
		return insuranceBankCode;
	}
	public void setInsuranceBankCode(String insuranceBankCode) {
		this.insuranceBankCode = insuranceBankCode;
	}
	public String getInsuranceBankSeriNO() {
		return insuranceBankSeriNO;
	}
	public void setInsuranceBankSeriNO(String insuranceBankSeriNO) {
		this.insuranceBankSeriNO = insuranceBankSeriNO;
	}
	public String getInsuranceBRNO() {
		return insuranceBRNO;
	}
	public void setInsuranceBRNO(String insuranceBRNO) {
		this.insuranceBRNO = insuranceBRNO;
	}
	public String getInsuranceTELLERNO() {
		return insuranceTELLERNO;
	}
	public void setInsuranceTELLERNO(String insuranceTELLERNO) {
		this.insuranceTELLERNO = insuranceTELLERNO;
	}
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}
	public String getIntegralValue() {
		return integralValue;
	}
	public void setIntegralValue(String integralValue) {
		this.integralValue = integralValue;
	}
	public String getActivityValue() {
		return activityValue;
	}
	public void setActivityValue(String activityValue) {
		this.activityValue = activityValue;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getPolicyNoOld() {
		return policyNoOld;
	}
	public void setPolicyNoOld(String policyNoOld) {
		this.policyNoOld = policyNoOld;
	}
}