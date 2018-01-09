package cn.com.sinosoft.service;

import javax.servlet.http.HttpServletRequest;

import cn.com.sinosoft.entity.SDAppnt;

/**
 * Service接口 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB99DB2E7E2EF324F36B462FD6C183267
 * ============================================================================
 */

public interface SDAppntService extends BaseService<SDAppnt, String> {
	
	/**
	 * 赠险自动注册
	 */
	public void userRegisted(SDAppnt cSDAppnt,HttpServletRequest request);    
}