package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.RoleDao;
import cn.com.sinosoft.entity.Role;
import cn.com.sinosoft.service.RoleService;
import cn.com.sinosoft.util.SpringUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 角色
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5780B32776CB0A6FF3A3530C4BC96D54
 * ============================================================================
 */

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
	
	@Resource
	RoleDao roleDao;

	@Resource
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}
	
	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(Role role) {
		roleDao.delete(role);
		roleDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String id) {
		Role role = roleDao.load(id);
		this.delete(role);
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Role role = roleDao.load(id);
			roleDao.delete(role);
		}
		roleDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，保存时刷新SpringSecurity权限信息
	@Override
	public String save(Role role) {
		String id = roleDao.save(role);
		roleDao.flush();
		roleDao.clear();
		flushSpringSecurity();
		return id;
	}

	// 重写方法，更新时刷新SpringSecurity权限信息
	@Override
	public void update(Role role) {
		roleDao.update(role);
		roleDao.flush();
		flushSpringSecurity();
	}
	
	// 刷新SpringSecurity权限信息
	private void flushSpringSecurity() {
		try {
			FactoryBean factoryBean = (FactoryBean)SpringUtil.getBean("&adminSecurityDefinitionSource");
			FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource) factoryBean.getObject();
		    FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) SpringUtil.getBean("filterSecurityInterceptor");
		    filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}