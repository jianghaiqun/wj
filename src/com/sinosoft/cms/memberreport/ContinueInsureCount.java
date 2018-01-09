package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.wangjin.infoseeker.QueryUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liyinfeng on 2016/10/12.
 */
public class ContinueInsureCount extends Page {

    /**
     * 初始化回购查询页面.
     *
     * @param params
     * @return
     */
    public static Mapx init(Mapx params) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        params.put("startDate", PubFun.getPrevMonthDay(fmt.format(date)));
        params.put("endDate", fmt.format(date));
        return params;
    }

    /**
     * 续保查询
     *
     * @param dga
     */
    public void dg1DataBind(DataGridAction dga) {

        String startDate = dga.getParam("startDate")+ " 00:00:00";
        String endDate = dga.getParam("endDate")+" 23:59:59";
        String channelsn = dga.getParam("contant");

        // 呼出且购买数
        String callAndBuyOrders = getCallAndBuyOrders(startDate, endDate, channelsn);
        String callAndBuyOrderCount;
        if (!StringUtil.isEmpty(callAndBuyOrders)) {
            String [] arr = callAndBuyOrders.split(",");
            callAndBuyOrderCount = String.valueOf(arr.length);
        } else {
            callAndBuyOrderCount = "0";
        }

        // 实际呼出次数
        DataTable dtBaseCallOrder = getBaseCallOrder(startDate, endDate, channelsn);
        String baseCallOrderCount = getBaseCallOrderCount(startDate, endDate, channelsn);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dtBaseCallOrder.getRowCount(); i++) {
            stringBuilder.append(dtBaseCallOrder.getString(i,0));
            if (i != dtBaseCallOrder.getRowCount() - 1) {
                stringBuilder.append(",");
            }
        }
        // 实际呼出的订单号，用","连接
        String baseCallOrder = stringBuilder.toString();

        // 购买的保费与保单
        DataTable dtInsurePayTotalAndCount1 = getInsurePayTotalAndCount(callAndBuyOrders);
        //保费
        String totalAmount1 = dtInsurePayTotalAndCount1.getString(0, "totalAmount");
        // 保单
        String policycount1 = dtInsurePayTotalAndCount1.getString(0, "policycount");

        // 实际呼出订单的预计保费与保单
        DataTable dtInsurePayTotalAndCount2 = getInsurePayTotalAndCount(baseCallOrder);
        //保费
        String totalAmount2 = dtInsurePayTotalAndCount2.getString(0, "totalAmount");
        // 保单
        String policycount2 = dtInsurePayTotalAndCount2.getString(0, "policycount");

        // 成功率计算
        logger.info("****************呼出且购买数callAndBuyOrderCount : {}", callAndBuyOrderCount);
        String orderCountPercent = computePercent(callAndBuyOrderCount, baseCallOrderCount);
        String amountCountPercent = computePercent(totalAmount1, totalAmount2);
        String policyCountPercent = computePercent(policycount1, policycount2);

        DataTable dt = new DataTable();
        Object[] catalogRow = {"呼出且购买计数", callAndBuyOrderCount, totalAmount1, policycount1, callAndBuyOrders, "0"};
        Object[] catalogRow1 = {"实际呼出单数", baseCallOrderCount, totalAmount2, policycount2, baseCallOrder, "1"};
        Object[] catalogRow2 = {"成功率", orderCountPercent, amountCountPercent, policyCountPercent, "", "2"};
        dt.insertRow(catalogRow,0);
        dt.insertRow(catalogRow1,1);
        dt.insertRow(catalogRow2,2);

        dga.bindData(dt);
    }

    /**
     * 呼出回购查询
     *
     * @param dga
     */
    public void dg2DataBind(DataGridAction dga) {
        String startDate = dga.getParam("startDate")+ " 00:00:00";
        String endDate = dga.getParam("endDate")+" 23:59:59";
        String channelsn = dga.getParam("contant");

        // 续保回购订单数
        List<String> orderList = getCallAndBuyAgainOrders(startDate, endDate, channelsn);
        String buyAgainCount = String.valueOf(orderList.size());
        StringBuilder stringBuilder1 = new StringBuilder();
        for (int i = 0; i < orderList.size(); i++) {
            stringBuilder1.append(orderList.get(i));
            if (i != orderList.size() - 1) {
                stringBuilder1.append(",");
            }
        }

        //续保回购订单数,用","连接
        String bugAgainOrderSn  = stringBuilder1.toString();

        // 实际呼出次数
        DataTable dtBaseCallOrder = getBaseCallOrder(startDate, endDate, channelsn);
        String baseCallOrderCount = getBaseCallOrderCount(startDate, endDate, channelsn);
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < dtBaseCallOrder.getRowCount(); i++) {
            stringBuilder2.append(dtBaseCallOrder.getString(i,0));
            if (i != dtBaseCallOrder.getRowCount() - 1) {
                stringBuilder2.append(",");
            }
        }

        // 实际呼出的订单号，用","连接
        String baseCallOrder = stringBuilder2.toString();

        // 回购的保费与保单
        DataTable dtInsurePayTotalAndCount1 = getInsurePayTotalAndCount(bugAgainOrderSn);
        //保费
        String totalAmount1 = dtInsurePayTotalAndCount1.getString(0, "totalAmount");
        // 保单
        String policycount1 = dtInsurePayTotalAndCount1.getString(0, "policycount");

        // 实际呼出订单的预计保费与保单
        DataTable dtInsurePayTotalAndCount2 = getInsurePayTotalAndCount(baseCallOrder);
        //保费
        String totalAmount2 = dtInsurePayTotalAndCount2.getString(0, "totalAmount");
        // 保单
        String policycount2 = dtInsurePayTotalAndCount2.getString(0, "policycount");

        // 成功率计算
        String orderCountPercent = computePercent(buyAgainCount, baseCallOrderCount);
        String amountCountPercent = computePercent(totalAmount1, totalAmount2);
        String policyCountPercent = computePercent(policycount1, policycount2);

        DataTable dt = new DataTable();
        Object[] catalogRow = {"呼出且购买计数", buyAgainCount, totalAmount1, policycount1, bugAgainOrderSn, "0"};
        Object[] catalogRow1 = {"实际呼出单数", baseCallOrderCount, totalAmount2, policycount2, baseCallOrder, "1"};
        Object[] catalogRow2 = {"成功率", orderCountPercent, amountCountPercent, policyCountPercent, "", "2"};
        dt.insertRow(catalogRow,0);
        dt.insertRow(catalogRow1,1);
        dt.insertRow(catalogRow2,2);

        dga.bindData(dt);
    }

    /**
     * getOrderSDialog:订单数据. <br/>
     *
     * @author wwy
     * @param dga
     */
    public void getOrderSDialog(DataGridAction dga) {
        // 订单号集合字符串
        String orderSns = $V("orderSns");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ma.userName ,a.ordersn, appnt.applicantName, i.startdate, a.modifyDate, i.productname, i.planname, ");
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
        sql.append(" FROM sdorders a LEFT JOIN member m ON a.memberid = m.id ");
        sql.append(" LEFT JOIN sdmark ma ON ma.ordersn=a.ordersn ");
        sql.append(" LEFT JOIN zdcode h ON h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus, ");
        sql.append(" sdinformation i LEFT JOIN sdproduct p ON i.productid = p.ProductID,sdinformationappnt appnt ");

        sql.append(" WHERE i.informationSn = appnt.informationSn AND i.orderSn = a.orderSn ");
        sql.append(" AND FIND_IN_SET(a.orderSn, '" + orderSns + "') ");
        sql.append(" GROUP BY a.ordersn order by a.modifydate desc ");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        dga.bindData(dt);
    }

    /**
     * getRiskTypeDialog:保单数据. <br/>
     *
     * @author wwy
     * @param dga
     */
    public void getRiskTypeDialog(DataGridAction dga) {
        // 订单号集合字符串
        String orderSns = $V("orderSns");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT o.ordersn, insured.recognizeeSn, i.productname, risk.policyNo, risk.payPrice, h.codename, ");
        sql.append("appnt.applicantName, insured.recognizeeName, insured.recognizeeIdentityId, risk.svaliDate, risk.evaliDate, ");
        sql.append("CONCAT(IFNULL(mem.email,''),'/',IFNULL(mem.mobileno,'')) AS MID ");
        sql.append("FROM sdorders o LEFT JOIN member mem ON mem.id = o.memberid ");
        sql.append("LEFT JOIN zdcode h ON h.CodeType = 'orderstatus' AND h.codevalue = o.orderstatus, ");
        sql.append("sdinformation i,sdinformationappnt appnt, sdinformationRiskType risk, sdinformationinsured insured ");
        sql.append(" WHERE i.ordersn = o.orderSn AND appnt.informationSn = i.informationSn ");
        sql.append(" AND risk.ordersn = o.orderSn AND insured.informationSn = i.informationSn ");
        sql.append(" AND FIND_IN_SET(o.orderSn, '" + orderSns + "') ");
        sql.append(" GROUP BY risk.id order by o.modifydate desc ");
        QueryBuilder qb = new QueryBuilder(sql.toString());
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

        dga.bindData(dt);
    }

    /**
     * 获取呼出且购买 续保订单(集合)
     * @param startDate
     * @param endDate
     * @return
     */
    private String getCallAndBuyOrders (String startDate, String endDate, String channelsn) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT GROUP_CONCAT(t1.newOrderSn) ");
        builder.append("FROM servicecallcollection t1 ,SDInformation t2 ");
        if (StringUtil.isNotEmpty(channelsn)) {
            builder.append(",sdorders t3 ");
        }
        builder.append("WHERE t1.callConnect = '1' AND t1.oldordersn = t2.ordersn ");
        builder.append("AND t2.endDate >= '" + startDate + "' ");
        builder.append("AND t2.endDate <= '" + endDate + "' ");

        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn,"");
            builder.append(" and t1.oldordersn = t3.ordersn ");
            builder.append(" and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = t3.channelsn and ChannelCode IN ("+channel+"))");
        }

        QueryBuilder qb = new QueryBuilder(builder.toString());
        return qb.executeString();
    }

    /**
     * 获取呼出且回购订单(集合)
     * @param startDate
     * @param endDate
     * @return
     */
    private List<String> getCallAndBuyAgainOrders (String startDate, String endDate, String channelsn) {
        List<String> resultList = new ArrayList<String>();

        // 续保订单
        String callAndBuyOrders = getCallAndBuyOrders(startDate, endDate, channelsn);
        if (StringUtil.isEmpty(callAndBuyOrders)) {
            return resultList;
        }

        // 回购订单
        // 时间段内 第一次购买的时间
        String mindateSql = "";
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            mindateSql = "SELECT DATE_FORMAT(MIN(o.createdate),'%Y-%m-%d') FROM sdorders o INNER JOIN tradeinformation tf ON o.ordersn=tf.ordid WHERE tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' AND o.memberid = a.memberid";
        }

        //回购数据
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT GROUP_CONCAT(DISTINCT a.ordersn) AS ordercount ");
        sql.append(" FROM SDOrderItem f, sdinformationrisktype sdor, member m, sdorders a inner JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
        sql.append(" WHERE a.ordersn = sdor.ordersn AND a.orderStatus IN ('7', '10', '12', '14') AND sdor.appStatus <= '1' AND f.ordersn = a.ordersn AND a.memberid IS NOT NULL AND a.memberid != '' AND (f.channelCode != '02' OR f.channelCode IS NULL) AND a.memberid = m.id ");
        sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
        sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
        sql.append(" AND NOT EXISTS (SELECT b.memberid FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<(" + mindateSql + ")) ");
        sql.append(" AND a.memberid NOT IN (SELECT b.id FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD((" + mindateSql + "), INTERVAL -30 DAY)) ");
        sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn,"");
            sql.append(" and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))");
        }
        QueryBuilder queryBuilder = new QueryBuilder(sql.toString());
        String buyAgainOrders = queryBuilder.executeString();

        if (StringUtil.isEmpty(buyAgainOrders)) {
            return resultList;
        }

        //比较 续保订单与回购订单的交集
        String[] callAndBuyOrdersArray = callAndBuyOrders.split(",");
        String[] buyAgainOrdersArray = buyAgainOrders.split(",");
        List<String> listA = Arrays.asList(callAndBuyOrdersArray);
        List<String> listB = Arrays.asList(buyAgainOrdersArray);

        for (int i = 0; i < listA.size(); i++) {
            for (int j = 0; j < listB.size(); j++) {
                if (listA.get(i).equals(listB.get(j))) {
                    resultList.add(listA.get(i));
                    break;
                }
            }
        }

        return resultList;
    }

    /**
     * 获取保费和保单数
     *
     * @return
     */
    private DataTable getInsurePayTotalAndCount(String strOrderSns) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT FORMAT(IFNULL(SUM(timePrem), '0'), 2) AS totalAmount,COUNT(1) AS policycount ");
        builder.append("FROM sdinformationrisktype WHERE FIND_IN_SET(ordersn,'"+ strOrderSns +"')");
        QueryBuilder qb = new QueryBuilder(builder.toString());
        DataTable dataTable = qb.executeDataTable();
        return dataTable;
    }

    /**
     * 实际呼出订单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private DataTable getBaseCallOrder (String startDate, String endDate, String channelsn) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT oldordersn FROM servicecallcollection t1,SDInformation t3 ");
        if (StringUtil.isNotEmpty(channelsn)) {
            builder.append(",sdorders t2 ");
        }
        builder.append("WHERE t1.callConnect = '1' AND t1.oldordersn = t3.ordersn ");
        builder.append("AND t3.endDate >= '" + startDate + "' AND t3.endDate <= '" + endDate + "' ");
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn,"");
            builder.append(" and t1.oldordersn = t2.ordersn ");
            builder.append(" and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = t2.channelsn and ChannelCode IN ("+channel+"))");
        }
        QueryBuilder qb = new QueryBuilder(builder.toString());
        DataTable baseCallOrder = qb.executeDataTable();
        return baseCallOrder;
    }

    /**
     * 实际呼出次数
     *
     * @param startDate
     * @param endDate
     * @param channelsn
     * @return
     */
    private String getBaseCallOrderCount (String startDate, String endDate, String channelsn) {
        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT COUNT(oldordersn) AS callCountSum FROM servicecallcollection t1,  SDInformation t3  ");
        if (StringUtil.isNotEmpty(channelsn)) {
            builder.append(",sdorders t2 ");
        }

        builder.append("WHERE t1.callConnect = '1' AND t1.oldordersn = t3.ordersn ");
        builder.append("AND t3.endDate >= '" + startDate + "' AND t3.endDate <= '" + endDate + "' ");
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn,"");
            builder.append(" and t1.oldordersn = t2.ordersn ");
            builder.append(" and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = t2.channelsn and ChannelCode IN ("+channel+"))");
        }
        QueryBuilder qb = new QueryBuilder(builder.toString());
        Double baseCallOrderCount = qb.executeDouble();
        return String.valueOf(baseCallOrderCount.intValue());
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
        if (StringUtil.isEmpty(a) || "0".equals(a) || y.compareTo(new BigDecimal("0.00")) == 0) {
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
    
     
    public void mark(){
	 
        String orderSns = $V("orderSns");
		String userName = $V("dg1");

		String[] orders= orderSns.split(",");
		Transaction trans = new Transaction();
		for (String string : orders) {
		 
			String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
			QueryBuilder qb = new QueryBuilder(" insert into sdmark VALUES (?,?,?,now(),'1','1',now(),'1')");
			qb.add(id_index);
			qb.add(string);
			qb.add(userName);
			trans.add(qb);
		}

		
    	if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
    }
    
    
    
}
