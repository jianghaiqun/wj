package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;


/**
 * Service接口 - 礼品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9A058EDC3D9C0E5B105452AB83A4DF2C
 * ============================================================================
 */

public interface PresentCategoryService extends BaseService<PresentCategory, String> {
	
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
	public List<PresentCategory> getParentPresentCategoryList(PresentCategory productCategory);
	
	/**
	 * 根据Present对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<PresentCategory> getParentPresentCategoryList(Present product);
	
	/**
	 * 根据PresentCategory对象获取路径集合;
	 * 
	 * @return PresentCategory集合
	 * 
	 */
	public List<PresentCategory> getPresentCategoryPathList(PresentCategory productCategory);
	
	/**
	 * 根据Present对象获取路径集合;
	 * 
	 * @return PresentCategory集合
	 * 
	 */
	public List<PresentCategory> getPresentCategoryPathList(Present product);
	
	/**
	 * 根据PresentCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<PresentCategory> getChildrenPresentCategoryList(PresentCategory productCategory);
	
	/**
	 * 根据Present对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<PresentCategory> getChildrenPresentCategoryList(Present product);
	
	/**
	 * 获取商品分类树集合;
	 * 
	 * @return 商品分类树集合
	 * 
	 */
	public List<PresentCategory> getPresentCategoryTreeList();

}