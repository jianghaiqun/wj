package cn.com.sinosoft.bean;


/**
 * Bean类 - 邮件配置
 * ============================================================================
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB7808BFD2543A7A80440B320DC4A3B9B
 * ============================================================================
 */

public class MailConfig {
	
	public static final String SMTP_TEST = "smtpTest";//  SMTP邮箱配置测试
	public static final String SMTP_SIGN = "smtpSign";//  SMTP邮箱配置测试
	public static final String PASSWORD_RECOVER = "passwordRecover";// 密码找回
	
	private String name;// 配置名称
	private String description;// 描述
	private String subject;// 主题
	private String templateFilePath;// Freemarker模板文件路径
	private String isEmail;// 模板是否正在使用

	public String getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}