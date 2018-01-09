/**
 * 
 */
package com.sinosoft.cms.dataservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;

/**
 * @author wangcaiyun
 *
 */
public class ChangeInfoInquity extends Page {
	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getPrevMonthDay(getFormat("yyyy-MM-dd", new Date())));
		params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));
		params.put("changeType", HtmlUtil.codeToOptions("PolicyChangeType", true));
		return params;
	}
	
	private static String getFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public void dg1Inquery(DataGridAction dga) {
		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String newPaySn = dga.getParams().getString("newPaySn");
		String oldPaySn = dga.getParams().getString("oldPaySn");
		String initPaySn = dga.getParams().getString("initPaySn");
		String newOrderSn = dga.getParams().getString("newOrderSn");
		String oldOrderSn = dga.getParams().getString("oldOrderSn");
		String initOrderSn = dga.getParams().getString("initOrderSn");
		String changeType = dga.getParams().getString("changeType");
		StringBuffer sb = new StringBuffer();
		sb.append("select createDate, newPaySn, newOrderSn, oldPaySn, oldOrderSn, ");
		sb.append("initPaySn, initOrderSn, beforeValue, afterValue, createUser, ");
		sb.append("CodeName as changeTypeName from PolicyChangeInfo, zdcode ");
		sb.append("where CodeType='PolicyChangeType' and CodeValue=changeType ");
		// 变更起始时间
		if (StringUtil.isNotEmpty(createDate)) {
			sb.append(" and createDate >='" + createDate + " 00:00:00'");
		}
		// 变更结束时间
		if (StringUtil.isNotEmpty(endCreateDate)) {
			sb.append(" and createDate <='" + endCreateDate + " 23:59:59'");
		}
		// 新交易流水号
		if (StringUtil.isNotEmpty(newPaySn)) {
			sb.append(" and newPaySn like '%" + newPaySn.trim() + "%'");
		}
		// 旧交易流水号
		if (StringUtil.isNotEmpty(oldPaySn)) {
			sb.append(" and oldPaySn like '%" + oldPaySn.trim() + "%'");
		}
		// 初始交易流水号
		if (StringUtil.isNotEmpty(initPaySn)) {
			sb.append(" and initPaySn like '%" + initPaySn.trim() + "%'");
		}
		// 新订单号
		if (StringUtil.isNotEmpty(newOrderSn)) {
			sb.append(" and newOrderSn like '%" + newOrderSn.trim() + "%'");
		}
		// 旧订单号
		if (StringUtil.isNotEmpty(oldOrderSn)) {
			sb.append(" and oldOrderSn like '%" + oldOrderSn.trim() + "%'");
		}
		// 初始订单号
		if (StringUtil.isNotEmpty(initOrderSn)) {
			sb.append(" and initOrderSn like '%" + initOrderSn.trim() + "%'");
		}
		// 变更类型
		if (StringUtil.isNotEmpty(changeType)) {
			sb.append(" and changeType = '" + changeType.trim() + "'");
		}
		sb.append(" order by createDate desc ");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());		
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}
