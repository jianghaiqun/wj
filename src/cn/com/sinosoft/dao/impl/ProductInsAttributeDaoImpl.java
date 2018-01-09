package cn.com.sinosoft.dao.impl;

import java.util.List;
import java.util.Map;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.ProductInsAttributeDao;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;

/**
 * Dao实现类 - 商品属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9F75E1059319B1C5FEFD644E19AE7400
 * ============================================================================
 */

@Repository
public class ProductInsAttributeDaoImpl extends BaseDaoImpl<ProductInsAttribute, String> implements ProductInsAttributeDao {
	
	@SuppressWarnings("unchecked")
	public List<ProductInsAttribute> getEnabledProductInsAttributeList() {
		String hql = "from ProductInsAttribute productInsAttribute where productInsAttribute.isEnabled = ? order by productInsAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductInsAttribute> getEnabledProductInsAttributeList(ProductInsType productInsType) {
		String hql = "from ProductInsAttribute productInsAttribute where productInsAttribute.isEnabled = ? and productInsAttribute.productInsType = ? order by productInsAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productInsType).list();
	}
	
	public ProductInsAttribute getProductInsAttribute(ProductInsType productInsType, String name) {
		String hql = "from ProductInsAttribute productInsAttribute where productInsAttribute.productInsType = ? and productInsAttribute.name = ?";
		return (ProductInsAttribute) getSession().createQuery(hql).setParameter(0, productInsType).setParameter(1, name).uniqueResult();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInsAttribute> getAll() {
		String hql = "from ProductInsAttribute productInsAttribute order by productInsAttribute.orderList asc productInsAttribute.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductInsAttribute> getList(String propertyName, Object value) {
		String hql = "from ProductInsAttribute productInsAttribute where productInsAttribute." + propertyName + "=? order by productInsAttribute.orderList asc productInsAttribute.createDate desc";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderBy("orderList");
			pager.setOrderType(OrderType.asc);
		}
		return super.findByPager(pager, detachedCriteria);
	}

	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductInsAttribute.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
	// 重写方法，删除的同时清除关联
	@SuppressWarnings("unchecked")
	@Override
	public void delete(ProductInsAttribute productInsAttribute) {
		String hql = "from Product as product join product.productInsAttributeMapStore productInsAttributeMapStore where index(productInsAttributeMapStore) = :key";
		List<Product> productList = getSession().createQuery(hql).setParameter("key", productInsAttribute).list();
		for (Product product : productList) {
			Map<ProductInsAttribute, String> productattrib = product.getProductInsattrib();
			productattrib.remove(productInsAttribute);
		}
		super.delete(productInsAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String id) {
		ProductInsAttribute productInsAttribute = load(id);
		this.delete(productInsAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}