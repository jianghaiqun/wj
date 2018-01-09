package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 
 * 实体类：受益人信息 关联SDInformation
 *
 */



@Entity
public class SDInformationBnf extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7018777079396123537L;
	
	private String	informationSn2	;//	订单明细表编号
	private String	orderSn	;//	订单编号
	private String	recognizeeSn	;//	被保人编号
	private String	informationSn	;//	订单详细表编码
	private String	bnfNo	;//	受益人序号
	private String	bnfName	;//	客户姓名
	private String	bnfSex	;//	客户性别
	private String	bnfSexName	;//	客户性别名称
	private String	bnfBirthday	;//	客户出生日期
	private String	bnfIDType	;//	证件类型
	private String	bnfIDTypeName	;//	证件类型名称
	private String	bnfIDNo	;//	证件号码
	private String	beneWay	;//	受益方式
	private String	benePer	;//	受益比例
	private String	bnfType	;//	受益人类别
	private String	relationToInsured	;//	与被保人关系
	private String	relationToInsuredName	;//	与被保人关系名称
	private String	bnfLot	;//	受益份额
	private String	bankCode	;//	银行编码
	private String	bankAccNo	;//	银行帐号
	private String	accName	;//	银行户名
	private String	postalAddress	;//	联系地址
	private String	zipCode	;//	邮编
	private String	phone	;//	固定电话
	private String	mobile	;//	手机
	private String	email	;//	电子邮件
	private String	occupation	;//	受益人职业
	private String	bnfStartID	;//	证件生效起期
	private String	bnfEndID	;//	证件生效止期
	private String	bnfArea1	;//	地区一级
	private String	bnfArea2	;//	地区二级
	private String	bnfArea3	;//	地区三级
	private String	bnfOccupation1	;//	投保人职业一级
	private String	bnfOccupation2	;//	投保人职业二级
	private String	bnfOccupation3	;//	投保人职业三级
	
	
	private SDInformationInsured sdinformationInsured;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformationInsured getSdinformationInsured() {
		return sdinformationInsured;
	}
	public void setSdinformationInsured(SDInformationInsured sdinformationInsured) {
		this.sdinformationInsured = sdinformationInsured;
	}
	public String getInformationSn2() {
		return informationSn2;
	}
	public void setInformationSn2(String informationSn2) {
		this.informationSn2 = informationSn2;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getRecognizeeSn() {
		return recognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getBnfNo() {
		return bnfNo;
	}
	public void setBnfNo(String bnfNo) {
		this.bnfNo = bnfNo;
	}
	public String getBnfName() {
		return bnfName;
	}
	public void setBnfName(String bnfName) {
		this.bnfName = bnfName;
	}
	public String getBnfSex() {
		return bnfSex;
	}
	public void setBnfSex(String bnfSex) {
		this.bnfSex = bnfSex;
	}
	public String getBnfSexName() {
		return bnfSexName;
	}
	public void setBnfSexName(String bnfSexName) {
		this.bnfSexName = bnfSexName;
	}
	public String getBnfBirthday() {
		return bnfBirthday;
	}
	public void setBnfBirthday(String bnfBirthday) {
		this.bnfBirthday = bnfBirthday;
	}
	public String getBnfIDType() {
		return bnfIDType;
	}
	public void setBnfIDType(String bnfIDType) {
		this.bnfIDType = bnfIDType;
	}
	public String getBnfIDTypeName() {
		return bnfIDTypeName;
	}
	public void setBnfIDTypeName(String bnfIDTypeName) {
		this.bnfIDTypeName = bnfIDTypeName;
	}
	public String getBnfIDNo() {
		return bnfIDNo;
	}
	public void setBnfIDNo(String bnfIDNo) {
		this.bnfIDNo = bnfIDNo;
	}
	public String getBeneWay() {
		return beneWay;
	}
	public void setBeneWay(String beneWay) {
		this.beneWay = beneWay;
	}
	public String getBenePer() {
		return benePer;
	}
	public void setBenePer(String benePer) {
		this.benePer = benePer;
	}
	public String getBnfType() {
		return bnfType;
	}
	public void setBnfType(String bnfType) {
		this.bnfType = bnfType;
	}
	public String getRelationToInsured() {
		return relationToInsured;
	}
	public void setRelationToInsured(String relationToInsured) {
		this.relationToInsured = relationToInsured;
	}
	public String getRelationToInsuredName() {
		return relationToInsuredName;
	}
	public void setRelationToInsuredName(String relationToInsuredName) {
		this.relationToInsuredName = relationToInsuredName;
	}
	public String getBnfLot() {
		return bnfLot;
	}
	public void setBnfLot(String bnfLot) {
		this.bnfLot = bnfLot;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getBnfStartID() {
		return bnfStartID;
	}
	public void setBnfStartID(String bnfStartID) {
		this.bnfStartID = bnfStartID;
	}
	public String getBnfEndID() {
		return bnfEndID;
	}
	public void setBnfEndID(String bnfEndID) {
		this.bnfEndID = bnfEndID;
	}
	public String getBnfArea1() {
		return bnfArea1;
	}
	public void setBnfArea1(String bnfArea1) {
		this.bnfArea1 = bnfArea1;
	}
	public String getBnfArea2() {
		return bnfArea2;
	}
	public void setBnfArea2(String bnfArea2) {
		this.bnfArea2 = bnfArea2;
	}
	
	public String getBnfArea3() {
		return bnfArea3;
	}
	public void setBnfArea3(String bnfArea3) {
		this.bnfArea3 = bnfArea3;
	}
	public String getBnfOccupation1() {
		return bnfOccupation1;
	}
	public void setBnfOccupation1(String bnfOccupation1) {
		this.bnfOccupation1 = bnfOccupation1;
	}
	public String getBnfOccupation2() {
		return bnfOccupation2;
	}
	public void setBnfOccupation2(String bnfOccupation2) {
		this.bnfOccupation2 = bnfOccupation2;
	}
	public String getBnfOccupation3() {
		return bnfOccupation3;
	}
	public void setBnfOccupation3(String bnfOccupation3) {
		this.bnfOccupation3 = bnfOccupation3;
	}
	

}