package cn.com.sinosoft.action.admin;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.sinosoft.cms.pub.PublicHead;
import com.sinosoft.utility.DataCache;
import freemarker.template.Configuration;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import java.util.Date;

/**
 * 后台Action类 - 缓存
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT0FAAA132C15B2FB2C919342F341B91C6
 * ============================================================================
 */

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {   

	private static final long serialVersionUID = 3290111140770511789L;
	@Resource
	private GeneralCacheAdministrator cacheManager;
	@Resource
	private FreemarkerManager freemarkerManager;
	
	// 刷新所有缓存
	public String flush() {
		cacheManager.flushAll();
		flushCache();
		DataCache.clean();
		PublicHead.clean();
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
//			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
			Configuration c = (Configuration)servletContext.getAttribute(FreemarkerManager.CONFIG_SERVLET_CONTEXT_KEY);
		    c.clearTemplateCache();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}

}