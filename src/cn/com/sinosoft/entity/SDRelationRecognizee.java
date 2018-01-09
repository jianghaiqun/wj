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
public class SDRelationRecognizee extends BaseEntity {

	 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5601250057944018109L;
	
	private String  productId;//产品id
	private String	RecognizeeName	;//	投保人姓名
	private String	recognizeeEnName	;//	被保人英文姓名或拼音
	private String	RecognizeeIdentityType	;//	投保人证件类型
	private String	RecognizeeIdentityTypeName	;//	投保人证件类型显示
	private String	RecognizeeIdentityId	;//	投保人证件号码
	private String	RecognizeeSex	;//	投保人性别
	private String  RecognizeeAge;//投保人年龄
	private String	RecognizeeSexName	;//	投保人性别显示
	private String	RecognizeeBirthday	;//	投保人出生日期
	private String	RecognizeeMail	;//	投保人电子邮箱
	private String	RecognizeeArea1	;//	投保人地区一级
	private String	RecognizeeArea2	;//	投保人地区二级
	private String	RecognizeeArea3	;//	投保人地区三级
	private String	RecognizeeAddress	;//	投保人地址
	private String	RecognizeeZipCode	;//	邮政编码
	private String	RecognizeeMobile	;//	投保人移动电话
	private String	RecognizeeTel	;//	投保人电话
	private String	RecognizeeOccupation1	;//	投保人职业一级
	private String	RecognizeeOccupation2	;//	投保人职业二级
	private String	RecognizeeOccupation3	;//	投保人职业三级
	private String  RecognizeeStartID;//证件有效期起期
	private String  RecognizeeEndID;//证件有效期止期
	private String  memberId;//会员id，用于查询
	private String	remark	;//	备用字段
	
    private Member  mMember;
	
    @ManyToOne(fetch = FetchType.LAZY) 
    @OrderBy("modifyDate desc")
	public Member getmMember() {
		return mMember;
	}
	public void setmMember(Member mMember) {
		this.mMember = mMember;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRecognizeeName() {
		return RecognizeeName;
	}
	public void setRecognizeeName(String recognizeeName) {
		RecognizeeName = recognizeeName;
	}
	public String getRecognizeeIdentityType() {
		return RecognizeeIdentityType;
	}
	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		RecognizeeIdentityType = recognizeeIdentityType;
	}
	public String getRecognizeeIdentityTypeName() {
		return RecognizeeIdentityTypeName;
	}
	public void setRecognizeeIdentityTypeName(String recognizeeIdentityTypeName) {
		RecognizeeIdentityTypeName = recognizeeIdentityTypeName;
	}
	public String getRecognizeeIdentityId() {
		return RecognizeeIdentityId;
	}
	public void setRecognizeeIdentityId(String recognizeeIdentityId) {
		RecognizeeIdentityId = recognizeeIdentityId;
	}
	public String getRecognizeeSex() {
		return RecognizeeSex;
	}
	public void setRecognizeeSex(String recognizeeSex) {
		RecognizeeSex = recognizeeSex;
	}
	public String getRecognizeeAge() {
		return RecognizeeAge;
	}
	public void setRecognizeeAge(String recognizeeAge) {
		RecognizeeAge = recognizeeAge;
	}
	public String getRecognizeeSexName() {
		return RecognizeeSexName;
	}
	public void setRecognizeeSexName(String recognizeeSexName) {
		RecognizeeSexName = recognizeeSexName;
	}
	public String getRecognizeeBirthday() {
		return RecognizeeBirthday;
	}
	public void setRecognizeeBirthday(String recognizeeBirthday) {
		RecognizeeBirthday = recognizeeBirthday;
	}
	public String getRecognizeeMail() {
		return RecognizeeMail;
	}
	public void setRecognizeeMail(String recognizeeMail) {
		RecognizeeMail = recognizeeMail;
	}
	public String getRecognizeeArea1() {
		return RecognizeeArea1;
	}
	public void setRecognizeeArea1(String recognizeeArea1) {
		RecognizeeArea1 = recognizeeArea1;
	}
	public String getRecognizeeArea2() {
		return RecognizeeArea2;
	}
	public void setRecognizeeArea2(String recognizeeArea2) {
		RecognizeeArea2 = recognizeeArea2;
	}
	public String getRecognizeeArea3() {
		return RecognizeeArea3;
	}
	public void setRecognizeeArea3(String recognizeeArea3) {
		RecognizeeArea3 = recognizeeArea3;
	}
	public String getRecognizeeAddress() {
		return RecognizeeAddress;
	}
	public void setRecognizeeAddress(String recognizeeAddress) {
		RecognizeeAddress = recognizeeAddress;
	}
	public String getRecognizeeZipCode() {
		return RecognizeeZipCode;
	}
	public void setRecognizeeZipCode(String recognizeeZipCode) {
		RecognizeeZipCode = recognizeeZipCode;
	}
	public String getRecognizeeMobile() {
		return RecognizeeMobile;
	}
	public void setRecognizeeMobile(String recognizeeMobile) {
		RecognizeeMobile = recognizeeMobile;
	}
	public String getRecognizeeTel() {
		return RecognizeeTel;
	}
	public void setRecognizeeTel(String recognizeeTel) {
		RecognizeeTel = recognizeeTel;
	}
	public String getRecognizeeOccupation1() {
		return RecognizeeOccupation1;
	}
	public void setRecognizeeOccupation1(String recognizeeOccupation1) {
		RecognizeeOccupation1 = recognizeeOccupation1;
	}
	public String getRecognizeeOccupation2() {
		return RecognizeeOccupation2;
	}
	public void setRecognizeeOccupation2(String recognizeeOccupation2) {
		RecognizeeOccupation2 = recognizeeOccupation2;
	}
	public String getRecognizeeOccupation3() {
		return RecognizeeOccupation3;
	}
	public void setRecognizeeOccupation3(String recognizeeOccupation3) {
		RecognizeeOccupation3 = recognizeeOccupation3;
	}
	public String getRecognizeeStartID() {
		return RecognizeeStartID;
	}
	public void setRecognizeeStartID(String recognizeeStartID) {
		RecognizeeStartID = recognizeeStartID;
	}
	public String getRecognizeeEndID() {
		return RecognizeeEndID;
	}
	public void setRecognizeeEndID(String recognizeeEndID) {
		RecognizeeEndID = recognizeeEndID;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRecognizeeEnName() {
		return recognizeeEnName;
	}
	public void setRecognizeeEnName(String recognizeeEnName) {
		this.recognizeeEnName = recognizeeEnName;
	}
	
	
}