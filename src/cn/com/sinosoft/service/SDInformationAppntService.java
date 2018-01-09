package cn.com.sinosoft.service;

import java.util.List;
import java.util.Set;

import cn.com.sinosoft.entity.InsuredShow;
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

public interface SDInformationAppntService extends BaseService<SDInformationAppnt, String> {
	SDInformationAppnt getByOrParentId(String sdinformationId);
	/**
	 * 组装页面投保人显示信息
	 */
	public List<List<InsuredShow>> createShowInformationAppnt(
			Set<SDInformationAppnt> sdinformationAppntSet , String comCode);    
	
	/**
	 * 获取当前登陆用户下所有有过支付订单的投保人姓名(按支付历史顺序排列)
	 * @param MemberId
	 * @return
	 */
	public List<String> getPaidAppntNameByMemberId(String memberId);
	
	/**
	 * 获取当前用户下指定姓名的投保人信息
	 * 
	 * @param applicantName
	 * @param memberId
	 * @return
	 */
	public SDInformationAppnt getByAppntNameAndMemberId(String applicantName,
			String memberId);
}