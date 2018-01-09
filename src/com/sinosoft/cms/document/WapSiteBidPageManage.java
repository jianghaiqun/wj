package com.sinosoft.cms.document;

import cn.com.sinosoft.util.Tool;
import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.site.CatalogCJWT;
import com.sinosoft.cms.site.CatalogShowConfig;
import com.sinosoft.cms.site.CatalogYXCP;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Filter;
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
import com.sinosoft.schema.SDWapBidMenuSchema;
import com.sinosoft.schema.SDWapBidMenuSet;
import com.sinosoft.schema.SDWapBidProductSchema;
import com.sinosoft.schema.SDWapBidProductSet;
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
import java.util.List;

/**
 * WAP站竞价页管理
 */
public class WapSiteBidPageManage extends Page {

	/**
	 * 添加标题菜单对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initAddDialog(Mapx params) {
		return params;
	}

	/**
	 * 标题菜单对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initProductMenu(Mapx params) {
		return params;
	}

	/**
	 * 产品内容编辑对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initProductContent(Mapx params) {
		return params;
	}

	/**
	 * 产品选择对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initProductSel(Mapx params) {
		params.put("CompanyList", HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}

	/**
	 * @Title: saveMenu.
	 * @Description: (标题菜单-添加).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void saveMenu() {
		String documentId = $V("DocumentId");
		String menuName = $V("MenuName");
		String orderFlag = $V("OrderFlag");
		String description = $V("Description");

		try {
			Transaction ts = new Transaction();
			SDWapBidMenuSchema pms = new SDWapBidMenuSchema();

			QueryBuilder query = new QueryBuilder(
					"select ID from SDWapBidMenu where DocumentId = ? and MenuName = ?");
			query.add(documentId);
			query.add(menuName);
			String result = query.executeString();

			if (StringUtil.isNotEmpty(result)) {
				Response.setStatus(2);
				Response.setMessage("该菜单已存在!");
				return;
			}

			pms.setID(NoUtil.getMaxNo("WapBidMenuId"));
			pms.setMenuName(menuName);

			pms.setDescription(description);

			pms.setOrderFlag(orderFlag);
			pms.setDocumentId(documentId);
			pms.setCreateUser(User.getUserName());
			pms.setCreateDate(Tool.getCurrentTime());
			ts.add(pms, Transaction.INSERT);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("saveMenu 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: saveProducts.
	 * @Description: (选择产品).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void saveProducts() {

		String wapBidMenuId = $V("WapBidMenuId");
		String articleId = $V("DocumentId");
		DataTable DT_Product = (DataTable) Request.get("DT_Product");
		if (DT_Product == null || DT_Product.getRowCount() == 0) {
			Response.setLogInfo(0, "保存失败，原因：产品数据为空!");
		}

		try {

			Transaction trans = new Transaction();

			QueryBuilder queryOrder = new QueryBuilder(
					"select Max(OrderFlag) from SDWapBidProduct where WapBidMenuId = '" + wapBidMenuId + "'");

			String resultOrder = queryOrder.executeString();
			Integer orderStart = 1;
			if (StringUtil.isNotEmpty(resultOrder)) {
				orderStart = Integer.valueOf(resultOrder) + 1;
			}

			for (int i = 0; i < DT_Product.getRowCount(); i++) {
				SDWapBidProductSchema sSchema = new SDWapBidProductSchema();

				// 整个页面的重复数据查询
//				QueryBuilder query = new QueryBuilder(
//						"select m.ID from SDWapBidProduct p, sdWapBidMenu m WHERE p.WapBidMenuId = m.ID AND m.DocumentId = ? AND p.ProductId = ? ");

				// 同一标题菜单下只能有一个产品
				QueryBuilder query = new QueryBuilder(
						"select ID from SDWapBidProduct where WapBidMenuId = ? and ProductId = ?");
				String productID = DT_Product.getString(i, "ProductID");
				query.add(wapBidMenuId);
				query.add(productID);
				String result = query.executeString();

				if (StringUtil.isNotEmpty(result)) {
					continue;
				}
				sSchema.setID(NoUtil.getMaxNo("WapBidProductId"));
				sSchema.setWapBidMenuId(wapBidMenuId);
				sSchema.setProductId(productID);
				sSchema.setOrderFlag(String.valueOf(orderStart++));
				sSchema.setCreateUser(User.getUserName());
				sSchema.setCreateDate(Tool.getCurrentTime());
				trans.add(sSchema, Transaction.INSERT);
			}

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("saveMailConfig 方法异常! error:" + e.getMessage(), e);
		}
	}

	/**
	 * @Title: saveProductOrderFlag.
	 * @Description: (修改-顺序调整+WAP产品名称保存).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void saveProductOrderFlag() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保存失败，原因：顺序调整模块信息为空!");
			return;
		}

		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			SDWapBidProductSchema sSchema = new SDWapBidProductSchema();
			sSchema.setID(dt.getString(i, "ID"));
			if (sSchema.fill()) {
				sSchema.setWapProductName(dt.getString(i, "WapProductName"));
				sSchema.setOrderFlag(dt.getString(i, "OrderFlag"));
				sSchema.setModifyUser(User.getUserName());
				sSchema.setModifyDate(Tool.getCurrentTime());
				trans.add(sSchema, Transaction.UPDATE);
			} else {
				Response.setLogInfo(0, "查询模块信息失败!");
				return;
			}
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	/**
	 * @Title: delContents.
	 * @Description: (删除产品内容).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void delContents() {
		try {
			String ids = $V("IDs");
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			Transaction trans = new Transaction();
			SDWapBidProductSchema sDWapBidProductSchema = new SDWapBidProductSchema();
			SDWapBidProductSet set = sDWapBidProductSchema
					.query(new QueryBuilder("where id in (" + ids + ")"));
			if (set == null || set.size() == 0) {
				Response.setLogInfo(0, "删除失败!");
				return;
			}
			trans.add(set, Transaction.DELETE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "删除失败!" + e.getMessage());
		}
	}

	/**
	 * @Title: delMenus.
	 * @Description: (删除标题菜单).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void delMenus() {
		try {
			String ids = $V("IDs");
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			Transaction trans = new Transaction();

			SDWapBidMenuSchema sDWapBidMenuSchema = new SDWapBidMenuSchema();
			SDWapBidMenuSet set = sDWapBidMenuSchema.query(new QueryBuilder(
					"where id in (" + ids + ")"));

			SDWapBidProductSchema sDWapBidProductSchema = new SDWapBidProductSchema();
			SDWapBidProductSet productSet = sDWapBidProductSchema
					.query(new QueryBuilder("where WapBidMenuId in (" + ids
							+ ")"));

			if (set == null || set.size() == 0) {
				Response.setLogInfo(0, "删除失败!");
				return;
			}
			trans.add(set, Transaction.DELETE);
			trans.add(productSet, Transaction.DELETE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "删除失败!" + e.getMessage());
		}
	}

	/**
	 * @Title: saveOrderFlag.
	 * @Description: (修改-顺序调整).
	 * @return void 返回类型.
	 * @author wangwenying.
	 */
	public void saveOrderFlag() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保存失败，原因：顺序调整模块信息为空!");
			return;
		}

		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			SDWapBidMenuSchema sDWapBidMenuSchema = new SDWapBidMenuSchema();
			sDWapBidMenuSchema.setID(dt.getString(i, "ID"));
			if (sDWapBidMenuSchema.fill()) {
				sDWapBidMenuSchema.setOrderFlag(dt.getString(i, "OrderFlag"));
				sDWapBidMenuSchema.setModifyUser(User.getUserName());
				sDWapBidMenuSchema.setModifyDate(Tool.getCurrentTime());
				trans.add(sDWapBidMenuSchema, Transaction.UPDATE);
			} else {
				Response.setLogInfo(0, "查询模块信息失败!");
				return;
			}
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	/**
	 * 向外提供显示栏目树
	 * 
	 */
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString())
				: Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj
				.toString()) : CatalogCJWT.CATALOG;

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.DocList.LastCatalog");
		}
		String[] codes = CatalogYXCP.getSelectedCatalogList(IDs,
				CatalogShowConfig.getArticleCatalogShowLevel());

		DataTable dt = null;
		ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());

		QueryBuilder qb = new QueryBuilder(
				"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
						+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? and innercode like '"
						+ Config.getValue("WapBid")
						+ "%' order by orderflag,innercode");
		qb.add(catalogType);
		qb.add(siteID);
		qb.add(ta.getLevel());
		dt = qb.executeDataTable();
		CatalogYXCP.prepareSelectedCatalogData(dt, codes, catalogType, siteID,
				ta.getLevel());

		String siteName = "文档库";
		if (catalogType == CatalogCJWT.SHOP_GOODS) {
			siteName = "商品库";
		} else if (catalogType == CatalogCJWT.SHOP_GOODS_BRAND) {
			siteName = "商品品牌库";
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE,
						dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='"
					+ siteID + "'><label for='_site'>" + siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='"
					+ siteID
					+ "' onclick='selectAll()'><label for='_site'>"
					+ siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}
		ta.bindData(dt);
		CatalogYXCP.addSelectedBranches(ta, codes);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	/**
	 * Wap竞价页-标题菜单查询
	 * 
	 * @param dga
	 */
	public static void dgMenuDataBind(DataGridAction dga) {
		String sql = " SELECT s.ID, s.MenuName, s.DocumentId, s.Description, s.OrderFlag, s.prop1, s.prop2, s.prop3, "
				+ " (SELECT COUNT(1) FROM SDWapBidProduct p WHERE s.ID = p.WapBidMenuId) PCount "
				+ "  from SDWapBidMenu s where s.DocumentId = ? "
				+ " order by s.OrderFlag ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(dga.getParams().get("DocumentId"));
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

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

	/**
	 * Wap竞价页-产品内容查询
	 * 
	 * @param dga
	 */
	public static void dgConDataBind(DataGridAction dga) {
		String sql = " SELECT s.ID, s.WapBidMenuId, s.ProductId, s.WapProductName, s.OrderFlag, s.prop1, s.prop2, s.prop3,"
				+ " p.ProductName, p.InsureName FROM sdWapBidProduct s, sdproduct p "
				+ "  where s.ProductId = p.ProductID AND s.WapBidMenuId = ? order by s.OrderFlag ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(dga.getParams().get("WapBidMenuId"));
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

	/**
	 * 产品数据查询
	 * 
	 * @param dga
	 */
	public static void productGd1DataBind(DataGridAction dga) {
		String sql = "select s.ProductID,s.ProductName,s.InsureName,s.ProductGenera,s.IsPublish,s.Remark6 from sdproduct s where 1=1 ";

		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		String CompanyCode = (String) dga.getParams().get("CompanyCode");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(ProductID)) {
			qb.append(" and s.ProductID like ? ", "%" + ProductID + "%");
		}

		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and s.ProductName like ? ", "%" + ProductName + "%");
		}

		if (StringUtil.isNotEmpty(CompanyCode)) {
			qb.append(" and s.Remark6 = ? ", CompanyCode);
		}

		qb.append(" order by s.ProductID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
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
		tSCFAQSchema.setOrderFlag(OrderUtil.getDefaultOrder() + "");
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
		String sql = "select * from SCFAQ where RelaId='" + tRelaId
				+ "' order by orderflag";
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
			// FaqHtml += "<div class=\"cp_cjwt\">";
			// FaqHtml += "<div class=\"ccp_box_con\">";
			// FaqHtml += "<ul class=\"pl_list_convs\">";
			for (int i = 0; i < tSCFAQSet.size(); i++) {
				ZDColumnValueSchema value = new ZDColumnValueSchema();
				SCFAQSchema tSCFAQSchema1 = tSCFAQSet.get(i);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String temp = formatter.format(new java.util.Date());
				/*
				 * 产品详细页常见问题样式修改 FaqHtml += "<dl class=\"problem_box\">";
				 * FaqHtml += "<dt>"; FaqHtml += tSCFAQSchema1.getFAQName();
				 * FaqHtml += "</dt>"; FaqHtml += "<dd>"; FaqHtml +=
				 * tSCFAQSchema1.getFAQContent(); FaqHtml += "</dd>"; FaqHtml +=
				 * "<dd class=\"problem_tiem\">"; FaqHtml += temp; FaqHtml +=
				 * "</dd>"; FaqHtml += "</dl>";
				 */

				// FaqHtml += "<li><b>";
				FaqHtml += "'";
				FaqHtml += tSCFAQSchema1.getFAQName();

				if (i == (tSCFAQSet.size() - 1)) {
					FaqHtml += "'";
				} else {
					FaqHtml += "',";
				}
				// FaqHtml += "</b><p>";
				// FaqHtml += tSCFAQSchema1.getFAQContent();
				// FaqHtml += "</p></li>";

			}
			// FaqHtml += "</ul>";
			// FaqHtml += "</div>";
			// FaqHtml += "</div>";
			String articleId = tSCFAQSet.get(0).getRelaId();
			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			tZCArticleSchema.setID(articleId);
			tZCArticleSchema.fill();
			Transaction trans = new Transaction();
			saveCustomColumn(trans, tZCArticleSchema.getCatalogID(),
					tZCArticleSchema.getID(), false, FaqHtml);
			tZCArticleSchema.getStatus();
			trans.add(tZCArticleSchema, Transaction.UPDATE);
			if (tZCArticleSchema.getStatus() != 0) {
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
