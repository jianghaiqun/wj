package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Admin;

/**
 * Dao接口 - 管理员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT6EC45B0FCAF1A33579C2CA7460906C3F
 * ============================================================================
 */

public interface AdminDao extends BaseDao<Admin, String> {
	
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);

}