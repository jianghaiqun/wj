package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.InformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationInsuredElements;

/**
 * Service接口 - 被保人信息
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

public interface SDInformationInsuredElementsService extends BaseService<SDInformationInsuredElements, String> {

	/**
	 * 
	 * 根据被保人id及产品投保要素id查询产品投保要素是否存在
	 */
	public SDInformationInsuredElements findIsExist(String appFactorCode, String id);

}