package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.MemberAttribute;


/**
 * Service接口 - 会员属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT249F14A09332DCE8ED60265E3F523A3A
 * ============================================================================
 */

public interface MemberAttributeService extends BaseService<MemberAttribute, String> {
	
	/**
	 * 获取已启用的会员注册项.
	 * 
	 * @return 已启用的会员注册项集合.
	 */
	public List<MemberAttribute> getEnabledMemberAttributeList();

}