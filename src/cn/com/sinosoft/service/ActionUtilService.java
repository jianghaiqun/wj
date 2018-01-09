package cn.com.sinosoft.service;

import java.util.Map;

import org.apache.catalina.connector.Request;

/**
 * Service接口 - 横向接口
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

public interface ActionUtilService extends BaseService<Object, String> {

	/**
	 * 获取SDInterAction对象
	 * 
	 * @return SDInterAction对象
	 * 
	 */
	public boolean doAction(String actionId,Map<String,Object> data, Request request);

}