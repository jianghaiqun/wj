package com.sinosoft.cms.site;

import java.util.List;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.schema.ZCImagePlayerSchema;
import com.sinosoft.schema.ZCImagePlayerSet;
import com.sinosoft.schema.ZCImageRelaSchema;
import com.sinosoft.schema.ZCImageRelaSet;

/**
 * 图片拨放器
 * 
 */
public class ImagePlayer extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String InnerCode = dga.getParam("CatalogInnerCode");
		if (StringUtil.isEmpty(InnerCode) || InnerCode == null || InnerCode.equalsIgnoreCase("null")) {
			InnerCode = "0";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ZCImagePlayer.*,(SELECT Name from ZCCatalog where ZCCatalog.InnerCode = ZCImagePlayer.RelaCatalogInnerCode) as CatalogName from ZCImagePlayer where SiteID=? ",
				Application.getCurrentSiteID());
		if (!InnerCode.equals("0")) {
			qb.append(" and RelaCatalogInnerCode = ? ", InnerCode);
		}
		qb.append(" Order by RelaCatalogInnerCode asc ");
		
		dga.setTotal(qb);
		
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), Priv.SITE, "" + Application.getCurrentSiteID(), Priv.SITE_MANAGE)) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogInnerCode = dr.getString("RelaCatalogInnerCode");
				if ("0".equals(RelaCatalogInnerCode)) {
					return Priv.getPriv(User.getUserName(), Priv.SITE, "" + Application.getCurrentSiteID(),
							Priv.ARTICLE_MANAGE);
				} else {
					return Priv.getPriv(User.getUserName(), Priv.ARTICLE, RelaCatalogInnerCode, Priv.ARTICLE_MODIFY);
				}
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt.getRowCount()
				&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
			newdt.insertRow(dt.getDataRow(i));
		}
		for (int i = 0; i < newdt.getRowCount(); i++) {
			if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
				newdt.set(i, "CatalogName", "文档库");
			}
		}
	
		dga.bindData(newdt);
	}

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and "
							+ " exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? and "
							+ " exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

		String siteName = "文档库";
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_MANAGE);
			}
		});
		ta.setRootText(siteName);
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
		ZCImageRelaSet imageSet = imageRela.query(new QueryBuilder(" where relaid in (" + ids + ") and RelaType = ?",
				ImagePlayerRela.RELATYPE_IMAGEPLAYER));
		trans.add(imageSet, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
