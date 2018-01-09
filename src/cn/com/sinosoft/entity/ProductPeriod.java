package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class ProductPeriod  extends BaseEntity{

	private static final long serialVersionUID = -8286334608165507435L;
	/**
	 * 保险公司编码
	 */
	private String comCode;
	/**
	 * 产品编码
	 */
	private String riskCode;
	/**
	 * 起始日期与当前日期差
	 */
	private String startPeriod;
	/**
	 * 标志位
	 */
	private String flag;
	/**
	 * 起始日期与当前日期差
	 */
	private String endPeriod;
	
	public String getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
