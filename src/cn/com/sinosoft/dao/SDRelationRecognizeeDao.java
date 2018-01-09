package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDRelationRecognizee;

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

public interface SDRelationRecognizeeDao extends BaseDao<SDRelationRecognizee, String> {
	public SDRelationRecognizee getSDRelationRecognizeeInfo(String comCode,String productID,String memberId,String appntName);
	public List<SDRelationRecognizee> getSDRelationRecognizeeInfoList(String comCode,String productID,String memberId,String recognizeeName);
}