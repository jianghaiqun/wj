package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.DeliveryItemDao;
import cn.com.sinosoft.entity.DeliveryItem;
import cn.com.sinosoft.service.DeliveryItemService;

/**
 * Service实现类 - 发货项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItem, String> implements DeliveryItemService {

	@Resource
	public void setBaseDao(DeliveryItemDao deliveryItemDao) {
		super.setBaseDao(deliveryItemDao);
	}

}