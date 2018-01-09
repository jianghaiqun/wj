package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.InformationAppnt;

/**
 * Dao接口 - 投保人信息
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

public interface InformationAppntDao extends BaseDao<InformationAppnt, String> {
	public InformationAppnt getByOrParentId(String informationId) ;
}