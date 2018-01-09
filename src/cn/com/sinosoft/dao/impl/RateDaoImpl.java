/**
 * @CopyRight:Sinosoft
 * @File:RateDaoImpl.java
 * @Package:cn.com.sinosoft.dao.impl
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.dao.RateDao;
import cn.com.sinosoft.entity.PremiumRate;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiuXin
 *
 */
@Repository
public class RateDaoImpl extends BaseDaoImpl<PremiumRate,Long> implements RateDao{

	@Override
	public boolean deleteRateTempBySql(String sql) {
		try {
			int ret = this.getSession().createSQLQuery(sql).executeUpdate();
			if(ret!=-1){
				return true;
			}else{
				return false;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean saveByRateSql(String sql) {
		try {
			int ret = this.getSession().createSQLQuery(sql).executeUpdate();
			if(ret!=-1){
				return true;
			}else{
				return false;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public PremiumRate getPremiumOrRate(String sql) {
		List<PremiumRate> list = this.getSession().createSQLQuery(sql).addEntity(PremiumRate.class).list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
