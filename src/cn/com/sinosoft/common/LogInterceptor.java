package cn.com.sinosoft.common;

import java.util.List;

import javax.annotation.Resource;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.com.sinosoft.action.admin.BaseAdminAction;
import cn.com.sinosoft.entity.Log;
import cn.com.sinosoft.entity.LogConfig;
import cn.com.sinosoft.service.AdminService;
import cn.com.sinosoft.service.LogConfigService;
import cn.com.sinosoft.service.LogService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

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

public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 276741467699160227L;
	
	public static final String[] excludeActionClassNames = new String[] {"cn.com.sinosoft.action.admin.InstallAction"};// 需要排除的Action类名称
	public static final String[] excludeActionMethodNames = new String[] {};// 需要排除的Action方法名称

	@Resource
	private LogService logService;
	@Resource
	private AdminService adminService;
	@Resource
	private LogConfigService logConfigService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		invocation.invoke();
		String actionClassName = invocation.getAction().getClass().getName();
		String actionMethodName = invocation.getProxy().getMethod();
		if (ArrayUtils.contains(excludeActionClassNames, actionClassName)) {
			return null;
		}
		if (ArrayUtils.contains(excludeActionMethodNames, actionMethodName)) {
			return null;
		}
		List<LogConfig> allLogConfig = logConfigService.getAll();
		if (allLogConfig != null) {
			for (LogConfig logConfig : allLogConfig) {
				if (StringUtils.equals(logConfig.getActionClassName(), actionClassName)
						&& StringUtils.equals(logConfig.getActionMethodName(), actionMethodName)) {
					BaseAdminAction baseAction = (BaseAdminAction) invocation.getAction();
					String logInfo = baseAction.getLogInfo();
					String operator = adminService.getLoginAdmin().getUsername();
					if(operator == null) {
						operator = "未知用户";
					}
					String ip = ServletActionContext.getRequest().getRemoteAddr();
					String channel=ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
					String operationName = logConfig.getOperationName();
					Log log = new Log();
					if(channel.equals("http://127.0.0.1:8080/")){
						log.setChannel("1")	;
					}
					else{
						log.setChannel("2")	;
					}
					log.setOperationName(operationName);
					log.setActionClassName(actionClassName);
					log.setActionMethodName(actionMethodName);
					log.setOperator(operator);
					log.setIp(ip);
					log.setInfo(logInfo);
					logService.save(log);
					break;
				}
			}
		}
		return null;
	}

}