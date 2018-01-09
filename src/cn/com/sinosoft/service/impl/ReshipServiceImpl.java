package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ReshipDao;
import cn.com.sinosoft.entity.Reship;
import cn.com.sinosoft.service.ReshipService;
import cn.com.sinosoft.util.SerialNumberUtil;

/**
 * Service实现类 - 退货
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT14093C23520A786815DFA8C679BCDD5F
 * ============================================================================
 */

@Service
public class ReshipServiceImpl extends BaseServiceImpl<Reship, String> implements ReshipService {
	
	@Resource
	private ReshipDao reshipDao;

	@Resource
	public void setBaseDao(ReshipDao reshipDao) {
		super.setBaseDao(reshipDao);
	}
	
	public String getLastReshipSn() {
		return reshipDao.getLastReshipSn();
	}

	// 重写对象，保存时自动设置退货编号
	@Override
	public String save(Reship reship) {
		reship.setReshipSn(SerialNumberUtil.buildReshipSn());
		return super.save(reship);
	}

}