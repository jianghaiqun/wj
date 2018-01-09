package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationItemCargoDao;
import cn.com.sinosoft.entity.InformationItemCargo;
import cn.com.sinosoft.service.InformationItemCargoService;

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
public class InformationItemCargoServiceImpl extends BaseServiceImpl<InformationItemCargo, String> implements InformationItemCargoService {

	@Resource
	private InformationItemCargoDao informationItemCargoDao;
	@Resource
	public void setBaseDao(InformationItemCargoDao informationItemCargoDao) {
		super.setBaseDao(informationItemCargoDao);
	}
}