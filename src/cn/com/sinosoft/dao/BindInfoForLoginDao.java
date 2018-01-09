package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.SDOrder;

/**
 * Dao接口 - 绑定信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9B6182BB453DB3970191ECBF6F4D8AD0
 * ============================================================================
 */

public interface BindInfoForLoginDao extends BaseDao<BindInfoForLogin, String> {
	
	public BindInfoForLogin getBindInfoForLoginByOpenID(String openid);
	
}