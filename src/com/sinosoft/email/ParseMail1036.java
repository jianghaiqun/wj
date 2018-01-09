package com.sinosoft.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 瑞泰人寿,邮件模版解析
 *
 */
public class ParseMail1036 extends ParseMail {

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
		Pattern pattern = Pattern.compile("电子保单-([A-Z]*[0-9]*)");
		Matcher matcher = pattern.matcher(title);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
