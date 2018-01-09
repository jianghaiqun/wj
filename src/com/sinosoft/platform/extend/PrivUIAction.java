package com.sinosoft.platform.extend;

import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.JSPExtendAction;
import com.sinosoft.framework.extend.actions.JSPContext;

public class PrivUIAction extends JSPExtendAction {
	public void execute(JSPContext context) throws ExtendException {
		String Type = context.getRequest().getString("Type");
		String UserName = context.getRequest().getString("UserName");
		String RoleCode = context.getRequest().getString("RoleCode");
		if ("Role".equals(Type))
			context.include("Platform/MenuPrivExtend.jsp?Type=Role&RoleCode=" + RoleCode);
		else
			context.include("Platform/MenuPrivExtend.jsp?Type=User&UserName=" + UserName);
	}

	@Override
	public String execute(RequestImpl request, CookieImpl cookie) {
		JSPContext jsp = new JSPContext(request);
		try {
			execute(jsp);
		} catch (ExtendException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}