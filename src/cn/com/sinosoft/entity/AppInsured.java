package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SDAPPINSURED")
public class AppInsured extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465573485995939765L;
	public static final int LIST_COUNT = 10; 
	
	@Column(name = "applicantArea", length = 255)
	private String applicantArea;
	@Column(name = "applicantBirthday", length = 255)
	private String applicantBirthday;
	@Column(name = "applicantIdentityId", length = 255)
	private String applicantIdentityId;
	@Column(name = "applicantIdentityType", length = 255)
	private String applicantIdentityType;
	@Column(name = "applicantMail", length = 255)
	private String applicantMail;
	@Column(name = "applicantName", length = 255)
	private String applicantName;
	@Column(name = "applicantSex", length = 255)
	private String applicantSex;
	@Column(name = "applicantTel", length = 255)
	private String applicantTel;
	@Column(name = "electronicCout", length = 255)
	private String electronicCout;
	@Column(name = "occupation1", length = 255)//投保人职业
	private String occupation1;
	@Column(name = "occupation2", length = 255)//被保人职业
	private String occupation2;
	@Column(name = "occupation3", length = 255)
	private String occupation3;
	@Column(name = "recognizeeArea", length = 255)
	private String recognizeeArea;
	@Column(name = "recognizeeBirthday", length = 255)
	private String recognizeeBirthday;
	@Column(name = "recognizeeIdentityId", length = 255)
	private String recognizeeIdentityId;
	@Column(name = "recognizeeIdentityType", length = 255)
	private String recognizeeIdentityType;
	@Column(name = "recognizeeMail", length = 255)
	private String recognizeeMail;
	@Column(name = "recognizeeName", length = 255)
	private String recognizeeName;
	@Column(name = "recognizeeSex", length = 255)
	private String recognizeeSex;
	@Column(name = "recognizeeTel", length = 255)
	private String recognizeeTel;
	@Column(name = "recognizeeZipCode", length = 255)
	private String recognizeeZipCode;
	@Column(name = "memberID", length = 255)
	private String memberID;
	@Column(name = "gxstate", length = 12)
	private String gxState;  // 0 增加 1 修改 2 删除
	@Column(name = "relation", length = 12)
    private String relation; 


	public String getApplicantArea() {
		return applicantArea;
	}

	public void setApplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
	}

	public String getApplicantBirthday() {
		return applicantBirthday;
	}

	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}

	public String getApplicantIdentityId() {
		return applicantIdentityId;
	}

	public void setApplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
	}

	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}

	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
	}

	public String getApplicantMail() {
		return applicantMail;
	}

	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantSex() {
		return applicantSex;
	}

	public void setApplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
	}

	public String getApplicantTel() {
		return applicantTel;
	}

	public void setApplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
	}



	public String getElectronicCout() {
		return electronicCout;
	}

	public void setElectronicCout(String electronicCout) {
		this.electronicCout = electronicCout;
	}

	public String getOccupation1() {
		return occupation1;
	}

	public void setOccupation1(String occupation1) {
		this.occupation1 = occupation1;
	}

	public String getOccupation2() {
		return occupation2;
	}

	public void setOccupation2(String occupation2) {
		this.occupation2 = occupation2;
	}

	public String getOccupation3() {
		return occupation3;
	}

	public void setOccupation3(String occupation3) {
		this.occupation3 = occupation3;
	}

	public String getRecognizeeArea() {
		return recognizeeArea;
	}

	public void setRecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
	}

	public String getRecognizeeBirthday() {
		return recognizeeBirthday;
	}

	public void setRecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
	}

	public String getRecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	public void setRecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
	}

	public String getRecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
	}

	public String getRecognizeeMail() {
		return recognizeeMail;
	}

	public void setRecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
	}

	public String getRecognizeeName() {
		return recognizeeName;
	}

	public void setRecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
	}

	public String getRecognizeeSex() {
		return recognizeeSex;
	}

	public void setRecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
	}

	public String getRecognizeeTel() {
		return recognizeeTel;
	}

	public void setRecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
	}

	public String getRecognizeeZipCode() {
		return recognizeeZipCode;
	}

	public void setRecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setGxState(String gxState) {
		this.gxState = gxState;
	}

	public String getGxState() {
		return gxState;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelation() {
		return relation;
	}

	

}
