/**
 * 活动优惠汇总管理
 */
package com.wangjin.coupon;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @author wangcaiyun
 * 
 */
public class ActivityStatistics extends Page {

	/**
	 * 月度活动优惠汇总查询
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate") + " 00:00:00";
		String endDate = dga.getParam("endDate") + " 23:59:59";
		// 取得每月参加活动优惠额度
		StringBuffer sb = new StringBuffer();
		sb.append("select round(SUM(orderActivity),2) as sumActivity,date_format(b.receiveDate,'%Y-%m') as month ");
		sb.append("from sdorders a,tradeinformation b where b.ordID = a.ordersn and b.receiveDate >= ? and b.receiveDate <= ? ");
		sb.append("and a.orderActivity is not null and a.orderActivity != '' GROUP by month order by month asc");
		QueryBuilder qb = new QueryBuilder(sb.toString(), startDate, endDate);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 月度活动优惠明细查询
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String month = $V("month");

		if (StringUtil.isEmpty(startDate)) {
			startDate = month + "-01 00:00:00";
		}
		if (StringUtil.isEmpty(endDate)) {
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDate = month + "-" + day + " 23:59:59";
		}

		// 取得每月参加活动优惠额度
		StringBuffer sb = new StringBuffer();
		sb.append("select max(b.receiveDate) as BuyTime, a.paySn, round(sum(a.totalAmount), 2) as TotalAmount, ");
		sb.append("round(sum(a.payPrice), 2) as TradeAmount, round(sum(a.orderActivity), 2) as ActivitySumAmount ");
		sb.append("from sdorders a,tradeinformation b where b.ordID = a.ordersn and b.receiveDate >= ? and b.receiveDate <= ? ");
		sb.append("and a.orderActivity != 0 GROUP by a.paySn order by b.receiveDate asc");
		QueryBuilder qb = new QueryBuilder(sb.toString(), startDate, endDate);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

}
