package cn.com.sinosoft.dao.impl;

import java.util.List;
import java.util.Set;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.PaymentConfigDao;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Payment;
import cn.com.sinosoft.entity.PaymentConfig;
import cn.com.sinosoft.entity.Refund;
import cn.com.sinosoft.entity.PaymentConfig.PaymentConfigType;

/**
 * Dao实现类 - 支付配置
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTE1842B16170CAE78F2D0E2F7E18EEE4B
 * ============================================================================
 */

@Repository
public class PaymentConfigDaoImpl extends BaseDaoImpl<PaymentConfig, String> implements PaymentConfigDao {

	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getNonDepositPaymentConfigList() {
		String hql = "from PaymentConfig as paymentConfig where paymentConfig.paymentConfigType != ? order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).setParameter(0, PaymentConfigType.deposit).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		String hql = "from PaymentConfig as paymentConfig where paymentConfig.paymentConfigType != ? and paymentConfig.paymentConfigType != ? order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).setParameter(0, PaymentConfigType.deposit).setParameter(1, PaymentConfigType.offline).list();
	}
	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getBankPaymentConfigList(){
		String hql = "from PaymentConfig as paymentConfig where paymentConfig.paymentConfigType = ? order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).setParameter(0, PaymentConfigType.bank).list();
	}
	// 关联处理
	@Override
	public void delete(PaymentConfig paymentConfig) {
//		Set<Order> orderSet = paymentConfig.getOrderSet();
//		if (orderSet != null) {
//			for (Order order : orderSet) {
//				order.setPaymentConfig(null);
//			}
//		}
		Set<Payment> paymentSet = paymentConfig.getPaymentSet();
		if (paymentSet != null) {
			for (Payment payment : paymentSet) {
				payment.setPaymentConfig(null);
			}
		}
		Set<Refund> refundSet = paymentConfig.getRefundSet();
		if (refundSet != null) {
			for (Refund refund : refundSet) {
				refund.setPaymentConfig(null);
			}
		}
		super.delete(paymentConfig);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		PaymentConfig paymentConfig = load(id);
		this.delete(paymentConfig);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			PaymentConfig paymentConfig = load(id);
			this.delete(paymentConfig);
		}
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentConfig> getAll() {
		String hql = "from PaymentConfig paymentConfig order by paymentConfig.orderList asc paymentConfig.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getList(String propertyName, Object value) {
		String hql = "from PaymentConfig paymentConfig where paymentConfig." + propertyName + "=? order by paymentConfig.orderList asc paymentConfig.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PaymentConfig.class);
		return this.findByPager(pager, detachedCriteria);
	}

}