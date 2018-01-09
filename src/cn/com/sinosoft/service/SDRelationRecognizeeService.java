package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDRelationRecognizee;

/**
 * Service接口 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB99DB2E7E2EF324F36B462FD6C183267
 * ============================================================================
 */

public interface SDRelationRecognizeeService extends BaseService<SDRelationRecognizee, String> {
	public SDRelationRecognizee getSDRelationRecognizeeInfo(String comCode,String productID,String memberId,String recognizeeName);
	public List<SDRelationRecognizee> getSDRelationRecognizeeInfoList(String comCode,String productID,String memberId,String recognizeeName);

}