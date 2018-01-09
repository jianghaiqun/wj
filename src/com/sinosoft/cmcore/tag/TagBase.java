package com.sinosoft.cmcore.tag;

import com.sinosoft.cmcore.template.TemplateBase;
import com.sinosoft.cmcore.template.TemplateContext;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/**
 */
public abstract class TagBase {
	public static final int SKIP = 1;

	public static final int CONTINUE = 2;

	public static final int END = 3;

	protected TemplateBase template;

	protected TemplateContext context;

	protected Mapx attributes = new Mapx();

	public abstract int onTagStart();// 此方法要负责判断是否需要输出内容，并负责往TemplateContext加入变量

	public abstract String[] getMandantoryAttributeNames();

	public abstract String[] getAllAttributeNames();

	public abstract int onTagEnd();

	public abstract boolean isIterative();

	public abstract boolean prepareNext();// 此方法负责判断是否还有下一次迭代，并且往TemplateContext加入循环变量

	public abstract String getPrefix();

	public abstract String getTagName();

	public boolean checkAttribute() {
		String[] mandantory = this.getMandantoryAttributeNames();
		if (mandantory == null) {
			return true;
		}
		for (int i = 0; i < mandantory.length; i++) {
			String v = attributes.getString(mandantory[i]);
			if (StringUtil.isEmpty(v)) {
				context.addError("标签" + this.getPrefix() + ":" + this.getTagName() + "的属性名" + mandantory[i] + "不能为空");
			}
		}
		return true;
	}

	public TemplateContext getContext() {
		return context;
	}

	public void setContext(TemplateContext context) {
		this.context = context;
	}

	public TemplateBase getTemplate() {
		return template;
	}

	public void setTemplate(TemplateBase template) {
		this.template = template;
	}

	public void setAttribute(String key, String value) {
		String[] all = this.getAllAttributeNames();
		if (all == null) {
			context.addError("标签" + this.getPrefix() + ":" + this.getTagName() + "不支持任何属性名!");
			return;
		}
		boolean flag = false;
		for (int i = 0; i < all.length; i++) {
			if (all[i].equalsIgnoreCase(key)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			context.addError("标签" + this.getPrefix() + ":" + this.getTagName() + "不支持属性名" + key);
		}
		attributes.put(key, value);
	}

	public String getAttribute(String key) {
		return attributes.getString(key);
	}

	public Mapx getAttributes() {
		return attributes;
	}

}
