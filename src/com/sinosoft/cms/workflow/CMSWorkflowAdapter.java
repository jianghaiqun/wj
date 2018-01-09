package com.sinosoft.cms.workflow;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.document.MessageCache;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mail;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.BZCArticleSchema;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZDUserSchema;
import com.sinosoft.workflow.Context;
import com.sinosoft.workflow.Workflow;
import com.sinosoft.workflow.WorkflowAction;
import com.sinosoft.workflow.WorkflowAdapter;
import com.sinosoft.workflow.WorkflowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
public class CMSWorkflowAdapter extends WorkflowAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CMSWorkflowAdapter.class);
	public void onActionExecute(Context context, WorkflowAction action) {
		// 处理历史
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail(action.getName());
		//2011-1-14 处理历史显示真实姓名
		articleLog.setAddUser(User.getRealName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, Transaction.INSERT);
	}

	public void onStepCreate(Context context) {
		// 备份数据
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(context.getInstance().getDataID());
		if (!article.fill()) {
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		for (int i = 0; i < article.getColumnCount(); i++) {
			barticle.setV(i, article.getV(i));
		}
		barticle.setBackupMemo("流程备份");
		barticle.setBackupNo(SchemaUtil.getBackupNo());
		barticle.setBackupOperator(User.getUserName());
		barticle.setBackupTime(new Date());

		Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
		String nodeName = wf.findNode(context.getStep().getNodeID()).getName();
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());

		// 处理历史
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail("文档流转到步骤:" + nodeName);
		//2011-1-14 处理历史显示真实姓名
		articleLog.setAddUser(User.getRealName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, Transaction.INSERT);

		// 流程消息
		StringFormat sf = new StringFormat("标题为 ? 的文档己流转到步骤 ? 。");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");
		String subject = sf.toString();
		
		String username = context.getStep().getAddUser();
		ZDUserSchema theuser = new ZDUserSchema().query(new QueryBuilder("where username=?", username)).get(0);
		String realName = theuser.getRealName();

		sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? 。");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
		//sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
		sf.add("<font class='deepred'>" + realName + "</font>");
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");

		MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), article.getAddUser(), "SYSTEM");

		context.getStep().setDataVersionID(barticle.getBackupNo());

		context.getTransaction().add(barticle, Transaction.INSERT);
	}

	public void notifyNextStep(Context context, String[] users) {
		Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
		String stepName = wf.findNode(context.getStep().getNodeID()).getName();
		StringFormat sf = new StringFormat("有流转到步骤 ?的文档待处理。");
		sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");
		String subject = sf.toString();
		
		String username = context.getStep().getAddUser();
		ZDUserSchema theuser = new ZDUserSchema().query(new QueryBuilder("where username=?", username)).get(0);
		String realName = theuser.getRealName();
		
		sf = new StringFormat("标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? ,您可以开始处理。");
		sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
//		sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
		sf.add("<font class='deepred'>" + realName + "</font>");
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");

		StringFormat mailSubjectSF = new StringFormat("文档?流转到?,请您处理");
		mailSubjectSF.add(context.getValue("Title"));
		mailSubjectSF.add(stepName);
		String subjectSubject = mailSubjectSF.toString();

		for (int i = 0; i < users.length; i++) {
			MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), users[i], "SYSTEM");
			Mapx mailMap = new Mapx();

			// 发送邮件
			if ("Y".equals(Config.getValue("Workflow.SendMail"))) {
				String userName = users[i];
				DataTable dt = new QueryBuilder("select Email,RealName from ZDUser where UserName=?", userName)
						.executeDataTable();
				if (dt.getRowCount() > 0) {
					String email = dt.getString(0, "email");
					if (StringUtil.isNotEmpty(email)) {
						mailMap.put("ToUser", email);
						mailMap.put("Subject", subjectSubject);
						mailMap.put("Content", sf.toString());
						String flag = Mail.sendSimpleEmail(mailMap);
						if (Mail.SUCCESS.equals(flag)) {
							logger.info("发送邮件成功：{}", subjectSubject);
						} else {
							logger.info("发送邮件失败：{}" , subjectSubject + " " + flag);
						}
					}
				}
			}
		}
	}

	public void onWorkflowDelete(Transaction tran, long workflowID) {
		// 文章关联的工作流被删除时需要将相关的文章状态置为待发布
		tran.add(new QueryBuilder("update ZCArticle set Status=? where exists (select 1 from ZWInstance "
				+ "where WorkflowID=? and ID=ZCArticle.WorkflowID)", Article.STATUS_TOPUBLISH, workflowID));
		// 相关栏目的工作流设为null
		tran.add(new QueryBuilder("update ZCCatalog set Workflow=? where Workflow=?", null, workflowID+""));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.workflow.WorkflowAdapter#getVariables(java.lang.String,
	 * java.lang.String)
	 */
	public Mapx getVariables(String dataID, String dataVersionID) {
		// 对于CMS而言，不需要dataVersionID，仅取ZCArticle中的记录
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(dataID);
		article.fill();
		Mapx map = article.toCaseIgnoreMapx();// 忽略大小写
		DataTable dt = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, dataID);
		if (dt != null && dt.getRowCount() > 0) {
			map.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.workflow.WorkflowAdapter#saveVariables(com.sinosoft.workflow.Context
	 * )
	 */
	public boolean saveVariables(Context context) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setValue(context.getVariables());
		article.fill();
		Article.saveCustomColumn(context.getTransaction(), context.getVariables(), article.getCatalogID(), article
				.getID(), false);
		context.getTransaction().add(article, Transaction.UPDATE);
		return true;
	}

}
