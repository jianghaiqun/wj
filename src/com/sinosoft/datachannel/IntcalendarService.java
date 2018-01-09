/**
 * Project Name:wj
 * File Name:IntcalendarService.java
 * Package Name:com.sinosoft.datachannel
 * Date:2015年11月4日下午4:38:21
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.datachannel;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:IntcalendarService <br/>
 * Function:TODO ADD 检测积分情况定时任务. <br/>
 * Date:2015年11月4日 下午4:38:21 <br/>
 *
 * @author:郭斌
 */
public class IntcalendarService extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.ConfigEanbleTaskManager";

	@Override
	public String getID() {

		return CODE;
	}

	@Override
	public String getName() {

		return "检测积分情况定时任务";
	}

	@Override
	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "获取积分预警");

		return map;
	}

	@Override
	public void execute(String paramString) {

		String lastdate = DateUtil.toString(DateUtil.addDay(new Date(), -1));

		String sql = "select s1.id,s2.payPrice,s1.point ,s2.createdate ,s2.ordersn,ROUND( s1.point/s2.payPrice,2  )  ,s2.memberId,member.mobileNO,member.email";
		sql += " from sdinformation s1,sdorders s2 left join member on (s2.memberid=member.id)  ";
		sql += " where s1.ordersn=s2.ordersn and s2.orderStatus='7' and s1.point/s2.payPrice > ? and s2.createdate>= ? ";

		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(Config.getValue("BuyIntegralThresHold"));
		qb.add(lastdate);
		DataTable dt = qb.executeDataTable();

		if (dt == null || dt.getRowCount() == 0) {
			return; 
		}

		List<Map<String, String>> content = new ArrayList<Map<String, String>>();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("id", dt.get(i).getString("id"));
			data.put("payprice", dt.get(i).getString("payPrice"));
			data.put("point", dt.get(i).getString("point"));
			data.put("createdate", dt.get(i).getString("createdate"));
			data.put("ordersn", dt.get(i).getString("ordersn"));

			String membercode = "";

			String mobileNO = dt.get(i).getString("mobileNO");
			String email = dt.get(i).getString("email");

			membercode += StringUtil.isEmpty(mobileNO) ? "-" : mobileNO + "/";
			membercode += StringUtil.isEmpty(email) ? "-" : email;

			data.put("membercode", membercode);
			content.add(data);

		}
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			// 发送邮件
			data.put("content", content);
			String toEmail = Config.getValue("BuyIntegralWarnMail");
			if(StringUtil.isNotEmpty(toEmail)){
				ActionUtil.sendMail("wj00206", toEmail, data);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public static void main(String[] args) {

		IntcalendarService i = new IntcalendarService();
		i.execute("1");
	}

	@Override
	public boolean isRunning(String paramString) {

		return false;
	}

	@Override
	public String getCode() {

		return CODE;
	}

}
