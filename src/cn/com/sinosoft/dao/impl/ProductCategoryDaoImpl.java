package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.ProductCategoryDao;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.ProductCategory;

/**
 * Dao实现类 - 商品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT218DE2372F1FD61042DB651AAC5D5594
 * ============================================================================
 */

@Repository
public class ProductCategoryDaoImpl extends BaseDaoImpl<ProductCategory, String> implements ProductCategoryDao {

	@SuppressWarnings("unchecked")
	public List<ProductCategory> getRootProductCategoryList() {
		String hql = "from ProductCategory productCategory where productCategory.parent is null  order by productCategory.orderList asc";
		return getSession().createQuery(hql).list();
	}
	//根据渠道分类选择产品
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getRootProductCategoryList(String channel) {
		String hql = "from ProductCategory as  productCategory  left join  fetch productCategory.channelSet  as  channel where  channel.id =? order by productCategory.orderList asc ";
		return getSession().createQuery(hql).setParameter(0, channel).list();
	}
	//根据渠道分类选择产品
		@SuppressWarnings("unchecked")
		public List<ProductCategory> getRootProductCategoryList(String[] channel) {
			String hql = "from ProductCategory as  productCategory  left join  fetch productCategory.channelSet  as  channel where  channel.id in(:channel) order by productCategory.orderList asc ";
			return getSession().createQuery(hql).setParameterList("channel", channel).list();
		}
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory) {
		String hql = "from ProductCategory productCategory where productCategory != ? and productCategory.id in(:ids) order by productCategory.orderList asc";
		String[] ids = productCategory.getPath().split(ProductCategory.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, productCategory).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory) {
		String hql = "from ProductCategory productCategory where productCategory != ? and productCategory.path like ? order by productCategory.orderList asc";
		return getSession().createQuery(hql).setParameter(0, productCategory).setParameter(1, productCategory.getPath() + "%").list();
	}
	
	// 重写方法，保存时计算path值
	@Override
	public String save(ProductCategory productCategory) {
		String id = super.save(productCategory);
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			productCategory.setPath(parentPath + ProductCategory.PATH_SEPARATOR + id);
		} else {
			productCategory.setPath(id);
		}
		super.update(productCategory);
		return id;
	}
	
	// 重写方法，更新时计算path值
	@Override
	public void update(ProductCategory productCategory) {
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			productCategory.setPath(parentPath + ProductCategory.PATH_SEPARATOR + productCategory.getId());
		} else {
			productCategory.setPath(productCategory.getId());
		}
		super.update(productCategory);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> getAll() {
		String hql = "from ProductCategory productCategory order by productCategory.orderList asc productCategory.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getList(String propertyName, Object value) {
		String hql = "from ProductCategory productCategory where productCategory." + propertyName + "=? order by productCategory.orderList asc productCategory.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductCategory.class);
		return this.findByPager(pager, detachedCriteria);
	}

}
