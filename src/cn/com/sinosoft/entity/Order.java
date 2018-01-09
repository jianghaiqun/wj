package cn.com.sinosoft.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import cn.com.sinosoft.util.SystemConfigUtil;


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
@Table(name = "orders")
public class Order extends BaseEntity {

	private static final long serialVersionUID = -8541323033439515148L;

	public static final int DEFAULT_ORDER_LIST_PAGE_SIZE =11;// 订单列表默认每页显示数

	// 订单状态（未处理,已处理,已完成、已取消,暂存、待支付、处理中、已支付,自动取消,已撤销,有撤销,已退保,有退保,已作废,有作废）
	public enum OrderStatus {
		unprocessed, processed, completed, invalid,temptorysave,prepay, processe, paid,autoinvalid,cancel,partcancel,surrender,partsurrender,change,partchange
	};

	// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
	public enum PaymentStatus {
		unpaid, partPayment, paid, partRefund, refunded
	};
	
	// 投保状态（已投保、未投保）
	public enum InsureStatus {
		insured,uninsured
	};
	private String memberId;// 订单编号
	private String orderSn;// 订单编号
	private InsureStatus insureStatus;// 支付方式名称
    private String policyNumber;//保单号
    private OrderStatus orderStatus;// 订单状态
	private PaymentStatus paymentStatus;// 支付状态
	private String paymentConfigName;// 支付方式名称
	private BigDecimal productTotalPrice;// 商品总价格
	private BigDecimal paymentFee;// 支付费用
	private BigDecimal totalAmount;// 订单总额
	private BigDecimal paidAmount;// 已付金额
	private Integer productTotalQuantity;// 商品总数
	private String memo;// 附言
//	private Member member;// 会员
	private Set<OrderLog> orderLogSet;// 订单日志
//	private Set<Refund> refundSet;// 退款
	private Set<OrderItem> orderItemSet;// 订单项
	private String productId;// 产品号
	private String productName;// 产品名称
	private String insuranceCompany;// 保险公司名称
	private String insuranceCompanySn;// 保险公司代码
	private String outRiskCode;
	private String eRiskType; //险种类别
	private String subRiskTypeCode; //险种子类
    private int point = 0;//未登录 积分存放处
	private String pointFrom;
	private BigDecimal amntPrice;//保额
	private String pointStatus;//积分状态
	private String tpySn;// 订单编号
	private String tpySysSn;//太平洋系统订单号
	private String tpySysPaySn;//太平洋系统支付流水号
	private String insuranceSn;//险种编码
	private String discountRates;//折扣费率
	private BigDecimal currentTermAmount; //原始保费
	private String brkRiskCode;//计划编码
	private String brkRiskName;//计划名称
	private String UWCheckFlag;//核保标记'Y'表示曾经核保通过

	public String getUWCheckFlag() {
		return UWCheckFlag;
	}

	public void setUWCheckFlag(String UWCheckFlag) {
		this.UWCheckFlag = UWCheckFlag;
	}

	/**
	 * @return the brkRiskName
	 */
	public String getBrkRiskName() {
		return brkRiskName;
	}

	/**
	 * @param brkRiskName the brkRiskName to set
	 */
	public void setBrkRiskName(String brkRiskName) {
		this.brkRiskName = brkRiskName;
	}

	/**
	 * @return the discountRates
	 */
	public String getDiscountRates() {
		return discountRates;
	}

	/**
	 * @param discountRates the discountRates to set
	 */
	public void setDiscountRates(String discountRates) {
		this.discountRates = discountRates;
	}
	/**
	 * @return the brkRiskCode
	 */
	public String getBrkRiskCode() {
		return brkRiskCode;
	}

	/**
	 * @param brkRiskCode the brkRiskCode to set
	 */
	public void setBrkRiskCode(String brkRiskCode) {
		this.brkRiskCode = brkRiskCode;
	}

	/**
	 * @return the currentTermAmount
	 */
	public BigDecimal getCurrentTermAmount() {
		return currentTermAmount;
	}

	/**
	 * @param currentTermAmount the currentTermAmount to set
	 */
	public void setCurrentTermAmount(BigDecimal currentTermAmount) {
		this.currentTermAmount = currentTermAmount;
	}

