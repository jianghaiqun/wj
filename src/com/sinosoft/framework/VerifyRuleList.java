package com.sinosoft.framework;

import java.util.ArrayList;

/**
 * 校验规则的容器
 * 
 */
public class VerifyRuleList {
	private ArrayList array = new ArrayList();

	private String Message;

	private RequestImpl Request;

	private ResponseImpl Response;

	/**
	 * 增加一个校验字段及其校验规则
	 * 
	 * @param fieldID
	 * @param fieldName
	 * @param rule
	 */
	public void add(String fieldID, String fieldName, String rule) {
		array.add(new String[] { fieldID, fieldName, rule });
	}

	/**
	 * 校验所有字段
	 * 
	 * @return
	 */
	public boolean doVerify() {
		VerifyRule rule = new VerifyRule();
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.size(); i++) {
			String[] f = (String[]) array.get(i);
			rule.setRule(f[2]);
			if (!rule.verify(Request.getString(f[0]))) {
				sb.append(rule.getMessages(f[1]));
				flag = false;
			}
		}
		if (!flag) {
			Response.setStatus(0);
			Message = sb.toString();
			Response.setMessage(sb.toString());
		}
		return flag;
	}

	public String getMessage() {
		return Message;
	}

	public RequestImpl getRequest() {
		return Request;
	}

	public void setRequest(RequestImpl request) {
		Request = request;
	}

	public ResponseImpl getResponse() {
		return Response;
	}

	public void setResponse(ResponseImpl response) {
		Response = response;
	}
}
