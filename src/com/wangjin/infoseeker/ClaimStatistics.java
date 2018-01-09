/**
 * Project Name:wj File Name:ClaimStatistics.java Package
 * Name:com.wangjin.infoseeker Date:2016年10月12日上午10:15:18 Copyright (c) 2016,
 * www.kaixinbao.com All Rights Reserved.
 *
 */

package com.wangjin.infoseeker;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.antlr.grammar.v3.ANTLRv3Parser.element_return;

import com.google.zxing.FormatException;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.InitiateRefundSchema;
import com.sinosoft.schema.InitiateRefundSet;
import com.sinosoft.schema.claimstatisticsdetailsSchema;
import com.sinosoft.schema.claimstatisticsdetailsSet;

/**
 * ClassName: ClaimStatistics <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选,不用就删除). <br/>
 * date: 2016年10月12日 上午10:15:18 <br/>
 *
 * @author dongsheng 
 * @version
 */
public class ClaimStatistics extends Page {

	public static Mapx<String, Object> initStaff(Mapx<String, Object> params) {

		// 公司
		QueryBuilder qbCompany = new QueryBuilder(
				"SELECT codename,codevalue FROM zdcode WHERE codetype = 'SupplierCode' and parentcode = 'SupplierCode' order by CONVERT( codename USING gbk )");
		DataTable dtCompany = qbCompany.executeDataTable();
		params.put("company",
				HtmlUtil.dataTableToOptions(dtCompany, false));

		Date endDate = new Date();
		Date startDate = DateUtil.addYear(endDate, -1);
		params.put("startDate", DateUtil.toString(startDate));
		params.put("endDate", DateUtil.toString(endDate));
		return params;
	}

	/**
	 * 获得发布统计的数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {

		String startDateStr = dga.getParam("startDate");
		String endDateStr = dga.getParam("endDate");
		String codeValue = dga.getParam("company");
		String channelStr = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelStr)&&((channelStr.indexOf("xb2b_ht")>0)
				||(channelStr.indexOf("xb2c_pc")>0)||(channelStr.indexOf("xb2c_wap")>0))){
			channelStr = channelStr.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		Date startDate, endDate;
		String[] channels;
		// 日期空的情况 为页面初始化,起始时间都置为去年今天，截止时间都置为今天

		if (!StringUtil.isEmpty(endDateStr) && DateUtil.isLegalDate(startDateStr)) {
			endDate = DateUtil.parse(endDateStr);
		} else {
			endDate = null;
		}

		if (!StringUtil.isEmpty(startDateStr) && DateUtil.isLegalDate(startDateStr)) {
			startDate = DateUtil.parse(startDateStr);
		} else {
			startDate = null;
		}

		String sql = "select c.id,c.policyNo,claimsItemsName,claimsAmount,date_format(claimsDate,'%Y-%m-%d') claimsDate,n.ChannelName,t.timePrem,z.CodeName "
				+ "from claimstatisticsdetails c "
				+ "LEFT JOIN sdinformationrisktype t ON c.policyNo=t.policyNo "
				+ "LEFT JOIN sdorders o ON t.orderSn=o.orderSn "
				+ "LEFT JOIN channelinfo n ON o.channelsn=n.ChannelCode "
				+ "LEFT JOIN sdproduct p ON t.riskCode=p.ProductID "
				+ "LEFT JOIN zdcode z ON z.CodeValue=p.Remark6 and codetype = 'SupplierCode' and parentcode = 'SupplierCode' "
				+ "WHERE 1=1 ";

		String timePremSumSql = "select SUM(timePrem) "
				+ "FROM sdinformationrisktype t "
				+ "LEFT JOIN sdorders o ON t.orderSn=o.orderSn "
				+ "LEFT JOIN sdproduct p ON t.riskCode=p.ProductID "
				+ "LEFT JOIN zdcode z ON z.CodeValue=p.Remark6 and codetype = 'SupplierCode' and parentcode = 'SupplierCode' "
				+ "WHERE appStatus like '1' ";

		String amountSumSql = "select SUM(claimsAmount) "
				+ "from claimstatisticsdetails c "
				+ "LEFT JOIN sdinformationrisktype t ON c.policyNo=t.policyNo "
				+ "LEFT JOIN sdorders o ON t.orderSn=o.orderSn "
				+ "LEFT JOIN sdproduct p ON t.riskCode=p.ProductID "
				+ "LEFT JOIN zdcode z ON z.CodeValue=p.Remark6 and codetype = 'SupplierCode' and parentcode = 'SupplierCode' "
				+ "WHERE 1=1 ";

		QueryBuilder qb = new QueryBuilder(sql);
		QueryBuilder qbAmountSum = new QueryBuilder(amountSumSql);
		QueryBuilder qbTimePremSum = new QueryBuilder(timePremSumSql);
		if (startDate != null) {
			qb.append("and claimsDate >= DATE(?) ", startDate);
			qbAmountSum.append("and claimsDate >= DATE(?) ", startDate);
			qbTimePremSum.append("and DATE(svaliDate) >= DATE(?) ", startDate);
		}
		if (endDate != null) {
			qb.append("and claimsDate <= DATE(?) ", endDate);
			qbAmountSum.append("and claimsDate <= DATE(?) ", endDate);
			qbTimePremSum.append("and DATE(svaliDate) <= DATE(?) ", endDate);
		}
		if (startDate == null && endDate == null) {
			qb.append("and false ");
			qbAmountSum.append("and false ");
			qbTimePremSum.append("and false ");
		}
		if (codeValue != null) {
			qb.append("and z.CodeValue = ? ", codeValue);
			qbAmountSum.append("and z.CodeValue = ? ", codeValue);
			qbTimePremSum.append("and z.CodeValue = ? ", codeValue);
		}
		if (!StringUtil.isEmpty(channelStr)) {
			channelStr = QueryUtil.getChannelInfo(channelStr, "");
			qb.append("and channelsn in (" + channelStr + ") ");
			qbAmountSum.append("and channelsn in (" + channelStr + ") ");
			qbTimePremSum.append("and channelsn in (" + channelStr + ") ");
		} else {
			qb.append("and false ");
			qbAmountSum.append("and false ");
			qbTimePremSum.append("and false ");
		}

		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);

		String amountSumStr = qbAmountSum.executeString();
		String timePremSumStr = qbTimePremSum.executeString();
		String claimStatistics = null;
		double amountSum = 0d;
		double timePremSum = 0d;
		if (!StringUtil.isEmpty(amountSumStr)) {
			amountSum = Double.parseDouble(amountSumStr);
		}
		if (!StringUtil.isEmpty(timePremSumStr)) {
			timePremSum = Double.parseDouble(timePremSumStr);
		}

		if (timePremSum == 0d) {
			claimStatistics = "没有数据,无法统计";
		} else {
			NumberFormat format = NumberFormat.getPercentInstance();
			format.setMaximumFractionDigits(2);
			claimStatistics = format.format(amountSum / timePremSum);
		}
		dt.insertColumn("fee", timePremSum);
		dt.insertColumn("claimStatistics", claimStatistics);

		dga.setTotal(qb);
		dga.bindData(dt);

	}

	public void del() {

		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);

		Transaction trans = new Transaction();
		claimstatisticsdetailsSchema schema = new claimstatisticsdetailsSchema();
		claimstatisticsdetailsSet set = schema.query(new QueryBuilder(" where id in (" + sqlIds + ") "));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else {
			Response.setLogInfo(0, "删除失败");
		}
	}

}