	/**
	 * @return the insuranceSn
	 */
	public String getInsuranceSn() {
		return insuranceSn;
	}

	/**
	 * @param insuranceSn the insuranceSn to set
	 */
	public void setInsuranceSn(String insuranceSn) {
		this.insuranceSn = insuranceSn;
	}

	/**
	 * @return the tpySysSn
	 */
	public String getTpySysSn() {
		return tpySysSn;
	}

	/**
	 * @param tpySysSn the tpySysSn to set
	 */
	public void setTpySysSn(String tpySysSn) {
		this.tpySysSn = tpySysSn;
	}

	/**
	 * @return the tpySysPaySn
	 */
	public String getTpySysPaySn() {
		return tpySysPaySn;
	}

	/**
	 * @param tpySysPaySn the tpySysPaySn to set
	 */
	public void setTpySysPaySn(String tpySysPaySn) {
		this.tpySysPaySn = tpySysPaySn;
	}

	/**
	 * @return the tpySn
	 */
	public String getTpySn() {
		return tpySn;
	}

	/**
	 * @param tpySn the tpySn to set
	 */
	public void setTpySn(String tpySn) {
		this.tpySn = tpySn;
	}

	public InsureStatus getInsureStatus() {
		return insureStatus;
	}

	public void setInsureStatus(InsureStatus insureStatus) {
		this.insureStatus = insureStatus;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	private Boolean orderValid = false;//订单提交后生效，等待付款
	public Boolean getOrderValid() {
		return orderValid;
	}
	
	public void setOrderValid(Boolean orderValid) {
		this.orderValid = orderValid;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(unique = true, updatable = false, nullable = false)
	public String getOrderSn() {
		return orderSn;
	}
	
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Enumerated
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}
	
	// 精度处理
	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
	}
	
	@Column(precision = 15, scale = 5)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	// 精度处理
	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SystemConfigUtil.getOrderScaleBigDecimal(paymentFee);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	// 精度处理
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = SystemConfigUtil.getOrderScaleBigDecimal(totalAmount);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	
	// 精度处理
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = SystemConfigUtil.getOrderScaleBigDecimal(paidAmount);
	}
//	@Column(nullable = false)
	public String getPaymentConfigName() {
		return paymentConfigName;
	}
	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}

	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	public Member getMember() {
//		return member;
//	}
//
//	public void setMember(Member member) {
//		this.member = member;
//	}
	
//	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//	@Cascade(value = { CascadeType.DELETE })
//	@OrderBy("createDate desc")
//	public Set<Refund> getRefundSet() {
//		return refundSet;
//	}
//
//	public void setRefundSet(Set<Refund> refundSet) {
//		this.refundSet = refundSet;
//	}
	/**
	 * @return the eRiskType
	 */
	public String geteRiskType() {
		return eRiskType;
	}

	/**
	 * @param eRiskType the eRiskType to set
	 */
	public void seteRiskType(String eRiskType) {
		this.eRiskType = eRiskType;
	}

	/**
	 * @return the subRiskTypeCode
	 */
	public String getSubRiskTypeCode() {
		return subRiskTypeCode;
	}

	/**
	 * @param subRiskTypeCode the subRiskTypeCode to set
	 */
	public void setSubRiskTypeCode(String subRiskTypeCode) {
		this.subRiskTypeCode = subRiskTypeCode;
	}
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

	public void setPointFrom(String pointFrom) {
		this.pointFrom = pointFrom;
	}

	public String getPointFrom() {
		return pointFrom;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	
	public String getInsuranceCompanySn() {
		return insuranceCompanySn;
	}
	public void setInsuranceCompanySn(String insuranceCompanySn) {
		this.insuranceCompanySn = insuranceCompanySn;
	}

	public void setAmntPrice(BigDecimal amntPrice) {
		this.amntPrice = amntPrice;
	}

	public BigDecimal getAmntPrice() {
		return amntPrice;
	}
	public String getOutRiskCode() {
		return outRiskCode;
	}
	public void setOutRiskCode(String outRiskCode) {
		this.outRiskCode = outRiskCode;
	}

	public String getPointStatus() {
		return pointStatus;
	}

	public void setPointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
	}
	
}