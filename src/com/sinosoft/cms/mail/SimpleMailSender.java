package com.sinosoft.cms.mail;

import cn.com.sinosoft.util.PresentConfigUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

/**
 * @ClassName: SimpleMailSender.
 * @Description: TODO(邮件发送).
 * @author CongZN. 
 * @date 2014-8-27 上午10:27:12.
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class SimpleMailSender{
	private static final Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

	private static final long serialVersionUID = 1L;
	
	public static String mailServer;
	public static String mailPort;
	public static String mailUser;
	public static String mailPass;
	public static String mailFrom;
	public static String shopName;
	
	static{
		String configFilePath;
		try {
			configFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + PresentConfigUtil.CONFIG_FILE_NAME;
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(configFilePath));
			parseDOM4J(document);
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @Title: sendTextMail.
	 * @Description: TODO(发送邮件-文本).
	 * @return boolean    返回类型.
	 * @author CongZN.
	 */
	public static synchronized boolean sendTextMail(
			final MailSenderInfo mailInfo) {
		
		mailInfo.setMailServerHost(mailServer);
		mailInfo.setMailServerPort(mailPort);
		mailInfo.setValidate(true);
		mailInfo.setUserName(mailUser);
		mailInfo.setPassword(mailPass);// 您的邮箱密码
		mailInfo.setFromAddress(mailFrom);

		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro,
				new Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(mailInfo
								.getUserName(), mailInfo.getPassword());
					}
				});
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return false;
	}
	
	/**
	 * @Title: sendTextMail.
	 * @Description: TODO(发送邮件-HTML格式).
	 * @return boolean    返回类型.
	 * @author CongZN.
	 * @throws MessagingException 
	 */
	public static synchronized boolean sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException {
		mailInfo.setMailServerHost(mailServer);
		mailInfo.setMailServerPort(mailPort);
		mailInfo.setValidate(true);
		mailInfo.setUserName(mailUser);
		mailInfo.setPassword(mailPass);// 您的邮箱密码
		mailInfo.setFromAddress(mailFrom);

		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getInstance(pro, authenticator);
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			String[] cto = mailInfo.getToAddress().split(";");
			Address[] mto = new Address[cto.length];
			for(int i=0;i<cto.length;i++){
				Address to = new InternetAddress(cto[i]);
				mto[i]=to;
			}
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipients(Message.RecipientType.TO, mto);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			
		return true;
	}
	
	/**
	 * @Title: parseDOM4J.
	 * @Description: TODO(解析XML).
	 * @return void    返回类型.
	 * @author CongZN.
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	private static void parseDOM4J(Document doc) throws UnsupportedEncodingException {
        Element root = doc.getRootElement();
        for (Iterator iter = root.elementIterator("systemConfig"); iter.hasNext();) {// 遍历line结点的所有子节点,也可以使用root.elementIterator()
            Element element = (Element) iter.next();
            
            shopName = element.elementText("shopName");
            mailServer = element.elementText("smtpHost");
            mailPort = element.elementText("smtpPort");
            mailUser = element.elementText("smtpUsername");
            mailPass = element.elementText("smtpPassword");
            mailFrom = MimeUtility.encodeWord(shopName, "UTF-8", "B") + " <" + element.elementText("smtpFromMail")  + ">";
        }
    }

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		SimpleMailSender.mailServer = mailServer;
	}

	public String getMailPort() {
		return mailPort;
	}

	public static void setMailPort(String mailPort) {
		SimpleMailSender.mailPort = mailPort;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		SimpleMailSender.mailUser = mailUser;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		SimpleMailSender.mailPass = mailPass;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		SimpleMailSender.mailFrom = mailFrom;
	}

}
