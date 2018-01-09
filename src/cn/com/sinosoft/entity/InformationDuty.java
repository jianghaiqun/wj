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
public class InformationDuty extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private Information information;// 被保人
	private String dutySn;//责任编码
	private String dutyName;//责任名称
	private String siskCode;//险种编码
	private String timePrem;// 保费
	private String showAmnt;//显示值
	private String amnt;//计算值保额
	private String coverage;//保障内容
	private String orderId;//订单号
	private String mainRiskFlag;//是否主险标志
	private String supplierDutyCode;//保险公司责任/险别编码
	private String dutyFullName;//责任名称全称
	
	public String getDutyFullName() {
		return dutyFullName;
	}
	public void setDutyFullName(String dutyFullName) {
		this.dutyFullName = dutyFullName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDutySn() {
		return dutySn;
	}
	public void setDutySn(String dutySn) {
		this.dutySn = dutySn;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getSiskCode() {
		return siskCode;
	}
	public void setSiskCode(String siskCode) {
		this.siskCode = siskCode;
	}
	public String getTimePrem() {
		return timePrem;
	}
	public void setTimePrem(String timePrem) {
		this.timePrem = timePrem;
	}
	public String getAmnt() {
		return amnt;
	}
	public void setAmnt(String amnt) {
		this.amnt = amnt;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Information getInformation() {
		return information;
	}
	public void setInformation(Information information) {
		this.information = information;
	}
	public String getShowAmnt() {
		return showAmnt;
	}
	public void setShowAmnt(String showAmnt) {
		this.showAmnt = showAmnt;
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
	
}