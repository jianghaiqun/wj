package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.GALogDao;
import cn.com.sinosoft.entity.GALog;
/**
 * Dao实现类 - GA日志
 * ============================================================================
 * KEY:SINOSOFTAAD838C2FAD2E047153F618E017ED96E
 * ============================================================================
 */
@Repository
public class GALogDaoImpl extends BaseDaoImpl<GALog, String> implements GALogDao {
	public GALog getGALogByOrderSn(String orderSn){
		String hql = "from GALog where orderSn=? ";
		return (GALog)(getSession().createQuery(hql).setParameter(0, orderSn).setFirstResult(0).setMaxResults(1).uniqueResult());
	}
	
	public GALog getGALogByMemberId(String memberId)
	{
		String hql = "from GALog where memberid=? ";
		return (GALog)(getSession().createQuery(hql).setParameter(0, memberId).setFirstResult(0).setMaxResults(1).uniqueResult());
	}
}