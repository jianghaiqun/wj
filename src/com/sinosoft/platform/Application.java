package com.sinosoft.platform;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.HtmlScript;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class Application extends Page {

	/**
	 * 初始化以获取站点、菜单权限
	 * 
	 * @return Mapx
	 */
	public static Mapx init(Mapx params) {
		// 站点列表
		DataTable dt = null;
		// orderflag --> id 2010/09/14

		if ("admin".equals(User.getUserName())) {
			dt = new QueryBuilder("select name,id from zcsite order by id ").executeDataTable();
		} else {
			dt = new QueryBuilder("select name,id from zcsite order by id desc").executeDataTable();
		}

		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
			}
		});
		if (Application.getCurrentSiteID() == 0 && dt.getRowCount() > 0) {
			Application.setCurrentSiteID(dt.getString(0, "ID"));
		}
		params.put("Sites", HtmlUtil.dataTableToOptions(dt, Application.getCurrentSiteID() + ""));
		// orderflag --> id 2010/09/14
		DataTable dtsite = null;
		if ("admin".equals(User.getUserName())) {
			dtsite = new QueryBuilder("select ID,Name from zcsite order by id desc").executeDataTable();
		} else {
			dtsite = new QueryBuilder("select ID,Name from zcsite order by id desc").executeDataTable();
		}

		StringBuffer sitestb = new StringBuffer();
		for (int i = 0; i < dtsite.getRowCount(); i++) {
			if (dtsite.getString(i, "ID").equals(Application.getCurrentSiteID() + "")) {
				sitestb.append("<a value=\"" + dtsite.getString(i, "ID") + "\" class=\"ahover\" hidefocus href=\"javascript:void(0);\">" + dtsite.getString(i, "Name") + "</a>");
				params.put("CurrentSiteName", dtsite.getString(i, "Name"));
				params.put("CurrentSiteId", dtsite.getString(i, "ID"));
			} else {
				sitestb.append("<a value=\"" + dtsite.getString(i, "ID") + "\" hidefocus href=\"javascript:void(0);\">" + dtsite.getString(i, "Name") + "</a>");
			}
		}
		params.put("ZCSites", sitestb.toString());
		// 菜单
		dt = new QueryBuilder("select name,id from zdmenu where  visiable='Y' and parentID=0 order by OrderFlag").executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() + "-" + dr.getString("id"), Priv.MENU_BROWSE);
			}
		});
		boolean hasMenu = false;
		String template = "<a id='_Menu_${ID}' onclick='Application.onMainMenuClick(this);return false;' hidefocus='true'><b>${Name}</b></a>";
		String menuHtml = HtmlUtil.replaceWithDataTable(dt, template);
		if (dt.getRowCount() > 0) {
			hasMenu = true;
		}

		StringBuffer sb = new StringBuffer();
		// Script
		template = "arr.push([${ID},\"${Name}\",\"${URL}\",\"${Icon}\",\"${Prop1}\"]);";
		sb.append("var arr;");
		for (int i = 0; i < dt.getRowCount(); i++) {
			long id = dt.getLong(i, "ID");
			sb.append("arr = [];");
			DataTable dt2 = new QueryBuilder("select name,id,url,icon,prop1 from zdmenu where visiable='Y' and parentID=? order by OrderFlag", id).executeDataTable();
			dt2 = dt2.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() + "-" + dr.getString("id"), Priv.MENU_BROWSE);
				}
			});
			sb.append(HtmlUtil.replaceWithDataTable(dt2, template));
			sb.append("$('_Menu_" + id + "').ChildArray = arr;");
			if (dt2.getRowCount() > 0) {
				hasMenu = true;
			}
		}

		HtmlScript script = new HtmlScript();
		script.setInnerHTML(sb.toString());
		if (hasMenu) {
			params.put("Menu", menuHtml + script.getOuterHtml());
		} else {
			params.put("Menu", "<font color='yellow'>对不起，你没有任何菜单权限，请联系'管理员'分配菜单权限后再登陆！</font>");
		}

		DataCollection privDC = Login.getAllPriv(new DataCollection());
		String priv = StringUtil.htmlEncode(privDC.toXML().replaceAll("\\s+", " "));
		params.put("Privileges", priv);
		return params;
	}

	//判断用户修改密码时间
	public void initTime() {
		      try{  // 得到当前时间的月数
				String currentDate=DateUtil.getCurrentDate();
				String[] dateArray1 = currentDate.split("-");
				int month1 =Integer.parseInt(dateArray1[0]) * 12 + Integer.parseInt(dateArray1[1]);
				// 得到密码修改时间的月数
				QueryBuilder qb=new QueryBuilder("select prop6 from zduser where UserName=?");
				qb.add(User.getUserName());
				DataTable dtpass=qb.executeDataTable();
				if(dtpass!=null){
				   if(StringUtil.isNotEmpty(dtpass.getString(0, "prop6"))){
				     String pwModifyTime=dtpass.getString(0, "prop6");
				     String[] dateArray2 = pwModifyTime.split("-");
				     int month2 = Integer.parseInt(dateArray2[0]) * 12 + Integer.parseInt(dateArray2[1]);
				   //计算相差月份
				    int m =month1-month2 ;
				    if(m>=3){
					   Response.setStatus(5);
				    }
				}else{
					QueryBuilder qb1=new QueryBuilder("update zduser set prop6='"+currentDate+"'"+" where UserName='"+User.getUserName()+"'");
					qb1.executeNoQuery();
				}
				}
		      }catch(Exception e){
		    	  logger.error(e.getMessage(), e);
		      }
	}
	/**
	 * 更改站点
	 */
	public void changeSite() {
		String SiteID = $V("SiteID");
		Application.setCurrentSiteID(SiteID);
	}

	/**
	 * 设置当前站点的站点ID
	 * 
	 * @param siteID
	 *            站点ID
	 */
	public static void setCurrentSiteID(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			User.setValue("_CurrentSiteID", "");
		} else {
			User.setValue("_CurrentSiteID", siteID);
		}

	}

	/**
	 * 获取当前站点的站点ID
	 * 
	 * @return siteID 站点ID
	 */
	public static long getCurrentSiteID() {
		String id = (String) User.getValue("_CurrentSiteID");
		if (StringUtil.isEmpty(id)) {
			if (UserList.ADMINISTRATOR.equals(User.getUserName())) {
				logger.info("请在站点管理->站点列表下先创建站点");
				return 0L;
			} else {
				logger.info("用户：{}没有任何站点的浏览权限，请先设置权限再登陆", User.getUserName());
				return 0L;
			}
		}
		return Long.parseLong(id);
	}

	/**
	 * 获取当前站点的站点别名
	 * 
	 * @return siteAlias 站点别名
	 */
	public static String getCurrentSiteAlias() {
		return (String) SiteUtil.getAlias(getCurrentSiteID());
	}

	/**
	 * 修改密码
	 */
	public void changePassword() {
		String OldPassword = $V("OldPassword");
		String Password = $V("Password");
		QueryBuilder qb = new QueryBuilder("update ZDUser set Password=? where UserName=? and Password=?");
		qb.add(StringUtil.md5Hex(Password));
		qb.add(User.getUserName());
		qb.add(StringUtil.md5Hex(OldPassword));
		if (qb.executeNoQuery() > 0) {
			UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "修改密码成功", Request.getClientIP());
			Response.setMessage("修改密码成功");
			Response.setStatus(1);
			this.updatetime();
		} else {
			UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "修改密码失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("修改密码失败，旧密码不正确");
		}
	}
	/**
	 * 记录修改密码时间
	 */
	public void updatetime(){
		String pwModifyTime=DateUtil.getCurrentDate();
		QueryBuilder qb = new QueryBuilder("update ZDUser set prop6=? where UserName=?" );
		qb.add(pwModifyTime);
		qb.add(User.getUserName());
		qb.executeNoQuery();
	}

	/**
	 * 退出
	 */
	public void logout() {
		String logouturl = Config.getContextPath() + "Logout.jsp";

		Response.put("Status", 1);
		UserLog.log(UserLog.LOG, UserLog.LOGOUT, "正常退出系统", Request.getClientIP());

		redirect(logouturl);
	}

}
