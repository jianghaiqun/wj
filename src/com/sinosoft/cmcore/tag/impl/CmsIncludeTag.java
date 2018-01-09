package com.sinosoft.cmcore.tag.impl;

import com.sinosoft.cmcore.tag.SimpleTag;
import com.sinosoft.cmcore.tag.TagBase;
import com.sinosoft.cmcore.template.TemplateBase;
import com.sinosoft.cmcore.template.TemplateContext;
import com.sinosoft.cmcore.template.TemplateManager;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmsIncludeTag extends SimpleTag {
	private static final Logger logger = LoggerFactory.getLogger(CmsIncludeTag.class);

	public String[] getAllAttributeNames() {
		return new String[] { "file", "type" };
	}

	public String[] getMandantoryAttributeNames() {
		return new String[] { "file" };
	}

	public int onTagStart() {
		String path = this.template.getTemplateFilePath();
		path = path.substring(0, path.indexOf("/template/"));
		String file = this.attributes.getString("file");
		// 计算文件相对位置并读取之
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		while (file.startsWith(".")) {
			if (file.startsWith("./")) {
				file = file.substring(2);
			} else if (file.startsWith("../")) {
				file = file.substring(3);
				if (path.lastIndexOf("/") < 0) {
					this.context.addError("文件包含路径不正确:" + this.attributes.getString("file"));
				}
				path = path.substring(0, path.lastIndexOf("/"));
			} else {
				break;
			}
		}
		file = path + "/" + file;
		String type = attributes.getString("type");
		if (context.isSSIContext()) {
			int level = 0;
			if ("template".equals(type)) {
				// 先检查文件是否存在，不存在则生成之
				TemplateBase tpl = TemplateManager.findTemplate(file);
				TemplateContext ctx = new TemplateContext();
				tpl.setContext(ctx);
				try {
					tpl.execute();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			checkIncludeFileExists(file, 0);
			file = this.attributes.getString("file");// 需要使用原始路径
			file = file.substring(0, file.lastIndexOf(".")) + "_" + level + "."
					+ file.substring(file.lastIndexOf(".") + 1);
			template.getWriter().print("<!--#include virtual=\"" + file + "\"-->");
		} else {
			if ("template".equals(type)) {
				TemplateBase tpl = TemplateManager.findTemplate(file);
				tpl.setContext(context);
				try {
					tpl.execute();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				String html = TemplateManager.getFileText(file);
				template.getWriter().print(html);
			}
		}
		return TagBase.CONTINUE;
	}

	/**
	 * 检查文件是否存在，如果没有，则
	 */
	private static void checkIncludeFileExists(String file, int level) {

	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "include";
	}

	public boolean checkAttribute() {
		if (!super.checkAttribute()) {
			return false;
		}
		String type = attributes.getString("type");
		if (StringUtil.isNotEmpty(type)) {
			if (type.equalsIgnoreCase("html") || type.equalsIgnoreCase("template")) {
				return true;
			} else {
				context.addError(this.getPrefix() + ":" + this.getTagName() + "的type属性值不正确:" + type);
			}
		}
		return true;
	}

}
