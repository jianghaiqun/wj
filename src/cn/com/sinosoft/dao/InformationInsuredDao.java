package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;

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

public interface InformationInsuredDao extends BaseDao<InformationInsured, String> {
	public InformationInsured getByOrParentId(String informationId) ;

}