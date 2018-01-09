package com.sinosoft.framework.extend;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BeforeSSIFilterAction implements IExtendAction {
	public static final String Type = "BeforeSSIFilter";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		HttpServletRequest request = (HttpServletRequest) args[0];
		HttpServletResponse response = (HttpServletResponse) args[1];
		FilterChain chain = (FilterChain) args[2];
		execute(request, response, chain);
	}

	public abstract void execute(HttpServletRequest request, HttpServletResponse response, FilterChain chain);
}
