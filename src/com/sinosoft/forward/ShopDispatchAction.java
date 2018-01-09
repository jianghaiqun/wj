package com.sinosoft.forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;


public class ShopDispatchAction extends ShopAction {
	protected static final Logger logger = LoggerFactory.getLogger(ShopDispatchAction.class);
	protected Class						clazz	= this.getClass();
	protected HashMap<String, Object>	methods	= new HashMap<String, Object>();
	protected Class[]					types	= { HttpServletRequest.class };

	public String execute(HttpServletRequest request) throws Exception {
		String methodName = request.getParameter("method");
		if (methodName == null || methodName.equals("")) {
			throw new Exception("No method in request");
		}
		Method method = getMethod(methodName);
		Object[] objects = new Object[] { request };
		return (String) method.invoke(this, objects);
	}

	protected Method getMethod(String name) throws NoSuchMethodException {
		synchronized (methods) {
			Method method = (Method) methods.get(name);
			if (method == null) {
				method = clazz.getMethod(name, types);
				methods.put(name, method);
			}
			return method;
		}
	}
 

}
