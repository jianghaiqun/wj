package cn.com.sinosoft.entity;

import java.io.Serializable;

public class RiskAppFactor implements Serializable {
	private static final long serialVersionUID = -7211642868351278663L;
	private String productCode;
	private String factorType;
	private String factorValue;
	private String prem;
	private String amnt;
	private String dutyCode;
	private String productInt;
	private String discountPrem;//折扣价格
	/**
	 * 1=产品投保要素，2=责任投保要素。
	 */
	private String flag;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getFactorType() {
		return factorType;
	}

	public void setFactorType(String factorType) {
		this.factorType = factorType;
	}

	public String getFactorValue() {
		return factorValue;
	}

	public void setFactorValue(String factorValue) {
		this.factorValue = factorValue;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAmnt() {
		return amnt;
	}

	public void setAmnt(String amnt) {
		this.amnt = amnt;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getDiscountPrem() {
		return discountPrem;
	}

	public void setDiscountPrem(String discountPrem) {
		this.discountPrem = discountPrem;
	}

	public String getProductInt() {
		return productInt;
	}

	public void setProductInt(String productInt) {
		this.productInt = productInt;
	}

}
