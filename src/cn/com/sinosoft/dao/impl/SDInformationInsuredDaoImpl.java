package cn.com.sinosoft.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInformationInsuredDao;
import cn.com.sinosoft.entity.SDInformationInsured;

/**
 * Dao实现类 - 受益人信息
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
public class SDInformationInsuredDaoImpl extends BaseDaoImpl<SDInformationInsured, String> implements SDInformationInsuredDao{
	@Override
	public List<SDInformationInsured> getListByOrderSn(String orderSn) {
		String hql = "from SDInformationInsured as informationInsured where informationInsured.orderSn = ?";
		return (List<SDInformationInsured>)getSession().createQuery(hql).setParameter(0, orderSn).list();
	}
	@Override
	public SDInformationInsured getByOrParentId(String informationId) {
		String hql = "from InformationInsured as informationInsured where informationInsured.information.id = ?";
		return (SDInformationInsured)getSession().createQuery(hql).setParameter(0, informationId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SDInformationInsured> getListByOrParentId(String informationId) {
		String hql = "from SDInformationInsured as informationInsured where informationInsured.sdinformation.id = ?";
		return (List<SDInformationInsured>)getSession().createQuery(hql).setParameter(0, informationId).list();
	}
}