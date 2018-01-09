package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.PaymentDao;
import cn.com.sinosoft.entity.Payment;
import cn.com.sinosoft.service.PaymentService;
import cn.com.sinosoft.util.SerialNumberUtil;

/**
 * Service实现类 - 支付
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT84F252BF71A4D877C0285E8086FBE56D
 * ============================================================================
 */

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {
	
	@Resource
	private PaymentDao paymentDao;

	@Resource
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}
	
	public String getLastPaymentSn() {
		return paymentDao.getLastPaymentSn();
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		return paymentDao.getPaymentByPaymentSn(paymentSn);
	}
	public Pager getPaymentPager(Payment payment, Pager pager){
		return paymentDao.getPaymentPager(payment, pager);
	}

	// 重写对象，保存时自动设置支付编号
	@Override
	public String save(Payment payment) {
		payment.setPaymentSn(SerialNumberUtil.buildPaymentSn());
		return super.save(payment);
	}

}