/**
 * Project Name:wj
 * File Name:PanSTrackServlet.java
 * Package Name:com.wangjin.emar.cps
 * Date:2016年12月23日下午5:40:27
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

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
 * ClassName:PanSTrackServlet <br/>
 * Function:盘石保存cookie方法<br/>
 * Date:2016年12月23日 下午5:40:27 <br/>
 * cookie
 * @author:wangtz
 */
public class PanSTrackServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(PanSTrackServlet.class);

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response){
		try{ 
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String um_id = request.getParameter("um_id");
			String track_code = request.getParameter("track_code");
			String target = request.getParameter("target");
			String src = request.getParameter("src");
			
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
			//parter_uid
			Cookie cookie_uid = new Cookie("partners_uid", track_code);
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_uid);
			
			Cookie cookie_channel = new Cookie("channelSn", "cps_"+um_id);
			cookie_channel.setPath("/");
			cookie_channel.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie_channel);
			//cpsUserId
			Cookie cookie_cpsUserId = new Cookie("cpsUserId", um_id);
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

