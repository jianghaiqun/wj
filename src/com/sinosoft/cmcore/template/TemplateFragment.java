package com.sinosoft.cmcore.template;

import com.sinosoft.framework.utility.Mapx;

public class TemplateFragment {
	/**
	 * 普通HTML
	 */
	public static final int FRAGMENT_HTML = 1;

	/**
	 * 标签
	 */
	public static final int FRAGMENT_TAG = 2;

	/**
	 * 占位符
	 */
	public static final int FRAGMENT_HOLDER = 3;

	/**
	 * 脚本
	 */
	public static final int FRAGMENT_SCRIPT = 4;

	public int Type;

	public String TagPrefix;// 只有类型为FRAGMENT_TAG有值

	public String TagName;// 只有类型为FRAGMENT_TAG有值

	public Mapx Attributes;// 只有类型为FRAGMENT_TAG有值

	public String FragmentText;

	public int StartLineNo;

	public int StartCharIndex;

	public int EndCharIndex;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (Type == FRAGMENT_HTML) {
			sb.append("HTML");
		}
		if (Type == FRAGMENT_TAG) {
			sb.append("TAG");
		}
		if (Type == FRAGMENT_SCRIPT) {
			sb.append("SCRIPT");
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("HOLDER");
		}
		sb.append(":");
		if (Type == FRAGMENT_TAG) {
			sb.append("<" + this.TagPrefix + ":" + this.TagName);
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("${");
		}
		if (FragmentText != null) {
			String str = FragmentText.replaceAll("[\\n\\r]+", "\\\\n");
			str = FragmentText.replaceAll("\\s+", " ");
			if (str.length() > 100) {
				str = str.substring(0, 100);
			}
			sb.append(str);
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("}");
		}
		return sb.toString();
	}
}
