package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.CustomerDemand;


/**
 * Dao接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7DE6DBC156621DE89E663E0E451C2E85
 * ============================================================================
 */

public interface CustomerDemandDao extends BaseDao<CustomerDemand, String> {
	public List<CustomerDemand> getCustomerDemandList(int firstResult, int maxResults);

	public List<CustomerDemand> getCustomerDemandInsNameList(int maxResults);	
	public List<CustomerDemand> getCustomerDemandInsTypeList(int maxResults);	
	public List<CustomerDemand> getCustomerDemandGpList(int maxResults);	
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

}