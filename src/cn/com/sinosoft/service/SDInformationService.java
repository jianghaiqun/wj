package cn.com.sinosoft.service;

import java.util.List;
import java.util.Set;

import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;

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

public interface SDInformationService extends BaseService<SDInformation, String> {


	SDInformation getByOrderId(String orderId);
	SDInformation getByProduct(String id,String orderId);
	SDInformation getByOrP(String orderItemId);
	public List<List<InsuredShow>> createShowInformationPeriod(SDInformation tSDInformation);    
	SDInformation getByOrderSn(String orderSn);
}