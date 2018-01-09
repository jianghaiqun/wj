package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;

import cn.com.sinosoft.dao.BindInfoForLoginDao;
import cn.com.sinosoft.dao.SDOrderDao;
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.service.BindInfoForLoginService;

/**
 * Service实现类 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTDD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
 */

@Service
public class BindInfoForLoginServiceImpl extends BaseServiceImpl<BindInfoForLogin, String> implements BindInfoForLoginService {
	@Resource
	private BindInfoForLoginDao bindInfoForLoginDao;
	@Resource
	public void setBindInfoForLoginDao(BindInfoForLoginDao bindInfoForLoginDao) {
		super.setBaseDao(bindInfoForLoginDao);
	}
	public BindInfoForLogin getBindInfoForLoginByOpenID(String openid){
		return bindInfoForLoginDao.getBindInfoForLoginByOpenID(openid);
	}
	@Override
	@CacheFlush(modelId = "flushing")
	public String save(BindInfoForLogin entity) {
		return bindInfoForLoginDao.save(entity);
	}
}