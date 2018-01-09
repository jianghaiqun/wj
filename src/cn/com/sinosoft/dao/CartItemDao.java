package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.CartItem;

/**
 * Dao接口 - 购物车项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7BB3B72AA380C1AB12162EA2337E5F6B
 * ============================================================================
 */

public interface CartItemDao extends BaseDao<CartItem, String> {


	public CartItem getByProduct(String productId);

	public CartItem getByProMemb(String productId, String memberId);

}
