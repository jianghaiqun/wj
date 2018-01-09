package cn.com.sinosoft.service;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;


/**
 * Service接口 - 礼品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT62F330D4576F1CE472FE4A72A3C1070A
 * ============================================================================
 */

public interface PresentService extends BaseService<Present, String> {

	/**
	 * 根据PresentCategory对象，获取此分类下的所有礼品（只包含isMarketable=true的对象，包含子分类礼品）
	 * 
	 * @param presentCategory
	 *            礼品分类
	 * 
	 * @return 此分类下的所有礼品集合
	 */
	public List<Present> getPresentList(PresentCategory presentCategory);
	
	/**
	 * 根据起始结果数、最大结果数，获取所有礼品（只包含isMarketable=true的对象）
	 * 
	 * @param firstResult
	 *            起始结果数
	 * 
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有礼品集合
	 */
	public List<Present> getPresentList(int firstResult, int maxResults);
	
	/**
	 * 根据PresentCategory对象、起始结果数、最大结果数，获取此分类下的所有文章（只包含isMarketable=true的对象，包含子分类礼品）
	 * 
	 * @param presentCategory
	 *            礼品分类
	 * 
	 * @param firstResult
	 *            起始结果数
	 * 
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有礼品集合
	 */
	public List<Present> getPresentList(PresentCategory presentCategory, int firstResult, int maxResults);
	
	/**
	 * 根据起始日期、结束日期、起始结果数、最大结果数，获取礼品集合（只包含isMarketable=true的对象）
	 * 
	 * @param beginDate
	 *            起始日期
	 * 
	 * @param endDate
	 *            结束日期
	 * 
	 * @param firstResult
	 *            起始结果数
	 * 
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有礼品集合
	 */
	public List<Present> getPresentList(Date beginDate, Date endDate, int firstResult, int maxResults);
	
	
	/**
	 * 根据PresentCategory对象、和渠道，获取此分类下的所有礼品（只包含isMarketable=true的对象，包含子分类礼品）
	 * 
	 * @param presentCategory
	 *            礼品分类
	 *  @param channel
	 *            渠道
	 * 
	 * @return 此分类下的所有礼品集合
	 */
	 public List<Present> getIndexPresentList(PresentCategory presentCategory);
	
	
	/**
	 * 根据PresentCategory和Pager对象，获取此分类下的礼品分页对象（只包含isMarketable=true的对象，包含子分类礼品）
	 * 
	 * @param presentCategory
	 *            礼品分类
	 * 
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getPresentPager(PresentCategory presentCategory, Pager pager);
//=======================首页产品中心==================================
	public Pager getPresentHomePager(Pager pager);
//=======================首页产品中心==================================
	
	/**
	 * 根据最大返回数获取所有精品礼品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有精品礼品集合
	 */
	public List<Present> getBestPresentList(int maxResults);

	/**
	 * 根据PresentCategory对象和最大返回数获取此分类下的所有精品礼品(只包含isMarketable=true的对象，包含子分类礼品)
	 * 
	 * @param presentCategory
	 *            礼品分类
	 *            
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有精品礼品集合
	 */
	public List<Present> getBestPresentList(PresentCategory presentCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取所有热卖礼品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有热卖礼品集合
	 */
	public List<Present> getHotPresentList(int maxResults);

	/**
	 * 根据PresentCategory对象和最大返回数获取此分类下的所有热卖礼品(只包含isMarketable=true的对象，包含子分类礼品)
	 * 
	 * @param presentCategory
	 *            礼品分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有热卖礼品集合
	 */
	public List<Present> getHotPresentList(PresentCategory presentCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取最新礼品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 最新礼品集合
	 */
	public List<Present> getNewPresentList(int maxResults);
	/**
	 * 根据PresentCategory对象和最大返回数获取此分类下的最新礼品(只包含isMarketable=true的对象，包含子分类礼品)
	 * 
	 * @param presentCategory
	 *            礼品分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的最新礼品集合
	 */
	public List<Present> getNewPresentList(PresentCategory presentCategory, int maxResults);
	
	/**
	 * 根据分页对象搜索文章
	 * 
	 * @return 分页对象
	 */
//	public Pager search(Pager pager);
	
	/**
	 * 根据Member、Pager获取收藏礼品分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 收藏礼品分页对象
	 */
	public Pager getFavoritePresentPager(Member member, Pager pager);
	
	/**
	 * 获取礼品库存报警数
	 *            
	 * @return 礼品库存报警数
	 */
	public Long getStoreAlertCount();
	
	/**
	 * 获取已上架礼品数
	 *            
	 * @return 已上架礼品数
	 */
	public Long getMarketablePresentCount();
	
	/**
	 * 获取已下架礼品数
	 *            
	 * @return 已下架礼品数
	 */
	public Long getUnMarketablePresentCount();
//==================获取present属性测试==========================================	
	public List<Present> getAllPresentAttr(String id);
//==================获取present属性测试==========================================	
	/**
	 * 获取礼品已兑换列表
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 礼品已兑换分页对象
	 */
	public List getAlreadyExchangeList();
	
	/**
	 * 获取礼品未兑换列表
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 礼品未兑换分页对象
	 */
	public List getNoExchangeList();
	/**
	 * 查询所有推荐商品
	 * 
	 * 
	 * @return 推荐礼品计划
	 */
	public List<Present> getNewPresentList();
	/**
	 * 获取所有推荐产品详细信息
	 * 
	 * 
	 * @return 推荐产品详细信息
	 */
	public Present getNewPresentInfo(String id) ;
	/**
	 * 获取固定运营商产品详细信息
	 * 
	 * 
	 * @return 固定运营商详细信息
	 */
	public List<Present> getMobilePresentInfo(String htmlFilePath,String actCode) ;
}