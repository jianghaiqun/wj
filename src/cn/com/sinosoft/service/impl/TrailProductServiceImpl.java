package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.TrailProductDao;
import cn.com.sinosoft.entity.TrailProduct;
import cn.com.sinosoft.service.TrailProductService;

@Service
public class TrailProductServiceImpl extends BaseServiceImpl<TrailProduct,String> implements TrailProductService{
	@Resource
	private TrailProductDao trailProductDao;

	@Resource
	public void setTrailProductDao(TrailProductDao trailProductDao) {
		super.setBaseDao(trailProductDao);
	}
	

}
