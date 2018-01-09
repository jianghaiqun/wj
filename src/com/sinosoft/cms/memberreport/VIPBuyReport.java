package com.sinosoft.cms.memberreport;

import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VIPBuyReport extends Page implements FieldNameMatchable{

	/**
	 * 初始化会员查询页面.
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
	 * dg1DataBind:vip消费查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.channelsn, p.productType, ci.channelname, p.ProductGenera, ");
		sql.append(" COUNT(DISTINCT a.memberid) membercount,COUNT(DISTINCT a.orderSn) ordercount, COUNT(DISTINCT sdor.id) AS policycount, FORMAT(IFNULL(SUM(sdor.timePrem),'0.00'),2) totalAmount ");
		sql.append(" FROM SDOrderItem f ,sdinformationrisktype sdor,sdinformation i left join sdproduct p on i.productid = p.productid,member m,sdorders a inner join tradeinformation tf on a.ordersn=tf.ordid  ");
		sql.append(" left join channelinfo ci on ci.channelcode = a.channelsn ");
		sql.append(" WHERE i.ordersn = a.ordersn and a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' AND a.memberid = m.id ");
		sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
		sql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" GROUP BY a.channelsn, p.productType ORDER BY a.channelsn DESC ");
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();

		//String vipallcount = new QueryBuilder(" select count(m.id) from member m where m.vipFlag = 'Y' ").executeString();
		//dt.insertColumn("vipallcount", vipallcount);

		String vipbuysql = "select COUNT(DISTINCT o.memberid) FROM sdorders o, tradeinformation tf, member m, VIPLog v "
				+ " WHERE o.ordersn = tf.ordid AND o.memberid = m.id AND m.id = v.memberid "
				+ " AND o.orderStatus IN ('7', '10', '12', '14') "
				+ " AND m.vipFlag = 'Y' AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate "
				+ " AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ";

		String vipbuycount = new QueryBuilder(vipbuysql).executeString();
		dt.insertColumn("vipbuycount", vipbuycount);
		
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("channelsn", params.getString("channelsn"));
		params.put("productType", params.getString("productType"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		return params;
	}

	public static void dg1DataBind_MHeaddialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		String vipbuysql = "";
		if (StringUtil.isNotEmpty(type) && "BUY".equals(type)) {
			vipbuysql = "select distinct m.*,IF(m.vipflag = 'Y', 'vip', m.grade) as gradevip FROM sdorders o, tradeinformation tf, member m "
					+ " WHERE o.ordersn = tf.ordid AND o.memberid = m.id AND o.memberid = m.id "
					+ " AND o.orderStatus IN ('7', '10', '12', '14') AND  m.vipFlag = 'Y' "
					+ " AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) "
					+ " AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ";
		} else {
			vipbuysql = "select m.*,IF(m.vipflag = 'Y', 'vip', m.grade) as gradevip from member m where m.vipFlag = 'Y'";
		}

		QueryBuilder qb = new QueryBuilder(vipbuysql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		dt.decodeColumn("registerType", HtmlUtil.codeToMapx("Member.registerType"));
		// 等级
		DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
		dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
		dga.bindData(dt);
	}
	
	public static void dg1DataBind_Mdialog(DataGridAction dga) {

		String channelsn = dga.getParams().getString("channelsn");
		String productType = dga.getParams().getString("productType");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";

		String vipbuysql = "select distinct m.*,IF(m.vipflag = 'Y', 'vip', m.grade) as gradevip "
				+ " FROM SDOrderItem f, sdorders o,sdinformation i left join sdproduct p on i.productid = p.productid, tradeinformation tf, member m "
				+ " WHERE f.ordersn = o.ordersn AND o.memberid IS NOT NULL AND (f.channelCode != '02' OR f.channelCode IS NULL) "
				+ " AND o.ordersn = tf.ordid AND o.memberid != '' AND o.memberid = m.id AND m.vipFlag = 'Y' AND i.ordersn = o.ordersn "
				+ " AND o.orderStatus IN ('7', '10', '12', '14') "
				+ " AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) "
				+ " AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ";

		if (StringUtil.isNotEmpty(productType)) {
			vipbuysql += " AND p.productType = '" + productType + "'";
		} else {
			vipbuysql += " AND p.productType IS NULL OR p.productType = '' ";
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			vipbuysql += " AND o.channelsn = '" + channelsn + "'";
		}
		QueryBuilder qb = new QueryBuilder(vipbuysql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		dt.decodeColumn("registerType", HtmlUtil.codeToMapx("Member.registerType"));
		// 等级
		DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
		dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
		dga.bindData(dt);
	}

	public static void dg1DataBind_Odialog(DataGridAction dga) {

		String productType = dga.getParams().getString("productType");
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
		sql.append(" sdinformationappnt appnt, zdcode h, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL ");
		sql.append(" AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id AND i.informationSn = appnt.informationSn ");
		sql.append(" AND i.orderSn = a.orderSn AND h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" AND m.vipflag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		} else {
			sql.append(" AND p.productType IS NULL OR p.productType = '' ");
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			sql.append(" AND a.channelsn = '" + channelsn + "'");
		}
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	public static void dg1DataBind_Rdialog(DataGridAction dga) {

		String channelsn = dga.getParams().getString("channelsn");
		String productType = dga.getParams().getString("productType");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.ordersn, insured.recognizeeSn, i.productname, sdor.policyNo, IF(LEFT(a.paysn, 2) = 'BG', '0', sdor.payPrice) as payPrice, h.codename, ");
		sql.append(" appnt.applicantName, insured.recognizeeName, insured.recognizeeIdentityId, sdor.svaliDate, sdor.evaliDate, ");
		sql.append(" CONCAT(IFNULL(m.email,''),'/',IFNULL(m.mobileno,'')) AS MID ");
		sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor LEFT JOIN sdinformationinsured insured ON insured.recognizeeSn = sdor.recognizeeSn, ");
		sql.append(" member m, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID,sdinformationappnt appnt, zdcode h, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
		sql.append(" AND i.informationSn = appnt.informationSn AND i.orderSn = a.orderSn AND h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus ");
		sql.append(" AND m.vipflag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		} else {
			sql.append(" AND p.productType IS NULL OR p.productType = '' ");
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			sql.append(" AND a.channelsn = '" + channelsn + "'");
		}
		sql.append(" GROUP BY sdor.id ");
		sql.append(" ORDER BY a.modifyDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	@Override
	public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
		return matcher.setStartDatetime("startDate").setEndDatetime("endDate");
	}
}