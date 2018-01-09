package com.sinosoft.framework.extend.actions;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sinosoft.framework.extend.ExtendManager;

public class ExtendTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int doStartTag() throws JspException {
		ExtendManager.invoke(this.id, new Object[] { this.pageContext });
		return 0;
	}
}