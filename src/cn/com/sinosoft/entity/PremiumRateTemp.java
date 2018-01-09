/**
 * @CopyRight:Sinosoft
 * @File:PremiumRateTemp.java
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
public class PremiumRateTemp extends BaseEntity  {
	private static final long serialVersionUID = 1194238579897644887L;
	/**
	 * 险别编码
	 */
	@Column(name = "RISK_CODE", length = 30)
	private String riskCode;
	/**
	 * 地区编码
	 */
	@Column(name = "REGION_CODE", length = 30)
	private String regionCode;
	/**
	 * 车型代码
	 */
	@Column(name = "MODELS_CODE", length = 30)
	private String modelsCode;
	/**
	 * 载重下限
	 */
	@Column(name = "LOAD_LOWER", precision = 4, length = 15)
	private double loadLower;
	/**
	 * 载重下限
	 */
	@Column(name = "LOAD_UPPER", precision = 4, length = 15)
	private double loadUpper;
	/**
	 * 乘客下限
	 */
	@Column(name = "PASSENGER_LOWER", precision = 4, length = 15)
	private double passengerLower;
	/**
	 * 乘客下限
	 */
	@Column(name = "PASSERNGER_UPPER", precision = 4, length = 15)
	private double passerngerUpper;
	/**
	 * 功率下限
	 */
	@Column(name = "POWER_LOWER", precision = 4, length = 15)
	private double powerLower;
	/**
	 * 功率上限
	 */
	@Column(name = "POWER_UPPER", precision = 4, length = 15)
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
	@Column(name = "AGE_LOWER", precision = 4, length = 15)
	private double ageLower;
	/**
	 * 车龄上限
	 */
	@Column(name = "AGE_UPPER", precision = 4, length = 15)
	private double ageUpper;
	/**
	 * 费率类型
	 */
	@Column(name = "RATE_TYPE", length = 20)
	private String rateType;
	/**
	 * 新车购置价下限
	 */
	@Column(name = "PRICE_LOWER", precision = 4, length = 15)
	private double priceLower;
	/**
	 * 新车购置价上限
	 */
	@Column(name = "PRICE_UPPER", precision = 4, length = 15)
	private double priceUpper;
	/**
	 * 模板编码
	 */
	@Column(name = "MODELTYPE", length = 30)
	private String modelType;
	/**
	 * 所属性质编码
	 */
	@Column(name = "PERTAIN_CODE", length = 30)
	private String pertainCode;
	/**
	 * 使用性质编码
	 */
	@Column(name = "USED_CODE", length = 30)
	private String usedCode;
	/**
	 * 适用险别编码
	 */
	@Column(name = "APPLY_RISK_CODE", length = 30)
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
