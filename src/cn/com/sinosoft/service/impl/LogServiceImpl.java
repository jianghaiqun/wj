package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.LogDao;
import cn.com.sinosoft.entity.Log;
import cn.com.sinosoft.service.LogService;

/**
 * Service实现类 - 日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1F694A92A7CFA4B05E87616ADCA7D169
 * ============================================================================
 */

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}
