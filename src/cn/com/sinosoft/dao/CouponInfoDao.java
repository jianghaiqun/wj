package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.CouponInfo;

import com.sinosoft.framework.data.DataTable;

public interface CouponInfoDao extends BaseDao<CouponInfo, String> {
	
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
	 * 通过产品ID从产品中心获取产品的险种编码
	 *            
	 * @return 查询结果集
	 */
	public DataTable getRiskCodeFromProductCenter(String productId);
	
	/**
	 * 通过产品ID从产品中心获取产品的公司编码
	 *            
	 * @return 查询结果集
	 */
	public String getCompanyCodeFromProductCenter(String productId);
	
}
