package com.sinosoft.bbs;

import com.sinosoft.bbs.admin.ForumScore;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.ZCForumConfigSchema;
import com.sinosoft.schema.ZCForumGroupSchema;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZCForumMemberSet;
import com.sinosoft.schema.ZCForumSchema;
import com.sinosoft.schema.ZCForumSet;
import com.sinosoft.schema.ZCThemeSchema;
import com.sinosoft.schema.ZCThemeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 * @author wst
 * 
 */

public class ForumUtil {
	private static final Logger logger = LoggerFactory.getLogger(ForumUtil.class);
	private static final String YES = "Y";

	/**
	 * 从缓存中得到论坛开放状态
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumStatus(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return false;
		}
		return YES.equalsIgnoreCase(config.getTempCloseFlag());
	}

	/**
	 * 从缓存中得到板块锁定状态
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumLock(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return YES.equalsIgnoreCase(forum.getLocked());
	}

	/**
	 * 得到板块是否设置密码
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumPassword(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return StringUtil.isNotEmpty(forum.getPassword());
	}

	/**
	 * 得到板块是否显示
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumDisplay(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return YES.equalsIgnoreCase(forum.getVisible());
	}

	/**
	 * 判断论坛是否进行过数据初始化
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean isInitDB(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return false;
		}
		return true;
	}

	/**
	 * 如果用户的用户组允许访问论坛，则在User中放入Y
	 * 
	 * @param SiteID
	 */
	public static void allowVisit(String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		createBBSUser(SiteID);
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowVisit")) {
			User.setValue("AllowMemberVisit", "Y");
		} else {
			User.setValue("AllowMemberVisit", "N");
		}
	}

	public static void isUnLockGroup(String ForumID) {
		if (StringUtil.isEmpty(User.getUserName()) || StringUtil.isEmpty(ForumID)) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (StringUtil.isEmpty(forum.getUnLockID())) {
			return;
		}
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnLockID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if (id == member.getUserGroupID() || id == member.getAdminID() || id == member.getDefinedID()) {
				User.setValue("UnLockGroup", "Y");
				return;
			}
		}
		User.setValue("UnLockGroup", "N");
	}

	public static void isUnPasswordGroup(String ForumID) {
		if (StringUtil.isEmpty(User.getUserName())) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnPasswordID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if (id == member.getUserGroupID() || id == member.getAdminID() || id == member.getDefinedID()) {
				User.setValue("pass_" + ForumID, "Y");
				return;
			}
		}
		User.setValue("pass_" + ForumID, "N");
	}

	/**
	 * 传入一个用户名userName，判断当前用户是否对此用户有操作权限
	 * 
	 * @author cjc
	 * @param userName
	 * @return
	 */
	public static boolean isOperateMember(String userName) {
		if (StringUtil.isNotEmpty(userName) && ForumUtil.isExistMember(userName)) {
			if (User.getUserName().equals(UserList.ADMINISTRATOR)) {
				return true;
			}
			if (User.getUserName().equals(userName)) {
				return true;
			}
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			ZCForumGroupSchema group = null;
			ZCForumMemberSchema currentMember = new ZCForumMemberSchema();
			ZCForumGroupSchema currentGroup = null;
			member.setUserName(userName);
			member.fill();

			currentMember.setUserName(User.getUserName());
			currentMember.fill();

			group = ForumCache.getGroup(member.getAdminID());
			currentGroup = ForumCache.getGroup(currentMember.getAdminID());

			if (currentGroup.getSystemName().equals("系统管理员")) {
				return true;
			}
			if (member.getAdminID() == 0) {
				return true;
			} else if (StringUtil.isNotEmpty(currentGroup.getSystemName())
					&& currentGroup.getSystemName().equals("超级版主")) {
				if (!group.getSystemName().equals("超级版主") && !group.getSystemName().equals("系统管理员")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据分数来更新用户的普通用户组
	 * 
	 * @author wst
	 * @param trans
	 * @param user
	 */
	public static void userGroupChange(ZCForumMemberSchema member) {
		long SiteID = member.getSiteID();
		long ForumScore = member.getForumScore();
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZCForumGroup where SiteID=? and LowerScore<=? and UpperScore>?", SiteID, ForumScore);
		qb.add(ForumScore);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			member.setUserGroupID(dt.getLong(0, "ID"));
		}
	}

	/**
	 * 重载方法
	 * 
	 * @param memberSet
	 */
	public static void userGroupChange(ZCForumMemberSet memberSet) {
		for (int i = 0; i < memberSet.size(); i++) {
			userGroupChange(memberSet.get(i));
		}
	}

	/**
	 * 如果用户第一次进入论坛则加入用户论坛信息表
	 * 
	 * @author wst
	 * @param trans
	 * @param userName
	 */
	public static void createBBSUser(Transaction trans, String userName, String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		if (StringUtil.isEmpty(userName)) {
			return;
		}
		ZCForumMemberSchema forumMember = new ZCForumMemberSchema();
		forumMember.setUserName(userName);
		if (forumMember.fill()) {
			return;
		}
		forumMember.setThemeCount(0);
		forumMember.setReplyCount(0);
		forumMember.setAdminID(0);
		forumMember.setUserGroupID(0);
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			forumMember.setSiteID(0);
			forumMember.setForumScore(new ForumScore(0).InitScore);
		} else {
			forumMember.setSiteID(SiteID);
			forumMember.setForumScore(new ForumScore(SiteID).InitScore);
		}
		forumMember.setUseSelfImage("N");
		forumMember.setHeadImage("Images/SystemHeadImage/HeadImage01.gif");
		forumMember.setAddTime(new Date());
		forumMember.setAddUser(userName);
		// 通过会员的初始积分给会员一个普通用户组
		userGroupChange(forumMember);
		if (trans != null) {
			trans.add(forumMember, Transaction.INSERT);
		} else {
			forumMember.insert();
		}
	}

	public static void createBBSUser(String SiteID) {
		createBBSUser(null, User.getUserName(), SiteID);
	}

	/**
	 * 根据板块ID得到当前板块的版主和分区版主字符串数组
	 * 
	 * @author wst
	 * @param ThemeID
	 * @return 版主的UserName的字符串数组
	 */
	public static String[] getAdmins(String ForumID) {
		DataTable dt = new QueryBuilder(
				"select a.ForumAdmin,b.ForumAdmin ParentAdmin from ZCForum a,ZCForum b where a.ID=?"
						+ " and b.ID=a.ParentID", ForumID).executeDataTable();
		String ForumAdmins = "";
		if (dt.getRowCount() > 0) {
			String ForumAdmin = dt.getString(0, "ForumAdmin");
			if (ForumAdmin.endsWith(",")) {
				int index = ForumAdmin.lastIndexOf(",");
				ForumAdmin = ForumAdmin.substring(0, index);
			}
			String ParentAdmin = dt.getString(0, "ParentAdmin");
			if (ParentAdmin.endsWith(",")) {
				int index = ParentAdmin.lastIndexOf(",");
				ParentAdmin = ParentAdmin.substring(0, index);
			}
			if (StringUtil.isNotEmpty(ForumAdmin)) {
				ForumAdmins = ForumAdmin;
				if (StringUtil.isNotEmpty(ParentAdmin)) {
					ForumAdmins += "," + ParentAdmin;
				}
			} else {
				if (StringUtil.isNotEmpty(ParentAdmin)) {
					ForumAdmins = ParentAdmin;
				}
			}
		}
		return ForumAdmins.split(",");
	}

	/**
	 * 判断当前登录用户是不是相应板块的版主和超级版主
	 * 
	 * @author wst
	 * @param ThemeID
	 * @return
	 */
	public static int isAdmin(String ForumID, String SiteID) {
		if (StringUtil.isNotEmpty(User.getUserName())) {

			if (User.getUserName().equals(UserList.ADMINISTRATOR)) {
				return 1;// 系统管理员
			}
			String sqlAdmin = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='系统管理员'";
			int countAdmin = new QueryBuilder(sqlAdmin, SiteID).executeInt();
			if (countAdmin > 0) {
				return 1;// 系统管理员
			}
			/*
			 * String sqlSpecail = "select count(*) " +
			 * "from ZCForumMember a,ZCforumgroup b,ZCAdminGroup c,ZCforumgroup d "
			 * + "where a."
			 * +sqlSiteID+" and b."+sqlSiteID+" and c."+sqlSiteID+" and d."
			 * +sqlSiteID+ " and a.UserName='lolo' and a.userGroupID=b.ID and
			 * b.ID=c.groupID and b.RadminID=d.ID and d.SystemName='超级版主'" ; int
			 * countSpecail = new QueryBuilder(sqlSpecail).executeInt();
			 * if(countSpecail > 0){ return 2;//特殊组关联到超级版主 }
			 */
			String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='超级版主'";
			int count = new QueryBuilder(sql, SiteID).executeInt();
			if (count > 0) {
				return 2;// 超级版主
			}
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return 3;// 版主
				}
			}
		}
		return 0;
	}

	/**
	 * 得到该用户是否是该板块的版主
	 */
	public static boolean isForumAdmin(String ForumID, String member) {
		String[] forumAdmins = getAdmins(ForumID);
		for (int i = 0; i < forumAdmins.length; i++) {
			if (forumAdmins[i].equals(member)) {
				return true;// 版主
			}
		}
		return false;
	}

	public static boolean isForumAdmin(String ForumID) {
		return isForumAdmin(ForumID, User.getUserName());
	}

	/**
	 * 用户是否有可以操作主题的权限
	 * 
	 * @param ForumID
	 * @param SiteID
	 * @return
	 */
	public static boolean isOperateTheme(String ForumID, String SiteID) {
		if (!User.isLogin()) {
			return false;
		}
		String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
				+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='版主'";
		int count = new QueryBuilder(sql, SiteID).executeInt();
		if (count > 0) {
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return true;// 版主
				}
			}
			return false;
		}
		return true;
	}

	public static String operateThemeButton(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		if (isOperateTheme(ForumID, SiteID)) {
			ForumPriv priv = new ForumPriv(SiteID, ForumID);
			if (priv.hasPriv("RemoveTheme")) {
				sb.append("<a href='#;' onclick='del()'>删除主题</a>");
			}
			if (priv.hasPriv("MoveTheme")) {
				sb.append("<a href='#;' onclick='move()'>移动主题</a>");
			}
			if (priv.hasPriv("BrightTheme")) {
				sb.append("<a href='#;' onclick='bright()'>高亮/取消显示</a>");
			}
			if (priv.hasPriv("TopTheme")) {
				sb.append("<a href='#;' onclick='top()'>置顶/解除置顶</a>");
			}
			if (priv.hasPriv("UpOrDownTheme")) {
				sb.append("<a href='#;' onclick='upOrDown()'>提升/下沉主题</a>");
			}
			if (priv.hasPriv("BestTheme")) {
				sb.append("<a href='#;' onclick='best()'>设为/取消精华</a>");
			}
		}
		return sb.toString();
	}

	/**
	 * 查询数据库判断是不是所有用户名都存在
	 * 
	 * @author wst
	 * @param members
	 *            以","分割的用户名字符串
	 * @return
	 */
	public static boolean isExistMember(String members) {
		if (!StringUtil.checkID(members)) {
			logger.warn("可能的SQL注入,ForumUtil.isExistMember:{}", members);
			return true;
		}
		String[] ForumAdmins = members.trim().split(",");
		StringBuffer sql = new StringBuffer("select count(*) from ZDMember where UserName='" + ForumAdmins[0] + "'");
		for (int i = 1; i < ForumAdmins.length; i++) {
			sql.append(" or UserName='" + ForumAdmins[i] + "'");
		}
		int count = new QueryBuilder(sql.toString()).executeInt();
		if (count == ForumAdmins.length) {
			return true;
		}
		return false;
	}

	/**
	 * 更新板块的版主后，在member表中做更新
	 * 
	 * @author wst
	 * @param trans
	 * @param ForumAdmin
	 * @return
	 */
	public static void addAdminID(Transaction trans, String ForumID, String ForumAdmin) {
		String[] ForumAdmins = ForumAdmin.trim().split(",");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ForumID);
		forum.fill();
		checkDelAdmin(trans, ForumAdmins, forum);
		// 判断更新版主在member表中已经存在版主或者更高的身份，并根据判断进行更新
		long groupID = ForumCache.getGroupBySystemName(String.valueOf(getCurrentBBSSiteID()), "版主").getID();
		for (int i = 0; i < ForumAdmins.length; i++) {
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			member.setUserName(ForumAdmins[i]);
			member.fill();
			if (member.getAdminID() == 0) {
				member.setAdminID(groupID);
			} else {
				long id = ForumCache.getGroupBySystemName(String.valueOf(getCurrentBBSSiteID()), "禁止访问").getID();
				if (member.getAdminID() == id) {
					member.setAdminID(groupID);
				}
			}
			trans.add(member, Transaction.UPDATE);
		}

	}

	public static String getBBSName(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return null;
		}
		return config.getName();
	}

	/**
	 * 初始化导航条权限
	 * 
	 * @author wst
	 * @param ForumID
	 * @return
	 */
	public static String initPriv(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		ForumPriv priv = new ForumPriv(SiteID);
		if (User.isLogin()) {
			sb.append("<a href='Logout.jsp?SiteID=" + SiteID + "'>退出</a>");
		}
		if (priv.hasPriv("AllowSearch")) {
			sb.append(" | <a href='ThemeSearch.jsp?SiteID=" + SiteID + "'>搜索</a>");
		}
		if (priv.hasPriv("AllowPanel")) {
			sb.append(" | <a href='ControlPanel.jsp?SiteID=" + SiteID + "'>控制面板</a> | <a href='MyThemes.jsp?SiteID="
					+ SiteID + "'>我的话题</a> ");
		}
		if (priv.hasPriv("Admin")) {
			sb.append(" | <a href='MasterPanel.jsp?SiteID=" + SiteID + "'>版主管理面版</a>");
		}
		if (StringUtil.isNotEmpty(ForumID)) {
			ForumRule rule = new ForumRule(ForumID);
			if (rule.getRule("AllowTheme") || ForumUtil.isAdmin(ForumID, SiteID) > 0) {
				// if (priv.getPriv("AllowTheme")) { 任何时刻都要显示发表新话题，如果没有权限，则引导去注册
				sb.append(" |<a href='ThemeAdd.jsp?SiteID=" + SiteID + "&ForumID=" + ForumID + "'>发表新话题</a>");
				// /}
			}
		}
		return sb.toString();
	}

	/**
	 * 重载方法
	 * 
	 * @return
	 */
	public static String initPriv(String SiteID) {
		return initPriv(null, SiteID);
	}

	/**
	 * 版主管理板块权限
	 * 
	 * @param map
	 */
	public static void adminPriv(Mapx map) {
		String SiteID = map.getString("SiteID");
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowEditUser")) {
			map.put("AllowEditUser", "<a href='MasterPanel.jsp?SiteID=" + SiteID + "'>编辑用户</a>");
		}
		/*
		 * if(priv.getPriv("AllowForbidUser")){ map.put("AllowForbidUser",
		 * "<a href='ForbidUser.jsp?SiteID="+SiteID+"'>禁止用户</a>"); }
		 */
		if (priv.hasPriv("AllowEditForum")) {
			map.put("AllowEditForum", "<a href='ForumEdit.jsp?SiteID=" + SiteID + "'>板块编辑</a>");
		}
		if (priv.hasPriv("AllowVerfyPost")) {
			map.put("AllowVerfyPost", "<a href='PostAudit.jsp?SiteID=" + SiteID + "'>帖子审核</a>");
		}
	}

	/**
	 * 主题移动或删除后，需要判断是不是该板块下的最后发帖以便更改板块最后发帖信息
	 * 
	 * @author wst
	 * @param forum
	 * @param ids
	 */
	public static void changeLastTheme(ZCForumSchema originalForum, ZCForumSchema targetForum, String ids) {
		if (!StringUtil.checkID(ids)) {
			logger.warn("可能的SQL注入,ForumUtil.changeLastTheme:{}", ids);
			return;
		}
		String sqlMin = "select ID from ZCTheme where status='Y' and ForumID=" + originalForum.getID() + " and ID in("
				+ ids + ") order by ID desc";
		DataTable dtMin = new QueryBuilder(sqlMin).executePagedDataTable(1, 0);
		String sqlNext = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
				+ originalForum.getID() + " and ID>" + dtMin.getString(0, "ID") + " and ID not in(" + ids + ")";
		int count = new QueryBuilder(sqlNext).executeInt();
		if (count == 0) {
			String sqlOriginal = "select Subject,AddUser,ID from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ originalForum.getID() + " and ID not in(" + ids + ") order by ID desc";
			DataTable dt = new QueryBuilder(sqlOriginal).executePagedDataTable(1, 0);
			if (dt.getRowCount() > 0) {
				originalForum.setLastPost(dt.getString(0, "Subject"));
				originalForum.setLastPoster(dt.getString(0, "AddUser"));
				originalForum.setLastThemeID(dt.getString(0, "ID"));
			} else {
				originalForum.setLastPost("");
				originalForum.setLastPoster("");
				originalForum.setLastThemeID("");
			}
		}
		if (targetForum != null) {
			String sqlTarget = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ targetForum.getID() + " and ID>" + dtMin.getString(0, "ID");
			int countTarget = new QueryBuilder(sqlTarget).executeInt();
			if (countTarget == 0) {
				String sql = "select Subject,AddUser,ID from ZCTheme where ID=" + dtMin.getString(0, "ID");
				DataTable dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
				if (dt.getRowCount() > 0) {
					targetForum.setLastPost(dt.getString(0, "Subject"));
					targetForum.setLastPoster(dt.getString(0, "AddUser"));
					targetForum.setLastThemeID(dt.getString(0, "ID"));
				} else {
					targetForum.setLastPost("");
					targetForum.setLastPoster("");
					targetForum.setLastThemeID("");
				}
			}
		}
	}

	public static void changeLastTheme(ZCForumSchema originalForum, String ids) {
		changeLastTheme(originalForum, null, ids);
	}

	/**
	 * 得到当前的站点ID
	 * 
	 * @return
	 */
	public static long getCurrentBBSSiteID() {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			return 0;
		} else {
			int count = new QueryBuilder("select ID from ZCSite").executeInt();
			if (count > 0) {
				return Application.getCurrentSiteID();
			} else {
				return 0;
			}
		}
	}

	/**
	 * 前台得到当前的站点ID
	 * 
	 * @return
	 */
	public static String getCurrentBBSSiteID(String SiteID) {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			SiteID = "0";
		} else {
			if (StringUtil.isNotEmpty(User.getUserName())) {
				if (User.isManager() && User.isLogin()) {
					SiteID = ForumUtil.getCurrentBBSSiteID() + "";
				} else {
					if (!UserList.ADMINISTRATOR.equals(User.getUserName())) {
						SiteID = new QueryBuilder("select SiteID from ZCForumMember where UserName='"
								+ User.getUserName() + "'").executeString();
					}
				}
			} else {
				if (StringUtil.isEmpty(SiteID)) {
					String sql = "select ID from ZCSite";
					DataTable dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
					if (dt.getRowCount() > 0) {
						SiteID = dt.getString(0, "ID");
					} else {
						SiteID = "0";
					}
				}
			}
		}
		return SiteID;
	}

	/**
	 * 判断用户在相应板块下是不是不能发帖
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isNotSendTheme(String SiteID, String ForumID) {
		return !isSendTheme(SiteID, ForumID);
	}

	/**
	 * 断用户在相应板块下是不是能发帖
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isSendTheme(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (rule.getRule("AllowTheme") || isAdmin(ForumID, SiteID) > 0) {
			if (priv.hasPriv("AllowTheme")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户在相应板块下是不是不能回复
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isNotReplyPost(String SiteID, String ForumID) {
		return !isReplyPost(SiteID, ForumID);
	}

	/**
	 * 判断用户在相应板块下是不是能回复
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isReplyPost(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (rule.getRule("ReplyPost") || isAdmin(ForumID, SiteID) > 0) {
			if (priv.hasPriv("AllowReply")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户在相应板块下是不是能编辑
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @param UserName
	 * @return
	 */
	public static boolean isEditPost(String SiteID, String ForumID, String UserName) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (isAdmin(ForumID, SiteID) > 0 && priv.hasPriv("AllowEdit")) {
			return true;
		} else if (rule.getRule("EditPost")) {
			if (StringUtil.isNotEmpty(User.getUserName()) && User.getUserName().equals(UserName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 找到member存在于set中的索引，如不存在返回-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfMemberSet(ZCForumMemberSet memberSet, ZCForumMemberSchema member) {
		for (int j = 0; j < memberSet.size(); j++) {
			if (member.getUserName().equals(memberSet.get(j).getUserName())) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 找到forum存在于set中的索引，如不存在返回-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfForumSet(ZCForumSet forumSet, ZCForumSchema forum) {
		for (int j = 0; j < forumSet.size(); j++) {
			if (forum.getID() == forumSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 找到theme存在于set中的索引，如不存在返回-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfThemeSet(ZCThemeSet themeSet, ZCThemeSchema theme) {
		for (int j = 0; j < themeSet.size(); j++) {
			if (theme.getID() == themeSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 检查被删除的版主在其他板块下是不是版主，如果不是则在把该用户在member表下的AdminID置为0，并更新该用户的用户组
	 * 
	 * @author wst
	 * @param ForumAdmins
	 * @param forum
	 */
	private static void checkDelAdmin(Transaction trans, String[] ForumAdmins, ZCForumSchema forum) {
		String admins = StringUtil.isEmpty(forum.getForumAdmin()) ? "" : forum.getForumAdmin();
		String[] oldAdmins = admins.split(",");
		String checkAdmin = "";
		Loop: for (int i = 0; i < oldAdmins.length; i++) {
			for (int j = 0; j < ForumAdmins.length; j++) {
				if (oldAdmins[i].equals(ForumAdmins[j])) {
					continue Loop;
				}
			}
			checkAdmin += oldAdmins[i] + ",";
		}
		if (checkAdmin.length() == 0) {
			return;
		}
		if (!StringUtil.checkID(checkAdmin)) {
			logger.warn("可能的SQL注入,ForumUtil.checkDelAdmin:{}", checkAdmin);
			return;
		}
		String[] checkAdmins = checkAdmin.split(",");
		for (int i = 0; i < checkAdmins.length; i++) {
			String sql = "select count(*) from ZCForum where ForumAdmin like '%" + checkAdmins[i] + "," + "%'";
			int count = new QueryBuilder(sql).executeInt();
			if (count == 1) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(checkAdmins[i]);
				member.fill();
				QueryBuilder qb = new QueryBuilder("select SystemName from ZCForumGroup where ID=?", member
						.getAdminID());
				DataTable dtType = qb.executeDataTable();
				if (dtType.getString(0, "SystemName").equals("超级版主")
						|| dtType.getString(0, "SystemName").equals("系统管理员")) {
					return;
				}
				member.setAdminID(0);
				// userGroupChange(member);
				trans.add(member, Transaction.UPDATE);
			}
		}
	}

	/**
	 * 获得当前论坛的名称
	 */
	public static String getCurrentName(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (StringUtil.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (StringUtil.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (StringUtil.isEmpty(siteID)) {
					return null;
				}
			}
		}
		Mapx map = CacheManager.getMapx("Forum", "Config");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumConfigSchema config = (ZCForumConfigSchema) vs[i];
			if (config.getSiteID() == Long.parseLong(siteID)) {
				return config.getName();
			}
		}
		return "";
	}

}
