package cn.com.sinosoft.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * 实体类：被保人信息 关联Information
 *
 */     
@Entity    
public class InformationInsured extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private Information information;// 基本信息
	
	private String recognizeeName;// 被保人姓名
	private String recognizeeEnName;// 被保人英文姓名或拼音
	private String recognizeeIdentityType;// 被保人证件类型
	private String recognizeeIdentityTypeName;// 被保人证件类型显示
	private String recognizeeIdentityId;// 被保人证件号码
	private String recognizeeSex;// 被保人性别
	private String recognizeeSexName;// 被保人性别显示
	private String recognizeeBirthday;// 被保人出生日期
	private String recognizeeMobile;// 被保人手机
	private String recognizeeTel;// 被保人电话
	private String recognizeeMail;// 被保人电子邮箱
	private String recognizeeZipCode;// 被保人邮政编码
//	private String occupationType;// 职业类别
	private String occupation1;// 职业
	private String occupation2;// 职业
	private String occupation3;// 职业
	private String recognizeeArea1;// 被保人所在地
	private String recognizeeArea2;// 被保人所在地
	
	private String recognizeeIsMarry;//婚否
	private String recognizeeAppntRelation;//与投保人关系
	private String recognizeeAppntRelationName;//与投保人关系显示
	private String schoolOrCompany;//被保人机构,境外留学学校或境外工作公司，用于境外产品
	private String outGoingParpose;//出行目的
	private String recognizeeFirstEnName;//被保人姓氏拼音
	private String recognizeeLastEnName;//被保人名字拼音
	private String recognizeeAddress;//被保人联系地址
	private String recognizeeLiveAddress;//被保人居住地址
	private String flightNo;//航班號
	private Date flightTime;//起飛時間
	private Set<InformationItemMain> informationItemMainSet;//标的信息
	private Set<InformationInsuredElements> informationInsuredElementsSet;//产品投保要素信息
	private String driverSchoolName;//驾校名称
	private String driverNo;//学员编号
	private String destinationCountry;//目的地,用于境外产品
	private String destinationCountryText;//目的地,用于境外产品文字
	private String flightLocation;//起飛地点 
	private String contNo;//保单号
	private String isSelf;//是否是投保人本人。Y=是；N=否。
	private String height;//身高
	private String weight;//体重
	private String overseasOccupation;//境外工作职业，用于境外产品
	private String travelType;//旅行种类
	private String travelMode;//旅行方式
	private String nationality;//国籍
	private Set<InformationRiskType> informationRiskTypeSet;// 做级联删除用
	private Set<InformationBnf> informationBnfSet;//受益人
	private Set<InsuredHealth> insuredHealthSet;//健康告知
	private QuestionPaper questionPaper;
	private String haveBuy;//被保险人已经或正在购买其他保险公司的身故保险金额。单位万元
	public String getDestinationCountryText() {
		return destinationCountryText;
	}
	public void setDestinationCountryText(String destinationCountryText) {
		this.destinationCountryText = destinationCountryText;
	}
	
	public String getRecognizeeSexName() {
		return recognizeeSexName;
	}
	public void setRecognizeeSexName(String recognizeeSexName) {
		this.recognizeeSexName = recognizeeSexName;
	}
	public String getRecognizeeAppntRelationName() {
		return recognizeeAppntRelationName;
	}
	public void setRecognizeeAppntRelationName(String recognizeeAppntRelationName) {
		this.recognizeeAppntRelationName = recognizeeAppntRelationName;
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
	public String getRecognizeeArea1() {
		return recognizeeArea1;
	}
	public void setRecognizeeArea1(String recognizeeArea1) {
		this.recognizeeArea1 = recognizeeArea1;
	}
	public String getRecognizeeArea2() {
		return recognizeeArea2;
	}
	public void setRecognizeeArea2(String recognizeeArea2) {
		this.recognizeeArea2 = recognizeeArea2;
	}
	public String getRecognizeeAddress() {
		return recognizeeAddress;
	}
	public void setRecognizeeAddress(String recognizeeAddress) {
		this.recognizeeAddress = recognizeeAddress;
	}
	public String getRecognizeeLiveAddress() {
		return recognizeeLiveAddress;
	}
	public void setRecognizeeLiveAddress(String recognizeeLiveAddress) {
		this.recognizeeLiveAddress = recognizeeLiveAddress;
	}
	public String getRecognizeeFirstEnName() {
		return recognizeeFirstEnName;
	}
	public void setRecognizeeFirstEnName(String recognizeeFirstEnName) {
		this.recognizeeFirstEnName = recognizeeFirstEnName;
	}
	public String getRecognizeeLastEnName() {
		return recognizeeLastEnName;
	}
	public void setRecognizeeLastEnName(String recognizeeLastEnName) {
		this.recognizeeLastEnName = recognizeeLastEnName;
	}
	public String getRecognizeeMobile() {
		return recognizeeMobile;
	}
	public void setRecognizeeMobile(String recognizeeMobile) {
		this.recognizeeMobile = recognizeeMobile;
	}
	public String getSchoolOrCompany() {
		return schoolOrCompany;
	}
	public void setSchoolOrCompany(String schoolOrCompany) {
		this.schoolOrCompany = schoolOrCompany;
	}
	public String getOutGoingParpose() {
		return outGoingParpose;
	}
	public void setOutGoingParpose(String outGoingParpose) {
		this.outGoingParpose = outGoingParpose;
	}
	
	public String getRecognizeeAppntRelation() {
		return recognizeeAppntRelation;
	}
	public void setRecognizeeAppntRelation(String recognizeeAppntRelation) {
		this.recognizeeAppntRelation = recognizeeAppntRelation;
	}

	public String getRecognizeeIsMarry() {
		return recognizeeIsMarry;
	}
	public void setRecognizeeIsMarry(String recognizeeIsMarry) {
		this.recognizeeIsMarry = recognizeeIsMarry;
	}
	public String getRecognizeeName() {
		return recognizeeName;
	}
	public void setRecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
	}
	public String getRecognizeeEnName() {
		return recognizeeEnName;
	}
	public void setRecognizeeEnName(String recognizeeEnName) {
		this.recognizeeEnName = recognizeeEnName;
	}
	public String getRecognizeeIdentityType() {
		return recognizeeIdentityType;
	}
	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
	}
	public String getRecognizeeIdentityId() {
		return recognizeeIdentityId;
	}
	public void setRecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
	}
	public String getRecognizeeSex() {
		return recognizeeSex;
	}
	public void setRecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
	}
	public String getRecognizeeBirthday() {
		return recognizeeBirthday;
	}
	public void setRecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
	}
	public String getRecognizeeTel() {
		return recognizeeTel;
	}
	public void setRecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
	}
	public String getRecognizeeMail() {
		return recognizeeMail;
	}
	public void setRecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
	}
	
	public String getRecognizeeZipCode() {
		return recognizeeZipCode;
	}
	public void setRecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
	}
	
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public Date getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(Date flightTime) {
		this.flightTime = flightTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Information getInformation() {
		return information;
	}
	public void setInformation(Information information) {
		this.information = information;
	}
	public String getRecognizeeIdentityTypeName() {
		return recognizeeIdentityTypeName;
	}
	public void setRecognizeeIdentityTypeName(String recognizeeIdentityTypeName) {
		this.recognizeeIdentityTypeName = recognizeeIdentityTypeName;
	}
	@OneToMany(mappedBy = "informationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationRiskType> getInformationRiskTypeSet() {
		return informationRiskTypeSet;
	}
	
	public void setInformationRiskTypeSet(Set<InformationRiskType> informationRiskTypeSet) {
		this.informationRiskTypeSet = informationRiskTypeSet;
	}
	@OneToMany(mappedBy = "informationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationBnf> getInformationBnfSet() {
		return informationBnfSet;
	}
	public void setInformationBnfSet(Set<InformationBnf> informationBnfSet) {
		this.informationBnfSet = informationBnfSet;
	}
	
	@OneToMany(mappedBy = "informationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationItemMain> getInformationItemMainSet() {
		return informationItemMainSet;
	}
	public void setInformationItemMainSet(
			Set<InformationItemMain> informationItemMainSet) {
		this.informationItemMainSet = informationItemMainSet;
	}
	
	@OneToMany(mappedBy = "informationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationInsuredElements> getInformationInsuredElementsSet() {
		return informationInsuredElementsSet;
	}
	public void setInformationInsuredElementsSet(
			Set<InformationInsuredElements> informationInsuredElementsSet) {
		this.informationInsuredElementsSet = informationInsuredElementsSet;
	}
	@OneToMany(mappedBy = "informationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("showOrder asc")
	public Set<InsuredHealth> getInsuredHealthSet() {
		return insuredHealthSet;
	}
	public void setInsuredHealthSet(Set<InsuredHealth> insuredHealthSet) {
		this.insuredHealthSet = insuredHealthSet;
	}
	public String getDriverSchoolName() {
		return driverSchoolName;
	}
	public void setDriverSchoolName(String driverSchoolName) {
		this.driverSchoolName = driverSchoolName;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	public String getOverseasOccupation() {
		return overseasOccupation;
	}
	public void setOverseasOccupation(String overseasOccupation) {
		this.overseasOccupation = overseasOccupation;
	}
	public String getFlightLocation() {
		return flightLocation;
	}
	public void setFlightLocation(String flightLocation) {
		this.flightLocation = flightLocation;
	}
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	public String getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public String getTravelMode() {
		return travelMode;
	}
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getHaveBuy() {
		return haveBuy;
	}
	public void setHaveBuy(String haveBuy) {
		this.haveBuy = haveBuy;
	}
	
}