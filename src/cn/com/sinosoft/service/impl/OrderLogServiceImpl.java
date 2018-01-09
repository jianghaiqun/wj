package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.OrderLogDao;
import cn.com.sinosoft.dao.ProductDao;
import cn.com.sinosoft.entity.OrderLog;
import cn.com.sinosoft.service.OrderLogService;

/**
 * Service实现类 - 订单日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT99551A0E5292A6C03298AFBE162F1450
 * ============================================================================
 */

@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, String> implements OrderLogService {

	@Resource
	private OrderLogDao orderLogDao;
	@Resource
	public void setBaseDao(OrderLogDao orderLogDao) {
		super.setBaseDao(orderLogDao);
	}

	@Override
	public OrderLog getByOrderId(String orderId) {
		return orderLogDao.getByOrderId(orderId);
	}

}