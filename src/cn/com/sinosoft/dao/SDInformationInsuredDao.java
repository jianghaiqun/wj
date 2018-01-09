package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationInsured;

/**
 * Dao接口 - 受益人信息
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

public interface SDInformationInsuredDao extends BaseDao<SDInformationInsured, String> {
	public List<SDInformationInsured> getListByOrderSn(String orderSn);
	public SDInformationInsured getByOrParentId(String informationId) ;
	public List<SDInformationInsured> getListByOrParentId(String informationId) ;

}