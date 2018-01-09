package cn.com.sinosoft.service;

import java.util.List;
import java.util.Set;

import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;

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

public interface SDInformationInsuredService extends BaseService<SDInformationInsured, String> {

	List<SDInformationInsured> getListByOrderSn(String orderSn);
	SDInformationInsured getByOrParentId(String informationId);
	List<SDInformationInsured> getListByOrParentId(String informationId);
	/**
	 * 组装页面被保人显示信息
	 */
	public List<List<InsuredShow>> createShowInformationInsured(Set<SDInformationInsured> sdinformationInsuredSet , String comCode);  
	public List<List<InsuredShow>> createShowInformationInsuredNew(Set<SDInformationInsured> sdinformationInsuredSet , String comCode,String ordersn);  
	
	public String getInsuredToCountry(SDInformationInsured tSDInformationInsured);
	public SDInformationInsured getInsuredByAppnt(SDInformation sdinf,SDInformationAppnt sdapp);
	public List<List<InsuredShow>> createBnfShow(Set<SDInformationInsured> sdinformationInsuredSet);
}