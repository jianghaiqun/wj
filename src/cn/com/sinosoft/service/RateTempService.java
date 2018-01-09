/**
 * @CopyRight:Sinosoft
 * @File:RateTempService.java
 * @Package:cn.com.sinosoft.service
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.service;


import cn.com.sinosoft.entity.PremiumRateTemp;

/**
 * @author LiuXin
 *
 */
public interface RateTempService extends BaseService<PremiumRateTemp,Long>{
	public boolean deleteRateTempBySql(String sql);
	public boolean savePrt(PremiumRateTemp prt);

}
