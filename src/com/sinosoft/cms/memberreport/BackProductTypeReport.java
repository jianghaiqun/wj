package com.sinosoft.cms.memberreport;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.infoseeker.QueryUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BackProductTypeReport extends Page implements FieldNameMatchable{

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
		return params;
	}

	/**
	 * dg1DataBind:回购查询. <br/>ni 
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		 // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_old(dga);
        	return;
        }
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String channelsn = dga.getParam("contant");
		String parameter = "";
		if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
		
        String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		String sql = "SELECT c.channelname, a.channelsn,p.ProductGenera,p.ProductType,COUNT(DISTINCT a.ordersn) AS ordercount, "
				+ " FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount,COUNT(DISTINCT a.sdorid) AS policycount "
				+ " FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID"
				+ " LEFT JOIN channelinfo c ON a.channelsn = c.channelcode,dataprecipitationorder_1 b "
				+ " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
				+ " AND a.memberCreateDate <= a.orderCreateDate_30	AND a.memberid = b.memberid	AND b.inDealFlag = '" + uuid +"'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
				+ " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) "
				+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' "
				+ parameter
				+ "GROUP BY a.channelsn,p.ProductType ORDER BY a.channelsn";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        
		dga.bindData(dt);
	}
	
	public static void dg1DataBind_old(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		String channelsn = dga.getParam("contant");
		String parameter = "";

		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
		}

		// 时间段内 第一次购买的时间
		String mindateSql = "";
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' AND o.memberid = a.memberid";
		}

		//回购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT (SELECT channelname FROM channelinfo WHERE channelcode = a.channelsn) channelname, a.channelsn, p.ProductGenera, p.ProductType, COUNT(DISTINCT a.ordersn) AS ordercount, ");
		sql.append(" FORMAT(IFNULL(SUM(sdor.timePrem), '0'), 2) AS totalAmount, COUNT(DISTINCT sdor.id) AS policycount ");
		sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE a.ordersn = i.ordersn and a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
		sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
		sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
		sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
		sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(parameter);

		sql.append(" GROUP BY a.channelsn,p.ProductType");
		sql.append(" ORDER BY a.channelsn");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		//回购率计算
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("channelsn", params.getString("channelsn"));
		params.put("productType", params.getString("productType"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		params.put("contant", params.getString("contant"));
		return params;
	}

	public static void dg1DataBind_Odialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Odialog_old(dga);
        	return;
        }
		String productType = dga.getParams().getString("productType");
		String channelsn = dga.getParams().getString("channelsn");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		String sql = "SELECT a.ordersn, a.applicantName, a.startdate,	a.receiveDate,p.productname,a.planname, COUNT(a.sdorId) as insuredNum, ROUND(SUM(a.timePrem), 2) as premSum,"
				+ " ROUND(SUM(a.payPrice), 2) as payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID, "
				+ " a.orderStatusName, a.couponsn, a.activitySn, ROUND(SUM(a.couponValue), 2) as couponAmount, ROUND(SUM(a.integralValue), 2) as integralSum, ROUND(SUM(a.activityValue), 2) as activitySum, "
				+ " c.channelname AS orderchannel,(SELECT channelname	FROM channelinfo c WHERE c.channelcode = a.fromWap) AS memberchannel, p.ProductGenera "
				+ " FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID LEFT JOIN channelinfo c ON a.channelSn = c.ChannelCode,dataprecipitationorder_1 b "
				+ " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
				+ " AND a.memberCreateDate <= a.orderCreateDate_30	AND a.memberid = b.memberid	AND b.inDealFlag = '" + uuid +"'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
				+ " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) "
				+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ";
				
		if (StringUtil.isNotEmpty(channelsn)) {
			sql += " AND a.channelsn = '" + channelsn + "'";
		} else {
			sql += " AND (a.channelsn = '' OR a.channelsn IS NULL)";
		}
		if (StringUtil.isNotEmpty(productType)) {
			sql += " AND p.producttype = '" + productType + "'";
		} else {
			sql += " AND (p.producttype IS NULL OR p.producttype = '')";
		}
		sql += " GROUP BY a.ordersn ORDER BY a.receiveDate DESC";
		QueryBuilder qb = new QueryBuilder(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		dga.bindData(dt);
	}

	public static void dg1DataBind_Odialog_old(DataGridAction dga) {

		String productType = dga.getParams().getString("productType");
		String csn = dga.getParams().getString("channelsn");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";

		/*String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
		}*/

		// 时间段内 第一次购买的时间
		String mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' AND o.memberid = a.memberid";

		//回购数据
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn, appnt.applicantName, i.startdate, a.modifyDate, i.productname, i.planname, ");
		sql.append("(SELECT COUNT(r.id) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1') insuredNum, ");
		sql.append("(SELECT ROUND(SUM(r.timePrem), 2) FROM sdinformationrisktype r WHERE a.ordersn = r.orderSn AND r.appStatus <= '1') premSum, ");
		sql.append("a.payPrice, CONCAT(IFNULL(m.email,''),'/',IFNULL(m.mobileno,'')) AS MID, h.CodeName, a.couponsn, ");
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
		sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
		sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
		sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
		sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		//sql.append(parameter);
		if (StringUtil.isNotEmpty(csn)) {
			sql.append(" AND a.channelsn = '" + csn + "'");
		} else {
			sql.append(" AND (a.channelsn = '' OR a.channelsn IS NULL)");
		}
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		} else {
			sql.append(" AND (p.producttype IS NULL OR p.producttype = '')");
		}
		sql.append(" GROUP BY a.ordersn ORDER BY a.modifyDate DESC");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		dga.bindData(dt);
	}
	
	public static void dg1DataBind_Rdialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Rdialog_old(dga);
        	return;
        }
		String channelsn = dga.getParams().getString("channelsn");
		String productType = dga.getParams().getString("productType");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");

		String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
			
		String sql = "SELECT a.ordersn,a.recognizeeSn,a.productname,a.policyNo,a.payPrice,a.orderStatusName,a.applicantName,a.recognizeeName,"
				+ " a.recognizeeIdentityId,a.startDate,a.endDate,CONCAT(IFNULL(a.membermail, ''),'/',IFNULL(a.membermobile, '')) AS MID "
				+ " FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID,dataprecipitationorder_1 b "
				+ " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
				+ " AND a.memberCreateDate <= a.orderCreateDate_30	AND a.memberid = b.memberid	AND b.inDealFlag = '" + uuid +"'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
				+ " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) "
				+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ";
				
		if (StringUtil.isNotEmpty(channelsn)) {
			sql += " AND a.channelsn = '" + channelsn + "'";
		} else {
			sql += " AND (a.channelsn = '' OR a.channelsn IS NULL)";
		}
		if (StringUtil.isNotEmpty(productType)) {
			sql += " AND p.producttype = '" + productType + "'";
		} else {
			sql += " AND (p.producttype IS NULL OR p.producttype = '')";
		}
		sql += " GROUP BY a.sdorid ORDER BY a.receiveDate desc ";
		QueryBuilder qb = new QueryBuilder(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		dga.bindData(dt);
	}

	public static void dg1DataBind_Rdialog_old(DataGridAction dga) {

		String csn = dga.getParams().getString("channelsn");
		String productType = dga.getParams().getString("productType");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
		/*String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
		}*/

		// 时间段内 第一次购买的时间
		String mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' AND o.memberid = a.memberid";

		//回购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.ordersn, insured.recognizeeSn, i.productname, sdor.policyNo, sdor.payPrice, h.codename, ");
		sql.append(" appnt.applicantName, insured.recognizeeName, insured.recognizeeIdentityId, sdor.svaliDate, sdor.evaliDate, ");
		sql.append(" CONCAT(IFNULL(m.email,''),'/',IFNULL(m.mobileno,'')) AS MID ");
		sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor LEFT JOIN sdinformationinsured insured ON insured.recognizeeSn = sdor.recognizeeSn, ");
		sql.append(" member m, sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID,sdinformationappnt appnt, zdcode h, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
		sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
		sql.append(" AND i.informationSn = appnt.informationSn AND i.orderSn = a.orderSn AND h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus ");
		sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
		sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
		sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
		sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		//sql.append(parameter);
		if (StringUtil.isNotEmpty(csn)) {
			sql.append(" AND a.channelsn = '" + csn + "'");
		} else {
			sql.append(" AND (a.channelsn = '' OR a.channelsn IS NULL)");
		}
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		} else {
			sql.append(" AND (p.producttype IS NULL OR p.producttype = '')");
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
		return matcher.setStartDatetime("startDate").setEndDatetime("endDate").setChannelSn("channelsn").setChannelSns("contant");
	}
    
    /**
     * dealTempData:插入临时数据. <br/>
     *
     * @author wwy
     * @param startDate
     * @param endDate
     * @param uuid
     * @param parameter
     */
    private static void dealTempData(String startDate, String endDate, String uuid){
    	 // 插入临时表
		String tempSql = "INSERT INTO dataprecipitationorder_1 (id,memberId,minOrderCreateDate,minOrderCreateDate_30,inDealFlag) "
				+ " SELECT REPLACE (UUID(), '-', ''), a.memberId,	DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'), "
				+ " DATE_ADD(DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'),INTERVAL - 30 DAY),	'" + uuid + "'"
				+ " FROM dataprecipitationorder a WHERE	a.memberid IS NOT NULL AND a.memberid != ''	AND a.receiveDate2 >= '" + startDate + "'"
				+ " AND a.receiveDate2 <= '" + endDate + "'" + "GROUP BY a.memberId";
		QueryBuilder tempQb = new QueryBuilder(tempSql);
		tempQb.executeNoQuery();
    }
}