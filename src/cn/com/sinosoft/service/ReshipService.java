package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Reship;

/**
 * Service接口 - 退货
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC7E38C49F1DEA30184481ABB3241FF04
 * ============================================================================
 */

public interface ReshipService extends BaseService<Reship, String> {

	/**
	 * 获取最后生成的退货编号
	 * 
	 * @return 退货编号
	 */
	public String getLastReshipSn();

}