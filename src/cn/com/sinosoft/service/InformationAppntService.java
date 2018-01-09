package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;

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

public interface InformationAppntService extends BaseService<InformationAppnt, String> {
	InformationAppnt getByOrParentId(String informationId);
}