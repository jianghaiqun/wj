package cn.com.sinosoft.dao.impl;



import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InformationDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.OrderItem;

/**
 * Dao实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository
public class InformationDaoImpl extends BaseDaoImpl<Information, String> implements InformationDao {

//	@Override
//	public Information getByOrder(String id) {
//		String hql = "from Information as information where information.orderItem.order.id = ?";
//		return (Information) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
//	}

	@Override
	public Information getByProduct(String id,String orderId) {
		String hql = "from Information as information where information.orderItem.productId = ? and information.orderItem.order.id = ?";
		return (Information) getSession().createQuery(hql).setParameter(0, id).setParameter(1, orderId).uniqueResult();
	}
	@Override
	public Information getByOrP(String orderItemId) {
		String hql = "from Information as information where information.orderItem.id = ?";
		return (Information)getSession().createQuery(hql).setParameter(0, orderItemId).uniqueResult();
	}
	
	
}