package com.sinosoft.datachannel;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class OrderStatusManager extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.OrderStatusManager";

	public boolean isRunning(long id) {

		return false;
	}

	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		// add by wangej 20160317 系统凌晨零点30分将新华待支付并且已经过了起保日期的订单自动设置为取消状态
		map.put("0", "新华待支付订单状态更新");
		return map;
	}

	public void execute(long id) {

		if ("0".equals(id + "")) {
			try {
				StringBuffer sqlOrderSn = new StringBuffer();
				sqlOrderSn.append("SELECT a.orderSn orderSn FROM sdorders a, sdinformation b WHERE a.orderStatus = 5 ");
				sqlOrderSn.append("AND a.orderSn = b.orderSn AND b.insuranceCompany = '1014' AND b.startDate <= SYSDATE() ");
				QueryBuilder qb = new QueryBuilder(sqlOrderSn.toString());
				DataTable dt = qb.executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					Transaction transaction = new Transaction();
					for (int i = 0, j = dt.getRowCount(); i < j; i++) {
						transaction.add(new QueryBuilder("UPDATE sdorders SET orderStatus = 3 WHERE orderSn = ? ",
								dt.getString(i, "orderSn")));
						transaction.add(new QueryBuilder("DELETE FROM SDShoppingCart WHERE orderSn =? ", dt.getString(i,
								"orderSn")));
					}
					if (!transaction.commit()) {
						logger.error("类OrderStatusManager执行方法execute()-0-新华待支付订单状态更新 错误");
					}
				}

			} catch (Exception e) {
				logger.error("类OrderStatusManager执行方法execute()-0-新华待支付订单状态更新  发生异常！" + e.getMessage(), e);
			}
		}

	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "订单状态管理";
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

		return "com.sinosoft.datachannel.OrderStatusManager";
	}

	/*****************************************************************************/
	public static void main(String[] args) {

		OrderStatusManager orderStatusManager = new OrderStatusManager();
		orderStatusManager.execute(0);
	}
}
