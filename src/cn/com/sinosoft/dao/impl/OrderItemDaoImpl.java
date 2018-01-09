package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.OrderItemDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.OrderItem;

/**
 * Dao实现类 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD05ED0DBA7D4396E779CC3E07ED13EAF
 * ============================================================================
 */

@Repository
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, String> implements OrderItemDao {

	@Override
	public OrderItem getByOrP(String productId, String orderId) {
		String hql = "from OrderItem as orderItem where orderItem.productId = ? and orderItem.order.id = ?";
		return (OrderItem)getSession().createQuery(hql).setParameter(0, productId).setParameter(1, orderId).uniqueResult();
	}



}