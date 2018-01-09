/**
 * 
 */
package com.wangjin.daren;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.MonthGoalsInfoSchema;

/**
 * @author wangcaiyun
 *
 */
public class MonthGoalsInput extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		params.put("month", HtmlUtil.mapxToOptions(getMonth(), true));
		params.put("year", HtmlUtil.mapxToOptions(getYear(), true));
		DataTable dt = new QueryBuilder("select userName, IFNULL(realName, userName) from zduser where ((branchInnerCode like '"+User.getBranchInnerCode()+"%' and branchInnerCode != ?) or userName = ? ) ", User.getBranchInnerCode(), User.getUserName()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Mapx<String, String> mapx = new Mapx<String, String>();
			String[] checkarray = null;
			int i = 0;
			int rowCount = dt.getRowCount();
			for (; i < rowCount; i++) {
				mapx.put(dt.getString(i, 0), dt.getString(i, 1));
			}
			params.put("salesName",HtmlUtil.mapxToCheckboxes("salesName",mapx,checkarray,checkarray));
		}
		params.put("monthValue", DateUtil.getCurrentDate("MM"));
		params.put("yearValue", DateUtil.getCurrentDate("yyyy"));
		return params;
	}
	
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("month", HtmlUtil.mapxToOptions(getMonth(), false));
		params.put("year", HtmlUtil.mapxToOptions(getYear(), false));
		String type = params.getString("type");
		String id = params.getString("ID");
		if ("Modify".equals(type) && StringUtil.isNotEmpty(id)) {
			MonthGoalsInfoSchema schema = new MonthGoalsInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.put("yearValue", schema.getmonth().split("-")[0]);
				params.put("monthValue", schema.getmonth().split("-")[1]);
				params.put("goalsPrem", schema.getgoalsPrem().toString());
			}
		}
		
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		// 年
		String year = dga.getParams().getString("year");
		// 月
		String month = dga.getParams().getString("month");
		// 业务员名
		String salesName = dga.getParams().getString("salesName");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id,month,goalsPrem,DATE_FORMAT(createDate,'%Y-%m-%d') as createDate,");
		sb.append(" (select IFNULL(realName, userName) from zduser where userName = createUser) as createUser,");
		sb.append(" DATE_FORMAT(modifyDate,'%Y-%m-%d') as modifyDate,IF(modifyUser is null, '',");
		sb.append(" (select IFNULL(realName, userName) from zduser where userName = MonthGoalsInfo.modifyUser)) as modifyUser");
		sb.append(" FROM MonthGoalsInfo where ((branchInnerCode like '"+User.getBranchInnerCode()+"%' and branchInnerCode != ?) or createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sb.toString(), User.getBranchInnerCode(),User.getUserName());
		if (StringUtil.isNotEmpty(year) && StringUtil.isNotEmpty(month)) {
			qb.append(" and month = ? ", (year+"-"+month));
		} else if (StringUtil.isNotEmpty(year)) {
			qb.append(" and month like '"+year+"%'");
		} else if (StringUtil.isNotEmpty(month)) {
			qb.append(" and month like '%-"+month+"'");
		}
		if (StringUtil.isNotEmpty(salesName)) {
			qb.append(" and createUser in ('"+salesName.replace(",", "','")+"') ");
		}
		qb.append(" order by month desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		// 年月
		String month = dga.getParams().getString("year")+"-"+dga.getParams().getString("month");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT (select IFNULL(realName, userName) from zduser where userName = m.createUser) as createUser, IFNULL( m.goalsPrem , 0) goalsPrem ,IFNULL( round(sum(t.sumPrem),2) , 0) completePrem, IFNULL( sum(t.orderNum) ,0) orderNum, ");
		sb.append(" '0' as completeRates, '0' as surplusPrem, '0' as surplusUnit, '0' as goalsUnit, '0' as surplusDay ");
		sb.append(" FROM MonthGoalsInfo m left join TravelNotesStatistics t on t.createUser=m.createUser and statisticalTime like '"+month+"%'");
		sb.append(" where ((m.branchInnerCode like '"+User.getBranchInnerCode()+"%' and m.branchInnerCode != ?) or m.createUser = ? ) ");
		sb.append(" and m.month = ? group by m.createUser order by completePrem desc ");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(User.getBranchInnerCode());
		qb.add(User.getUserName());
		qb.add(month);
		dga.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			BigDecimal goalsPremSum = new BigDecimal(0);
			BigDecimal completePremSum = new BigDecimal(0);
			BigDecimal orderNumSum = new BigDecimal(0);
			BigDecimal completeRatesSum = new BigDecimal(0);
			BigDecimal surplusPremSum = new BigDecimal(0);
			BigDecimal surplusUnitSum = new BigDecimal(0);
			BigDecimal goalsUnitSum = new BigDecimal(0);
			
			// 计划保费
			BigDecimal goalsPrem;
			// 已完成保费
			BigDecimal completePrem;
			// 订单量
			BigDecimal orderNum;
			// 完成比例
			BigDecimal completeRates;
			// 剩余保费
			BigDecimal surplusPrem;
			// 剩余日均
			BigDecimal surplusUnit;
			// 计划日均
			BigDecimal goalsUnit;
			BigDecimal zero = new BigDecimal(0);
			
			Date date = DateUtil.parse(month, "yyyy-MM");
			// 总天数
			int sumDay = DateUtil.getMaxDayOfMonth(date);
			// 剩余天数
			int surplusDay = 0;
			int compare = month.compareTo(DateUtil.getCurrentDate("yyyy-MM"));
			if (compare == 0) {
				surplusDay = sumDay-DateUtil.getDayOfMonth(new Date());
			} else if (compare > 0) {
				surplusDay = sumDay;
			}
			
			int rowCount = dt.getRowCount();
			int i = 0;
			for (; i < rowCount; i++) {
				goalsPrem = new BigDecimal(dt.getString(i, "goalsPrem"));
				completePrem = new BigDecimal(dt.getString(i, "completePrem"));
				orderNum = new BigDecimal(dt.getString(i, "orderNum"));
				// 完成比例 = 已完成保费*100/计划保费
				if (goalsPrem.compareTo(zero) == 0) {
					completeRates = new BigDecimal(0);
				} else {
					completeRates = completePrem.multiply(new BigDecimal(100)).divide(goalsPrem, 2, BigDecimal.ROUND_HALF_UP);
				}
				surplusPrem = goalsPrem.subtract(completePrem).setScale(2, BigDecimal.ROUND_HALF_UP);
				if (surplusDay == 0) {
					surplusUnit = surplusPrem;
				} else {
					surplusUnit = surplusPrem.divide(new BigDecimal(surplusDay), 2, BigDecimal.ROUND_HALF_UP);
				}
				goalsUnit = goalsPrem.divide(new BigDecimal(sumDay), 2, BigDecimal.ROUND_HALF_UP);
				dt.set(i, "completeRates", completeRates.toString()+"%");
				dt.set(i, "surplusPrem", surplusPrem.toString());
				dt.set(i, "surplusUnit", surplusUnit.toString());
				dt.set(i, "goalsUnit", goalsUnit.toString());
				dt.set(i, "surplusDay", String.valueOf(surplusDay));
				
				goalsPremSum = goalsPremSum.add(goalsPrem);
				completePremSum = completePremSum.add(completePrem);
				orderNumSum = orderNumSum.add(orderNum);
				completeRatesSum = completeRatesSum.add(completeRates);
				surplusPremSum = surplusPremSum.add(surplusPrem);
				surplusUnitSum = surplusUnitSum.add(surplusUnit);
				goalsUnitSum = goalsUnitSum.add(goalsUnit);
			}
			
			// 完成比例 = 已完成保费*100/计划保费
			if (goalsPremSum.compareTo(zero) == 0) {
				completeRatesSum = new BigDecimal(0);
			} else {
				completeRatesSum = completePremSum.multiply(new BigDecimal(100)).divide(goalsPremSum, 2, BigDecimal.ROUND_HALF_UP);
			}				
			Object[] rowValue = {
					"合计",
					goalsPremSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString(),
					completePremSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString(),
					orderNumSum.setScale(0, BigDecimal.ROUND_HALF_UP)
							.toString(),
					completeRatesSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString() + "%",
					surplusPremSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString(),
					surplusUnitSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString(),
					goalsUnitSum.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString(), String.valueOf(surplusDay) };

			dt.insertRow(rowValue);
		}
		dga.bindData(dt);
	}
	public void add() {
		String month = Request.getString("year")+"-"+Request.getString("month");
		
		// 校验该月计划是否已经录入
		int count = new QueryBuilder("select count(1) from MonthGoalsInfo where month = ? and createUser = ? ", month, User.getUserName()).executeInt();
		if (count > 0) {
			Response.setStatus(0);
			Response.setMessage("该月计划已录入，不能重复录入！");
			return;
		}
		MonthGoalsInfoSchema schema = new MonthGoalsInfoSchema();
		schema.setValue(Request);
		schema.setmonth(month);
		schema.setid(NoUtil.getMaxID("MonthGoalsInfoID")+"");
		schema.setbranchInnerCode(User.getBranchInnerCode());
		schema.setcreateDate(new Date());
		schema.setcreateUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(schema, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
	}
	
	public void edit() {
		String id = Request.getString("ID");
		if (StringUtil.isEmpty(id)) {
			Response.setStatus(0);
			Response.setMessage("数据ID不能为空！");
			return;
		}
		MonthGoalsInfoSchema schema = new MonthGoalsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			String month = Request.getString("year")+"-"+Request.getString("month");
			// 校验该月计划是否已经录入
			int count = new QueryBuilder("select count(1) from MonthGoalsInfo where month = ? and createUser = ?", month , schema.getcreateUser()).executeInt();
			if (count > 1) {
				Response.setStatus(0);
				Response.setMessage("该月计划已录入，不能重复！");
				return;
			}
			schema.setValue(Request);
			schema.setmonth(month);
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());
			Transaction trans = new Transaction();
			trans.add(schema, Transaction.UPDATE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败！");
			}
		}
	}
	
	public void delete() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("DELETE FROM MonthGoalsInfo WHERE id IN ('"+ids.replace(",", "','")+"')"));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	
	public static Map<String, String> getMonth() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("01", "01");
		map.put("02", "02");
		map.put("03", "03");
		map.put("04", "04");
		map.put("05", "05");
		map.put("06", "06");
		map.put("07", "07");
		map.put("08", "08");
		map.put("09", "09");
		map.put("10", "10");
		map.put("11", "11");
		map.put("12", "12");
		return map;
	}
	
	public static Map<String, String> getYear() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Date date = new Date();
		String prevYear = DateUtil.toString(DateUtil.addYear(date, -1), "yyyy");
		String nextYear = DateUtil.toString(DateUtil.addYear(date, 1), "yyyy");
		String currYear = DateUtil.toString(date, "yyyy");
		map.put(currYear, currYear);
		map.put(nextYear, nextYear);
		map.put(prevYear, prevYear);
		return map;
	}
	
}
