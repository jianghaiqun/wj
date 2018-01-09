package com.wangjin.emar.cps;

import cn.com.sinosoft.util.CookieUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: LinkstarsTrackServlet <br/>
 * Function: LinkStars广告联盟 订单查询接口. <br/>
 * date: 2017年3月14日 上午8:40:42 <br/>
 *
 * @author wwy
 * @version 
 */
public class LinkstarsTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LinkstarsTrackServlet.class);

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response){
		try{ 
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String feedback = request.getParameter("feedback");
			String target = request.getParameter("to");
			String source = request.getParameter("source");
			
			//取得cookie中原有的channelSn
			Cookie ck = CookieUtil.getCookieByName(request, "channelSn");
			String oldChannelSn = "";
			if(!StringUtil.isEmpty(ck) && StringUtil.isNotEmpty(ck.getValue())){
				oldChannelSn = ck.getValue();
			} 
			if(oldChannelSn.indexOf("dlr")<0 && oldChannelSn.indexOf("swpt")<0 ){
			
			Cookie cookie_src = new Cookie("cpsUserSource", source);// 统一cps cookie key值  CPS用户ID
			cookie_src.setPath("/");
			cookie_src.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_src);
			//parter_uid
			Cookie cookie_uid = new Cookie("partners_uid", feedback);
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_uid);
			
			Cookie cookie_channel = new Cookie("channelSn", "cps_" + source);
			cookie_channel.setPath("/");
			cookie_channel.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_channel);
			//cpsUserId
			Cookie cookie_cpsUserId = new Cookie("cpsUserId", source);
			cookie_cpsUserId.setPath("/");
			cookie_cpsUserId.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_cpsUserId);
			}
			//url
			response.sendRedirect(target);
			return;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

}

