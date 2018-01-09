package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationAppntDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.service.InformationAppntService;

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
public class InformationAppntServiceImpl extends BaseServiceImpl<InformationAppnt, String> implements InformationAppntService {

	@Resource
	private InformationAppntDao informationAppntDao;
	@Resource
	public void setBaseDao(InformationAppntDao informationAppntDao) {
		super.setBaseDao(informationAppntDao);
	}
	@Override
	public InformationAppnt getByOrParentId(String informationId) {
		return informationAppntDao.getByOrParentId(informationId);
	}
}