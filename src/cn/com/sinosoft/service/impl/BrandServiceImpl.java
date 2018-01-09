package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.BrandDao;
import cn.com.sinosoft.entity.Brand;
import cn.com.sinosoft.service.BrandService;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

	@Resource
	public void setBaseDao(BrandDao brandDao) {
		super.setBaseDao(brandDao);
	}

}