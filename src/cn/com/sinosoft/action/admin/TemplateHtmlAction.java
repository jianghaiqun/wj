package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.HtmlConfig;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * 后台Action类 - 静态模板
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT24688E4E855C92B9AB5435A7725A65EA
 * ============================================================================
 */

@ParentPackage("admin")
public class TemplateHtmlAction extends BaseAdminAction {

	private static final long serialVersionUID = -4868275299665932737L;
	
	private HtmlConfig htmlConfig;
	private String templateFileContent;
	
	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		htmlConfig = TemplateConfigUtil.getHtmlConfig(htmlConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(htmlConfig);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		htmlConfig = TemplateConfigUtil.getHtmlConfig(htmlConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(htmlConfig, templateFileContent);
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		redirectionUrl = "template_html!list.action";
		return SUCCESS;
	}

	// 获取生成静态模板配置集合
	public List<HtmlConfig> getHtmlConfigList() {
		return TemplateConfigUtil.getHtmlConfigList();
	}

	public HtmlConfig getHtmlConfig() {
		return htmlConfig;
	}

	public void setHtmlConfig(HtmlConfig htmlConfig) {
		this.htmlConfig = htmlConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}