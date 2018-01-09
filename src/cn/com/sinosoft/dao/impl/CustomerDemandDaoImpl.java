package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.CustomerDemandDao;
import cn.com.sinosoft.entity.CustomerDemand;

/**
 * Dao实现类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository
public class CustomerDemandDaoImpl extends BaseDaoImpl<CustomerDemand, String> implements CustomerDemandDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDemand> getCustomerDemandList(int firstResult,int maxResults) {
		String hql = "from CustomerDemand as customerDemand order by customerDemand.createDate desc";
		return getSession().createQuery(hql).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	@Override
	public List<CustomerDemand> getCustomerDemandGpList(int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDemand> getCustomerDemandInsNameList(int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDemand> getCustomerDemandInsTypeList(int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}
}