package com.wangjin.emar.cps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InswindowTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(InswindowTrackServlet.class);

	private static final long serialVersionUID = 1L;
	
	/**
	 * 拦截来自推广联盟的访问，向Cookie中载入信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String inswindow_src = request.getParameter("inswindow_src");
			String inswindow_channel = request.getParameter("inswindow_channel");
			String url = request.getParameter("url");
			
			Cookie cookie_inswindow_src = new Cookie("inswindow_src", inswindow_src);
			cookie_inswindow_src.setPath("/");
			cookie_inswindow_src.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_inswindow_src);
			
			Cookie cookie_inswindow_channel = new Cookie("inswindow_channel", inswindow_channel);
			cookie_inswindow_channel.setPath("/");
			cookie_inswindow_channel.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_inswindow_channel);
			
			response.sendRedirect(url);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
