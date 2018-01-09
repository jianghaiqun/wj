package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Agreement;

/**
 * Dao接口 - 会员注册协议
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAD6A2E1229B83B3D983EBEB49F0BC7E0
 * ============================================================================
 */
public interface AgreementDao extends BaseDao<Agreement, String> {

	/**
	 * 获取Agreement对象
	 * 
	 * @return Agreement对象
	 * 
	 */
	public Agreement getAgreement();

}
