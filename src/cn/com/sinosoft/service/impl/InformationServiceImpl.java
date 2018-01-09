package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ArticleDao;
import cn.com.sinosoft.dao.InformationDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.service.InformationService;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class InformationServiceImpl extends BaseServiceImpl<Information, String> implements InformationService {

	@Resource
	private InformationDao informationDao;
	@Resource
	public void setBaseDao(InformationDao informationDao) {
		super.setBaseDao(informationDao);
	}


	@Override
	public Information getByProduct(String id,String orderId) {
		return informationDao.getByProduct(id,orderId);
	}
	@Override
	public Information getByOrP(String orderItemId) {
		return informationDao.getByOrP(orderItemId);
	}

}