/**
 * Project Name:wj
 * File Name:MemberCookieUtil.java
 * Package Name:cn.com.sinosoft.action.shop
 * Date:2015年12月29日下午7:41:44
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.action.shop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sinosoft.entity.Member;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDMemberLoginRecordSchema;
import com.sinosoft.schema.memberSchema;

/**
 * ClassName:MemberCookieUtil <br/>
 * Function:TODO 会员跨域session问题处理. <br/>
 * Date:2015年12月29日 下午7:41:44 <br/>
 *
 * @author:郭斌
 */
public class MemberCookieUtil {

	final static String SSOID = "SSOID";
	final static String DOMAIN = Config.getValue("ShareSessionDomain");

	/**
	 * addCookie:(添加cookie). <br/>
	 *
	 * @author 郭斌
	 * @param request
	 * @param response
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String memberid, String loginName) {

		String session = request.getSession().getId();

		// 添加cookie至数据库
		SDMemberLoginRecordSchema tLoginRecord = new SDMemberLoginRecordSchema();
		tLoginRecord.setSSOID(session);
		if (!tLoginRecord.fill()) {

			// 添加cookie至浏览器
			Cookie c = new Cookie(SSOID, session);
			c.setDomain(DOMAIN);
			c.setPath("/");
			response.addCookie(c);

			tLoginRecord.setMemberID(memberid);
			tLoginRecord.setLastOprTime(DateUtil.getCurrentDateTime());
			tLoginRecord.insert();

		} else {
			tLoginRecord.setLastOprTime(DateUtil.getCurrentDateTime());
			tLoginRecord.update();

		}

		setUserToSession(request, tLoginRecord.getMemberID());

	}

	/**
	 * delCookie:(添加delCookie). <br/>
	 *
	 * @author 郭斌
	 * @param request
	 * @param response
	 */
	public static void delCookie(HttpServletRequest request, String memberid) {

		String ssoid = getSSOID(request);
		QueryBuilder qb = new QueryBuilder("delete from SDMemberLoginRecord where memberid=? or SSOID=? ", memberid, ssoid);
		qb.executeNoQuery();

	}

	/**
	 * 
	 * searchMember:(登录id). <br/>
	 *
	 * @author 郭斌
	 * @param request
	 * @param response
	 * @return
	 */
	public static String searchMember(HttpServletRequest request, HttpServletResponse response) {

		String ssoid = getSSOID(request);

		SDMemberLoginRecordSchema tLoginRecord = new SDMemberLoginRecordSchema();
		tLoginRecord.setSSOID(ssoid);
		if (tLoginRecord.fill()) {

			setUserToSession(request, tLoginRecord.getMemberID());

			return tLoginRecord.getMemberID();

		}

		return null;
	}

	/**
	 * 
	 * checkMember:(检测用户). <br/>
	 *
	 * @author 郭斌
	 * @param request
	 * @param response
	 * @return
	 */
	public static void checkMember(HttpServletRequest request, String memberid) {

		String ssoid = getSSOID(request);

		QueryBuilder qb = new QueryBuilder("update SDMemberLoginRecord set LastOprTime=now() where memberid=? or SSOID=? ",
				memberid, ssoid);

		qb.executeNoQuery();

		setUserToSession(request, memberid);
	}

	/**
	 * 
	 * setUserToSession:(设置用户到session内). <br/>
	 * 
	 * @author 郭斌
	 * @param request
	 * @param memberid
	 */
	private static void setUserToSession(HttpServletRequest request, String memberid) {

		Object loginName = request.getSession().getAttribute("loginName");

		if (StringUtil.isEmpty(loginName)) {
			memberSchema member = new memberSchema();
			member.setid(memberid);

			if (member.fill()) {
				String username = member.getusername();
				if (StringUtil.isEmpty(username)) {
					username = member.getemail();
 
					if (StringUtil.isEmpty(username)) {
						username = member.getmobileNO();

					}
				}

				request.getSession().setAttribute("loginName", username);

			}
		}

		Object member_id = request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(member_id)) {
			request.getSession().setAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME, memberid);
		}

	}

	/**
	 * 
	 * getSSOID:(获取用户cookie). <br/>
	 *
	 * @author 郭斌
	 * @param request
	 * @return
	 */
	private static String getSSOID(HttpServletRequest request) {

		Cookie cookies[] = request.getCookies(); // 读出用户硬盘上的Cookie，并将所有的Cookie放到一个cookie对象数组里面
		for (int i = 0; cookies != null && i < cookies.length; i++) { // 用一个循环语句遍历刚才建立的Cookie对象数组
			if (cookies[i] != null && SSOID.equals(cookies[i].getName())) {

				return cookies[i].getValue();
			}
		}

		return "";
	}

}
