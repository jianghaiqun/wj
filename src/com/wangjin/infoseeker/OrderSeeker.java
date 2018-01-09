package com.wangjin.infoseeker;

import com.sinosoft.framework.GetDBdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderSeeker {
	private static final Logger logger = LoggerFactory.getLogger(OrderSeeker.class);
	/*
	 * 插入订单的订单号和该订单生成人的IP
	 */
	public static void orderInfoInsert(String sn, String ip) {
		try {
			GetDBdata db = new GetDBdata();
			String sql_insert = "insert into ipseeker values('" + sn + "','"
					+ ip + "','')";
			db.execUpdateSQL(sql_insert, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 插入订单的订单号和该订单支付人的IP
	 */
	public static void alipayInfoInsert(String sn, String ip) {
		try {
			GetDBdata db = new GetDBdata();
			String sql_insert = "update ipseeker set alipayIP='" + ip
					+ "' where orderSn='" + sn + "'";
			db.execUpdateSQL(sql_insert, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
