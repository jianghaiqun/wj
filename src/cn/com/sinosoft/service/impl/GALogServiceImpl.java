package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.GALogDao;
import cn.com.sinosoft.entity.GALog;
import cn.com.sinosoft.service.GALogService;
/**
 * Service实现类 - GA日志
 * ============================================================================
 * KEY:SINOSOFT1F694A92A7CFA4B05E87616ADCA7D169
 * ============================================================================
 */
@Service
public class GALogServiceImpl extends BaseServiceImpl<GALog, String> implements GALogService {
	
	@Resource
	private GALogDao gaLogDao;
	
	@Resource
	public void setBaseDao(GALogDao gaLogDao) {
		super.setBaseDao(gaLogDao);
	}

	@Override
	public GALog getGALogByOrderSn(String orderSn) {
		return gaLogDao.getGALogByOrderSn(orderSn);
	}
	
	public GALog getGALogByMemberId(String memberId) {
		return gaLogDao.getGALogByMemberId(memberId);
	}
}
