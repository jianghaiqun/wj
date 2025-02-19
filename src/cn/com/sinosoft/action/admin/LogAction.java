package cn.com.sinosoft.action.admin;

import javax.annotation.Resource;


import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.Log;
import cn.com.sinosoft.service.LogService;

/**
 * 后台Action类 - 日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT12370A283DAE7C30B22944B7DAC51220
 * ============================================================================
 */

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Log log;

	@Resource
	private LogService logService;

	// 列表
	public String list() {
		pager = logService.findByPager(pager);
		return LIST;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}