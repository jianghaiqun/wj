package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.CustomerDemand;


/**
 * Service接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT868582BB07E8457F3171FCCADB94B449
 * ============================================================================
 */

public interface CustomerDemandService extends BaseService<CustomerDemand, String> {
	//===========9.15========================================================
	public List<CustomerDemand> getCustomerDemandList(int firstResult, int maxResults);
	
	/**
	 * 根据ArticleCategory对象、起始结果数、最大结果数，获取此分类下的所有文章（只包含isPublication=true的对象，包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类
	 *            
	 * @param firstResult
	 *            起始结果数
	 *            
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有文章集合
	 */
	//===========9.15========================================================

}