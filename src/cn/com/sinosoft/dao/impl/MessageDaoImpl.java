package cn.com.sinosoft.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.MessageDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Message;
import cn.com.sinosoft.entity.Message.DeleteStatus;

/**
 * Dao实现类 - 消息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF443BAE0CC7277A3DD71774267D31005
 * ============================================================================
 */

@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{
	
	public Pager getMemberInboxPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("toMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("fromMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("fromMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", true));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager getAdminInboxPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.isNull("toMember"));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager getAdminOutboxPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.isNull("fromMember"));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Long getUnreadMessageCount(Member member) {
		String hql = "select count(*) from Message as message where message.toMember = ? and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, member).setParameter(1, false).setParameter(2, false).setParameter(3, DeleteStatus.toDelete).uniqueResult();
	}
	
	public Long getUnreadMessageCount() {
		String hql = "select count(*) from Message as message where message.toMember is null and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, false).setParameter(1, false).setParameter(2, DeleteStatus.toDelete).uniqueResult();
	}

}