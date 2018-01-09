package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.ProductInsAttributeDao;
import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.service.ProductInsAttributeService;

/**
 * Service实现类 - 商品属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1DBC8F3D75E6812599E1C363C0BB757D
 * ============================================================================
 */

@Service
public class ProductInsAttributeServiceImpl extends BaseServiceImpl<ProductInsAttribute, String> implements ProductInsAttributeService {

	@Resource
	private ProductInsAttributeDao productInsAttributeDao;
	
	@Resource
	public void setBaseDao(ProductInsAttributeDao productInsAttributeDao) {
		super.setBaseDao(productInsAttributeDao);
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductInsAttribute> getEnabledProductInsAttributeList() {
		List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeDao.getEnabledProductInsAttributeList();
		if (enabledProductInsAttributeList != null) {
			for (ProductInsAttribute enabledProductInsAttribute : enabledProductInsAttributeList) {
				Hibernate.initialize(enabledProductInsAttribute);
			}
		}
		return enabledProductInsAttributeList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductInsAttribute> getEnabledProductInsAttributeList(ProductInsType productInsType) {
		List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeDao.getEnabledProductInsAttributeList(productInsType);
		if (enabledProductInsAttributeList != null) {
			for (ProductInsAttribute enabledProductInsAttribute : enabledProductInsAttributeList) {
				Hibernate.initialize(enabledProductInsAttribute);
			}
		}
		return enabledProductInsAttributeList;
	}
	
	public ProductInsAttribute getProductInsAttribute(ProductInsType productInsType, String name) {
		return productInsAttributeDao.getProductInsAttribute(productInsType, name);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(ProductInsAttribute productInsAttribute) {
		productInsAttributeDao.delete(productInsAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		productInsAttributeDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		productInsAttributeDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(ProductInsAttribute productInsAttribute) {
		return productInsAttributeDao.save(productInsAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(ProductInsAttribute productInsAttribute) {
		productInsAttributeDao.update(productInsAttribute);
	}

}