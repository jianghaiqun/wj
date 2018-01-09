package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
@Entity
public class SDInsuredHealth extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5759444889543177378L;
	
	private String	orderSn	;//	订单编号
	private String	informationSn	;//	订单详细表编码
	private String	recognizeeSn	;//	被保人编号
	private String	productId	;//	产品编码
	private String	insuranceCompany	;//	保险公司
	private String	showOrder	;//	信息序号
	private String	showInfo	;//	信息描述
	private String	uiInfo	;//	界面显示信息
	private String	showDistrict	;//	显示区域
	private String	isMustInput	;//	是否必填
	private String	isDisplay	;//	是否显示
	private String	dataType	;//	数据类型表
	private String	showInfoType	;//	健康告知类型
	private String	showInfoCode	;//	健康告知编码
	private String	backUp1	;//	同个健康告知类型下的数量总和
	private String	selectFlag	;//	选择标志
	private String	typeShowOrder	;//	类型序号
	private String	valueTypeFlag	;//	是否有关联下级健康信息
	private String	healthyInfo	;//	子集健康信息
	private String	healthyInfoNum	;//	需要填写值的数量
	private String	childInfoValue	;//	子集健康告知值
    private String  healthyInfoId;//关联同步健康告知id
	
	private SDInformationInsured sdinformationinsured;
	
	
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformationInsured getSdinformationinsured() {
		return sdinformationinsured;
	}
	public void setSdinformationinsured(SDInformationInsured sdinformationinsured) {
		this.sdinformationinsured = sdinformationinsured;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getRecognizeeSn() {
		return recognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
	}
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
	public String getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}
	public String getShowInfo() {
		return showInfo;
	}
	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}
	public String getUiInfo() {
		return uiInfo;
	}
	public void setUiInfo(String uiInfo) {
		this.uiInfo = uiInfo;
	}
	public String getShowDistrict() {
		return showDistrict;
	}
	public void setShowDistrict(String showDistrict) {
		this.showDistrict = showDistrict;
	}
	public String getIsMustInput() {
		return isMustInput;
	}
	public void setIsMustInput(String isMustInput) {
		this.isMustInput = isMustInput;
	}
	public String getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
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
		return backUp1;
	}
	public void setBackUp1(String backUp1) {
		this.backUp1 = backUp1;
	}
	public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getTypeShowOrder() {
		return typeShowOrder;
	}
	public void setTypeShowOrder(String typeShowOrder) {
		this.typeShowOrder = typeShowOrder;
	}
	public String getValueTypeFlag() {
		return valueTypeFlag;
	}
	public void setValueTypeFlag(String valueTypeFlag) {
		this.valueTypeFlag = valueTypeFlag;
	}
	public String getHealthyInfo() {
		return healthyInfo;
	}
	public void setHealthyInfo(String healthyInfo) {
		this.healthyInfo = healthyInfo;
	}
	public String getHealthyInfoNum() {
		return healthyInfoNum;
	}
	public void setHealthyInfoNum(String healthyInfoNum) {
		this.healthyInfoNum = healthyInfoNum;
	}
	public String getChildInfoValue() {
		return childInfoValue;
	}
	public void setChildInfoValue(String childInfoValue) {
		this.childInfoValue = childInfoValue;
	}
	public String getHealthyInfoId() {
		return healthyInfoId;
	}
	public void setHealthyInfoId(String healthyInfoId) {
		this.healthyInfoId = healthyInfoId;
	}
	
	
}
