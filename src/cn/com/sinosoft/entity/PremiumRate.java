/**
 * @CopyRight:Sinosoft
 * @File:PremiumRate.java
 * @Package:cn.com.sinosoft.entity
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author LiuXin
 * 
 */
@Entity
public class PremiumRate extends BaseEntity  {

	private static final long serialVersionUID = -8708763218338645918L;
	/**
	 * 险别编码
	 */
	@Column(name = "RISKCODE", length = 30)
	private String riskCode;
	/**
	 * 地区编码
	 */
	@Column(name = "REGIONCODE", length = 30)
	private String regionCode;
	/**
	 * 车型代码
	 */
	@Column(name = "MODELSCODE", length = 30)
	private String modelsCode;
	/**
	 * 载重下限
	 */
	@Column(name = "LOADLOWER", precision = 4, length = 15)
	private double loadLower;
	/**
	 * 载重上限
	 */
	@Column(name = "LOADUPPER", precision = 4, length = 15)
	private double loadUpper;
	/**
	 * 乘客下限
	 */
	@Column(name = "PASSENGERLOWER", precision = 4, length = 15)
	private double passengerLower;
	/**
	 * 乘客上限
	 */
	@Column(name = "PASSERNGERUPPER", precision = 4, length = 15)
	private double passerngerUpper;
	/**
	 * 功率下限
	 */
	@Column(name = "POWERLOWER", precision = 4, length = 15)
	private double powerLower;
	/**
	 * 功率上限
	 */
	@Column(name = "POWERUPPER", precision = 4, length = 15)
	private double powerUpper;
	/**
	 * 基础保费
	 */
	@Column(name = "PREMIUM", precision = 4, length = 15)
	private double premium;
	/**
	 * 基础费率
	 */
	@Column(name = "RATE", precision = 4, length = 15)
	private double rate;
	/**
	 * 保额
	 */
	@Column(name = "SUMASSURED", precision = 4, length = 15)
	private double sumassured;
	/**
	 * 车龄下限
	 */
	@Column(name = "AGELOWER", precision = 4, length = 15)
	private double ageLower;
	/**
	 * 车龄上限
	 */
	@Column(name = "AGEUPPER", precision = 4, length = 15)
	private double ageUpper;
	/**
	 * 费率类型
	 */
	@Column(name = "RATETYPE", length = 20)
	private String rateType;
	/**
	 * 新车购置价下限
	 */
	@Column(name = "PRICELOWER", precision = 4, length = 15)
	private double priceLower;
	/**
	 * 新车购置价上限
	 */
	@Column(name = "PRICEUPPER", precision = 4, length = 15)
	private double priceUpper;
	/**
	 * 模板编码
	 */
	@Column(name = "MODELTYPE", length = 30)
	private String modelType;
	/**
	 * 所属性质编码
	 */
	@Column(name = "PERTAINCODE", length = 30)
	private String pertainCode;
	/**
	 * 使用性质编码
	 */
	@Column(name = "USEDCODE", length = 30)
	private String usedCode;
	/**
	 * 适用险别编码
	 */
	@Column(name = "APPLYRISKCODE", length = 30)
	private String applyRiskCode;


	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getModelsCode() {
		return modelsCode;
	}

	public void setModelsCode(String modelsCode) {
		this.modelsCode = modelsCode;
	}

	public double getLoadLower() {
		return loadLower;
	}

	public void setLoadLower(double loadLower) {
		this.loadLower = loadLower;
	}

	public double getLoadUpper() {
		return loadUpper;
	}

	public void setLoadUpper(double loadUpper) {
		this.loadUpper = loadUpper;
	}

	public double getPassengerLower() {
		return passengerLower;
	}

	public void setPassengerLower(double passengerLower) {
		this.passengerLower = passengerLower;
	}

	public double getPasserngerUpper() {
		return passerngerUpper;
	}

	public void setPasserngerUpper(double passerngerUpper) {
		this.passerngerUpper = passerngerUpper;
	}

	public double getPowerLower() {
		return powerLower;
	}

	public void setPowerLower(double powerLower) {
		this.powerLower = powerLower;
	}

	public double getPowerUpper() {
		return powerUpper;
	}

	public void setPowerUpper(double powerUpper) {
		this.powerUpper = powerUpper;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getSumassured() {
		return sumassured;
	}

	public void setSumassured(double sumassured) {
		this.sumassured = sumassured;
	}

	public double getAgeLower() {
		return ageLower;
	}

	public void setAgeLower(double ageLower) {
		this.ageLower = ageLower;
	}

	public double getAgeUpper() {
		return ageUpper;
	}

	public void setAgeUpper(double ageUpper) {
		this.ageUpper = ageUpper;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public double getPriceLower() {
		return priceLower;
	}

	public void setPriceLower(double priceLower) {
		this.priceLower = priceLower;
	}

	public double getPriceUpper() {
		return priceUpper;
	}

	public void setPriceUpper(double priceUpper) {
		this.priceUpper = priceUpper;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getPertainCode() {
		return pertainCode;
	}

	public void setPertainCode(String pertainCode) {
		this.pertainCode = pertainCode;
	}

	public String getUsedCode() {
		return usedCode;
	}

	public void setUsedCode(String usedCode) {
		this.usedCode = usedCode;
	}

	public String getApplyRiskCode() {
		return applyRiskCode;
	}

	public void setApplyRiskCode(String applyRiskCode) {
		this.applyRiskCode = applyRiskCode;
	}

}
