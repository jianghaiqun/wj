package com.sinosoft.datachannel;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

/**
 * Created by liyinfeng on 2016/10/14.
 */
public class ContinueInsureStatusUpdate extends ConfigEanbleTaskManager {
    public static final String CODE = "com.sinosoft.datachannel.ContinueInsureStatusUpdate";

    public boolean isRunning(long id) {
        return false;
    }

    public Mapx getConfigEnableTasks() {
        Mapx<String, String> map = new Mapx<String, String>();
        map.put("1", "定期更新订单的续保状态");
        return map;
    }

    public static void execute(long id) {
        if ("1".equals(id + "")) {
            // 查询客服呼出后，会员是否有订单购买
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT GROUP_CONCAT(DISTINCT a.ordersn) AS ordersns,MIN(b.oldordersn) as oldordersn ");
            builder.append(" FROM sdorders a, servicecallcollection b,tradeinformation c,SDInformation d");
            builder.append(" WHERE a.memberId = b.memberId AND a.orderStatus IN ('7', '10', '12', '14') AND a.ordersn = c.ordid ");
            builder.append(" AND a.ordersn = d.orderSn AND (d.ensureLimitType = 'Y' AND d.ensureLimit = '1' OR d.ensureLimitType = 'D' AND d.ensureLimit = '365'");
            builder.append(" AND d.ensureLimit = '365' OR d.ensureLimitType = 'M' AND d.ensureLimit = '12')");
            builder.append(" AND c.receiveDate >= b.lastCallTime AND c.receiveDate <= DATE_ADD(b.lastCallTime, INTERVAL 30 DAY) ");
            builder.append(" AND b.lastCallTime >= DATE_ADD(NOW(), INTERVAL -30 DAY) AND b.lastCallTime <= NOW() ");
            builder.append(" GROUP BY b.memberId ");

            QueryBuilder queryBuilder = new QueryBuilder(builder.toString());
            DataTable dataTable = queryBuilder.executeDataTable();

            // 更新呼出续保统计表，把续保订单存入表中
            String ordersns;
            String oldordersn;
            String tempordersns;
            String[] strarr;
            Transaction tran = new Transaction();
            builder = new StringBuilder();
            for (int i = 0; i < dataTable.getRowCount();i++) {
                ordersns = dataTable.getString(i, "ordersns");
                oldordersn = dataTable.getString(i, "oldordersn");
                if (StringUtil.isEmpty(ordersns)) {
                    tran.add(new QueryBuilder("UPDATE servicecallcollection SET continueStatus = 2 WHERE oldordersn = ' " + oldordersn + "'"));
                } else {
                    // 去重处理
                    if (ordersns.contains(oldordersn)) {
                        strarr = ordersns.split(",");
                        if (strarr.length == 1) {
                            tran.add(new QueryBuilder("UPDATE servicecallcollection SET continueStatus = 2 WHERE oldordersn = ' " + oldordersn + "'"));
                        } else {
                            for (int j = 0; j < strarr.length; j++) {
                                if (!strarr[j].equals(oldordersn)) {
                                    builder.append(strarr[j]);
                                    if (j != strarr.length - 1) {
                                        builder.append(",");
                                    }
                                }
                            }
                            tempordersns = builder.toString();
                            tran.add(new QueryBuilder("UPDATE servicecallcollection SET newOrderSn = '" + tempordersns +"',continueStatus = 1 WHERE oldordersn = '"+ oldordersn + "'"));
                        }
                    } else {
                        tran.add(new QueryBuilder("UPDATE servicecallcollection SET newOrderSn = '" + ordersns +"',continueStatus = 1 WHERE oldordersn = '"+ oldordersn + "'"));
                    }
                }
            }
            if (!tran.commit()) {
                logger.error("定时任务：定期更新订单的续保状态更新失败！");
            }
        }
    }

    public String getCode() {
        return CODE;
    }

    public String getName() {
        return "定期更新订单的续保状态";
    }

    @Override
    public void execute(String paramString) {
        execute(Long.parseLong(paramString));
    }

    @Override
    public boolean isRunning(String paramString) {
        return false;
    }

    @Override
    public String getID() {
        return "com.sinosoft.datachannel.ContinueInsureStatusUpdate";
    }

}
