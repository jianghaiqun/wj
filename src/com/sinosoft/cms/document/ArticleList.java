package com.sinosoft.cms.document;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.site.CatalogSite;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.BZCArticleSchema;
import com.sinosoft.schema.BZCArticleSet;
import com.sinosoft.schema.ModuleElementInfoSchema;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleLogSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCAttachmentRelaSchema;
import com.sinosoft.schema.ZCAttachmentRelaSet;
import com.sinosoft.schema.ZCAudioRelaSchema;
import com.sinosoft.schema.ZCAudioRelaSet;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.ZCCommentSet;
import com.sinosoft.schema.ZCImageRelaSchema;
import com.sinosoft.schema.ZCImageRelaSet;
import com.sinosoft.schema.ZCVideoRelaSchema;
import com.sinosoft.schema.ZCVideoRelaSet;
import com.sinosoft.schema.ZCVoteItemSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.utility.DataCache;
import com.sinosoft.workflow.Workflow;
import com.sinosoft.workflow.WorkflowAction;
import com.sinosoft.workflow.WorkflowStep;
import com.sinosoft.workflow.WorkflowUtil;
import com.sinosoft.workflow.Workflow.Node;

/**
 * 文章管理
 * 
 */
public class ArticleList extends Page {
	public static void magazineListDataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = dga.getParams().getString("Cookie.DocList.LastMagazineCatalog");
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
			if (!(Application.getCurrentSiteID() + "").equals(CatalogUtil.getSiteID(catalogID))) {
				catalogID = 0;
				dga.getParams().put("CatalogID", catalogID);
			}
		}

		// cookie存的catalogID有可能用户没有权限
		if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_BROWSE)) {
			dga.bindData(new DataTable());
			return;
		}

		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		String Prop2 = (String) dga.getParams().get("Prop2");
		if (StringUtil.isEmpty(listType)) {
			listType = "ALL";
		}
		String Table = "ZCArticle z1";
		/*if ("ARCHIVE".equals(listType)) {// 归档文章
			Table = "BZCArticle";
		} else {
			Table = "ZCArticle z1";
		}*/
		/**
		 * 2010-11-2 文档工作台显示的文章列表中，创建者显示用户真实姓名，AddUser-Author QueryBuilder qb =
		 * new QueryBuilder(
		 * "select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
		 * +
		 * "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from "
		 * + Table + " where CatalogID=?");
		 */
		QueryBuilder qb = new QueryBuilder("select ID,CatalogInnerCode,Attribute,Title,URL,Author,PublishDate,Addtime,Status,WorkFlowID,Type,"
				+ "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID,FirstPublishDate from " + Table);
		if (StringUtil.isNotEmpty(keyword)) {
			ZCCatalogSchema zcSchema = new ZCCatalogSchema();
			zcSchema.setID(catalogID);
			if (zcSchema.fill() && StringUtil.isNotEmpty(zcSchema.getInnerCode())) {

				qb.append(" where CatalogInnerCode like ? ", zcSchema.getInnerCode() + "%");
				qb.append(" and title like ? ", "%" + keyword.trim() + "%");
			}
		} else {
			qb.append(" where CatalogID=? ");
			qb.add(catalogID);
		}

		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if (!StringUtil.isEmpty(Prop2)) {
			qb.append(" and Attribute like ? ", "%" + Prop2 + "%");
		}

		if ("ADD".equals(listType)) { // 添加的文档
			qb.append(" and z1.adduser=?", User.getUserName());
		} else if ("TOPUBLISH".equals(listType)) {// 待发布的文档
			qb.append(" and status=?", Article.STATUS_TOPUBLISH);
		} else if ("PUBLISHED".equals(listType)) {// 已发布的文档
			qb.append(" and status=?", Article.STATUS_PUBLISHED);
		} else if ("OFFLINE".equals(listType)) {// 下线文章
			qb.append(" and status=?", Article.STATUS_OFFLINE);
		} else if ("EDITING".equals(listType)) {// 重新编辑
			qb.append(" and status=?", Article.STATUS_EDITING);
		}else if ("DRAFT".equals(listType)) {// 初稿
			qb.append(" and status=?", Article.STATUS_DRAFT);
		}
		
		/*else if ("WORKFLOW".equals(listType)) {// 待流转
			qb.append(" and status=?", Article.STATUS_WORKFLOW);
		} else if ("ARCHIVE".equals(listType)) {// 归档文章
			qb.append(" and BackUpMemo='Archive'");
		} else if ("ONPUBLISH".equals(listType)) {// 发布中
			qb.append(" and status=?", Article.STATUS_ONPUBLISH);
		}*/
		qb.append(dga.getSortString());

		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,orderflag desc");
		} else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");

		}
		String url = new QueryBuilder("select URL from ZCsite").executeOneValue() + "/";
		dt.insertColumn("HeadURL");
		if (StringUtil.isNotEmpty(url)) {

			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "HeadURL", url);
			}
		}
		StringBuffer Lanmu = null;
		dt.insertColumn("LanMu");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String[] Innercodes = dt.getString(i, "CatalogInnerCode").split(",");
			for (int j = 0; j < Innercodes.length; j++) {
				String Innercode = Innercodes[j];
				if (StringUtil.isNotEmpty(Innercode)) {
					Lanmu = new StringBuffer();
					int index = 6;
					for (int u = 1; u <= Innercode.length() / 6; u++) {
						QueryBuilder qblevel2 = new QueryBuilder("select name from zccatalog where InnerCode =?");
						qblevel2.add(Innercode.substring(0, index));
						if (u == Innercode.length() / 6) {
							Lanmu.append(qblevel2.executeOneValue());
						} else {
							Lanmu.append((qblevel2.executeOneValue() + "-"));
						}

						index = index + 6;
					}
				}
			}
			dt.set(i, "LanMu", Lanmu.toString());
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
							attributeName = attributeName + attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName + attributemap.getString(array[j]);
						}
					}
					dt.set(i, "Title", StringUtil.htmlEncode(dt.getString(i, "Title")) + " <font class='lightred'> [" + attributeName + "]</font>");
				}

				StringBuffer icons = new StringBuffer();

				String topFlag = dt.getString(i, "TopFlag");
				if ("1".equals(topFlag)) {
					String topdate = "永久置顶";
					if (StringUtil.isNotEmpty(dt.getString(i, "TopDate"))) {
						topdate = DateUtil.toString((Date) dt.get(i, "TopDate"), DateUtil.Format_DateTime);
					}
					icons.append("<img src='../Icons/icon13_stick.gif' title='有效期限: " + topdate + "'/>");
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

	public static void dg2DataBind(DataGridAction dga) {
		String catalog = (String) dga.getParams().get("Catalogs");
		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String aothor = (String) dga.getParams().get("aothor");
		String listType = (String) dga.getParams().get("Type");
		if (StringUtil.isEmpty(listType)) {
			listType = "PUBLISHED";
		}

		/**
		 * 2010-11-2 文档工作台显示的文章列表中，创建者显示用户真实姓名，AddUser-Author QueryBuilder qb =
		 * new QueryBuilder(
		 * "select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
		 * +
		 * "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from "
		 * + Table + " where CatalogID=?");
		 */
		QueryBuilder qb = new QueryBuilder("select z1.title,z1.MetaKeywords,concat('http://www.kaixinbao.com/',z1.url) URL,"
				+ "z1.firstpublishdate ,z1.author ,z1.status ,z1.orderflag ,z2.name from ZCArticle z1," + "zccatalog z2 where z1.catalogid=z2.id ");
		if (StringUtil.isNotEmpty(keyword)) {
			ZCCatalogSchema zcSchema = new ZCCatalogSchema();
			if (zcSchema.fill() && StringUtil.isNotEmpty(zcSchema.getInnerCode())) {
				qb.append(" and CatalogInnerCode like ? ", zcSchema.getInnerCode() + "%");
				qb.append(" and MetaKeywords like ? ", "%" + keyword.trim() + "%");
			}
		}
		if (StringUtil.isNotEmpty(catalog)) {
			qb.append(" and z1.catalogid in ( " + catalog + ")");
		}

		if (StringUtil.isNotEmpty(aothor)) {
			qb.append(" and z1.author = ?");
			qb.add(aothor);
		}

		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and date_format(firstpublishdate,'%Y-%m-%d') >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and date_format(firstpublishdate,'%Y-%m-%d') <= ? ", endDate);
		}
		if ("PUBLISHED".equals(listType)) { // 已发布的文档
			qb.append(" and status=?", Article.STATUS_PUBLISHED);
		} else if ("WORKFLOW".equals(listType)) {// 待流转
			qb.append(" and status=?", Article.STATUS_WORKFLOW);
		} else if ("TOPUBLISH".equals(listType)) {// 待发布的文档
			qb.append(" and status=?", Article.STATUS_TOPUBLISH);
		} else if ("ADD".equals(listType)) {// 全部文档
			qb.append(" and status in (20,30,40,50)");
		} else if ("OFFLINE".equals(listType)) {// 下线文章
			qb.append(" and status=?", Article.STATUS_OFFLINE);
		} else if ("ARCHIVE".equals(listType)) {// 归档文章
			qb.append(" and status=?", Article.STATUS_ARCHIVE);
		}
		qb.append(" order by firstpublishdate");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
		}
		setDetailWorkflowStatus(dt);// 细化工作流状态
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
		QueryBuilder qb = new QueryBuilder("select WorkflowID,NodeID,InstanceID,ActionID,State from ZWStep where (State=? or State=?) and InstanceID in (" + ids + ") order by ID asc");
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

			actionMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "ActionID"));
			stateMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "State"));
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
						WorkflowAction action = WorkflowUtil.findAction(node.getWorkflow().getID(), actionMap.getInt(instanceID));
						if (action != null) {
							dt.set(i, "StatusName", action.getName());
						}
					} else if (WorkflowStep.UNREAD.equals(stateMap.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-未读");
					} else if (WorkflowStep.UNDERWAY.equals(stateMap.getString(instanceID))) {
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
		QueryBuilder qb = new QueryBuilder("select ID,Title,author,publishDate,Addtime,catalogID,topflag,SiteID,FirstPublishDate " + "from ZCArticle where catalogid=?", Long.parseLong(catalogID));
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		qb.append(dga.getSortString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
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
	 * @param ta
	 */
	public static void treeDataBind(TreeAction ta) {
		Catalog.treeDataBind(ta);
	}

	public static Mapx init(Mapx params) {
		String catalogID = (String) params.get("CatalogID");
		if (catalogID == null) {
			return params;
		}
		DataTable dtCatalog = new QueryBuilder("select siteid from zccatalog where id=?", catalogID).executeDataTable();
		long siteID = ((Long) dtCatalog.get(0, "siteid")).longValue();
		params.put("SiteID", siteID + "");
		params.put("ListType", (String) params.get("Type"));
		return params;
	}
	public static Mapx initTransURL(Mapx params) {
		String ArticleID = (String) params.get("ArticleID");
		if (ArticleID == null) {
			return params;
		}
		String ArticleURL = new QueryBuilder("SELECT URL FROM zcarticle WHERE id=?", ArticleID).executeString();;
		params.put("ArticleURL", ArticleURL + "");
		return params;
	}
	
	

	public static Mapx initDocList(Mapx params) {
		params.put("Prop2", HtmlUtil.codeToOptions("ArticleAttribute", true));
		return params;
	}

	public void add() {
	}

	public void up() {
		String ids = $V("ArticleIDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, "文章上线失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		// 上线引用，判断源是否上线，如果上线则上线，如无上线，则不上线
		ZCArticleSet set4 = article.query(new QueryBuilder("where id in(" + ids + ") and type = ? ", Article.TYPE_URL));
		String ReferSourceID = "";
		if (set4 != null && set4.size() > 0) {
			for (int i = 0; i < set4.size(); i++) {
				ReferSourceID += set4.get(i).getReferSourceID() + ",";
			}
			if (StringUtil.isNotEmpty(ReferSourceID) && ReferSourceID.endsWith(",")) {
				ReferSourceID = ReferSourceID.substring(0, ReferSourceID.length() - 1);
				ZCArticleSet set1 = article.query(new QueryBuilder("where id in(" + ReferSourceID + ") and status != ? ", Article.STATUS_PUBLISHED));
				if (set1 != null && set1.size() > 0) {
					Response.setStatus(0);
					Response.setMessage("上线引用文件，请选上线源文件!");
					return;
				}
			}
		}
		// 上线源，引用上线；；
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids + ") or ReferSourceID in (" + ids + ")"));
		String article_ids = "";
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			article_ids += article.getID();
			if (i != set.size() - 1) {
				article_ids += ",";
			}
			ids = article_ids;
		}
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "UP", "上线处理");
		trans.add(new QueryBuilder("update zcarticle set Status =" + Article.STATUS_PUBLISHED + ",PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where status = " + Article.STATUS_OFFLINE
				+ " and id in(" + ids + ")", new Date()));
		DataTable dt = new QueryBuilder("select Title from ZCArticle where status = " + Article.STATUS_OFFLINE + " and id in (" + ids + ")").executeDataTable();
		if (trans.commit()) {
			upTask(ids);
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			clearCacheInfo(ids);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, logs + "上线成功", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("上线成功");
		} else {

			dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, logs + "上线失败", Request.getClientIP());

			Response.setStatus(0);
			Response.setMessage("上线失败");
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				ZCArticleSchema site = new ZCArticleSchema();
				ZCArticleSet set = site.query(new QueryBuilder("where status = " + Article.STATUS_PUBLISHED + " and id in (" + ids + ")"));
				if (set != null && set.size() > 0) {
					p.publishArticle(set, false, this);
					p.publishCatalog(set.get(0).getCatalogID(), false, false , true);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void dealArticleHistory(String ids, Transaction trans, String dealName, String dealDetail) {
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		String[] idarr = ids.split(",");
		for (int i = 0; i < idarr.length; i++) {
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(idarr[i]);
			articleLog.setAction(dealName);
			articleLog.setActionDetail(dealDetail);
			// 2011-1-14 处理历史显示真实姓名
			articleLog.setAddUser(User.getRealName());
			articleLog.setAddTime(new Date());
			trans.add((ZCArticleLogSchema) articleLog.clone(), Transaction.INSERT);
		}
	}

	public void down() {
		String ids = $V("ArticleIDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, "文章下线失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();

		ZCArticleSchema article = new ZCArticleSchema();
		// 下线源文件同时下线引用文件，反之，下线引用，源文件不下线；
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids + ") or ReferSourceID in (" + ids + ")"));
		String article_ids = "";
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			article_ids += article.getID();
			if (i != set.size() - 1) {
				article_ids += ",";
			}
			// 判断栏目是否是本机构独立管理
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
					return;
				}
			}
		}
		ids = article_ids;
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "DOWN", "下线处理");
		trans.add(new QueryBuilder("update zcarticle set Status = " + Article.STATUS_OFFLINE + ",TopFlag='0',DownlineDate = ?,modifyTime=? where   id in(" + ids + ")", now, now));
		if (trans.commit()) {
			DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, logs + "下线成功", Request.getClientIP());

			ZCArticleSchema site = new ZCArticleSchema();
			set = site.query(new QueryBuilder("where status = " + Article.STATUS_OFFLINE + " and id in (" + ids + ")"));
			downTask(set);
			CatalogSite catalogSite = new CatalogSite();
			catalogSite.publishIndex();
			clearCacheInfo(ids);
			Response.setStatus(1);
		} else {

			DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, logs + "下线失败", Request.getClientIP());

			Response.setStatus(0);
		}
	}

	private long downTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				if (set != null && set.size() > 0) {
					p.deletePubishedFile(set);

					Mapx catalogMap = new Mapx();
					for (int k = 0; k < set.size(); k++) {
						catalogMap.put(set.get(k).getCatalogID() + "", set.get(k).getCatalogID() + "");
						String pid = CatalogUtil.getParentID(set.get(k).getCatalogID());
						while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
							catalogMap.put(pid, pid);
							pid = CatalogUtil.getParentID(pid);
						}
					}

					// 生成本级栏目
					Object[] vs = catalogMap.valueArray();
					for (int j = 0; j < catalogMap.size(); j++) {
						String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
						// if (StringUtil.isEmpty(listpage) ||
						// "0".equals(listpage) || "-1".equals(listpage)) {
						if (StringUtil.isEmpty(listpage)) {
							listpage = "20"; // 默认只发布前二十页
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()), false, false, true , Integer.parseInt(listpage));
						setPercent(getPercent() + 5);
						setCurrentInfo("发布栏目页面");
					}
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	// 校验文章状态，指定状态的文档不能删除
	private boolean checkArticleStatus(ZCArticleSet set, String allowArticleStatus) {
		DataTable dt = set.toDataTable();
		dt.insertColumn("ActionID");
		Mapx map = new Mapx();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
				map.put(dt.getString(i, "WorkflowID"), "1");
			}
		}

		String ids = StringUtil.join(map.keyArray());
		if (!StringUtil.checkID(ids) || map.size() == 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "ActionID", "0");
			}
		} else {
			QueryBuilder qb = new QueryBuilder("select InstanceID,ActionID from ZWStep where InstanceID in (" + ids + ") order by ID asc");
			DataTable stepTable = qb.executeDataTable();
			Mapx stepMap = stepTable.toMapx(0, 1);
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
					String id = dt.getString(i, "WorkflowID");
					if (stepMap.containsKey(id)) {
						dt.set(i, "ActionID", stepMap.get(id));
					} else {
						dt.set(i, "ActionID", "0");
					}
				} else {
					dt.set(i, "ActionID", "0");
				}
			}
		}

		if (!allowArticleStatus.startsWith(",")) {
			allowArticleStatus = "," + allowArticleStatus;
		}

		if (!allowArticleStatus.endsWith(",")) {
			allowArticleStatus = allowArticleStatus + ",";
		}

		for (int i = 0; dt != null && i < dt.getRowCount(); i++) {
			if (!checkArticleStatus(dt.get(i), allowArticleStatus)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkArticleStatus(DataRow dr, String notDeleteArticleStatus) {
		if (StringUtil.isNotEmpty(notDeleteArticleStatus) && notDeleteArticleStatus.indexOf("," + dr.getString("Status") + ",") != -1) {
			if (dr.getInt("Status") == Article.STATUS_WORKFLOW) {
				if ("0".equals(dr.getString("ActionID"))) {
					return true;
				}
			}
			return false;
		}

		return true;
	}

	/**
	 * 修改记录 20090525,shb,删除文章图片关联,删除文章的相关评论
	 * 
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "删除文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in (" + ids + ") or id in (select id from zcarticle where refersourceid in (" + ids + ") )"));

		String notDeleteArticleStatus = Article.STATUS_PUBLISHED + "," + Article.STATUS_WORKFLOW + "," + Article.STATUS_TOPUBLISH; // 处于该状态下的文档不允许删除
		if (!checkArticleStatus(set, notDeleteArticleStatus)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "已发布的文档或流转中的文档不能删除,请下线后再删除", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("已发布的文档或流转中的文档不能删除,请下线后再删除!");
			return;
		}

		trans.add(set, Transaction.DELETE_AND_BACKUP);
		Mapx catalogMap = new Mapx();
		StringBuffer logs = new StringBuffer("删除文章:");
		// 删除文章的相关评论等
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			// 判断栏目是否是本机构独立管理
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
					return;
				}
			}

			String sqlArticleCount = "update zccatalog set " + "total = total-1,isdirty=1 where innercode in(" + CatalogUtil.getParentCatalogCode(article.getCatalogInnerCode()) + ")";
			trans.add(new QueryBuilder(sqlArticleCount));

			// 增加删除消息
			StringFormat sf = new StringFormat("标题为 ? 的文档己被删除");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			String subject = sf.toString();

			sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 删除。");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			sf.add("<font class='red'>" + DateUtil.getCurrentDateTime() + "</font>");
			// sf.add("<font class='red'>" + User.getUserName() + "</font>");
			sf.add("<font class='red'>" + User.getRealName() + "</font>");

			MessageCache.addMessage(trans, subject, sf.toString(), new String[] { article.getAddUser() }, "SYSTEM", false);

			// 删除文章相关的自定义字段
			ZDColumnValueSchema colValue = new ZDColumnValueSchema();
			colValue.setRelaID(article.getID() + "");
			colValue.setRelaType(ColumnUtil.RELA_TYPE_DOCID);// 必须加，有可能文章ID和栏目ID重复
			ZDColumnValueSet colValueSet = colValue.query();
			trans.add(colValueSet, Transaction.DELETE_AND_BACKUP);

			// 删除文章图片关联
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setRelaID(article.getID() + "");
			imageRela.setRelaType(Article.RELA_IMAGE);// 必须加，需要考虑图片播放器
			ZCImageRelaSet imageRelaSet = imageRela.query();
			trans.add(imageRelaSet, Transaction.DELETE_AND_BACKUP);

			// 删除文章视频关联
			ZCVideoRelaSchema videoRela = new ZCVideoRelaSchema();
			videoRela.setRelaID(article.getID() + "");
			videoRela.setRelaType(Article.RELA_VIDEO);
			ZCVideoRelaSet videoRelaSet = videoRela.query();
			trans.add(videoRelaSet, Transaction.DELETE_AND_BACKUP);

			// 删除文章附件关联
			ZCAttachmentRelaSchema attachmentRela = new ZCAttachmentRelaSchema();
			attachmentRela.setRelaID(article.getID() + "");
			attachmentRela.setRelaType(Article.RELA_ATTACH);
			ZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query();
			trans.add(attachmentRelaSet, Transaction.DELETE_AND_BACKUP);

			// 删除文章音频关联
			ZCAudioRelaSchema audioRela = new ZCAudioRelaSchema();
			audioRela.setRelaID(article.getID() + "");
			ZCAudioRelaSet audioRelaSet = audioRela.query();
			trans.add(audioRelaSet, Transaction.DELETE_AND_BACKUP);

			// 删除文章的相关评论
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setRelaID(article.getID() + "");
			ZCCommentSet commentSet = comment.query();
			trans.add(commentSet, Transaction.DELETE_AND_BACKUP);

			// 删除投票
			ZCVoteItemSchema voteitem = new ZCVoteItemSchema();
			voteitem.setVoteDocID(article.getID());
			trans.add(voteitem.query(), Transaction.DELETE_AND_BACKUP);

			// 操作日志
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			ZCArticleLogSet artilceLogSet = articleLog.query();

			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("DELETE");
			articleLog.setActionDetail("删除。删除原因：" + $V("DeleteReason"));
			// 2011-1-14 处理历史显示真实姓名
			articleLog.setAddUser(User.getRealName());
			articleLog.setAddTime(new Date());
			artilceLogSet.add(articleLog);// 先加入最后一条操作日志，然后再删除备份
			trans.add(artilceLogSet, Transaction.DELETE_AND_BACKUP);

			catalogMap.put(article.getCatalogID() + "", article.getCatalogInnerCode());

			if (article.getWorkFlowID() != 0) {
				WorkflowUtil.deleteInstance(trans, article.getWorkFlowID());
			}
		}

		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		if (trans.commit()) {
			downTask(set);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "成功", Request.getClientIP());

			Response.setStatus(1);
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void topublish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "转为待发布操作失败,ids:" + ids, Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入IDs参数错误!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids + ") or id in(select id from zcarticle where refersourceid in (" + ids + ") )"));
		String log = "转为待发布操作成功";
		ZCArticleSet updateset = new ZCArticleSet();
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if ((article.getStatus() == Article.STATUS_DRAFT || article.getStatus() == Article.STATUS_EDITING) && article.getWorkFlowID() == 0) {
				article.setStatus(Article.STATUS_TOPUBLISH);
				updateset.add(article);
			} else if (article.getWorkFlowID() != 0) {
				log = "此文档在工作流转中，不能转为待发布";
			} else {
				log = "只有‘初稿’和‘重新编辑’的文章转为待发布状态了";
			}
		}
		updateset.update();
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "转为待发布操作成功,ids:" + ids, Request.getClientIP());
		Response.setLogInfo(1, log);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, "文章发布失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入IDs参数错误!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));

		ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid in (" + ids + ")"));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
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
		//清除缓存start
		QueryBuilder qb_cache = new  QueryBuilder("SELECT code FROM fdcode WHERE codetype='ERiskType'");
		
		DataTable dt_catch = qb_cache.executeDataTable();
		for(DataRow dr : dt_catch){ 
			String riskType = dr.getString(0);
			if(StringUtil.isNotEmpty(riskType)&&riskType.length()>0){
				Set<String>  keySet = JedisCommonUtil.keys(1,riskType.charAt(0)+"*");
				String[] keyArray = null;
				if(keySet!=null&&keySet.size()>0){
				keyArray =   (String[]) keySet.toArray(new String[keySet.size()]);
				JedisCommonUtil.remove(1,keyArray);
				}
				 keySet = JedisCommonUtil.keys(2,riskType.charAt(0)+"*");
				 if(keySet!=null&&keySet.size()>0){
				 keyArray =   (String[]) keySet.toArray(new String[keySet.size()]); 
				 JedisCommonUtil.remove(2,keyArray);
				}	 
			}
		}
		//b2bapp版本升级
		updateB2bAppVersion();
       //清除缓存end	
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, logs + "成功", Request.getClientIP());
		Response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", "" + id);
	}

	public void changeToPublish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入IDs参数错误!");
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		BZCArticleSet bset = barticle.query(new QueryBuilder(" where id in (" + ids + ") and backupmemo='Archive' "));
		Transaction trans = new Transaction();
		for (int i = 0; i < bset.size(); i++) {
			barticle = bset.get(i);
			ZCArticleSchema article = new ZCArticleSchema();
			SchemaUtil.copyFieldValue(barticle, article);
			article.setStatus(Article.STATUS_PUBLISHED);
			article.setArchiveDate(CatalogUtil.getArchiveTime(barticle.getCatalogID()));
			trans.add(article, Transaction.INSERT);
			trans.add(barticle, Transaction.DELETE);
		}
		if (trans.commit()) {
			StringBuffer logs = new StringBuffer("从归档文章转为已发布文章: ");
			if (bset.size() > 0) {
				logs.append(bset.get(0).getTitle());
				if (bset.size() > 1) {
					logs.append(" 等，共" + bset.size() + "篇");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "转为已发布成功");
		} else {
			Response.setLogInfo(0, "转为已发布失败");
		}
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

	/**
	 * 转移文章
	 * 
	 */
	public void move() {
		String articleIDs = $V("ArticleIDs");
		if (!StringUtil.checkID(articleIDs)) {
			Response.setError("操作数据库时发生错误!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(catalogID)) {
			Response.setError("传入CatalogID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema srcArticle = new ZCArticleSchema();
		ZCArticleSet set = srcArticle.query(new QueryBuilder("where id in (" + articleIDs + ")"));
		long srcCatalogID = 0;

		String[] srcArticleIDs = null;
		if (set.size() > 0) {
			srcArticleIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcArticleIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		StringBuffer logs = new StringBuffer("转移文章:");
		for (int i = 0; i < set.size(); i++) {
			ZCArticleSchema article = set.get(i);
			// 判断栏目是否是本机构独立管理
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
					return;
				}
			}
			srcCatalogID = article.getCatalogID();
			String destCatalogID = catalogID;
			if (article.getStatus() == (long) Article.STATUS_WORKFLOW && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				Response.setStatus(0);
				Response.setMessage("文档处于流转中，不能进行转移操作！");
				return;
			}
			String ReferTarget = article.getReferTarget();
			if (StringUtil.isNotEmpty(ReferTarget)) {// 要防止有自己到自己的复制关系
				ReferTarget = "," + ReferTarget + ",";
				ReferTarget = StringUtil.replaceEx(ReferTarget, "," + catalogID + ",", ",");
				ReferTarget = ReferTarget.substring(0, ReferTarget.length() - 1);
				article.setReferTarget(ReferTarget);
			}
			article.setClusterTarget(null);// 栏目换了,网站群复制关系解除

			trans.add(new QueryBuilder("update zccatalog set total = total+1 where id=?", Long.parseLong(destCatalogID)));
			trans.add(new QueryBuilder("update zccatalog set total = total-1 where id=?", srcCatalogID));
			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setCatalogID(catalogID);
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			String workflowID = CatalogUtil.getWorkflow(destCatalogID);
			if (StringUtil.isNotEmpty(workflowID)) {
				article.setWorkFlowID(null);
				// trans.add(new
				// QueryBuilder("delete from zwstep where exists (select * from zwinstance"
				// + " where dataid=? and id=zwstep.instanceID)",
				// article.getID()));
				// trans.add(new
				// QueryBuilder("delete from zwinstance where dataid=?",
				// article.getID()));
				trans.add(new QueryBuilder("delete from zwstep where exists (select * from zwinstance" + " where dataid=? and id=zwstep.instanceID)", article.getID() + ""));
				trans.add(new QueryBuilder("delete from zwinstance where dataid=?", article.getID() + ""));

			}
			article.setStatus(Article.STATUS_DRAFT);

			trans.add(article, Transaction.UPDATE);

			// 操作日志
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("MOVE");
			articleLog.setActionDetail("转移。从" + CatalogUtil.getName(srcCatalogID) + "" + "转移到" + CatalogUtil.getName(destCatalogID) + "。");
			articleLog.setAddUser(User.getRealName());
			articleLog.setAddTime(new Date());
			trans.add(articleLog, Transaction.INSERT);
		}
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		if (trans.commit()) {
			Publisher p = new Publisher();
			// 删除原有文章
			p.deletePubishedFile(set);
			// 去掉直接发布的逻辑，转移后都为初稿状态
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_MOVEARTICLE, logs + "成功", Request.getClientIP());
			Response.setMessage("转移成功");
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_MOVEARTICLE, logs + "失败", Request.getClientIP());
			Response.setError("操作数据库时发生错误!");
		}
	}

	/**
	 * 复制文章
	 * 
	 */
	public void copy() {
		String articleIDs = $V("ArticleIDs");
		if (!StringUtil.checkID(articleIDs)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "复制文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ArticleID时发生错误!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!StringUtil.checkID(catalogIDs)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "复制文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		if (catalogIDs.indexOf("\"") >= 0 || catalogIDs.indexOf("\'") >= 0) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "复制文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in (" + articleIDs + ")"));

		// 复制文章
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if (article.getStatus() == (long) Article.STATUS_WORKFLOW && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				Response.setStatus(0);
				Response.setMessage("文档处于流转中，不能进行复制操作！");
				return;
			}

			// 复制自定义字段值信息
			DataTable customData = new QueryBuilder("select ColumnCode,TextValue from zdcolumnvalue where relaid=?", article.getID() + "").executeDataTable();
			for (int j = 0; j < customData.getRowCount(); j++) {
				Request.put(ColumnUtil.PREFIX + customData.getString(j, "ColumnCode"), customData.getString(j, "TextValue"));
			}
			Request.put("ReferTarget", catalogIDs);
			Article.copy(Request, trans, article);

			article.setReferTarget(catalogIDs);
			article.setReferType($V("ReferType"));
		}

		StringBuffer logs = new StringBuffer("复制文章:");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}

			trans.add(set, Transaction.UPDATE);
		}
		if (trans.commit()) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, logs + "成功", Request.getClientIP());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, logs + "失败", Request.getClientIP());
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	/**
	 * 对文章进行排序,文章默认排序值是时间戳
	 */
	public void sortArticle() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		boolean topFlag = "true".equals($V("TopFlag"));
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		Transaction tran = new Transaction();
		if (topFlag) {
			QueryBuilder qb1 = new QueryBuilder("update ZCArticle set TopFlag='0'");
			tran.add(qb1);
			QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where OrderFlag in (" + orders + ")");
			tran.add(qb);
		} else {
			QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where OrderFlag in (" + orders + ")");
			tran.add(qb);
		}
		OrderUtil.updateOrder("ZCArticle", "OrderFlag", type, target, orders, null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id).getString("ListPage");
					// if (StringUtil.isEmpty(listpage) || "0".equals(listpage)
					// || "-1".equals(listpage)) {
					if (StringUtil.isEmpty(listpage)) {
						listpage = "20"; // 默认只发布前二十页
					}
					p.publishCatalog(Long.parseLong(id), false, false, false, Integer.parseInt(listpage));
					setPercent(100);
				}
			};
			ltt.setUser(User.getCurrent());
			ltt.start();

			Response.setMessage("操作成功");
		} else {
			Response.setError("操作失败");
		}
	}

	public void setTop() {
		String ids = $V("IDs");
		String topDate = $V("TopDate");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where id in (" + ids + ")");
		if (StringUtil.isNotEmpty(topDate)) {
			if (new Date().compareTo(DateUtil.parseDateTime(topDate + " " + $V("TopTime"))) >= 0) {
				Response.setLogInfo(0, "置顶有效期限应大于当前时间!");
				return;
			}
			qb = new QueryBuilder("update ZCArticle set TopFlag='1',TopDate='" + topDate + " " + $V("TopTime") + "' where id in (" + ids + ")");
		}
		qb.executeNoQuery();
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETTOP", "置顶处理");
		if (!trans.commit()) {
			Response.setMessage("置顶操作时记录操作历史信息出错！");
		}
		DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
		StringBuffer logs = new StringBuffer("置顶文章:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" 等，共" + dt.getRowCount() + "篇");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPARTICLE, logs + "成功", Request.getClientIP());
		Response.setLogInfo(1, "置顶成功");

		// 另起线程执行新闻发布过程
		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false , false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
	}
	/**
	 * 
	 * setArchive:(归档). <br/>
	 *
	 * @author
	 */
	public void setArchive() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		QueryBuilder qb = new QueryBuilder("update ZCArticle set status='50' where id in (" + ids + ")");
		qb.executeNoQuery();
		//清除缓存start
		QueryBuilder qb_cache = new  QueryBuilder("SELECT code FROM fdcode WHERE codetype='ERiskType'");
		
		DataTable dt_catch = qb_cache.executeDataTable();
		for(DataRow dr : dt_catch){ 
			String riskType = dr.getString(0);
			if(StringUtil.isNotEmpty(riskType)&&riskType.length()>0){
				Set<String>  keySet = JedisCommonUtil.keys(1,riskType.charAt(0)+"*");
				String[] keyArray = null;
				if(keySet!=null&&keySet.size()>0){
				keyArray =   (String[]) keySet.toArray(new String[keySet.size()]);
				JedisCommonUtil.remove(1,keyArray);
				}
				 keySet = JedisCommonUtil.keys(2,riskType.charAt(0)+"*");
				 if(keySet!=null&&keySet.size()>0){
				 keyArray =   (String[]) keySet.toArray(new String[keySet.size()]); 
				 JedisCommonUtil.remove(2,keyArray);
				}	 
			}
		}
		//b2bapp版本升级
		updateB2bAppVersion();
       //清除缓存end	
		
	 	
		
		Response.setLogInfo(1, "归档成功");
	}

	public void setNotTop() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where id in (" + ids + ")");
		qb.executeNoQuery();
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETNOTTOP", "取消置顶");
		if (!trans.commit()) {
			Response.setMessage("取消置顶操作时记录操作历史信息出错！");
		}
		DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
		StringBuffer logs = new StringBuffer("取消置顶文章:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" 等，共" + dt.getRowCount() + "篇");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_NOTTOPARTICLE, logs + "成功", Request.getClientIP());
		Response.setLogInfo(1, "取消置顶成功");

		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false ,false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
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

	public void clearCacheInfo(String ids) {
		String sql = " SELECT TextValue FROM zdcolumnvalue WHERE relaid IN (" + ids + ") AND columncode = 'RiskCode' ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataCache.cleanOneProductInfo(dt.getString(i, "TextValue"));
			}
		}
	}
	/**
	 * 专题URL转换
	 */
	public void transURL() {
		try {
			String Id = $V("ArticleID");
			String oldURL = $V("oldURL");
			String newURL = $V("newURL");
			if(oldURL.equals(newURL)){
				Response.setStatus(0);
				Response.setMessage("文章新URL与文章原URL相同，无需转换！");
				return;
			}
			String status = new QueryBuilder(" SELECT STATUS FROM zcarticle WHERE id=? ",Id).executeString();
			if(!"40".equals(status)){
				Response.setStatus(0);
				Response.setMessage("专题URL转换失败,请先将该文章下线！");
				return;
			}
			QueryBuilder qb = new QueryBuilder(" UPDATE zcarticle SET url=? WHERE id=? ",newURL,Id);
			Transaction trans = new Transaction();
			trans.add(qb);
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("专题URL转换失败,保存数据库失败！");
				return;
			}
			Response.setStatus(1);
			Response.setMessage("专题URL转换成功！");
			return;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	
	/**
	 * 更新b2bapp版本
	 * @return
	 */
	private boolean updateB2bAppVersion(){
		QueryBuilder qb_cache = new  QueryBuilder("select codevalue from  zdcode where codetype ='b2b_app_version'");
		String version = String.valueOf(qb_cache.executeOneValue());
		String prefix="";
		String suffix="";
		int suffixNum=0; 
		
		if(version.indexOf(".")>0){
		 prefix = version.substring(0, version.indexOf(".")+1); 
		 suffix = version.substring(version.indexOf(".")+1);
		 try{
				suffixNum = Integer.parseInt(suffix)+1;	
				suffix = suffixNum+"";
			}catch(Exception e){
				suffix="99";
						
			}
		}
		else{
			prefix=version;
			suffix=".99";
		}
		
		
		version = prefix+suffix; 
		String updateVersion = "update zdcode set codevalue='"+version+"' where codetype ='b2b_app_version'"; 
		QueryBuilder qb_update = new  QueryBuilder(updateVersion); 
		try {
			qb_update.executeNoQuery();
		} catch (Exception e) {
			logger.error("微商产品上线/归档更新版本错误"+e.getMessage(), e);
		} 
		return true;
	}
}
