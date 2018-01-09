package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VIPTop20Report extends Page {

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
		return params;
	}
	
	/**
	 * dg1DataBind:vipTop20查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		String productType = dga.getParam("productType");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT i.productid, COUNT(DISTINCT a.memberid) membercount, COUNT(DISTINCT a.ordersn) ordersncount, COUNT(DISTINCT sdor.id) policycount, ");
		sql.append(" p.productname, FORMAT(IFNULL(SUM(sdor.timePrem), '0'), 2) AS totalAmount ");
		sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, sdinformation i left join sdproduct p on i.productid = p.productid, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE i.ordersn = a.ordersn and a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
		sql.append(" AND tf.receiveDate <='"+endDate+"' AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.productType = '" + productType + "'");
		}
		sql.append(" GROUP BY i.productid ORDER BY SUM(sdor.timePrem) DESC limit 20");
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("productType", params.getString("productType"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		params.put("contant", params.getString("contant"));
		params.put("productid", params.getString("productid"));
		return params;
	}

	public static void dg1DataBind_Mdialog(DataGridAction dga) {

		String productid = dga.getParams().getString("productid");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT m.*, IF(m.vipflag = 'Y', 'vip', m.grade) as gradevip ");
		sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, sdinformation i left join sdproduct p on i.productid = p.productid, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE i.ordersn = a.ordersn and a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
		sql.append(" AND tf.receiveDate <='"+endDate+"' AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND i.productid = '").append(productid).append("'");
		}
		sql.append(" GROUP BY m.id ORDER BY m.logindate desc");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		dt.decodeColumn("registerType", HtmlUtil.codeToMapx("Member.registerType"));
		// 是否启用
		DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?",
				"isAccountEnabled").executeDataTable();
		dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
		// 等级
		DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
		dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
		
		dga.bindData(dt);
	}

	public static void dg1DataBind_Odialog(DataGridAction dga) {

		String productid = dga.getParams().getString("productid");
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
		sql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND i.productid = '").append(productid).append("'");
		}
		sql.append(" GROUP BY a.ordersn ORDER BY a.modifyDate DESC");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	public static void dg1DataBind_Rdialog(DataGridAction dga) {

		String productid = dga.getParams().getString("productid");
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
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(" AND m.vipFlag = 'Y' AND EXISTS (SELECT 1 FROM VIPLog v WHERE m.id = v.memberid AND DATE_FORMAT(v.operaTime, '%Y-%m-%d %H:%i:%s') <= tf.receiveDate) ");
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND i.productid = '").append(productid).append("'");
		}
		sql.append(" GROUP BY sdor.id ");
		sql.append(" ORDER BY a.modifyDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}
 }