package com.sinosoft.cms.stat.report;

import com.sinosoft.cms.dataservice.Advertise;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;

/**
 */
public class TopReport extends Page {
	public static void dgArticleDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (StringUtil.isEmpty(id)) {
			code = "";
		} else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopArticleHitData(code, dga.getPageSize(), dga.getPageIndex()));
	}

	public static void dgImageDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (StringUtil.isEmpty(id)) {
			code = "";
		} else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopImageHitData(code, dga.getPageSize(), dga.getPageIndex()));
	}

	public static void dgVideoDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (StringUtil.isEmpty(id)) {
			code = "";
		} else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopVideoHitData(code, dga.getPageSize(), dga.getPageIndex()));
	}

	public static void dgADDataBind(DataGridAction dga) {
		DataTable dt = getTopADData(dga.getParam("PositionID"));
		ReportUtil.addTrend(dt, "Leaf", "AD", "ID");
		dga.bindData(dt);
	}

	/**
	 * 获取指定栏目页面访问量排行
	 */
	public static DataTable getTopArticleHitData(String catalogInnerCode, int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Title,HitCount,StickTime,AddUser from ZCArticle where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc", Application.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	/**
	 * 获取指定栏目图片访问量排行
	 */
	public static DataTable getTopImageHitData(String catalogInnerCode, int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Name,HitCount,StickTime,AddUser from ZCImage where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc", Application.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	/**
	 * 获取指定栏目视频访问量排行
	 */
	public static DataTable getTopVideoHitData(String catalogInnerCode, int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Name,HitCount,StickTime,AddUser from ZCVideo where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc", Application.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	/**
	 * 获取广告排行
	 */
	public static DataTable getTopADData(String positionID) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,PositionID,ADType,ADName,HitCount from ZCAdvertisement where SiteID=?");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(positionID)) {
			qb.append(" and PositionID=?", positionID);
		}
		qb.append(" order by OrderFlag desc,HitCount desc");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			dt.decodeColumn("ADType", Advertise.ADTypes);
			Mapx map = new QueryBuilder("select ID,PositionName from ZCADPosition where SiteID=?", Application
					.getCurrentSiteID()).executeDataTable().toMapx(0, 1);
			dt.decodeColumn("PositionID", map);
		}
		return dt;
	}

	public static DataTable getADPositionList(Mapx params) {
		QueryBuilder qb = new QueryBuilder("select ID,PositionName from ZCADPosition where SiteID=?", Application
				.getCurrentSiteID());
		return qb.executeDataTable();
	}
}
