package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.FriendLinkDao;
import cn.com.sinosoft.entity.FriendLink;

/**
 * Dao实现类 - 友情链接
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT101A4BDAB567AF3A03292AE07AFE4253
 * ============================================================================
 */

@Repository
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, String> implements FriendLinkDao {

	@SuppressWarnings("unchecked")
	public List<FriendLink> getPictureFriendLinkList() {
		String hql = "from FriendLink friendLink where friendLink.logo is not null order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<FriendLink> getTextFriendLinkList() {
		String hql = "from FriendLink friendLink where friendLink.logo is null order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<FriendLink> getAll() {
		String hql = "from FriendLink friendLink order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<FriendLink> getList(String propertyName, Object value) {
		String hql = "from FriendLink friendLink where friendLink." + propertyName + "=? order by friendLink.orderList asc friendLink.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FriendLink.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
}