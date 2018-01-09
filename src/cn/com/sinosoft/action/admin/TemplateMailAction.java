package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.service.MailTemplateService;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.framework.GetDBdata;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

/**
 * 后台Action类 - 邮件模板
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFT3AE912EF052249487D08E69CADDD3FCD
 * ============================================================================
 */

@ParentPackage("admin")
public class TemplateMailAction extends BaseAdminAction {

	private static final long serialVersionUID = -3965561383196862741L;

	private MailConfig mailConfig;
	private String templateFileContent;
	private String fileName;
	private String description;
	private String subject;
	private Boolean result;
	

	@Resource
	private MailTemplateService mailTemplateService;

	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		
		return LIST;
	}

	public String add() {

		return "new";
	}
	
	public String modifyState(){
		GetDBdata db = new GetDBdata();
		String updateSql = "update sdinteraction set isemail='"+mailConfig.getIsEmail()+"' where emailtype='"+mailConfig.getName()+"'";
		try {
			result = db.execUpdateSQL(updateSql,null);
		} catch (Exception e) {
			result = false;
			logger.error(e.getMessage(), e);
		}
		return ajaxJson(result.toString());
	}


	// 编辑
	public String edit() {
		mailConfig = TemplateConfigUtil.getMailConfig(mailConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(mailConfig);
		return INPUT;
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!") })
	@InputConfig(resultName = "error")
	public String update() {
		mailConfig = TemplateConfigUtil.getMailConfig(mailConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(mailConfig, templateFileContent);
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		redirectionUrl = "template_mail!list.action";
		return SUCCESS;
	}

	public String save() {
		if (!verifyName(fileName)) {
			addActionError("邮件模版名称首位不能是数字!");
			return ERROR;
		}
		if (!verifyXML(fileName)) {
			addActionError("邮件模版名称重复!");
			return ERROR;
		}
		boolean isOk = mailTemplateService.addMailTemplate(fileName, description, subject);
		if (isOk) {
			redirectionUrl = "template_mail!list.action";
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private boolean verifyName(String fileName) {
		char[] chars = new char[1];
		chars[0] = fileName.charAt(0);
		String temp = new String(chars);
		if (chars[0] >= '0' && chars[0] <= '9') {
			return false;
		}

		return true;
	}

	public static boolean verifyXML(String fileName) {
		String configFilePath = ServletActionContext.getRequest().getRealPath("/WEB-INF/template/");
		try {
			SAXBuilder builder = new SAXBuilder(false);
			org.jdom.Document doc = builder.build(configFilePath+File.separator+"template.xml");
			Element parentElement = (Element) org.jdom.xpath.XPath.selectSingleNode(doc, "/sinosoft/mailConfig/" + fileName);
			if (parentElement != null) {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return true;
	}

	// 获取邮件模板配置集合
	public List<MailConfig> getMailConfigList() {
		return TemplateConfigUtil.getMailConfigList();
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}


}