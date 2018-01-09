/**
 * Project Name:wj
 * File Name:GapGuarantee.java
 * Package Name:com.sinosoft.platform
 * Date:2017年4月19日下午2:03:21
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
import com.sinosoft.schema.ZDCodeSchema;

/**
 * ClassName:GapGuarantee <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2017年4月19日 下午2:03:21 <br/>
 *
 * @author:liuhongyu
 */
public class GapGuarantee extends Page {

	/*
	 * 十年存款能力比拼文案加载首页
	 */
	public static void dg1HomePage1(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select if(codename = '10','90万以上',"
						+ "if(codename = '1','小于10万',"
						+ "if(codename = '2','10万-20万',"
						+ "if(codename = '3','20万-30万',"
						+ "if(codename = '4','30万-40万',"
						+ "if(codename = '5','40万-50万',"
						+ "if(codename = '6','50万-60万',"
						+ "if(codename = '7','60万-70万',"
						+ "if(codename = '8','70万-80万','80万-90万'))))))))) as codename1,codename,CodeValue,"
						+ "substring_index(CodeValue, '||', 1)as CodeValuebefore,substring_index(CodeValue, '||', -1)as CodeValueafter "
						+ "from zdcode where codetype = 'GapGuaranteeCopywriting1' and parentCode != 'System' order by codename ");
		dga.bindData(qb);
	}
	/*
	 * 保险测评结果标题文案加载首页
	 */
	public static void dg2HomePage2(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select * from zdcode where codetype = 'GapGuaranteeCopywriting2' and parentCode != 'System' order by addtime desc ");
		dga.bindData(qb);
	}
	/*
	 * 缺口保障测评结果页标签文案加载首页
	 */
	public static void dg3HomePage3(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder("SELECT " +
				"	a.codevalue, " +
				"	b.CodeName codename1, " +
				"	a.codename, " +
				"IF (a.prop1 = 'M', '男', '女') AS prop11, " +
				" a.prop1, " +
				" c.CodeName prop21, " +
				" a.prop2 " +
				"FROM " +
				"	zdcode a, " +
				"	zdcode b, " +
				"	zdcode c " +
				"WHERE " +
				"	a.CodeType = 'GapGuaranteeCopywriting3' " +
				"AND b.CodeType = 'GapGuarantee.munu3' " +
				"AND c.CodeType = 'GapGuarantee.munu4' " +
				"AND a.CodeName = b.CodeValue " +
				"AND a.prop2 = c.CodeValue " +
				"ORDER BY " +
				"	a.addtime DESC");
		dga.bindData(qb);
	}
	/*
	 * 缺口保障测评结果页标签文案加载首页
	 */
	public static void dg4HomePage4(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select * from zdcode where codetype = 'GapGuaranteeCopywriting4' and parentCode != 'System' order by addtime desc ");
		dga.bindData(qb);
	}
	/*
	 * 各省统计数据加载首页
	 */
	public static void dg5HomePage5(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select * from zdcode where codetype = 'GapGuaranteeProvinceData' and parentCode != 'System' order by addtime desc ");
		dga.bindData(qb);
	}

	/*
	 * 新增、修改页面文案下拉菜单
	 */
	public static Mapx<String, String> dropdownMenu1(Mapx<String, String> params) {

		params.put("Memo", HtmlUtil.codeToOptions("GapGuarantee.munu1", true));
		return params;
	}
	/*
	 * 新增、修改页面文案下拉菜单
	 */
	public static Mapx<String, String> dropdownMenu2(Mapx<String, String> params) {

		params.put("Memo", HtmlUtil.codeToOptions("GapGuarantee.munu2", true));
		return params;
	}
	/*
	 * 新增、修改页面文案下拉菜单
	 */
	public static Mapx<String, String> dropdownMenu3(Mapx<String, String> params) {

		params.put("Memo3", HtmlUtil.codeToOptions("GapGuarantee.munu3", true));
		params.put("Gender", HtmlUtil.codeToOptions("Gender", true));
		params.put("Memo4", HtmlUtil.codeToOptions("GapGuarantee.munu4", true));
		return params;
	}
	/*
	 * 新增、修改页面文案下拉菜单
	 */
	public static Mapx<String, String> dropdownMenu4(Mapx<String, String> params) {

		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", true));
		return params;
	}
	/*
	 * 新增、修改页面文案下拉菜单
	 */
	public static Mapx<String, String> dropdownMenu5(Mapx<String, String> params) {

		params.put("Province", HtmlUtil.codeToOptions("GapGuaranteeProvinceData", true));
		return params;
	}

