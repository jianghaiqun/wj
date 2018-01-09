package cn.com.sinosoft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CouponInfo extends BaseEntity{
	
	/**优惠劵详细表
	 *  wangchangyang
	 */
	private static final long serialVersionUID = 4021488779042163823L;
	
	private String couponSn;//优惠券号
	private String riskCode;//优惠险种
	private String insuranceCompany;//优惠公司
	private String product;//优惠产品
	private String provideUser;//发放人
	private String purpose;//用途
	private BigDecimal parValue;//面值
	private BigDecimal  payAmount;//消费金额
	private String  useTimes;//使用次数
	private String direction;//使用说明
	private Date startTime;//起始时间
	private Date endTime;//终止时间
	//（0：“未使用”1：“已使用”2：“已发放”3：“已撤单”4：“冻结”5：“过期”6：“已删除”）
	private String status;//优惠劵状态 
	private String orderSn;// 使用单号
	private Date payTime;//使用时间
	private String  memberId;// 所属会员
	private String  mail;// 所属邮箱
	private String mobile;//所属手机
	private String createUser;//申请人
	private String modifyUser;//修改人
	private String prop1;//追加交易号
	private String prop2;//发放时间
	private String prop3;//优惠券类型  空或01：非折扣券 02：折扣券
	private String prop4;//折扣
	private String channelSn;//渠道
	private String maxDeduction;//最高抵扣金额
	
	public String getChannelSn() {
		return channelSn;
	}
	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCouponSn() {
		return couponSn;
	}
	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProvideUser() {
		return provideUser;
	}
	public void setProvideUser(String provideUser) {
		this.provideUser = provideUser;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public BigDecimal getParValue() {
		return parValue;
	}
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(String useTimes) {
		this.useTimes = useTimes;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getMaxDeduction() {
		return maxDeduction;
	}
	public void setMaxDeduction(String maxDeduction) {
		this.maxDeduction = maxDeduction;
	}
	
}
