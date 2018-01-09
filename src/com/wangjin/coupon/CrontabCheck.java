package com.wangjin.coupon;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CrontabCheck extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.coupon.CrontabCheck";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 一周前提醒
	 */
	public boolean remind() {
		String sql = "SELECT mail,mobile,parvalue,endtime,couponsn,direction,starttime,Prop3,Prop4,remindFlag FROM couponinfo WHERE id IN (SELECT id FROM couponinfo WHERE UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d'))=UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d'))-3600*24*7 AND STATUS ='2')";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String remindFlag = dt.getString(i, "remindFlag");
				// 是否需要发送提醒
				if (!"Y".equals(remindFlag)) {
					continue;
				}
				String mail = String.valueOf(dt.get(i, 0));
				String mobile = String.valueOf(dt.get(i, 1));
				if (mail != "") {
					// 发送邮件
					if (this.postEmail(mail, dt.get(i, 2).toString(), dt.get(i, 3).toString(), dt.get(i, 4).toString(),dt.get(i, 5).toString(),dt.get(i, 6).toString(),dt.getString(i, 7),dt.getString(i, 8))) {
						logger.info("邮件发送成功");
					}
				} else {
					// 发送短信
					if (this.sendMobile(mobile,dt.get(i, 2).toString(), dt.get(i, 3).toString(), dt.get(i, 4).toString())) {
						logger.info("短信发送成功");
					}
				}
			}
			logger.info("优惠券一周前提醒了{}条数据", dt.getRowCount());
			return true;
		} else {
			logger.info("优惠券没有一周前提醒的数据");
			return false;
		}
	}

	/**
	 * 优惠券过期检查
	 */
	public boolean pastDate() {
		String sql = "SELECT id FROM couponinfo WHERE UNIX_TIMESTAMP(endtime) <= UNIX_TIMESTAMP(NOW()) AND STATUS !='5' AND STATUS !='1' AND  STATUS !='3' AND  STATUS !='4' AND  STATUS !='5' AND  STATUS !='6' " ;
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		if (dt.getRowCount() > 0) {
			StringBuffer sql_update = new StringBuffer("update couponinfo set status='5' where id in (");
			for (int i = 0; i < dt.getRowCount(); i++) {
				sql_update.append("'" + String.valueOf(dt.get(i, 0)) + "',");
			}
			QueryBuilder querybuilder_update = new QueryBuilder(String.valueOf(sql_update).substring(0, sql_update.length() - 1) + ")");
			int row = querybuilder_update.executeNoQuery();
			logger.info("优惠券过期检查更新了{}条数据", row);
			return true;
		} else {
			logger.info("优惠券过期检查没有过期的数据");
			return false;
		}
	}

	/**
	 * 发送邮件
	 */
	public boolean postEmail(String mail, String parvalue, String endtime, String couponsn,String direction,String starttime,String prop3, String prop4) {
		Map<String, Object> data = new HashMap<String, Object>();
		//优惠券说明
		try {
			data.put("direction",java.net.URLDecoder.decode(direction,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		// 优惠金额
		data.put("parValue", parvalue);
		// 开始时间
		data.put("starttime", starttime.substring(0, 10));
		// 结束时间
		data.put("endtime", endtime.substring(0, 10));
		// 优惠券编号
		data.put("couponsn", couponsn);
		data.put("url",Config.getValue("FrontServerContextPath")+"/wj/shop/coupon!queryCoupon.action");

		// 非折扣券
		if (StringUtil.isEmpty(prop3) || "01".equals(prop3)) {
			// 优惠金额
			data.put("parValueShow", parvalue + "元");
			if (ActionUtil.sendMail("wj00089", mail, data)) {
				return true;
			} else {
				return false;
			}
		} else { // 折扣券
			// 优惠金额
			data.put("parValueShow", prop4 + "折");
			if (ActionUtil.sendMail("wj00131", mail, data)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 发送短信
	 */
	public boolean sendMobile(String mobile,String parvalue,String endtime,String couponsn) {
		// 优惠券结束时间
		String month = endtime.substring(5, 7);
		if (month.startsWith("0")) {
			month = month.substring(1, 2);
		}
		String day = endtime.substring(8, 10);
		if (day.startsWith("0")) {
			day = day.substring(1, 2);
		}
		String sendData = parvalue + ";" + endtime.substring(0, 4) + "-" + month + "-" + day;
		// 发送短信
		if (ActionUtil.sendSms("wj00090", mobile, sendData)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 定时任务调用主方法
	 * 
	 * @param id
	 */
	public void execute(long id) {
		// 优惠券过期提醒
		if ("0".equals(id + "")) {
			if (pastDate()) {
				logger.info("优惠券过期检测成功！");
			}
		}
		// 优惠券一周前提醒
		if ("1".equals(id + "")) {
			if (remind()) {
				logger.info("优惠券一周前提醒成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "优惠券过期定时任务");
		map.put("1", "优惠券一周前使用提醒定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "优惠券定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.coupon.CrontabCheck";
	}
}
