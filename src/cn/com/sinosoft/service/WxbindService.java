package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Wxbind;


/**
 * Service接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT868582BB07E8457F3171FCCADB94B449
 * ============================================================================
 */

public interface WxbindService extends BaseService<Wxbind, String> {
	
	/**
	 * 根据openid获取微信绑定信息
	 * @param openID
	 * @return
	 */
	public Wxbind getWxbindByOpenID(String openID);

	public void bindOpenIdAndMemberId(Wxbind wxbind);
}