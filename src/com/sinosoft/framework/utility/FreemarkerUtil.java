/**
 * Project Name:Automobile
 * File Name:FreemarkerAnalytical.java
 * Package Name:com.finance.util
 * Date:2015年8月27日下午5:37:49
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.framework.utility;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * ClassName:FreemarkerAnalytical <br/>
 * Function:解析freemarker模板为字符串. <br/>
 * Date:2015年8月27日 下午5:37:49 <br/>
 *
 * @author:zhangjing
 */
public class FreemarkerUtil {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
	/**
	 * 
	 * templateManage:(传入参数，解析ftl模板). <br/>
	 * 
	 * @author zhangjing
	 * @param templatename
	 *            ftl模板名称
	 * @param map
	 *            参数集合
	 * @return 由模板生成的字符串
	 */
	public static String templateManage(String filepath, String templatename, Map<String, Object> map) {

		if (StringUtil.isEmpty(templatename)) {
			return "";
		}

		Configuration cfg = new Configuration();
		BufferedWriter writer = null;
		try {
			// 定义模版的位置，从类路径中，相对于FreemarkerAnalytical所在的路径加载模版
			cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/");
			// 设置对象包装器
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDirectoryForTemplateLoading(new File(filepath));

			// 设置异常处理器
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

			// 通过freemarker解释模板，首先需要获得Template对象
			Template template = cfg.getTemplate(templatename + ".ftl", "UTF-8");
			StringWriter stringWriter = new StringWriter();
			writer = new BufferedWriter(stringWriter);
			template.setEncoding("UTF-8");
			// 解析模板
			template.process(map, writer);
			String result = stringWriter.toString();

			writer.flush();
			return result;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";

		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

}
