package com.sinosoft.bbs.admin;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCAdminGroupSchema;
import com.sinosoft.schema.ZCForumGroupSchema;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZCForumMemberSet;

public class ForumUserGroupOption extends Page {
	public static Mapx init(Mapx params) {
		return params;
	}

	/**
	 * 用户组基本设置初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initBasic(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		if (StringUtil.isEmpty(userGroup.getSystemName()) || !userGroup.getSystemName().equals("游客")) {
			params.put("AllowHeadImage", HtmlUtil.mapxToRadios("AllowHeadImage", map, userGroup.getAllowHeadImage()));
			params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName", map, userGroup.getAllowNickName()));
			params.put("AllowPanel", HtmlUtil.mapxToRadios("AllowPanel", map, userGroup.getAllowPanel()));
		} else {
			params.put("AllowHeadImage", "不允许");
			params.put("AllowNickName", "不允许");
			params.put("AllowPanel", "不允许");
		}
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map, userGroup.getAllowUserInfo()));
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map, userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map, userGroup.getAllowSearch()));
		return params;
	}

	/**
	 * 用户组帖子相关初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initPostOption(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		if (StringUtil.isEmpty(userGroup.getSystemName()) || !userGroup.getSystemName().equals("游客")) {
			params.put("AllowTheme", HtmlUtil.mapxToRadios("AllowTheme", map, userGroup.getAllowTheme()));
			params.put("AllowReply", HtmlUtil.mapxToRadios("AllowReply", map, userGroup.getAllowReply()));
			params.put("Verify", HtmlUtil.mapxToRadios("Verify", map, userGroup.getVerify()));
		} else {
			params.put("AllowTheme", "不允许");
			params.put("AllowReply", "不允许");
			params.put("Verify", "不允许");
		}
		return params;
	}

	/**
	 * 用户组设置保存
	 * 
	 */
	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(Request);
		trans.add(userGroup, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "设置成功!");
		} else {
			Response.setLogInfo(0, "操作失败！");
		}
	}

	public static Mapx initSpecailOption(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map, userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map, userGroup.getAllowSearch()));
		params.put("AllowHeadImage", HtmlUtil.mapxToRadios("AllowHeadImage", map, userGroup.getAllowHeadImage()));
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map, userGroup.getAllowUserInfo()));
		params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName", map, userGroup.getAllowNickName()));
		String sql = "select a.Name,b.GroupID from ZCForumGroup a,ZCAdminGroup b where a.SiteID=? and a.ID=b.GroupID and a.type='2' and a.SystemName<>'系统管理员'";
		DataTable dt = new QueryBuilder(sql, SiteID).executeDataTable();
		params.put("AdminGroup", HtmlUtil.dataTableToOptions(dt, userGroup.getRadminID() + ""));
		return params;
	}

	public void editSpecialSave() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(Request);
		ZCAdminGroupSchema newGroup = new ZCAdminGroupSchema();
		newGroup.setGroupID(userGroup.getID());
		// 判断是否存在于管理组表
		if (newGroup.fill()) {
			if ($V("RadminID").equals("0")) {
				trans.add(newGroup, Transaction.DELETE_AND_BACKUP);
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID(0);
				}
				trans.add(memberSet, Transaction.UPDATE);
			} else {
				ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
				adminGroup.setGroupID($V("RadminID"));
				adminGroup.fill();
				// 将特殊组关联的管理的设置复制给新加的自定义管理组
				newGroup = adminGroup;
				newGroup.setGroupID(userGroup.getID());
				trans.add(newGroup, Transaction.UPDATE);
				// 将已属于该特殊组的用户附加于更新的关联管理权限
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID($V("RadminID"));
				}
				trans.add(memberSet, Transaction.UPDATE);
			}
		} else {
			if (!$V("RadminID").equals("0")) {
				ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
				adminGroup.setGroupID($V("RadminID"));
				adminGroup.fill();
				// 将特殊组关联的管理的设置复制给新加的自定义管理组
				newGroup = adminGroup;
				newGroup.setGroupID(userGroup.getID());
				trans.add(newGroup, Transaction.INSERT);
				// 将已属于该特殊组的用户附加于更新的关联管理权限
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID($V("RadminID"));
				}
				trans.add(memberSet, Transaction.UPDATE);
			} else {

			}
		}
		trans.add(userGroup, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "设置成功!");
		} else {
			Response.setLogInfo(0, "操作失败！");
		}
	}

}
