package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.ProductAttributeDao;
import cn.com.sinosoft.entity.ProductAttribute;
import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.entity.ProductType;
import cn.com.sinosoft.service.ProductAttributeService;

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
public class ProductAttributeServiceImpl extends BaseServiceImpl<ProductAttribute, String> implements ProductAttributeService {

	@Resource
	private ProductAttributeDao productAttributeDao;
	
	@Resource
	public void setBaseDao(ProductAttributeDao productAttributeDao) {
		super.setBaseDao(productAttributeDao);
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductAttribute> getEnabledProductAttributeList() {
		List<ProductAttribute> enabledProductAttributeList = productAttributeDao.getEnabledProductAttributeList();
		if (enabledProductAttributeList != null) {
			for (ProductAttribute enabledProductAttribute : enabledProductAttributeList) {
				Hibernate.initialize(enabledProductAttribute);
			}
		}
		return enabledProductAttributeList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductAttribute> getEnabledProductAttributeList(ProductType productType) {
		List<ProductAttribute> enabledProductAttributeList = productAttributeDao.getEnabledProductAttributeList(productType);
		if (enabledProductAttributeList != null) {
			for (ProductAttribute enabledProductAttribute : enabledProductAttributeList) {
				Hibernate.initialize(enabledProductAttribute);
			}
		}
		return enabledProductAttributeList;
	}
	
	public ProductAttribute getProductAttribute(ProductType productType, String name) {
		return productAttributeDao.getProductAttribute(productType, name);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(ProductAttribute productAttribute) {
		productAttributeDao.delete(productAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		productAttributeDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		productAttributeDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(ProductAttribute productAttribute) {
		return productAttributeDao.save(productAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(ProductAttribute productAttribute) {
		productAttributeDao.update(productAttribute);
	}


}