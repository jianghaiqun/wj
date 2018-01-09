package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ProductInsTypeDao;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.service.ProductInsTypeService;

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
public class ProductInsTypeServiceImpl extends BaseServiceImpl<ProductInsType, String> implements
		ProductInsTypeService {

	@Resource
	public void setBaseDao(ProductInsTypeDao productInsTypeDao) {
		super.setBaseDao(productInsTypeDao);
	}

}
