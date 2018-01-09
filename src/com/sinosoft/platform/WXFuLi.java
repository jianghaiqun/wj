/**
 * Project Name:wj
 * File Name:WXFuLi.java
 * Package Name:com.sinosoft.platform
 * Date:2017年3月23日下午9:06:29
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.platform;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

/**
 * ClassName:WXFuLi <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2017年3月23日 下午9:06:29 <br/>
 *
 * @author:liuhongyu
 */

public class WXFuLi extends Page {


	public static Mapx init(Mapx params) {

		return params;
	}

	public static Mapx initList(Mapx params) {

		String codeType = params.getString("CodeType");
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType(codeType);
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}

	public void add() {

		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if (code.fill()) {
			Response.setLogInfo(0, "代码值" + code.getCodeValue() + "已经存在了!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "新建代码成功!");
		} else {
			Response.setLogInfo(0, "新建代码失败!");
		}
	}

	public void del() {

		DataTable dt = (DataTable) Request.get("DT");
		ZDCodeSet set = new ZDCodeSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(dt.getDataRow(i));
			code.fill();
			set.add(code);
			if ("System".equals(code.getParentCode())) {
				ZDCodeSchema childCode = new ZDCodeSchema();
				childCode.setParentCode(code.getCodeType());
				set.add(childCode.query());
			}
		}
		// 日志
		StringBuffer codeLog = new StringBuffer("删除代码:");
		if (set.size() > 0) {
			codeLog.append(set.get(0).getCodeName() + " 等");
		}
		if (set.deleteAndBackup()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "成功", Request.getClientIP());
			for (int i = 0; i < set.size(); i++) {
				CacheManager.remove("Code", set.get(i).getCodeType(), set.get(i).getCodeValue());
				if ("System".equals(set.get(i).getParentCode())) {
					CacheManager.removeType("Code", set.get(i).getCodeType());
				}
			}
			Response.setLogInfo(1, "删除代码成功!");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "失败", Request.getClientIP());
			Response.setLogInfo(0, "删除代码失败!");
		}
	}

	public void sortColumn() {

		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String parentCode = $V("ParentCode");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZDCode", "CodeOrder", type, target, orders, " ParentCode = '" + parentCode + "'")) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}

	/*
	 * 数据加载首页
	 */
	public static void dg1RiskTypeManage(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select *from zdcode where codetype = 'WelfareSet' and parentCode != 'System' order by addtime desc ");
		dga.bindData(qb);
	}

	public static Mapx initRiskTypeManageList(Mapx params) {

		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType("WelfareSet");
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}

	/*
	 * 新增、修改页面险种下拉菜单
	 */
	public static Mapx<String, String> initByLineTeamRiskType(Mapx<String, String> params) {

		params.put("Memo", HtmlUtil.codeToOptions("WXFuLi.CodeValue", true));
		return params;
	}

	/*
	 * 险种维护修改
	 */
	public void dg1update() {

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,Memo=?,Addtime=? "
						+ "where CodeType='WelfareSet' and ParentCode='WelfareSet' "
						+ "and CodeName=? and CodeValue=? and Memo=? ");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(Request.getString("Memo"));
		qb.add(new Date());
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		qb.add(Request.getString("MemoOld"));
		trans.add(qb); 
		
		if("1".equals(Request.getString("CodeValue"))){
			QueryBuilder qb2 = new QueryBuilder(
					"update sdpointrule set startdate=?,enddate=?,pointsNum=? "
					+ "where memberact = '27' and status = 'Y'");
			String[] date = Request.getString("Memo").split("-");
			
			Date startDate = DateUtil.parse(date[0], "yyyyMMdd");
			String strSDate = DateUtil.toString(startDate, "yyyy-MM-dd");
			Date endDate = DateUtil.parse(date[1], "yyyyMMdd");
			String strEDate = DateUtil.toString(endDate, "yyyy-MM-dd");
			
			qb2.add(strSDate);
			qb2.add(strEDate);
			qb2.add(Request.get("CodeName"));
			trans.add(qb2);
		}
		if (trans.commit()) {
			CacheManager.set("Code", "WelfareSet", Request.getString("CodeValue"), Request.getString("CodeName"));
			Response.setLogInfo(1, "修改成功!");
		} else {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(Request);
			if (code.fill()) {
				Response.setLogInfo(0, "编码值" + code.getCodeValue() + "已经存在了!");
				return;
			} else {
				Response.setLogInfo(0, "修改失败!");
			}
		}
	}

	public void Riskadd() {

		ZDCodeSchema code = new ZDCodeSchema();
		String codeValue = $V("CodeValue");
		String codeName = $V("CodeName");
		String memo = $V("Memo");
		code.setCodeType("WelfareSet");
		code.setParentCode("WelfareSet");
		code.setCodeOrder(System.currentTimeMillis());
		code.setCodeValue(codeValue);
		code.setCodeName(codeName);
		code.setMemo(memo);
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		Transaction transaction = new Transaction();
		transaction.add(code, Transaction.INSERT);

		Transaction trans = new Transaction();
		if("1".equals(Request.getString("CodeValue"))){
			QueryBuilder qb2 = new QueryBuilder(
					"update sdpointrule set startdate=?,enddate=?,pointsNum=? "
					+ "where memberact = '27' and status = 'Y'");
			String[] date = Request.getString("Memo").split("-");
			String startYear = date[0].substring(0, 4);
			String startMonth = date[0].substring(4, 6);
			String startDay = date[0].substring(6, 8);
			String endYear = date[1].substring(0, 4);
			String endMonth = date[1].substring(4, 6);
			String endDay = date[1].substring(6, 8);
			
			String startdate = startYear + "-" + startMonth + "-" + startDay;
			String enddate = endYear + "-" + endMonth + "-" + endDay;
			
			qb2.add(startdate);
			qb2.add(enddate);
			qb2.add(Request.get("CodeName"));
			trans.add(qb2);
		}
		
		if (transaction.commit() && trans.commit()) {
			Response.setLogInfo(1, "新建福利配置成功!");
		}else{
			Response.setLogInfo(0, "新建福利配置失败!");
		}
	}
}
