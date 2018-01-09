package cn.com.sinosoft.dao.impl;



import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InformationDao;
import cn.com.sinosoft.dao.SDInformationDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.SDInformation;

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
public class SDInformationDaoImpl extends BaseDaoImpl<SDInformation, String> implements SDInformationDao {

	@Override
	public SDInformation getByOrderId(String id) {
		String hql = "from SDInformation as information where information.sdorder.id = ?";
		return (SDInformation) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public SDInformation getByProduct(String id,String orderId) {
		String hql = "from SDInformation as information where information.orderItem.productId = ? and information.orderItem.order.id = ?";
		return (SDInformation) getSession().createQuery(hql).setParameter(0, id).setParameter(1, orderId).uniqueResult();
	}
	@Override
	public SDInformation getByOrP(String orderItemId) {
		String hql = "from Information as information where information.orderItem.id = ?";
		return (SDInformation)getSession().createQuery(hql).setParameter(0, orderItemId).uniqueResult();
	}
	@Override
	public SDInformation getByOrderSn(String orderSn) {
		String hql = "from SDInformation as information where information.orderSn = ?";
		return (SDInformation) getSession().createQuery(hql).setParameter(0, orderSn).uniqueResult();
	}
	
}