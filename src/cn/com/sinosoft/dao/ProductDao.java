package cn.com.sinosoft.dao;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductCategory;


/**
 * Dao接口 - 商品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT345CC4FC5532785F1241F140FCE5A740
 * ============================================================================
 */

public interface ProductDao extends BaseDao<Product, String> {
	
	/**
	 * 根据ProductCategory对象，获取此分类下的所有商品（只包含isMarketable=true的对象，包含子分类商品）
	 * 
	 * @param productCategory
	 *            商品分类
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Product> getProductList(ProductCategory productCategory);
	
	/**
	 * 根据起始结果数、最大结果数，获取所有商品（只包含isMarketable=true的对象）
	 *            
	 * @param firstResult
	 *            起始结果数
	 *            
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Product> getProductList(int firstResult, int maxResults);
	
	/**
	 * 根据ProductCategory对象、起始结果数、最大结果数，获取此分类下的所有商品（只包含isMarketable=true的对象，包含子分类商品）
	 * 
	 * @param productCategory
	 *            商品分类
	 *            
	 * @param firstResult
	 *            起始结果数
	 *            
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults);
	
	/**
	 * 根据起始日期、结束日期、起始结果数、最大结果数，获取商品集合（只包含isMarketable=true的对象）
	 * 
	 * @param beginDate
	 *            起始日期，为null则不限制起始日期
	 *            
	 * @param endDate
	 *            结束日期，为null则不限制结束日期
	 *            
	 * @param firstResult
	 *            起始结果数
	 *            
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults);
	
	/**
	 * 根据ProductCategory,Channel对象，获取此分类下的所有商品（只包含isMarketable=true的对象，包含子分类商品）
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param channel
	 *            渠道
	 * 
	 * @return 此分类下的所有商品集合
	 */
	
	
	public List<Product> getIndexProductList(ProductCategory productCategory,Channel channel);
	
	
	
	
	/**
	 * 根据ProductCategory和Pager对象，获取此分类下的商品分页对象（只包含isMarketable=true的对象，包含子分类商品）
	 * 
	 * @param productCategory
	 *            商品分类
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getProductPager(ProductCategory productCategory, Pager pager);
//=======================首页产品中心==================================
	public Pager getProductHomePager(Pager pager);
//=======================首页产品中心==================================
	
	/**
	 * 根据最大返回数获取所有精品商品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有精品商品集合
	 */
	public List<Product> getBestProductList(int maxResults);

	/**
	 * 根据ProductCategory对象和最大返回数获取此分类下的所有精品商品(只包含isMarketable=true的对象，包含子分类商品)
	 * 
	 * @param productCategory
	 *            商品分类
	 *            
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有精品商品集合
	 */
	public List<Product> getBestProductList(ProductCategory productCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取所有热卖商品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有热卖商品集合
	 */
	public List<Product> getHotProductList(int maxResults);

	/**
	 * 根据ProductCategory对象和最大返回数获取此分类下的所有热卖商品(只包含isMarketable=true的对象，包含子分类商品)
	 * 
	 * @param productCategory
	 *            商品分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有热卖商品集合
	 */
	public List<Product> getHotProductList(ProductCategory productCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取最新商品(只包含isMarketable=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 最新商品集合
	 */
	public List<Product> getNewProductList(int maxResults);

	/**
	 * 根据ProductCategory对象和最大返回数获取此分类下的最新商品(只包含isMarketable=true的对象，包含子分类商品)
	 * 
	 * @param productCategory
	 *            商品分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的最新商品集合
	 */
	public List<Product> getNewProductList(ProductCategory productCategory, int maxResults);
	
	/**
	 * 根据Member、Pager获取收藏商品分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 收藏商品分页对象
	 */
	public Pager getFavoriteProductPager(Member member, Pager pager);
	
	/**
	 * 获取商品库存报警数
	 *            
	 * @return 商品库存报警数
	 */
	public Long getStoreAlertCount();
	
	/**
	 * 获取已上架商品数
	 *            
	 * @return 已上架商品数
	 */
	public Long getMarketableProductCount();
	
	/**
	 * 获取已下架商品数
	 *            
	 * @return 已下架商品数
	 */
	public Long getUnMarketableProductCount();

	public List<Product> getAllProductAttr(String id);

}