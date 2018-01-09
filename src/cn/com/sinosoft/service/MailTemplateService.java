package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Admin;

/**
 * Service接口 - 创建邮件模版
 * ============================================================================
 *  
 *
 * KEY:SINOSOFT3F6674D6C2E7DF7287EF69622E4F46B5
 * ============================================================================
 */

public interface MailTemplateService extends BaseService<Admin, String> {

	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean addMailTemplate(String fileName,String description,String subject);
	

}