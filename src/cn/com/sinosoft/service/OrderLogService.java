package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.OrderLog;

/**
 * Service接口 - 订单日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTBAEB0B3C46002E1B892660497F9EA34E
 * ============================================================================
 */

public interface OrderLogService extends BaseService<OrderLog, String> {

	public OrderLog getByOrderId(String orderId);

}