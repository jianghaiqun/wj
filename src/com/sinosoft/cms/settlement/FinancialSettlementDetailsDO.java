package com.sinosoft.cms.settlement;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务结算明细查询。 Created by dongsheng on 2017/5/26.
 */
public class FinancialSettlementDetailsDO implements Serializable {

	// 保单号码
	private String policyNo;
	// 保监险种类别
	private String insuranceType;
	// 保监类别细分类
	private String insuranceSubType;
	// 一级渠道
	private String channel;
	// 二级渠道
	private String subChannel;
	// 渠道归属
	private String channelType;
	// 生效日期起期
	private Date svaliDate;
	// 生效日期止期
	private Date evaliDate;
	// 承保日期
	private Date insureDate;
	// 保单状态
	private String appStatus;
	// 新单保费
	private Double Premium1st;
	// 续期保费
	private Double Premium2nd;
	// 新单手续费比率
	private String feeRatio1st;
	// 续期手续费比率
	private String feeRatio2nd;
	// 新单手续费金额
	private Double fee1st;
	// 续期手续费金额
	private Double fee2nd;
	// 保费批次号
	private String premiumBatchNumber;
	// 手续费批次号
	private String feeBatchNumber;
	// 保险公司名称
	private String insuranceCompanyName;
	// 保险公司性质
	private String insuranceCompanyType;
	// 开票日期
	private Date invoiceDate;
	// 机构编码
	private String branchCode;

    @Override
    public String toString() {
        return "FinancialSettlementDetailsDO{" +
                "policyNo='" + policyNo + '\'' +
                ", insuranceType='" + insuranceType + '\'' +
                ", insuranceSubType='" + insuranceSubType + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", channelType='" + channelType + '\'' +
                ", svaliDate=" + svaliDate +
                ", evaliDate=" + evaliDate +
                ", insureDate=" + insureDate +
                ", appStatus='" + appStatus + '\'' +
                ", Premium1st=" + Premium1st +
                ", Premium2nd=" + Premium2nd +
                ", feeRatio1st='" + feeRatio1st + '\'' +
                ", feeRatio2nd='" + feeRatio2nd + '\'' +
                ", fee1st=" + fee1st +
                ", fee2nd=" + fee2nd +
                ", premiumBatchNumber='" + premiumBatchNumber + '\'' +
                ", feeBatchNumber='" + feeBatchNumber + '\'' +
                ", insuranceCompanyName='" + insuranceCompanyName + '\'' +
                ", insuranceCompanyType='" + insuranceCompanyType + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", branchCode='" + branchCode + '\'' +
                '}';
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public FinancialSettlementDetailsDO setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
        return this;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public FinancialSettlementDetailsDO setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
        return this;
    }

    public String getInsuranceSubType() {
        return insuranceSubType;
    }

    public FinancialSettlementDetailsDO setInsuranceSubType(String insuranceSubType) {
        this.insuranceSubType = insuranceSubType;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public FinancialSettlementDetailsDO setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public FinancialSettlementDetailsDO setSubChannel(String subChannel) {
        this.subChannel = subChannel;
        return this;
    }

    public String getChannelType() {
        return channelType;
    }

    public FinancialSettlementDetailsDO setChannelType(String channelType) {
        this.channelType = channelType;
        return this;
    }

    public Date getSvaliDate() {
        return svaliDate;
    }

    public FinancialSettlementDetailsDO setSvaliDate(Date svaliDate) {
        this.svaliDate = svaliDate;
        return this;
    }

    public Date getEvaliDate() {
        return evaliDate;
    }

    public FinancialSettlementDetailsDO setEvaliDate(Date evaliDate) {
        this.evaliDate = evaliDate;
        return this;
    }

    public Date getInsureDate() {
        return insureDate;
    }

    public FinancialSettlementDetailsDO setInsureDate(Date insureDate) {
        this.insureDate = insureDate;
        return this;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public FinancialSettlementDetailsDO setAppStatus(String appStatus) {
        this.appStatus = appStatus;
        return this;
    }

    public Double getPremium1st() {
        return Premium1st;
    }

    public FinancialSettlementDetailsDO setPremium1st(Double premium1st) {
        Premium1st = premium1st;
        return this;
    }

    public Double getPremium2nd() {
        return Premium2nd;
    }

    public FinancialSettlementDetailsDO setPremium2nd(Double premium2nd) {
        Premium2nd = premium2nd;
        return this;
    }

    public String getFeeRatio1st() {
        return feeRatio1st;
    }

    public FinancialSettlementDetailsDO setFeeRatio1st(String feeRatio1st) {
        this.feeRatio1st = feeRatio1st;
        return this;
    }

    public String getFeeRatio2nd() {
        return feeRatio2nd;
    }

    public FinancialSettlementDetailsDO setFeeRatio2nd(String feeRatio2nd) {
        this.feeRatio2nd = feeRatio2nd;
        return this;
    }

    public Double getFee1st() {
        return fee1st;
    }

    public FinancialSettlementDetailsDO setFee1st(Double fee1st) {
        this.fee1st = fee1st;
        return this;
    }

    public Double getFee2nd() {
        return fee2nd;
    }

    public FinancialSettlementDetailsDO setFee2nd(Double fee2nd) {
        this.fee2nd = fee2nd;
        return this;
    }

    public String getPremiumBatchNumber() {
        return premiumBatchNumber;
    }

    public FinancialSettlementDetailsDO setPremiumBatchNumber(String premiumBatchNumber) {
        this.premiumBatchNumber = premiumBatchNumber;
        return this;
    }

    public String getFeeBatchNumber() {
        return feeBatchNumber;
    }

    public FinancialSettlementDetailsDO setFeeBatchNumber(String feeBatchNumber) {
        this.feeBatchNumber = feeBatchNumber;
        return this;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public FinancialSettlementDetailsDO setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
        return this;
    }

    public String getInsuranceCompanyType() {
        return insuranceCompanyType;
    }

    public FinancialSettlementDetailsDO setInsuranceCompanyType(String insuranceCompanyType) {
        this.insuranceCompanyType = insuranceCompanyType;
        return this;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public FinancialSettlementDetailsDO setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public FinancialSettlementDetailsDO setBranchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }
}
