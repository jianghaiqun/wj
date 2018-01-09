package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Information;

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

public interface InformationDao extends BaseDao<Information, String> {


	public Information getByProduct(String id,String orderId);
	public Information getByOrP(String orderItemId) ;
}