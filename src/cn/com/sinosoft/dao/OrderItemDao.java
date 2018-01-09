package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.OrderItem;

/**
 * Dao接口 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD5A534BF01E9EC35D3DC732D4C8FF498
 * ============================================================================
 */

public interface OrderItemDao extends BaseDao<OrderItem, String> {

	public OrderItem getByOrP(String productId, String orderId);



}