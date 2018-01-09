package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.ProductCategory;

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

public interface ProductCategoryDao extends BaseDao<ProductCategory, String> {
	
	/**
	 * 获取所有顶级商品分类集合;
	 * 
	 * @return 所有顶级商品分类集合
	 * 
	 */
	public List<ProductCategory> getRootProductCategoryList();
	/**
	 * 根据渠道获取所有顶级商品分类集合;
	 * 
	 * @return 所有顶级商品分类集合
	 * 
	 */
	public List<ProductCategory> getRootProductCategoryList(String  channel);
	/**
	 * 根据渠道获取所有顶级商品分类集合;
	 * 
	 * @return 所有顶级商品分类集合
	 * 
	 */
	public List<ProductCategory> getRootProductCategoryList(String[]  channel);
	
	
	/**
	 * 根据ProductCategory对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory);
	
	/**
	 * 根据ProductCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory);

}
