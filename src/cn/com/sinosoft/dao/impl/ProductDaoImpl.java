package cn.com.sinosoft.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.ProductDao;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.DeliveryItem;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.util.SystemConfigUtil;

/**
 * Dao实现类 - 商品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT027556F67EB15567DE4DCC7E9D2E5516
 * ============================================================================
 */

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao {
	
	//首页产品展现
	@SuppressWarnings("unchecked")
	public List<Product> getIndexProductList(ProductCategory productCategory,Channel channel){
		String hql = "from Product as product left join  fetch product.channelSet  as  channel  where product.isMarketable = ? and product.productCategory.path like ? and channel.id=? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productCategory.getPath() + "%").setParameter(2, channel.getId()).list();	
	}
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(ProductCategory productCategory) {
		String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productCategory.getPath() + "%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(int firstResult, int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productCategory.getPath() + "%").setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		if (beginDate != null && endDate == null) {
			String hql = "from Product as product where product.isMarketable = ? and product.createDate > ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate == null) {
			String hql = "from Product as product where product.isMarketable = ? and product.createDate < ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate != null) {
			String hql = "from Product as product where product.isMarketable = ? and product.createDate > ? and product.createDate < ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else {
			String hql = "from Product as product where product.isMarketable = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	public Pager getProductPager(ProductCategory productCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
		detachedCriteria.createAlias("productCategory", "productCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("productCategory", productCategory), Restrictions.like("productCategory.path", productCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isMarketable", true));
		return super.findByPager(pager, detachedCriteria);
	}
//=============================首页产品中心==================================================
	public Pager getProductHomePager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
//		detachedCriteria.createAlias("productCategory", "productCategory");
//		detachedCriteria.add(Restrictions.or(Restrictions.eq("productCategory", productCategory), Restrictions.like("productCategory.path", productCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isMarketable", true));
		return super.findByPager(pager, detachedCriteria);
	}
//=============================首页产品中心==================================================
	@SuppressWarnings("unchecked")
	public List<Product> getBestProductList(int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isBest = ? order by product.name, product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getBestProductList(ProductCategory productCategory, int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isBest = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getHotProductList(int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isHot = ? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getHotProductList(ProductCategory productCategory, int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isHot = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getNewProductList(int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isNew = ? order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getNewProductList(ProductCategory productCategory, int maxResults) {
		String hql = "from Product as product where product.isMarketable = ? and product.isNew = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();
	}
	
	public Pager getFavoriteProductPager(Member member, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
		detachedCriteria.createAlias("favoriteMemberSet", "favoriteMemberSet");
		detachedCriteria.add(Restrictions.eq("favoriteMemberSet.id", member.getId()));
		detachedCriteria.addOrder(Order.desc("name"));
		return findByPager(pager, detachedCriteria);
	}
	
	public Long getStoreAlertCount() {
		String hql = "select count(*) from Product as product where product.isMarketable = ? and product.store - product.freezeStore <= ?";
		return (Long) getSession().createQuery(hql).setParameter(0, true).setParameter(1, SystemConfigUtil.getSystemConfig().getStoreAlertCount()).uniqueResult();
	}
	
	public Long getMarketableProductCount() {
		String hql = "select count(*) from Product as product where product.isMarketable = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
	}
	
	public Long getUnMarketableProductCount() {
		String hql = "select count(*) from Product as product where product.isMarketable = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, false).uniqueResult();
	}
//获取product属性测试
	@SuppressWarnings("unchecked")
	public List<Product> getAllProductAttr(String id) {
		String hql = "select a.* from Product as p,Productinstype as t,Productinsattribute as a where p.productinstype_id=t.id and a.productinstype_id=t.idand p.id=?";
		return getSession().createQuery(hql).setParameter(0,id).list();
	}
//获取product属性测试
	// 关联处理
	@Override
	public void delete(Product product) {
		Set<Member> favoriteMemberSet = product.getFavoriteMemberSet();
		if (favoriteMemberSet != null) {
			for (Member favoriteMember : favoriteMemberSet) {
				Set<Product> favoriteProductSet = favoriteMember.getFavoriteProductSet();
				favoriteProductSet.remove(product);
			}
		}
//		Set<OrderItem> orderItemSet = product.getOrderItemSet();
//		if (orderItemSet != null) {
//			for (OrderItem orderItem : orderItemSet) {
//				orderItem.setProduct(null);
//			}
//		}
		Set<DeliveryItem> deliveryItemSet = product.getDeliveryItemSet();
		if (deliveryItemSet != null) {
			for (DeliveryItem deliveryItem : deliveryItemSet) {
				deliveryItem.setProduct(null);
			}
		}
		super.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Product product = load(id);
		this.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Product product = load(id);
			this.delete(product);
		}
	}



}