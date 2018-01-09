package com.sinosoft.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 安盛天平保险,邮件模版解析
 *
 */
public class ParseMail2096 extends ParseMail {

	/**
	 * 获取保单号
	 * 
	 * <b>@param title 标题
	 * 
	 * <b>@param attachName 附件
	 * 
	 * <b>@param mailBody 正文
	 * 
	 * <b>@param company 保险公司
	 * 
	 * @return 保单号
	 */
	public String dealPolicyno(String title, String attachName, String mailBody, String company) {
		// 解析附件
		Pattern pattern = Pattern.compile("([0-9]*).*");
		Matcher matcher = pattern.matcher(attachName);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
