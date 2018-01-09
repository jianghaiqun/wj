package com.wangjin.infoseeker;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

public class UserTotalByDayUI extends Page {

    // 得到订单信息
    public void dg1DataBind(DataGridAction dga) {
        String startDate = dga.getParams().getString("startDate") +" 00:00:00";;
        String endDate = dga.getParams().getString("endDate") +" 23:59:59";
        String cPrice = dga.getParams().getString("cPrice");//会员回购统计部分 ：回购标记
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.id,d.id AS rid,a.memberid,d.recognizeesn AS recognizeeSn,a.ordersn,a.orderStatus,e.policyNo,e.appstatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d') AS svalidate,");
        sb.append("IFNULL( (SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn),a.channelsn) channelsn,a.channelsn as channelsnEn, c.ApplicantMail,a.renewalId, ");
        if("0".equals(cPrice)||"1".equals(cPrice)){
            //会员回购统计部分 判断会员回购统计部分   此处修复因活动优惠原因造成查找订单不准确问题
        }else{
            sb.append("f.FeeRate,");
        }
        sb.append("a.tbTradeSeriNo,b.ProductName,b.planName,");
        sb.append("ROUND(SUM(d.recognizeePrem),2) AS totalAmount,a.CreateDate,a.ModifyDate," + " a.ActivitySn,a.orderActivity,a.PayPrice,");
        sb.append("h.codeName AS orderstatusname , d.RecognizeeName AS recognizeeNu,c.ApplicantName,'' remark,");
        sb.append("IF (a.channelSn='b2b_app' || a.channelSn='b2c_pc' || a.channelSn='b2c_wap' || a.channelSn='b2b_ht', a.memberid, (SELECT CONCAT(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id=a.memberid)) AS MID ,  ");
        sb.append(" a.couponsn as couponSn,a.orderCoupon as orderCoupon,a.orderCoupon as parValue,a.orderIntegral as offsetPoint, a.orderIntegral as orderIntegral, a.paySn as paySn, '' as paymentReport,b.productid,b.insurancecompany ,a.diyActivityDescription as diyActivityDescription ,(SELECT ChannelName FROM channelinfo WHERE channelcode = (SELECT fromWap FROM member WHERE id=a.memberid)) fromWap ");
        sb.append(" FROM sdorders a");
        if("0".equals(cPrice)||"1".equals(cPrice)){//会员回购统计部分 判断会员回购统计部分   此处修复因活动优惠原因造成查找订单不准确问题(精确支付时间)
            sb.append(" LEFT JOIN tradeinformation tf  ON a.ordersn = tf.ordid ");
        }
        sb.append( " ,sdinformation b,sdinformationappnt c,sdinformationinsured d,sdinformationrisktype e,femrisk f,zdcode h ");
        sb.append("WHERE a.ordersn = b.ordersn AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn   AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn " );
        if("0".equals(cPrice)||"1".equals(cPrice)){//会员回购统计部分   此处修复因活动优惠原因造成查找订单不准确问题

        }else{
            sb.append( " AND b.productid = f.eriskid  ");
        }
        if("0".equals(cPrice)||"1".equals(cPrice)){//会员回购统计部分 判断
            sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and tf.receiveDate >='" + startDate + " 00:00:00' and tf.receiveDate  <='" + endDate + " 23:59:59' ");
        }else{
            sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus ");
        }
        // 渠道订单号
        if("0".equals(cPrice)||"1".equals(cPrice)){//会员回购统计部分判断
            sb.append(" and a.orderSn in  ");
            //此处根据保单号查找		//回购数据
            if("0".equals(cPrice)){
                sb.append("( SELECT a.orderSn ");
                sb.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
                sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
                sb.append(" AND a.channelsn!='b2b_app' ");
                sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
                sb.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
                sb.append(" AND a.memberid IN ( ");
                sb.append(" SELECT a.memberid ");
                sb.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
                sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
                sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
                sb.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
                sb.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
                sb.append(" AND NOT EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
                sb.append(" AND NOT EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
                sb.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
                sb.append("))");
            }else if("1".equals(cPrice)){//保单数据
                sb.append(" (SELECT  a.orderSn ");
                sb.append(" FROM SDOrderItem f ,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid  ");
                sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
                sb.append(" AND a.channelsn!='b2b_app' ");
                sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
                sb.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
                sb.append(")");
            }
        }
        sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");

        // 测试输出结果SQL
        QueryBuilder qb = new QueryBuilder(sb.toString());
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());


        dt.insertColumn("KID");
        // 产品更新弹出提示框标识
        dt.insertColumn("updateWarningFlag");

        if (dt != null) {
            // 对SessionId+OrderSn 做加密处理，保证期安全性
            // String KID = dga.getParams().getString("KID");
            String tempSql = null;
            for (int i = 0; i < dt.getRowCount(); i++) {
                dt.set(i, "KID", StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "orderSn")));
                if (dt.getString(i, "orderstatus").equals("7") || dt.getString(i, "appstatus").equals("1")) {
                    dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
                }
                // 保全记录查询
                String queryRemark = "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='" + dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
                QueryBuilder qbr = new QueryBuilder(queryRemark);
                DataTable dtr = qbr.executeDataTable();
                String remark = "";
                if (dtr != null && dtr.getRowCount() > 0) {
                    String remarkTem = "";
                    for (int j = 0; j < dtr.getRowCount(); j++) {
                        int a = j + 1;
                        remarkTem = dtr.getString(j, "remark") + "  " + dtr.getString(j, "OperateTime") + "  " + dtr.getString(j, "OperateName") + " && ";
                        if (j == dtr.getRowCount() -1 && remarkTem.indexOf("变更：初始商家订单号") >= 0) {
                            remark = remarkTem + " " + remark;
                        } else {
                            remark += a + ", " + remarkTem;
                        }
                    }
                    dt.set(i, "remark", remark);
                }
                // 被保人个数查询
                String sqlre = "SELECT recognizeeName,recognizeeIdentityId FROM sdinformationinsured WHERE ordersn ='" + dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
                QueryBuilder qbre = new QueryBuilder(sqlre);
                DataTable dtre = qbre.executeDataTable();
                if (dtre != null && dtre.getRowCount() > 0) {
                    dt.set(i, "recognizeeNu", dtre.getRowCount());
                } else {
                    dt.set(i, "recognizeeNu", 0);
                }

                String paymentSql = "select count(1) from paymentInfo where ordersn = ?";
                QueryBuilder paymentQb = new QueryBuilder(paymentSql, dt.getString(i, "ordersn"));
                if (paymentQb.executeInt() > 0) {
                    dt.set(i, "paymentReport", "是");

                }

                if (Integer.valueOf(dt.getString(i, "orderstatus")) < 7 || "8".equals(dt.getString(i, "orderstatus").trim())) {
                    dt.set(i, "PayPrice", "");
                }

                // 获取产品更新标识
                tempSql = "SELECT IsUpdateFlag, IsUpdateDate FROM sdproduct WHERE productid = ?";
                DataTable tempDt = new QueryBuilder(tempSql, dt.getString(i, "productid")).executeDataTable();
                if (tempDt != null && tempDt.getRowCount() > 0) {
                    String isUpdateFlag = tempDt.getString(0, "IsUpdateFlag");
                    String isUpdateDate = tempDt.getString(0, "IsUpdateDate");
                    String orderCreateDate = dt.getString(i, "createDate");
                    // 如果产品中心设置了更新标识为“Y”, 并且更新时间大于下单时间，设置更新提示层标识
                    if ("Y".equals(isUpdateFlag) && DateUtil.compare(isUpdateDate, orderCreateDate, DateUtil.Format_DateTime) >= 0) {
                        // 更新提示层标识
                        dt.set(i, "updateWarningFlag", "Y");
                    }
                }
            }

        }
        dga.setTotal(qb);
        dga.bindData(dt);
    }


}
