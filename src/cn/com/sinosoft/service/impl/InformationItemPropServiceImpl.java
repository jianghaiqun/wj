package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationItemPropDao;
import cn.com.sinosoft.entity.InformationItemProp;
import cn.com.sinosoft.service.InformationItemPropService;

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
public class InformationItemPropServiceImpl extends BaseServiceImpl<InformationItemProp, String> implements InformationItemPropService {

	@Resource
	private InformationItemPropDao informationItemPropDao;
	@Resource
	public void setBaseDao(InformationItemPropDao informationItemPropDao) {
		super.setBaseDao(informationItemPropDao);
	}
}