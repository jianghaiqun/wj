package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDRelationAppnt;

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

public interface SDRelationAppntService extends BaseService<SDRelationAppnt, String> {
	public SDRelationAppnt getSDRelationAppntInfo(String comCode,String productID,String memberId,String appntName);
	public List<SDRelationAppnt> getSDRelationAppntInfoList(String comCode,String productID,String memberId,String appntName);

}