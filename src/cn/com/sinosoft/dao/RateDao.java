/**
 * @CopyRight:Sinosoft
 * @File:RateDao.java
 * @Package:cn.com.sinosoft.dao
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.dao;


import cn.com.sinosoft.entity.PremiumRate;

/**
 * @author LiuXin
 *
 */
public interface RateDao extends BaseDao<PremiumRate,Long>{
	public boolean deleteRateTempBySql(String sql) ;
	public boolean saveByRateSql(String sql);
	public PremiumRate getPremiumOrRate(String sql);
}