	public void add1() {

		ZDCodeSchema code = new ZDCodeSchema();
		String codeName = $V("CodeName");
		String CodeValuebefore = $V("CodeValuebefore");
		String CodeValueafter = $V("CodeValueafter");
		code.setCodeType("GapGuaranteeCopywriting1");
		code.setParentCode("GapGuaranteeCopywriting1");
		code.setCodeOrder(System.currentTimeMillis());
		code.setCodeValue(CodeValuebefore + "||" + CodeValueafter);
		code.setCodeName(codeName);
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		Transaction transaction = new Transaction();
		transaction.add(code, Transaction.INSERT);

		if (transaction.commit()) {
			Response.setLogInfo(1, "新建成功!");
		}else{
			Response.setLogInfo(0, "新建失败!");
		}
	}
	public void add2() {

		ZDCodeSchema code = new ZDCodeSchema();
		String codeValue = $V("CodeValue");
		String codeName = $V("CodeName");
		code.setCodeType("GapGuaranteeCopywriting2");
		code.setParentCode("GapGuaranteeCopywriting2");
		code.setCodeOrder(System.currentTimeMillis());
		code.setCodeValue(codeValue);
		code.setCodeName(codeName);
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		Transaction transaction = new Transaction();
		transaction.add(code, Transaction.INSERT);

		if (transaction.commit()) {
			Response.setLogInfo(1, "新建成功!");
		}else{
			Response.setLogInfo(0, "新建失败!");
		}
	}
	public void add3() {

		ZDCodeSchema code = new ZDCodeSchema();
		String codeValue = $V("CodeValue");
		String codeName = $V("CodeName");
		String prop1 = $V("prop1");
		String prop2 = $V("prop2");
		code.setCodeType("GapGuaranteeCopywriting3");
		code.setParentCode("GapGuaranteeCopywriting3");
		code.setCodeOrder(System.currentTimeMillis());
		code.setCodeValue(codeValue);
		code.setCodeName(codeName);
		code.setProp1(prop1);
		code.setProp2(prop2);
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		Transaction transaction = new Transaction();
		transaction.add(code, Transaction.INSERT);

		if (transaction.commit()) {
			Response.setLogInfo(1, "新建成功!");
		}else{
			Response.setLogInfo(0, "新建失败!");
		}
	}
	public void add4() {

		ZDCodeSchema code = new ZDCodeSchema();
		String codeValue = $V("CodeValue");
		String codeName = $V("CodeName");
		code.setCodeType("GapGuaranteeCopywriting4");
		code.setParentCode("GapGuaranteeCopywriting4");
		code.setCodeOrder(System.currentTimeMillis());
		code.setCodeValue(codeValue);
		code.setCodeName(codeName);
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		Transaction transaction = new Transaction();
		transaction.add(code, Transaction.INSERT);

		if (transaction.commit()) {
			Response.setLogInfo(1, "新建成功!");
		}else{
			Response.setLogInfo(0, "新建失败!");
		}
	}

