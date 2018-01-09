/**
 * Project Name:b2b-code
 * File Name:MessageConstant.java
 * Package Name:com.finance.util
 * Date:2017年4月24日下午1:56:57
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.common.email;
/**
 * ClassName:MessageConstant <br/>
 * Function:TODO 发送邮件或短信消息常量定义. <br/>
 * Date:2017年4月24日 下午1:56:57 <br/>
 * @author:guozc
 */
public class MessageConstant {

	/**
	 * 短信参数-手机号码
	 */
	public static final String PARAM_MOBILE_NAME = "mobile";
	
	/**
	 * 短信参数-短信模板内容
	 */
	public static final String PARAM_SENDDATA_NAME = "sendData";
	
	public static final String PARAM_ACTIONID_NAME = "actionId";
	
	public static final String PARAM_SUBACTIONID_NAME = "subActionId";
	
	/**
	 * 邮件参数-接收地址
	 */
	public static final String PARAM_RECEIVER_NAME = "receiver";
	
	/**
	 * 邮件参数-抄送地址
	 */
	public static final String PARAM_CC_NAME = "ccAddress";
	
	/**
	 * 邮件参数-邮件主题
	 */
	public static final String PARAM_SUBJECT_NAME = "subject";
	
	/**
	 * 邮件参数-邮件内容
	 */
	public static final String PARAM_CONTENT_NAME = "content";
	
	/**
	 * 邮件参数-邮件附件
	 */
	public static final String PARAM_ATTACHMENTS = "attachments";
	
	/**
	 * 邮件参数-邮件中是否显示推荐产品和活动信息
	 */
	public static final String PARAM_SHOW_ACTIVITY_PRODUCT = "showActivityAndProduct";
	
	public static final String PARAM_SHOW_MEMBER = "showMember";
	
	public static final String PARAM_FREEMARKER_DATA = "freemarkerData";
	
}

