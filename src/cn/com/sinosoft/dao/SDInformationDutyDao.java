package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationDuty;


/**
 * Dao接口 - 险种信息
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

public interface SDInformationDutyDao extends BaseDao<SDInformationDuty, String> {

	public List<SDInformationDuty> getByOrderSn(String orderSn);
}