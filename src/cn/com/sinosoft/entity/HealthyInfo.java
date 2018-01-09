package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class HealthyInfo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4670136705875002184L;
	private String productId; 
	private String insuranceCompany;//保险公司
	private Integer showOrder;//信息序号   
	private String showInfo;//信息描述
	private String UIInfo;//界面显示信息
	private String dataType;//数据类型表
	private String IsDisplay;//是否显示
	private String IsMustInput;//是否必填
	private String showDistrict;//显示区域
	private String showInfoType;//健康告知类型
	private String showInfoCode;//健康告知编码
	private String BackUp1;//同个健康告知类型下的数量总和
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public Integer getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	public String getShowInfo() {
		return showInfo;
	}
	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}
	public String getUIInfo() {
		return UIInfo;
	}
	public void setUIInfo(String uIInfo) {
		UIInfo = uIInfo;
	}
	public String getShowDistrict() {
		return showDistrict;
	}
	public void setShowDistrict(String showDistrict) {
		this.showDistrict = showDistrict;
	}
	public String getIsMustInput() {
		return IsMustInput;
	}
	public void setIsMustInput(String isMustInput) {
		IsMustInput = isMustInput;
	}
	public String getIsDisplay() {
		return IsDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		IsDisplay = isDisplay;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getShowInfoType() {
		return showInfoType;
	}
	public void setShowInfoType(String showInfoType) {
		this.showInfoType = showInfoType;
	}
	public String getShowInfoCode() {
		return showInfoCode;
	}
	public void setShowInfoCode(String showInfoCode) {
		this.showInfoCode = showInfoCode;
	}
	public String getBackUp1() {
		return BackUp1;
	}
	public void setBackUp1(String backUp1) {
		BackUp1 = backUp1;
	}

}
