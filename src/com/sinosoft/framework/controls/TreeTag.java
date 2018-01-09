package com.sinosoft.framework.controls;

import com.sinosoft.framework.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TreeTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(TreeTag.class);

	private static final long serialVersionUID = 1L;

	private String id;

	private String method;

	private String style;

	private boolean lazy;

	private boolean resizeable;

	private boolean expand; // 延迟加载时全部展开

	private int level;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.method = null;
		this.id = null;
		this.style = null;
		this.lazy = false;
		this.resizeable=false;
		this.expand = false;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = this.getBodyContent();
		String content = body.getString().trim();
		try {
			if (method == null || method.equals("")) {
				throw new RuntimeException("Tree既未指定Method");
			}

			TreeAction tree = new TreeAction();
			tree.setTagBody(content);
			tree.setMethod(method);

			HtmlP p = new HtmlP();
			p.parseHtml(content);
			tree.setTemplate(p);

			tree.setID(id);
			tree.setLazy(lazy);
			tree.setResizeable(resizeable);
			tree.setExpand(expand);
			if (level <= 0) {
				level = 999;
			}
			tree.setLevel(level);
			tree.setStyle(style);

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

			Current.init(request, response, method);
			tree.setParams(Current.getRequest());
			Current.invokeMethod(method, new Object[] { tree });

			getPreviousOut().write(tree.getHtml());
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		return EVAL_PAGE;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

}
