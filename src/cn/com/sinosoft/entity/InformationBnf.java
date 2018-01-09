package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 
 * 实体类：受益人信息 关联Information
 *
 */



@Entity
public class InformationBnf extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationInsured informationInsured;// 基本信息
	
	private String bnfNo;// 受益人序号
	private String name;//客户姓名
	private String sex;//客户性别
	private String birthday;//客户出生日期
	private String iDType;// 证件类型
	private String iDNo;// 证件号码
	private String beneWay;// 受益方式
	private String benePer;// 受益比例
	private String bnfType;// 受益人类别
	private String relationToInsured;// 与被保人关系
	private String bnfLot;// 受益份额
	private String bankCode;// 银行编码
	private String bankAccNo;// 银行帐号
	private String accName;// 银行户名
	private String postalAddress;// 联系地址
	private String zipCode;//邮编
	private String phone;//固定电话
	private String mobile;//手机
	private String email;//电子邮件
	private String occupation;//受益人职业
	

	
	

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	/**
	 * @return the bnfNo
	 */
	public String getBnfNo() {
		return bnfNo;
	}
	/**
	 * @param bnfNo the bnfNo to set
	 */
	public void setBnfNo(String bnfNo) {
		this.bnfNo = bnfNo;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the iDType
	 */
	public String getiDType() {
		return iDType;
	}
	/**
	 * @param iDType the iDType to set
	 */
	public void setiDType(String iDType) {
		this.iDType = iDType;
	}
	/**
	 * @return the iDNo
	 */
	public String getiDNo() {
		return iDNo;
	}
	/**
	 * @param iDNo the iDNo to set
	 */
	public void setiDNo(String iDNo) {
		this.iDNo = iDNo;
	}
	/**
	 * @return the beneWay
	 */
	public String getBeneWay() {
		return beneWay;
	}
	/**
	 * @param beneWay the beneWay to set
	 */
	public void setBeneWay(String beneWay) {
		this.beneWay = beneWay;
	}
	/**
	 * @return the benePer
	 */
	public String getBenePer() {
		return benePer;
	}
	/**
	 * @param benePer the benePer to set
	 */
	public void setBenePer(String benePer) {
		this.benePer = benePer;
	}
	/**
	 * @return the bnfType
	 */
	public String getBnfType() {
		return bnfType;
	}
	/**
	 * @param bnfType the bnfType to set
	 */
	public void setBnfType(String bnfType) {
		this.bnfType = bnfType;
	}
	/**
	 * @return the relationToInsured
	 */
	public String getRelationToInsured() {
		return relationToInsured;
	}
	/**
	 * @param relationToInsured the relationToInsured to set
	 */
	public void setRelationToInsured(String relationToInsured) {
		this.relationToInsured = relationToInsured;
	}
	/**
	 * @return the bnfLot
	 */
	public String getBnfLot() {
		return bnfLot;
	}
	/**
	 * @param bnfLot the bnfLot to set
	 */
	public void setBnfLot(String bnfLot) {
		this.bnfLot = bnfLot;
	}
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 * @return the bankAccNo
	 */
	public String getBankAccNo() {
		return bankAccNo;
	}
	/**
	 * @param bankAccNo the bankAccNo to set
	 */
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}
	/**
	 * @param accName the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/**
	 * @return the postalAddress
	 */
	public String getPostalAddress() {
		return postalAddress;
	}
	/**
	 * @param postalAddress the postalAddress to set
	 */
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationInsured getInformationInsured() {
		return informationInsured;
	}
	public void setInformationInsured(InformationInsured informationInsured) {
		this.informationInsured = informationInsured;
	}
}