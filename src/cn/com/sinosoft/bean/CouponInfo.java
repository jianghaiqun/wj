package cn.com.sinosoft.bean;

public class CouponInfo {

	/** 优惠券码 */
	private String couponSn;
	/** 优惠券面值 */
	private String parValue;
	/** 使用说明 */
	private String direction;
	/** 生效起期 */
	private String startTime;
	/** 生效止期 */
	private String endTime;
	/** 订单号 */
	private String orderSn;
	/** 支付时间 */
	private String payTime;
	/** 序号 */
	private String serialno;
	/** 优惠券单位：元/折 */
	private String unit;
	/** 优惠券简称 */
	private String shortName;
	
	public String getCouponSn() {
		return couponSn;
	}
	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}

	public String getParValue() {
		return parValue;
	}
	public void setParValue(String parValue) {
		this.parValue = parValue;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
