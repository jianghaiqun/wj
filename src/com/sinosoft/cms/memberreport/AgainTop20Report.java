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

import java.text.SimpleDateFormat;
import java.util.Date;

public class AgainTop20Report extends Page implements FieldNameMatchable{

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
	 * dg1DataBind:复购Top20查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String channelsn = dga.getParam("contant");
		String productType = dga.getParam("productType");
		String parameter = "";
		
		if (StringUtil.isEmpty(channelsn)) {
			return;
		} else {
			parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
		}

		String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		//复购Top20数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.productid, COUNT(DISTINCT a.memberid) membercount, COUNT(DISTINCT a.ordersn) ordersncount,COUNT(DISTINCT a.sdorid) policycount, ");
		sql.append(" a.productname,FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount ");
		sql.append(" FROM dataprecipitationorder_1 b,dataprecipitationorder a LEFT JOIN sdproduct p on p.productid = a.productId ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		sql.append(parameter);
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.productType = '" + productType + "'");
		}
		sql.append(" GROUP BY a.productid ORDER BY SUM(a.timePrem) DESC limit 20");
		
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

		String productType = dga.getParams().getString("productType");
		String productid = dga.getParams().getString("productid");
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
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT w.*,IF (w.vipflag = 'Y','vip',w.grade) AS gradevip, ");
		sql.append(" DATEDIFF(z.mindate,(SELECT o.createdate FROM sdorders o WHERE o.createdate < z.mindate AND o.memberid = z.memberid ORDER BY o.createdate DESC LIMIT 1)) AS date,'' AS days ");
		sql.append(" FROM member w, ");
		sql.append(" (SELECT a.memberid,b.minOrderCreateDate AS mindate,b.minOrderCreateDate_30 AS mindate_30 FROM dataprecipitationorder a, dataprecipitationorder_1 b ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId AND o.orderCreateDate > b.minOrderCreateDate_30 AND o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.productType = '" + productType + "'");
		}
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND a.productid = '" + productid + "'");
		}
		sql.append(" GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		for(int i=0;i<dt.getRowCount();i++){
			String date = dt.getString(i, "date");
			if (StringUtil.isNotEmpty(date)) {
				dt.set(i, "days", date);
			}  else{
				dt.set(i, "days", "1");
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

	public static void dg1DataBind_Odialog(DataGridAction dga) {

		String productType = dga.getParams().getString("productType");
		String productid = dga.getParams().getString("productid");
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
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn, a.applicantName,a.startdate,a.receiveDate,a.productname,a.planname,COUNT(a.sdorId) as insuredNum,ROUND(SUM(a.timePrem), 2) AS premSum, ");
		sql.append("ROUND(SUM(a.payPrice), 2) AS payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID,a.orderStatusName,a.couponsn,a.activitySn, ");
		sql.append("ROUND(SUM(a.couponValue), 2) AS couponAmount,ROUND(SUM(a.integralValue), 2) AS integralSum,ROUND(SUM(a.activityValue), 2) AS activitySum, ");
		sql.append("c.channelname AS orderchannel,d.channelname AS memberchannel,p.ProductGenera ");
		sql.append("FROM dataprecipitationorder a ");
		sql.append("LEFT JOIN sdproduct p ON a.productid = p.ProductID ");
		sql.append("LEFT JOIN channelinfo c ON a.channelSn = c.ChannelCode ");
		sql.append("LEFT JOIN channelinfo d ON a.fromWap = d.ChannelCode, dataprecipitationorder_1 b ");
		sql.append("WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append("AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId AND o.orderCreateDate > b.minOrderCreateDate_30 AND o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		}
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND a.productid = '" + productid + "'");
		}
		sql.append(" GROUP BY a.ordersn ORDER BY a.receiveDate DESC");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	public static void dg1DataBind_Rdialog(DataGridAction dga) {

		String productid = dga.getParams().getString("productid");
		String productType = dga.getParams().getString("productType");
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
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.ordersn,a.recognizeeSn,a.productname,a.policyNo,a.payPrice,a.orderStatusName,a.applicantName,a.recognizeeName,a.recognizeeIdentityId, ");
		sql.append(" a.startDate,a.endDate,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID ");
		sql.append(" FROM dataprecipitationorder a LEFT JOIN sdproduct p ON a.productid = p.ProductID, dataprecipitationorder_1 b ");
		sql.append("WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append("AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId AND o.orderCreateDate > b.minOrderCreateDate_30 AND o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		if (StringUtil.isNotEmpty(productType)) {
			sql.append(" AND p.producttype = '" + productType + "'");
		}
		if (StringUtil.isNotEmpty(productid)) {
			sql.append(" AND a.productid = '" + productid + "'");
		}
		sql.append(" GROUP BY a.sdorid ");
		sql.append(" ORDER BY a.receiveDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
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