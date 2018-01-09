package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberChannel;



/**
 * Service接口 - 会员频道
 * ============================================================================
 *  
 * ============================================================================
 */

public interface MemberChannelService extends BaseService<MemberChannel, String>{
	
	/**
	 * 会员等级进度条展示
	 * 
	 * @param member
	 * @return
	 */
	public Map<String, String> ddPercent(Member member);
	
	/**
	 * 会员特权内容展示
	 * 
	 * @param member
	 * @return
	 */
	public Map<String, String> privileges(Member member);
	
	/**
	 * 广告获取
	 * 
	 * @return
	 */
	public Map<String, String> campaign();
	
	/**
	 * 积分产品信息
	 * 
	 * @param pageIndex
	 * @return
	 */
	public Map<String, String> pointsProduct(int pageIndex);
	
	/**
	 * 会员推荐url和title
	 * 
	 * @param pageIndex
	 * @return
	 */
	public Map<String, String> recommendInfo(Member member);
	/**
	 * 会员推荐信息
	 * 
	 * @param pageIndex
	 * @return
	 */
	public Map<String, String> recommendInfoAll();
	
	/**
	 * 完成会员资料送积分
	 * 
	 * @return
	 */
	public Map<String, String> givePointsInfo();
	
	/**
	 * 获取会员信息和评价信息
	 * 
	 * @return
	 */
	public Map<String, Object> memberAndzccomment();
	
	/**
	 * 获取特价产品
	 * 
	 * @param member
	 * @return
	 */
	public List<Map<String, String>> activityProduct(Member member);
}