package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class InsuredCompanyReturnData extends BaseEntity {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8587903550605963554L;
	private String applyPolicyNo;// 投保单号
	private String policyNo;// 保单号
	private String noticeNo;// 财务通知单号
	private String validateCode;// 保单验证码
	private String totalPremium;// 保单保费
	private String orderSn;// 订单号
	private String tradeSeriNO;// 支付银行或平台返回的交易的流水号
	private String appStatus;// 投保状态
	private String insuranceTransCode;//保险公司返回交易流水号
	private String insuranceBankCode;//保险公司返回银行编码
	private String insuranceBankSeriNO;//保险公司返回银行流水号
	private String insuranceAreaCode;//保险公司返回地区编码
	private String insuranceACCTDate;//保险公司返回投保日期
	private String insuranceACCTTime;//保险公司返回投保时间
    private String insuranceResultCode;//保险公司返回投保结果编码
	private String insuranceResultMsg;//保险公司返回投保结果信息
	private String currentDate;//当前日期
	private String insuranceCode;//保险公司编码
	private String insuranceBRNO;//保险公司返回银行系列号
	private String insuranceTELLERNO;//保险公司返回商户号
	private String settleMgr;//结算中心返回结果信息
	private String remark;//备注
	private String settleState;//是否与结算中心发生交互标记
	private String settleStatus;//结算中心返回结果状态
	private String policyPath;//保险公司保单下载路径
	private String remark1;//备注1
	private String remark2;//备注2
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public String getInsuranceACCTTime() {
		return insuranceACCTTime;
	}
	public void setInsuranceACCTTime(String insuranceACCTTime) {
		this.insuranceACCTTime = insuranceACCTTime;
	}
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
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
	public String getApplyPolicyNo() {
		return applyPolicyNo;
	}
	public void setApplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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
	public String getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getTradeSeriNO() {
		return tradeSeriNO;
	}
	public void setTradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
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
	public String getInsuranceAreaCode() {
		return insuranceAreaCode;
	}
	public void setInsuranceAreaCode(String insuranceAreaCode) {
		this.insuranceAreaCode = insuranceAreaCode;
	}
	public String getInsuranceACCTDate() {
		return insuranceACCTDate;
	}
	public void setInsuranceACCTDate(String insuranceACCTDate) {
		this.insuranceACCTDate = insuranceACCTDate;
	}
	public String getInsuranceResultCode() {
		return insuranceResultCode;
	}
	public void setInsuranceResultCode(String insuranceResultCode) {
		this.insuranceResultCode = insuranceResultCode;
	}
	public String getInsuranceResultMsg() {
		return insuranceResultMsg;
	}
	public void setInsuranceResultMsg(String insuranceResultMsg) {
		this.insuranceResultMsg = insuranceResultMsg;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getSettleMgr() {
		return settleMgr;
	}
	public void setSettleMgr(String settleMgr) {
		this.settleMgr = settleMgr;
	}
	public String getPolicyPath() {
		return policyPath;
	}
	public void setPolicyPath(String policyPath) {
		this.policyPath = policyPath;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
}
