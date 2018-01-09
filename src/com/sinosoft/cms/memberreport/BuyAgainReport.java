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
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyAgainReport extends Page implements FieldNameMatchable{

	/**
	 * 初始化复购查询页面.
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
	 * dg1DataBind:复购查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

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
		
		//复购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(DISTINCT a.memberid) AS membercount, COUNT(DISTINCT a.ordersn) AS ordercount, FORMAT(IFNULL(SUM(a.timePrem),'0'), 2) AS totalAmount, COUNT(DISTINCT a.sdorid) AS policycount, COUNT(DISTINCT a.fromWap) AS memchannel ");
		sql.append(" FROM dataprecipitationorder a, dataprecipitationorder_1 b ");
		sql.append(" WHERE a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		
		//订单数据
        StringBuffer sql1 = new StringBuffer();
        sql1.append(" SELECT COUNT(DISTINCT a.memberid) membercount, COUNT(DISTINCT a.orderSn) ordercount, COUNT(DISTINCT a.sdorid) AS policycount,  ");
        sql1.append(" FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount, COUNT(DISTINCT a.fromwap) AS memchannel  ");
        sql1.append(" FROM dataprecipitationorder a ");
        sql1.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
        sql1.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
        sql1.append(parameter);
        sql1.append(" AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' ");
        
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		QueryBuilder qb1 = new QueryBuilder(sql1.toString());
		DataTable dt1 = qb1.executeDataTable();
		String memberCount = computePercent(dt.getString(0, "membercount"),dt1.getString(0, "membercount"));
		String orderCount = computePercent(dt.getString(0, "ordercount"),dt1.getString(0, "ordercount"));
		String amountCount = computePercent(dt.getString(0, "totalAmount"),dt1.getString(0, "totalAmount"));
		String policyCount = computePercent(dt.getString(0, "policycount"),dt1.getString(0, "policycount"));
		String memChannel = computePercent(dt.getString(0, "memchannel"),dt1.getString(0, "memchannel"));
		
		DataTable dt2 = new DataTable();
		Object[] catalogRow = {"复购", dt.getString(0, "membercount"), dt.getString(0, "ordercount"),dt.getString(0, "totalAmount"), dt.getString(0, "policycount"), dt.getString(0, "memchannel"), "0"};
		Object[] catalogRow1 = {"总数", dt1.getString(0, "membercount"), dt1.getString(0, "ordercount"),dt1.getString(0, "totalAmount"), dt1.getString(0, "policycount"), dt1.getString(0, "memchannel"), "1"};
		Object[] catalogRow2 = {"复购率", memberCount, orderCount, amountCount, policyCount, memChannel, "2"};
		dt2.insertRow(catalogRow,0);
		dt2.insertRow(catalogRow1,1);
		dt2.insertRow(catalogRow2,2);

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
		//复购率计算
		dga.bindData(dt2);
	}

	/**
	 * initDialog:弹出窗口初始化. <br/>
	 *
	 * @author wwy
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Mdialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String channelsn = dga.getParams().getString("contant");
		String parameter = "";
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();
		
		//复购数据
		String sqlStr = "";
		if ("0".equals(type)) {

			// 插入临时表
			dealTempData(startDate, endDate, uuid);
			
			sqlStr = " SELECT w.*,IF(w.vipflag = 'Y','vip',w.grade) AS gradevip, "
					+ " DATEDIFF(z.mindate,(SELECT	DATE_FORMAT(MAX(b.createdate),'%Y-%m-%d') FROM sdorders b WHERE	b.createdate < z.mindate AND b.memberid = z.memberid)) AS maxdate, '' AS days "
					+ " FROM member w,(SELECT a.memberid, b.minOrderCreateDate AS mindate,b.minOrderCreateDate_30 AS mindate_30 FROM dataprecipitationorder a,dataprecipitationorder_1 b "
					+ " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
					+ " AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' "
					+ " AND EXISTS(SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) "
					+ " AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' "
					+ parameter
					+ " GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc";
		
		} else {
			sqlStr = " SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip, '' AS maxdate "
					+ " ,'' AS regdate,'' AS days "
					+ " FROM member w, (SELECT a.memberid "
					+ " FROM dataprecipitationorder a "
					+ " WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
					+ parameter
					+ " AND a.receiveDate <='" + endDate + " 23:59:59'  AND a.receiveDate >='" + startDate + "'"
					+ " GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc";
		}
		QueryBuilder qb = new QueryBuilder(sqlStr);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		if ("0".equals(type)) {
			for(int i=0;i<dt.getRowCount();i++){
				String date = dt.getString(i, "maxdate");
				if (StringUtil.isNotEmpty(date)) {
					dt.set(i, "days", date);
				} else{
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

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
		dga.bindData(dt);
	}

	/**
	 * dg1DataBind_Odialog:订单数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Odialog(DataGridAction dga) {

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
			dealTempData(startDate, endDate, uuid);
    	}
    	
    	sql = "SELECT a.ordersn,a.applicantName,a.startdate,a.receiveDate,p.productname,a.planname,COUNT(a.sdorId) AS insuredNum,"
    		+ " ROUND(SUM(a.timePrem), 2) AS premSum,ROUND(SUM(a.payPrice), 2) AS payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID, "
    		+ " a.orderStatusName,a.couponsn,a.activitySn,ROUND(SUM(a.couponValue), 2) AS couponAmount,ROUND(SUM(a.integralValue), 2) AS integralSum,ROUND(SUM(a.activityValue), 2) AS activitySum, "
    		+ " c.channelname AS orderchannel,d.channelname AS memberchannel,p.ProductGenera "
    		+ " FROM dataprecipitationorder a "
    		+ " LEFT JOIN sdproduct p ON a.productid = p.ProductID "
    		+ " LEFT JOIN channelinfo c ON a.channelSn = c.ChannelCode "
    		+ " LEFT JOIN channelinfo d ON a.fromWap = d.ChannelCode ";
    	if ("0".equals(type)) {
    		sql += ", dataprecipitationorder_1 b ";
    	}
    	sql	+= " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
    		+ " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ";
		if ("0".equals(type)) {
			sql += " AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' "
				+ " AND EXISTS(SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ";
		}
    	sql += " AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' "
    	    + parameter
    	    + "GROUP BY a.ordersn order by a.receiveDate desc";
    	
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
		dga.bindData(dt);
	}

	/**
	 * dg1DataBind_Rdialog:保单数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Rdialog(DataGridAction dga) {

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
			dealTempData(startDate, endDate, uuid);
		}
		
		sql += "SELECT a.ordersn,a.recognizeeSn,p.productname,a.policyNo,a.payPrice,a.orderstatusName,a.applicantName,a.recognizeeName, "
	            + " a.recognizeeIdentityId,	a.startDate, a.endDate,CONCAT(IFNULL(a.membermail, ''),'/',IFNULL(a.membermobile, '')) AS MID "
	            + " FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID ";
        if ("0".equals(type)) {
        	sql += ", dataprecipitationorder_1 b ";
    	}
    	sql	+= " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != ''"
        	+ " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ";
    	if ("0".equals(type)) {
    		sql += " AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' "
    			+ " AND EXISTS(SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ";
		}
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
	 * dg1DataBind_Sdialog:会员来源数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Sdialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		
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
			dealTempData(startDate, endDate, uuid);
    	}
    	
    	sql = "SELECT c.channelname,a.fromWap,count(DISTINCT a.memberid) AS memberNum "
    		+ " FROM dataprecipitationorder a LEFT JOIN channelinfo c ON a.fromWap = c.channelcode ";
    	if ("0".equals(type)) {
    		sql += " , dataprecipitationorder_1 b ";
    	}
    	sql += " WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' "
    		+ " AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) "
    		+ " AND a.fromWap IS NOT NULL AND a.fromWap != ''";
        if ("0".equals(type)) {
        	sql += " AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' "
        		+ " AND EXISTS(SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ";
		}
    	sql += " AND a.receiveDate2 <='" + endDate + "' AND a.receiveDate2 >='" + startDate + "' "
    	    + parameter
    	    + "GROUP BY a.fromWap ";
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
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
		if (y.compareTo(new BigDecimal("0.00")) == 0) {
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
    
    /**
     * dealTempData:插入临时数据. <br/>
     *
     * @author wwy
     * @param startDate
     * @param endDate
     * @param uuid
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

	@Override
	public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
		return matcher.setStartDatetime("startDate").setEndDatetime("endDate");
	}
 }