package com.sinosoft.cmcore.template;

public class TemplateConfig {
	private String Type;

	private String Name;

	private String Author;

	private String Version;

	private String Description;

	private String ScriptStart;

	private String ScriptEnd;

	public TemplateConfig(String type, String name, String author, String version, String description,
			String scriptStart, String scriptEnd) {
		this.Type = type;
		this.Name = name;
		this.Author = author;
		this.Version = version;
		this.Description = description;
		this.ScriptEnd = scriptEnd;
		this.ScriptStart = scriptStart;
	}

	/**
	 * 模权类型<br>
	 * 内容核心自带的类型只有一个:Block，即生成单独文件
	 * 其他类型必须有相应TemplateDataProvider支持，例如ArticleDetail,ArticleList
	 */
	public String getType() {
		return Type;
	}

	/**
	 * 获取模板名称
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 获取模板作者
	 */
	public String getAuthor() {
		return Author;
	}

	/**
	 * 获取模板版本
	 */
	public String getVersion() {
		return Version;
	}

	/**
	 * 获取模板描述
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * 模板中脚本开始标记
	 */
	public String getScriptStart() {
		return ScriptStart;
	}

	/**
	 * 模板中脚本结束标记
	 */
	public String getScriptEnd() {
		return ScriptEnd;
	}
}
