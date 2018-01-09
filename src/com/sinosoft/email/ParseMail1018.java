package com.sinosoft.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 太平养老保险股份有限公司,邮件模版解析
 *
 */
public class ParseMail1018 extends ParseMail {

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
	public String dealPolicyno(String title, String attachName, String mailBody, String company) throws Exception {
		// 解析正文
		Pattern pattern = Pattern.compile("您的保单号([0-9]*)现已承保");
		Matcher matcher = pattern.matcher(mailBody);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
