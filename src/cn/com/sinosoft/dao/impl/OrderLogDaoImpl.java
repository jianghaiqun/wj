package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.OrderLogDao;
import cn.com.sinosoft.entity.OrderLog;
import cn.com.sinosoft.util.SystemConfigUtil;

/**
 * Dao实现类 - 订单日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT82C3BEA1D19DE05C159EFE57E1E377CD
 * ============================================================================
 */

@Repository
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, String> implements OrderLogDao {

	@Override
	public OrderLog getByOrderId(String orderId) {
			String hql = "from OrderLog as orderLog where orderLog.order.id = ?";
			return (OrderLog) getSession().createQuery(hql).setParameter(0, orderId).uniqueResult();
	}
	
}