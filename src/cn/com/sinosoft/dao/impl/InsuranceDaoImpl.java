package cn.com.sinosoft.dao.impl;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InsuranceDao;
import cn.com.sinosoft.entity.Insurance;

@Repository
public class InsuranceDaoImpl extends BaseDaoImpl<Insurance,String> implements InsuranceDao{

	@Override
	public void deleteOldPremium(String sql) {
		try {
			this.getSession().createSQLQuery(sql).executeUpdate();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
