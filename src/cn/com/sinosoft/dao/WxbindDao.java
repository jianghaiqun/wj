package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Wxbind;


/**
 * Dao接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7DE6DBC156621DE89E663E0E451C2E85
 * ============================================================================
 */

public interface WxbindDao extends BaseDao<Wxbind, String> {
	
	public Wxbind getWxbindByOpenID(String openID);

	public int bindOpenIdAndMemberId(Wxbind wxbind);
}