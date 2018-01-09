/**
 * @CopyRight:Sinosoft
 * @File:RateTempDap.java
 * @Package:cn.com.sinosoft.dao
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.PremiumRateTemp;

/**
 * @author LiuXin
 *
 */
public interface RateTempDao extends BaseDao<PremiumRateTemp,Long>{
	public boolean deleteRateTempBySql(String sql);
	public boolean savePrt(PremiumRateTemp prt);

}
