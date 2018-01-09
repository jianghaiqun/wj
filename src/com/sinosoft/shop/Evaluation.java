package com.sinosoft.shop;

import java.util.Date;

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
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.ZCCommentSet;


public class Evaluation extends Page {

	public static Mapx init(Mapx params) {
		params.put("VerifyStatusOptions", HtmlUtil.codeToOptions("Comment.Status", false));
		return params;
	}

	public static Mapx initDetail(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(id);
			if (comment.fill()) {
				params.putAll(comment.toMapx());
				params.put("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status").get(params.get("VerifyFlag")));
				String addTimeStr = params.getString("AddTime");
				params.put("AddTime", addTimeStr.substring(0, addTimeStr.lastIndexOf(".")));
			}
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String VerifyStatus = dga.getParam("VerifyStatus");
		String CatalogID = dga.getParam("CatalogID");

		QueryBuilder qb = new QueryBuilder(
				"select ZCComment.*,(select Name from zccatalog where zccatalog.ID = ZCComment.CatalogID) as CatalogName,"
						+ " (select Name from ZSGoods where ZSGoods.ID = ZCComment.RelaID) as GoodsName"
						+ " from ZCComment where SiteID = ? and CatalogType = '9' ");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(VerifyStatus) && !"All".equals(VerifyStatus)) {
			qb.append(" and VerifyFlag = ?", VerifyStatus);
		}
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID = ? ", CatalogID);
		}
		dga.setTotal(qb);
		qb.append(" Order by VerifyFlag asc, ID desc");

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("PreLink");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "CatalogType").equals("9")) {
				dt.set(i, "PreLink", "../Document/Preview.jsp?GoodsID=" + dt.getString(i, "RelaID"));
			} else {
				dt.set(i, "PreLink", "#;");
			}
		}
		dt.decodeColumn("VerifyFlag", new QueryBuilder(
				"select CodeName,CodeValue from ZDCode where CodeType = 'Comment.Status'").executeDataTable().toMapx(1,
				0));
		dga.bindData(dt);
	}

	public void Verify() {
		String ID = $V("ID");
		String Type = $V("Type");
		String IDs = $V("IDs");
		if (StringUtil.isNotEmpty(ID) && StringUtil.isEmpty(IDs)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(ID);
			comment.fill();
			if (Type.equals("Pass")) {
				comment.setVerifyFlag("Y");
			} else if (Type.equals("NoPass")) {
				comment.setVerifyFlag("N");
			}
			comment.setVerifyUser(User.getUserName());
			comment.setVerifyTime(new Date());
			if (comment.update()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		} else if (StringUtil.isNotEmpty(IDs) && StringUtil.isEmpty(ID)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			if (!StringUtil.checkID(IDs)) {// 检查SQL注入
				return;
			}
			ZCCommentSet set = comment.query(new QueryBuilder("where ID in (" + IDs + ")"));
			Transaction trans = new Transaction();
			for (int i = 0; i < set.size(); i++) {
				comment = set.get(i);
				if (Type.equals("Pass")) {
					comment.setVerifyFlag("Y");
				} else if (Type.equals("NoPass")) {
					comment.setVerifyFlag("N");
				}
				comment.setVerifyUser(User.getUserName());
				comment.setVerifyTime(new Date());
				trans.add(comment, Transaction.UPDATE);
			}
			if (trans.commit()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();
		ZCCommentSet set = task.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除评论成功");
		} else {
			Response.setLogInfo(0, "删除评论失败");
		}
	}

	public void addSupporterCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getSupporterCount());
			return;
		}

		long count = task.getSupporterCount();

		task.setSupporterCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}
	}

	public void addAntiCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getAntiCount());
			return;
		}
		long count = task.getAntiCount();

		task.setAntiCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}

	}
}
