package com.sinosoft.cmcore.template;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 所有模板类的基类，各属性值是编译时从解析得到的TemplateConfig实例而来
 * 
 */
public abstract class TemplateBase {
	protected TemplateContext context;

	private StringWriter sw = new StringWriter();

	protected PrintWriter out = new PrintWriter(sw);

	public PrintWriter getWriter() {
		return out;
	}

	public String getResult() {
		return sw.toString();
	}

	public TemplateContext getContext() {
		return context;
	}

	public void setContext(TemplateContext context) {
		this.context = context;
	}

	/**
	 * 返回相对于应用根目录的模板文件路径
	 */
	public abstract String getTemplateFilePath();

	public abstract void execute() throws Exception;

	public abstract TemplateConfig getConfig();
}
