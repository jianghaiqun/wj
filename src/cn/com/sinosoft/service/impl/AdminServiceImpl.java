package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.AdminDao;
import cn.com.sinosoft.entity.Admin;
import cn.com.sinosoft.service.AdminService;

/**
 * Service实现类 - 管理员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTEEB47174264A6259B9A1BA5D141B72C0
 * ============================================================================
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}

	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}

	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

}