	/*
	 * 文案维护修改
	 */
	public void update1() {

		Transaction trans = new Transaction();
		String CodeValuebefore = Request.getString("CodeValuebefore");
		String CodeValueafter = Request.getString("CodeValueafter");
		String CodeValuebeforeOld = Request.getString("CodeValuebeforeOld");
		String CodeValueafterOld = Request.getString("CodeValueafterOld");
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,Addtime=? "
						+ "where CodeType='GapGuaranteeCopywriting1' and ParentCode='GapGuaranteeCopywriting1' "
						+ "and CodeName=? and CodeValue=? ");
		qb.add(Request.getString("CodeName"));
		qb.add(CodeValuebefore + "||" + CodeValueafter);
		qb.add(new Date());
		qb.add(Request.getString("CodeNameOld"));
		qb.add(CodeValuebeforeOld + "||" + CodeValueafterOld);
		trans.add(qb);

		if (trans.commit()) {
			CacheManager.set("Code", "GapGuaranteeCopywriting1", Request.getString("CodeValue"), Request.getString("CodeName"));
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
	/*
	 * 文案维护修改
	 */
	public void update2() {

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,Addtime=? "
						+ "where CodeType='GapGuaranteeCopywriting2' and ParentCode='GapGuaranteeCopywriting2' "
						+ "and CodeName=? and CodeValue=? ");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(new Date());
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		trans.add(qb);

		if (trans.commit()) {
			CacheManager.set("Code", "GapGuaranteeCopywriting2", Request.getString("CodeValue"), Request.getString("CodeName"));
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
	/*
	 * 文案维护修改
	 */
	public void update3() {

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,prop1=?,prop2=?,Addtime=? "
						+ "where CodeType='GapGuaranteeCopywriting3' and ParentCode='GapGuaranteeCopywriting3' "
						+ "and CodeName=? and CodeValue=? and prop1 = ? and prop2 = ? ");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(Request.getString("prop1"));
		qb.add(Request.getString("prop2"));
		qb.add(new Date());
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		qb.add(Request.getString("prop1Old"));
		qb.add(Request.getString("prop2Old"));
		trans.add(qb);

		if (trans.commit()) {
			CacheManager.set("Code", "GapGuaranteeCopywriting3", Request.getString("CodeValue"), Request.getString("CodeName"));
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
	/*
	 * 文案维护修改
	 */
	public void update4() {

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,Addtime=? "
						+ "where CodeType='GapGuaranteeCopywriting4' and ParentCode='GapGuaranteeCopywriting4' "
						+ "and CodeName=? and CodeValue=? ");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(new Date());
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		trans.add(qb);

		if (trans.commit()) {
			CacheManager.set("Code", "GapGuaranteeCopywriting4", Request.getString("CodeValue"), Request.getString("CodeName"));
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
	/*
	 * 文案维护修改
	 */
	public void update5() {

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set Addtime=?,prop1=?,prop2=?,prop3=? "
						+ "where CodeType='GapGuaranteeProvinceData' and ParentCode='GapGuaranteeProvinceData' "
						+ "and CodeValue=? and prop1 = ? and prop2 = ? and prop3 = ? ");
		qb.add(new Date());
		qb.add(Request.getString("prop1"));
		qb.add(Request.getString("prop2"));
		qb.add(Request.getString("prop3"));
		qb.add(Request.getString("CodeValueOld"));
		qb.add(Request.getString("prop1Old"));
		qb.add(Request.getString("prop2Old"));
		qb.add(Request.getString("prop3Old"));
		trans.add(qb);

		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	/*
	 * 删除文案数据
	 */
	public void del1() {

		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='GapGuaranteeCopywriting1' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除文案成功!");
		} else {
			Response.setLogInfo(0, "删除文案失败!");
		}
	}
	/*
	 * 删除文案数据
	 */
	public void del2() {

		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='GapGuaranteeCopywriting2' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除文案成功!");
		} else {
			Response.setLogInfo(0, "删除文案失败!");
		}
	}
	/*
	 * 删除文案数据
	 */
	public void del3() {

		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='GapGuaranteeCopywriting3' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除文案成功!");
		} else {
			Response.setLogInfo(0, "删除文案失败!");
		}
	}
	/*
	 * 删除文案数据
	 */
	public void del4() {

		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='GapGuaranteeCopywriting4' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除文案成功!");
		} else {
			Response.setLogInfo(0, "删除文案失败!");
		}
	}

