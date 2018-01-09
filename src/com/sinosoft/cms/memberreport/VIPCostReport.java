package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VIPCostReport extends Page {

	/**
	 * 初始化查询页面.
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		params.put("startDate", fmt.format(date));
		params.put("endDate", fmt.format(date));

		// 险种
		QueryBuilder qb_riskCode = new QueryBuilder("SELECT codename,code FROM fdcode WHERE codetype='erisktype' ");
		params.put("erisktype", HtmlUtil.queryToOptions(qb_riskCode, true));
		
		String vipallcount = new QueryBuilder(" select count(m.id) from member m where m.vipFlag = 'Y' ").executeString();
		params.put("vipallcount", vipallcount);
		params.put("vipbuycount", "0");
		return params;
	}
	
	/**
	 * dg1DataBind:vip支出成本查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	@SuppressWarnings("rawtypes")
	public static void dg1DataBind(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		
		// FORMAT(IFNULL(SUM(risk.timePrem), '0.00'), 2) FORMAT(IFNULL(SUM(risk.payPrice), '0.00'), 2) 
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.channelsn, (SELECT channelname from channelinfo where channelcode = a.channelsn) as channelname, ");
		sql.append(" COUNT(a.ordersn) as ordersncount, '' AS premTotal, ");
		sql.append(" '' AS payTotal, ");
		sql.append(" SUM(IF(a.couponsn IS NOT NULL AND a.couponsn <> '', 1, 0)) AS couponcount, SUM(a.orderCoupon) AS orderCouponsum, ");
		sql.append(" SUM(IF(a.activitySn IS NOT NULL AND a.activitySn <> '', 1, 0)) AS activitycount, SUM(a.orderActivity) AS orderActivitysum, ");
		sql.append(" SUM(IF(a.orderIntegral IS NOT NULL AND a.orderIntegral <> '' AND a.orderIntegral <> '0', 1, 0)) AS orderIntegralcount, SUM(a.orderIntegral) AS orderIntegralsum, ");
		sql.append(" FORMAT(SUM(g.Integral), 0) AS Integralsum, ");
		sql.append(" FORMAT(IFNULL(SUM(g.Integral), '0'), 2)/10 AS IntegralTotal ");
		sql.append(" FROM SDOrderItem f, member m, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID, sdinformationappnt appnt, tradeinformation tf, sdorders a ");
		sql.append(" LEFT JOIN (SELECT Integral, Businessid FROM sdintcalendar WHERE source = '0' AND manner = '0' AND Prop3 = 'VIP' AND STATUS <> '2') g ON (g.Businessid = a.ordersn OR g.Businessid = a.paysn) ");
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE a.ordersn = tf.ordid AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' AND a.memberid = m.id ");
		whereSql.append("  AND i.informationSn = appnt.informationSn AND i.orderSn = a.orderSn AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
		whereSql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		whereSql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		whereSql.append(" GROUP BY a.channelsn ORDER BY a.channelsn DESC ");
		
		QueryBuilder qb = new QueryBuilder(sql.toString() + whereSql.toString());
		DataTable dt = qb.executeDataTable();
		
		StringBuffer sqlRisk = new StringBuffer();
		sqlRisk.append(" SELECT a.channelsn, FORMAT(IFNULL(SUM(risk.timePrem), '0.00'), 2) AS premTotal, FORMAT(IFNULL(SUM(risk.payPrice), '0.00'), 2) AS payTotal ");
		sqlRisk.append(" FROM SDOrderItem f, member m, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID, sdinformationappnt appnt, tradeinformation tf, sdorders a ");
		sqlRisk.append(" inner join sdinformationrisktype risk on risk.ordersn = a.ordersn and risk.appStatus <= '1' ");
		
		QueryBuilder qbRisk = new QueryBuilder(sqlRisk.toString() + whereSql.toString());
		DataTable dtRisk = qbRisk.executeDataTable();
		Map mapPrem = dtRisk.toMapx("channelsn", "premTotal");
		Map mapTotal = dtRisk.toMapx("channelsn", "payTotal");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isNotEmpty((String) mapPrem.get(dt.get(i, "channelsn")))) {
				dt.set(i, "premTotal", mapPrem.get(dt.get(i, "channelsn")));
			} else {
				dt.set(i, "premTotal", "0");
			}
			if (StringUtil.isNotEmpty((String) mapTotal.get(dt.get(i, "channelsn")))) {
				dt.set(i, "payTotal", mapTotal.get(dt.get(i, "channelsn")));
			} else {
				dt.set(i, "payTotal", "0");
			}
		}
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("channelsn", params.getString("channelsn"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		return params;
	}

	public static void dg1DataBind_Odialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		String channelsn = dga.getParams().getString("channelsn");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn, appnt.applicantName, i.startdate, a.modifyDate, i.productname, i.planname, ");
		sql.append("(SELECT COUNT(r.id) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1') insuredNum, ");
		sql.append("(SELECT ROUND(SUM(r.timePrem), 2) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1') premSum, ");
		sql.append("IF(LEFT(a.paysn, 2) = 'BG', '0', a.payPrice) as payPrice, CONCAT(IFNULL(m.email,''),'/',IFNULL(m.mobileno,'')) AS MID, h.CodeName, a.couponsn, ");
		sql.append("IFNULL((SELECT ROUND(SUM(r.couponValue), 2) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1'), '0.00') AS couponAmount, ");
		sql.append("IFNULL((SELECT ROUND(SUM(r.integralValue), 2) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1'), '0.00') AS integralSum, ");
		sql.append("IFNULL((SELECT ROUND(SUM(r.activityValue), 2) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1'), '0.00') AS activitySum, ");
		sql.append("a.orderCoupon, a.activitySn, a.orderActivity, a.offsetPoint, a.orderIntegral, ");
		sql.append("(SELECT channelname FROM channelinfo c WHERE c.channelcode = a.channelsn) AS orderchannel, ");
		sql.append("(SELECT channelname FROM channelinfo c WHERE c.channelcode = m.fromWap) AS memberchannel, ");
		sql.append(" p.ProductGenera ");
		sql.append(" FROM SDOrderItem f, member m, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID, ");
		sql.append(" sdinformationappnt appnt, zdcode h, sdorders a, tradeinformation tf ");
		sql.append(" WHERE a.ordersn = tf.ordid AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL ");
		sql.append(" AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id AND i.informationSn = appnt.informationSn ");
		sql.append(" AND i.orderSn = a.orderSn AND h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" AND m.vipflag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(type)) {
			if ("all".equals(type)) {
				
			} else if ("coupon".equals(type)) {
				sql.append(" AND a.couponsn IS NOT NULL AND a.couponsn <> '' ");
			} else if ("activity".equals(type)) {
				sql.append(" AND a.activitySn IS NOT NULL AND a.activitySn <> '' ");
			} else if ("Integral".equals(type)) {
				sql.append(" AND a.orderIntegral IS NOT NULL AND a.orderIntegral <> '' AND a.orderIntegral <> '0' ");
			} else {
				
			}
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			sql.append(" AND a.channelsn = '" + channelsn + "'");
		}
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}
 }