package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 
 * 实体类：投保人信息 关联SDInformation
 *
 */



@Entity
public class SDInformationAppnt extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6191470061737668155L;
	
	
	private String	informationSn	;//	订单明细表编号
	private String	applicantSn	;//	投保人编码
	private String	applicantName	;//	投保人姓名
	private String	applicantEnName	;//	投保人英文名
	private String	applicantLastName	;//	投保人姓氏拼音
	private String	applicantFirstName	;//	投保人名字拼音
	private String	applicantIdentityType	;//	投保人证件类型
	private String	applicantIdentityTypeName	;//	投保人证件类型显示
	private String	applicantIdentityId	;//	投保人证件号码
	private String	applicantSex	;//	投保人性别
	private String  applicantAge;//投保人年龄
	private String	applicantSexName	;//	投保人性别显示
	private String	applicantBirthday	;//	投保人出生日期
	private String	applicantMail	;//	投保人电子邮箱
	private String	applicantArea1	;//	投保人地区一级
	private String	applicantArea2	;//	投保人地区二级
	private String	applicantAddress	;//	投保人地址
	private String	applicantZipCode	;//	邮政编码
	private String	applicantMobile	;//	投保人移动电话
	private String	applicantTel	;//	投保人电话
	private String	applicantOccupation1	;//	投保人职业一级
	private String	applicantOccupation2	;//	投保人职业二级
	private String	applicantOccupation3	;//	投保人职业三级
	private String	invoiceHeading	;//	发票台头
	private String	bankCode	;//	银行编码
	private String	bankAccNo	;//	银行帐号
	private String	accName	;//	银行户名
	private String	remark	;//	备用字段
	private String applicantArea3 ; //投保人地区三级
	private String applicantStartID ; //投保人证件类型有效起期
	private String applicantEndID ; //投保人证件类型有效始期
	private String socialSecurity; //是否有医保
	private String applicantIncome; //年收入
	
	private SDInformation sdinformaiton;//基本信息
	
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformation getSdinformaiton() {
		return sdinformaiton;
	}
	public void setSdinformaiton(SDInformation sdinformaiton) {
		this.sdinformaiton = sdinformaiton;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getApplicantSn() {
		return applicantSn;
	}
	public void setApplicantSn(String applicantSn) {
		this.applicantSn = applicantSn;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantEnName() {
		return applicantEnName;
	}
	public void setApplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
	}
	public String getApplicantLastName() {
		return applicantLastName;
	}
	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}
	public String getApplicantFirstName() {
		return applicantFirstName;
	}
	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}
	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}
	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
	}
	public String getApplicantIdentityTypeName() {
		return applicantIdentityTypeName;
	}
	public void setApplicantIdentityTypeName(String applicantIdentityTypeName) {
		this.applicantIdentityTypeName = applicantIdentityTypeName;
	}
	public String getApplicantIdentityId() {
		return applicantIdentityId;
	}
	public void setApplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
	}
	public String getApplicantSex() {
		return applicantSex;
	}
	public void setApplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
	}
	public String getApplicantSexName() {
		return applicantSexName;
	}
	public void setApplicantSexName(String applicantSexName) {
		this.applicantSexName = applicantSexName;
	}
	public String getApplicantBirthday() {
		return applicantBirthday;
	}
	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}
	public String getApplicantMail() {
		return applicantMail;
	}
	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}
	public String getApplicantArea1() {
		return applicantArea1;
	}
	public void setApplicantArea1(String applicantArea1) {
		this.applicantArea1 = applicantArea1;
	}
	public String getApplicantArea2() {
		return applicantArea2;
	}
	public void setApplicantArea2(String applicantArea2) {
		this.applicantArea2 = applicantArea2;
	}
	public String getApplicantAddress() {
		return applicantAddress;
	}
	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}
	public String getApplicantZipCode() {
		return applicantZipCode;
	}
	public void setApplicantZipCode(String applicantZipCode) {
		this.applicantZipCode = applicantZipCode;
	}
	public String getApplicantMobile() {
		return applicantMobile;
	}
	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}
	public String getApplicantTel() {
		return applicantTel;
	}
	public void setApplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
	}
	public String getApplicantOccupation1() {
		return applicantOccupation1;
	}
	public void setApplicantOccupation1(String applicantOccupation1) {
		this.applicantOccupation1 = applicantOccupation1;
	}
	public String getApplicantOccupation2() {
		return applicantOccupation2;
	}
	public void setApplicantOccupation2(String applicantOccupation2) {
		this.applicantOccupation2 = applicantOccupation2;
	}
	public String getApplicantOccupation3() {
		return applicantOccupation3;
	}
	public void setApplicantOccupation3(String applicantOccupation3) {
		this.applicantOccupation3 = applicantOccupation3;
	}
	public String getInvoiceHeading() {
		return invoiceHeading;
	}
	public void setInvoiceHeading(String invoiceHeading) {
		this.invoiceHeading = invoiceHeading;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApplicantAge() {
		return applicantAge;
	}
	public void setApplicantAge(String applicantAge) {
		this.applicantAge = applicantAge;
	}
	public String getApplicantArea3() {
		return applicantArea3;
	}
	public void setApplicantArea3(String applicantArea3) {
		this.applicantArea3 = applicantArea3;
	}
	public String getApplicantStartID() {
		return applicantStartID;
	}
	public void setApplicantStartID(String applicantStartID) {
		this.applicantStartID = applicantStartID;
	}
	public String getApplicantEndID() {
		return applicantEndID;
	}
	public void setApplicantEndID(String applicantEndID) {
		this.applicantEndID = applicantEndID;
	}
	public String getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	public String getApplicantIncome() {
		return applicantIncome;
	}
	public void setApplicantIncome(String applicantIncome) {
		this.applicantIncome = applicantIncome;
	}
	
	
}