package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.DeliveryCorpDao;
import cn.com.sinosoft.entity.DeliveryCorp;
import cn.com.sinosoft.service.DeliveryCorpService;

/**
 * Service实现类 - 物流公司
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF20B6E705344AD3757A3740CDC66E896
 * ============================================================================
 */

@Service
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, String> implements DeliveryCorpService {

	@Resource
	public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
		super.setBaseDao(deliveryCorpDao);
	}

}
