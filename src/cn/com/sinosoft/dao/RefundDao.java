package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Refund;

/**
 * Dao接口 - 退款
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA2811FC2485900DF874F20212E3C615B
 * ============================================================================
 */

public interface RefundDao extends BaseDao<Refund, String> {
	
	/**
	 * 获取最后生成的退款编号
	 * 
	 * @return 收款编号
	 */
	public String getLastRefundSn();
	
	/**
	 * 根据退款编号获取对象（若对象不存在，则返回null）
	 * 
	 * @return 退款对象
	 */
	public Refund getRefundByRefundSn(String refundSn);


}