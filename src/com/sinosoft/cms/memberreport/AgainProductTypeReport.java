package com.sinosoft.cms.memberreport;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AgainProductTypeReport extends Page implements FieldNameMatchable{

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
	 * dg1DataBind:复购险种查询. <br/>
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
		
		//复购险种数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.channelname,a.channelsn,p.ProductGenera,p.ProductType,COUNT(DISTINCT a.ordersn) AS ordercount, ");
		sql.append(" FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount,COUNT(DISTINCT a.sdorid) AS policycount ");
		sql.append(" FROM dataprecipitationorder_1 b,dataprecipitationorder a LEFT JOIN sdproduct p on p.productid = a.productId ");
		sql.append(" LEFT JOIN channelinfo c on c.ChannelCode = a.channelsn ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		sql.append(" GROUP BY a.channelsn, p.ProductType");
		sql.append(" ORDER BY a.channelsn");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
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

		String productType = dga.getParams().getString("productType");
		String csn = dga.getParams().getString("channelsn");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");

		String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		//复购险种数据
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn, a.applicantName,a.startdate,a.receiveDate,a.productname,a.planname,COUNT(a.sdorId) AS insuredNum, ");
		sql.append("SUM(a.timePrem) AS premSum,a.payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID,a.orderStatusName, ");
		sql.append("a.couponsn,SUM(a.couponValue) AS couponAmount,SUM(a.integralValue) AS integralSum,SUM(a.activityValue) AS activitySum, ");
		sql.append("a.couponValue,a.activitySn,a.activityValue,a.integralValue,c.channelname AS orderchannel,d.channelname AS memberchannel,p.ProductGenera ");
		sql.append(" FROM dataprecipitationorder_1 b,dataprecipitationorder a ");
		sql.append(" LEFT JOIN sdproduct p ON a.productid = p.ProductID ");
		sql.append(" LEFT JOIN channelinfo c ON c.channelcode = a.channelsn ");
		sql.append(" LEFT JOIN channelinfo d ON d.channelcode = a.fromWap ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
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
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		sql.append(" GROUP BY a.ordersn ORDER BY a.receiveDate DESC");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
		dga.bindData(dt);
	}

	public static void dg1DataBind_Rdialog(DataGridAction dga) {

		String csn = dga.getParams().getString("channelsn");
		String productType = dga.getParams().getString("productType");
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");

		String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		//复购险种数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.ordersn,a.recognizeeSn,a.productname,a.policyNo,a.payPrice,a.orderStatusName,a.applicantName,a.recognizeeName, ");
		sql.append(" a.recognizeeIdentityId,a.startDate,a.endDate,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID ");
		sql.append(" FROM dataprecipitationorder_1 b,dataprecipitationorder a LEFT JOIN sdproduct p on p.productid = a.productId ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS (SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
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
		sql.append(" GROUP BY a.sdorid ");
		sql.append(" ORDER BY a.receiveDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
        
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