/**
 * 
 */
package com.wangjin.coupon;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcaiyun
 * 
 */
public class CouponStatistics extends Page {

	/**
	 * 优惠券月度汇总
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate") + " 00:00:00";
		String endDate = dga.getParam("endDate") + " 23:59:59";
		DataTable dt1 = new QueryBuilder(
				"select '' as month, '' as sumSend, '' as sumUsed, '' as overtime, '' as usable from dual")
				.executeDataTable();
		dt1.deleteRow(0);
		// 取得两日期间隔的月份
		List<String> months = DateUtil.getMonth(startDate, endDate);

		// 取得每月发放优惠券额度
		String sql = "select round(SUM(parValue),2) as sumSend,date_format(prop2,'%Y-%m') as month from couponinfo where prop2 >= ? and prop2 <= ? GROUP by month order by month asc";
		DataTable dt = new QueryBuilder(sql, startDate, endDate)
				.executeDataTable();
		Map<String, String> sendInfo = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				sendInfo.put(dt.getString(i, 1), dt.getString(i, 0));
			}
		}

		// 取得每月使用额度
		sql = "select round(SUM(b.orderCoupon),2) as sumUsed,date_format(a.payTime,'%Y-%m') as month from couponinfo a, sdorders b where a.status='1' and a.couponsn = b.couponsn and a.payTime >= ? and a.payTime <= ? GROUP by month order by month asc";
		dt = new QueryBuilder(sql, startDate, endDate).executeDataTable();
		Map<String, String> usedInfo = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				usedInfo.put(dt.getString(i, 1), dt.getString(i, 0));
			}
		}

		// 取得每月失效额度
		sql = "select round(SUM(parValue),2) as sumUsed,date_format(endTime,'%Y-%m') as month from couponinfo where status='5' and endTime >= ? and endTime <= ? and (prop2 is not null and prop2 != '') GROUP by month order by month asc";
		dt = new QueryBuilder(sql, startDate, endDate).executeDataTable();
		Map<String, String> overInfo = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				overInfo.put(dt.getString(i, 1), dt.getString(i, 0));
			}
		}

		// 拼装前台显示查询结果
		int count = months.size();
		String month;
		String endDay;
		// 最多显示24个月的数据
		if (count > 24) {
			count = 24;
		}
		for (int i = 0; i < count; i++) {
			Object[] rowValue = new Object[5];
			month = months.get(i);
			// 统计月
			rowValue[0] = month;
			// 发放优惠券额度
			if (sendInfo.containsKey(month)) {
				rowValue[1] = sendInfo.get(month);
			} else {
				rowValue[1] = "0";
			}
			// 使用额度
			if (usedInfo.containsKey(month)) {
				rowValue[2] = usedInfo.get(month);
			} else {
				rowValue[2] = "0";
			}
			// 失效额度
			if (overInfo.containsKey(month)) {
				rowValue[3] = overInfo.get(month);
			} else {
				rowValue[3] = "0";
			}

			/* 取得可用额度 */
			// 取得每月最后一天
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDay = month + "-" + day + " 23:59:59";
			// 最后一个月用画面上填的结束日期
			if (endDate.indexOf(month) >= 0) {
				endDay = endDate;
			}

