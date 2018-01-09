package com.sinosoft.platform.extend;

import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.JSPExtendAction;
import com.sinosoft.framework.extend.actions.JSPContext;

public class UserAddUIAction extends JSPExtendAction {
	public void execute(JSPContext context) throws ExtendException {
		context.write("啊撒就打算的付款就哈会计师的回复可见阿萨德离开房间");
	}

	@Override
	public String execute(RequestImpl request, CookieImpl cookie) {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getTarget() {
		return null;
	}
}