package cn.com.sinosoft.service;

import java.util.Map;

import cn.com.sinosoft.entity.Member;


/**
 * Service接口 - 邮件服务
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT87B324D1BB3AC771F6FD0410911E18F1
 * ============================================================================
 */

public interface MailService {
	
	/**
	 * 检测邮件配置是否完成，发件人邮箱、SMTP服务器地址、SMTP服务器端口、SMTP用户名、SMTP密码 缺少其中任何一项则返回false
	 * 
	 */
	public boolean isMailConfigComplete();
	
	/**
	 * 根据主题、Freemarker模板文件路径、接收邮箱地址、Map数据发送邮件(异步发送)
	 * 
	 * @param subject
	 *            主题
	 * 
	 * @param templateFilePath
	 *            Freemarker模板文件路径
	 * 
	 * @param data
	 *            Map数据
	 *            
	 * @param toMail
	 *            收件人邮箱
	 * 
	 */
	public void sendMail(String subject, String templateFilePath, Map<String, Object> data, String toMail);
	
	/**
	 * 发送SMTP邮箱配置测试(同步发送)
	 * 
	 * @param smtpFromMail
	 *            发件人邮箱
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 *            
	 * @param smtpPort
	 *            SMTP服务器端口
	 *            
	 * @param smtpUsername
	 *            SMTP用户名
	 *            
	 * @param smtpPassword
	 *            SMTP密码
	 *            
	 * @param toMail
	 *            收件人邮箱
	 * 
	 */
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail);
	
	
	/**
	 * 发送订单邮件提醒
	 */
	public void sendSmtpSignMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail);
	
	/**
	 * 发送密码找回邮件
	 * 
	 * @param member
	 *            会员
	 * 
	 */
	public void sendPasswordRecoverMail(Member member);
	
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
	public void sendBatchMail(String[] to, String text, String title);
}
