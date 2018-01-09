package com.sinosoft.bbs;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCForumGroupSchema;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZCForumSchema;

public class MasterPanel extends Ajax {
	public static Mapx init(Mapx params) {
		params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		ForumUtil.adminPriv(params);
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void perInfoSave() {
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		forumUser.setNickName($V("NickName"));

		Transaction trans = new Transaction();
		trans.add(forumUser, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public static Mapx searchUserInit(Mapx params) {

		String userName = params.getString("UserName");
		if (StringUtil.isNotEmpty(userName) && !ForumUtil.isExistMember(userName)) {
			params.remove("UserName");
			params.put("Message", "用户名不存在！");
			return params;
		}

		if (StringUtil.isNotEmpty(userName)) {
			if (ForumUtil.isOperateMember(userName)) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(userName);
				member.fill();
				params.putAll(member.toMapx());
			} else {
				params.remove("UserName");
				params.put("Message", "您无权编辑该用户！");
			}
		}

		return params;
	}

	public void changeForum() {
		String forumID = $V("ForumID");
		if (StringUtil.isEmpty(forumID)) {
			Response.put("Info", "");
			return;
		}
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(forumID);

		if (forum.fill()) {
			Response.put("Info", forum.getInfo());
		} else {
			Response.setLogInfo(0, "该板块不存在!");
		}
	}

	public static Mapx forumEditInit(Mapx params) {
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		member.setUserName(User.getUserName());
		member.fill();
		group.setID(member.getAdminID());
		group.fill();
		DataTable dt = null;
		if (group.getSystemName().equals("超级版主")) {
			dt = new QueryBuilder("select Name, ID from ZCForum where SiteID=?", params.getString("SiteID"))
					.executeDataTable();

		} else {
			QueryBuilder qb = new QueryBuilder("select Name,ID from zcforum where SiteID=? and (forumadmin like '%"
					+ User.getUserName()
					+ ",%' and ParentID<>0) or ParentID in (select ID from zcforum where SiteID=? "
					+ " and forumadmin like '%" + User.getUserName() + ",%' and ParentID=0)", params
					.getString("SiteID"), params.getString("SiteID"));
			dt = qb.executeDataTable();
		}

		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public void editInfoSave() {
		ZCForumMemberSchema member = new ZCForumMemberSchema();

		member.setUserName($V("UName"));
		member.fill();
		member.setNickName($V("NickName"));
		member.setForumSign($V("ForumSign"));

		Transaction trans = new Transaction();
		trans.add(member, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public void editForumSave() {
		ZCForumSchema forum = new ZCForumSchema();

		forum.setID($V("ForumID"));
		forum.fill();
		forum.setInfo($V("Info"));

		Transaction trans = new Transaction();
		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}
}
