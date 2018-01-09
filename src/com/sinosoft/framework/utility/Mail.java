package com.sinosoft.framework.utility;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {
	private static final Logger logger = LoggerFactory.getLogger(Mail.class);

	public final static String SUCCESS = "success";  //发送成功
	
	public final static String FAILED_SEND = "failed_send";  //发送失败
	
	public final static String FAILED_WRONG = "failed_wrong"; //传值错误，map为空
	
	public final static String FAILED_EMPTY_TOUSER = "failed_empty_user"; //邮件接收者为空
	
	public final static String FAILED_EMPTY_CONTENT = "failed_empty_content";  //邮件内容为空
	
	public final static String FAILED_EMPTY_URL = "failed_empty_url";  //url为空
	
	public static String sendSimpleEmail(Mapx map) {
		String host = Config.getValue("mail.host");
		String userName = Config.getValue("mail.username");
		String password = Config.getValue("mail.password");
		if (map==null) {
			return FAILED_WRONG;
		} else if (StringUtil.isEmpty(map.getString("ToUser"))) {
			return FAILED_EMPTY_TOUSER;
		} else if (StringUtil.isEmpty(map.getString("Content"))) {
			return FAILED_EMPTY_CONTENT;
		}
		
		String realName = map.getString("RealName");
		if (StringUtil.isEmpty(realName)) {
			realName = map.getString("ToUser");
		}
		
		String subject = map.getString("Subject");
		if (StringUtil.isEmpty(subject)) {
			subject = "来自" + realName + "的系统邮件";
		}
		
		SimpleEmail email = new SimpleEmail();
		try {
			email.setAuthentication(userName, password);
			email.setHostName(host);
			email.addTo(map.getString("ToUser"), realName);
			email.setFrom(userName, password);
			email.setSubject(subject);
			email.setContent(map.getString("Content"), "text/html;charset="+Constant.GlobalCharset);
			email.send();
		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
			return FAILED_SEND;
		}
		return SUCCESS;
	}
	
	public String sendHtmlMail(Mapx map) {
		String host = Config.getValue("mail.host");
		String userName = Config.getValue("mail.username");
		String password = Config.getValue("mail.password");
		if (map==null) {
			return FAILED_WRONG;
		} else if (StringUtil.isEmpty(map.getString("ToUser"))) {
			return FAILED_EMPTY_TOUSER;
		} else if (StringUtil.isEmpty(map.getString("URL"))) {
			return FAILED_EMPTY_URL;
		}
		
		String realName = map.getString("RealName");
		if (StringUtil.isEmpty(realName)) {
			realName = map.getString("ToUser");
		}
		
		String subject = map.getString("Subject");
		if (StringUtil.isEmpty(subject)) {
			subject = "来自" + realName + "的系统邮件";
		}
		
		String htmlContent = FileUtil.readURLText(map.getString("URL"));
		HtmlEmail email = new HtmlEmail();
		try {
			email.setAuthentication(userName, password);
			email.addTo(map.getString("ToUser"), realName);
			email.setFrom(host, userName);
			email.setSubject(subject);
			email.setHtmlMsg(htmlContent);
			email.send();
		} catch (EmailException e) {
			return FAILED_SEND;
		}
		return SUCCESS;
	}
}
