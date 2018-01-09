package com.sinosoft.aeonlife;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.cc.CCDealInterface;
import com.sinosoft.jdt.tb.TBDealInterfaceNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SearchOrderService <br/>
 * Function: 理财险处理中数据查询. <br/>
 * date: 2016年6月20日 下午3:06:44 <br/>
 *
 * @author wwy
 */
public class LcbxSearchOrderService {
    private static final Logger logger = LoggerFactory.getLogger(LcbxSearchOrderService.class);
    public static void deal() {
        try {
            Transaction tr = new Transaction();
            // 支付中数据
            List<Map<String, Object>> data = queryDate();

            // 成功 订单号字符串
            String successOrdersn = "";
            //　成功订单号列表
            List<String> successOrdersnL = new ArrayList<String>();
            String failOrdersn = "";
            for (Map<String, Object> map : data) {

                //map中的key有ComCode,ordersn,insuredSn
                String orderSn = (String) map.get("ordersn");
                // String insuredSn = (String) map.get("insuredSn");
                // String comCode = (String) map.get("ComCode");
                String paySn = (String) map.get("paySn");
                String totalAmount = (String) map.get("totalAmount");
                Map<String, Object> result = invokeJDTForOrder(map);
                // System.out.println(map + " reslut: " + result);
                if (null == result) {
                    break;
                }
                String policyNo = (String) result.get("policyNo");
                // String insuranceBankSeriNO = (String) result.get("BK_SERIAL");
                String resultCode = (String) result.get("PA_RSLT_CODE");
                String resultMsg = (String) result.get("PA_RSLT_MESG");
                String policyPath = (String) result.get("policyPath");

                if ("000000".equals(resultCode)) {
                    successOrdersnL.add(orderSn);
                    successOrdersn += "'" + orderSn + "',";
                    String updSql = "update SDInformationrisktype set appStatus = '1', insurerFlag = '000000', insureMsg = '支付成功', "
                            + " couponValue = '0.00', integralValue = '0.00', activityValue = '0.00', payPrice = ?, "
                            + " electronicCout = ?, policyNo = ? where ordersn = ?";
                    QueryBuilder qb = new QueryBuilder(updSql);
                    qb.add(totalAmount);
                    qb.add(policyPath);
                    qb.add(policyNo);
                    qb.add(orderSn);
                    tr.add(qb);

                    // 更新支付记录
                    String updSql_tradeInfo = "update tradeinfo set Remark1 = '已支付', Modifydate = ? where ordersn = ?";
                    QueryBuilder qb_tradeInfo = new QueryBuilder(updSql_tradeInfo);
                    qb_tradeInfo.add(DateUtil.getCurrentDate());
                    qb_tradeInfo.add(orderSn);
                    tr.add(qb_tradeInfo);

                    String updSql_TradeInformation = "update TradeInformation set Traderesult = '0', Paystatus = '1', Receivedate = ? where ordID = ?";
                    QueryBuilder qb_TradeInformation = new QueryBuilder(updSql_TradeInformation);
                    qb_TradeInformation.add(DateUtil.getCurrentDate());
                    qb_TradeInformation.add(orderSn);
                    tr.add(qb_TradeInformation);

                    TradeSummaryInfoSchema sum = new TradeSummaryInfoSchema();
                    TradeSummaryInfoSet sumSet = sum.query(new QueryBuilder(" where OrderSns = ?", orderSn));
                    sum.setPaySn(paySn);
                    sum.setTradeSn(paySn);
                    sum.setOrderSns(orderSn);
                    sum.setCouponSumAmount("0.00");
                    sum.setActivitySumAmount("0.00");
                    sum.setPointSumAmount("0.00");
                    sum.setTradeResult("0");
                    sum.setPayType("zjzf");
                    sum.setPayTypeName("直接支付");
                    // 支付金额
                    sum.setTotalAmount(totalAmount);
                    sum.setTradeAmount(totalAmount);
                    
                    if (sumSet.size() > 0) {
                        sum.setid(sumSet.get(0).getid());
                        sum.setModifyDate(new Date());
                        tr.add(sum, Transaction.UPDATE);
                    } else {
                        sum.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
                        sum.setCreateDate(new Date());
                        tr.add(sum, Transaction.INSERT);
                    }


                } else if ("111111".equals(resultCode)) {
                    failOrdersn += "'" + orderSn + "',";
                    String msg = "交易失败！";
                    if (StringUtil.isNotEmpty(String.valueOf(resultMsg))) {
                        msg = resultMsg;
                    }
                    String updSql = "update SDInformationrisktype set appStatus = '0', insurerFlag = '111111', insureMsg = ?  where ordersn = ?";
                    QueryBuilder qb = new QueryBuilder(updSql, msg, orderSn);
                    tr.add(qb);
                } else if ("222222".equals(resultCode)) {
                    logger.info("继续支付中 ordersn:{}", orderSn);
                } else {
                    logger.error("理财险查询异常! {}", data);
                }
            }

            // 成功更新 订单状态
            if (StringUtil.isNotEmpty(successOrdersn)) {
                successOrdersn = successOrdersn.substring(0, successOrdersn.length() - 1);
                QueryBuilder qb = new QueryBuilder("update SDOrders set orderStatus = '7' where ordersn in (" + successOrdersn + ")");
                tr.add(qb);
            }
            // 失败更新订单状态
            if (StringUtil.isNotEmpty(failOrdersn)) {
                failOrdersn = failOrdersn.substring(0, failOrdersn.length() - 1);
                QueryBuilder qb = new QueryBuilder("update SDOrders set orderStatus = '5' where ordersn in (" + failOrdersn + ")");
                tr.add(qb);
            }
            // 更新数据库
            if (StringUtil.isNotEmpty(successOrdersn) || StringUtil.isNotEmpty(failOrdersn)) {
                if (tr.commit()) {
                    logger.info("理财险处理中数据处理完成");
                } else {
                	ActionUtil.sendAlarmMail("理财险保单查询异常!");
                }
            }
            for (String ordersn : successOrdersnL) {
                try {
                    //发送消息队列到经代通下载电子保单
                    Map<String, String> mapMQ = new HashMap<String, String>();
                    mapMQ.put("orderSn", ordersn);
                    mapMQ.put("policyNo", "");
                    mapMQ.put("channelCode", "wj");
                    mapMQ.put("isSendEmail", "Y");
                    mapMQ.put("isRewrite", "Y");
                    mapMQ.put("paramMap", null);
                    ProducerMQ mq = new ProducerMQ();
                    mq.sendToJDT(JSON.toJSONString(mapMQ));
                } catch (Exception e) {
                    logger.error("MQ异常，" + ordersn + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("理财险保单查询异常，" + e.getMessage(), e);
            ActionUtil.sendAlarmMail("理财险保单查询异常，" + e.getMessage());
        }
    }

    /**
     * selectData:获取支付中数据. <br/>
     *
     * @return
     * @author wwy
     */
    @Deprecated
    private static List<Map<String, Object>> selectData() {
        String sql = "SELECT r.insuranceBankSeriNO, d.bankCode,d.bankUserName,d.bankNo, SUBSTR(r.riskcode,1,4) as comCode, o.ordersn, o.paysn "
                + "FROM sdorders o, sdinformationRiskType r, DirectPayBankInfo d "
                + "WHERE o.ordersn = r.ordersn AND o.ordersn = d.ordersn AND r.insurerFlag = '222222' "
                + "AND FIND_IN_SET(r.riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts')) ";
        QueryBuilder qb = new QueryBuilder(sql);
        DataTable dt = qb.executeDataTable();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (dt.getRowCount() > 0) {
            for (int i = 0; i < dt.getRowCount(); i++) {
                String policyNo = dt.getString(i, "insuranceBankSeriNO");
                String bankCode = dt.getString(i, "bankCode");
                String bankUserName = dt.getString(i, "bankUserName");
                String bankNo = dt.getString(i, "bankNo");
                String comCode = dt.getString(i, "comCode");
                String ordersn = dt.getString(i, "ordersn");
                String paysn = dt.getString(i, "paysn");

                Map<String, Object> row = new HashMap<String, Object>();
                row.put("BussNo", policyNo);
                row.put("AccName", bankUserName);
                row.put("BankCode", bankCode);
                row.put("BankAccNo", bankNo);
                row.put("ComCode", comCode);
                row.put("ordersn", ordersn);
                row.put("paysn", paysn);
                data.add(row);
            }
        }
        return data;
    }


    /**
     * service:调用经代通 订单查询. <br/>
     *
     * @param toMap
     * @param comCode
     * @return
     * @author wwy
     */
    @Deprecated
    private static Map<String, Object> service(Map<String, Object> toMap, String comCode) {

        Map<String, Object> result = null;
        try {
            Class<?> ccDeal = Class.forName("com.sinosoft.jdt.cc.CCDeal" + comCode);
            CCDealInterface ccdi = (CCDealInterface) ccDeal.newInstance();
            result = ccdi.cardCheck(toMap);
        } catch (Exception e) {
            logger.error("调用经代通接口出现异常" + e.getMessage() , e);
        }
        return result;
    }

    private static Map<String, Object> invokeJDTForOrder(Map<String, Object> paramMap) {
        String strOrderSn = (String) paramMap.get("ordersn");
        String insuredSn = (String) paramMap.get("insuredSn");
        String comCode = ((String) paramMap.get("ComCode"));
        if (StringUtil.isEmpty(strOrderSn) || StringUtil.isEmpty(insuredSn) || StringUtil.isEmpty(comCode)) {
            logger.warn("invokeJDTForOrder方法无法调用,参数不全");
            return null;
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        boolean invokeFlag;
        try {
            Class<?> tbDeal = Class.forName("com.sinosoft.jdt.tb.TBDeal" + comCode);
            TBDealInterfaceNew tbDealInstance = (TBDealInterfaceNew) tbDeal.newInstance();
            invokeFlag = tbDealInstance.dealData(result, comCode, strOrderSn, insuredSn);
        } catch (Exception e) {
            logger.error("调用经代通接口出现异常" + e.getMessage(), e);
            invokeFlag = false;
        }
        if (invokeFlag)
            return result;
        else
            return null;
    }

    /**
     * selectData:获取支付中数据. <br/>
     *
     * @return
     * @author wwy
     */
    private static List<Map<String, Object>> queryDate() {
        String sql = "SELECT \n" +
                "    r.orderSn,\n" +
                "    SUBSTR(r.riskcode, 1, 4) comCode,\n" +
                "    i.insuredSn,\n" +
                "    o.paySn,\n" +
                "    o.totalAmount\n"+
                "FROM\n" +
                "    sdinformationRiskType r,\n" +
                "    sdinformationinsured i,\n" +
                "    sdorders o\n" +
                "WHERE\n" +
                "    r.insurerFlag = '222222'\n" +
                "        AND FIND_IN_SET(r.riskCode,\n" +
                "            (SELECT \n" +
                "                    VALUE\n" +
                "                FROM\n" +
                "                    zdconfig\n" +
                "                WHERE\n" +
                "                    TYPE = 'LicaiBaoxianProducts'))\n" +
                "        AND r.recognizeeSn = i.recognizeeSn\n" +
                "        AND r.orderSn = o.orderSn\n";
        QueryBuilder qb = new QueryBuilder(sql);
        DataTable dt = qb.executeDataTable();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (dt.getRowCount() > 0) {
            for (int i = 0; i < dt.getRowCount(); i++) {
                Map<String, Object> row = new HashMap<String, Object>();
                row.put("ComCode", dt.get(i, "comCode"));
                row.put("ordersn", dt.get(i, "orderSn"));
                row.put("insuredSn", dt.get(i, "insuredSn"));
                row.put("paySn", dt.get(i, "paySn"));
                row.put("totalAmount", dt.get(i, "totalAmount"));
                data.add(row);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        deal();
    }
}

