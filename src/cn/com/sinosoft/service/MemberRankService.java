package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.MemberRank;

/**
 * Service接口 - 会员分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC9F59342352D3FB31197AAAE5268EBCF
 * ============================================================================
 */

public interface MemberRankService extends BaseService<MemberRank, String> {
	
	/**
	 * 获取默认会员等级,若无默认会员等级,则获取最先添加的会员等级.
	 * 
	 */
	public MemberRank getDefaultMemberRank();
	
	/**
	 * 根据积分获取会员等级，若不存在则返回null
	 * 
	 */
	public MemberRank getMemberRankByPoint(Integer point);
	
	/**
	 * 根据积分获取符合此积分条件的最高会员等级，若不存在则返回null
	 * 
	 */
	public MemberRank getUpMemberRankByPoint(Integer point);

}