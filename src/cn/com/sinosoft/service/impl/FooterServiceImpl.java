package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.FooterDao;
import cn.com.sinosoft.entity.Footer;
import cn.com.sinosoft.service.FooterService;

/**
 * Service实现类 - 网页底部信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB793EDD8F3090C166D4679D20CCB2A9C
 * ============================================================================
 */

@Service
public class FooterServiceImpl extends BaseServiceImpl<Footer, String> implements FooterService {

	@Resource
	private FooterDao footerDao;

	@Resource
	public void setBaseDao(FooterDao footerDao) {
		super.setBaseDao(footerDao);
	}

	@Cacheable(modelId = "caching")
	public Footer getFooter() {
		Footer footer = footerDao.getFooter();
		Hibernate.initialize(footer);
		return footer;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(Footer entity) {
		footerDao.delete(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		footerDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		footerDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(Footer entity) {
		return footerDao.save(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(Footer entity) {
		footerDao.update(entity);
	}

}
