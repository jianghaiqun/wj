package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * Created by liyinfeng on 2017/6/19.
 */
public class CancelContRefundManage extends Page {

    // 撤单退款查询订单信息
    public void getOrderInfo(DataGridAction dga) {
        // 得到查询SQL
        String querySql = "SELECT orderSn,policyNo,prem,payPrem, (SELECT codename FROM zdcode WHERE codetype = 'refundStatus' AND codevalue = STATUS) AS STATUS," +
                "(SELECT codename FROM zdcode WHERE codetype = 'cancelFrom' AND codevalue = cancelFrom) AS cancelFrom,repealDate FROM cancelcontrefund where 1=1";

        StringBuffer wherePart = new StringBuffer();
        // 订单号
        String ordersn = dga.getParams().getString("ordersn");

        if (StringUtil.isNotEmpty(ordersn)) {
            wherePart.append(" and orderSn like '%" + ordersn.trim() + "%'");
        }
        // 退款状态
        String refundStatus = dga.getParams().getString("refundStatus");
        if (StringUtil.isNotEmpty(refundStatus) && !"-1".equals(refundStatus)) {
            wherePart.append(" and status = '" + refundStatus.trim() + "'");
        }

        // 撤单方式
        String cancelFrom = dga.getParams().getString("cancelFrom");
        if (StringUtil.isNotEmpty(cancelFrom) && !"-1".equals(cancelFrom)) {
            wherePart.append(" and cancelFrom = '" + cancelFrom.trim() + "'");
        }
        wherePart.append(" ORDER BY repealDate DESC");

        // 测试输出结果SQL
        logger.info("SQL========={}{}",querySql,wherePart.toString());
        QueryBuilder qb = new QueryBuilder(querySql + wherePart.toString());
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dt.insertColumn("KID");
        if (dt != null) {
            for (int i = 0; i < dt.getRowCount(); i++) {
                dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
            }
        }

        dga.setTotal(qb);
        dga.bindData(dt);
    }

    public void changeRefundStatus() {
        String policyNo = $V("policyNo");
        String refundStatus = $V("refundStatus");

        String sql = "UPDATE cancelContRefund SET STATUS = ? WHERE policyno = ? ";
        QueryBuilder qb = new QueryBuilder(sql, refundStatus,policyNo);
        int count = qb.executeNoQuery();

        if (count > 0) {
            Response.setLogInfo(1, "操作成功！");
        } else {
            Response.setLogInfo(0, "操作失败！");
        }
    }
}
