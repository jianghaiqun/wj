package com.sinosoft.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中国人民人寿保险,邮件模版解析
 *
 */
public class ParseMail1004 extends ParseMail {

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
		// 中国人民人寿，解析邮件标题即可获取保单号

		// 中国人民人寿保险有限公司电子保单-（000015460732158）
		// Pattern pattern = Pattern.compile("（([0-9a-zA-Z]*)）");
		Pattern pattern = Pattern.compile("（([0-9]*)）");
		Matcher matcher = pattern.matcher(title);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

 

}
