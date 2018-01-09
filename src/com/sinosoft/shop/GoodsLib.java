package com.sinosoft.shop;

import java.util.Date;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZSGoodsSchema;
import com.sinosoft.schema.ZSGoodsSet;

public class GoodsLib extends Page {
	public static Mapx initDialog(Mapx params) {
		params.put("goodsParentID", HtmlUtil.dataTableToOptions(CatalogUtil.getCatalogOptions((Catalog.SHOP_GOODS))));
		// params.put("brandParentID",
		// HtmlUtil.dataTableToOptions(CatalogUtil.getCatalogOptions((Catalog.SHOP_GOODS_BRAND),true));
		String catalogID = params.getString("CatalogID");
		params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		String catalogID = goods.getCatalogID() + "";
		// 处理自定义字段
		map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
				ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		return map;
	}

	// 得到商品的详细信息
	public static Mapx initGoodsInfo(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		map.put("imgpath", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + goods.getImage0()).replaceAll("//", "/"));

		String catalogID = goods.getCatalogID() + "";
		String sql = null;
		// 得到商品分类
		sql = "select name from zccatalog where id=?";
		String catalogName = new QueryBuilder(sql, goods.getCatalogID()).executeString();
		map.put("CatalogName", catalogName == null ? "没有所属分类" : catalogName);
		// 得到商品所属品牌
		sql = "select name from zccatalog where exists (select 1 from zcdocrela where docid=? and catalogType=? and catalogid=zccatalog.id)";
		String brandName = new QueryBuilder(sql, goods.getID(), Catalog.SHOP_GOODS_BRAND).executeString();
		map.put("BrandCatalogName", brandName == null ? "没有所属品牌" : brandName);
		// 处理自定义字段
		map.put("CustomColumn", ColumnUtil.getText(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
				ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		return map;
	}

	public static Mapx initEditLibDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String SearchContent = dga.getParam("SearchContent");

		QueryBuilder qb = new QueryBuilder(
				"select zsgoods.*,(select name from zccatalog c where c.id=zsgoods.catalogid) as CatalogIDName from zsgoods  where ");
		if (StringUtil.isEmpty(CatalogID)) {
			qb.append(" SiteID = ?", Application.getCurrentSiteID());
		} else {
			qb.append(" CatalogID = ?", CatalogID);
		}
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and Name like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by OrderFlag desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", Article.STATUS_MAP);
		if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("PublishDate").setDateFormat(DateUtil.Format_Date);
		}
		ColumnUtil.extendCatalogColumnData(dt, CatalogID);
		dga.bindData(dt);
	}

	public void setImageCover() {
		ZCImageSchema image = new ZCImageSchema();
		image.setID($V("ID"));
		image.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(image.getCatalogID());
		catalog.fill();
		// catalog.setImageID($V("ID"));
		if (catalog.update()) {
			this.Response.setLogInfo(1, "设置专辑封面成功！");
		} else {
			this.Response.setLogInfo(0, "设置专辑封面失败！");
		}
	}

	public void GoodsLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.Request);
		catalog.fill();
		catalog.setValue(this.Request);
		if (catalog.update()) {
			this.Response.setLogInfo(1, "修改分类成功！");
		} else {
			this.Response.setLogInfo(0, "修改分类失败！");
		}
	}

	public void addLib() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(Application.getCurrentSiteID());
		// parentID 为0表示上级节点是站点
		if (StringUtil.isEmpty(parentID)) {
			catalog.setParentID(0);
			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", 4));
			catalog.setTreeLevel(1);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setImageLibCount(site.getImageLibCount() + 1);
			trans.add(site, Transaction.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();

			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", pCatalog.getInnerCode(), 4));
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			trans.add(pCatalog, Transaction.UPDATE);
		}

		catalog.setName(name);
		catalog.setURL(" ");
		catalog.setAlias(StringUtil.getChineseFirstAlpha(name));
		catalog.setType(Catalog.SHOP_GOODS);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setOrderFlag(OrderUtil.getDefaultOrder());
		catalog.setLogo("");
		catalog.setListPageSize(10);
		catalog.setListPage(10);// ----
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		if (trans.commit()) {
			this.Response.setLogInfo(1, "新建分类成功!!!");
		} else {
			this.Response.setLogInfo(0, "新建分类失败!!!");
		}
	}

	public void delLib() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String catalogId = $V("catalogID");
		catalog.setID(catalogId);
		if (!catalog.fill()) {
			this.Response.setLogInfo(0, "没有这个分类！");
			return;
		}
		// 得到栏目下面所有的商品
		ZSGoodsSet goodsSet = new ZSGoodsSchema().query(new QueryBuilder("where CatalogInnerCode like '"
				+ catalog.getInnerCode() + "%'"));
		System.err.println(goodsSet.size());
		if (goodsSet.size() != 0) {
			this.Response.setLogInfo(1, "该分类下面有商品，请先删除商品！");
			return;
		} else {
			ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder("where InnerCode like '"
					+ catalog.getInnerCode() + "%'"));
			Transaction trans = new Transaction();
			trans.add(catalogSet, Transaction.DELETE);
			if (trans.commit()) {
				this.Response.setLogInfo(1, "删除该分类成功！");
			} else {
				this.Response.setLogInfo(0, "删除该分类失败，请重试!");
			}
		}
	}

	public void transfer() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		long catalogID = Long.parseLong($V("CatalogID"));
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			Response.setLogInfo(0, "The Catalog you selected is not existsed!");
			return;
		}
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update zsgoods set CatalogID = ?,CatalogInnerCode = ?,BranchCode = ?,BranchInnerCode = ?,SiteID = ? where ID in ("
						+ IDs + ")");
		qb.add(catalogID);
		qb.add(catalog.getInnerCode());
		// qb.add(catalog.getBranchCode());
		qb.add(catalog.getBranchInnerCode());
		qb.add(Application.getCurrentSiteID());
		trans.add(qb);
		if (trans.commit()) {
			Response.setLogInfo(1, "转移成功");
		} else {
			Response.setLogInfo(0, "转移失败");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = params.getString("ParentLevel");
		String parentID = params.getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(Catalog.SHOP_GOODS);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type = ? and SiteID =? and TreeLevel-1 <=?  order by orderflag,innercode");
			qb.add(Catalog.SHOP_GOODS);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.SHOP_GOODS, SiteID, ta.getLevel());
		}
		ta.setRootText("商品库");
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log("Goods", "Publish_Goods", "商品发布失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in(" + ids + ")"));
		StringBuffer logs = new StringBuffer("发布商品:");
		for (int i = 0; i < set.size(); i++) {
			logs.append(set.get(i).getName() + ",");
		}
		UserLog.log("Goods", "Publish_Goods", logs + "成功", Request.getClientIP());

		Response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", "" + id);
	}

	private long publishSetTask(final ZSGoodsSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishGoods(set, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * 商品下线
	 */
	public void down() {
		String ids = $V("GoodsIDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();
		// dealGoodsHistory(ids, trans, "DOWN", "下线处理");
		trans.add(new QueryBuilder("update zsgoods set Status = " + Article.STATUS_OFFLINE
				+ ",TopFlag='0',DownlineDate = ?,modifyTime=? where id in(" + ids + ")", now, now));
		if (trans.commit()) {
			ZSGoodsSchema site = new ZSGoodsSchema();
			ZSGoodsSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
			downTask(set);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}
	}

	private long downTask(final ZSGoodsSet set) {
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
						//if (StringUtil.isEmpty(listpage) || "0".equals(listpage) || "-1".equals(listpage)) {
						if (StringUtil.isEmpty(listpage)) {
							listpage = "20"; // 默认只发布前二十页
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()), false, false, true, Integer.parseInt(listpage));
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

	/**
	 * 商品上线
	 */
	public void up() {
		String ids = $V("GoodsIDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();
		// dealArticleHistory(ids, trans, "UP", "上线处理");
		trans.add(new QueryBuilder("update zsgoods set Status =" + Article.STATUS_PUBLISHED
				+ ",PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where id in(" + ids + ")", now));
		if (trans.commit()) {
			upTask(ids);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				ZSGoodsSchema site = new ZSGoodsSchema();
				ZSGoodsSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
				if (set != null && set.size() > 0) {
					p.publishGoods(set, false, this);
					p.publishCatalog(set.get(0).getCatalogID(), false, false , true);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void treeDiagDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.SHOP_GOODS;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());

		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.SHOP_GOODS, siteID, ta.getLevel());
		}
		ta.setRootText("商品库");

		String siteName = "文档库";
		if (catalogType == Catalog.SHOP_GOODS) {
			siteName = "商品库";
		}
		if (catalogType == Catalog.SHOP_GOODS_BRAND) {
			siteName = "商品品牌库";
		}
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}
		// dt = dt.filter(new Filter() {
		// public boolean filter(Object obj) {
		// DataRow dr = (DataRow) obj;
		// return Priv.getPriv(User.getUserName(), Priv.ARTICLE,
		// dr.getString("InnerCode"), Priv.ARTICLE_MANAGE);
		// }
		// });
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	/**
	 * 转移商品
	 */
	public void move() {
		String goodsIDs = $V("GoodsIDs");
		if (!StringUtil.checkID(goodsIDs)) {
			Response.setError("操作数据库时发生错误!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(catalogID)) {
			Response.setError("传入CatalogID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZSGoodsSchema srcGoods = new ZSGoodsSchema();
		ZSGoodsSet set = srcGoods.query(new QueryBuilder("where id in (" + goodsIDs + ")"));
		long srcCatalogID = 0;

		String[] srcGoodsIDs = null;
		if (set.size() > 0) {
			srcGoodsIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcGoodsIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZSGoodsSchema goods = set.get(i);
			srcCatalogID = goods.getCatalogID();
			String destCatalogID = catalogID;
			trans.add(new QueryBuilder("update zccatalog set total = total+1 where id=?", destCatalogID));
			trans.add(new QueryBuilder("update zccatalog set total = total-1 where id=?", srcCatalogID));
			goods.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			goods.setCatalogID(catalogID);
			goods.setOrderFlag(OrderUtil.getDefaultOrder());
			trans.add(goods, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Publisher p = new Publisher();
			// 删除原有文章
			p.deletePubishedFile(set);
			// 重新生成新文章，重新生成原来目录的列表
			if (p.publishGoods(set, true, null) && p.publishCatalog(srcCatalogID, false, false , true)) {
				Response.setMessage("转移成功");
			} else {
				Response.setError("转移成功,发布失败。" + Errorx.printString());
			}
		} else {
			Response.setError("操作数据库时发生错误!");
		}
	}

	public void copy() {
		String catalogIDs = $V("CatalogIDs");
		ZCCatalogSet catalogs = new ZCCatalogSchema().query(new QueryBuilder("where ID in (" + catalogIDs + ")"));
		for (int i = 0; i < catalogs.size(); i++) {
			String IDs = $V("GoodsIDs");
			if (!StringUtil.checkID(IDs)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			ZSGoodsSet goodsSet = new ZSGoodsSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
			for (int j = 0; j < goodsSet.size(); j++) {
				ZSGoodsSchema goods = goodsSet.get(j);
				goods.setID(NoUtil.getMaxID("ID"));
				goods.setCatalogID(catalogs.get(i).getID());
				goods.setCatalogInnerCode(catalogs.get(i).getInnerCode());
				goods.setBranchInnerCode(catalogs.get(i).getBranchInnerCode());
				goods.setSiteID(Application.getCurrentSiteID());
				goods.setAddTime(new Date());
				goods.setAddUser(User.getUserName());
			}
			if (goodsSet.insert()) {
				this.Response.setLogInfo(1, "复制成功");
			} else {
				this.Response.setLogInfo(0, "复制失败");
			}
		}
	}

	public static void dg2DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = "0";
			dga.getParams().put("CatalogID", catalogID);
		}

		QueryBuilder qb = new QueryBuilder("select * from ZSGoods where CatalogID=?", catalogID);

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
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd");
		}

		dga.bindData(dt);
	}

	/**
	 * 对文章进行排序,文章默认排序值是时间戳
	 */
	public void sortGoods() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		Transaction tran = new Transaction();

		OrderUtil.updateOrder("ZSGoods", "OrderFlag", type, target, orders, null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id).getString("ListPage");
					//if (StringUtil.isEmpty(listpage) || "0".equals(listpage) || "-1".equals(listpage)) {
					if (StringUtil.isEmpty(listpage)) {
						listpage = "20"; // 默认只发布前二十页
					}
					p.publishCatalog(Long.parseLong(id), false, false, true, Integer.parseInt(listpage));
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

	/*
	 * public void cancellTopper(){ String IDs = $V("IDs"); String typeTop =
	 * $V("typeTop"); String[] ids = IDs.split(","); Transaction trans = new
	 * Transaction(); if ("Special".equalsIgnoreCase(typeTop)) { for (int i = 0;
	 * i < ids.length; i++) { ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID(ids[i]); if(goods.fill()){ goods.setIsSpecial("N");
	 * trans.add(goods, Transaction.UPDATE); } } } if
	 * ("Hot".equalsIgnoreCase(typeTop)) { for (int i = 0; i < ids.length; i++)
	 * { ZSGoodsSchema goods = new ZSGoodsSchema(); goods.setID(ids[i]);
	 * if(goods.fill()){ goods.setIsHot("N"); trans.add(goods,
	 * Transaction.UPDATE); } } } if ("New".equalsIgnoreCase(typeTop)) { for
	 * (int i = 0; i < ids.length; i++) { ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID(ids[i]); if(goods.fill()){
	 * goods.setIsNew("N"); trans.add(goods, Transaction.UPDATE); } } } if
	 * ("Month".equalsIgnoreCase(typeTop)) { for (int i = 0; i < ids.length;
	 * i++) { ZSGoodsSchema goods = new ZSGoodsSchema(); goods.setID(ids[i]);
	 * if(goods.fill()){ goods.setIsSaleMonth("N"); trans.add(goods,
	 * Transaction.UPDATE); } } } if ("Week".equalsIgnoreCase(typeTop)) { for
	 * (int i = 0; i < ids.length; i++) { ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID(ids[i]); if(goods.fill()){
	 * goods.setIsSaleWeek("N"); trans.add(goods, Transaction.UPDATE); } } } if
	 * (trans.commit()) { Response.setLogInfo(1,"设置成功"); } else {
	 * Response.setLogInfo(0,"设置失败"); } }
	 */
}
