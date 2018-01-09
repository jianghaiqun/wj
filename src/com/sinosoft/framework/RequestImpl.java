package com.sinosoft.framework;

import com.sinosoft.framework.data.DataCollection;

/**
 * 对ServletRequest的封装
 * 
 */
public class RequestImpl extends DataCollection {
	private static final long serialVersionUID = 1L;

	private String URL;

	private String ClientIP;

	private String ClassName;

	private String ServerName;

	private int Port;

	private String Scheme;

	/**
	 * 返回当前请求URL中的域名
	 * 
	 * @return
	 */
	public String getServerName() {
		return ServerName;
	}

	/**
	 * 设置当前请求的域名，仅框架和测试代码需要调用本方法。
	 * 
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	/**
	 * 返回当前请求URL中的端口
	 * 
	 * @return
	 */
	public int getPort() {
		return Port;
	}

	/**
	 * 设置当前请求的端口，仅框架和测试代码需要调用本方法。
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		Port = port;
	}

	/**
	 * 返回当前请求中的协议，如http/https
	 * 
	 * @return
	 */
	public String getScheme() {
		return Scheme;
	}

	/**
	 * 设置当前请求的协议，仅框架和测试代码需要调用本方法。
	 * 
	 * @param scheme
	 */
	public void setScheme(String scheme) {
		Scheme = scheme;
	}

	/**
	 * 返回当前Page类的类名
	 * 
	 * @return
	 */
	public String getClassName() {
		return ClassName;
	}

	/**
	 * 设置当前请求的类名，仅框架和测试代码需要调用本方法。
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		ClassName = className;
	}

	/**
	 * 返回客户端IP地址
	 * 
	 * @return
	 */
	public String getClientIP() {
		return ClientIP;
	}

	/**
	 * 设置当前请求的客户端IP地址，仅框架和测试代码需要调用本方法。
	 * 
	 * @param clientIP
	 */
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	/**
	 * 返回当前请求的URL
	 * 
	 * @return
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * 设置当前请求的URL，仅框架和测试代码需要调用本方法。
	 * 
	 * @param url
	 */
	public void setURL(String url) {
		URL = url;
	}
}
