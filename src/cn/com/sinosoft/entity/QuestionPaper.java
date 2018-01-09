package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class QuestionPaper extends BaseEntity{

	/**调查问卷表
	 *  add by lilang
	 */
	private static final long serialVersionUID = -8869366809169175600L;
		
	private String ordID;//订单号      
	private String applicantName;//投保人姓名
	private String effective;//填写日期
	private String recognizeeArea1;//地址1
	private String recognizeeArea2;//地址2
	private String recognizeeArea3;//地址3
	private String applicantMobile;//电话号码
	private String  Cannualincome;//预计今年收入
	private String  Cmisrevenues;//今年其它收入
	private String Lannualincome;//去年收入
	private String Lmisrevenues;//去年其它收入
	private String LTannualincome;//两年前
	private  String LTmisrevenues;//两年前其它收入
	private String Ctotalincome;// 收入总和1
	private String Ltotalincome;//收入总和2
	private String  LTtotalincome;// 收入总和3
	private String  insurePurpose;// 保险目的  0-还贷保障，1-家庭保障，2- 商业捐献，3-抵押偿还 ，4-其它
	private String otherInsurePurpose;//其它目的
	private String liveStatus;//居住情况 0-自有居所， 1-租借居所
	private String buyPrice;//购买价格
	private String rentPrice;//租住价格0-3000元以下，1- 3000-5000元，2-5000元以上
	private String livedTime ;//居住时间0：一年以下，1：1-3年，2：3-6年，3：更长时间
	private String familySituation ;//家庭情况 0- 独身，1-已婚
	private String childrenCount ;//孩子个数
	private String peopleCount ;//居住人数
	private String carNum ;//汽车数量 0-1辆，1-2辆，2->=3辆
	private String carBrand1 ;//汽车品牌1
	private String carBrand2 ;//汽车品牌2
	private String carBrand3 ;//汽车品牌3
	private String propertyName1 ;//财产名称1
	private String propertyAdress1 ;//财产地址1
	private String propertyValue1 ;//市值1
	private String GpropertyValue1 ;//估计价值1
	private String propertyName2 ;//财产名称2
	private String propertyAdress2 ;//财产地址2
	private String propertyValue2 ;//市值2
	private String GpropertyValue2 ;//估计价值2
	private String serviceYear ;//服务年数
	private String supInformation ;//补充信息
	private String prop1 ;//备用属性1
	private String prop2 ;//备用属性2
	private String prop3 ;//备用属性3
	private String prop4 ;//备用属性4
	
	private String insuranceCompany1;// 寿险保险公司1
	private String period1;// 寿险保障期限1
	private String amnt1;// 寿险保险金额(万元)1
	private String CValiDate1;// 寿险生效日期1
	private String insuranceCompany2;// 寿险保险公司2
	private String period2;// 寿险保障期限2
	private String amnt2;// 寿险保险金额(万元)2
	private String CValiDate2;// 寿险生效日期2
	private String insuranceCompany3;// 寿险保险公司3
	private String period3;// 寿险保障期限3
	private String amnt3;// 寿险保险金额(万元)3
	private String CValiDate3;// 寿险生效日期3
	private String insuranceCompany4;// 意外险保险公司1
	private String period4;// 意外险保障期限1
	private String amnt4;// 意外险保险金额(万元)1
	private String CValiDate4;// 意外险生效日期1
	private String insuranceCompany5;// 意外险保险公司2
	private String period5;// 意外险保障期限2
	private String amnt5;// 意外险保险金额(万元)2
	private String CValiDate5;// 意外险生效日期2
	private String insureStatus;// 曾经保险状态0-被拒过，1-未被拒过
	private String partnerName;// 配偶姓名
	private String incomeSource;// 收入来源0-工资，1-奖金和分红，2-继承遗产，3-房屋租赁 ，4-投资收益，5-其它
	private String incomeSourceDetail;// 其它收入来源详述
	private String incomeSum;// 去年总计年收入0-<10万元，1-10万元，2-15万元，3-20万元 ，4-25万元，5-30万元，6-40万元，7->=50万元
	private String assetStatus;// 是否负债0-是，1-否
	private String debtKind1;// 债务种类1
	private String debtMoney1;// 债务金额1
	private String repayContent1;// 偿还条件及时间1
	private String debtKind2;// 债务种类2
	private String debtMoney2;// 债务金额2
	private String repayContent2;// 偿还条件及时间2
	private String debtKind3;// 债务种类3
	private String debtMoney3;// 债务金额3
	private String repayContent3;// 偿还条件及时间3
	private String workStatus;// 是否能正常劳动工作0-是，1-否
	private String operationStatus;// 曾或正接受任何外科手术或住院医疗 0-是，1-否
	private String moveDisorder;// 是否有行动或智能障碍等 0-是，1-否
	private String drugStatus;// 是否使用各类药物滥用成瘾等 0-是，1-否
	private String riskyBehaviour;// 是否经常从事危险活动的爱好或行为 0-是，1-否
	private String workPlaceStatus;// 是否因工作原因需要经常前往危险地区或国家 0-是，1-否
	
	private SDInformationInsured sdinformationInsured;//被保人
	public String getOrdID() {
		return ordID;
	}
	public void setOrdID(String ordID) {
		this.ordID = ordID;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getEffective() {
		return effective;
	}
	public void setEffective(String effective) {
		this.effective = effective;
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
	public String getRecognizeeArea3() {
		return recognizeeArea3;
	}
	public void setRecognizeeArea3(String recognizeeArea3) {
		this.recognizeeArea3 = recognizeeArea3;
	}
	public String getApplicantMobile() {
		return applicantMobile;
	}
	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}
	public String getCannualincome() {
		return Cannualincome;
	}
	public void setCannualincome(String cannualincome) {
		Cannualincome = cannualincome;
	}
	public String getCmisrevenues() {
		return Cmisrevenues;
	}
	public void setCmisrevenues(String cmisrevenues) {
		Cmisrevenues = cmisrevenues;
	}
	public String getLannualincome() {
		return Lannualincome;
	}
	public void setLannualincome(String lannualincome) {
		Lannualincome = lannualincome;
	}
	public String getLmisrevenues() {
		return Lmisrevenues;
	}
	public void setLmisrevenues(String lmisrevenues) {
		Lmisrevenues = lmisrevenues;
	}
	public String getLTannualincome() {
		return LTannualincome;
	}
	public void setLTannualincome(String lTannualincome) {
		LTannualincome = lTannualincome;
	}
	public String getLTmisrevenues() {
		return LTmisrevenues;
	}
	public void setLTmisrevenues(String lTmisrevenues) {
		LTmisrevenues = lTmisrevenues;
	}
	public String getCtotalincome() {
		return Ctotalincome;
	}
	public void setCtotalincome(String ctotalincome) {
		Ctotalincome = ctotalincome;
	}
	public String getLtotalincome() {
		return Ltotalincome;
	}
	public void setLtotalincome(String ltotalincome) {
		Ltotalincome = ltotalincome;
	}
	public String getLTtotalincome() {
		return LTtotalincome;
	}
	public void setLTtotalincome(String lTtotalincome) {
		LTtotalincome = lTtotalincome;
	}
	public String getInsurePurpose() {
		return insurePurpose;
	}
	public void setInsurePurpose(String insurePurpose) {
		this.insurePurpose = insurePurpose;
	}
	public String getOtherInsurePurpose() {
		return otherInsurePurpose;
	}
	public void setOtherInsurePurpose(String otherInsurePurpose) {
		this.otherInsurePurpose = otherInsurePurpose;
	}
	public String getLiveStatus() {
		return liveStatus;
	}
	public void setLiveStatus(String liveStatus) {
		this.liveStatus = liveStatus;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}
	public String getLivedTime() {
		return livedTime;
	}
	public void setLivedTime(String livedTime) {
		this.livedTime = livedTime;
	}
	public String getFamilySituation() {
		return familySituation;
	}
	public void setFamilySituation(String familySituation) {
		this.familySituation = familySituation;
	}
	public String getChildrenCount() {
		return childrenCount;
	}
	public void setChildrenCount(String childrenCount) {
		this.childrenCount = childrenCount;
	}
	public String getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(String peopleCount) {
		this.peopleCount = peopleCount;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarBrand1() {
		return carBrand1;
	}
	public void setCarBrand1(String carBrand1) {
		this.carBrand1 = carBrand1;
	}
	public String getCarBrand2() {
		return carBrand2;
	}
	public void setCarBrand2(String carBrand2) {
		this.carBrand2 = carBrand2;
	}
	public String getCarBrand3() {
		return carBrand3;
	}
	public void setCarBrand3(String carBrand3) {
		this.carBrand3 = carBrand3;
	}
	public String getPropertyName1() {
		return propertyName1;
	}
	public void setPropertyName1(String propertyName1) {
		this.propertyName1 = propertyName1;
	}
	public String getPropertyAdress1() {
		return propertyAdress1;
	}
	public void setPropertyAdress1(String propertyAdress1) {
		this.propertyAdress1 = propertyAdress1;
	}
	public String getPropertyValue1() {
		return propertyValue1;
	}
	public void setPropertyValue1(String propertyValue1) {
		this.propertyValue1 = propertyValue1;
	}
	public String getGpropertyValue1() {
		return GpropertyValue1;
	}
	public void setGpropertyValue1(String gpropertyValue1) {
		GpropertyValue1 = gpropertyValue1;
	}
	public String getPropertyName2() {
		return propertyName2;
	}
	public void setPropertyName2(String propertyName2) {
		this.propertyName2 = propertyName2;
	}
	public String getPropertyAdress2() {
		return propertyAdress2;
	}
	public void setPropertyAdress2(String propertyAdress2) {
		this.propertyAdress2 = propertyAdress2;
	}
	public String getPropertyValue2() {
		return propertyValue2;
	}
	public void setPropertyValue2(String propertyValue2) {
		this.propertyValue2 = propertyValue2;
	}
	public String getGpropertyValue2() {
		return GpropertyValue2;
	}
	public void setGpropertyValue2(String gpropertyValue2) {
		GpropertyValue2 = gpropertyValue2;
	}
	public String getServiceYear() {
		return serviceYear;
	}
	public void setServiceYear(String serviceYear) {
		this.serviceYear = serviceYear;
	}
	
	public String getSupInformation() {
		return supInformation;
	}
	public void setSupInformation(String supInformation) {
		this.supInformation = supInformation;
	}
	public String getProp1() {
		return prop1;
	}
	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}
	public String getProp2() {
		return prop2;
	}
	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}
	public String getProp3() {
		return prop3;
	}
	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}
	public String getProp4() {
		return prop4;
	}
	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getInsuranceCompany1() {
		return insuranceCompany1;
	}
	public void setInsuranceCompany1(String insuranceCompany1) {
		this.insuranceCompany1 = insuranceCompany1;
	}
	public String getPeriod1() {
		return period1;
	}
	public void setPeriod1(String period1) {
		this.period1 = period1;
	}
	public String getAmnt1() {
		return amnt1;
	}
	public void setAmnt1(String amnt1) {
		this.amnt1 = amnt1;
	}
	public String getCValiDate1() {
		return CValiDate1;
	}
	public void setCValiDate1(String cValiDate1) {
		CValiDate1 = cValiDate1;
	}
	public String getInsuranceCompany2() {
		return insuranceCompany2;
	}
	public void setInsuranceCompany2(String insuranceCompany2) {
		this.insuranceCompany2 = insuranceCompany2;
	}
	public String getPeriod2() {
		return period2;
	}
	public void setPeriod2(String period2) {
		this.period2 = period2;
	}
	public String getAmnt2() {
		return amnt2;
	}
	public void setAmnt2(String amnt2) {
		this.amnt2 = amnt2;
	}
	public String getCValiDate2() {
		return CValiDate2;
	}
	public void setCValiDate2(String cValiDate2) {
		CValiDate2 = cValiDate2;
	}
	public String getInsuranceCompany3() {
		return insuranceCompany3;
	}
	public void setInsuranceCompany3(String insuranceCompany3) {
		this.insuranceCompany3 = insuranceCompany3;
	}
	public String getPeriod3() {
		return period3;
	}
	public void setPeriod3(String period3) {
		this.period3 = period3;
	}
	public String getAmnt3() {
		return amnt3;
	}
	public void setAmnt3(String amnt3) {
		this.amnt3 = amnt3;
	}
	public String getCValiDate3() {
		return CValiDate3;
	}
	public void setCValiDate3(String cValiDate3) {
		CValiDate3 = cValiDate3;
	}
	public String getInsuranceCompany4() {
		return insuranceCompany4;
	}
	public void setInsuranceCompany4(String insuranceCompany4) {
		this.insuranceCompany4 = insuranceCompany4;
	}
	public String getPeriod4() {
		return period4;
	}
	public void setPeriod4(String period4) {
		this.period4 = period4;
	}
	public String getAmnt4() {
		return amnt4;
	}
	public void setAmnt4(String amnt4) {
		this.amnt4 = amnt4;
	}
	public String getCValiDate4() {
		return CValiDate4;
	}
	public void setCValiDate4(String cValiDate4) {
		CValiDate4 = cValiDate4;
	}
	public String getInsuranceCompany5() {
		return insuranceCompany5;
	}
	public void setInsuranceCompany5(String insuranceCompany5) {
		this.insuranceCompany5 = insuranceCompany5;
	}
	public String getPeriod5() {
		return period5;
	}
	public void setPeriod5(String period5) {
		this.period5 = period5;
	}
	public String getAmnt5() {
		return amnt5;
	}
	public void setAmnt5(String amnt5) {
		this.amnt5 = amnt5;
	}
	public String getCValiDate5() {
		return CValiDate5;
	}
	public void setCValiDate5(String cValiDate5) {
		CValiDate5 = cValiDate5;
	}
	public String getInsureStatus() {
		return insureStatus;
	}
	public void setInsureStatus(String insureStatus) {
		this.insureStatus = insureStatus;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getIncomeSource() {
		return incomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}
	public String getIncomeSourceDetail() {
		return incomeSourceDetail;
	}
	public void setIncomeSourceDetail(String incomeSourceDetail) {
		this.incomeSourceDetail = incomeSourceDetail;
	}
	public String getIncomeSum() {
		return incomeSum;
	}
	public void setIncomeSum(String incomeSum) {
		this.incomeSum = incomeSum;
	}
	public String getAssetStatus() {
		return assetStatus;
	}
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	public String getDebtKind1() {
		return debtKind1;
	}
	public void setDebtKind1(String debtKind1) {
		this.debtKind1 = debtKind1;
	}
	public String getDebtMoney1() {
		return debtMoney1;
	}
	public void setDebtMoney1(String debtMoney1) {
		this.debtMoney1 = debtMoney1;
	}
	public String getRepayContent1() {
		return repayContent1;
	}
	public void setRepayContent1(String repayContent1) {
		this.repayContent1 = repayContent1;
	}
	public String getDebtKind2() {
		return debtKind2;
	}
	public void setDebtKind2(String debtKind2) {
		this.debtKind2 = debtKind2;
	}
	public String getDebtMoney2() {
		return debtMoney2;
	}
	public void setDebtMoney2(String debtMoney2) {
		this.debtMoney2 = debtMoney2;
	}
	public String getRepayContent2() {
		return repayContent2;
	}
	public void setRepayContent2(String repayContent2) {
		this.repayContent2 = repayContent2;
	}
	public String getDebtKind3() {
		return debtKind3;
	}
	public void setDebtKind3(String debtKind3) {
		this.debtKind3 = debtKind3;
	}
	public String getDebtMoney3() {
		return debtMoney3;
	}
	public void setDebtMoney3(String debtMoney3) {
		this.debtMoney3 = debtMoney3;
	}
	public String getRepayContent3() {
		return repayContent3;
	}
	public void setRepayContent3(String repayContent3) {
		this.repayContent3 = repayContent3;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getMoveDisorder() {
		return moveDisorder;
	}
	public void setMoveDisorder(String moveDisorder) {
		this.moveDisorder = moveDisorder;
	}
	public String getDrugStatus() {
		return drugStatus;
	}
	public void setDrugStatus(String drugStatus) {
		this.drugStatus = drugStatus;
	}
	public String getRiskyBehaviour() {
		return riskyBehaviour;
	}
	public void setRiskyBehaviour(String riskyBehaviour) {
		this.riskyBehaviour = riskyBehaviour;
	}
	public String getWorkPlaceStatus() {
		return workPlaceStatus;
	}
	public void setWorkPlaceStatus(String workPlaceStatus) {
		this.workPlaceStatus = workPlaceStatus;
	}
	@OneToOne(fetch = FetchType.LAZY)
	public SDInformationInsured getSdinformationInsured() {
		return sdinformationInsured;
	}
	public void setSdinformationInsured(SDInformationInsured sdinformationInsured) {
		this.sdinformationInsured = sdinformationInsured;
	}
}
