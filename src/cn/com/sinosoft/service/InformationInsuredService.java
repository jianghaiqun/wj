package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.InsuredShow;

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

public interface InformationInsuredService extends BaseService<InformationInsured, String> {

	InformationInsured getByOrParentId(String informationId);
	/**
	 * 组装页面被保人显示信息
	 */
	public List<List<InsuredShow>> createShowInformationInsured(
			Set<InformationInsured> informationInsuredSet , String comCode);    

}