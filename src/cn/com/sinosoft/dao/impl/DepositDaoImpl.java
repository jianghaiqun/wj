package cn.com.sinosoft.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.DepositDao;
import cn.com.sinosoft.entity.Deposit;
import cn.com.sinosoft.entity.Member;

/**
 * Dao实现类 - 预存款记录
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC18C08E26DB83F5F881422AB66E5DC75
 * ============================================================================
 */

@Repository
public class DepositDaoImpl extends BaseDaoImpl<Deposit, String> implements DepositDao {

	public Pager getDepositPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Deposit.DEFAULT_DEPOSIT_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deposit.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		return super.findByPager(pager, detachedCriteria);
	}
	
}