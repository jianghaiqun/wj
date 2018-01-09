package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.OrderItemDao;
import cn.com.sinosoft.dao.PresentObtainDao;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.PresentObtain;

/**
 * Dao实现类 - 兑换记录
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD05ED0DBA7D4396E779CC3E07ED13EAF
 * ============================================================================
 */

@Repository
public class PresentObtainDaoImpl extends BaseDaoImpl<PresentObtain, String> implements PresentObtainDao {

	@Override
	public PresentObtain getByOrP(String presentId) {
		String hql = "from PresentObtain as presentObtain where PresentObtain.product.id = ?";
		return (PresentObtain)getSession().createQuery(hql).setParameter(0, presentId).uniqueResult();
	}



}