	public static Mapx<String, String> init(Mapx<String, String> params) {
		String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
		params.put("yesterday", yesterday);
		params.put("today", today);
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParams().getString("startDate");
		String stopDate = dga.getParams().getString("stopDate");
		String sql = "";
		// （0：“未使用”1：“已使用”2：“已发放”3：“已撤单”4：“冻结”5：“过期”6：“已删除”）
		sql = "select if(a.type = 'A','发放优惠券','未知') as type ,a.mobileNo,a.couponSn,a.createdate,"
		+"IF(b.status = 0,'未使用',IF(b.status = 1,'已使用',IF(b.status = 2,'已发放',IF(b.status = 3,'已撤单',IF(b.status = 4,'冻结',IF(b.status = 5,'过期','已删除')))))) as status from wxsecurityData a,couponinfo b where type = 'A' and a.couponSn = b.couponSn ";
		if (StringUtil.isNotEmpty(startDate)) {
			sql = sql + " and DATE(a.createdate) >='" + startDate + "' ";
		}
		if (StringUtil.isNotEmpty(stopDate)) {
			sql = sql + " and DATE(a.createdate) <='" + stopDate + "' ";
		}
		sql = sql + "ORDER BY a.createdate desc";
		QueryBuilder qb = new QueryBuilder(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public void orderCountForNewRegist(){
		String startDate = $V("startDate");
		String stopDate = $V("stopDate");
		String sql = "";
		sql = "select count(*) as count from member where fromwap = 'wap_Security' ";
		if (StringUtil.isNotEmpty(startDate)) {
			sql = sql + " and DATE(createdate) >='" + startDate + "' ";
		}
		if (StringUtil.isNotEmpty(stopDate)) {
			sql = sql + " and DATE(createdate) <='" + stopDate + "' ";
		}
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Response.setMessage(dt.getString(0, 0));
	}

	public void orderCountForReceiveCoupon(){
		String startDate = $V("startDate");
		String stopDate = $V("stopDate");
		String sql = "";
		sql = "SELECT COUNT(*) FROM ( SELECT b.* FROM wxsecuritydata a, couponinfo b WHERE type = 'A' AND a.couponSn = b.couponSn ";
		if (StringUtil.isNotEmpty(startDate)) {
			sql = sql + " and DATE(a.createdate) >='" + startDate + "' ";
		}
		if (StringUtil.isNotEmpty(stopDate)) {
			sql = sql + " and DATE(a.createdate) <='" + stopDate + "' ";
		}
		sql = sql + "GROUP BY a.mobileNo ";
		sql = sql + ") as count";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Response.setMessage(dt.getString(0, 0));
	}

	public void orderCountForUseCoupon(){
		String startDate = $V("startDate");
		String stopDate = $V("stopDate");
		String sql = "";
		sql = "SELECT COUNT(*) FROM ("
				+ "select "
				+ "b.* "
				+ "from wxsecurityData a,couponinfo b "
				+ "where a.type = 'A' "
				+ "and a.couponSn = b.couponSn "
				+ "and b.status = '1' ";
		if (StringUtil.isNotEmpty(startDate)) {
			sql = sql + " and DATE(a.createdate) >='" + startDate + "' ";
		}
		if (StringUtil.isNotEmpty(stopDate)) {
			sql = sql + " and DATE(a.createdate) <='" + stopDate + "' ";
		}

		sql = sql + "GROUP BY a.mobileNo ";
		sql = sql + ") as A";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Response.setMessage(dt.getString(0, 0));
	}

}

