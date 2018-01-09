package com.sinosoft.framework.extend.actions;

import java.util.ArrayList;

import com.sinosoft.framework.RequestImpl;

public class JSPContext {
	private StringBuilder sb = new StringBuilder();
	private ArrayList<String> includes = new ArrayList<String>();
	private RequestImpl request = null;

	public JSPContext(RequestImpl request) {
		this.request = request;
	}

	public RequestImpl getRequest() {
		return this.request;
	}

	protected String getOut() {
		return this.sb.toString();
	}

	protected ArrayList<String> getIncludes() {
		return this.includes;
	}

	public void write(Object obj) {
		this.sb.append(obj);
	}

	public void include(String file) {
		this.includes.add(file);
	}
}