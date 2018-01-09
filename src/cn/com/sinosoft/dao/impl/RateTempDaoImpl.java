/**
 * @CopyRight:Sinosoft
 * @Project:ZKING-PORTAL
 * @File:RateTempDaoImpl.java
 * @CreateTime:2012-4-26 上午7:20:45
 * @Package:cn.com.sinosoft.zking.dao.impl
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.dao.impl;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.com.sinosoft.dao.RateTempDao;
import cn.com.sinosoft.entity.PremiumRateTemp;

/**
 * @author LiuXin
 *
 */
@Repository
public class RateTempDaoImpl extends BaseDaoImpl<PremiumRateTemp,Long> implements RateTempDao{
	public boolean deleteRateTempBySql(String sql){
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
	public boolean savePrt(PremiumRateTemp prt) {
		try {
			Assert.notNull(prt, "entity is required");
			getSession().save(prt);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
}
