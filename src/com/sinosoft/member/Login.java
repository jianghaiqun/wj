package com.sinosoft.member;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;

public class Login extends Ajax {
	private static ArrayList wrongList = new ArrayList();

	public static Mapx initSiteLinks(Mapx params) {
		String SiteID = params.getString("SiteID");
		String UserName = User.getUserName();
		String SiteLinks = "";
		if (StringUtil.isEmpty(SiteID)) {
			if (StringUtil.isEmpty(UserName)) {
				params.put("SiteLinks", "获取网站链接失败");
				return params;
			} else {
				Member member = new Member(UserName);
				member.fill();
				SiteID = member.getSiteID() + "";
			}
		}
		if (User.isLogin() && User.isMember()) {
			params.put("display", "none");
		}
		String SiteAlias = SiteUtil.getAlias(SiteID);
		String Path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteAlias + "/";
		DataTable dt = new QueryBuilder("select * from zccatalog where Type = 1 and ParentID = 0 and SiteID = ?",
				SiteID).executePagedDataTable(10, 0);
		SiteLinks += "<a href='" + Path + "'>首页</a>&nbsp;&nbsp;&nbsp;";

		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				SiteLinks += "<a href='" + Path + dt.getString(i, "URL") + "'>" + dt.getString(i, "Name")
						+ "</a>&nbsp;&nbsp;&nbsp;";
			}
		}
		params.put("SiteID", SiteID);
		params.put("SiteLinks", SiteLinks);
		return params;
	}

	public void doLogin() {
		Object authCode = User.getValue(Constant.DefaultAuthKey);
		String userName = $V("UserName");
		if (wrongList.contains(userName)) {
			if (authCode == null || !authCode.equals($V("VerifyCode"))) {
				Response.setStatus(0);
				Response.setMessage("验证码输入错误");
				return;
			}
		}
		String passWord = $V("PassWord");

		int cookieTime = 0;
		if (StringUtil.isNotEmpty($V("CookieTime"))) {
			cookieTime = Integer.parseInt($V("CookieTime"));
		} else {
			cookieTime = 1800;
		}

		Member member = new Member(userName);
		if (member.isExists()) {
			if (!member.fill()) {
				Response.setLogInfo(0, "用户名或密码错误，请重新输入");
			}
			if (member.checkPassWord(passWord)) {
				String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10)
						+ (System.currentTimeMillis() + ""));
				Cookie.setCookie("VerifyCookie", VerifyCookie, cookieTime);
				Cookie.setCookie("UserName", userName, cookieTime);
				User.setManager(false);
				User.setLogin(true);
				User.setUserName(userName);
				User.setRealName(member.getName());
				User.setType(member.getType());
				User.setMember(true);
				User.setValue("SiteID", member.getSiteID() + "");
				User.setValue("UserName", member.getUserName() + "");
				User.setValue("Type", member.getType());
				ExtendManager.executeAll("Member.Login", new Object[] { member });// 调用扩展
				if (StringUtil.isNotEmpty(member.getName())) {
					User.setValue("Name", member.getName());
				} else {
					User.setValue("Name", member.getUserName());
				}
				member.setLoginMD5(VerifyCookie);
				member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= "
						+ member.getScore() + " order by Score desc").executeOneValue()
						+ "");
				member.setLastLoginIP(Request.getClientIP());
				member.setLastLoginTime(new Date());
				member.update();
				Response.setLogInfo(1, "登录成功，欢迎您 " + userName);
				Response.put("UserName", userName);
				Response.put("Name", member.getName());
				Response.put("Type", member.getType());
				synchronized (wrongList) {
					wrongList.remove(userName);
				}
			} else {
				Response.setLogInfo(0, "用户名或密码错误，请重新输入");
				if (!wrongList.contains(userName)) {
					synchronized (wrongList) {
						if (wrongList.size() > 20000) {// 错误的用户名大于2万则要清掉，以免被恶意攻击导致内存泄漏
							wrongList.clear();
						}
						wrongList.add(userName);
					}
				}
			}
		} else {
			Response.setLogInfo(0, "用户名不存在，请重新输入");
		}
	}

	public static void checkAndLogin(HttpServletRequest request) {
		String VerifyCookie = ServletUtil.getCookieValue(request, "VerifyCookie");
		String UserName = ServletUtil.getCookieValue(request, "UserName");
		if (!User.isManager() && !User.isLogin() && StringUtil.isNotEmpty(UserName)) {
			Member member = new Member(UserName);
			if (member.fill()) {
				if (StringUtil.isNotEmpty(member.getLoginMD5()) && member.getLoginMD5().equalsIgnoreCase(VerifyCookie)) {
					User.setManager(false);
					User.setLogin(true);
					User.setUserName(member.getUserName());
					User.setRealName(member.getName());
					User.setType(member.getType());
					User.setMember(true);
					User.setValue("SiteID", member.getSiteID() + "");
					User.setValue("UserName", member.getUserName() + "");
					User.setValue("Type", member.getType());

					ExtendManager.executeAll("Member.Login", new Object[] { member });// 调用扩展

					if (StringUtil.isNotEmpty(member.getName())) {
						User.setValue("Name", member.getName());
					} else {
						User.setValue("Name", member.getUserName());
					}
					member.setLoginMD5(VerifyCookie);
					member.setLastLoginIP(request.getRemoteAddr());
					member.setLastLoginTime(new Date());
					member.update();
				}
			}
		}
	}

	public void logout() {
		Cookie.setCookie("VerifyCookie", "", 0);
		Cookie.setCookie("UserName", "", 0);
		String SiteID = User.getValue("SiteID") + "";
		User.setUserName("");
		User.setLogin(false);
		User.setMember(false);
		User.destory();
		Response.setStatus(1);
		Response.setMessage(SiteID);
	}
	
	/**
	 * 输入用户名和密码评论后让会员成为登录状态
	 * 
	 */
	public void loginComment(HttpServletRequest request, HttpServletResponse response, String userName, String passWord) {
		Member member = new Member(userName);
		if (member.isExists()) {
			member.fill();
			if (member.checkPassWord(passWord)) {
				String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10)
						+ (System.currentTimeMillis() + ""));
				Cookie = new CookieImpl(request);
				Cookie.setCookie("VerifyCookie", VerifyCookie, 0);
				Cookie.setCookie("UserName", userName, 0);
				Cookie.writeToResponse(request, response);
				User.setManager(false);
				User.setLogin(true);
				User.setUserName(userName);
				User.setRealName(member.getName());
				User.setType(member.getType());
				User.setMember(true);
				User.setValue("SiteID", member.getSiteID() + "");
				User.setValue("UserName", member.getUserName() + "");
				User.setValue("Type", member.getType());

				ExtendManager.executeAll("Member.Login", new Object[] { member });// 调用扩展

				if (StringUtil.isNotEmpty(member.getName())) {
					User.setValue("Name", member.getName());
				} else {
					User.setValue("Name", member.getUserName());
				}
				member.setLoginMD5(VerifyCookie);
				member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= "
						+ member.getScore() + " order by Score desc").executeOneValue()
						+ "");
				member.setLastLoginIP(request.getRemoteAddr());
				member.setLastLoginTime(new Date());
				member.update();
			}
		}
	}

}
