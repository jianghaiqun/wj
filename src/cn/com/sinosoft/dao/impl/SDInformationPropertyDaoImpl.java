package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInformationPropertyDao;
import cn.com.sinosoft.entity.SDInformationProperty;

@Repository
public class SDInformationPropertyDaoImpl extends BaseDaoImpl<SDInformationProperty,String> implements SDInformationPropertyDao{

	@Override
	public SDInformationProperty getByInsuredId(String insuredId) {
		String hql = "from SDInformationProperty as property where property.sdinformationinsured.id = ?";
		return (SDInformationProperty)getSession().createQuery(hql).setParameter(0, insuredId).uniqueResult();
	}
}
