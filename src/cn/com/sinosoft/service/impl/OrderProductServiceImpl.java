package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.OrderProductDao;
import cn.com.sinosoft.entity.OrderProduct;
import cn.com.sinosoft.service.OrderProductService;

@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct, String> implements OrderProductService {
	@Resource
	public void setBaseDao(OrderProductDao orderProductDao) {
		super.setBaseDao(orderProductDao);
	}
} 