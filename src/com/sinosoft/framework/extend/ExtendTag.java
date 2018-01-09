package com.sinosoft.framework.extend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ExtendTag extends TagSupport {
	private static final Logger logger = LoggerFactory.getLogger(ExtendTag.class);

	private static final long serialVersionUID = 1L;

	private String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int doStartTag() throws JspException {
		try {
			if (ExtendManager.hasAction(target)) {
				IExtendAction actions[] = ExtendManager.find(target);
				for (int i = 0; i < actions.length; i++) {
					if (!(actions[i] instanceof JSPExtendAction)) {
						logger.warn("类{}必须继承JSPExtendAction!",  actions[i].getClass().getName());
						continue;
					}
					actions[i].execute(new Object[] { pageContext });
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SKIP_BODY;
	}
}
