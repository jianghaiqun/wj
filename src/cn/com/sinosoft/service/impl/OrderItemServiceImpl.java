package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ArticleDao;
import cn.com.sinosoft.dao.OrderItemDao;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.service.OrderItemService;

/**
 * Service实现类 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT3CCC6CA8E9A226C1626A7887BB102AC6
 * ============================================================================
 */

@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, String> implements OrderItemService {

	@Resource
	private OrderItemDao orderItemDao;
	@Resource
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}
	public OrderItem getByOrP(String productId, String orderId) {
		return orderItemDao.getByOrP(productId,orderId);
	}


}