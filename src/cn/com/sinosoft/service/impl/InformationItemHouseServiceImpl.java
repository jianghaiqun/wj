package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationItemHouseDao;
import cn.com.sinosoft.entity.InformationItemHouse;
import cn.com.sinosoft.service.InformationItemHouseService;

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
	public class InformationItemHouseServiceImpl extends BaseServiceImpl<InformationItemHouse, String> implements InformationItemHouseService {
	@Resource
	private InformationItemHouseDao informationItemHouseDao;
	@Resource
	public void setBaseDao(InformationItemHouseDao informationItemHouseDao) {
		super.setBaseDao(informationItemHouseDao);
	}
}