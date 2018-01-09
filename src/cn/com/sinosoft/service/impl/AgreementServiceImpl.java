package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.AgreementDao;
import cn.com.sinosoft.entity.Agreement;
import cn.com.sinosoft.service.AgreementService;

/**
 * Service实现类 - 会员注册协议
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT992A2F1230488510985F95030CEAAFA3
 * ============================================================================
 */

@Service
public class AgreementServiceImpl extends BaseServiceImpl<Agreement, String> implements AgreementService {

	@Resource
	private AgreementDao agreementDao;

	@Resource
	public void setBaseDao(AgreementDao agreementDao) {
		super.setBaseDao(agreementDao);
	}

	@Cacheable(modelId = "caching")
	public Agreement getAgreement() {
		Agreement agreement = agreementDao.getAgreement();
		Hibernate.initialize(agreement);
		return agreement;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(Agreement entity) {
		agreementDao.delete(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		agreementDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		agreementDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(Agreement entity) {
		return agreementDao.save(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(Agreement entity) {
		agreementDao.update(entity);
	}

}
