package com.sinosoft.framework.controls;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DataListTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(DataListTag.class);

	private static final long serialVersionUID = 1L;

	private String method;

	private String id;

	private int size;

	private boolean page;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.method = null;
		this.id = null;
		this.page = true;
		this.size = 0;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = this.getBodyContent();
		String content = body.getString().trim();
		try {
			if (StringUtil.isEmpty(method)) {
				throw new RuntimeException("DataList未指定Method");
			}

			DataListAction dla = new DataListAction();
			dla.setTagBody(content);
			dla.setPage(this.page);
			dla.setMethod(method);

			dla.setID(id);
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
			dla.setPageSize(size);

			if (page) {
				dla.setPageIndex(0);
				if (StringUtil.isNotEmpty(dla.getParam(Constant.DataGridPageIndex))) {
					dla.setPageIndex(Integer.parseInt(dla.getParam((Constant.DataGridPageIndex))));
				}
				if (dla.getPageIndex() < 0) {
					dla.setPageIndex(0);
				}
				dla.setPageSize(size);
			}

			Current.init(request, response, method);
			dla.setParams(Current.getRequest());
			Current.invokeMethod(method, new Object[] { dla });

			pageContext.setAttribute(id + Constant.DataGridPageTotal, "" + dla.getTotal());
			pageContext.setAttribute(id + Constant.DataGridPageIndex, "" + dla.getPageIndex());
			pageContext.setAttribute(id + Constant.Size, "" + dla.getPageSize());

			getPreviousOut().write(dla.getHtml());
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		return EVAL_PAGE;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
		/* ${_ZVING_LICENSE_CODE_} */
	}
}
