package com.wangjin.emar.cps;

import cn.com.sinosoft.util.CookieUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TrackServlet.class);

	/**
	 * 亿起发
	 */
	private static final long serialVersionUID = 1L; 

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String src = request.getParameter("src");
			String channel = request.getParameter("channel");
			String wi = request.getParameter("wi");
			String cid = request.getParameter("cid");
			String url = request.getParameter("url");

			//取得cookie中原有的channelSn
			Cookie ck = CookieUtil.getCookieByName(request, "channelSn");
			String oldChannelSn = "";
			if(!StringUtil.isEmpty(ck) && StringUtil.isNotEmpty(ck.getValue())){
				oldChannelSn = ck.getValue();
			}
			
			if(oldChannelSn.indexOf("dlr")<0 && oldChannelSn.indexOf("swpt")<0 ){
			
			//Cookie cookie_src = new Cookie("src", src);
			Cookie cookie_src = new Cookie("cpsUserSource", src);// 统一cps cookie key值
			cookie_src.setPath("/");
			cookie_src.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_src);
			
			Cookie cookie_channel = new Cookie("channel", channel);
			cookie_channel.setPath("/");
			cookie_channel.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_channel);

			Cookie cookie_wi = new Cookie("wi", wi);
			cookie_wi.setPath("/");
			cookie_wi.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_wi);

			//Cookie cookie_cid = new Cookie("cid_cps", cid);
			Cookie cookie_cid = new Cookie("cpsUserId", cid);// 统一cps cookie key值
			cookie_cid.setPath("/");
			cookie_cid.setMaxAge(30 * 24 * 3600);
			
			Cookie channelSn = new Cookie("channelSn", "cps_01");
			channelSn.setPath("/");
			channelSn.setMaxAge(30 * 24 * 3600);
			response.addCookie(channelSn);
			response.addCookie(cookie_cid);
			}
			response.sendRedirect(url);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
