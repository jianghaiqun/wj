package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Information;

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

public interface InformationService extends BaseService<Information, String> {


	Information getByProduct(String id,String orderId);
	Information getByOrP(String orderItemId);

}