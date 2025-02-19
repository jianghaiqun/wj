package com.sinosoft.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * 对Servlet中的Cookie对象的封装，可以在Page类中获取
 * 
 */
public class CookieImpl {
	private static final Logger logger = LoggerFactory.getLogger(CookieImpl.class);

	public ArrayList list = new ArrayList();

	public static final String CookieCharset = "UTF-8";

	/**
	 * 构造一个空的Cookie对象
	 */
	public CookieImpl() {

	}

	/**
	 * 从ServletRequest中建立一个Cookie对象
	 * 
	 * @param request
	 */
	public CookieImpl(ServletRequest request) {
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		if (cookies == null) {
			return;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			CookieObject c = new CookieObject();
			c.name = cookie.getName();
			try {
				c.value = URLDecoder.decode(cookie.getValue(), CookieCharset);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			c.domain = cookie.getDomain();
			c.maxAge = cookie.getMaxAge();
			c.path = cookie.getPath();
			c.path = normalizePath(c.path);
			c.secure = cookie.getSecure();
			c.comment = cookie.getComment();
			c.version = cookie.getVersion();
			list.add(c);
		}
	}

	/**
	 * 设置Cookie的值
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param maxAge
	 * @param path
	 * @param secure
	 * @param comment
	 */
	public void setCookie(String name, String value, String domain, int maxAge, String path, boolean secure,
			String comment) {
		path = normalizePath(path);
		for (int i = 0; i < list.size(); i++) {
			CookieObject c = (CookieObject) list.get(i);
			if (c.name.equals(name) && (c.domain == null || c.domain.equals(domain))
					&& (c.path == null || c.path.equals(path)) && c.secure == secure) {
				if (c.value != null && !c.value.equals(value)) {
					c.changed = true;
					c.value = value;
					c.comment = comment;
					return;
				}
			}
		}
		CookieObject c = new CookieObject();
		c.name = name;
		c.value = value;
		c.comment = comment;
		c.domain = domain;
		c.maxAge = maxAge;
		c.path = path;
		c.secure = secure;
		c.version = 0;
		c.changed = true;
		list.add(c);
	}

	/**
	 * 获取所有的Cookie名值对
	 * 
	 * @return
	 */
	public CookieObject[] getArray() {
		CookieObject[] cos = new CookieObject[list.size()];
		for (int i = 0; i < cos.length; i++) {
			cos[i] = (CookieObject) list.get(i);
		}
		return cos;
	}

	/**
	 * 设置Cookie的值
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public void setCookie(String name, String value, int maxAge) {
		setCookie(name, value, null, maxAge, getDefaultPath(), false, null);
	}

	/**
	 * 设置Cookie的值
	 * 
	 * @param name
	 * @param value
	 */
	public void setCookie(String name, String value) {
		setCookie(name, value, null, 60 * 60 * 24 * 36500, getDefaultPath(), false, null);
	}

	/**
	 * 设置Cookie的值
	 * 
	 * @param name
	 * @param value
	 * @param path
	 */
	public void setCookie(String name, String value, String path) {
		setCookie(name, value, path, 60 * 60 * 24 * 36500, getDefaultPath(), false, null);
	}

	/**
	 * 设置Cookie的值
	 * 
	 * @param name
	 * @param value
	 * @param path
	 * @param maxAge
	 */
	public void setCookie(String name, String value, String path, int maxAge) {
		setCookie(name, value, path, maxAge, getDefaultPath(), false, null);
	}

	/**
	 * 获取Cookie的值
	 * 
	 * @param name
	 * @return
	 */
	public String getCookie(String name) {
		return getCookie(name, null);
	}

	/**
	 * 获取Cookie默认路径
	 * 
	 * @return
	 */
	private static String getDefaultPath() {
		String path = Config.getContextPath();
		return normalizePath(path);
	}

	/**
	 * 规范Cookie中的path
	 * 
	 * @param path
	 * @return
	 */
	private static String normalizePath(String path) {
		if (path == null) {
			path = "/";
		}
		if (path.endsWith("/") && !path.equals("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	/**
	 * 获取Cookie的值
	 * 
	 * @param name
	 * @param path
	 * @return
	 */
	public String getCookie(String name, String path) {
		path = normalizePath(path);
		ArrayList arr = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			CookieObject c = (CookieObject) list.get(i);
			if (c.name.equals(name)) {
				if (path != null) {
					if (c.path.equals(path)) {
						arr.add(c.value);
					}
				} else {
					arr.add(c.value);
				}
			}
		}
		if (arr.size() == 0) {
			return null;
		}
		if (arr.size() == 1) {
			return arr.get(0) == null ? null : String.valueOf(arr.get(0));
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			if (arr.get(i) == null) {
				sb.append("");
			} else {
				sb.append(arr.get(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 将Cookie中的值写入到ServletResponse
	 * 
	 * @param request
	 * @param response
	 */
	public void writeToResponse(HttpServletRequest request, HttpServletResponse response) {
		try {
			for (int j = 0; j < list.size(); j++) {
				CookieObject co = (CookieObject) list.get(j);
				if (!co.changed) {
					continue;
				}
				Cookie[] cs = request.getCookies();
				boolean flag = true;
				for (int i = 0; i < cs.length; i++) {
					String path = normalizePath(cs[i].getPath());
					if (cs[i].getName().equals(co.name) && (path == null || path.equals(co.path))) {
						cs[i].setValue(URLEncoder.encode(co.value, CookieCharset));
						cs[i].setMaxAge(co.maxAge);
						if (co.domain != null) {
							cs[i].setDomain(co.domain);
						}
						response.addCookie(cs[i]);
						flag = false;
						break;
					}
				}
				if (flag) {
					Cookie cookie = new Cookie(co.name, URLEncoder.encode(co.value, CookieCharset));
					cookie.setMaxAge(co.maxAge);
					cookie.setPath(co.path);
					if (co.domain != null) {
						cookie.setDomain(co.domain);
					}
					cookie.setSecure(co.secure);
					response.addCookie(cookie);
				}
			}

		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 表示一个Cookie名值对
	 * 
	 */
	public static class CookieObject {
		/**
		 * Cookie名称
		 */
		public String name;

		/**
		 * Cookie值
		 */
		public String value;

		/**
		 * Cookie备注
		 */
		public String comment;

		/**
		 * Cookie所在域
		 */
		public String domain;

		/**
		 * Cookie存活时长
		 */
		public int maxAge = -1;

		/**
		 * Cookie路径
		 */
		public String path;

		/**
		 * 是不是SSL下的Cookie
		 */
		public boolean secure;

		/**
		 * Cookie版本
		 */
		public int version = 0;

		/**
		 * 是否修改过
		 */
		public boolean changed = false;
	}
}
