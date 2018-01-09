package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationAppnt;

/**
 * Dao接口 - 投保人信息
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

public interface SDInformationAppntDao extends
		BaseDao<SDInformationAppnt, String> {
	public SDInformationAppnt getByOrParentId(String informationId);

	/**
	 * 获取当前登陆用户下所有有过支付订单的投保人姓名(按支付历史顺序排列)
	 * 
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