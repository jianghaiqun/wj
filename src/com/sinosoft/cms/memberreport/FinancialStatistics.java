/**
 * Project Name:wj
 * File Name:FinancialStatistics.java
 * Package Name:com.sinosoft.cms.memberreport
 * Date:2016年10月26日下午7:21:53
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.cms.memberreport;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName:FinancialStatistics <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年10月26日 下午7:21:53 <br/>
 *
 * @author:liuhongyu
 */
public class FinancialStatistics extends Page {

	public static void select(DataGridAction dga1) {

		// 查询时间
		String standardDateStart = dga1.getParams().getString("standardDateStart");
		String standardDateEnd = dga1.getParams().getString("standardDateEnd");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		fmt.setLenient(false);
		if (StringUtil.isEmpty(standardDateStart)) {
			standardDateStart = fmt.format(new Date());
		}
		if (StringUtil.isEmpty(standardDateEnd)) {
			standardDateEnd = fmt.format(new Date());
		}

		// 当日投保成功保费总额
		String sql1 = "SELECT sum(convert(r.timePrem, decimal(20, 2))) as sumtimePrem ,o.channelsn,p.partnerName "
				+ "FROM sdorders o,sdinformationrisktype r,partnerinfo p , partnerpolicyreq q "
				+ "WHERE o.ordersn = r.ordersn "
				+ "AND o.channelsn = p.channelsn "
				+ "AND DATE(q.createdatetime) >='" + standardDateStart + "' AND DATE(q.createdatetime) <='"+standardDateEnd+"' "
				+ "and r.appStatus in ('1','2','3','4') "
				+ "and q.ordersn = o.orderSn "
				+ "group by p.id ";

		String sql2 = "SELECT sum(convert(b.PayAmount, decimal(20, 2))) as sumPayAmount, sum(convert(b.IncomeAmount, decimal(20, 2))) as sumIncomeAmount,b.ChannelSn "
				+ "FROM PartnerAccountBalanceRecord b,sdorders o,partnerinfo p,partnerpolicyreq q "
				+ "WHERE q.fundTransferSn = b.FundTransferSn "
				+ "AND q.paysn = o.paySn "
				+ "AND b.ChannelSn = p.channelSn "
				+ "AND p.type = 'asyn' "
				+ "AND DATE(q.createdatetime) >='" + standardDateStart + "' AND DATE(q.createdatetime) <='"+standardDateEnd+"' "
				+ "AND q.isInsureSuccess = '1' "
				+ "GROUP BY p.id ";

		String sql3 = "SELECT sum(convert(f.total, decimal(20, 2))) as fsumtotal, p.partnerName,o.channelsn "
				+ "FROM sdorders o,sdinformationrisktype r,partnerinfo p ,financinginfo f ,partnerrefundapply y "
				+ "WHERE o.ordersn = r.ordersn "
				+ "AND o.channelsn = p.channelsn "
				+ "AND f.ordersn = o.ordersn "
				+ "AND DATE(y.createdate) >='" + standardDateStart + "' AND DATE(y.createdate) <='"+standardDateEnd+"' "
				+ "AND r.appstatus in ('2','3','4') "
				+ "and y.pordersn = o.paysn "
				+ "GROUP BY p.id ";

		// SELECT sum(convert(f.total, decimal(20, 2))) as fsumtotal,
		// p.partnerName,o.channelsn
		// FROM sdorders o,sdinformationrisktype r,partnerinfo p ,financinginfo
		// f
		// WHERE o.ordersn = r.ordersn
		// AND o.channelsn = p.channelsn
		// AND f.ordersn = o.ordersn
		// AND DATE(r.cancelDate) = '2016-11-30'
		// AND r.appstatus in ('2','3','4')
		// GROUP BY p.id

		// 当日合作方已划拨金额
		String sql4 = "select sum(convert(u.total, decimal(20, 2))) as usumtotal,p.channelsn "
				+ "from PartnerRefundFundsRecord u,partnerinfo p ,sdinformationrisktype r , sdorders o ,partnerrefundapply y  "
				+ "where u.channelsn = p.channelsn "
				+ "and u.pordersn = o.paysn "
				+ "and r.ordersn = o.ordersn "
				+ "and p.type = 'asyn' "
				+ "and u.DealResult = '0' "
				+ "AND DATE(y.CreateDate) >='" + standardDateStart + "' AND DATE(y.CreateDate) <='"+standardDateEnd+"' "
				+ "AND y.pordersn = o.paysn "
				+ "GROUP BY p.id ";

		String sqlPaterName = "select channelsn,partnerName from partnerInfo where type = 'asyn' ";

		String sqla = "select sql1.channelsn,sql1.partnerName,sql2.sumtimePrem from "
				+ " ( "
				+ sqlPaterName
				+ ") as sql1 "
				+ "LEFT JOIN "
				+ " ( "
				+ sql1
				+ ") as sql2 "
				+ "on sql1.channelsn = sql2.channelsn ";
																			
		String sqlb = "select sql3.channelsn,sql3.partnerName,sql3.sumtimePrem,(convert(sql4.sumIncomeAmount, decimal(20, 2)) - convert(sql4.sumPayAmount, decimal(20, 2))) as Income from "
				+ " ( "
				+ sqla
				+ " ) as sql3 "
				+ " LEFT JOIN "
				+ " ( "
				+ sql2
				+ " ) as sql4 "
				+ " on sql3.channelsn = sql4.channelsn ";

		String sqlc = "select sql5.channelsn,sql5.partnerName,sql5.sumtimePrem,sql5.Income,sql6.fsumtotal from "
				+ " ( "
				+ sqlb
				+ ") as sql5 "
				+ "LEFT JOIN "
				+ " ( "
				+ sql3
				+ ") as sql6 "
				+ "on sql5.channelsn = sql6.channelsn ";

		String sql = "select sql7.channelsn,sql7.partnerName,sql7.sumtimePrem,sql7.Income,sql7.fsumtotal,sql8.usumtotal from "
				+ " ( "
				+ sqlc
				+ " ) as sql7 "
				+ "LEFT JOIN "
				+ " ( "
				+ sql4
				+ " ) as sql8 "
				+ "on sql7.channelsn = sql8.channelsn ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();

		for (int i = 0; i < dt.getRowCount(); i++) {

			if (StringUtil.isEmpty(dt.getString(i, "channelsn"))) {
				dt.set(i, "channelsn", "null");
			}
			if (StringUtil.isEmpty(dt.getString(i, "partnerName"))) {
				dt.set(i, "partnerName", "null");
			}
			if (StringUtil.isEmpty(dt.getString(i, "sumtimePrem"))) {
				dt.set(i, "sumtimePrem", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "Income"))) {
				dt.set(i, "Income", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "fsumtotal"))) {
				dt.set(i, "fsumtotal", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "usumtotal"))) {
				dt.set(i, "usumtotal", 0);
			}
		}
		dga1.bindData(dt);

	}

	public static void Insure(DataGridAction dga2) {

		// 查询时间
		String channelsn = dga2.getParams().getString("channelsn");
		String standardDateStart = dga2.getParams().getString("standardDateStart");
		String standardDateEnd = dga2.getParams().getString("standardDateEnd");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		fmt.setLenient(false);
		if (StringUtil.isEmpty(standardDateStart)) {
			standardDateStart = fmt.format(new Date());
		}
		if (StringUtil.isEmpty(standardDateEnd)) {
			standardDateEnd = fmt.format(new Date());
		}
		String sql1 = "SELECT r.timePrem ,o.channelsn,p.partnerName ,o.orderSn ,r.PolicyNo "
				+ "FROM sdorders o,sdinformationrisktype r,partnerinfo p ,partnerpolicyreq q "
				+ "WHERE o.ordersn = r.ordersn "
				+ "AND o.channelsn = p.channelsn "
				+ "AND DATE(q.createdatetime) >='" + standardDateStart + "' AND DATE(q.createdatetime) <='"+standardDateEnd+"' "
				+ "and p.channelsn = '" + channelsn + " ' "
				+ "and r.appStatus in ('1','2','3','4') "
				+ "and q.ordersn = o.orderSn";

		String sql2 = "SELECT b.PayAmount,b.IncomeAmount,b.ChannelSn ,o.orderSn "
				+ "FROM PartnerAccountBalanceRecord b,sdorders o,partnerinfo p,partnerpolicyreq q "
				+ "WHERE q.fundTransferSn = b.FundTransferSn "
				+ "AND q.paysn = o.paySn "
				+ "AND b.ChannelSn = p.channelSn "
				+ "AND p.type = 'asyn' "
				+ "AND DATE(q.createdatetime) >='" + standardDateStart + "' AND DATE(q.createdatetime) <='"+standardDateEnd+"' "
				+ "AND q.isInsureSuccess = '1' ";

		String sql = "SELECT dt1.partnerName,dt1.timePrem,(dt2.IncomeAmount - dt2.PayAmount) as Income ,dt1.orderSn ,dt1.PolicyNo FROM "
				+ " ( " + sql1 + " ) AS dt1 "
				+ " LEFT JOIN "
				+ " ( " + sql2
				+ " ) AS dt2 ON dt1.ordersn = dt2.ordersn ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {

			if (StringUtil.isEmpty(dt.getString(i, "partnerName"))) {
				dt.set(i, "partnerName", "null");
			}
			if (StringUtil.isEmpty(dt.getString(i, "timePrem"))) {
				dt.set(i, "timePrem", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "Income"))) {
				dt.set(i, "Income", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "orderSn"))) {
				dt.set(i, "orderSn", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "PolicyNo"))) {
				dt.set(i, "PolicyNo", 0);
			}
		}
		dga2.bindData(dt);

	}

	public static void Refund(DataGridAction dga3) {
		String channelsn = dga3.getParams().getString("channelsn");
		// 查询时间
		String standardDateStart = dga3.getParams().getString("standardDateStart");
		String standardDateEnd = dga3.getParams().getString("standardDateEnd");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		fmt.setLenient(false);
		if (StringUtil.isEmpty(standardDateStart)) {
			standardDateStart = fmt.format(new Date());
		}
		if (StringUtil.isEmpty(standardDateEnd)) {
			standardDateEnd = fmt.format(new Date());
		}
		
		
		String sql3 = "SELECT f.total as total1, p.partnerName,o.channelsn ,o.ordersn ,r.PolicyNo "
				+ "FROM sdorders o,sdinformationrisktype r,partnerinfo p ,financinginfo f ,partnerrefundapply y "
				+ "WHERE o.ordersn = r.ordersn "
				+ "AND o.channelsn = p.channelsn "
				+ "AND f.ordersn = o.ordersn "
				+ "AND DATE(y.CreateDate) >='" + standardDateStart + "' AND DATE(y.CreateDate) <='"+standardDateEnd+"' "
				+ "and p.channelsn = '" + channelsn + " ' "
				+ "AND r.appstatus in ('2','3','4') "
				+ "and y.pordersn = o.paysn ";

		String sql4 = "select u.total as total2 , u.RefundAplaySn , u.TradeDate , p.channelsn , o.ordersn "
				+ "from PartnerRefundFundsRecord u,partnerinfo p ,sdinformationrisktype r , sdorders o ,partnerrefundapply y "
				+ "where u.channelsn = p.channelsn "
				+ "and u.pordersn = o.paysn "
				+ "and r.ordersn = o.ordersn "
				+ "and p.type = 'asyn' "
				+ "and u.DealResult = '0' "
				+ "AND DATE(y.CreateDate) >='" + standardDateStart + "' AND DATE(y.CreateDate) <='"+standardDateEnd+"' "
				+ "and y.pordersn = o.paysn ";

		String sql = "SELECT dt3.partnerName,DT3.ordersn,dt3.total1,dt4.total2 ,dt4.RefundAplaySn , dt4.TradeDate ,dt3.PolicyNo from "
				+ " ( "
				+ sql3
				+ " ) AS dt3 "
				+ " LEFT JOIN "
				+ " ( "
				+ sql4
				+ " ) AS dt4 ON dt3.ordersn = dt4.ordersn ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {

			if (StringUtil.isEmpty(dt.getString(i, "partnerName"))) {
				dt.set(i, "partnerName", "null");
			}
			if (StringUtil.isEmpty(dt.getString(i, "orderSn"))) {
				dt.set(i, "orderSn", "null");
			}
			if (StringUtil.isEmpty(dt.getString(i, "total1"))) {
				dt.set(i, "total1", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "total2"))) {
				dt.set(i, "total2", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "RefundAplaySn"))) {
				dt.set(i, "RefundAplaySn", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "TradeDate"))) {
				dt.set(i, "TradeDate", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "PolicyNo"))) {
				dt.set(i, "PolicyNo", 0);
			}
		}
		dga3.bindData(dt);
	}
}
