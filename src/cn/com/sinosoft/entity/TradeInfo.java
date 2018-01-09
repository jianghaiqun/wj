package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tradeinfo")
public class TradeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5645831032128954883L;

	/**
	 * 商户订单号
	 */
	private String paySn;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 更新时间
	 */
	private Date modifyDate;
	
	/**
	 * 订单编号
	 */
	private String	orderSn;
	
	/**
	 * 优惠券码或活动码
	 */
	private String	couponSn;
	
	/**
	 * 交易金额
	 */
	private String	totalAmnout;
	
	/**
	 * 积分.
	 */
	private String integral;
	
	/**
	 * 支付类型.
	 */
	private String payType;
	
	/**
	 * 备用字段1,记录是否支付.
	 */
	private String	remark1;
	
	/**
	 * 备用字段2
	 */
	private String	remark2;

	@Id
	@Column(length = 30, nullable = true)
	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getOrderSn() {
		return orderSn;
	}
	
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getCouponSn() {
		return couponSn;
	}

	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}
	
	public String getTotalAmnout() {
		return totalAmnout;
	}

	public void setTotalAmnout(String totalAmnout) {
		this.totalAmnout = totalAmnout;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	

}
