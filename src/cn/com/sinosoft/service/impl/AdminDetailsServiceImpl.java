package cn.com.sinosoft.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.dao.AdminDao;
import cn.com.sinosoft.entity.Admin;
import cn.com.sinosoft.entity.Role;
import cn.com.sinosoft.util.SystemConfigUtil;

/**
 * Service实现类 - 后台权限认证
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB3694CBA6550E55995F7798B7B95AE5B
 * ============================================================================
 */

@Service
@Transactional
public class AdminDetailsServiceImpl implements UserDetailsService {

	private static final long serialVersionUID = 2653636739190406891L;

	@Resource
	private AdminDao adminDao;

	public Admin loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		Admin admin = adminDao.getAdminByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
		}
		// 解除管理员账户锁定
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		if (admin.getIsAccountLocked() == true) {
			if (systemConfig.getIsLoginFailureLock() == true) {
				int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
				if (loginFailureLockTime != 0) {
					Date lockedDate = admin.getLockedDate();
					Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					Date now = new Date();
					if (now.after(nonLockedTime)) {
						admin.setLoginFailureCount(0);
						admin.setIsAccountLocked(false);
						admin.setLockedDate(null);
						adminDao.update(admin);
					}
				}
			} else {
				admin.setLoginFailureCount(0);
				admin.setIsAccountLocked(false);
				admin.setLockedDate(null);
				adminDao.update(admin);
			}
		}
		admin.setAuthorities(getGrantedAuthorities(admin));
		return admin;
	}

	// 获得管理角色数组
	public GrantedAuthority[] getGrantedAuthorities(Admin admin) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (Role role : admin.getRoleSet()) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getValue()));
		}
		return grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]);
	}

}