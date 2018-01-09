package com.sinosoft.framework.extend.actions;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.framework.extend.ExtendException;
import com.sinosoft.framework.extend.plugin.IExtendAction;

public abstract class BeforeSSIFilterAction implements IExtendAction {
	public static final String ExtendPointID = "com.sinosoft.framework.BeforeSSIFilter";

	public Object execute(Object[] args) throws ExtendException {
		HttpServletRequest request = (HttpServletRequest) args[0];
		HttpServletResponse response = (HttpServletResponse) args[1];
		FilterChain chain = (FilterChain) args[2];
		execute(request, response, chain);
		return null;
	}

	public abstract void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, FilterChain paramFilterChain) throws ExtendException;
}