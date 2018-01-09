package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.PaymentCompanyDao;
import cn.com.sinosoft.entity.PaymentCompany;
import cn.com.sinosoft.service.PaymentCompanyService;

/**
 * Service实现类 - 保险公司支付方式管理
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1F694A92A7CFA4B05E87616ADCA7D169
 * ============================================================================
 */

@Service
public class PaymentCompanyServiceImpl extends BaseServiceImpl<PaymentCompany,String> implements PaymentCompanyService {
	@Resource
	private PaymentCompanyDao paymentcompanydao;
	@Resource
	public void setBaseDao(PaymentCompanyDao paymentcompanydao) {
		super.setBaseDao(paymentcompanydao);
	}
	
	@Cacheable(modelId = "caching")
    public List<PaymentCompany> getBrandPaymentCompany(String  brandid){
		return paymentcompanydao.getBrandPaymentCompany(brandid);
	}
	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		paymentcompanydao.delete(ids);
	}
}
