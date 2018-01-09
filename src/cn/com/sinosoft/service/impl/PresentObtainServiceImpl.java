package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ArticleDao;
import cn.com.sinosoft.dao.OrderItemDao;
import cn.com.sinosoft.dao.PresentObtainDao;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.PresentObtain;
import cn.com.sinosoft.service.OrderItemService;
import cn.com.sinosoft.service.PresentObtainService;

/**
 * Service实现类 - 兑换记录
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
public class PresentObtainServiceImpl extends BaseServiceImpl<PresentObtain, String> implements PresentObtainService {

	@Resource
	private OrderItemDao orderItemDao;
	@Resource
	public void setBaseDao(PresentObtainDao presentObtainDao) {
		super.setBaseDao(presentObtainDao);
	}
//	public OrderItem getByOrP(String productId, String orderId) {
//		return orderItemDao.getByOrP(productId,orderId);
//	}


}