package com.wangjin.emar.cps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FNMiaoTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(FNMiaoTrackServlet.class);

	private static final long serialVersionUID = 1L;
	
	/**
	 * 拦截来自59秒的访问，向Cookie中载入信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String fnMiao_source = request.getParameter("source");
			String fnMiao_uid = request.getParameter("uid");
			String url = request.getParameter("url");
			
			//Cookie cookie_fnMiao_source = new Cookie("fnMiao_source", fnMiao_source);
			Cookie cookie_fnMiao_source = new Cookie("cpsUserSource", fnMiao_source);// 统一cps cookie key值
			cookie_fnMiao_source.setPath("/");
			cookie_fnMiao_source.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_fnMiao_source);
			
			//Cookie cookie_fnMiao_uid = new Cookie("fnMiao_uid", fnMiao_uid);
			Cookie cookie_fnMiao_uid = new Cookie("cpsUserId", fnMiao_uid);// 统一cps cookie key值
			cookie_fnMiao_uid.setPath("/");
			cookie_fnMiao_uid.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_fnMiao_uid);
			
			response.sendRedirect(url);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
