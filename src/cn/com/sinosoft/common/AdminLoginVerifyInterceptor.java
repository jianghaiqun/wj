package cn.com.sinosoft.common;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinosoft.framework.User;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 拦截器 - 管理日志
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFT8EF2677C515B19AF3363B817A1842E24
 * ============================================================================
 */

public class AdminLoginVerifyInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -4427162292703636790L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// admin = adminService.getLoginAdmin();

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		Object adminInfo = session.get("SPRING_SECURITY_LAST_USERNAME");
		// if (!StringUtil.isEmpty(adminInfo + "")) {
		// adminInfo = User.getUserName();
		// }
		if (StringUtil.isEmpty(adminInfo + "")) {
			return "admin_login";
		}
		return invocation.invoke();

	}

}