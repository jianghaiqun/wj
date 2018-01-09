package cn.com.sinosoft.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.PaymentDao;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.Payment;

/**
 * Dao实现类 - 支付
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF9A10498A79B1352A22264FA2960B2E5
 * ============================================================================
 */

@Repository
public class PaymentDaoImpl extends BaseDaoImpl<Payment, String> implements PaymentDao {
	
	@SuppressWarnings("unchecked")
	public String getLastPaymentSn() {
		String hql = "from Payment as payment order by payment.createDate desc";
		List<Payment> paymentList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (paymentList != null && paymentList.size() > 0) {
			return paymentList.get(0).getPaymentSn();
		} else {
			return null;
		}
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		String hql = "from Payment as payment where payment.paymentSn = ?";
		return (Payment) getSession().createQuery(hql).setParameter(0, paymentSn).uniqueResult();
	}
	public Pager getPaymentPager(Payment payment, Pager pager){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Payment.class);
		if(!"".equals(payment.getPayer())){
			detachedCriteria.add(Restrictions.eq("payer", payment.getPayer()));			
		}
		if(payment.getPaymentType()!=null){ 
			detachedCriteria.add(Restrictions.eq("paymentType", payment.getPaymentType()));			
		}
		if(payment.getPaymentStatus()!=null){ 
			detachedCriteria.add(Restrictions.eq("paymentStatus", payment.getPaymentStatus()));			
		}
		if(payment.getCreateDate()!=null){ 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String createDate =sdf.format(payment.getCreateDate());
			detachedCriteria.add(Restrictions.sqlRestriction("date_format(createDate,'%Y%m%d')='"+createDate+"'"));			
		}
		return super.findByPager(pager, detachedCriteria);
	}
}