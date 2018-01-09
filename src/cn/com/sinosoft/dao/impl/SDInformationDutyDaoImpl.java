package cn.com.sinosoft.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInformationDutyDao;
import cn.com.sinosoft.entity.SDInformationDuty;

/**
 * Dao实现类 - 险种信息
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
public class SDInformationDutyDaoImpl extends BaseDaoImpl<SDInformationDuty, String> implements SDInformationDutyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SDInformationDuty> getByOrderSn(String orderSn) {
		String hql = "from SDInformationDuty as duty where duty.orderSn = ?";
		return getSession().createQuery(hql).setParameter(0, orderSn).list();
	}
	
}