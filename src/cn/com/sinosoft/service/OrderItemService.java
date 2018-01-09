package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.OrderItem;

/**
 * Service接口 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD8793F2D579C64707D76ED2D3AEEE96C
 * ============================================================================
 */

public interface OrderItemService extends BaseService<OrderItem, String> {

	public OrderItem getByOrP(String productId, String orderId);


}