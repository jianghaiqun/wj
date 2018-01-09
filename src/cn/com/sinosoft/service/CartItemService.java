package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.CartItem;

/**
 * Service接口 - 购物车项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB0E2C3542B59C806EB9934E73E251F7C
 * ============================================================================
 */

public interface CartItemService extends BaseService<CartItem, String> {

	public CartItem getByProduct(String productId);

	public CartItem getByProMemb(String productId, String memberId);
	
}