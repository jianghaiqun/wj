package com.sinosoft.framework;

import com.sinosoft.framework.data.DataCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个页面或模块中的所有后台方法的集合。<br>
 * 所有响应JavaScript中Server.sendRequest()方法的后台类都必须继承本类<br>
 * 
 */
public abstract class Page {
	/**
	 * 响应本次请求的数据容器，放在Response中的数据在JavaScript中可以用Response.get()获取到
	 */
	protected ResponseImpl Response = new ResponseImpl();

	/**
	 * 本次请求的所有参数，包括URL和表单参数，以及部分Http头
	 */
	protected RequestImpl Request;

	/**
	 * 本次请求发送的所有Cookie
	 */
	protected CookieImpl Cookie;

	/**
	 * 日志对象
	 */
	protected  static final Logger logger = LoggerFactory.getLogger(Page.class);

	/**
	 * 设置请求参数对象，初始化之后立即会执行
	 */
	public void setRequest(RequestImpl r) {
		Request = r;
	}

	/**
	 * 获取参数，类似于JavaScript中的$V，相当于Request.getString()
	 */
	public String $V(String id) {
		return Request.getString(id);
	}

	/**
	 * 设置参数，类似于JavaScript中的$S，相当于Response.put()
	 */
	public void $S(String id, Object value) {
		Response.put(id, value);
	}

	/**
	 * 获取当前响应数据
	 */
	public DataCollection getResponse() {
		return Response;
	}

	/**
	 * 获取Cookie数据
	 */
	public CookieImpl getCookie() {
		return Cookie;
	}

	/**
	 * 设置Cookie数据
	 */
	public void setCookie(CookieImpl c) {
		this.Cookie = c;
	}

	/**
	 * 重定向到指定URL
	 */
	public void redirect(String url) {
		Response.put(Constant.ResponseScriptAttr, "window.location=\"" + url + "\";");
	}
}
