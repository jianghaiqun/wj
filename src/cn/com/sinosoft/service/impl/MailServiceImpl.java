package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.MailService;
import cn.com.sinosoft.util.SystemConfigUtil;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sohu.sendcloud.Message;
import com.sohu.sendcloud.SendCloud;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Service实现类 - 邮件服务
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT002914E795CA825D58C64186BDBFF7A2
 * ============================================================================
 */

@Service
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	@Resource
	private FreemarkerManager freemarkerManager;
	@Resource
	private JavaMailSender javaMailSender;
	@Resource
	private TaskExecutor taskExecutor;
	
	/**
	 * SendCloud用户名.
	 */
	private final static String SENDCLOUD_USERNAME = "postmaster@kaixinbao.sendcloud.org";
	/**
	 * SendCloud密码.
	 */
	private final static String SENDCLOUD_PASSWORD = "6DZu5oikUbjmjzfJ";
	
	
	public boolean isMailConfigComplete() {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		if (StringUtils.isEmpty(systemConfig.getSmtpFromMail()) || StringUtils.isEmpty(systemConfig.getSmtpHost()) || systemConfig.getSmtpPort() == null || StringUtils.isEmpty(systemConfig.getSmtpUsername()) || StringUtils.isEmpty(systemConfig.getSmtpPassword())) {
			return false;
		} else {
			return true;
		}
	}
	
	// 增加邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage,final Map<String, Object> data) throws Exception {
		try {
			taskExecutor.execute(new Runnable() {
				public void run(){
					try {
						javaMailSender.send(mimeMessage);
					} catch (Exception e) {
						if(!StringUtil.isEmpty(data.get("FilePath")) && !"Y".equals(data.get("m_sign"))){
							errorWarningMail(data);
						}
						logger.error(e.getMessage(), e);

					}
				}
			});
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void sendMail(String subject, String templateFilePath, Map<String, Object> data, String toMail) {
		try {
			subject = java.net.URLDecoder.decode(subject,"utf8");
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			Map<String, Object> data1 = getCommonData();
			data = ActionUtil.getMailConfig(data, data.get("EmailType").toString(),toMail);
			data1.putAll(data);
			ServletContext servletContext = ServletActionContext.getServletContext();
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(systemConfig.getSmtpHost());
			//javaMailSenderImpl.setHost("smtp.163.com");
			javaMailSenderImpl.setPort(systemConfig.getSmtpPort());
			javaMailSenderImpl.setUsername(systemConfig.getSmtpUsername());
			javaMailSenderImpl.setPassword(systemConfig.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			configuration.setDefaultEncoding("UTF-8");
			Template template = configuration.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data1);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(systemConfig.getShopName().toString(), "UTF-8", "B") + " <" + systemConfig.getSmtpFromMail().toString()  + ">");
			//System.out.println(new String(systemConfig.getShopName().getBytes("ISO-8859-1"),"UTF-8") + " <" + systemConfig.getSmtpFromMail().toString() + ">");
			//mimeMessageHelper.setFrom("开心保网"+"<webmail@kaixinbao.com>");
			mimeMessageHelper.setTo(toMail);
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			
			
			
			if(StringUtil.isNotEmpty(data.get("FilePath")+"") && !"Y".equals(data.get("m_sign"))){
//				mimeMessageHelper.addAttachment(MimeUtility.encodeWord((String)data.get("FileName")), new File((String)data.get("FilePath")));
				
				//邮件需求新增部分
				String fileName = MimeUtility.encodeWord((String)data.get("FileName"));
				String filePath = ""+data.get("FilePath");
				String fromName = systemConfig.getShopName().toString();
				String fromAddress = systemConfig.getSmtpFromMail().toString();
				sendMailSoHu(subject,fromAddress,fromName,fileName,filePath, text, toMail,data);
				return;
			}
			
			addSendMailTask(mimeMessage,data);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	* @Title: sendMailSouHu sendcloud.
	* @Description: TODO(sendcloud邮件发送). 
	* @return void    返回类型 
	* @author congzn.
	 */
	public static void sendMailSoHu(String p_subject,String p_fromAddress,String p_fromName,String p_fileName,String p_templateFilePath,String p_data,String p_toMail,final Map<String, Object> data) {
		
		SendCloud sendCloud = null;
		
		try {
			String SendCloudAddress = Config.getValue("SendCloudAddress");
			String SendCloudName = Config.getValue("SendCloudName");
			
			if(StringUtil.isEmpty(SendCloudAddress)){
				SendCloudAddress = p_fromAddress;
			}
			if(StringUtil.isEmpty(SendCloudName)){
				SendCloudName = p_fromName;
			}
			
			Message message = new Message(SendCloudAddress,SendCloudName);
			//主题
			message.setSubject(p_subject);
			//内容
			message.setBody(p_data);
			
			List<String> addressList = new ArrayList<String>();
			message.addRecipients(addressList)
			.addRecipient(p_toMail);
			
			if(StringUtil.isNotEmpty(p_fileName)){
				// 添加附件
				message.addAttachment(p_fileName, p_templateFilePath);
			}
			
			// 组装消息发送邮件
			// 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
			sendCloud = new SendCloud(SENDCLOUD_USERNAME, SENDCLOUD_PASSWORD);
			sendCloud.setMessage(message);
//			sendCloud.setDebug(true); //设置调试, 可以看到java mail的调试信息
			sendCloud.send();
			
		} catch (Exception e) {
			logger.error("sendMailSoHu 发送异常! "+sendCloud.getEmailIdList() + e.getMessage(), e);
			errorWarningMail(data);
		}
		
    }
	
	/**
	 * 
	* @Title: errorWarningMail 
	* @Description: TODO(电子保单发送错误,重新发送,错误告警!) 
	* @return void    返回类型 
	* @author XXX
	 */
	public static void errorWarningMail(final Map<String, Object> data){
		Map<String, Object> map = new HashMap<String, Object>();
		String sMailString = Config.getValue("sendMailError");
		map.put("m_productName", data.get("ProductName"));
		map.put("m_ApplicantName", data.get("ApplicantName"));
		map.put("m_orderSn",data.get("orderSn"));
		map.put("m_toMail", data.get("toMail"));
		ActionUtil.sendMail("wj00095", sMailString, map);
	}
	
	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ServletContext servletContext = ServletActionContext.getServletContext();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", servletContext.getContextPath());
		commonData.put("systemConfig", SystemConfigUtil.getSystemConfig());
		return commonData;
	}
	
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		Map<String, Object> data = getCommonData();
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(MailConfig.SMTP_TEST);
		String subject = mailConfig.getSubject();
		String templateFilePath = mailConfig.getTemplateFilePath();
		ServletContext servletContext = ServletActionContext.getServletContext();
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(systemConfig.getSmtpHost());
			javaMailSenderImpl.setUsername(systemConfig.getSmtpUsername());
			javaMailSenderImpl.setPassword(systemConfig.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			String aa=MimeUtility.encodeWord(systemConfig.getShopName()) + " <" + systemConfig.getSmtpFromMail() + ">";
			mimeMessageHelper.setFrom(aa);
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			
//			mimeMessageHelper.setFrom("周翔"+"<z3031728@163.com>");
//			mimeMessageHelper.setTo(toMail);
//			mimeMessageHelper.setSubject("111");
//			mimeMessageHelper.setText("111", true);
			javaMailSender.send(mimeMessage);
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void sendSmtpSignMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		Map<String, Object> data = getCommonData();
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(MailConfig.SMTP_SIGN);
		String subject = mailConfig.getSubject();
		String templateFilePath = mailConfig.getTemplateFilePath();
		ServletContext servletContext = ServletActionContext.getServletContext();
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(smtpHost);
			javaMailSenderImpl.setPort(smtpPort);
			javaMailSenderImpl.setUsername(smtpUsername);
			javaMailSenderImpl.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(systemConfig.getShopName()) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void sendPasswordRecoverMail(Member member) {
		Map<String, Object> data = getCommonData();
		data.put("Member", member);
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(MailConfig.PASSWORD_RECOVER);
		String subject = mailConfig.getSubject();
		String templateFilePath = mailConfig.getTemplateFilePath();
		sendMail(subject, templateFilePath, data, member.getEmail());
	}
	
	/**
	 * 发送理赔报案邮件提醒
	 * 
	 * @param to
	 *            收件人地址
	 * @param text
	 *            邮件内容
	 * @param title
	 *            邮件标题
	 */
	public void sendBatchMail(String[] to, String text, String title) {
		if (to == null || to.length == 0) {
			logger.error("邮件发送地址不能为空");
		}
		try {
			InternetAddress[] address = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				address[i] = new InternetAddress(to[i]);
			}
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
			javaMailSenderImpl.setHost(systemConfig.getSmtpHost());
			javaMailSenderImpl.setPort(systemConfig.getSmtpPort());
			javaMailSenderImpl.setUsername(systemConfig.getSmtpUsername());
			javaMailSenderImpl.setPassword(systemConfig.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, true, "UTF-8");
			messageHelper.setFrom(MimeUtility.encodeWord(systemConfig
					.getShopName().toString(), "UTF-8", "B")
					+ " <" + systemConfig.getSmtpFromMail().toString() + ">");
			messageHelper.setSubject(title);
			messageHelper.setText(text, true);
			messageHelper.setTo(address);
			mimeMessage = messageHelper.getMimeMessage();
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}