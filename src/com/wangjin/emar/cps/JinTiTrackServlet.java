package com.wangjin.emar.cps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JinTiTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(JinTiTrackServlet.class);

	private static final long serialVersionUID = 1L;
	
	/**
	 * 拦截来自今题网的访问，向Cookie中载入信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String jinTi_src = request.getParameter("src");
			String jinTi_extcode = request.getParameter("extcode");
			String url = request.getParameter("reurl");
			
			Cookie cookie_jinTi_src = new Cookie("jinTi_src", jinTi_src);
			cookie_jinTi_src.setPath("/");
			cookie_jinTi_src.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_jinTi_src);
			
			Cookie cookie_jinTi_extcode = new Cookie("jinTi_extcode", jinTi_extcode);
			cookie_jinTi_extcode.setPath("/");
			cookie_jinTi_extcode.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_jinTi_extcode);
			
			response.sendRedirect(url);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
