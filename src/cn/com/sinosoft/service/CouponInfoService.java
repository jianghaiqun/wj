package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.SDOrder;

public interface CouponInfoService extends BaseService<CouponInfo, String> {
	
	/**
	 * 通过订单号获取优惠劵对象
	 *            
	 * @return 优惠劵对象
	 */
	public CouponInfo getCouponInfoByOrderSn(String orderSn);
	
	/**
	 * 通过优惠劵号获取优惠劵对象
	 *            
	 * @return 优惠劵对象
	 */
	public CouponInfo getCouponInfoByCouponSn(String couponSn);
	
	/**
	 * 点击使用优惠劵时，优惠劵验证方法
	 *            
	 * @return 验证结果
	 */
	public String couponVerify(CouponInfo coupon,String productId,SDOrder sdorder);
	
	/**
	 * 支付订单时，优惠劵验证方法
	 *            
	 * @return 验证结果
	 */
	public boolean couponVerifyForPay(CouponInfo coupon);
}