			sql = "select round(SUM(parValue),2) as usable from couponinfo where prop2 is not null and prop2 != '' and prop2 <= ? and ((status = '1' and payTime > ?) or status = '2' or (status='5' and endTime > ?)) ";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(endDay);
			qb.add(endDay);
			qb.add(endDay);
			rowValue[4] = qb.executeString();
			dt1.insertRow(rowValue);
		}

		dga.bindData(dt1);
	}

	/**
	 * 月度优惠券优惠明细查询
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String month = $V("month");
		String status = $V("status");

		if (StringUtil.isEmpty(startDate)) {
			startDate = month + "-01 00:00:00";
		}
		if (StringUtil.isEmpty(endDate)) {
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDate = month + "-" + day + " 23:59:59";
		}

		// 取得每月参加活动优惠额度
		if (StringUtil.isEmpty(status)) {
			StringBuffer sb = new StringBuffer();
			sb.append("select a.payTime as tjTime, a.couponSn, a.createUser, a.realProvideUser, a.parValue,");
			sb.append("'已使用' as statusName, b.PaySn, round(sum(b.totalAmount), 2) as TotalAmount,");
			sb.append("round(sum(b.payPrice), 2) as TradeAmount, round(sum(b.orderCoupon), 2) as Sumcoupon ");
			sb.append("from couponinfo a, sdorders b where a.status = '1' and b.couponsn = a.couponsn ");
			sb.append("and a.payTime >= ? and a.payTime <= ? GROUP by a.couponsn,b.PaySn ");
			sb.append("union ");
			sb.append("select a.prop2 as tjTime, a.couponSn, a.createUser, a.realProvideUser,a.parValue,");
			sb.append("'已发放' as statusName, '' as PaySn, '' as TotalAmount, '' as TradeAmount, '' as Sumcoupon ");
			sb.append("from couponinfo a where a.prop2 >= ? and a.prop2 <= ? ");
			sb.append("union ");
			sb.append("select a.endTime as tjTime, a.couponSn, a.createUser, a.realProvideUser,a.parValue,");
			sb.append("'已过期' as statusName, '' as PaySn, '' as TotalAmount, '' as TradeAmount, '' as Sumcoupon ");
			sb.append("from couponinfo a where a.status='5' and a.endTime >= ? and a.endTime <= ? ");
			sb.append("and (prop2 is not null and prop2 != '') order by statusName asc,tjTime asc ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(startDate);
			qb.add(endDate);
			qb.add(startDate);
			qb.add(endDate);
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		} else if ("1".contains(status)) {
			StringBuffer sb = new StringBuffer();
			sb.append("select a.payTime as tjTime, a.couponSn, a.createUser, a.realProvideUser, a.parValue,");
			sb.append("'已使用' as statusName, b.PaySn, round(sum(b.totalAmount), 2) as TotalAmount,");
			sb.append("round(sum(b.payPrice), 2) as TradeAmount, round(sum(b.orderCoupon), 2) as Sumcoupon ");
			sb.append("from couponinfo a, sdorders b where a.status = '1' and b.couponsn = a.couponsn ");
			sb.append("and a.payTime >= ? and a.payTime <= ? GROUP by a.couponsn,b.PaySn order by tjTime asc ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		} else if ("2".contains(status)) {
			StringBuffer sb = new StringBuffer();
			sb.append("select a.prop2 as tjTime, a.couponSn, a.createUser, a.realProvideUser,a.parValue,");
			sb.append("'已发放' as statusName, '' as PaySn, '' as TotalAmount, '' as TradeAmount, '' as Sumcoupon ");
			sb.append("from couponinfo a where a.prop2 >= ? and a.prop2 <= ? order by tjTime asc ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		} else if ("5".contains(status)) {
			StringBuffer sb = new StringBuffer();
			sb.append("select a.endTime as tjTime, a.couponSn, a.createUser, a.realProvideUser,a.parValue,");
			sb.append("'已过期' as statusName, '' as PaySn, '' as TotalAmount, '' as TradeAmount, '' as Sumcoupon ");
			sb.append("from couponinfo a where a.status='5' and a.endTime >= ? and a.endTime <= ? ");
			sb.append("and (prop2 is not null and prop2 != '') order by tjTime asc ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		}
	}

	/**
	 * 支付有礼活动 优惠券使用统计
	 * @param dga
	 */
	public void dg3DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate") + " 00:00:00";
		String endDate = dga.getParam("endDate") + " 23:59:59";
		// 发放渠道
		String provideChannel = dga.getParam("provideChannel");
		// 使用渠道
		String usedChannel = dga.getParam("usedChannel");
		String provideChannelName = dga.getParam("provideChannelName");
		String usedChannelName = dga.getParam("usedChannelName");

		// 计算发放次数
		String sql = "SELECT COUNT(DISTINCT t1.couponsn) FROM couponinfo t1, ActivitySendCouponLog t2 " +
				"WHERE t1.status IN ('1','2') AND FIND_IN_SET(t1.couponsn, t2.couponsns) > 0 " +
				"AND t2.createDate >'" +startDate+"' and t2.createDate < '"+endDate+"' ";
		if (StringUtil.isNotEmpty(provideChannel)) {
			sql += " AND t2.channelsn = '"+provideChannel+"' ";
		}
		int provideCount = new QueryBuilder(sql).executeInt();
		// 计算使用次数
		sql = "SELECT COUNT(DISTINCT t1.couponsn) FROM couponinfo t1, ActivitySendCouponLog t2,sdorders t3 " +
				"WHERE t1.status = '1' AND FIND_IN_SET(t1.couponsn, t2.couponsns) > 0 " +
				"AND t2.createDate >'" +startDate+"' and t2.createDate < '"+endDate+"' and t1.ordersn = t3.paySn ";
		if (StringUtil.isNotEmpty(provideChannel)) {
			sql += " AND t2.channelsn = '"+provideChannel+"' ";
		}
		if (StringUtil.isNotEmpty(usedChannel)) {
			sql += " AND t3.channelsn = '"  + usedChannel + "' ";
		}
		int usedCount = new QueryBuilder(sql).executeInt();
		// 计算使用率
		String usedPercent = computePercent(String.valueOf(usedCount), String.valueOf(provideCount));

		DataTable dt = new DataTable();
		Object[] catalogRow = {provideChannelName, usedChannelName, String.valueOf(provideCount), String.valueOf(usedCount), usedPercent};
		dt.insertRow(catalogRow,0);

		dga.bindData(dt);
	}

	/**
	 * 计算a在b中所占百分比
	 * @param a
	 * @param b
	 * @return 百分比的值
	 */
	private static String computePercent(String a, String b) {
		BigDecimal y = new BigDecimal(a.replaceAll(",", ""));
		BigDecimal z = new BigDecimal(b.replaceAll(",", ""));
		if (StringUtil.isEmpty(a) || "0".equals(a) || y.compareTo(new BigDecimal("0.00")) == 0) {
			return "-";
		} else {
			// 接受百分比的值
			String baifenbi = "";
			BigDecimal baiy = y.multiply(new BigDecimal("1.00"));
			BigDecimal baiz = z.multiply(new BigDecimal("1.00"));
			BigDecimal fen = baiy.divide(baiz,5,BigDecimal.ROUND_HALF_UP);
			// 00.00% 百分比格式，后面不足2位的用0补齐
			DecimalFormat df1 = new DecimalFormat("0.00%");
			baifenbi = df1.format(fen);
			return baifenbi;
		}
	}
}
