package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.PaymentCompany;

/**
 * Service接口 - 保险公司支付方式
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT6293041B8DFA2A13E8B33FEC71AA8B5D
 * ============================================================================
 */
public interface PaymentCompanyService extends BaseService<PaymentCompany,String>{
	/*
	 * 根据保险公司取此公司下的所以对象
	 */
  public List<PaymentCompany> getBrandPaymentCompany(String  brandid);
}
