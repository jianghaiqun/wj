package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class Reservation extends BaseEntity {

	private static final long serialVersionUID = -8369091246417542914L;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 客户手机号码
	 */
	private String customerTel;
	/**
	 * 客户省级地区
	 */
	private String customerAreas1;
	/**
	 * 客户市级地区
	 */
	private String customerAreas2;
	/**
	 * 客户地区显示
	 */
	private String areaShow;
	/**
	 * 预约产品名称
	 */
	private String productName;
	/**
	 * 预约标志
	 * N=未处理；Y=已处理；
	 */
	private String flag;
	/**
	 * 团险预约标志
	 * Y=团险；
	 */
	private String isTeam;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public String getCustomerAreas1() {
		return customerAreas1;
	}

	public void setCustomerAreas1(String customerAreas1) {
		this.customerAreas1 = customerAreas1;
	}

	public String getCustomerAreas2() {
		return customerAreas2;
	}

	public void setCustomerAreas2(String customerAreas2) {
		this.customerAreas2 = customerAreas2;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAreaShow() {
		return areaShow;
	}

	public void setAreaShow(String areaShow) {
		this.areaShow = areaShow;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getIsTeam() {
		return isTeam;
	}

	public void setIsTeam(String isTeam) {
		this.isTeam = isTeam;
	}

}
