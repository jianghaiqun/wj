package cn.com.sinosoft.entity;

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
 * 实体类：被保人信息 关联SDInformation
 *
 */
@Entity
public class SDInformationInsured extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9215593645162772197L;
	
	private String	orderSn	;//	订单编号
	private String	informationSn	;//	订单详细表编码
	private String	recognizeeSn	;//	被保人编号
	private String	recognizeeAppntRelation	;//	与投保人关系
	private String	recognizeeAppntRelationName	;//	与投保人关系显示
	private String	recognizeeName	;//	被保人姓名
	private String	recognizeeEnName	;//	被保人英文姓名或拼音
	private String	recognizeeLashName	;//	被保人姓氏拼音
	private String	recognizeeFirstName	;//	被保人名字拼音
	private String	recognizeeIdentityType	;//	被保人证件类型
	private String	recognizeeIdentityTypeName	;//	被保人证件类型显示
	private String	recognizeeIdentityId	;//	被保人证件号码
	private String	recognizeeSex	;//	被保人性别
	private String	recognizeeSexName	;//	被保人性别显示
	private String	recognizeeBirthday	;//	被保人出生日期
	private String	recognizeeMobile	;//	被保人手机
	private String	recognizeeTel	;//	被保人电话
	private String	recognizeeMail	;//	被保人电子邮箱
	private String	recognizeeOccupation1	;//	职业一级
	private String	recognizeeOccupation2	;//	职业二级
	private String	recognizeeOccupation3	;//	职业三级
	private String	recognizeeArea1	;//	被保人所在地一级
	private String	recognizeeArea2	;//	被保人所在地二级
	private String	recognizeeAddress	;//	被保人地址
	private String	recognizeeZipCode	;//	被保人邮政编码
	private String	recognizeeIsMarry	;//	婚否
	private String  recognizeeAge;
	private String	schoolOrCompany	;//	被保人机构 
	private String	outGoingParpose	;//	出行目的
	private String	flightNo	;//	航班號
	private String	flightTime	;//	起飛時間
	private String	flightLocation	;//	起飛地点
	private String	driverSchoolName	;//	驾校名称
	private String	driverNo	;//	学员编号
	private String	destinationCountry	;//	目的地1
	private String	destinationCountryText	;//	目的地2
	private String	isSelf	;//	是否是投保人本人
	private String	height	;//	身高
	private String	weight	;//	体重
	private String	overseasOccupation	;//	境外工作职业
	private String	travelType	;//	旅行种类
	private String	travelMode	;//	旅行方式
	private String	nationality	;//	国籍
	private String	haveBuy	;//	其他身故保险金额
	private String	uwCheckFlag	;//	核保标记
	private String	remark	;//	备用字段
	private String  insuredSn;//被保人流水号
	private String  recognizeePrem;//被保人保费
	private String  recognizeeOperate;//被保人操作标示
	private String  mulInsuredFlag;
	private String  recognizeeTotalPrem;
	private String recognizeeArea3; //被保人地区三级
	private String recognizeeStartID;//被保人证件类型有效期开始日期
	private String recognizeeEndID;//被保人证件类型有效期结束日期
	private String recognizeeMul;//被保人购买份数
	private String recognizeeKey;//同一被保人标志--多份
	private String socialSecurity;//是否有医保。是=Y；否=N。
	private String discountPrice;//被保人打折后保费
	private SDInformation sdinformation;//訂單詳細表信息
	private Set<SDInsuredHealth> sdinsuredHealthSet;//健康告知
	private Set<SDOrderItemOth> sdorderitemothSet;//订单项表2
	private Set<SDInformationInsuredElements> sdinforinselementsSet;//产品投保要素
	private Set<SDInformationBnf> sdinformationbnfSet;//受益人信息
	private SDInformationRiskType sdinformaitonrisktype;
	private QuestionPaper questionPaper;
	private SDInformationProperty sdinformationproperty;
	private String carPlateNo;//车牌号码
	private String recognizeeOrigin1;//被保人出发地一级
	private String recognizeeOrigin2;//被保人出发地二级
	private String recognizeeOrigin3;//被保人出发地三级
	private String recognizeeDestination1;//被保人目的地一级
	private String recognizeeDestination2;//被保人目的地二级
	private String recognizeeDestination3;//被保人目的地三级
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "sdinformationInsured")
	@Cascade(value = { CascadeType.DELETE })
	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}
	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	} 
	@OneToOne(mappedBy = "sdinformationinsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public SDInformationRiskType getSdinformaitonrisktype() {
		return sdinformaitonrisktype;
	}

	public void setSdinformaitonrisktype(SDInformationRiskType sdinformaitonrisktype) {
		this.sdinformaitonrisktype = sdinformaitonrisktype;
	} 
	@OneToOne(mappedBy = "sdinformationinsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public SDInformationProperty getSdinformationproperty() {
		return sdinformationproperty;
	}

	public void setSdinformationproperty(SDInformationProperty sdinformationproperty) {
		this.sdinformationproperty = sdinformationproperty;
	}
	@OneToMany(mappedBy = "sdinformationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("bnfNo asc")
	public Set<SDInformationBnf> getSdinformationbnfSet() {
		return sdinformationbnfSet;
	}

	public void setSdinformationbnfSet(Set<SDInformationBnf> sdinformationbnfSet) {
		this.sdinformationbnfSet = sdinformationbnfSet;
	}

	@OneToMany(mappedBy = "sdinformationInsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformationInsuredElements> getSdinforinselementsSet() {
		return sdinforinselementsSet;
	}

	public void setSdinforinselementsSet(Set<SDInformationInsuredElements> sdinforinselementsSet) {
		this.sdinforinselementsSet = sdinforinselementsSet;
	}

	@OneToMany(mappedBy = "sdinformationinsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInsuredHealth> getSdinsuredHealthSet() {
		return sdinsuredHealthSet;
	}

	public void setSdinsuredHealthSet(Set<SDInsuredHealth> sdinsuredHealthSet) {
		this.sdinsuredHealthSet = sdinsuredHealthSet;
	}

	@OneToMany(mappedBy = "sdinformationinsured", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDOrderItemOth> getSdorderitemothSet() {
		return sdorderitemothSet;
	}

	public void setSdorderitemothSet(Set<SDOrderItemOth> sdorderitemothSet) {
		this.sdorderitemothSet = sdorderitemothSet;
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

	public String getRecognizeeAppntRelation() {
		return recognizeeAppntRelation;
	}

	public void setRecognizeeAppntRelation(String recognizeeAppntRelation) {
		this.recognizeeAppntRelation = recognizeeAppntRelation;
	}

	public String getRecognizeeAppntRelationName() {
		return recognizeeAppntRelationName;
	}

	public void setRecognizeeAppntRelationName(String recognizeeAppntRelationName) {
		this.recognizeeAppntRelationName = recognizeeAppntRelationName;
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

	public String getRecognizeeLashName() {
		return recognizeeLashName;
	}

	public void setRecognizeeLashName(String recognizeeLashName) {
		this.recognizeeLashName = recognizeeLashName;
	}

	public String getRecognizeeFirstName() {
		return recognizeeFirstName;
	}

	public void setRecognizeeFirstName(String recognizeeFirstName) {
		this.recognizeeFirstName = recognizeeFirstName;
	}

	public String getRecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
	}

	public String getRecognizeeIdentityTypeName() {
		return recognizeeIdentityTypeName;
	}

	public void setRecognizeeIdentityTypeName(String recognizeeIdentityTypeName) {
		this.recognizeeIdentityTypeName = recognizeeIdentityTypeName;
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

	public String getRecognizeeSexName() {
		return recognizeeSexName;
	}

	public void setRecognizeeSexName(String recognizeeSexName) {
		this.recognizeeSexName = recognizeeSexName;
	}

	public String getRecognizeeBirthday() {
		return recognizeeBirthday;
	}

	public void setRecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
	}

	public String getRecognizeeMobile() {
		return recognizeeMobile;
	}

	public void setRecognizeeMobile(String recognizeeMobile) {
		this.recognizeeMobile = recognizeeMobile;
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

	public String getRecognizeeOccupation1() {
		return recognizeeOccupation1;
	}

	public void setRecognizeeOccupation1(String recognizeeOccupation1) {
		this.recognizeeOccupation1 = recognizeeOccupation1;
	}

	public String getRecognizeeOccupation2() {
		return recognizeeOccupation2;
	}

	public void setRecognizeeOccupation2(String recognizeeOccupation2) {
		this.recognizeeOccupation2 = recognizeeOccupation2;
	}

	public String getRecognizeeOccupation3() {
		return recognizeeOccupation3;
	}

	public void setRecognizeeOccupation3(String recognizeeOccupation3) {
		this.recognizeeOccupation3 = recognizeeOccupation3;
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

	public String getRecognizeeZipCode() {
		return recognizeeZipCode;
	}

	public void setRecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
	}

	public String getRecognizeeIsMarry() {
		return recognizeeIsMarry;
	}

	public void setRecognizeeIsMarry(String recognizeeIsMarry) {
		this.recognizeeIsMarry = recognizeeIsMarry;
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

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getFlightLocation() {
		return flightLocation;
	}

	public void setFlightLocation(String flightLocation) {
		this.flightLocation = flightLocation;
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

	public String getDestinationCountryText() {
		return destinationCountryText;
	}

	public void setDestinationCountryText(String destinationCountryText) {
		this.destinationCountryText = destinationCountryText;
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

	public String getOverseasOccupation() {
		return overseasOccupation;
	}

	public void setOverseasOccupation(String overseasOccupation) {
		this.overseasOccupation = overseasOccupation;
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

	public String getUwCheckFlag() {
		return uwCheckFlag;
	}

	public void setUwCheckFlag(String uwCheckFlag) {
		this.uwCheckFlag = uwCheckFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInsuredSn() {
		return insuredSn;
	}

	public void setInsuredSn(String insuredSn) {
		this.insuredSn = insuredSn;
	}

	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformation getSdinformation() {
		return sdinformation;
	}

	public void setSdinformation(SDInformation sdinformation) {
		this.sdinformation = sdinformation;
	}

	public String getRecognizeePrem() {
		return recognizeePrem;
	}

	public void setRecognizeePrem(String recognizeePrem) {
		this.recognizeePrem = recognizeePrem;
	}

	public String getRecognizeeOperate() {
		return recognizeeOperate;
	}

	public void setRecognizeeOperate(String recognizeeOperate) {
		this.recognizeeOperate = recognizeeOperate;
	}

	public String getMulInsuredFlag() {
		return mulInsuredFlag;
	}

	public void setMulInsuredFlag(String mulInsuredFlag) {
		this.mulInsuredFlag = mulInsuredFlag;
	}

	public String getRecognizeeAge() {
		return recognizeeAge;
	}

	public void setRecognizeeAge(String recognizeeAge) {
		this.recognizeeAge = recognizeeAge;
	}

	public String getRecognizeeTotalPrem() {
		return recognizeeTotalPrem;
	}
	public void setRecognizeeTotalPrem(String recognizeeTotalPrem) {
		this.recognizeeTotalPrem = recognizeeTotalPrem;
	}
	public String getRecognizeeArea3() {
		return recognizeeArea3;
	}
	public void setRecognizeeArea3(String recognizeeArea3) {
		this.recognizeeArea3 = recognizeeArea3;
	}
	public String getRecognizeeStartID() {
		return recognizeeStartID;
	}
	public void setRecognizeeStartID(String recognizeeStartID) {
		this.recognizeeStartID = recognizeeStartID;
	}
	public String getRecognizeeEndID() {
		return recognizeeEndID;
	}
	public void setRecognizeeEndID(String recognizeeEndID) {
		this.recognizeeEndID = recognizeeEndID;
	}
	public String getRecognizeeMul() {
		return recognizeeMul;
	}
	public void setRecognizeeMul(String recognizeeMul) {
		this.recognizeeMul = recognizeeMul;
	}
	public String getRecognizeeKey() {
		return recognizeeKey;
	}
	public void setRecognizeeKey(String recognizeeKey) {
		this.recognizeeKey = recognizeeKey;
	}
	public String getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public String getRecognizeeOrigin1() {
		return recognizeeOrigin1;
	}
	public void setRecognizeeOrigin1(String recognizeeOrigin1) {
		this.recognizeeOrigin1 = recognizeeOrigin1;
	}
	public String getRecognizeeOrigin2() {
		return recognizeeOrigin2;
	}
	public void setRecognizeeOrigin2(String recognizeeOrigin2) {
		this.recognizeeOrigin2 = recognizeeOrigin2;
	}
	public String getRecognizeeOrigin3() {
		return recognizeeOrigin3;
	}
	public void setRecognizeeOrigin3(String recognizeeOrigin3) {
		this.recognizeeOrigin3 = recognizeeOrigin3;
	}
	public String getRecognizeeDestination1() {
		return recognizeeDestination1;
	}
	public void setRecognizeeDestination1(String recognizeeDestination1) {
		this.recognizeeDestination1 = recognizeeDestination1;
	}
	public String getRecognizeeDestination2() {
		return recognizeeDestination2;
	}
	public void setRecognizeeDestination2(String recognizeeDestination2) {
		this.recognizeeDestination2 = recognizeeDestination2;
	}
	public String getRecognizeeDestination3() {
		return recognizeeDestination3;
	}
	public void setRecognizeeDestination3(String recognizeeDestination3) {
		this.recognizeeDestination3 = recognizeeDestination3;
	}
	
}