package com.sinosoft.cms.stat.report;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;

import java.util.Date;

/**
 */
public class URLReport extends Page {

	public static void dgTopDataBind(DataGridAction dga) {
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
		DataTable dt = getTopURLData(Application.getCurrentSiteID(), start, end);
		ReportUtil.addTrend(dt, "URL", "Top");
		dga.bindData(dt);
	}

	public static void dgEntranceDataBind(DataGridAction dga) {
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
		DataTable dt = getEntranceData(Application.getCurrentSiteID(), start, end);
		ReportUtil.addTrend(dt, "URL", "Entrance");
		dga.bindData(dt);
	}

	public static void dgExitDataBind(DataGridAction dga) {
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
		DataTable dt = getExitData(Application.getCurrentSiteID(), start, end);
		ReportUtil.addTrend(dt, "URL", "Exit");
		dga.bindData(dt);
	}

	/**
	 * 获取URL排行
	 */
	public static DataTable getTopURLData(long siteID, Date start, Date end) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=?");
		qb.add(siteID);
		qb.add("Global");
		qb.add("PV");
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executeDataTable();
		dt = ReportUtil.toItemTable(dt, start, end);
		int total = dt.getRowCount() == 0 ? 0 : dt.getInt(0, "PV");
		qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=? order by SiteID");// 为兼容SQLServer必须加上order
		// by
		qb.add(siteID);
		qb.add("URL");
		qb.add("Top");
		qb.add(period1);
		qb.add(period2);
		dt = qb.executePagedDataTable(100, 0);
		dt = ReportUtil.toItemTable(dt, start, end);
		dt.sort("Top");
		dt.insertColumn("Rate");
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			int c = dt.getInt(i, 1);
			if (c == 0) {
				dt.deleteRow(i);
			} else {
				dt.set(i, "Rate", NumberUtil.round(c * 100.0 / total, 2) + "%");
			}
		}
		return dt;
	}

	/**
	 * 获取入口排行
	 */
	public static DataTable getEntranceData(long siteID, Date start, Date end) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=?");
		qb.add(siteID);
		qb.add("URL");
		qb.add("Entrance");
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executePagedDataTable(100, 0);
		dt = ReportUtil.toItemTable(dt, start, end);
		dt.sort("Entrance");
		dt.insertColumn("Rate");
		int total = 0;
		for (int i = 0; i < dt.getRowCount(); i++) {
			total += dt.getInt(i, 1);
		}
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			int c = dt.getInt(i, 1);
			if (c == 0) {
				dt.deleteRow(i);
			} else {
				dt.set(i, "Rate", NumberUtil.round(c * 100.0 / total, 2) + "%");
			}
		}
		return dt;
	}

	/**
	 * 获取出口排行
	 */
	public static DataTable getExitData(long siteID, Date start, Date end) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=? order by SiteID");
		qb.add(siteID);
		qb.add("URL");
		qb.add("Exit");
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executePagedDataTable(100, 0);
		dt = ReportUtil.toItemTable(dt, start, end);
		dt.sort("Exit");
		ReportUtil.computeRate(dt, "Exit", "Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		return dt;
	}
}
