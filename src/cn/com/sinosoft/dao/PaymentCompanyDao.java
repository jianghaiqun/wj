package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.PaymentCompany;

/**
 * Dao接口 - 保险公司支付方式管理
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF0640ED29F118E8C97B3B48248458ACA
 * ============================================================================
 */
public interface PaymentCompanyDao extends BaseDao<PaymentCompany,String>{
	/*
	 * 根据保险公司取此公司下的所以对象
	 */
  public List<PaymentCompany> getBrandPaymentCompany(String  brandid);

}
