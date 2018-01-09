package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.service.MailService;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台Action类 - 邮箱
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT72C2E9AA7ADCD7AA59BF9EC2590B1829
 * ============================================================================
 */

@ParentPackage("admin")
public class MailAction extends BaseAdminAction {

	private static final long serialVersionUID = -601752481727227841L;

	private String smtpFromMail="z3031728@163.com.cn";
	private String smtpHost="smtp.163.com";
	private Integer smtpPort=25;
	private String smtpUsername="z3031728";
	private String smtpPassword="8767xxxx";
	private String smtpToMail="zhouxiang@sinosoft.com.cn";
	
	@Resource
	private MailService mailService;
	
//	@Resource
//	private ActionUtilService actionUtilService;
	
	// 发送SMTP测试邮件
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "smtpFromMail", message = "发件人邮箱不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpHost", message = "SMTP服务器地址不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpUsername", message = "SMTP用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpPassword", message = "SMTP密码不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpToMail", message = "收件人邮箱不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "smtpPort", message = "SMTP服务器端口不允许为空!")
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "smtpPort", min = "0", message = "SMTP端口必须为零正整数!")
		},
		emails = {
			@EmailValidator(fieldName = "smtpFromMail", message = "发件人邮箱格式错误!"),
			@EmailValidator(fieldName = "smtpToMail", message = "收件人邮箱格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxSendSmtpTest() {
		try {
			Map<String,Object> data =new HashMap<String, Object>();
			data.put("captcha", "1111");
			//actionUtilService.doAction("a001", data, null);
			//mailService.sendSmtpTestMail(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, smtpToMail);
			MailConfig mailConfig = TemplateConfigUtil.getMailConfig("a0001");
			String templateFilePath = mailConfig.getTemplateFilePath();
			mailService.sendMail("", templateFilePath, data, smtpToMail);
		} catch (MailAuthenticationException e) {
			return ajaxJsonErrorMessage("权限验证失败，请检查SMTP用户名、密码！");
		} catch (MailSendException e) {
			return ajaxJsonErrorMessage("邮件发送失败，请检查发件人邮箱、SMTP服务器地址、端口！");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("邮件发送失败！");
		}
		return ajaxJsonSuccessMessage("测试邮件发送成功，请查收邮件！");
	}
	
	public void t1() {
		try {
			Map<String,Object> data =new HashMap<String, Object>();
			//mailService.sendSmtpTestMail(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, smtpToMail);
			MailConfig mailConfig = TemplateConfigUtil.getMailConfig("a0001");
			String templateFilePath = mailConfig.getTemplateFilePath();
			mailService.sendMail("", templateFilePath, data, smtpToMail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpToMail() {
		return smtpToMail;
	}

	public void setSmtpToMail(String smtpToMail) {
		this.smtpToMail = smtpToMail;
	}

}