package com.wangjin.emar.cps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WoSouTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(WoSouTrackServlet.class);

	private static final long serialVersionUID = 1L;
	
	/**
	 * 拦截来自"我搜"推广联盟的访问，向Cookie中载入信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String participator = request.getParameter("participator");
			String siteId = request.getParameter("siteId");
			String url = request.getParameter("url");
			
			Cookie cookie_participator = new Cookie("participator", participator);
			cookie_participator.setPath("/");
			cookie_participator.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_participator);
			
			Cookie cookie_siteId = new Cookie("SiteId", siteId);
			cookie_siteId.setPath("/");
			cookie_siteId.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_siteId);
			
			response.sendRedirect(url);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
