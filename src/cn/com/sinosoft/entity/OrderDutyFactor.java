package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;

public class OrderDutyFactor implements Serializable {

	private static final long serialVersionUID = 8008565257503829614L;
	private String productCode;
	/**
	 * 责任/险别代码
	 */
	private String dutyCode;  
	/**
	 * 产品责任/险别名称
	 */
	private String dudtyFactorName;  
	/**
	 * 保险公司责任全称
	 */
	private String dutyFullName;
	/**
	 * 责任投保要素ID
	 */
	private String dutyFactorID; 
	/**
	 * 责任/险别说明
	 */
	private String define; 
	/**
	 * 是否关联产品级投保要素
	 */
	private String isRelaRiskFactor;
	/**
	 * 投保要素是否用于保费计算
	 */
	private String isPremCalFacotor;
	/**
	 * 责任投保要素值数组
	 */
	private List<FEMDutyAmntPremList> fdAmntPremList;
	/**
	 * 关联的产品投保要素编码
	 */
	private String appFactorCode;
	/**
	 * 值类型
	 */
	private String dataType;
	/**
	 * 主副险标志
	 */
	private String currency;
	/**
	 * 保险公司责任/险别编码
	 */
	private String supplierDutyCode;
	/**
	 * 责任英文名
	 */
	private String dutyEnName;
	/**
	 * 责任英文说明
	 */
	private String EnCoverage;
	/**
	 * 排序
	 */
	private String orderFlag;

	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getIsRelaRiskFactor() {
		return isRelaRiskFactor;
	}
	public void setIsRelaRiskFactor(String isRelaRiskFactor) {
		this.isRelaRiskFactor = isRelaRiskFactor;
	}
	public String getIsPremCalFacotor() {
		return isPremCalFacotor;
	}
	public void setIsPremCalFacotor(String isPremCalFacotor) {
		this.isPremCalFacotor = isPremCalFacotor;
	}
	public String getAppFactorCode() {
		return appFactorCode;
	}
	public void setAppFactorCode(String appFactorCode) {
		this.appFactorCode = appFactorCode;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public List<FEMDutyAmntPremList> getFdAmntPremList() {
		return fdAmntPremList;
	}
	public void setFdAmntPremList(List<FEMDutyAmntPremList> fdAmntPremList) {
		this.fdAmntPremList = fdAmntPremList;
	}
	public String getDudtyFactorName() {
		return dudtyFactorName;
	}
	public void setDudtyFactorName(String dudtyFactorName) {
		this.dudtyFactorName = dudtyFactorName;
	}
	public String getDefine() {
		return define;
	}
	public void setDefine(String define) {
		this.define = define;
	}
	public String getSupplierDutyCode() {
		return supplierDutyCode;
	}
	public void setSupplierDutyCode(String supplierDutyCode) {
		this.supplierDutyCode = supplierDutyCode;
	}
	public String getDutyFactorID() {
		return dutyFactorID;
	}
	public void setDutyFactorID(String dutyFactorID) {
		this.dutyFactorID = dutyFactorID;
	}
	public String getDutyFullName() {
		return dutyFullName;
	}
	public void setDutyFullName(String dutyFullName) {
		this.dutyFullName = dutyFullName;
	}
	public String getDutyEnName() {
		return dutyEnName;
	}
	public void setDutyEnName(String dutyEnName) {
		this.dutyEnName = dutyEnName;
	}
	public String getEnCoverage() {
		return EnCoverage;
	}
	public void setEnCoverage(String enCoverage) {
		EnCoverage = enCoverage;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	
}
