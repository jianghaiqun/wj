package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationItemMainDao;
import cn.com.sinosoft.entity.InformationItemMain;
import cn.com.sinosoft.service.InformationItemMainService;

/**
 * Service实现类 - 运输信息
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
public class InformationItemMainServiceImpl extends BaseServiceImpl<InformationItemMain, String> implements InformationItemMainService {

	@Resource
	private InformationItemMainDao informationItemMainDao;
	@Resource
	public void setBaseDao(InformationItemMainDao informationItemMainDao) {
		super.setBaseDao(informationItemMainDao);
	}
}