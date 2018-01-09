package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.AdminDao;
import cn.com.sinosoft.entity.Admin;

/**
 * Dao实现类 - 管理员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT2A5505E6E90FBFF03FB4B33E05AE2E5C
 * ============================================================================
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
}