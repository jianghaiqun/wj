package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ShippingDao;
import cn.com.sinosoft.entity.Shipping;
import cn.com.sinosoft.service.ShippingService;
import cn.com.sinosoft.util.SerialNumberUtil;

/**
 * Service实现类 - 发货
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTEACE05E3DC84AC6892E51AD68CBADA74
 * ============================================================================
 */

@Service
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, String> implements ShippingService {
	
	@Resource
	private ShippingDao shippingDao;

	@Resource
	public void setBaseDao(ShippingDao shippingDao) {
		super.setBaseDao(shippingDao);
	}
	
	public String getLastShippingSn() {
		return shippingDao.getLastShippingSn();
	}

	// 重写对象，保存时自动设置发货编号
	@Override
	public String save(Shipping shipping) {
		shipping.setShippingSn(SerialNumberUtil.buildShippingSn());
		return super.save(shipping);
	}

}