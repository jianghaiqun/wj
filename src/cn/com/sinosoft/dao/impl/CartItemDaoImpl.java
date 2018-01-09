package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.CartItemDao;
import cn.com.sinosoft.entity.CartItem;
import cn.com.sinosoft.entity.Information;

/**
 * Dao实现类 - 购物车项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1BCC0CDFEEE26A589F809A8B426E5595
 * ============================================================================
 */

@Repository
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, String> implements CartItemDao {

	// 重写方法，若保存对象的member、product属性值相同，则只更新已有对象的quantity属性值
	@Override
	public String save(CartItem cartItem) {
		String hql = "from CartItem cartItem where cartItem.member = ? and cartItem.product = ?";
		CartItem persistent = (CartItem) getSession().createQuery(hql).setParameter(0, cartItem.getMember()).setParameter(1, cartItem.getProduct()).uniqueResult();
		if (persistent == null) {
			return super.save(cartItem);
		} else {
			persistent.setQuantity(persistent.getQuantity() + cartItem.getQuantity());
			super.update(persistent);
			return persistent.getId();
		}
	}

	@Override
	public CartItem getByProduct(String productId) {
		String hql = "from CartItem as cartItem where cartItem.product.id = ? ";
		return (CartItem) getSession().createQuery(hql).setParameter(0, productId).uniqueResult();
	}

	@Override
	public CartItem getByProMemb(String productId, String memberId) {
		String hql = "from CartItem as cartItem where cartItem.product.id = ? and cartItem.member.id=?";
		return (CartItem) getSession().createQuery(hql).setParameter(0, productId).setParameter(1, memberId).uniqueResult();
	}

}