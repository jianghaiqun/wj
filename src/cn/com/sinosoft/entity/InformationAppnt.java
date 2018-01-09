package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * 
 * 实体类：投保人信息 关联Information
 *
 */



@Entity
public class InformationAppnt extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private Information information;// 基本信息
	
	private String applicantName;// 投保人姓名
	private String applicantIdentityType;// 投保人证件类型
	private String applicantIdentityTypeName;// 投保人证件类型显示
	private String applicantIdentityId;// 投保人证件号码
	private String applicantSex;// 投保人性别
	private String applicantSexName;// 投保人性别显示
	private String applicantBirthday;// 投保人出生日期
	private String applicantMail;// 投保人电子邮箱
	private String applicantArea1;// 投保人地区
	private String applicantArea2;// 投保人地区
	private String applicantZipCode;//邮政编码
	private String applicantMobile;//投保人移动电话
	private String applicantTel;// 投保人电话
	private String applicantMobile2;//投保人小灵通
	private String applicantHomePhone;//投保人家庭电话
	private String applicantEnName;//投保人英文名
	private String applicantAddress;//投保人联系地址
	private String applicantLiveAddress;//投保人居住地址
	private String applicantFirstEnName;//投保人姓氏拼音
	private String applicantLastEnName;//投保人名字拼音
	private String applicantOccupation1;// 投保人职业
	private String applicantOccupation2;// 投保人职业
	private String applicantOccupation3;// 投保人职业
	private String invoiceheading;//发票台头
	
	/**
	 * @return the applicantSexName
	 */
	public String getApplicantSexName() {
		return applicantSexName;
	}
	/**
	 * @param applicantSexName the applicantSexName to set
	 */
	public void setApplicantSexName(String applicantSexName) {
		this.applicantSexName = applicantSexName;
	}
	/**
	 * @return the applicantIdentityTypeName
	 */
	public String getApplicantIdentityTypeName() {
		return applicantIdentityTypeName;
	}
	/**
	 * @param applicantIdentityTypeName the applicantIdentityTypeName to set
	 */
	public void setApplicantIdentityTypeName(String applicantIdentityTypeName) {
		this.applicantIdentityTypeName = applicantIdentityTypeName;
	}
	/**
	 * @return the applicantArea1
	 */
	public String getApplicantArea1() {
		return applicantArea1;
	}
	/**
	 * @param applicantArea1 the applicantArea1 to set
	 */
	public void setApplicantArea1(String applicantArea1) {
		this.applicantArea1 = applicantArea1;
	}
	/**
	 * @return the applicantArea2
	 */
	public String getApplicantArea2() {
		return applicantArea2;
	}
	/**
	 * @param applicantArea2 the applicantArea2 to set
	 */
	public void setApplicantArea2(String applicantArea2) {
		this.applicantArea2 = applicantArea2;
	}
	/**
	 * @return the applicantOccupation1
	 */
	public String getApplicantOccupation1() {
		return applicantOccupation1;
	}
	/**
	 * @param applicantOccupation1 the applicantOccupation1 to set
	 */
	public void setApplicantOccupation1(String applicantOccupation1) {
		this.applicantOccupation1 = applicantOccupation1;
	}
	/**
	 * @return the applicantOccupation2
	 */
	public String getApplicantOccupation2() {
		return applicantOccupation2;
	}
	/**
	 * @param applicantOccupation2 the applicantOccupation2 to set
	 */
	public void setApplicantOccupation2(String applicantOccupation2) {
		this.applicantOccupation2 = applicantOccupation2;
	}
	/**
	 * @return the applicantOccupation3
	 */
	public String getApplicantOccupation3() {
		return applicantOccupation3;
	}
	/**
	 * @param applicantOccupation3 the applicantOccupation3 to set
	 */
	public void setApplicantOccupation3(String applicantOccupation3) {
		this.applicantOccupation3 = applicantOccupation3;
	}
	/**
	 * @return the applicantEnName
	 */
	public String getApplicantEnName() {
		return applicantEnName;
	}
	/**
	 * @param applicantEnName the applicantEnName to set
	 */
	public void setApplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
	}
	/**
	 * @return the applicantAddress
	 */
	public String getApplicantAddress() {
		return applicantAddress;
	}
	/**
	 * @param applicantAddress the applicantAddress to set
	 */
	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}
	/**
	 * @return the applicantLiveAddress
	 */
	public String getApplicantLiveAddress() {
		return applicantLiveAddress;
	}
	/**
	 * @param applicantLiveAddress the applicantLiveAddress to set
	 */
	public void setApplicantLiveAddress(String applicantLiveAddress) {
		this.applicantLiveAddress = applicantLiveAddress;
	}
	/**
	 * @return the applicantFirstEnName
	 */
	public String getApplicantFirstEnName() {
		return applicantFirstEnName;
	}
	public void setApplicantFirstEnName(String applicantFirstEnName) {
		this.applicantFirstEnName = applicantFirstEnName;
	}
	public String getApplicantLastEnName() {
		return applicantLastEnName;
	}
	public void setApplicantLastEnName(String applicantLastEnName) {
		this.applicantLastEnName = applicantLastEnName;
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
	/**
	 * @return the applicantMobile2
	 */
	public String getApplicantMobile2() {
		return applicantMobile2;
	}
	/**
	 * @param applicantMobile2 the applicantMobile2 to set
	 */
	public void setApplicantMobile2(String applicantMobile2) {
		this.applicantMobile2 = applicantMobile2;
	}
	/**
	 * @return the applicantHomePhone
	 */
	public String getApplicantHomePhone() {
		return applicantHomePhone;
	}
	/**
	 * @param applicantHomePhone the applicantHomePhone to set
	 */
	public void setApplicantHomePhone(String applicantHomePhone) {
		this.applicantHomePhone = applicantHomePhone;
	}
	/**
	 * @return the recognizeeMobile
	 */

	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}
	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
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
	public String getApplicantBirthday() {
		return applicantBirthday;
	}
	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}
	public String getApplicantTel() {
		return applicantTel;
	}
	public void setApplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
	}
	public String getApplicantMail() {
		return applicantMail;
	}
	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}
	@OneToOne(fetch = FetchType.LAZY)
	public Information getInformation() {
		return information;
	}
	public void setInformation(Information information) {
		this.information = information;
	}
	public String getInvoiceheading() {
		return invoiceheading;
	}
	public void setInvoiceheading(String invoiceheading) {
		this.invoiceheading = invoiceheading;
	}	
	
}