package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationDuty;

/**
 * Service接口 - 险种
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

public interface SDInformationDutyService extends BaseService<SDInformationDuty, String> {

	public List<SDInformationDuty> getByOrderSn(String orderSn);
}