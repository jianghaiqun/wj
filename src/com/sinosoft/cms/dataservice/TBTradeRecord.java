package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;

public class TBTradeRecord extends Page {

	public static Mapx init(Mapx params) {
		params.put("tradeDateStart", PubFun.getPrevMonthDay(DateUtil.getCurrentDate()));
		params.put("tradeDateEnd", DateUtil.getCurrentDate());
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String appntName = dga.getParams().getString("appntName");
		String certificateId = dga.getParams().getString("certificateId");
		String tradeDateStart = dga.getParams().getString("tradeDateStart");
		String tradeDateEnd = dga.getParams().getString("tradeDateEnd");
		String tradeSumMin = dga.getParams().getString("tradeSumMin");
		String tradeSumMax = dga.getParams().getString("tradeSumMax");
		String useSumMin = dga.getParams().getString("useSumMin");
		String useSumMax = dga.getParams().getString("useSumMax");
		String effSumMin = dga.getParams().getString("effSumMin");
		String effSumMax = dga.getParams().getString("effSumMax");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id,appntName,certificateTypeName,certificateId,tradeDate,tradeSum,useSum,tradeSum - useSum AS effSum ");
		sb.append(" FROM SDTBTradeRecord a WHERE 1=1 ");
		if (StringUtil.isNotEmpty(appntName)) {
			sb.append(" AND a.appntName LIKE '%" + appntName.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(certificateId)) {
			sb.append(" AND a.certificateId LIKE '%" + certificateId.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(tradeDateStart)) {
			sb.append(" AND a.tradeDate >='" + tradeDateStart + "' ");
		}
		if (StringUtil.isNotEmpty(tradeDateEnd)) {
			sb.append(" AND a.tradeDate <='" + tradeDateEnd + "' ");
		}
		if (StringUtil.isNotEmpty(tradeSumMin)) {
			sb.append(" AND a.tradeSum >=" + tradeSumMin + " ");
		}
		if (StringUtil.isNotEmpty(tradeSumMax)) {
			sb.append(" AND a.tradeSum <=" + tradeSumMax + " ");
		}
		if (StringUtil.isNotEmpty(useSumMin)) {
			sb.append(" AND a.useSum >=" + useSumMin + " ");
		}
		if (StringUtil.isNotEmpty(useSumMax)) {
			sb.append(" AND a.useSum <=" + useSumMax + " ");
		}
		if (StringUtil.isNotEmpty(effSumMin)) {
			sb.append(" AND a.tradeSum - a.useSum >=" + effSumMin + " ");
		}
		if (StringUtil.isNotEmpty(effSumMax)) {
			sb.append(" AND a.tradeSum - a.useSum <=" + effSumMax + " ");
		}
		sb.append( "ORDER BY tradeDate DESC");
		
		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 修改天猫活动消费信息
	 */
	public void save() {
		String id = Request.getString("recordId");
		String tradeSum = Request.getString("tradeSum");
		String useSum = Request.getString("useSum");
		
		String update_sql = "UPDATE SDTBTradeRecord SET TradeSum=?,UseSum=? WHERE Id=?";
		QueryBuilder update_qb = new QueryBuilder(update_sql);
		update_qb.add(tradeSum);
		update_qb.add(useSum);
		update_qb.add(id);
		
		if (update_qb.executeNoQuery() > 0) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}
}
