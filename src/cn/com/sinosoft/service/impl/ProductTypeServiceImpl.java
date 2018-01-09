package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ProductTypeDao;
import cn.com.sinosoft.entity.ProductType;
import cn.com.sinosoft.service.ProductTypeService;

/**
 * Service实现类 - 商品类型
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT11AE7AEEC7428ABAA892EED06C4E877F
 * ============================================================================
 */

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements
		ProductTypeService {

	@Resource
	public void setBaseDao(ProductTypeDao productTypeDao) {
		super.setBaseDao(productTypeDao);
	}

}
