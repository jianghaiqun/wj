package cn.com.sinosoft.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;



/**
 * 实体类 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT112C95F35897FDDCA9E38A76E6DE9B8C
 * ============================================================================
 */

@Entity
@Table(name = "sdorders")
public class SDOrder extends BaseEntity { 

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8693350675856123182L;
	public static final int DEFAULT_ORDER_LIST_PAGE_SIZE =11;// 订单列表默认每页显示数

	// 订单状态（0未处理,1已处理,2已完成、3已取消,4暂存、5待支付、6处理中、7已支付,8自动取消,9已撤销,10有撤销,11已退保,12有退保,13已作废,14有作废）
	public enum SDOrderStatus {
		unprocessed, processed, completed, invalid,temptorysave,prepay, processe, paid,autoinvalid,cancel,partcancel,surrender,partsurrender,change,partchange
	};

	// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
	public enum SDPayStatus {
		unpaid, partPayment, paid, partRefund, refunded
	};
	
	// 投保状态（已投保、未投保） 
	/*public enum InsureStatus {
		insured,uninsured
	};*/
	private String	orderSn	;//	订单编号
	private String	memberId	;//	会员编号
	private SDOrderStatus	orderStatus	;//	订单状态
	private String	payType	;//	支付类型
	private SDPayStatus	payStatus	;//	支付状态
	private Integer	productNum	;//	商品总数
	private BigDecimal	productTotalPrice	;//	商品总价格 
	private String	discountRates	;//	订单折扣费率
	private String	discountAmount	;//	订单优惠金额
	private BigDecimal	totalAmount	;//	订单总额
	private BigDecimal	payAmount	;//	已付金额
	private String	paySn	;//	支付流水号
	private String	remark	;//	备用字段
	private String tbTradeSeriNo;//淘宝交易流水号
	private String tbComCode ;//淘宝店铺号
	private SDOrderItem sdorderitem;//订单项
	private Set<SDInformation> sdinformationSet;//订单详细表（产品表）
	private Set<SDInformationRiskType> sdinformationrisktypeSet;//保单信息表
	private String couponSn;//优惠劵号
	private String offsetPoint;//订单抵消积分
	private Long commentId;//评论ID
	private String orderCoupon;//订单优惠金额
	private String orderIntegral;//订单积分抵值金额
	private String sumCoupon;//交易总优惠金额
	private String sumIntegral;//交易总积分抵值金额
	private String  activitySn;//活动编码
	private String  orderActivity;//订单活动优惠金额
	private String  payPrice;//订单实际支付金额
	private String  sumActivity;//活动总优惠金额
	private String channelsn;//订单来源 wap
	private String dellFlag;//订单是否已删除标志位
	private String diyActivitySn;//自定义活动号
	private String diyActivityDescription;//自定义活动描述
	private String renewalId;//续保id
	
	@OneToOne(mappedBy = "sdorder", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public SDOrderItem getSdorderitem() {
		return sdorderitem;
	}
	public void setSdorderitem(SDOrderItem sdorderitem) {
		this.sdorderitem = sdorderitem;
	}
	@OneToMany(mappedBy = "sdorder", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformation> getSdinformationSet() {
		return sdinformationSet;
	}
	public void setSdinformationSet(Set<SDInformation> sdinformationSet) {
		this.sdinformationSet = sdinformationSet;
	}
	@OneToMany(mappedBy = "sdorder", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDInformationRiskType> getSdinformationrisktypeSet() {
		return sdinformationrisktypeSet;
	}
	public void setSdinformationrisktypeSet(
			Set<SDInformationRiskType> sdinformationrisktypeSet) {
		this.sdinformationrisktypeSet = sdinformationrisktypeSet;
	}
	
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public SDOrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(SDOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public SDPayStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(SDPayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public String getDiscountRates() {
		return discountRates;
	}
	public void setDiscountRates(String discountRates) {
		this.discountRates = discountRates;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getPaySn() {
		return paySn;
	}
	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTbTradeSeriNo() {
		return tbTradeSeriNo;
	}
	public void setTbTradeSeriNo(String tbTradeSeriNo) {
		this.tbTradeSeriNo = tbTradeSeriNo;
	}
	public String getTbComCode() {
		return tbComCode;
	}
	public void setTbComCode(String tbComCode) {
		this.tbComCode = tbComCode;
	}
	public String getCouponSn() {
		return couponSn;
	}
	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}
	public String getOffsetPoint() {
		return offsetPoint;
	}
	public void setOffsetPoint(String offsetPoint) {
		this.offsetPoint = offsetPoint;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getOrderCoupon() {
		return orderCoupon;
	}
	public void setOrderCoupon(String orderCoupon) {
		this.orderCoupon = orderCoupon;
	}
	public String getOrderIntegral() {
		return orderIntegral;
	}
	public void setOrderIntegral(String orderIntegral) {
		this.orderIntegral = orderIntegral;
	}
	public String getSumCoupon() {
		return sumCoupon;
	}
	public void setSumCoupon(String sumCoupon) {
		this.sumCoupon = sumCoupon;
	}
	public String getSumIntegral() {
		return sumIntegral;
	}
	public void setSumIntegral(String sumIntegral) {
		this.sumIntegral = sumIntegral;
	}
	public String getActivitySn() {
		return activitySn;
	}
	public void setActivitySn(String activitySn) {
		this.activitySn = activitySn;
	}
	public String getOrderActivity() {
		return orderActivity;
	}
	public void setOrderActivity(String orderActivity) {
		this.orderActivity = orderActivity;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getSumActivity() {
		return sumActivity;
	}
	public void setSumActivity(String sumActivity) {
		this.sumActivity = sumActivity;
	}
	public String getChannelsn() {
		return channelsn;
	}
	public void setChannelsn(String channelsn) {
		this.channelsn = channelsn;
	}
	public String getDellFlag() {
		return dellFlag;
	}
	public void setDellFlag(String dellFlag) {
		this.dellFlag = dellFlag;
	}
	public String getDiyActivitySn() {
		return diyActivitySn;
	}
	public void setDiyActivitySn(String diyActivitySn) {
		this.diyActivitySn = diyActivitySn;
	}
	public String getDiyActivityDescription() {
		return diyActivityDescription;
	}
	public void setDiyActivityDescription(String diyActivityDescription) {
		this.diyActivityDescription = diyActivityDescription;
	}
	public String getRenewalId() {
		return renewalId;
	}
	public void setRenewalId(String renewalId) {
		this.renewalId = renewalId;
	}
}