package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.workflow.WorkflowStep;
import com.sinosoft.workflow.WorkflowUtil;

import java.util.List;

/**
 * 工作流任务
 * 
 */
public class WorkList extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = null;
		DataTable dt = null;
		String listType = dga.getParam("Type");
		if (UserList.ADMINISTRATOR.equals(User.getUserName()) && StringUtil.isEmpty(listType)) {
			listType = "ALL";// 管理员默认是所有流转中的文档
		}
		if (StringUtil.isEmpty(listType) || "TOME".equals(listType)) {
			// 待我处理的文档
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.author,a.addtime,a.status,"
							+ " b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName"
							+ " from ZCArticle a,ZWStep b,ZWInstance c");
			if (Config.isSybase()) {
				qb.append(" where convert(varchar,a.ID)=c.DataID");
			} else {
				qb.append(" where a.ID=c.DataID");
			}
			qb.append(" and b.InstanceID=c.ID and a.siteID=? and a.status = " + Article.STATUS_WORKFLOW);
			qb.append(" and (b.State=? or (b.State=? and Owner=?))", Application.getCurrentSiteID());
			qb.add(WorkflowStep.UNREAD);
			qb.add(WorkflowStep.UNDERWAY);
			qb.add(User.getUserName());

			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executeDataTable();

			final List roleList = PubFun.getRoleCodesByUserName(User.getUserName());
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("cataloginnercode"),
							Priv.ARTICLE_BROWSE)
							|| !Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("cataloginnercode"),
									Priv.ARTICLE_AUDIT)) {// 必须要有审核权限
						return false;
					}
					String state = dr.getString("State");
					String allowUser = "," + dr.getString("AllowUser") + ",";
					String allowRole = "," + dr.getString("AllowRole") + ",";
					String allowOrgan = "," + dr.getString("AllowOrgan") + ",";
					if (WorkflowStep.UNREAD.equals(state)) {
						dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
								.getInt("ActionID")));
						dr.set("StepName", WorkflowUtil
								.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
						dr.set("StateName", "未读");
						if (allowUser.indexOf("," + User.getUserName() + ",") >= 0) {
							return true;
						}
						if (StringUtil.isNotEmpty(dr.getString("AllowOrgan"))
								&& allowOrgan.indexOf("," + User.getBranchInnerCode() + ",") != 0) {
							return false;
						}
						String[] roles = allowRole.split(",");
						for (int i = 0; i < roles.length; i++) {
							if (roleList.contains(roles[i])) {
								return true;
							}
						}
						return false;
					} else {
						if (dr.getInt("ActionID") != 0) {
							dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
									.getInt("ActionID")));
						} else {
							dr.set("ActionName", "新建");
						}
						dr.set("StepName", WorkflowUtil
								.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
						dr.set("StateName", "处理中");
					}
					return true;
				}
			});
			DataTable newdt = new DataTable(dt.getDataColumns(), null);
			for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt.getRowCount()
					&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
				newdt.insertRow(dt.getDataRow(i));
			}
			newdt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(dt.getRowCount());
			dga.bindData(newdt);
		} else if ("ALL".equals(listType)) {
			// 所有流转中的文档,只有管理员能看到
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.author,a.addtime,a.status,"
							+ " b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName"
							+ " from ZCArticle a,ZWStep b,ZWInstance c");
			if (Config.isSybase()) {
				qb.append(" where convert(varchar,a.ID)=c.DataID");
			} else {
				qb.append(" where a.ID=c.DataID");
			}
			qb.append(" and b.InstanceID=c.ID and a.siteID=? and a.status=" + Article.STATUS_WORKFLOW);
			qb.append(" and b.State in('" + WorkflowStep.UNREAD + "','" + WorkflowStep.UNDERWAY + "')", Application
					.getCurrentSiteID());
			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				if (dr.getInt("ActionID") != 0) {
					dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
							.getInt("ActionID")));
				} else {
					dr.set("ActionName", "新建");
				}
				dr.set("StepName", WorkflowUtil.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
				String state = dr.getString("State");
				if (WorkflowStep.UNREAD.equals(state)) {
					dr.set("StateName", "未读");
				} else {
					dr.set("StateName", "处理中");
				}
				if (dr.getString("State").equals(WorkflowStep.UNREAD)) {
					// 如果是未读，且有allowUser，则将处理人置为AllowUser，在会签时有用
					String allowUser = dr.getString("AllowUser");
					String allowOrgan = dr.getString("AllowOrgan");
					String allowRole = dr.getString("AllowRole");
					if (StringUtil.isEmpty(allowRole) && StringUtil.isEmpty(allowOrgan)) {
						if (StringUtil.isNotEmpty(allowUser) && allowUser.indexOf(",") < 0) {
							dr.set("Owner", allowUser);
						}
					}
				}
			}
			dga.bindData(dt);
		} else if ("HANDLED".equals(listType)) {
			// 我处理过的文档
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.title,a.workflowid,a.author,a.addtime,a.status,"
							+ " (select workflowid from zwinstance  where zwinstance.id = a.workflowid) as workflowconfigid"
							+ " from ZCArticle a where exists (select '' from zwstep s where s.instanceid=a.workflowid and s.owner=?)"
							+ " and a.siteID=? and a.status<>" + Article.STATUS_WORKFLOW, User.getUserName(),
					Application.getCurrentSiteID());

			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			dga.bindData(dt);
		}
	}

	public void applyStep() {
		DataTable dt = Request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				// 判断栏目是否是本机构独立管理
				String id = dt.getString(i, "ID");
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(id);
				if (!article.fill()) {
					Response.setStatus(0);
					Response.setMessage("发生错误：未找到对应的文章，ID“" + id);
					return;
				}
				ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
				if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
					String branchInnerCode = article.getBranchInnerCode();
					if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
						Response.setStatus(0);
						Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
						return;
					}
				}

				WorkflowUtil.applyStep(dt.getLong(i, "WorkflowID"), dt.getInt(i, "NodeID"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				failCount++;
				sb.append(failCount + "、" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0) {
			Response.setMessage("申请成功");
		} else {
			Response.setMessage((dt.getRowCount() - failCount) + "个申请成功，" + failCount + "个申请失败!<br><br>" + sb);
		}
	}

	public void forceEnd() {
		DataTable dt = Request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				long flowID = dt.getLong(i, "WorkflowID");
				if (WorkflowUtil.findWorkflow(flowID) == null) {// 没有流程，则直接将文档状态转为待发布
					new QueryBuilder("update ZCArticle set Status=? where ID=?", Article.STATUS_TOPUBLISH, dt.getLong(
							i, "ID")).executeNoQuery();
					sb.append("第" + (i + 1) + "行、未找到工作流定义，己将文件状态转为待发布<br>");
				}
				WorkflowUtil.forceEnd(flowID, dt.getInt(i, "NodeID"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				failCount++;
				sb.append("第" + (i + 1) + "行、" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0) {
			Response.setMessage("强制结束流程成功<br><br>" + sb);
		} else {
			Response.setMessage((dt.getRowCount() - failCount) + "个成功，" + failCount + "个失败!<br><br>" + sb);
		}
	}
}
