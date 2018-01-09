package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.PaymentCompanyDao;
import cn.com.sinosoft.entity.PaymentCompany;
import cn.com.sinosoft.entity.PaymentConfig;

/**
 * Dao实现类 -保险公司支付方式管理
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAAD838C2FAD2E047153F618E017ED96E
 * ============================================================================
 */

@Repository
public class PaymentCompanyDaoImpl extends BaseDaoImpl<PaymentCompany,String> implements PaymentCompanyDao{
	@SuppressWarnings("unchecked")
	public List<PaymentCompany> getBrandPaymentCompany(String  brandid){
		String hql = "from PaymentCompany as paymentcompany   where paymentcompany.brand.id = ?  order by paymentcompany.createDate desc";
		return getSession().createQuery(hql).setParameter(0, brandid).list();
		
	}
	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			PaymentCompany paymentcompany = load(id);
			this.delete(paymentcompany);
		}
	}

}
