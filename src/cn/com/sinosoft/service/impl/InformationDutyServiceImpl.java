package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationDutyDao;
import cn.com.sinosoft.dao.InformationRiskTypeDao;
import cn.com.sinosoft.entity.InformationDuty;
import cn.com.sinosoft.service.InformationDutyService;

/**
 * Service实现类 - 责任
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
public class InformationDutyServiceImpl extends BaseServiceImpl<InformationDuty, String> implements InformationDutyService {

	@Resource
	private InformationDutyDao informationDutyDao;
	@Resource
	public void setBaseDao(InformationDutyDao informationDutyDao) {
		super.setBaseDao(informationDutyDao);
	}
}