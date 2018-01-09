package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class SDHealthyInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7567403833631815406L;

	private String	OrderSn	;//	订单编号	
	private String	InformationSn	;//	订单详细表编码	
	private String	RecognizeeSn	;//	被保人编号	
	private String	ProductId	;//	产品编码	
	private String	InsuranceCompany	;//	保险公司	
	private String	ShowOrder	;//	信息序号	
	private String	ShowInfo	;//	信息描述	
	private String	UIInfo	;//	界面显示信息	
	private String	showDistrict	;//	显示区域	
	private String	IsMustInput	;//	是否必填	
	private String	IsDisplay	;//	是否显示	
	private String	DataType	;//	数据类型表	
	private String	ShowInfoType	;//	健康告知类型	
	private String	ShowInfoCode	;//	健康告知编码	
	private String	BackUp1	;//	同个健康告知类型下的数量总和	
	private String	SelectFlag	;//	选择标志	
	private String	TypeShowOrder	;//	类型序号	
	private String	ValueTypeFlag	;//	是否有关联下级健康信息	
	private String	HealthyInfo	;//	子集健康信息	
	private String	HealthyInfoNum	;//	需要填写值的数量	
	private String	ChildInfoValue	;//	子集健康告知值	
	public String getOrderSn() {
		return OrderSn;
	}
	public void setOrderSn(String orderSn) {
		OrderSn = orderSn;
	}
	public String getInformationSn() {
		return InformationSn;
	}
	public void setInformationSn(String informationSn) {
		InformationSn = informationSn;
	}
	public String getRecognizeeSn() {
		return RecognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		RecognizeeSn = recognizeeSn;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getInsuranceCompany() {
		return InsuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		InsuranceCompany = insuranceCompany;
	}
	public String getShowOrder() {
		return ShowOrder;
	}
	public void setShowOrder(String showOrder) {
		ShowOrder = showOrder;
	}
	public String getShowInfo() {
		return ShowInfo;
	}
	public void setShowInfo(String showInfo) {
		ShowInfo = showInfo;
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
		return DataType;
	}
	public void setDataType(String dataType) {
		DataType = dataType;
	}
	public String getShowInfoType() {
		return ShowInfoType;
	}
	public void setShowInfoType(String showInfoType) {
		ShowInfoType = showInfoType;
	}
	public String getShowInfoCode() {
		return ShowInfoCode;
	}
	public void setShowInfoCode(String showInfoCode) {
		ShowInfoCode = showInfoCode;
	}
	public String getBackUp1() {
		return BackUp1;
	}
	public void setBackUp1(String backUp1) {
		BackUp1 = backUp1;
	}
	public String getSelectFlag() {
		return SelectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		SelectFlag = selectFlag;
	}
	public String getTypeShowOrder() {
		return TypeShowOrder;
	}
	public void setTypeShowOrder(String typeShowOrder) {
		TypeShowOrder = typeShowOrder;
	}
	public String getValueTypeFlag() {
		return ValueTypeFlag;
	}
	public void setValueTypeFlag(String valueTypeFlag) {
		ValueTypeFlag = valueTypeFlag;
	}
	public String getHealthyInfo() {
		return HealthyInfo;
	}
	public void setHealthyInfo(String healthyInfo) {
		HealthyInfo = healthyInfo;
	}
	public String getHealthyInfoNum() {
		return HealthyInfoNum;
	}
	public void setHealthyInfoNum(String healthyInfoNum) {
		HealthyInfoNum = healthyInfoNum;
	}
	public String getChildInfoValue() {
		return ChildInfoValue;
	}
	public void setChildInfoValue(String childInfoValue) {
		ChildInfoValue = childInfoValue;
	}

	
	

}
