package com.sinosoft.bbs.admin;

import java.util.Date;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCForumSchema;
import com.sinosoft.schema.ZCForumSet;
import com.sinosoft.schema.ZCPostSchema;
import com.sinosoft.schema.ZCPostSet;
import com.sinosoft.schema.ZCThemeSchema;
import com.sinosoft.schema.ZCThemeSet;

public class ForumAdmin extends Page {

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initAddDialog(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?" + " and ParentID=0 order by orderflag", SiteID)
				.executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt1 = new QueryBuilder("select ParentID from ZCForum where SiteID=? and ID=?", ID).executeDataTable();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?" + " and ParentID=0 order by orderflag", SiteID)
				.executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt, dt1.getString(0, "ParentID")));
		return params;
	}

	/**
	 * 显示板块列表
	 */
	public static void dg1DataBind(DataGridAction dga) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select ID,ParentID,Name,Info,Addtime,Type,ForumAdmin,ThemeCount,PostCount,'' as Expand,'' as TreeLevel,'' as EditButton from ZCForum where "
						+ " SiteID=? order by OrderFlag", SiteID);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("group".equals(dt.get(i, "Type"))) {
				dt.set(i, "Expand", "Y");
				dt.set(i, "TreeLevel", "0");
			} else {
				dt.set(i, "Expand", "N");
				dt.set(i, "TreeLevel", "1");
			}
			if (dt.getString(i, "ForumAdmin").endsWith(",")) {
				int index = dt.getString(i, "ForumAdmin").lastIndexOf(",");
				dt.set(i, "ForumAdmin", dt.getString(i, "ForumAdmin").substring(0, index));
			}
		}
		dga.bindData(dt);
	}

	public void add() {
		String name = $V("Name");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setName(name);
		forum.setValue(Request);
		forum.setID(NoUtil.getMaxID("ForumID"));
		if (forum.getParentID() == 0) {
			forum.setType("group");
		} else {
			forum.setType("forum");
		}
		forum.setSiteID(ForumUtil.getCurrentBBSSiteID());
		forum.setStatus("0");
		forum.setThemeCount(0);
		forum.setPostCount(0);
		forum.setTodayPostCount(0);
		forum.setVisible("Y");
		forum.setLocked("N");
		forum.setUnLockID("0");
		forum.setReplyPost("Y");
		forum.setVerify("N");
		forum.setAllowTheme("Y");
		forum.setAnonyPost("N");
		forum.setEditPost("Y");
		forum.setRecycle("N");
		forum.setAllowHTML("N");
		forum.setAllowFace("Y");
		forum.setUnPasswordID("0");

		forum.setOrderFlag(OrderUtil.getDefaultOrder());
		forum.setAddTime(new Date());
		forum.setAddUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(forum, Transaction.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "操作成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	/**
	 * 删除分区或者板块，连同它下面的所有主题和帖子全部删除
	 * 
	 * @author wst
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		// 检查选择分区时有没有没被选入的板块
		ZCForumSchema forumCheck = new ZCForumSchema();
		ZCForumSet set = forumCheck.query(new QueryBuilder("where id in(" + ids + ")"));
		for (int i = 0; i < set.size(); i++) {
			forumCheck = set.get(i);
			if (forumCheck.getParentID() == 0) {
				long count = new QueryBuilder(
						"select count(*) from ZCForum where SiteID=? and ParentID=?" + " and ID not in (" + ids + ")", SiteID, forumCheck
								.getID()).executeLong();
				if (count > 0) {
					Response.setStatus(0);
					Response.setMessage("不能删除分区\"" + forumCheck.getName() + "\",该分区下还有未被选中的板块!");
					return;
				}
			}
		}

		String[] IDs = getForumIDs(ids);
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(IDs[n]);
			forum.fill();
			trans.add(forum, Transaction.DELETE_AND_BACKUP);
			if (forum.getParentID() == 0) {// 如果删除的是一个分区
				// 删除它下面的所有板块
				ZCForumSet forumSet = forum.query(new QueryBuilder("where SiteID=? and ParentID=?", SiteID, forum.getID()));
				trans.add(forumSet, Transaction.DELETE_AND_BACKUP);
				// 删除每个板块下的所有主题
				for (int i = 0; i < forumSet.size(); i++) {
					ZCThemeSchema theme = new ZCThemeSchema();
					theme.setID(forumSet.get(i).getID());
					ZCThemeSet themeSet = theme.query();
					trans.add(themeSet, Transaction.DELETE_AND_BACKUP);
					ZCPostSchema post = new ZCPostSchema();
					// 删除每个主题下的所有帖子
					for (int j = 0; j < themeSet.size(); j++) {
						post.setID(themeSet.get(i).getID());
						ZCPostSet postSet = post.query();
						trans.add(postSet, Transaction.DELETE_AND_BACKUP);
					}
				}
			} else {// 如果删除的是一个板块
				// 删除板块下的所有主题
				ZCThemeSchema theme = new ZCThemeSchema();
				theme.setForumID(forum.getID());
				ZCThemeSet themeSet = theme.query();
				trans.add(themeSet, Transaction.DELETE_AND_BACKUP);
				ZCPostSchema post = new ZCPostSchema();
				// 删除主题下的所有帖子
				for (int i = 0; i < themeSet.size(); i++) {
					post.setThemeID(themeSet.get(i).getID());
					ZCPostSet postSet = post.query();
					trans.add(postSet, Transaction.DELETE_AND_BACKUP);
				}
			}
		}
		if (trans.commit()) {
			for (int n = 0; n < IDs.length; n++) {
				CacheManager.remove("Forum", "Forum", IDs[n]);
			}
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	/**
	 * 保存修改
	 * 
	 */
	public void dg1Edit() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setName($V("Name"));
		// 判断输入的用户名是不是都存在
		if ($V("ForumAdmin").trim().length() == 0 || ForumUtil.isExistMember($V("ForumAdmin"))) {
			if ($V("ForumAdmin").trim().length() == 0 || isExistAdmin(trans, $V("ForumAdmin"), forum)) {
				forum.setForumAdmin($V("ForumAdmin") + ",");
				ForumUtil.addAdminID(trans, forum.getID() + "", forum.getForumAdmin());
			} else {
				Response.setLogInfo(0, "请勿将分区版主重复设置在板块下");
				return;
			}
		} else {
			Response.setLogInfo(0, "输入了不存在的用户名");
			return;
		}

		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "操作成功！");
		} else {
			Response.setLogInfo(0, "操作失败！");
		}
	}

	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setValue(Request);
		if ($V("ForumAdmin").trim().length() == 0 || isExistAdmin(trans, $V("ForumAdmin"), forum)) {
			forum.setForumAdmin($V("ForumAdmin") + ",");
			ForumUtil.addAdminID(trans, forum.getID() + "", forum.getForumAdmin());
		} else {
			Response.setLogInfo(0, "请勿将分区版主重复设置在板块下");
			return;
		}
		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "操作成功！");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}

	public void isGroup() {
		String id = $V("ID");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(id);
		forum.fill();
		if (forum.getParentID() == 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	/**
	 * 通过一个ID集合，当分区ID和此分区下的板块ID都存在时，只提取分区ID，排除板块ID。当分区ID不存在时却存在此分区下的板块ID时提取出板块ID
	 * 
	 * @param ids
	 * @return
	 */
	private String[] getForumIDs(String ids) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return null;
		}
		QueryBuilder qb = new QueryBuilder("select ID from ZCForum where SiteID=? and (ID in (" + ids + ") and ParentID not in (" + ids
				+ ")) or (ID in (" + ids + ") and ParentID=0)", SiteID);
		DataTable dt = qb.executeDataTable();
		String[] IDs = new String[dt.getRowCount()];
		for (int i = 0; i < dt.getRowCount(); i++) {
			IDs[i] = dt.getString(i, "ID");
		}
		return IDs;
	}

	/**
	 * 判断该用户是否已经是分区的版主
	 * 
	 * @param forumAdmins
	 * @param forum
	 * @return
	 */
	private boolean isExistAdmin(Transaction trans, String forumAdmins, ZCForumSchema forum) {
		String[] admins = forumAdmins.split(",");
		if (forum.getParentID() == 0) {
			DataTable dt = new QueryBuilder("select ForumAdmin,ID from ZCForum where ParentID=?", forum.getID()).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < admins.length; j++) {
					String ParentAdmin = dt.getString(i, "ForumAdmin");
					if (ParentAdmin.indexOf(admins[j] + ",") >= 0) {
						ParentAdmin = ParentAdmin.replaceAll(admins[j] + ",", "");
						trans.add(new QueryBuilder("update ZCForum set ForumAdmin=? where ID=?", ParentAdmin, dt.getString(i, "ID")));
					}
				}
			}
		} else {
			QueryBuilder qb = new QueryBuilder("select ForumAdmin from ZCForum where ID=?", forum.getParentID());
			String groupAdmin = qb.executeString();
			if (StringUtil.isEmpty(groupAdmin)) {
				return true;
			}
			for (int i = 0; i < admins.length; i++) {
				if (groupAdmin.indexOf(admins[i] + ",") >= 0) {
					return false;
				}
			}
		}
		return true;
	}

}
