package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInsuredHealthDao;
import cn.com.sinosoft.entity.SDInsuredHealth;

@Repository
public class SDInsuredHealthDaoImpl extends BaseDaoImpl<SDInsuredHealth,String> implements SDInsuredHealthDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<SDInsuredHealth> getInfoByOrderHealthySn(String order_healthySn) {
		String hql = "from SDInsuredHealth as info where info.orderSn=?";
		List<SDInsuredHealth> infoList =  (List<SDInsuredHealth>)getSession().createQuery(hql).setParameter(0, order_healthySn).list();
		if (infoList != null && infoList.size() > 0) {
			return infoList;
		} else {
			return null;
		}
	}

}
