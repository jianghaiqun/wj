/**
 * 
 */
package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * @author wangcaiyun
 *
 */
@Entity
public class TradeSummaryInfo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 925614743875989113L;
	
	private String	PaySn	;//	商家订单号
	private String	TradeSn	;//	支付流水号
	private String	TradeResult	;//	支付结果
	private String	OrderSns	;//	订单号
	private String	CouponSumAmount	;//	优惠券优惠金额
	private String	ActivitySumAmount	;//	活动优惠金额
	private String	PointSumAmount	;//	积分抵值金额
	private String	CouponSn	;//	优惠券码
	private String	PayType	;//	支付方式
	private String	PayTypeName	;//	支付方式名称
	private String	TotalAmount	;//	总金额
	private String	TradeAmount	;//	交易金额
	
	public String getPaySn() {
		return PaySn;
	}
	public void setPaySn(String paySn) {
		PaySn = paySn;
	}
	public String getTradeSn() {
		return TradeSn;
	}
	public void setTradeSn(String tradeSn) {
		TradeSn = tradeSn;
	}
	public String getTradeResult() {
		return TradeResult;
	}
	public void setTradeResult(String tradeResult) {
		TradeResult = tradeResult;
	}
	public String getOrderSns() {
		return OrderSns;
	}
	public void setOrderSns(String orderSns) {
		OrderSns = orderSns;
	}
	public String getCouponSumAmount() {
		return CouponSumAmount;
	}
	public void setCouponSumAmount(String couponSumAmount) {
		CouponSumAmount = couponSumAmount;
	}
	public String getActivitySumAmount() {
		return ActivitySumAmount;
	}
	public void setActivitySumAmount(String activitySumAmount) {
		ActivitySumAmount = activitySumAmount;
	}
	public String getPointSumAmount() {
		return PointSumAmount;
	}
	public void setPointSumAmount(String pointSumAmount) {
		PointSumAmount = pointSumAmount;
	}
	public String getCouponSn() {
		return CouponSn;
	}
	public void setCouponSn(String couponSn) {
		CouponSn = couponSn;
	}
	public String getPayType() {
		return PayType;
	}
	public void setPayType(String payType) {
		PayType = payType;
	}
	public String getPayTypeName() {
		return PayTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		PayTypeName = payTypeName;
	}
	public String getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getTradeAmount() {
		return TradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		TradeAmount = tradeAmount;
	}
}
