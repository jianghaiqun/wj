package cn.com.sinosoft.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.PresentDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;
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
public class PresentDaoImpl extends BaseDaoImpl<Present, String> implements PresentDao {
	
	//首页产品展现
	@SuppressWarnings("unchecked")
	public List<Present> getIndexPresentList(PresentCategory presentCategory){
		String hql = "from Present as present where present.isMarketable = ? and present.presentCategory.path like ?  order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, presentCategory.getPath() + "%").list();	
	}
	@SuppressWarnings("unchecked")
	public List<Present> getPresentList(PresentCategory presentCategory) {
		String hql = "from Present as present where present.isMarketable = ? and present.presentCategory.path like ? order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, presentCategory.getPath() + "%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Present> getPresentList(int firstResult, int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Present> getPresentList(PresentCategory presentCategory, int firstResult, int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.presentCategory.path like ? order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, presentCategory.getPath() + "%").setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Present> getPresentList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		if (beginDate != null && endDate == null) {
			String hql = "from Present as present where present.isMarketable = ? and present.createDate > ? order by present.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate == null) {
			String hql = "from Present as present where present.isMarketable = ? and present.createDate < ? order by present.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate != null) {
			String hql = "from Present as present where present.isMarketable = ? and present.createDate > ? and present.createDate < ? order by present.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else {
			String hql = "from Present as present where present.isMarketable = ? order by present.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	public Pager getPresentPager(PresentCategory presentCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Present.class);
		detachedCriteria.createAlias("presentCategory", "presentCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("presentCategory", presentCategory), Restrictions.like("presentCategory.path", presentCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isMarketable", true));
		return super.findByPager(pager, detachedCriteria);
	}
//=============================首页产品中心==================================================
	public Pager getPresentHomePager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Present.class);
//		detachedCriteria.createAlias("presentCategory", "presentCategory");
//		detachedCriteria.add(Restrictions.or(Restrictions.eq("presentCategory", presentCategory), Restrictions.like("presentCategory.path", presentCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isMarketable", true));
		return super.findByPager(pager, detachedCriteria);
	}
//=============================首页产品中心==================================================
	@SuppressWarnings("unchecked")
	public List<Present> getBestPresentList(int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isBest = ? order by present.name, present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Present> getBestPresentList(PresentCategory presentCategory, int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isBest = ? and (presentCategory = ? or present.presentCategory.path like ?) order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, presentCategory).setParameter(3, presentCategory.getPath() + "%").list();
	}

	@SuppressWarnings("unchecked")
	public List<Present> getHotPresentList(int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isHot = ? order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Present> getHotPresentList(PresentCategory presentCategory, int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isHot = ? and (presentCategory = ? or present.presentCategory.path like ?) order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, presentCategory).setParameter(3, presentCategory.getPath() + "%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Present> getNewPresentList(int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isNew = ? order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Present> getNewPresentList(PresentCategory presentCategory, int maxResults) {
		String hql = "from Present as present where present.isMarketable = ? and present.isNew = ? and (presentCategory = ? or present.presentCategory.path like ?) order by present.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, presentCategory).setParameter(3, presentCategory.getPath() + "%").list();
	}
	public Pager getFavoritePresentPager(Member member, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Present.class);
		detachedCriteria.createAlias("favoriteMemberSet", "favoriteMemberSet");
		detachedCriteria.add(Restrictions.eq("favoriteMemberSet.id", member.getId()));
		detachedCriteria.addOrder(Order.desc("name"));
		return findByPager(pager, detachedCriteria);
	}
	
	public Long getStoreAlertCount() {
		String hql = "select count(*) from Present as present where present.isMarketable = ? and present.store - present.freezeStore <= ?";
		return (Long) getSession().createQuery(hql).setParameter(0, true).setParameter(1, SystemConfigUtil.getSystemConfig().getStoreAlertCount()).uniqueResult();
	}
	
	public Long getMarketablePresentCount() {
		String hql = "select count(*) from Present as present where present.isMarketable = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
	}
	
	public Long getUnMarketablePresentCount() {
		String hql = "select count(*) from Present as present where present.isMarketable = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, false).uniqueResult();
	}
//获取present属性测试
	@SuppressWarnings("unchecked")
	public List<Present> getAllPresentAttr(String id) {
		String hql = "select a.* from Present as p,Presentinstype as t,Presentinsattribute as a where p.presentinstype_id=t.id and a.presentinstype_id=t.idand p.id=?";
		return getSession().createQuery(hql).setParameter(0,id).list();
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Present present = load(id);
		this.delete(present);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Present present = load(id);
			this.delete(present);
		}
	}
	@SuppressWarnings("unchecked")
	public List<Present> getNewPresentList() {
		String hql = "from Present as present where isNew='1' GROUP BY present.actCode order by present.createDate desc";
		return getSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	public Present getNewPresentInfo(String id) {
		String hql = "from Present as present  where id=?";
		return (Present)getSession().createQuery(hql).setParameter(0,id).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<Present> getMobilePresentInfo(String htmlFilePath,String actCode) {
		String hql = "from Present as present  where htmlFilePath=? and actCode=?";
		return getSession().createQuery(hql).setParameter(0,htmlFilePath).setParameter(1,actCode).list();
	}


}