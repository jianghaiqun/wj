package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.ProductCollection;



/**
 * Service接口 - 加入收藏
 * ============================================================================
 *  
 * ============================================================================
 */

public interface FavoritesService extends BaseService<ProductCollection, String>{
	
	/**
	 *加入收藏
	 * 
	 */

	public boolean contains(ProductCollection productCollection);
	
	
}