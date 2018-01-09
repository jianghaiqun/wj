package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

/**
 * 
 * 实体类：快速录入投保人信息 关联Member
 *
 */



@Entity
public class SDRelationAppnt extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2476021979691411114L;
	
	private String  productId;//产品id
	private String	applicantName	;//	投保人姓名
	private String	applicantEnName	;//	投保人英文名
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
	private String	applicantArea3	;//	投保人地区三级
	private String	applicantAddress	;//	投保人地址
	private String	applicantZipCode	;//	邮政编码
	private String	applicantMobile	;//	投保人移动电话
	private String	applicantTel	;//	投保人电话
	private String	applicantOccupation1	;//	投保人职业一级
	private String	applicantOccupation2	;//	投保人职业二级
	private String	applicantOccupation3	;//	投保人职业三级
	private String  applicantStartID;//证件有效期起期
	private String  applicantEndID;//证件有效期止期
	private String  memberId;//会员id，用于查询
	private String	remark	;//	备用字段
	private String  socialSecurity;//是否有医保
	
    private Member  mMember;
	
    @ManyToOne(fetch = FetchType.LAZY) 
    @OrderBy("modifyDate desc")
	public Member getmMember() {
		return mMember;
	}
	public void setmMember(Member mMember) {
		this.mMember = mMember;
	}
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getApplicantEnName() {
		return applicantEnName;
	}
	public void setApplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
	}
	public String getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	
	
}