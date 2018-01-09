package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Insurance;

/**
 * @author LiuXin
 *
 */
public interface InsuranceDao extends BaseDao<Insurance,String>{
	public void deleteOldPremium(String sql);
}
