package com.sinosoft.forward;

import javax.servlet.http.HttpServletRequest;

public abstract class ShopAction {
	public abstract String execute(HttpServletRequest request) throws Exception;
}
