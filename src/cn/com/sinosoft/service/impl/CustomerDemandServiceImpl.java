package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.CustomerDemandDao;
import cn.com.sinosoft.entity.CustomerDemand;
import cn.com.sinosoft.service.CustomerDemandService;

/**
 * Service实现类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9A3ACBDD2D3A5E96E54D057769342EAF
 * ============================================================================
 */

@Service
public class CustomerDemandServiceImpl extends BaseServiceImpl<CustomerDemand, String> implements CustomerDemandService {

	@Resource
	private CustomerDemandDao customerDemandDao;
	@Resource
	public void setBaseDao(CustomerDemandDao customerDemandDao) {
		super.setBaseDao(customerDemandDao);
	}
	@Override
	public List<CustomerDemand> getCustomerDemandList(int firstResult,	int maxResults) {
		return customerDemandDao.getCustomerDemandList(firstResult, maxResults);
	}
}