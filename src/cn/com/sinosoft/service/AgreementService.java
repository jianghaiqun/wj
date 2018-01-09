package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Agreement;

/**
 * Service接口 - 会员注册协议
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT2887E727E668781FCD8779E09CB64B99
 * ============================================================================
 */

public interface AgreementService extends BaseService<Agreement, String> {

	/**
	 * 获取Agreement对象
	 * 
	 * @return Agreement对象
	 * 
	 */
	public Agreement getAgreement();

}