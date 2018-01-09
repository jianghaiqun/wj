package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author LiuXin
 *
 */
@Entity
public class ShowInsurance extends BaseEntity{

	private static final long serialVersionUID = 5705695321761513921L;
	/**
	 * 流水号
	 */
	private String serialNumber;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 保险公司编码
	 */
	@Column(name = "COMCODE", length = 20)
	private String comCode;
	/**
	 * 经济型总保费
	 */
	@Column(name = "ECONOMICPREMIUM", precision = 2, length = 15)
	private Double economicPremium;
	/**
	 * 经济型最低标志
	 */
	@Column(name = "ECONOMICFLAG", length = 1)
	private String economicFlag;
	/**
	 * 标准型总保费
	 */
	@Column(name = "ORDINARYPREMIUM", precision = 2, length = 15)
	private Double ordinaryPremium;
	/**
	 * 标准型最低标志
	 */
	@Column(name = "ORDINARYFLAG", length = 1)
	private String ordinaryFlag;
	/**
	 * 豪华型总保费
	 */
	@Column(name = "LUXURYPREMIUM", precision = 2, length = 15)
	private Double luxuryPremium;
	/**
	 * 豪华型最低标志
	 */
	@Column(name = "LUXURYFLAG", length = 1)
	private String luxuryFlag;
	/**
	 * 自定义总保费
	 */
	private Double zdyPremium;
	/**
	 * 自定义最低标志
	 */
	private String zdyFlag;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Double getEconomicPremium() {
		return economicPremium;
	}
	public void setEconomicPremium(Double economicPremium) {
		this.economicPremium = economicPremium;
	}
	public String getEconomicFlag() {
		return economicFlag;
	}
	public void setEconomicFlag(String economicFlag) {
		this.economicFlag = economicFlag;
	}
	public Double getOrdinaryPremium() {
		return ordinaryPremium;
	}
	public void setOrdinaryPremium(Double ordinaryPremium) {
		this.ordinaryPremium = ordinaryPremium;
	}
	public String getOrdinaryFlag() {
		return ordinaryFlag;
	}
	public void setOrdinaryFlag(String ordinaryFlag) {
		this.ordinaryFlag = ordinaryFlag;
	}
	public Double getLuxuryPremium() {
		return luxuryPremium;
	}
	public void setLuxuryPremium(Double luxuryPremium) {
		this.luxuryPremium = luxuryPremium;
	}
	public String getLuxuryFlag() {
		return luxuryFlag;
	}
	public void setLuxuryFlag(String luxuryFlag) {
		this.luxuryFlag = luxuryFlag;
	}
	public Double getZdyPremium() {
		return zdyPremium;
	}
	public void setZdyPremium(Double zdyPremium) {
		this.zdyPremium = zdyPremium;
	}
	public String getZdyFlag() {
		return zdyFlag;
	}
	public void setZdyFlag(String zdyFlag) {
		this.zdyFlag = zdyFlag;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
