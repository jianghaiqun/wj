package com.sinosoft.cms.memberreport;

import cn.com.sinosoft.util.CommonUtil;
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
import com.wangjin.infoseeker.QueryUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyBackReport extends Page implements FieldNameMatchable {

    /**
     * 初始化回购查询页面.
     *
     * @param params
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Mapx init(Mapx params) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        params.put("startDate", fmt.format(date));
        params.put("endDate", fmt.format(date));
        return params;
    }

    /**
     * dg1DataBind:回购查询. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind(DataGridAction dga) {

        long start = System.currentTimeMillis();
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
		dealTempData(startDate, endDate, uuid, parameter);
		String sql = "SELECT COUNT(DISTINCT a.memberid) AS membercount, COUNT(DISTINCT a.ordersn) AS ordercount, FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount,"
				+ " COUNT(DISTINCT a.sdorId) AS policycount, COUNT(DISTINCT a.fromwap) AS memchannel "
				+ " FROM dataprecipitationorder a, dataprecipitationorder_1 b "
				+ " WHERE a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
				+ " AND a.memberCreateDate <= a.orderCreateDate_30	AND a.memberid = b.memberid	AND b.inDealFlag = '" + uuid +"'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
				+ " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) "
				+ " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) "
				+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' "
				+ parameter;
        /*String sql = "{call Report_buy_back('startDate', 'endDate', 'UUID', 'channelsn')}";
        sql = sql.replace("startDate", startDate);
        sql = sql.replace("endDate", endDate);
        sql = sql.replace("UUID", CommonUtil.getUUID());
        sql = sql.replace("channelsn", channelsn);*/
        QueryBuilder qb = new QueryBuilder(sql);
        DataTable dt = qb.executeDataTable();
        logger.info("回购查询{}", (start - System.currentTimeMillis()));
        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        
        //订单数据
        StringBuffer sql1 = new StringBuffer();
        sql1.append(" SELECT COUNT(DISTINCT a.memberid) membercount, COUNT(DISTINCT a.orderSn) ordercount, COUNT(DISTINCT a.sdorid) AS policycount,  ");
        sql1.append(" FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount, COUNT(DISTINCT a.fromwap) AS memchannel  ");
        sql1.append(" FROM dataprecipitationorder a ");
        sql1.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
        sql1.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
        sql1.append(parameter);
        sql1.append(" AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' ");
        QueryBuilder qb1 = new QueryBuilder(sql1.toString());
        DataTable dt1 = qb1.executeDataTable();
        String memberCount = computePercent(dt.getString(0, "membercount"), dt1.getString(0, "membercount"));
        String orderCount = computePercent(dt.getString(0, "ordercount"), dt1.getString(0, "ordercount"));
        String amountCount = computePercent(dt.getString(0, "totalAmount"), dt1.getString(0, "totalAmount"));
        String policyCount = computePercent(dt.getString(0, "policycount"), dt1.getString(0, "policycount"));
        String memChannel = computePercent(dt.getString(0, "memchannel"), dt1.getString(0, "memchannel"));

        DataTable dt2 = new DataTable();
        Object[] catalogRow = {"回购", dt.getString(0, "membercount"), dt.getString(0, "ordercount"), dt.getString(0, "totalAmount"), dt.getString(0, "policycount"), dt.getString(0, "memchannel"), "0"};
        Object[] catalogRow1 = {"总数", dt1.getString(0, "membercount"), dt1.getString(0, "ordercount"), dt1.getString(0, "totalAmount"), dt1.getString(0, "policycount"), dt1.getString(0, "memchannel"), "1"};
        Object[] catalogRow2 = {"回购率", memberCount, orderCount, amountCount, policyCount, memChannel, "2"};
        dt2.insertRow(catalogRow, 0);
        dt2.insertRow(catalogRow1, 1);
        dt2.insertRow(catalogRow2, 2);
        //回购率计算

        long end = System.currentTimeMillis();
        logger.info("总耗时{}", (start - end));
        dga.bindData(dt2);
    }
    
    /**
     * dg1DataBind_old:回购统计优化前逻辑. <br/>
     *
     * @author wwy
     * @param dga
     */
    private static void dg1DataBind_old(DataGridAction dga){
    	
    	String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate") + " 23:59:59";
        String channelsn = dga.getParam("contant");
        String parameter = "";

        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, "");
            parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))";
        }

        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' AND o.memberid = a.memberid";
        }

        //回购数据
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(DISTINCT a.memberid) AS membercount, COUNT(DISTINCT a.ordersn) AS ordercount, FORMAT(IFNULL(SUM(sdor.timePrem),'0'), 2) AS totalAmount, COUNT(DISTINCT sdor.id) AS policycount, COUNT(DISTINCT m.fromwap) AS memchannel ");
        sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
        sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
        sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
        sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
        sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
        sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        sql.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql.append(parameter);

        //订单数据
        StringBuffer sql1 = new StringBuffer();
        sql1.append(" SELECT COUNT(DISTINCT a.memberid) membercount,COUNT(DISTINCT a.orderSn) ordercount, COUNT(DISTINCT sdor.id) AS policycount, FORMAT(IFNULL(SUM(sdor.timePrem),'0.00'),2) totalAmount, COUNT(DISTINCT m.fromwap) AS memchannel ");
        sql1.append(" FROM SDOrderItem f ,sdinformationrisktype sdor,member m,sdorders a inner join tradeinformation tf on a.ordersn=tf.ordid  ");
        sql1.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' AND a.memberid = m.id ");
        sql1.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
        sql1.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql1.append(parameter);

        QueryBuilder qb = new QueryBuilder(sql.toString());
        DataTable dt = qb.executeDataTable();

        QueryBuilder qb1 = new QueryBuilder(sql1.toString());
        DataTable dt1 = qb1.executeDataTable();
        String memberCount = computePercent(dt.getString(0, "membercount"), dt1.getString(0, "membercount"));
        String orderCount = computePercent(dt.getString(0, "ordercount"), dt1.getString(0, "ordercount"));
        String amountCount = computePercent(dt.getString(0, "totalAmount"), dt1.getString(0, "totalAmount"));
        String policyCount = computePercent(dt.getString(0, "policycount"), dt1.getString(0, "policycount"));
        String memChannel = computePercent(dt.getString(0, "memchannel"), dt1.getString(0, "memchannel"));

        DataTable dt2 = new DataTable();
        Object[] catalogRow = {"回购", dt.getString(0, "membercount"), dt.getString(0, "ordercount"), dt.getString(0, "totalAmount"), dt.getString(0, "policycount"), dt.getString(0, "memchannel"), "0"};
        Object[] catalogRow1 = {"总数", dt1.getString(0, "membercount"), dt1.getString(0, "ordercount"), dt1.getString(0, "totalAmount"), dt1.getString(0, "policycount"), dt1.getString(0, "memchannel"), "1"};
        Object[] catalogRow2 = {"回购率", memberCount, orderCount, amountCount, policyCount, memChannel, "2"};
        dt2.insertRow(catalogRow, 0);
        dt2.insertRow(catalogRow1, 1);
        dt2.insertRow(catalogRow2, 2);
        //回购率计算
        dga.bindData(dt2);
    }

    /**
     * initDialog:弹出窗口初始化. <br/>
     *
     * @param params
     * @return
     * @author wwy
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Mapx initDialog(Mapx params) {
        params.put("type", params.getString("type"));
        params.put("startDate", params.getString("startDate"));
        params.put("endDate", params.getString("endDate"));
        params.put("contant", params.getString("contant"));
        return params;
    }

    /**
     * dg1DataBind_Mdialog:会员数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Mdialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Mdialog_old(dga);
        	return;
        }
        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        String sql = "";
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();
		if ("0".equals(type)) {
			// 插入临时表
			dealTempData(startDate, endDate, uuid, parameter);
			
			sql = " SELECT w.*,IF(w.vipflag = 'Y','vip',w.grade) AS gradevip, "
					+ " DATEDIFF(z.mindate,(SELECT	DATE_FORMAT(MAX(b.createdate),'%Y-%m-%d') FROM sdorders b WHERE	b.createdate <= z.mindate_30 AND b.memberid = z.memberid)) AS maxdate, "
					+ " DATEDIFF(z.mindate,(SELECT	DATE_FORMAT(b.createdate, '%Y-%m-%d') FROM member b WHERE	b.id = z.memberid AND b.createdate <= z.mindate_30)) AS regdate, '' AS days "
					+ " FROM member w,(SELECT a.memberid, b.minOrderCreateDate AS mindate,b.minOrderCreateDate_30 AS mindate_30 FROM dataprecipitationorder a,dataprecipitationorder_1 b "
					+ " WHERE a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
					+ " AND a.memberCreateDate <= a.orderCreateDate_30	AND a.memberid = b.memberid	AND b.inDealFlag = '" + uuid +"'"
					+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
					+ " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) "
					+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' "
					+ parameter
					+ " GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc";
		} else {
			sql = " SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip, '' AS maxdate "
					+ " ,'' AS regdate,'' AS days "
					+ " FROM member w, (SELECT a.memberid "
					+ " FROM dataprecipitationorder a "
					+ " WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
					+ parameter
					+ " AND a.receiveDate2 <='" + endDate + "' AND a.receiveDate2 >='" + startDate + "'"
					+ " GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc";
		}
        
        QueryBuilder qb = new QueryBuilder(sql);

        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        
        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        
        if ("0".equals(type)) {
            for (int i = 0; i < dt.getRowCount(); i++) {
                String maxdate = dt.getString(i, "maxdate");
                String regdate = dt.getString(i, "regdate");
                if (StringUtil.isNotEmpty(maxdate)) {
                    dt.set(i, "days", maxdate);
                } else if (StringUtil.isNotEmpty(regdate)) {
                    dt.set(i, "days", regdate);
                } else {
                    dt.set(i, "days", "1");
                }
            }
        }

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

    public static void dg1DataBind_Mdialog_old(DataGridAction dga) {

        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate") + " 23:59:59";

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, "");
            parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))";
        }

        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if ("0".equals(type) && StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' AND o.memberid = a.memberid";
        }

        //回购数据
        StringBuffer sql = new StringBuffer();
        if ("0".equals(type)) {
            sql.append(" SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip, DATEDIFF(z.mindate,(SELECT DATE_FORMAT(MAX(b.createdate),'%Y-%m-%d') FROM sdorders b WHERE b.createdate<=DATE_ADD(z.mindate, INTERVAL -30 DAY) AND b.memberid=z.memberid)) AS maxdate ");
            sql.append(" ,DATEDIFF(z.mindate,(SELECT DATE_FORMAT(b.createdate,'%Y-%m-%d') FROM member b WHERE b.id = z.memberid AND b.createdate<=DATE_ADD(z.mindate, INTERVAL -30 DAY))) AS regdate,'' AS days ");
            sql.append(" FROM member w, (SELECT a.memberid, (" + mindateSql + ") AS mindate ");
        } else {
            sql.append(" SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip, '' AS maxdate ");
            sql.append(" ,'' AS regdate,'' AS days ");
            sql.append(" FROM member w, (SELECT a.memberid ");
        }
        sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
        sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
        if ("0".equals(type)) {
            sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
            sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
            sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
            sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        }
        sql.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql.append(parameter);
        sql.append(" GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        if ("0".equals(type)) {
            for (int i = 0; i < dt.getRowCount(); i++) {
                String maxdate = dt.getString(i, "maxdate");
                String regdate = dt.getString(i, "regdate");
                if (StringUtil.isNotEmpty(maxdate)) {
                    dt.set(i, "days", maxdate);
                } else if (StringUtil.isNotEmpty(regdate)) {
                    dt.set(i, "days", regdate);
                } else {
                    dt.set(i, "days", "1");
                }
            }
        }

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
    
    /**
     * dg1DataBind_Odialog:订单数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Odialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Odialog_old(dga);
        	return;
        }
        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        
        String sql = "";
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();

    	if ("0".equals(type)) {
			// 插入临时表
			dealTempData(startDate, endDate, uuid, parameter);
    	}
    	
    	sql = "SELECT a.ordersn,a.applicantName,a.startdate,a.receiveDate,p.productname,a.planname,COUNT(a.sdorId) AS insuredNum,"
    		+ " ROUND(SUM(a.timePrem), 2) AS premSum,ROUND(SUM(a.payPrice), 2) AS payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID, "
    		+ " a.orderStatusName,a.couponsn,a.activitySn,ROUND(SUM(a.couponValue), 2) AS couponAmount,ROUND(SUM(a.integralValue), 2) AS integralSum,ROUND(SUM(a.activityValue), 2) AS activitySum, "
    		+ " c.channelname AS orderchannel,d.channelname AS memberchannel,p.ProductGenera "
    		+ " FROM dataprecipitationorder a "
    		+ " LEFT JOIN sdproduct p ON a.productid = p.ProductID "
    		+ " LEFT JOIN channelinfo c ON a.channelSn = c.ChannelCode "
    		+ " LEFT JOIN channelinfo d ON a.fromWap = d.ChannelCode";
    	if ("0".equals(type)) {
    		sql += ", dataprecipitationorder_1 b ";
    	}
    	sql	+= " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''";
		if ("0".equals(type)) {
			sql += "AND a.memberCreateDate <= a.orderCreateDate_30 AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid + "'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
	    	    + " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) ";
		}
    	sql += " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ";
    	sql += " AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' "
    	    + parameter
    	    + "GROUP BY a.ordersn order by a.receiveDate desc";
        
        QueryBuilder qb = new QueryBuilder(sql);
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        dga.bindData(dt);
    }
    
    /**
     * dg1DataBind_Odialog:订单数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Odialog_old(DataGridAction dga) {

        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate") + " 23:59:59";

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, "");
            parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))";
        }

        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if ("0".equals(type) && StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' AND o.memberid = a.memberid";
        }

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
        if ("0".equals(type)) {
            sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
            sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
            sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
            sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        }
        sql.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql.append(parameter);
        sql.append(" GROUP BY a.ordersn order by a.modifydate desc ");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        dga.bindData(dt);
    }

    /**
     * dg1DataBind_Rdialog:保单数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Rdialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Rdialog_old(dga);
        	return;
        }
        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        String sql = "";
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();
    	if ("0".equals(type)) {
			// 插入临时表
			dealTempData(startDate, endDate, uuid, parameter);
    	}
    	
        sql += "SELECT a.ordersn,a.recognizeeSn,p.productname,a.policyNo,a.payPrice,a.orderstatusName,a.applicantName,a.recognizeeName, "
            + " a.recognizeeIdentityId,	a.startDate, a.endDate,CONCAT(IFNULL(a.membermail, ''),'/',IFNULL(a.membermobile, '')) AS MID "
            + " FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID ";
        if ("0".equals(type)) {
        	sql += ", dataprecipitationorder_1 b ";
    	}
    	sql	+= " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''";
    	if ("0".equals(type)) {
			sql += "AND a.memberCreateDate <= a.orderCreateDate_30 AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid + "'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
	    	    + " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) ";
		}
    	sql += " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ";
    	sql += " AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' "
    	    + parameter
        	+ " GROUP BY a.sdorId order by a.receiveDate desc ";
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        dga.bindData(dt);
    }

    /**
     * dg1DataBind_Rdialog_old:保单数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Rdialog_old(DataGridAction dga) {

        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate") + " 23:59:59";

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, "");
            parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))";
        }

        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if ("0".equals(type) && StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' AND o.memberid = a.memberid";
        }

        //回购数据
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT o.ordersn, insured.recognizeeSn, i.productname, risk.policyNo, risk.payPrice, h.codename, ");
        sql.append("appnt.applicantName, insured.recognizeeName, insured.recognizeeIdentityId, risk.svaliDate, risk.evaliDate, ");
        sql.append("CONCAT(IFNULL(mem.email,''),'/',IFNULL(mem.mobileno,'')) AS MID ");
        sql.append("FROM sdorders o,sdinformation i,sdinformationappnt appnt, zdcode h ,member mem, sdinformationRiskType risk, sdinformationinsured insured, ");
        sql.append("(SELECT DISTINCT a.ordersn FROM SDOrderItem f, sdinformationrisktype sdor, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
        sql.append("WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
        if ("0".equals(type)) {
            sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
            sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
            sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
            sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        }
        sql.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql.append(parameter);
        // 按照登录时间逆序排序，讲最后登录的放到最上面
        sql.append(" ) z WHERE o.ordersn = z.ordersn AND i.ordersn = o.orderSn AND appnt.informationSn = i.informationSn AND h.CodeType = 'orderstatus' ");
        sql.append(" AND h.codevalue = o.orderstatus AND mem.id = o.memberid AND risk.ordersn = o.orderSn AND insured.informationSn = i.informationSn AND risk.appStatus <= '1' ");
        sql.append(" GROUP BY risk.id order by o.modifydate desc ");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        dga.bindData(dt);
    }
    
    /**
     * dg1DataBind_Sdialog:会员来源数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Sdialog(DataGridAction dga) {

        // 优化前
        String oldFlag = dga.getParam("oldFlag");
        
        if ("1".equals(oldFlag)) {
        	dg1DataBind_Sdialog_old(dga);
        	return;
        }
        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        String sql = "";
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();
    	if ("0".equals(type)) {
			// 插入临时表
			dealTempData(startDate, endDate, uuid, parameter);
    	}
    	
    	sql = "SELECT c.channelname,a.fromWap,count(DISTINCT a.memberid) AS memberNum "
    		+ " FROM dataprecipitationorder a LEFT JOIN channelinfo c ON a.fromWap = c.channelcode ";
    	if ("0".equals(type)) {
    		sql += " , dataprecipitationorder_1 b ";
    	}
    	sql += " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
    		+ " AND a.fromWap IS NOT NULL AND a.fromWap != ''";
        if ("0".equals(type)) {
			sql += "AND a.memberCreateDate <= a.orderCreateDate_30 AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid + "'"
				+ " AND NOT EXISTS (SELECT c.memberid FROM sdorders c WHERE c.memberid = a.memberid AND c.createdate >= a.orderCreateDate_30 AND c.createdate < b.minOrderCreateDate) "
	    	    + " AND NOT EXISTS (SELECT 1 FROM member b, dataprecipitationorder_1 c WHERE b.id = a.memberid AND b.id = c.memberid AND b.createdate >= c.minOrderCreateDate_30) ";
		}
    	sql += " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ";
    	sql += " AND a.receiveDate2 <='" + endDate + "' AND a.receiveDate2 >='" + startDate + "' "
    	    + parameter
    	    + "GROUP BY a.fromWap ";
    	
        QueryBuilder qb = new QueryBuilder(sql);
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        dga.bindData(dt);
    }
    
    /**
     * dg1DataBind_Sdialog:会员来源数据. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind_Sdialog_old(DataGridAction dga) {

        String type = dga.getParams().getString("type");
        // 统计时间
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate") + " 23:59:59";

        String channelsn = dga.getParams().getString("contant");
        String parameter = "";
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, "");
            parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))";
        }

        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if ("0".equals(type) && StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' AND o.memberid = a.memberid";
        }

        //回购数据
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT (SELECT channelname FROM channelinfo WHERE w.fromWap = channelcode) as source, w.fromWap, count(w.id) as memberNum ");
        sql.append(" FROM member w, (SELECT a.memberid ");
        sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
        sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
        if ("0".equals(type)) {
            sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
            sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
            sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
            sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        }
        sql.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
        sql.append(parameter);
        // 按照登录时间逆序排序，讲最后登录的放到最上面
        sql.append(" GROUP BY a.memberid) z WHERE w.id = z.memberid group by w.fromWap ORDER BY w.logindate desc");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        dga.bindData(dt);
    }
    
    /**
     * 计算a在b中所占百分比
     *
     * @param a
     * @param b
     * @return 百分比的值
     */
    private static String computePercent(String a, String b) {

        BigDecimal y = new BigDecimal(a.replaceAll(",", ""));
        BigDecimal z = new BigDecimal(b.replaceAll(",", ""));
        if (y.compareTo(new BigDecimal("0.00")) == 0) {
            return "-";
        } else {
            // 接受百分比的值
            String baifenbi = "";
            BigDecimal baiy = y.multiply(new BigDecimal("1.00"));
            BigDecimal baiz = z.multiply(new BigDecimal("1.00"));
            BigDecimal fen = baiy.divide(baiz, 5, BigDecimal.ROUND_HALF_UP);
            // 00.00% 百分比格式，后面不足2位的用0补齐
            DecimalFormat df1 = new DecimalFormat("0.00%");
            baifenbi = df1.format(fen);
            return baifenbi;
        }
    }

    @Override
    public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
        matcher.setStartDatetime("startDate").setEndDatetime("endDate").setChannelSns("contant").setType("type");
        return matcher;
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
    private static void dealTempData(String startDate, String endDate, String uuid, String parameter){
    	 // 插入临时表
		String tempSql = "INSERT INTO dataprecipitationorder_1 (id,memberId,minOrderCreateDate,minOrderCreateDate_30,inDealFlag) "
				+ " SELECT REPLACE (UUID(), '-', ''), a.memberId,	DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'), "
				+ " DATE_ADD(DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'),INTERVAL - 30 DAY),	'" + uuid + "'"
				+ " FROM dataprecipitationorder a WHERE	a.memberid IS NOT NULL AND a.memberid != ''	AND a.receiveDate2 >= '" + startDate + "'"
				+ " AND a.receiveDate2 <= '" + endDate + "'" + parameter + "GROUP BY a.memberId";
		QueryBuilder tempQb = new QueryBuilder(tempSql);
		tempQb.executeNoQuery();
    }
}