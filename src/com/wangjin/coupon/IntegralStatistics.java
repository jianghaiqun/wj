/**
 * 
 */
package com.wangjin.coupon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @author wangcaiyun
 * 
 */
public class IntegralStatistics extends Page {

	/**
	 * 积分月度汇总查询
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate") + " 00:00:00";
		String endDate = dga.getParam("endDate") + " 23:59:59";
		DataTable dt1 = new QueryBuilder(
				"select '' as month, '' as sendNum, '' as sendNumMoney, '' as freezeNum, '' as freezeNumMoney, '' as usedPoint, '' as useableNum, '' as useableNumMoney from dual where 1=2")
				.executeDataTable();

		// 取得两日期间隔的月份
		List<String> months = DateUtil.getMonth(startDate, endDate);

		// 取得已发未冻结积分的数量及额度
		// String sql =
		// "select round(SUM(Integral),0) as sendNum,date_format(createdate,'%Y-%m') as month from sdintcalendar where status in ('0','2') and source != '2' and source != '3' and createdate >= ? and createdate <= ? GROUP by month order by month asc";
		// 购买送
		String sql1 = "select round(SUM(a.point),0), date_format(b.receiveDate,'%Y-%m') as month from sdinformation a, tradeinformation b where a.pointStatus = ? and a.orderSn = b.ordId and b.receiveDate >= ? and b.receiveDate <= ? and a.point is not null and a.point != '' GROUP by month order by month asc";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add("2");
		qb.add(startDate);
		qb.add(endDate);
		DataTable dt = qb.executeDataTable();
		Map<String, String> sendNumInfo = new HashMap<String, String>();
		Map<String, String> sendNumMoneyInfo = new HashMap<String, String>();
		String unit = Config.getValue("PointScalerUnit");
		
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				sendNumInfo.put(dt.getString(i, 1),
						String.valueOf(dt.getInt(i, 0)));
				// // 2014年7月以前200积分抵1元 2014年7月及以后100积分抵1元
				// if ("2014-07".compareTo(dt.getString(i, 1)) > 0) {
				// unit = "200";
				// } else {
				// unit = "100";
				// }
				sendNumMoneyInfo.put(
						dt.getString(i, 1),
						new BigDecimal(dt.getString(i, 0)).divide(
								new BigDecimal(unit), 2,
								BigDecimal.ROUND_HALF_UP).toString());
			}
		}
		// 除去购买送其他来源获得的积分（注册送、评论送等）
		String sql = "select round(SUM(Integral),0) as sendNum,date_format(createdate,'%Y-%m') as month from sdintcalendar where source not in ('0', '2', '3') and createdate >= ? and createdate <= ? GROUP by month order by month asc";
		dt = new QueryBuilder(sql, dga.getParam("startDate"), dga.getParam("endDate")).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				// // 2014年7月以前200积分抵1元 2014年7月及以后100积分抵1元
				// if ("2014-07".compareTo(dt.getString(i, 1)) > 0) {
				// unit = "200";
				// } else {
				// unit = "100";
				// }
				if (sendNumInfo.containsKey(dt.getString(i, 1))) {
					sendNumInfo.put(
							dt.getString(i, 1),
							String.valueOf(Integer.valueOf(sendNumInfo.get(dt
									.getString(i, 1))) + dt.getInt(i, 0)));
					sendNumMoneyInfo.put(
							dt.getString(i, 1),
							new BigDecimal(sendNumInfo.get(dt.getString(i, 1)))
									.divide(new BigDecimal(unit), 2,
											BigDecimal.ROUND_HALF_UP)
									.toString());
				} else {
					sendNumInfo.put(dt.getString(i, 1), dt.getString(i, 0));
					sendNumMoneyInfo.put(
							dt.getString(i, 1),
							new BigDecimal(dt.getString(i, 0)).divide(
									new BigDecimal(unit), 2,
									BigDecimal.ROUND_HALF_UP).toString());
				}
			}
		}

		// 取得已发冻结积分的数量及额度
		qb = new QueryBuilder(sql1);
		qb.add("1");
		qb.add(startDate);
		qb.add(endDate);
		dt = qb.executeDataTable();
		Map<String, String> freezeNumInfo = new HashMap<String, String>();
		Map<String, String> freezeNumMoneyInfo = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				freezeNumInfo.put(dt.getString(i, 1), dt.getString(i, 0));
				// // 2014年7月以前200积分抵1元 2014年7月及以后100积分抵1元
				// if ("2014-07".compareTo(dt.getString(i, 1)) > 0) {
				// unit = "200";
				// } else {
				// unit = "100";
				// }
				freezeNumMoneyInfo.put(
						dt.getString(i, 1),
						new BigDecimal(dt.getString(i, 0)).divide(
								new BigDecimal(unit), 2,
								BigDecimal.ROUND_HALF_UP).toString());
			}
		}

		// 取得已使用积分额度
		Map<String, String> usedPointInfo = new HashMap<String, String>();
		// 1、先取得积分兑换礼品所使用的积分 12000个积分兑换50元话费以及24000个积分兑换100元话费
		sql = "select round(SUM(prop2),2), date_format(createdate,'%Y-%m') as month from sdintcalendar where prop1='point' and source = '3' and createdate >= ? and createdate <= ? GROUP by month order by month asc";
		dt = new QueryBuilder(sql, dga.getParam("startDate"), dga.getParam("endDate")).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				usedPointInfo.put(dt.getString(i, 1), dt.getString(i, 0));
			}
		}
		// 2、再取得积分抵值所使用的积分额度
		sql = "select round(SUM(a.orderIntegral),2), date_format(b.receiveDate,'%Y-%m') as month from sdorders a, tradeinformation b where a.ordersn = b.ordID and b.receiveDate >= ? and b.receiveDate <= ? GROUP by month order by month asc";
		dt = new QueryBuilder(sql, startDate, endDate).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				// 若已存在使用积分额度 则相加后存入
				if (usedPointInfo.containsKey(dt.getString(i, 1))) {
					usedPointInfo.put(
							dt.getString(i, 1),
							new BigDecimal(
									usedPointInfo.get(dt.getString(i, 1)))
									.add(new BigDecimal(dt.getString(i, 0)))
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString());
				} else {
					usedPointInfo.put(dt.getString(i, 1), dt.getString(i, 0));
				}
			}
		}

		// 取得当前可用积分额度 不包括冻结积分
		sql = "select sum(currentValidatePoint) from member ";
		String useableNum = new QueryBuilder(sql).executeString();
		String useableMoney = new BigDecimal(useableNum).divide(
				new BigDecimal(unit), 2, BigDecimal.ROUND_HALF_UP).toString();

		// 拼装前台显示查询结果
		String month;
		int count = months.size();
		// 最多显示24个月的数据
		if (count > 24) {
			count = 24;
		}
		// 最多显示24个月的数据
		for (int i = 0; i < count; i++) {
			Object[] rowValue = new Object[8];
			month = months.get(i);
			// 统计月
			rowValue[0] = month;
			// 已发未冻结积分个数
			if (sendNumInfo.containsKey(month)) {
				rowValue[1] = sendNumInfo.get(month);
			} else {
				rowValue[1] = "0";
			}
			// 已发未冻结积分额度
			if (sendNumMoneyInfo.containsKey(month)) {
				rowValue[2] = sendNumMoneyInfo.get(month);
			} else {
				rowValue[2] = "0";
			}
			// 已发冻结积分个数
			if (freezeNumInfo.containsKey(month)) {
				rowValue[3] = freezeNumInfo.get(month);
			} else {
				rowValue[3] = "0";
			}
			// 已发冻结积分额度
			if (freezeNumMoneyInfo.containsKey(month)) {
				rowValue[4] = freezeNumMoneyInfo.get(month);
			} else {
				rowValue[4] = "0";
			}
			// 已使用积分额度
			if (usedPointInfo.containsKey(month)) {
				rowValue[5] = usedPointInfo.get(month);
			} else {
				rowValue[5] = "0";
			}
			// 可用积分个数
			rowValue[6] = useableNum;
			// 可用积分额度
			rowValue[7] = useableMoney;

			dt1.insertRow(rowValue);
		}

		dga.bindData(dt1);
	}

	public static Mapx initDialog(Mapx params) {
		params.put("source", HtmlUtil.codeToOptions("Source", true));
		return params;
	}

	/**
	 * 积分月度明细查询
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String month = $V("month");
		String source = $V("source");
		String unit = Config.getValue("PointScalerUnit");
		if (StringUtil.isEmpty(startDate)) {// 00:00:00
			startDate = month + "-01";
		}
		if (StringUtil.isEmpty(endDate)) {//" 23:59:59"
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDate = month + "-" + day;
		}

		StringBuffer sb = new StringBuffer();
		// 购买送积分
		sb.append("select t.receiveDate as tjTime, if(m.email is null or m.email = '',m.mobileNO, m.email) as mem, ");
		sb.append("a.ordersn as businessid, b.totalAmount, b.payPrice, '购买产品' as source, a.point, ");
		sb.append("round(a.point/"+unit+", 2) as money from sdinformation a, tradeinformation t, sdorders b ");
		sb.append("left join member m on b.memberId = m.id where a.ordersn = t.ordId ");
		sb.append("and a.ordersn = b.ordersn and t.receiveDate >= ? and t.receiveDate <= ? ");
		sb.append("and a.pointStatus in ('1', '2') and a.point is not null and a.point != '' ");
		// 积分抵值
		StringBuffer sb1 = new StringBuffer();
		sb1.append("select concat(a.createdate,' ',a.createtime) as tjTime, ");
		sb1.append("if(m.email is null or m.email = '',m.mobileNO, m.email) as mem, ");
		sb1.append("a.businessid, round(sum(b.totalAmount), 2) as totalAmount, ");
		sb1.append("round(sum(b.payPrice), 2) as payPrice, '积分抵值' as source, ");
		sb1.append("b.offsetPoint as point, round(sum(b.orderIntegral), 2) as money ");
		sb1.append("from sdorders b, sdintcalendar a left join member m ");
		sb1.append("on a.memberId = m.id where b.paySn = a.businessid and b.offsetPoint !=0 ");
		sb1.append("and a.createdate >= ? and a.createdate <= ? group by a.businessid ");

		// 其他来源
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select concat(a.createdate,' ',a.createtime) as tjTime, ");
		sb2.append("if(m.email is null or m.email = '',m.mobileNO, m.email) as mem, ");
		sb2.append("a.businessid, b.totalAmount, b.payPrice, z.CodeName as source, a.Integral as point, ");
		sb2.append("if(a.Source = '3', round(SUM(a.prop2),2), round(a.Integral/"+unit+", 2)) as money ");
		sb2.append("from sdintcalendar a left join member m on a.memberId = m.id ");
		sb2.append("left join sdorders b on b.orderSn = a.businessid ");
		sb2.append("left join zdcode z on z.CodeValue = a.Source and z.CodeType='Source' ");
		sb2.append("where a.Integral != 0 and a.createdate >= ? and a.createdate <= ? and a.Source not in ('0', '2') ");
		String union = "union ";
		String order = "order by source desc, tjTime asc ";
		// 查询所有
		if (StringUtil.isEmpty(source)) {
			String sql = sb.toString() + union + sb1.toString() + union
					+ sb2.toString() + order;
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(startDate + " 00:00:00");
			qb.add(endDate + " 23:59:59");
			qb.add(startDate);
			qb.add(endDate);
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);

		} else if ("0".equals(source)) {
			// 查询购买送积分明细
			String sql = sb.toString() + order;
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(startDate + " 00:00:00");
			qb.add(endDate + " 23:59:59");
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);

		} else if ("2".equals(source)) {
			// 查询积分抵值明细
			String sql = sb1.toString() + order;
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(startDate);
			qb.add(endDate);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		} else {
			// 查询其他来源明细
			String sql = sb2.toString() + "and a.source = ? " + order;
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(startDate);
			qb.add(endDate);
			qb.add(source);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dga.setTotal(qb);
			dga.bindData(dt);
		}
	}
}
