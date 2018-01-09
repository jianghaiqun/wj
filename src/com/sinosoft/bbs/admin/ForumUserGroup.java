package com.sinosoft.bbs.admin;

import java.util.Date;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCForumGroupSchema;
import com.sinosoft.schema.ZCForumGroupSet;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZCForumMemberSet;

public class ForumUserGroup extends Page {
	public static final String USERGROUP = "1";

	public static final String SYSTEMGROUP = "2";

	public static final String DEFINEDGROUP = "3";

	public static Mapx init(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String ID = params.getString("GroupID");
		String originalImage = new QueryBuilder("select Image from ZCForumGroup where SiteID=? and ID=?", SiteID, ID).executeString();
		params.put("ID", ID);
		params.put("originalImage", originalImage);
		return params;
	}

	public static void getList(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder("select * from ZCForumGroup where SiteID=? and type='1' order by OrderFlag", SiteID);
		dla.bindData(qb);
	}

	/**
	 * 添加用户组
	 * 
	 */
	public void add() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("1");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		set.sort("OrderFlag");
		int index = -1;
		for (int i = 0; i < set.size(); i++) {
			if (Long.parseLong($V("LowerScore")) < set.get(i).getLowerScore()) {
				set.get(i).setOrderFlag(set.get(i).getOrderFlag() + 1);
				if (i == set.size() - 1) {
					userGroup.setOrderFlag(set.get(i).getOrderFlag() - 1);
					index = i;
				}
			} else if (Long.parseLong($V("LowerScore")) == set.get(i).getLowerScore()) {
				Response.setLogInfo(1, "最少到达积分已存在");
				return;
			} else {
				if (i != 0) {
					index = i - 1;
				}
				set.get(i).setUpperScore($V("LowerScore"));
				userGroup.setOrderFlag(set.get(i).getOrderFlag() + 1);
				break;
			}
		}
		trans.add(set, Transaction.UPDATE);
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setValue(Request);
		if (set.size() == 0) {
			// userGroup.setLowerScore(-99999);
			userGroup.setOrderFlag(1);
		}
		if (index == -1) {
			userGroup.setUpperScore(999999);
		} else {
			userGroup.setUpperScore(set.get(index).getLowerScore());
		}
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		userGroup.setAllowAutograph("Y");
		userGroup.setAllowFace("Y");
		userGroup.setAllowHeadImage("N");
		userGroup.setAllowNickName("Y");
		userGroup.setAllowPanel("Y");
		userGroup.setAllowReply("Y");
		userGroup.setAllowSearch("Y");
		userGroup.setAllowTheme("Y");
		userGroup.setAllowUserInfo("Y");
		userGroup.setAllowVisit("Y");
		userGroup.setImage("../Images/SystemHeadImage/HeadImage01.gif");
		userGroup.setAddUser(User.getUserName());
		userGroup.setAddTime(new Date());
		userGroup.setVerify("N");
		trans.add(userGroup, Transaction.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void del() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		// 排序在被删除的组织后的所有组的排序字段都提前一个
		ZCForumGroupSet set = userGroup.query(new QueryBuilder("where SiteID=? and OrderFlag> ? ", SiteID, userGroup.getOrderFlag()));
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setOrderFlag(set.get(i).getOrderFlag() - 1);
		}
		trans.add(set, Transaction.UPDATE);
		// 将删除的最高分赋给他前一个的最高分
		DataTable dt = new QueryBuilder("select ID from ZCForumGroup where SiteID=? and UpperScore=?", SiteID, userGroup.getLowerScore())
				.executeDataTable();
		ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?" + " and UserGroupID=?", SiteID,
				userGroup.getID()));
		if (dt.getRowCount() > 0) {
			trans.add(new QueryBuilder(
					"update ZCForumGroup set UpperScore=" + userGroup.getUpperScore() + " where " + " SiteID=? and ID=?", SiteID, dt
							.getString(0, "ID")));
			// 如果会员用户组是被删除的组 则更新会员用户组为前一个组
			for (int i = 0; i < memberSet.size(); i++) {
				memberSet.get(i).setUserGroupID(dt.getString(0, "ID"));
			}
		} else {
			DataTable dtNext = new QueryBuilder("select ID from ZCForumGroup where SiteID=? and LowerScore=?", SiteID, userGroup
					.getUpperScore()).executeDataTable();
			trans.add(new QueryBuilder("update ZCForumGroup set LowerScore=-99999 where SiteID=? and ID=?", SiteID, dtNext
					.getString(0, "ID")));
			// 如果会员用户组是被删除的组，而且被删除的是第一个组，则更新会员用户组为下一个组
			if (dtNext.getRowCount() > 0) {
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setUserGroupID(dtNext.getString(0, "ID"));
				}
			} else {
				Response.setLogInfo(0, "您至少要留下一个用户组");
				return;
			}
		}
		trans.add(userGroup, Transaction.DELETE_AND_BACKUP);
		trans.add(memberSet, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set.get(i));
			}
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void save() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("1");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			String LowerScore = $V("LowerScore_" + set.get(i).getID()).trim();
			String Color = $V("Color_" + set.get(i).getID()).trim();
			set.get(i).setLowerScore(LowerScore);
			set.get(i).setColor(Color);
		}
		set.sort("LowerScore", "asc");
		for (int i = 0; i < set.size(); i++) {
			if (i > 0 && set.get(i).getLowerScore() == set.get(i - 1).getLowerScore()) {
				Response.setLogInfo(0, "最少到达积分不能相同");
				return;
			}
			set.get(i).setOrderFlag(i + 1);
			if (i == set.size() - 1) {
				set.get(i).setUpperScore(99999);
				break;
			}
			set.get(i).setUpperScore(set.get(i + 1).getLowerScore());
		}
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "更新成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public static void getListSystemGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder("select * from ZCForumGroup where SiteID=? and type='2'", SiteID);
		dla.bindData(qb);
	}

	public static void getListSpecialGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder("select * from ZCForumGroup where SiteID=? and type='3'", SiteID);
		dla.bindData(qb);
	}

	public void addSpecialGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setType("3");
		userGroup.setValue(Request);
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		userGroup.setRadminID(0);
		userGroup.setAllowAutograph("Y");
		userGroup.setAllowFace("Y");
		userGroup.setAllowHeadImage("N");
		userGroup.setAllowNickName("Y");
		userGroup.setAllowPanel("Y");
		userGroup.setAllowReply("Y");
		userGroup.setAllowSearch("Y");
		userGroup.setAllowTheme("Y");
		userGroup.setAllowUserInfo("Y");
		userGroup.setAllowVisit("Y");
		userGroup.setVerify("N");
		userGroup.setImage("../Images/SystemHeadImage/HeadImage01.gif");
		userGroup.setAddUser(User.getUserName());
		userGroup.setAddTime(new Date());
		trans.add(userGroup, Transaction.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void imageSave() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setImage($V("select"));

		trans.add(userGroup, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void editSpecialGroup() {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("3");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setColor($V("Color_" + set.get(i).getID()));
		}
		if (set.update()) {
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set.get(i));
			}
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void editSystemGroup() {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("2");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setColor($V("Color_" + set.get(i).getID()));
		}
		if (set.update()) {
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set.get(i));
			}
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void delSpecailGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where DefinedID=?", userGroup.getID()));
		for (int i = 0; i < memberSet.size(); i++) {
			memberSet.get(i).setDefinedID(0);
		}
		trans.add(userGroup, Transaction.DELETE_AND_BACKUP);
		trans.add(memberSet, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.remove("Forum", "Group", userGroup.getID());
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}

	}

}
