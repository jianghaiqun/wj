/**
 * 
 */
package com.wangjin.coupon;

import com.sinosoft.framework.Config;
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
public class IntegralRollBack extends Page {

	/**
	 * 查询月度积分回冲汇总
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String unit = Config.getValue("PointScalerUnit");
		
		// 取得升级后每月回冲积分额度
		StringBuffer sb = new StringBuffer();
		// 冻结积分回冲
		sb.append("select date_format(x.tjTime,'%Y-%m') as month, round(sum(x.Integral), 0) as freezeNum, ");
		sb.append("round(sum(x.freezeMoney), 2) as freezeMoney, round(sum(x.point), 2) as point, sum(offsetPoint) offsetPoint from ");
		sb.append("(select id, createdate as tjTime, Integral, round(Integral/"+unit+", 2) as freezeMoney, '0' as point, '0' offsetPoint ");
		sb.append("from sdintcalendar where source='0' and manner = '2' and Integral != 0 and createdate >= ? and createdate <= ? ");
		sb.append("union ");
		
		// 积分抵值回冲
		sb.append("select id, createdate tjTime , '0' as Integral, '0' as freezeMoney, round(Integral/"+unit+",2) point, ");
		sb.append("round(Integral,0) from sdintcalendar WHERE source='23' and manner = '0' and Integral != 0 and createdate >= ? and createdate <= ? ");
		sb.append(") x group by month order by month asc ");

		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(startDate);
		qb.add(endDate);
		qb.add(startDate);
		qb.add(endDate);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	/**
	 * 查询月度冻结积分回冲明细
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String month = $V("month");
		String unit = Config.getValue("PointScalerUnit");
		if (StringUtil.isEmpty(startDate)) {
			startDate = month + "-01";
		}
		if (StringUtil.isEmpty(endDate)) {
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDate = month + "-" + day;
		}

		// 取得每月回冲积分额度明细
		StringBuffer sb = new StringBuffer();
		// 冻结积分回冲
		sb.append("select max(concat(i.createdate,' ',i.createtime)) as tjTime, o.paySn, o.orderSn, ");
		sb.append("round(sum(i.Integral),0) freezeNum, round(sum(i.Integral)/"+unit+", 2) as freezeMoney ");
		sb.append("from sdintcalendar i, sdorders o where i.businessid = o.orderSn ");
		sb.append("and i.source='0' and i.manner = '2' and i.Integral != 0 and i.createdate >= ? and i.createdate <= ? ");
		sb.append("group by o.paySn, o.orderSn order by o.paySn, o.orderSn, i.createdate ");

		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(startDate);
		qb.add(endDate);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 查询月度积分抵值回冲明细
	 * 
	 * @param dga
	 */
	public void dg3DataBind(DataGridAction dga) {
		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String month = $V("month");
		String unit = Config.getValue("PointScalerUnit");
		if (StringUtil.isEmpty(startDate)) {
			startDate = month + "-01";
		}
		if (StringUtil.isEmpty(endDate)) {
			int day = DateUtil.getMaxDayOfMonth(DateUtil
					.parse(month, "yyyy-MM"));
			endDate = month + "-" + day;
		}
		
		// 取得每月回冲积分额度明细
		StringBuffer sb = new StringBuffer();
		sb.append("select createdate tjTime, Businessid paySn, round(Integral/"+unit+",2) orderIntegral, round(Integral,0) offsetPoint ");
		sb.append("from sdintcalendar WHERE source='23' and manner = '0' and Integral != 0 and createdate >= ? and createdate <= ? ");
		sb.append(" order by createdate ");

		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(startDate);
		qb.add(endDate);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}
