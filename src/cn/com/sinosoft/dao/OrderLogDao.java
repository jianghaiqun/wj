package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.OrderLog;

/**
 * Dao接口 - 订单日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1A7FF03BB754C9BD9866FF289083878A
 * ============================================================================
 */

public interface OrderLogDao extends BaseDao<OrderLog, String> {

	public OrderLog getByOrderId(String orderId);

}