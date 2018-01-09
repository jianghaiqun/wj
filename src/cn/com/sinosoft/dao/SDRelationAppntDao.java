package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDRelationAppnt;

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

public interface SDRelationAppntDao extends BaseDao<SDRelationAppnt, String> {
	public SDRelationAppnt getSDRelationAppntInfo(String comCode,String productID,String memberId,String appntName);
	public List<SDRelationAppnt> getSDRelationAppntInfoList(String comCode,String productID,String memberId,String appntName);
}