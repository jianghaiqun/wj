package com.sinosoft.cms.stat.report;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;

import java.util.Date;

public class CatalogReport extends Page {

	public void getChartData() {
		String code = $V("Code");
		if (!StringUtil.verify(code, "Number") || StringUtil.isEmpty(code) || code.equals("null")) {
			code = "";
		}
		Date start = DateUtil.parse($V("StartDate"));
		Date end = DateUtil.parse($V("EndDate"));
		DataTable dt = getCatalogHitData(Application.getCurrentSiteID(), start, end, code);
		dt.deleteColumn("StickTime");
		dt.deleteColumn("Item");
		Object[] vs = dt.getColumnValues("PV");
		dt.deleteColumn("PV");
		dt.insertColumn("PV", vs);
		String xml = ChartUtil.getPie3DChart(dt, 8);
		$S("Data", xml);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String code = dga.getParam("Code");
		if (!StringUtil.verify(code, "Number") || StringUtil.isEmpty(code) || code.equals("null")) {
			code = "";
		}
		String startDate = dga.getParam("StartDate");
		String endDate = dga.getParam("EndDate");
		Date start = null;
		Date end = null;
		if (StringUtil.isEmpty(startDate)) {
			start = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L);
			end = new Date();
		} else {
			start = DateUtil.parse(startDate);
			end = DateUtil.parse(endDate);
		}
		DataTable dt = getCatalogHitData(Application.getCurrentSiteID(), start, end, code);
		dt.sort("PV");

		// 判断是否有子节点
		StringBuffer sb = new StringBuffer("''");
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(",'");
			sb.append(dt.getString(i, "Item"));
			sb.append("'");
		}
		DataTable dt2 = new QueryBuilder("select InnerCode from ZCCatalog where isLeaf=0 and InnerCode in (" + sb + ")")
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt2.getRowCount(); j++) {
				if (dt.getString(i, "Item").equals(dt2.getString(j, 0))) {
					dt.set(i, "ItemName", "<a href='Catalog.jsp?Code=" + dt.getString(i, "Item") + "'>"
							+ dt.getString(i, "ItemName") + "</a>");
				}
			}
		}
		ReportUtil.computeRate(dt, "PV", "Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		ReportUtil.addTrend(dt, "Catalog", "PV");
		dga.bindData(dt);
	}

	/**
	 * 获取指定栏目下一级子栏目访问量排行
	 */
	public static DataTable getCatalogHitData(long siteID, Date start, Date end, String catalogInnerCode) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type=? and SubType in ('PV','StickTime') and Period>=? and Period<=? and Item like '"
						+ catalogInnerCode + "%'");
		if (Config.isSQLServer() || Config.isSybase()) {
			qb.append(" and len(Item)=?");
		} else {
			qb.append(" and length(Item)=?");
		}
		qb.add(siteID);
		qb.add("Catalog");
		qb.add(period1);
		qb.add(period2);
		qb.add(catalogInnerCode.length() + 6);
		DataTable dt = qb.executeDataTable();
		dt = ReportUtil.toItemTable(dt, start, end);
		dt.sort("PV");
		dt.insertColumn("ItemName");
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			String name = CatalogUtil.getNameByInnerCode(dt.getString(i, "Item"));
			if (StringUtil.isEmpty(name)) {// 有可能被删除了
				QueryBuilder qb2 = new QueryBuilder(
						"select Name from BZCCatalog where InnerCode=? order by BackupNo desc", dt.getString(i, "Item"));
				name = qb2.executeString();
			}
			dt.set(i, "ItemName", name);
			if (dt.getInt(i, "PV") == 0) {
				dt.deleteRow(i);
			}
		}
		return dt;
	}
}
