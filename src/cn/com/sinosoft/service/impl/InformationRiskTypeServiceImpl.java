package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationRiskTypeDao;
import cn.com.sinosoft.entity.InformationRiskType;
import cn.com.sinosoft.service.InformationRiskTypeService;

/**
 * Service实现类 - 险种
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
public class InformationRiskTypeServiceImpl extends BaseServiceImpl<InformationRiskType, String> implements InformationRiskTypeService {

	@Resource
	private InformationRiskTypeDao informationRiskTypeDao;
	@Resource
	public void setBaseDao(InformationRiskTypeDao informationRiskTypeDao) {
		super.setBaseDao(informationRiskTypeDao);
	}
}