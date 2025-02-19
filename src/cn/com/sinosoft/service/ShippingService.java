package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Shipping;

/**
 * Service接口 - 发货
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTCB3CC9F5224F4E532DC7C0C2EB2379DA
 * ============================================================================
 */

public interface ShippingService extends BaseService<Shipping, String> {

	/**
	 * 获取最后生成的发货编号
	 * 
	 * @return 发货编号
	 */
	public String getLastShippingSn();

}