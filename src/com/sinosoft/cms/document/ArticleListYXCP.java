package com.sinosoft.cms.document;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.site.CatalogYXCP;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.SCFAQSchema;
import com.sinosoft.schema.SCFAQSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.workflow.Workflow;
import com.sinosoft.workflow.Workflow.Node;
import com.sinosoft.workflow.WorkflowAction;
import com.sinosoft.workflow.WorkflowStep;
import com.sinosoft.workflow.WorkflowUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常见问题管理
 * 
 */
public class ArticleListYXCP extends Page {
	public static void magazineListDataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = dga.getParams().getString(
					"Cookie.DocList.LastMagazineCatalog");
			if (StringUtil.isEmpty(catalogID) || "null".equals(catalogID)) {
				catalogID = "0";
			}
			dga.getParams().put("CatalogID", catalogID);
		}
		dg1DataBind(dga);
	}

	public static void dg1DataBind(DataGridAction dga) {
		long catalogID = dga.getParams().getLong("CatalogID");
		if (catalogID == 0) {
			catalogID = dga.getParams().getLong("Cookie.DocList.LastCatalog");
			dga.getParams().put("CatalogID", catalogID);
		}

		if (catalogID != 0) {// 只能是本站点下的栏目
			if (!(Application.getCurrentSiteID() + "").equals(CatalogUtil
					.getSiteID(catalogID))) {
				catalogID = 0;
				dga.getParams().put("CatalogID", catalogID);
			}
		}

		// cookie存的catalogID有可能用户没有权限
		if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE,
				CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_BROWSE)) {
			dga.bindData(new DataTable());
			return;
		}

		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		if (StringUtil.isEmpty(listType)) {
			listType = "ALL";
		}
		String Table = "";
		if ("ARCHIVE".equals(listType)) {// 归档文章
			Table = "BZCArticle";
		} else {
			Table = "ZCArticle";
		}
		/**
		 * 2010-11-2 文档工作台显示的文章列表中，创建者显示用户真实姓名，AddUser-Author QueryBuilder qb =
		 * new QueryBuilder(
		 * "select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
		 * +
		 * "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from "
		 * + Table + " where CatalogID=?");
		 */
		QueryBuilder qb = new QueryBuilder(
				"select ID,Attribute,Title,Author,PublishDate,Addtime,Status,WorkFlowID,Type,"
						+ "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from "
						+ Table + " where CatalogID=?");
		qb.add(catalogID);
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if ("ADD".equals(listType)) { // 添加的文档
			qb.append(" and adduser=?", User.getUserName());
		} else if ("WORKFLOW".equals(listType)) {// 待流转
			qb.append(" and status=?", Article.STATUS_WORKFLOW);
		} else if ("TOPUBLISH".equals(listType)) {// 待发布的文档
			qb.append(" and status=?", Article.STATUS_TOPUBLISH);
		} else if ("PUBLISHED".equals(listType)) {// 已发布的文档
			qb.append(" and status=?", Article.STATUS_PUBLISHED);
		} else if ("OFFLINE".equals(listType)) {// 下线文章
			qb.append(" and status=?", Article.STATUS_OFFLINE);
		} else if ("ARCHIVE".equals(listType)) {// 归档文章
			qb.append(" and BackUpMemo='Archive'");
		}
		qb.append(dga.getSortString());

		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,orderflag desc");
		} else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}
		setDetailWorkflowStatus(dt);// 细化工作流状态

		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		dt.insertColumn("Icon");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1) {
							attributeName = attributeName
									+ attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName
									+ attributemap.getString(array[j]);
						}
					}
					dt.set(i, "Title",
							StringUtil.htmlEncode(dt.getString(i, "Title"))
									+ " <font class='lightred'> ["
									+ attributeName + "]</font>");
				}

				StringBuffer icons = new StringBuffer();

				String topFlag = dt.getString(i, "TopFlag");
				if ("1".equals(topFlag)) {
					String topdate = "永久置顶";
					if (StringUtil.isNotEmpty(dt.getString(i, "TopDate"))) {
						topdate = DateUtil.toString(
								(Date) dt.get(i, "TopDate"),
								DateUtil.Format_DateTime);
					}
					icons.append("<img src='../Icons/icon13_stick.gif' title='有效期限: "
							+ topdate + "'/>");
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferSourceID"))) {
					int referType = dt.getInt(i, "ReferType");
					if (referType == 1) {
						icons.append("<img src='../Icons/icon13_copy.gif' title='复制'/>");
					} else if (referType == 2) {
						icons.append("<img src='../Icons/icon13_refer.gif' title='引用'/>");
					}
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferTarget"))) {
					icons.append("<img src='../Icons/icon13_copyout.gif' title='复制源'/>");
				}

				dt.set(i, "Icon", icons.toString());
			}
		}
		dga.bindData(dt);
	}

	private static void setDetailWorkflowStatus(DataTable dt) {
		Mapx instanceIDMap = new Mapx();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
				instanceIDMap.put(dt.getString(i, "WorkflowID"), "1");
			}
		}
		String ids = StringUtil.join(instanceIDMap.keyArray());
		if (!StringUtil.checkID(ids) || instanceIDMap.size() == 0) {
			return;
		}
		QueryBuilder qb = new QueryBuilder(
				"select WorkflowID,NodeID,InstanceID,ActionID,State from ZWStep where (State=? or State=?) and InstanceID in ("
						+ ids + ") order by ID asc");
		qb.add(WorkflowStep.UNREAD);
		qb.add(WorkflowStep.UNDERWAY);
		DataTable stepTable = qb.executeDataTable();
		Mapx instanceNodeMap = new Mapx();
		Mapx actionMap = new Mapx();
		Mapx stateMap = new Mapx();

		for (int i = 0; i < stepTable.getRowCount(); i++) {
			int flowID = stepTable.getInt(i, "WorkflowID");
			int nodeID = stepTable.getInt(i, "NodeID");
			Node node = WorkflowUtil.findWorkflow(flowID).findNode(nodeID);
			instanceNodeMap.put(stepTable.getString(i, "InstanceID"), node);

			actionMap.put(stepTable.getString(i, "InstanceID"),
					stepTable.getString(i, "ActionID"));
			stateMap.put(stepTable.getString(i, "InstanceID"),
					stepTable.getString(i, "State"));
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
				String instanceID = dt.getString(i, "WorkflowID");
				if (instanceNodeMap.containsKey(instanceID)) {
					Node node = (Node) instanceNodeMap.get(instanceID);
					String nodeName = node.getName();
					String nodeType = node.getType();
					dt.set(i, "StatusName", nodeName);
					if (Workflow.STARTNODE.equals(nodeType)) {
						WorkflowAction action = WorkflowUtil.findAction(node
								.getWorkflow().getID(), actionMap
								.getInt(instanceID));
						if (action != null) {
							dt.set(i, "StatusName", action.getName());
						}
					} else if (WorkflowStep.UNREAD.equals(stateMap
							.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-未读");
					} else if (WorkflowStep.UNDERWAY.equals(stateMap
							.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-处理中");
					}
				}
			}
		}
	}

	public static void dialogDg1DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = "0";
		}
		String keyword = (String) dga.getParams().get("Keyword");
		QueryBuilder qb = new QueryBuilder(
				"select ID,Title,author,publishDate,Addtime,catalogID,topflag,SiteID,FirstPublishDate "
						+ "from ZCArticle where catalogid=?",
				Long.parseLong(catalogID));
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		qb.append(dga.getSortString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		int size = dt.getRowCount();
		String[] columnValue = new String[dt.getRowCount()];
		for (int i = 0; i < size; i++) {
			columnValue[i] = PubFun.getDocURL(dt.get(i));
		}

		dt.insertColumn("Link", columnValue);
		dga.bindData(dt);
	}

	/**
	 * 向外提供显示栏目树
	 * 
	 */
	public static void treeDataBind(TreeAction ta) {
		CatalogYXCP.treeDataBind1(ta);
	}

	public static Mapx init(Mapx params) {
		String tId = (String) params.get("Id");
		if (StringUtil.isNotEmpty(tId)) {
			SCFAQSchema tScfaqSchema = new SCFAQSchema();
			tScfaqSchema.setId(tId);
			if (!tScfaqSchema.fill()) {
				return params;
			}
			params = tScfaqSchema.toMapx();
		}
		return params;
	}

	public void add() {
		Transaction transaction = new Transaction();
		SCFAQSchema tSCFAQSchema = new SCFAQSchema();
		String id = NoUtil.getMaxID("FAQID") + "";
		tSCFAQSchema.setId(id);
		tSCFAQSchema.setValue(this.Request);
		tSCFAQSchema.setAddTime(new Date());
		tSCFAQSchema.setAddUser(User.getUserName());
		tSCFAQSchema.setOrderFlag(OrderUtil.getDefaultOrder()+"");
		transaction.add(tSCFAQSchema, Transaction.INSERT);

		if (!transaction.commit()) {
			Response.setError("操作失败!");
		} else {
			Response.setMessage("操作成功!");
		}
	}

	public void del() {
		String Id = $V("Id");
		Transaction transaction = new Transaction();
		SCFAQSchema tSCFAQSchema = new SCFAQSchema();
		SCFAQSet tSCFAQSet = tSCFAQSchema.query(new QueryBuilder(
				"where Id in (" + Id + ")"));
		transaction.add(tSCFAQSet, Transaction.DELETE);
		if (transaction.commit()) {
			Response.setLogInfo(1, "删除提问成功");
		} else {
			Response.setLogInfo(0, "删除提问失败");
		}
	}

	public static void dg2DataBind(DataGridAction dga) {
		String tRelaId = dga.getParam("RelaId");
		String sql = "select * from SCFAQ where RelaId='" + tRelaId + "' order by orderflag";
		QueryBuilder qb = new QueryBuilder();
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

	public void save() {
		String ID = $V("Id");
		// 获取问题信息
		Transaction transaction = new Transaction();
		SCFAQSchema tSCFAQSchema = new SCFAQSchema();
		tSCFAQSchema.setId(ID);
		tSCFAQSchema.fill();
		tSCFAQSchema.setValue(this.Request);
		tSCFAQSchema.setModifyTime(new Date());
		tSCFAQSchema.setModifyUser(User.getUserName());
		transaction.add(tSCFAQSchema, Transaction.UPDATE);
		if (!transaction.commit()) {
			Response.setError("操作失败!");
		} else {
			Response.setMessage("操作成功!");
		}

	}

	/**
	 * 常见问题生成
	 */
	public void create() {
		String Id = $V("Id");
		SCFAQSchema tSCFAQSchema = new SCFAQSchema();
		SCFAQSet tSCFAQSet = tSCFAQSchema.query(new QueryBuilder(
				"where Id in (" + Id + ") order by orderflag"));
		if (tSCFAQSet.size() > 0) {
			String FaqHtml = "";
//			FaqHtml += "<div class=\"cp_cjwt\">";
//			FaqHtml += "<div class=\"ccp_box_con\">";
//			FaqHtml += "<ul class=\"pl_list_convs\">";
			for (int i = 0; i < tSCFAQSet.size(); i++) {
				ZDColumnValueSchema value = new ZDColumnValueSchema();
				SCFAQSchema tSCFAQSchema1 = tSCFAQSet.get(i);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String temp = formatter.format(new java.util.Date());
				/*产品详细页常见问题样式修改
				FaqHtml += "<dl class=\"problem_box\">";
				FaqHtml += "<dt>";
				FaqHtml += tSCFAQSchema1.getFAQName();
				FaqHtml += "</dt>";
				FaqHtml += "<dd>";
				FaqHtml += tSCFAQSchema1.getFAQContent();
				FaqHtml += "</dd>";
				FaqHtml += "<dd class=\"problem_tiem\">";
				FaqHtml += temp;
				FaqHtml += "</dd>";
				FaqHtml += "</dl>";
				*/
				
//				FaqHtml += "<li><b>";
				FaqHtml += "'";
				FaqHtml += tSCFAQSchema1.getFAQName();
				
				if(i==(tSCFAQSet.size()-1))
				{
					FaqHtml += "'";
				}
				else
				{
					FaqHtml += "',";
				}
//				FaqHtml += "</b><p>";
//				FaqHtml += tSCFAQSchema1.getFAQContent();
//				FaqHtml += "</p></li>";
				
			}
//			FaqHtml += "</ul>";
//			FaqHtml += "</div>";
//			FaqHtml += "</div>";
			String articleId = tSCFAQSet.get(0).getRelaId();
			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			tZCArticleSchema.setID(articleId);
			tZCArticleSchema.fill();
			Transaction trans = new Transaction();
			saveCustomColumn(trans, tZCArticleSchema.getCatalogID(),
					tZCArticleSchema.getID(), false, FaqHtml);
			tZCArticleSchema.getStatus();
			trans.add(tZCArticleSchema, Transaction.UPDATE);
			if(tZCArticleSchema.getStatus()!=0){
				tZCArticleSchema.setStatus("60");
			}
			if (!trans.commit()) {
				Response.setError("操作失败!");
			} else {
				Response.setMessage("操作成功!");
			}
		}

	}

	private void saveCustomColumn(Transaction trans, long catalogID,
			long articleID, boolean newFlag, String html) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue(
					ColumnUtil.RELA_TYPE_DOCID, articleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder(
						"delete from zdcolumnvalue where columncode='FAQ' and relatype=? and relaid = ?",
						ColumnUtil.RELA_TYPE_DOCID, articleID + ""));
			}
			trans.add(getValueFromRequest(catalogID, articleID, html),
					Transaction.INSERT);
		} else {
			trans.add(getValueFromRequest(catalogID, articleID, html),
					Transaction.INSERT);
		}

	}

	private static SchemaSet getValueFromRequest(long catalogID, long docID,
			String html) {
		DataTable dt = ColumnUtil.getColumn(
				ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID);
		ZDColumnValueSet set = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("FAQ".equals(dt.getString(i, "Code"))) {
				ZDColumnValueSchema value = new ZDColumnValueSchema();
				value.setID(NoUtil.getMaxID("ColumnValueID"));
				value.setColumnID(dt.getString(i, "ID"));
				value.setColumnCode(dt.getString(i, "Code"));
				value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
				value.setRelaID(docID + "");
				value.setTextValue(html);
				set.add(value);
			}
		}
		return set;
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE,
					"文章发布失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入IDs参数错误!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(" where id in ("
				+ ids + ")"));

		ZCArticleSet referset = article.query(new QueryBuilder(
				" where refersourceid in (" + ids + ")"));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(),
						Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i)
						.getCatalogID());
				// 如果复制文章目标栏目没有工作流且用户有目标栏目的文章管理权限，则发布时一起更新为发布状态
				if (hasPriv && StringUtil.isEmpty(workflow)) {
					set.add(referset.get(i));
				}
			}
		}
		StringBuffer logs = new StringBuffer("发布文章: ");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, logs
				+ "成功", Request.getClientIP());

		Response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", "" + id);
	}

	private long publishSetTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				setPercent(5);
				p.publishArticle(set, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public void sortArticle1() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCArticleSet set = new ZCArticleSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(Integer.parseInt(dt.getString(i, "ID")));// 根据页面选中的主键ID，从库中选出本条记录
			article.fill();
			article.setOrderFlag(dt.getString(i, "OrderFlag")); // 页面本条记录的修改值填入相应位置
			// article.setValue(dt.getDataRow(i)); //页面传所有值到Schema
			article.setModifyTime(new Date());
			article.setModifyUser(User.getUserName());

			set.add(article);
		}
		if (set.update()) {
			// Type_KeyWordMap.clear();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}

	}
	
	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("SCFAQ", type, target, orders, "1 = 1")) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}

}
