package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.ArticleCategoryDao;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.ProductCategory;

/**
 * Dao实现类 - 文章分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD739359CCEEC5A2398CCE08730B31986
 * ============================================================================
 */

@Repository
public class ArticleCategoryDaoImpl extends BaseDaoImpl<ArticleCategory, String> implements ArticleCategoryDao {
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getRootArticleCategoryList() {
		String hql = "from ArticleCategory articleCategory where articleCategory.parent is null order by articleCategory.orderList asc";
		return getSession().createQuery(hql).list();
	}
	//根据渠道分类选择文章分类
			@SuppressWarnings("unchecked")
			public List<ArticleCategory> getRootArticleCategoryChannelList(String[] channel) {
				String hql = "from ArticleCategory  as articleCategory  left join  fetch articleCategory.channelSet  as  channel where  channel.id in(:channel) order by articleCategory.orderList asc ";
				return getSession().createQuery(hql).setParameterList("channel", channel).list();
			}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory) {
		String hql = "from ArticleCategory articleCategory where articleCategory != ? and articleCategory.id in(:ids) order by articleCategory.orderList asc";
		String[] ids = articleCategory.getPath().split(ArticleCategory.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, articleCategory).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory) {
		String hql = "from ArticleCategory articleCategory where articleCategory != ? and articleCategory.path like ? order by articleCategory.orderList asc";
		return getSession().createQuery(hql).setParameter(0, articleCategory).setParameter(1, articleCategory.getPath() + "%").list();
	}
	
	// 设置path值
	@Override
	public String save(ArticleCategory articleCategory) {
		String id = super.save(articleCategory);
		ArticleCategory parent = articleCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			articleCategory.setPath(parentPath + ArticleCategory.PATH_SEPARATOR + id);
		} else {
			articleCategory.setPath(id);
		}
		super.update(articleCategory);
		return id;
	}
	
	// 设置path值
	@Override
	public void update(ArticleCategory articleCategory) {
		ArticleCategory parent = articleCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			articleCategory.setPath(parentPath + ArticleCategory.PATH_SEPARATOR + articleCategory.getId());
		} else {
			articleCategory.setPath(articleCategory.getId());
		}
		super.update(articleCategory);
	}

	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleCategory> getAll() {
		String hql = "from ArticleCategory articleCategory order by articleCategory.orderList asc articleCategory.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getList(String propertyName, Object value) {
		String hql = "from ArticleCategory articleCategory where articleCategory." + propertyName + "=? order by articleCategory.orderList asc articleCategory.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleCategory.class);
		return this.findByPager(pager, detachedCriteria);
	}

}