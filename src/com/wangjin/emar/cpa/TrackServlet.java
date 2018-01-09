package com.wangjin.emar.cpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TrackServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			//String domain = "223.4.235.43";
			String src = request.getParameter("src");
			String wi = request.getParameter("wi");
			String cid = request.getParameter("cid");

			Cookie cookie_src = new Cookie("src", src);
			//cookie_src.setDomain(domain);
			cookie_src.setPath("/");
			cookie_src.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_src);

			Cookie cookie_wi = new Cookie("wi", wi);
			//cookie_wi.setDomain(domain);
			cookie_wi.setPath("/");
			cookie_wi.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_wi);

			Cookie cookie_cid = new Cookie("cid_cpa", cid);
			//cookie_cid.setDomain(domain);
			cookie_cid.setPath("/");
			cookie_cid.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_cid);
			response.sendRedirect("/wj/shop/member_register!index.action");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
