package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.RefundDao;
import cn.com.sinosoft.entity.Refund;

/**
 * Dao实现类 - 退款
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT87BA9D2CA8BCC7CF406CEAC791678E86
 * ============================================================================
 */

@Repository
public class RefundDaoImpl extends BaseDaoImpl<Refund, String> implements RefundDao {
	
	@SuppressWarnings("unchecked")
	public String getLastRefundSn() {
		String hql = "from Refund as refund order by refund.createDate desc";
		List<Refund> refundList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (refundList != null && refundList.size() > 0) {
			return refundList.get(0).getRefundSn();
		} else {
			return null;
		}
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		String hql = "from Refund as refund where refund.refundSn = ?";
		return (Refund) getSession().createQuery(hql).setParameter(0, refundSn).uniqueResult();
	}

}