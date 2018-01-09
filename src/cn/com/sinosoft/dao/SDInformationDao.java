package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.SDInformation;

/**
 * Dao接口 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT28061A4E3AB2BF6EE590DF6B934079B7
 * ============================================================================
 */

public interface SDInformationDao extends BaseDao<SDInformation, String> {


	public SDInformation getByOrderId(String orderId);
	public SDInformation getByProduct(String id,String orderId);
	public SDInformation getByOrP(String orderItemId) ;
	public SDInformation getByOrderSn(String orderSn);
}