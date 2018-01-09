package com.sinosoft.platform;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDUserSchema;
import com.sinosoft.schema.ZDUserSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class Login extends Ajax {
	private static ArrayList wrongList = new ArrayList();

	public static String ssoLoginNew(String username) {
		String result = "0";
		if (username == null) {
			return result;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		ZDUserSet userSet = user.query();

		if (userSet == null || userSet.size() < 1) {
			return result;
		} else {
			result = "1";
			return result;
		}
	}

	public static void ssoLogin(HttpServletRequest request, HttpServletResponse response, String username) {
		if (username == null) {
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		ZDUserSet userSet = user.query();

		if (!Config.isAllowLogin && !username.equalsIgnoreCase(UserList.ADMINISTRATOR)) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "临时禁止登录.用户名：" + username, request.getRemoteAddr(), username);
			return;
		}

		if (userSet == null || userSet.size() < 1) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "SSO登陆失败.用户名：" + username, request.getRemoteAddr(), username);

		} else {
			user = userSet.get(0);
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);
			User.setValue("AdminInfo", user);
			
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "登录成功", request.getRemoteAddr());

			DataTable dt = new QueryBuilder("select name,id from zcsite order by id,BranchInnerCode ,orderflag ").executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});

			if (dt.getRowCount() > 0) {
				Application.setCurrentSiteID(dt.getString(0, 1));
			} else {
				Application.setCurrentSiteID("");
			}

			try {
				String path = request.getParameter("Referer");
				logger.info("SSOLogin,Referer:{}", path);
				if (StringUtil.isNotEmpty(path)) {
					if (StringUtil.isNotEmpty(request.getParameter("t")) && !"null".equalsIgnoreCase(request.getParameter("t"))) {
						response.sendRedirect(path + "?t=" + request.getParameter("t"));
					} else {
						response.sendRedirect(path);
					}
				} else {
					response.sendRedirect("Application.jsp");
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void submit() {
		int count=0;
		String userName = $V("UserName").toLowerCase();
		//if (wrongList.contains(userName)) {
			Object authCode = User.getValue(Constant.DefaultAuthKey);
			if (authCode == null || !authCode.equals($V("VerifyCode"))) {
				Response.setStatus(0);
				Response.setMessage("验证码输入错误");
				return;
			}
		//}
		// 密码被加密md5
		String Password = StringUtil.md5Hex($V("Password"));
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		ZDUserSet userSet = user.query();

		if (!Config.isAllowLogin && !user.getUserName().equalsIgnoreCase(UserList.ADMINISTRATOR)) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "临时禁止登录.用户名" + $V("UserName"), Request.getClientIP(), $V("UserName"));
			Response.setStatus(0);
			Response.setMessage("临时禁止登录，请与系统管理员联系!");
			return;
		}
		
		if (userSet == null || userSet.size() < 1) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆失败.用户名：" + $V("UserName"), Request.getClientIP(), $V("UserName"));
			Response.setStatus(0);
			Response.setMessage("用户名或密码输入错误");
			count+=1;
			setErrorCount(userName,count);
			// 用户名不存在则不需要加入到wrongList
		} else {
			user = userSet.get(0);
			if (!UserList.ADMINISTRATOR.equalsIgnoreCase(user.getUserName()) && UserList.STATUS_STOP.equals(user.getStatus())) {
				UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆失败.用户名：" + $V("UserName") + "已停用", Request.getClientIP(), $V("UserName"));

				Response.setStatus(0);
				Response.setMessage("该用户处于停用状态，请联系管理员！");
				return;
			}
			if (!user.getPassword().equalsIgnoreCase(Password)) {
				Response.setStatus(0);
				Response.setMessage("用户名或密码输入错误");
				count+=1;
				setErrorCount(userName,count);
				if (!wrongList.contains(userName)) {
					synchronized (wrongList) {
						if (wrongList.size() > 20000) {// 错误的用户名大于2万则要清掉，以免被恶意攻击导致内存泄漏
							wrongList.clear();
						}
						wrongList.add(userName);
					}
				}
				return;
				// http://www.jjwxc.net/onebook.php?novelid=937665&chapterid=13
			}
			
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆成功", Request.getClientIP());

			DataTable dt = new QueryBuilder("select name,id from zcsite order by id desc").executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});
			String siteID = this.getCookie().getCookie("SiteID");
			if (StringUtil.isNotEmpty(siteID) && Priv.getPriv(User.getUserName(), Priv.SITE, siteID, Priv.SITE_BROWSE)) {
				Application.setCurrentSiteID(siteID);
			} else {
				if (dt.getRowCount() > 0) {
					Application.setCurrentSiteID(dt.getString(0, 1));
				} else {
					Application.setCurrentSiteID("");
				}
			}

			Response.setStatus(1);
			synchronized (wrongList) {
				wrongList.remove(userName);
			}
			redirect("Application.jsp");
		}
		this.setErrorCount(userName,count);
	}

	//获取登录错误次数prop5
	public int getErrorCount(String userName){
		int count=0;
		try{
		DataTable dt = new QueryBuilder("select prop5 from zduser where username='"+userName+"'").executeDataTable();
		if(dt!=null&&dt.getRowCount()>0){
			if(StringUtil.isNotEmpty(dt.getString(0, "prop5")) ){
				count=Integer.parseInt(dt.getString(0, "prop5"));
			}
		}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return count;
	}
	
	//记录登录错误次数
	public void setErrorCount(String userName,int count){
		//count=0表示登录成功
		if(count>0){
			count+=this.getErrorCount(userName);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("update zduser set prop5="+count);
		//登录错误此时超过3次则停用账号
		if(count>=3){
			sb.append(",status='S'");
		}
		sb.append(" where username='"+userName+"'");
		QueryBuilder qb=new QueryBuilder(sb.toString());
		qb.executeNoQuery();
		
	}
	// 使用单点登录的用户登录方法
	public void submit(String userName, String Password) {
		// String userName = $V("UserName").toLowerCase();
		if (wrongList.contains(userName)) {
			Object authCode = User.getValue(Constant.DefaultAuthKey);
			if (authCode == null || !authCode.equals($V("VerifyCode"))) {
				Response.setStatus(0);
				Response.setMessage("验证码输入错误");
				return;
			}
		}
		// 密码被加密md5
		// String Password = StringUtil.md5Hex($V("Password"));
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		ZDUserSet userSet = user.query();

		if (!Config.isAllowLogin && !user.getUserName().equalsIgnoreCase(UserList.ADMINISTRATOR)) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "临时禁止登录.用户名" + $V("UserName"), Request.getClientIP(), $V("UserName"));
			Response.setStatus(0);
			Response.setMessage("临时禁止登录，请与系统管理员联系!");
			return;
		}

		if (userSet == null || userSet.size() < 1) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆失败.用户名：" + $V("UserName"), Request.getClientIP(), $V("UserName"));
			Response.setStatus(0);
			Response.setMessage("用户名或密码输入错误");
			// 用户名不存在则不需要加入到wrongList
		} else {
			user = userSet.get(0);
			if (!user.getPassword().equalsIgnoreCase(Password)) {
				Response.setStatus(0);
				Response.setMessage("用户名或密码输入错误");
				if (!wrongList.contains(userName)) {
					synchronized (wrongList) {
						if (wrongList.size() > 20000) {// 错误的用户名大于2万则要清掉，以免被恶意攻击导致内存泄漏
							wrongList.clear();
						}
						wrongList.add(userName);
					}
				}
				return;
				// http://www.jjwxc.net/onebook.php?novelid=937665&chapterid=13
			}
			if (!UserList.ADMINISTRATOR.equalsIgnoreCase(user.getUserName()) && UserList.STATUS_STOP.equals(user.getStatus())) {
				UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆失败.用户名：" + $V("UserName") + "已停用", Request.getClientIP(), $V("UserName"));

				Response.setStatus(0);
				Response.setMessage("该用户处于停用状态，请联系管理员！");
				return;
			}
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			// UserLog.log(UserLog.LOG, UserLog.LOGIN, "登陆成功",
			// Request.getClientIP());

			DataTable dt = new QueryBuilder("select name,id from zcsite order by id desc").executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});
			// String siteID = this.getCookie().getCookie("SiteID");
			// if (StringUtil.isNotEmpty(siteID) &&
			// Priv.getPriv(User.getUserName(), Priv.SITE, siteID,
			// Priv.SITE_BROWSE)) {
			// Application.setCurrentSiteID(siteID);
			// } else {
			if (dt.getRowCount() > 0) {
				Application.setCurrentSiteID(dt.getString(0, 1));
			} else {
				Application.setCurrentSiteID("");
			}
			// }

			Response.setStatus(1);
			synchronized (wrongList) {
				wrongList.remove(userName);
			}
			redirect("Application.jsp");
		}
	}

	public void getAllPriv() {
		getAllPriv(this.Response);
	}

	public static DataCollection getAllPriv(DataCollection Response) {
		if (UserList.ADMINISTRATOR.equalsIgnoreCase(User.getUserName())) {
			Response.put("isBranchAdmin", "Y");
		} else {
			Response.put("isBranchAdmin", "N");
			StringBuffer privTypes = new StringBuffer();
			Object[] ks = Priv.PRIV_MAP.keyArray();
			for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
				if (Priv.MENU.equals(ks[i])) {
					continue;
				}
				privTypes.append(ks[i].toString());
				privTypes.append(",");
			}
			privTypes.deleteCharAt(privTypes.length() - 1);
			Response.put("privTypes", privTypes.toString());

			Response.put(Priv.SITE + "DT", Priv.getSitePrivDT(User.getUserName(), Application.getCurrentSiteID() + "", Priv.SITE));

			for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
				if (Priv.MENU.equals(ks[i])) {
					continue;
				} else if (Priv.SITE.equals(ks[i])) {

				} else {
					Response.put(ks[i] + "DT", Priv.getCatalogPrivDT(User.getUserName(), Application.getCurrentSiteID() + "", (String) ks[i]));
				}
			}
		}
		return Response;
	}
}
