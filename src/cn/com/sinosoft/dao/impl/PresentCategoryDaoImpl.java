package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.PresentCategoryDao;
import cn.com.sinosoft.entity.PresentCategory;

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
public class PresentCategoryDaoImpl extends BaseDaoImpl<PresentCategory, String> implements PresentCategoryDao {

	@SuppressWarnings("unchecked")
	public List<PresentCategory> getRootPresentCategoryList() {
		String hql = "from PresentCategory presentCategory where presentCategory.parent is null  order by presentCategory.orderList asc";
		return getSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	public List<PresentCategory> getParentPresentCategoryList(PresentCategory presentCategory) {
		String hql = "from PresentCategory presentCategory where presentCategory != ? and presentCategory.id in(:ids) order by presentCategory.orderList asc";
		String[] ids = presentCategory.getPath().split(PresentCategory.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, presentCategory).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PresentCategory> getChildrenPresentCategoryList(PresentCategory presentCategory) {
		String hql = "from PresentCategory presentCategory where presentCategory != ? and presentCategory.path like ? order by presentCategory.orderList asc";
		return getSession().createQuery(hql).setParameter(0, presentCategory).setParameter(1, presentCategory.getPath() + "%").list();
	}
	
	// 重写方法，保存时计算path值
	@Override
	public String save(PresentCategory presentCategory) {
		String id = super.save(presentCategory);
		PresentCategory parent = presentCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			presentCategory.setPath(parentPath + PresentCategory.PATH_SEPARATOR + id);
		} else {
			presentCategory.setPath(id);
		}
		super.update(presentCategory);
		return id;
	}
	
	// 重写方法，更新时计算path值
	@Override
	public void update(PresentCategory presentCategory) {
		PresentCategory parent = presentCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			presentCategory.setPath(parentPath + PresentCategory.PATH_SEPARATOR + presentCategory.getId());
		} else {
			presentCategory.setPath(presentCategory.getId());
		}
		super.update(presentCategory);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<PresentCategory> getAll() {
		String hql = "from PresentCategory presentCategory order by presentCategory.orderList asc presentCategory.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<PresentCategory> getList(String propertyName, Object value) {
		String hql = "from PresentCategory presentCategory where presentCategory." + propertyName + "=? order by presentCategory.orderList asc presentCategory.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PresentCategory.class);
		return this.findByPager(pager, detachedCriteria);
	}

}
