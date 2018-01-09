package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationBnfDao;
import cn.com.sinosoft.entity.InformationBnf;
import cn.com.sinosoft.service.InformationBnfService;

/**
 * Service实现类 - 受益人
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
public class InformationBnfServiceImpl extends BaseServiceImpl<InformationBnf, String> implements InformationBnfService {

	@Resource
	private InformationBnfDao informationBnfDao;
	@Resource
	public void setBaseDao(InformationBnfDao informationBnfDao) {
		super.setBaseDao(informationBnfDao);
	}
}