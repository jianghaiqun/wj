/**
 * 
 */
package com.wangjin.daren;

import java.math.BigDecimal;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @author wangcaiyun
 *
 */
public class SummaryStatistics extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		// 统计开始时间
		String statisticalTime = (String) dga.getParams().get("statisticalTime");
		// 统计结束时间
		String endStatisticalTime = (String) dga.getParams().get("endStatisticalTime");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT u.realName, sum(t.orderNum) orderNum, sum(t.sumPrem) sumPrem,");
		sb.append(" SUM((SELECT SUM(payPrice) FROM PaymemntDetailInfo where payTime=t.statisticalTime and createUser=t.createUser and isPay='Y')) as cost,");
		sb.append(" '' production, sum(t.flow) flow, sum(a.contactType) as authorNum,");
		sb.append(" sum(a.articleLink) as articleNum, '' conversionRate,");
		sb.append(" '' orderNu, '' prem, '' average, sum(t.convertNum) convertNum");
		sb.append(" FROM TravelNotesStatistics t left join (SELECT COUNT(DISTINCT articleLink) articleLink, COUNT(DISTINCT contactType) contactType,cooperationTime");
		sb.append(" ,createUser FROM AuthorDetailInfo GROUP BY createUser,cooperationTime) a ");
		sb.append(" on a.createUser=t.createUser AND a.cooperationTime=t.statisticalTime, zduser u ");
		sb.append(" where ((t.branchInnerCode like '"+User.getBranchInnerCode()+"%' and t.branchInnerCode != ?) or t.createUser = ? ) ");
		sb.append(" and u.username = t.createUser ");
		
		QueryBuilder qb = new QueryBuilder(sb.toString(), User.getBranchInnerCode(),User.getUserName());
		
		if (StringUtil.isNotEmpty(statisticalTime)) {
			qb.append(" and t.statisticalTime >= ?", statisticalTime);
		}
		if (StringUtil.isNotEmpty(endStatisticalTime)) {
			qb.append(" and t.statisticalTime <= ?", endStatisticalTime);
		}
		qb.append(" group by t.createUser ");
		dga.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			// 成本
			BigDecimal cost;
			// 保费
			BigDecimal sumPrem;
			// 订单量
			BigDecimal orderNum;
			// 文章量
			BigDecimal articleNum;
			// 流量
			BigDecimal flow;
			// 投产比
			BigDecimal production;
			// 转化率
			BigDecimal conversionRate;
			// 单文订单
			BigDecimal order;
			// 单文保费
			BigDecimal prem;
			// 单均
			BigDecimal average;
			
			// 成本总计
			BigDecimal costTotal = new BigDecimal(0);
			// 保费总计
			BigDecimal sumPremTotal = new BigDecimal(0);
			// 订单量总计
			BigDecimal orderNumTotal = new BigDecimal(0);
			// 文章量总计
			BigDecimal articleNumTotal = new BigDecimal(0);
			// 作者量总计
			BigDecimal authorNumTotal = new BigDecimal(0);
			// 流量总计
			BigDecimal flowTotal = new BigDecimal(0);
			// 辅助转化数总计
			BigDecimal convertNumTotal = new BigDecimal(0);
			// 投产比总计
			BigDecimal productionTotal = new BigDecimal(0);
			// 转化率总计
			BigDecimal conversionRateTotal = new BigDecimal(0);
			// 单文订单总计
			BigDecimal orderTotal = new BigDecimal(0);
			// 单文保费总计
			BigDecimal premTotal = new BigDecimal(0);
			// 单均总计
			BigDecimal averageTotal = new BigDecimal(0);
			
			BigDecimal zero = new BigDecimal(0);
			for (int i = 0; i < rowCount; i++) {
				sumPrem = new BigDecimal(dt.getString(i, "sumPrem"));
				sumPremTotal = sumPremTotal.add(sumPrem);
				if (StringUtil.isNotEmpty(dt.getString(i, "cost"))) {
					cost = new BigDecimal(dt.getString(i, "cost"));
				} else {
					cost = new BigDecimal(0);
					dt.set(i, "cost", "0");
				}
				costTotal = costTotal.add(cost);
				orderNum = new BigDecimal(dt.getString(i, "orderNum"));
				orderNumTotal = orderNumTotal.add(orderNum);
				if (StringUtil.isNotEmpty(dt.getString(i, "articleNum"))) {
					articleNum = new BigDecimal(dt.getString(i, "articleNum"));
				} else {
					articleNum = new BigDecimal(0);
					dt.set(i, "articleNum", "0");
				}
				articleNumTotal = articleNumTotal.add(articleNum);
				flow = new BigDecimal(dt.getString(i, "flow"));
				flowTotal = flowTotal.add(flow);
				if (StringUtil.isNotEmpty(dt.getString(i, "convertNum"))) { 
					convertNumTotal = convertNumTotal.add(new BigDecimal(dt.getString(i, "convertNum")));
				}
				
				if (StringUtil.isNotEmpty(dt.getString(i, "authorNum"))) {
					authorNumTotal = authorNumTotal.add(new BigDecimal(dt.getString(i, "authorNum")));
				} else {
					dt.set(i, "authorNum", "0");
				}
				// 计算投产比  投产比=保费/成本
				if (cost.compareTo(zero) == 0) {
					dt.set(i, "production", "0");
				} else {
					production = sumPrem.divide(cost, 2, BigDecimal.ROUND_HALF_UP);
					dt.set(i, "production", production.toString());
				}
				
				// 计算转化率  转化率=订单量/流量
				if (flow.compareTo(zero) == 0) {
					dt.set(i, "conversionRate", "0");
				} else {
					conversionRate = orderNum.multiply(new BigDecimal(100)).divide(flow, 2, BigDecimal.ROUND_HALF_UP);
					dt.set(i, "conversionRate", conversionRate.toString()+"%");
				}
				
				// 计算单文订单  单文订单=订单量/文章量
				if (articleNum.compareTo(zero) == 0) {
					dt.set(i, "orderNu", "0");
				} else {
					order = orderNum.divide(articleNum, 2, BigDecimal.ROUND_HALF_UP);
					dt.set(i, "orderNu", order.toString());
				}
				
				// 计算单文保费  单文保费=保费/文章量
				if (articleNum.compareTo(zero) == 0) {
					dt.set(i, "prem", "0");
				} else {
					prem = sumPrem.divide(articleNum, 2, BigDecimal.ROUND_HALF_UP);
					dt.set(i, "prem", prem.toString());
				}
				
				// 计算单均  单均=保费/订单量
				if (orderNum.compareTo(zero) == 0) {
					dt.set(i, "average", "0");
				} else {
					average = sumPrem.divide(orderNum, 2, BigDecimal.ROUND_HALF_UP);
					dt.set(i, "average", average.toString());
					averageTotal = averageTotal.add(average);
				}
			}
			
			if (costTotal.compareTo(zero) != 0) {
				productionTotal = sumPremTotal.divide(costTotal, 2, BigDecimal.ROUND_HALF_UP);
			}
			if (flowTotal.compareTo(zero) != 0) {
				conversionRateTotal = orderNumTotal.multiply(new BigDecimal(100)).divide(flowTotal, 2, BigDecimal.ROUND_HALF_UP);
			}
			if (articleNumTotal.compareTo(zero) != 0) {
				orderTotal = orderNumTotal.divide(articleNumTotal, 2, BigDecimal.ROUND_HALF_UP);
				premTotal = sumPremTotal.divide(articleNumTotal, 2, BigDecimal.ROUND_HALF_UP);
			}
			if (orderNumTotal.compareTo(zero) != 0) {
				averageTotal = sumPremTotal.divide(orderNumTotal, 2, BigDecimal.ROUND_HALF_UP);
			}
			
			Object[] rowValue = { "总计", orderNumTotal.toString(),
					sumPremTotal.toString(), costTotal.toString(),
					productionTotal.toString(), flowTotal.toString(),
					authorNumTotal.toString(), articleNumTotal.toString(),
					conversionRateTotal.toString()+"%", orderTotal.toString(),
					premTotal.toString(), averageTotal.toString(),
					convertNumTotal.toString() };

			dt.insertRow(rowValue);
			
		}
		dga.bindData(dt);
	}
}
