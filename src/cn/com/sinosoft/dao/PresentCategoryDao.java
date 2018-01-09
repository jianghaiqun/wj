package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.PresentCategory;

/**
 * Dao接口 - 商品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD05276A7E5664AF1D9750EF7CEB8D92E
 * ============================================================================
 */

public interface PresentCategoryDao extends BaseDao<PresentCategory, String> {
	
	/**
	 * 获取所有顶级商品分类集合;
	 * 
	 * @return 所有顶级商品分类集合
	 * 
	 */
	public List<PresentCategory> getRootPresentCategoryList();
	
	/**
	 * 根据PresentCategory对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<PresentCategory> getParentPresentCategoryList(PresentCategory presentCategory);
	
	/**
	 * 根据PresentCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<PresentCategory> getChildrenPresentCategoryList(PresentCategory presentCategory);

}
