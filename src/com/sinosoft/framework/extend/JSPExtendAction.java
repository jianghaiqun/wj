package com.sinosoft.framework.extend;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.RequestImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public abstract class JSPExtendAction extends Ajax implements IExtendAction {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		PageContext pageContext = (PageContext) args[0];
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		Current.init(request, response, this.getClass().getName() + ".execute");
		RequestImpl requestImpl = Current.getRequest();
		CookieImpl cookie = Current.getPage().getCookie();
		String html = execute(requestImpl, cookie);
		try {
			pageContext.getOut().print(html);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public abstract String execute(RequestImpl request, CookieImpl cookie);
}
