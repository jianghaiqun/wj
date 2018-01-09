/**
 * @CopyRight:Sinosoft
 * @File:RateService.java
 * @Package:cn.com.sinosoft.service
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.service;


import java.util.List;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.PremiumRate;

/**
 * @author LiuXin
 *
 */
public interface RateService extends BaseService<PremiumRate,Long>{
	public boolean saveRateBySql(String sql);
	public boolean deleteRateBySql(String sql);
	public List<PremiumRate> findByQBs(List<QueryBuilder> qbs, String orderBy,String orderType);
}
