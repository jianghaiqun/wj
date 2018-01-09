package cn.com.sinosoft.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * 实体类：险种信息
 */



@Entity
public class InformationRiskType extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationInsured informationInsured;// 基本信息
	
	private String insuredName;// 被保人姓名
	private String riskCode;//险种编码
	private String riskName;//险种名称


	private String amnt;//保险金额
	private String timePrem;//保费
	private String mult;//份数
	private Date cValiDate;//生效日期
	private Date eValiDate;//失效日期
	private String periodFlag;//交费年期/年龄标志
	private String period;//交费年期/年龄
	
	/**
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return insuredName;
	}
	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	/**
	 * @return the riskName
	 */
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	/**
	 * @param riskName the riskName to set
	 */
	/**
	 * @return the riskCode
	 */
	public String getRiskCode() {
		return riskCode;
	}
	/**
	 * @param riskCode the riskCode to set
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * @return the eValiDate
	 */
	public Date geteValiDate() {
		return eValiDate;
	}
	/**
	 * @param eValiDate the eValiDate to set
	 */
	public void seteValiDate(Date eValiDate) {
		this.eValiDate = eValiDate;
	}
	/**
	 * @return the amnt
	 */
	public String getAmnt() {
		return amnt;
	}
	/**
	 * @param amnt the amnt to set
	 */
	public void setAmnt(String amnt) {
		this.amnt = amnt;
	}
	/**
	 * @return the timePrem
	 */
	public String getTimePrem() {
		return timePrem;
	}
	/**
	 * @param timePrem the timePrem to set
	 */
	public void setTimePrem(String timePrem) {
		this.timePrem = timePrem;
	}
	/**
	 * @return the mult
	 */
	public String getMult() {
		return mult;
	}
	/**
	 * @param mult the mult to set
	 */
	public void setMult(String mult) {
		this.mult = mult;
	}
	/**
	 * @return the cValiDate
	 */
	public Date getcValiDate() {
		return cValiDate;
	}
	/**
	 * @param cValiDate the cValiDate to set
	 */
	public void setcValiDate(Date cValiDate) {
		this.cValiDate = cValiDate;
	}
	/**
	 * @return the periodFlag
	 */
	public String getPeriodFlag() {
		return periodFlag;
	}
	/**
	 * @param periodFlag the periodFlag to set
	 */
	public void setPeriodFlag(String periodFlag) {
		this.periodFlag = periodFlag;
	}
	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationInsured getInformationInsured() {
		return informationInsured;
	}
	public void setInformationInsured(InformationInsured informationInsured) {
		this.informationInsured = informationInsured;
	}
	
}