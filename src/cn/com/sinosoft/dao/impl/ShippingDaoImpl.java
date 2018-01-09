package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.ShippingDao;
import cn.com.sinosoft.entity.Shipping;

/**
 * Dao实现类 - 发货
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD6D1EE134504CE3150EDDFCA5A22242B
 * ============================================================================
 */

@Repository
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, String> implements ShippingDao {
	
	@SuppressWarnings("unchecked")
	public String getLastShippingSn() {
		String hql = "from Shipping as shipping order by shipping.createDate desc";
		List<Shipping> shippingList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (shippingList != null && shippingList.size() > 0) {
			return shippingList.get(0).getShippingSn();
		} else {
			return null;
		}
	}

}