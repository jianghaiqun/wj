package cn.com.sinosoft.service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Payment;

/**
 * Service接口 - 支付
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB7C9CABB5231718C0C92BE618D9B5D15
 * ============================================================================
 */

public interface PaymentService extends BaseService<Payment, String> {

	/**
	 * 获取最后生成的支付编号
	 * 
	 * @return 支付编号
	 */
	public String getLastPaymentSn();
	
	/**
	 * 根据支付编号获取对象（若对象不存在，则返回null）
	 * 
	 * @return 支付对象
	 */
	public Payment getPaymentByPaymentSn(String paymentSn);
	
	/**
	 * 根据Payment和Pager对象，获取分页对象
	 * 
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getPaymentPager(Payment payment, Pager pager);

}