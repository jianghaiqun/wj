package cn.com.sinosoft.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDInformationDutyDao;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.service.SDInformationDutyService;

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
public class SDInformationDutyServiceImpl extends BaseServiceImpl<SDInformationDuty, String> implements SDInformationDutyService {

	@Resource
	private SDInformationDutyDao sdinformationDutyDao;
	@Resource
	public void setBaseDao(SDInformationDutyDao sdinformationDutyDao) {
		super.setBaseDao(sdinformationDutyDao);
	}
	@Override
	public List<SDInformationDuty> getByOrderSn(String orderSn) {
		return sdinformationDutyDao.getByOrderSn(orderSn);
	}
}