package com.sinosoft.framework;

import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.extend.AfterPageMethodInvokeAction;
import com.sinosoft.framework.extend.BeforePageMethodInvokeAction;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 所有JavaScript中的Server.sendRequest()请求都将发送到本Servlet。<br>
 * 本Servlet将根据ServletRequest初始化相关对象，并通过反射执行指定方法。<br>
 * 未登录的用户调用继承Page的类时，本Servlet将会重定向到指定的登录页面。
 * 
 */
public class MainServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setHeader("Pragma", "No-Cache");
			response.setHeader("Cache-Control", "No-Cache");
			response.setDateHeader("Expires", 0);

			response.setContentType("text/xml");

			if (Config.ServletMajorVersion == 2 && Config.ServletMinorVersion == 3) {
				response.setContentType("text/xml;charset=utf-8");
			} else {
				response.setCharacterEncoding("UTF-8");// 从js中传入的编码默认是utf-8
			}
			request.setCharacterEncoding("UTF-8");

			String method = request.getParameter(Constant.Method);
			String url = request.getParameter(Constant.URL);
			if ("".equals(url) || "/".equals(url)) {
				url = "/Index.jsp";
			}

			if ("www.zving.com".equalsIgnoreCase(request.getServerName())
					&& "/demo".equalsIgnoreCase(request.getContextPath())) {
				if (!"admin".equalsIgnoreCase(User.getUserName())
						&& getServletConfig().getInitParameter(method) != null) {
					logger.warn(
							"method:{},操作：{}此操作被拒绝!<br>系统提示：为保证全维智码软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.",
							method, getServletConfig().getInitParameter(method));
					DataCollection dcResponse = new DataCollection();
					dcResponse.put(Constant.ResponseStatusAttrName, "0");
					dcResponse
							.put(
									Constant.ResponseMessageAttrName,
									"此操作被拒绝!<br>系统提示：为保证全维智码软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.如需要可下载安装程序到本地来试用.<br>下载地址：<a href='http://www.zving.com/download/program/index.shtml' target='_blank'>下载ZCMS</a>");
					response.getWriter().write(dcResponse.toXML());
					return;
				}
			}

			Current.init(request, response, method);

			if (StringUtil.isEmpty(method)) {
				logger.error("错误的Server.sendRequest()调用，URL={}，Referer={}", url, request.getHeader("referer"));
				return;
			}
			String className = method.substring(0, method.lastIndexOf("."));
			Class c = Class.forName(className);

			String LoginClass = Config.getValue("App.LoginClass");
			// 未登录的用户重定向
			if (!Ajax.class.isAssignableFrom(c) && !className.equals("com.sinosoft.framework.Framework")
					&& !className.equals(LoginClass) && !User.isLogin()) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put(Constant.ResponseScriptAttr, "window.top.location='" + Config.getContextPath()
						+ Config.getLoginPage() + "';");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if (!className.equals(LoginClass) && !SessionCheck.check(c)) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put(Constant.ResponseMessageAttrName, "不允许越权访问!");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if (ExtendManager.hasAction(BeforePageMethodInvokeAction.Type)) {
				ExtendManager.executeAll(BeforePageMethodInvokeAction.Type, new Object[] { method });
			}

			Current.invokeMethod(method, (Object[]) null);

			if (ExtendManager.hasAction(AfterPageMethodInvokeAction.Type)) {
				ExtendManager.executeAll(AfterPageMethodInvokeAction.Type, new Object[] { method });
			}

			response.getWriter().write(Current.getResponse().toXML());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
