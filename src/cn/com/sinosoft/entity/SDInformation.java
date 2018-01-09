package cn.com.sinosoft.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * 实体类：投被保人基本信息 ，关联orderItem,以及用户订单界面公共属性的Information
 *
 */



@Entity
public class SDInformation extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private String	informationSn	;	//	订单明细表编号
	private String	orderSn	;	//	订单编号
	private String	productId	;	//	产品编码
	private String	productOutCode	;	//	产品外部编码
	private String	productName	;	//	产品名称
	private String	discountRates	;	//	产品折扣率
	private String	productPrice	;	//	产品标准价
	private String	productDiscountPrice	;	//	产品折扣价
	private String	productHtmlFilePath	;	//	产品HTML静态文件路径
	private String	productQuantity	;	//	产品数量
	private String	insuranceCompany	;	//	保险公司
	private String	point	;	//	积分
	private String	pointStatus	;	//	积分状态
	private Date	startDate	;	//	保险起期
	private Date	endDate	;	//	保险止期
	private String	ensureLimitType	;	//	保障期限类型
	private String	ensureLimit	;	//	保障期限
	private String	ensure	;	//	保障(产品中心传过来的值)
	private String	chargeType	;	//	缴费类型
	private String	chargeYear	;	//	缴费年期
	private String  chargeYearName;
	private String	planCode	;	//	计划编码
	private String	planName	;	//	计划名称
	private String  subRiskType;//内部统计险种子类别
	private String	remark	;	//	备用字段
    private String  ensureDisplay;//保障期限显示值
	private String	appLevel	;	//	缴费方式
	private String	appLevelName	;	//	 
	private String  appType;//投保类型
	private String	appTypeName	;	//	
	private String	chargeDisplay	;	//	缴费年期显示值
	private String  riskType;//中类 
	private String  supKindCode;//保险公司险种编码
	private String  supRiskCode;//保险公司计划编码
	private String  primitiveProductTitle;//产品原始名称
	private String  textAge;//投保年龄（复杂产品用）
	private String policyNum;
	private String  appMult;//购买份数（详细页选择份数时使用）
	
	public String getChargeYearName() {
		return chargeYearName;
	}
	public void setChargeYearName(String chargeYearName) {
		this.chargeYearName = chargeYearName;
	}
	private SDOrder sdorder; 
	private Set<SDInformationDuty> sdinformationDutySet;//责任投保要素信息
	private Set<SDInformationAppnt> sdinformationappntSet;//投保人信息 
	private Set<SDInformationInsured> sdinformationinsuredSet;//被保人信息
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	public SDOrder getSdorder() {
		return sdorder;
	}
	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}
	@OneToMany(mappedBy = "sdinformation", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformationDuty> getSdinformationDutySet() {
		return sdinformationDutySet;
	}
	public void setSdinformationDutySet(Set<SDInformationDuty> sdinformationDutySet) {
		this.sdinformationDutySet = sdinformationDutySet;
	} 
	@OneToMany(mappedBy = "sdinformaiton", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformationAppnt> getSdinformationappntSet() {
		return sdinformationappntSet;
	}
	public void setSdinformationappntSet(
			Set<SDInformationAppnt> sdinformationappntSet) {
		this.sdinformationappntSet = sdinformationappntSet;
	}
	@OneToMany(mappedBy = "sdinformation", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformationInsured> getSdinformationinsuredSet() {
		return sdinformationinsuredSet;
	}
	public void setSdinformationinsuredSet(
			Set<SDInformationInsured> sdinformationinsuredSet) {
		this.sdinformationinsuredSet = sdinformationinsuredSet;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductOutCode() {
		return productOutCode;
	}
	public void setProductOutCode(String productOutCode) {
		this.productOutCode = productOutCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
    
	public String getDiscountRates() {
		return discountRates;
	}
	public void setDiscountRates(String discountRates) {
		this.discountRates = discountRates;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDiscountPrice() {
		return productDiscountPrice;
	}
	public void setProductDiscountPrice(String productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
	}
	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}
	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getPointStatus() {
		return pointStatus;
	}
	public void setPointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEnsureLimitType() {
		return ensureLimitType;
	}
	public void setEnsureLimitType(String ensureLimitType) {
		this.ensureLimitType = ensureLimitType;
	}
	public String getEnsureLimit() {
		return ensureLimit;
	}
	public void setEnsureLimit(String ensureLimit) {
		this.ensureLimit = ensureLimit;
	}
	public String getEnsure() {
		return ensure;
	}
	public void setEnsure(String ensure) {
		this.ensure = ensure;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getChargeYear() {
		return chargeYear;
	}
	public void setChargeYear(String chargeYear) {
		this.chargeYear = chargeYear;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	public String getSubRiskType() {
		return subRiskType;
	}
	public void setSubRiskType(String subRiskType) {
		this.subRiskType = subRiskType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEnsureDisplay() {
		return ensureDisplay;
	}
	public void setEnsureDisplay(String ensureDisplay) {
		this.ensureDisplay = ensureDisplay;
	}
	public String getAppLevel() {
		return appLevel;
	}
	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}
	public String getAppLevelName() {
		return appLevelName;
	}
	public void setAppLevelName(String appLevelName) {
		this.appLevelName = appLevelName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppTypeName() {
		return appTypeName;
	}
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	public String getChargeDisplay() {
		return chargeDisplay;
	}
	public void setChargeDisplay(String chargeDisplay) {
		this.chargeDisplay = chargeDisplay;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getSupKindCode() {
		return supKindCode;
	}
	public void setSupKindCode(String supKindCode) {
		this.supKindCode = supKindCode;
	}
	public String getSupRiskCode() {
		return supRiskCode;
	}
	public void setSupRiskCode(String supRiskCode) {
		this.supRiskCode = supRiskCode;
	}
	
	public String getPrimitiveProductTitle() {
		return primitiveProductTitle;
	}
	public void setPrimitiveProductTitle(String primitiveProductTitle) {
		this.primitiveProductTitle = primitiveProductTitle;
	}
	public String getTextAge() {
		return textAge;
	}
	public void setTextAge(String textAge) {
		this.textAge = textAge;
	}
	public String getPolicyNum() {
		return policyNum;
	}
	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}
	public String getAppMult() {
		return appMult;
	}
	public void setAppMult(String appMult) {
		this.appMult = appMult;
	}
	
}