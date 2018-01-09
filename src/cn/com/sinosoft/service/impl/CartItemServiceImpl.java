package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.CartItemDao;
import cn.com.sinosoft.dao.InformationDao;
import cn.com.sinosoft.entity.CartItem;
import cn.com.sinosoft.service.CartItemService;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB29936B2E4DBE58A5D88D80B8580D7A5
 * ============================================================================
 */

@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, String> implements CartItemService {
	@Resource
	private CartItemDao cartItemDao;
	@Resource
	public void setBaseDao(CartItemDao cartItemDao) {
		super.setBaseDao(cartItemDao);
	}

	@Override
	public CartItem getByProduct(String productId) {
		return cartItemDao.getByProduct(productId);
	}

	@Override
	public CartItem getByProMemb(String productId, String memberId) {
		return cartItemDao.getByProMemb(productId,memberId);
	}

}