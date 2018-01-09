package com.sinosoft.bbs.admin;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZCForumMemberSet;
import com.sinosoft.schema.ZCForumSchema;
import com.sinosoft.schema.ZCForumSet;
import com.sinosoft.schema.ZCPostSchema;
import com.sinosoft.schema.ZCPostSet;
import com.sinosoft.schema.ZCThemeSchema;
import com.sinosoft.schema.ZCThemeSet;

import java.util.Date;

public class PostAudit extends Page {
	ForumScore forumScore = new ForumScore();

	public static Mapx init(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder("select Name, ID from ZCForum where SiteID=? and ParentID<>0", SiteID)
				.executeDataTable();
		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	/**
	 * 显示未审核回复列表
	 * 
	 * @param dla
	 */
	public static void getUnauditedPost(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String auditFlag = dla.getParams().getString("auditFlag");
		String forumID = dla.getParams().getString("ForumID");
		String typeID = dla.getParams().getString("TypeID");
		String first = dla.getParams().getString("First");

		QueryBuilder qb = new QueryBuilder("select p.*, f.Name ForumName, '' as AuditFlag "
				+ "from ZCPost p, ZCForum f where p.SiteID=?  and p.Invisible='Y' and p.ForumID=f.ID ", SiteID);

		if (StringUtil.isNotEmpty(typeID)) {
			qb.append(" and p.VerifyFlag=?", typeID);
		} else {
			qb.append(" and p.VerifyFlag='N'");
		}

		if (StringUtil.isNotEmpty(forumID) && !forumID.equals("0")) {
			if (!StringUtil.checkID(forumID)) {
				logger.warn("可能的SQL注入,PostAudit.getUnauditedPost:{}", forumID);
				return;
			}
			qb.append(" and p.ForumID in (" + forumID + ")");
		}

		if (StringUtil.isNotEmpty(first)) {
			qb.append(" and p.first=?", first);
		} else {
			qb.append(" and p.first='Y'");
		}
		int pageSize = dla.getPageSize();
		int pageIndex = dla.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("StatusCount");
		for (int i = 0; i < dt.getRowCount(); i++) {
			Mapx map = new Mapx();
			map.put("Y", "通过");
			map.put("N", "删除");
			map.put("X", "忽略");
			if (StringUtil.isNotEmpty(auditFlag)) {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, auditFlag, true));
			} else {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, "Y", true));
			}
			dt.set(i, "StatusCount", i);
		}

		dla.setTotal(qb);
		dla.bindData(dt);
	}

	/**
	 * 显示用户申请删除回复列表
	 * 
	 * @param dla
	 */
	public static void getApplyDelPost(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String auditFlag = dla.getParams().getString("auditFlag");
		String forumID = dla.getParams().getString("ForumID");
		String typeID = dla.getParams().getString("TypeID");
		String first = dla.getParams().getString("First");

		QueryBuilder qb = new QueryBuilder("select p.*, f.Name ForumName, '' as AuditFlag "
				+ "from ZCPost p, ZCForum f where p.SiteID=? and p.Invisible='Y' and p.ForumID=f.ID ", SiteID);

		if (StringUtil.isNotEmpty(typeID)) {
			qb.append(" and p.ApplyDel=?", typeID);
		} else {
			qb.append(" and p.ApplyDel='Y'");
		}
		if (StringUtil.isNotEmpty(forumID) && !forumID.trim().equals("0")) {
			qb.append(" and ForumID=?", forumID);
		}
		if (StringUtil.isNotEmpty(first)) {
			qb.append(" and p.first=?", first);
		} else {
			qb.append(" and p.first='Y'");
		}
		int pageSize = dla.getPageSize();
		int pageIndex = dla.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("StatusCount");
		for (int i = 0; i < dt.getRowCount(); i++) {
			Mapx map = new Mapx();
			map.put("N", "申请通过");
			map.put("Z", "拒绝申请");
			map.put("X", "暂时忽略");
			if (StringUtil.isNotEmpty(auditFlag)) {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, auditFlag, true));
			} else {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, "N", true));
			}
			dt.set(i, "StatusCount", i);

		}

		dla.setTotal(qb);
		dla.bindData(dt);
	}

	/**
	 * 执行审核(通过/删除/忽略)
	 * 
	 */
	public void exeAudit() {
		if (!StringUtil.checkID($V("ids"))) {
			logger.warn("可能的SQL注入,ForumUtil.exeAudit:{}", $V("ids"));
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where SiteID=? and id in(" + $V("ids") + ")",
				SiteID));
		ZCThemeSet themeSet = new ZCThemeSet();
		ZCForumSet forumSet = new ZCForumSet();

		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Mapx map = new Mapx();

		for (int i = 0; i < postSet.size(); i++) {
			ZCPostSchema post = postSet.get(i);
			ZCThemeSchema theme = new ZCThemeSchema();
			theme.setID(post.getThemeID());
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(post.getForumID());
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(post.getAddUser());
			int indexTheme = ForumUtil.getValueOfThemeSet(themeSet, theme);
			int indexForum = ForumUtil.getValueOfForumSet(forumSet, forum);
			int indexMember = ForumUtil.getValueOfMemberSet(userSet, user);
			String newFlag = $V("AuditFlag_" + post.getID());
			if (map.get("" + post.getThemeID()) == null) {
				long layer = getMAXLayer(post.getThemeID()) + 1;
				map.put("" + post.getThemeID(), layer);
			}
			if (StringUtil.isNotEmpty(newFlag)) {
				if (newFlag.equals("Y")) {
					post.setVerifyFlag("Y");

					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("Y");
						theme.setOrderTime(new Date());
						themeSet.add(theme);
						// 如果该发帖人已经存在于set中，则把数据更新到set，最后一起提交
						if (indexMember == -1) {
							user.fill();
							user.setThemeCount(user.getThemeCount() + 1);
							user.setForumScore(user.getForumScore() + forumScore.PublishTheme);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setThemeCount(userSet.get(indexMember).getThemeCount() + 1);
							userSet.get(indexMember).setForumScore(
									userSet.get(indexMember).getForumScore() + forumScore.PublishTheme);
						}
						// 如果该帖子属于的板块已经存在于set中，则把数据更新到set，最后一起提交
						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forum.setThemeCount(forum.getThemeCount() + 1);
							forum.setLastPost(post.getSubject());
							forum.setLastPoster(post.getAddUser());
							forum.setLastThemeID(theme.getID());
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setPostCount(forumSet.get(indexForum).getPostCount() + 1);
							forumSet.get(indexForum).setThemeCount(forumSet.get(indexForum).getThemeCount() + 1);
							forumSet.get(indexForum).setLastPost(post.getSubject());
							forumSet.get(indexForum).setLastPoster(post.getAddUser());
							forumSet.get(indexForum).setLastThemeID(theme.getID());
						}

					} else {
						post.setLayer(map.getLong("" + post.getThemeID()));
						map.put("" + post.getThemeID(), post.getLayer() + 1);
						if (indexMember == -1) {
							user.fill();
							user.setReplyCount(user.getReplyCount() + 1);
							user.setForumScore(user.getForumScore() + forumScore.PublishPost);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setReplyCount(userSet.get(indexMember).getThemeCount() + 1);
							userSet.get(indexMember).setForumScore(
									userSet.get(indexMember).getForumScore() + forumScore.PublishPost);
						}
						// 如果该帖子属于的板块已经存在于set中，则把数据更新到set，最后一起提交
						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setPostCount(forumSet.get(indexForum).getPostCount() + 1);
						}
						// 如果该帖子属于的主题已经存在于set中，则把数据更新到set，最后一起提交
						if (indexTheme == -1) {
							theme.fill();
							theme.setReplyCount(theme.getReplyCount() + 1);
							themeSet.add(theme);
						} else {
							themeSet.get(indexTheme).setReplyCount(themeSet.get(indexTheme).getReplyCount() + 1);
						}
						theme.setLastPostTime(new Date());
					}
				} else if (newFlag.equals("N")) {
					post.setInvisible("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setStatus("N");
						themeSet.add(theme);
					}
				} else {
					post.setVerifyFlag("X");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("X");
						themeSet.add(theme);
					}
				}
			}
		}
		trans.add(postSet, Transaction.UPDATE);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(forumSet, Transaction.UPDATE);
		trans.add(themeSet, Transaction.UPDATE);
		if (trans.commit()) {
			for (int i = 0; i < forumSet.size(); i++) {
				CacheManager.set("Forum", "Forum", forumSet.get(i).getID(), forumSet.get(i));
			}
			Response.setLogInfo(1, "操作成功");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	public void exeApplyDel() {
		if (!StringUtil.checkID($V("ids"))) {
			logger.warn("可能的SQL注入,ForumUtil.exeApplyDel:{}", $V("ids"));
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where SiteID=? and id in(" + $V("ids") + ")",
				SiteID));
		ZCThemeSet themeSet = new ZCThemeSet();
		ZCForumSet forumSet = new ZCForumSet();
		ZCForumMemberSet userSet = new ZCForumMemberSet();

		for (int i = 0; i < postSet.size(); i++) {
			ZCPostSchema post = postSet.get(i);
			ZCThemeSchema theme = new ZCThemeSchema();
			theme.setID(post.getThemeID());
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(post.getForumID());
			forum.fill();
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(post.getAddUser());
			int indexTheme = ForumUtil.getValueOfThemeSet(themeSet, theme);
			int indexForum = ForumUtil.getValueOfForumSet(forumSet, forum);
			int indexMember = ForumUtil.getValueOfMemberSet(userSet, user);
			String newFlag = $V("AuditFlag_" + post.getID());
			if (StringUtil.isNotEmpty(newFlag)) {
				if (newFlag.equals("N")) {
					post.setInvisible("N");
					// 如果删除的是主题，flag为true
					QueryBuilder qb = new QueryBuilder();
					if (post.getFirst().equals("Y")) {
						qb.append("update ZCpost set Invisible='N' where ThemeID=?", post.getThemeID());
						theme.setStatus("N");
						if (indexForum == -1) {
							forum.setThemeCount(forum.getThemeCount() - 1);
							forum.setPostCount(forum.getPostCount() - theme.getReplyCount() - 1);
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setThemeCount(forumSet.get(indexForum).getThemeCount() - 1);
							forumSet.get(indexForum).setPostCount(
									forumSet.get(indexForum).getPostCount() - theme.getReplyCount() - 1);
						}
						theme.fill();
						theme.setStatus("N");
						themeSet.add(theme);
						if (indexMember == -1) {
							user.fill();
							user.setThemeCount(user.getThemeCount() - 1);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setThemeCount(userSet.get(indexMember).getThemeCount() - 1);
						}
						int count = new QueryBuilder(
								"select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID=?"
										+ " and ID>?", forum.getID(), theme.getID()).executeInt();
						if (count == 0) {
							DataTable dt = new QueryBuilder(
									"select Subject,AddUser from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID=?"
											+ " and ID<?", forum.getID(), theme.getID() + " order by ID desc")
									.executePagedDataTable(1, 0);
							if (dt.getRowCount() > 0) {
								forum.setLastPost(dt.getString(0, "Subject"));
								forum.setLastPoster(dt.getString(0, "AddUser"));
							} else {
								forum.setLastPost("");
								forum.setLastPoster("");
							}
						}
					} else {
						qb.append("update ZCpost set layer=layer-1 where layer>", post.getLayer());
						if (indexForum == -1) {
							forum.setPostCount(forum.getPostCount() - 1);
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setPostCount(forumSet.get(indexForum).getPostCount() - 1);
						}
						if (indexTheme == -1) {
							theme.fill();

							theme.setReplyCount(theme.getReplyCount() - 1);
						} else {
							themeSet.get(indexTheme).setReplyCount(themeSet.get(indexTheme).getReplyCount() - 1);
						}
						if (indexMember == -1) {
							user.fill();
							user.setReplyCount(user.getReplyCount() - 1);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setReplyCount(userSet.get(indexMember).getReplyCount() - 1);
						}
					}

					trans.add(qb);
				} else if (newFlag.equals("Z")) {
					post.setApplyDel("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setApplydel("N");
						themeSet.add(theme);
					}
				} else {
					post.setApplyDel("X");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setApplydel("X");
						themeSet.add(theme);
					}
				}
			}
		}
		trans.add(postSet, Transaction.UPDATE);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(forumSet, Transaction.UPDATE);
		trans.add(themeSet, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "操作成功");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	/**
	 * 得到相应主题的最大楼层数
	 * 
	 * @param ThemeID
	 * @return
	 */
	private long getMAXLayer(long ThemeID) {
		long layer = new QueryBuilder("select Layer from ZCPost where Invisible='Y' and ThemeID=?"
				+ " order by Layer desc", ThemeID).executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	public void del(Transaction trans, ZCPostSchema post) {
		post.setInvisible("N");
		trans.add(post, Transaction.UPDATE);
		// 如果删除的是主题，flag为true
		boolean flag = post.getFirst().equals("Y") ? true : false;
		QueryBuilder qb = new QueryBuilder();
		if (flag) {
			qb.append("update ZCpost set Invisible='N' where ThemeID=?", post.getThemeID());
		} else {
			qb.append("update ZCpost set layer=layer-1 where layer>?", post.getLayer());
		}

		trans.add(qb);
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(post.getThemeID());
		theme.fill();
		if (flag) {
			theme.setStatus("N");
		} else {
			theme.setReplyCount(theme.getReplyCount() - 1);
		}
		trans.add(theme, Transaction.UPDATE);
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(theme.getForumID());
		forum.fill();
		if (flag) {
			// 如果删除的是主题，先判断是不是最后发帖，然后根据判断更改板块表中的最后发帖信息
			forum.setThemeCount(forum.getThemeCount() - 1);
			forum.setPostCount(forum.getPostCount() - theme.getReplyCount());
			int count = new QueryBuilder(
					"select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID=?" + " and ID>?",
					forum.getID(), theme.getID()).executeInt();
			if (count == 0) {
				DataTable dt = new QueryBuilder(
						"select Subject,AddUser from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID=?"
								+ " and ID<?", forum.getID(), theme.getID() + " order by ID desc")
						.executePagedDataTable(1, 0);
				if (dt.getRowCount() > 0) {
					forum.setLastPost(dt.getString(0, "Subject"));
					forum.setLastPoster(dt.getString(0, "AddUser"));
				} else {
					forum.setLastPost("");
					forum.setLastPoster("");
				}
			}
		}
		forum.setPostCount(forum.getPostCount() - 1);
		trans.add(forum, Transaction.UPDATE);
		ZCForumMemberSchema user = new ZCForumMemberSchema();
		user.setUserName(post.getAddUser());
		user.fill();
		// 根据删除的主题或者回复，论坛用户信息随之需要更改
		if (flag) {
			user.setThemeCount(user.getThemeCount() - 1);
		} else {
			user.setReplyCount(user.getReplyCount() - 1);
		}
		trans.add(user, Transaction.UPDATE);

	}
}
