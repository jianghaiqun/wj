package com.wangjin.infoseeker;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderInfo extends Page {

	private  String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private  String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	private  String tminutes = " 23:59:59";

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initStaff(Mapx params) {
		String today_S = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String yesterdays_S = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("Markingkg", HtmlUtil.mapxToOptions(getMarkingkgb(), null, true));// 类别
		map.put(" ", HtmlUtil.mapxToOptions(getSdChannel(), null, true));// 渠道
		map.put("yesterday", yesterdays_S);
		map.put("today", today_S);
		map.put("contant", HtmlUtil.codeToOptions("Sales.channel",false));

		String sql = "SELECT count(*) FROM ZDUserRole WHERE username = ? AND rolecode = 'orderinfo'";
		int count =  new QueryBuilder(sql, User.getUserName()).executeInt();
		map.put("WjRoleFlag", count);

		// 开心保所有子渠道
		sql = "SELECT GROUP_CONCAT(channelcode) FROM channelinfo WHERE ParentInnerChanelCode = '00001' GROUP BY ParentInnerChanelCode";
		String channelCode = new QueryBuilder(sql).executeString();
		map.put("channelCode", channelCode);
		return map;
	}

	/**
	 * 获得发布统计的数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String Markingkg = dga.getParam("Markingkg");
		String channelsn = dga.getParam("contant");
		String sdChannel = dga.getParam("sdChannel");
		
		String channelsnb2b = channelsn;
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}
		
		// 撤单标识  1:撤单
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		StringBuffer sql = new StringBuffer();
		/*if("b2b".equals(channelsn)){
			queryB2BData(dga);
			return;
		}*/
		DataTable b2bDT = queryB2BData(dga,channelsnb2b);
		if (!"1".equals(allDate)) {
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " and tf.receiveDate >= '" + startDate + "'";
			} else {
				parameter = " and tf.receiveDate >= '" + yesterday +"'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " and tf.receiveDate <='" + endDate +  tminutes+"'";
			} else {
				parameter = parameter + " and tf.receiveDate <='" + today + tminutes+"'";
			}
		
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			if("DL".equals(Markingkg) || "DM".equals(Markingkg)){
				parameter = parameter +" and e.ProductType='D'";
			}else{
				parameter = parameter +" and e.ProductType='" + Markingkg + "'";
			}
			
			parameter = parameter +" and e.ProductGenera is not null";
		}
		if("tbsd".equals(channelsn) && StringUtil.isNotEmpty(sdChannel)){//选择淘宝刷单渠道该条件才生效
			if("tbsd".equals(sdChannel)){//淘宝刷单
				parameter = parameter +" and a.ordersn like 'TB%' and a.ordersn not like 'TBMY%'";
			}else if("mysd".equals(sdChannel)){//蚂蚁刷单
				parameter = parameter +" and a.ordersn like 'TBMY%'";
			}
		}
		
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT  a.channelsn AS ChannelSn,sdor.policyNo,b.ordersn AS ordersn ,b.productName AS productName, ");
			sql.append(" FORMAT(sdor.timeprem,2) AS totalAmount, FORMAT((sdor.timeprem*e.feerate),2) AS charge,tf.receiveDate AS modifyDate,e.productGenera AS ProductGenera,e.insurename AS insureName,");
			sql.append(" DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,sdor.cancelDate AS cancelDate,insured.recognizeeIdentityId ");
			sql.append(" ,e.productid FROM sdinformationrisktype sdor,sdorders a,sdinformation b,sdproduct e,SDOrderItem f");
			sql.append(" LEFT JOIN tradeinformation tf ON tf.ordid=f.ordersn ,sdinformationinsured insured");
			sql.append(" WHERE sdor.ordersn=a.ordersn AND a.paySn NOT LIKE 'BG%' AND a.ordersn = b.ordersn AND b.productId = e.productId AND a.orderStatus >=7 AND a.orderStatus<>'8'   AND f.ordersn=b.ordersn  ");
			sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) " );
			sql.append(" and sdor.recognizeeSn = insured.recognizeeSn " );
			sql.append(parameter);
			sql.append(" ORDER BY tf.receiveDate desc ");
		} else if("1".equals(cancelFlag)){
			sql.append(" SELECT  ChannelSn,policyNo,ordersn,productName,cancelDate,FORMAT(-totalAmount,2) AS totalAmount,FORMAT(-charge,2) AS charge,modifyDate,ProductGenera,insureName,startDate,recognizeeIdentityId,productid FROM");
			sql.append(" (SELECT  a.channelsn AS ChannelSn,g.policyNo,b.ordersn AS ordersn ,b.productName AS productName, g.cancelDate,timePrem AS totalAmount,(timePrem * e.feerate) AS charge, tf.receiveDate AS modifyDate,e.productGenera AS ProductGenera,");
			sql.append(" e.insurename AS insureName, DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,insured.recognizeeIdentityId,e.productid FROM sdorders a,tradeinformation tf,sdinformation b, sdproduct e,SDOrderItem f,sdinformationrisktype g,sdinformationinsured insured");
			sql.append(" WHERE a.orderSn = tf.ordid AND a.ordersn = b.ordersn  AND a.paySn NOT LIKE 'BG%'  AND (g.changeStatus IS NULL OR g.changeStatus='')");
			sql.append(" AND b.productId = e.productId AND f.ordersn=b.ordersn AND f.orderSn = g.orderSn AND g.appstatus>=2 AND (f.channelCode != '02' OR f.channelCode IS NULL)  ");
			sql.append(" and g.recognizeeSn = insured.recognizeeSn ");
			sql.append(parameter);
			sql.append(" UNION ALL ");
			sql.append(" SELECT  a.channelsn AS ChannelSn,g.policyNo,b.ordersn AS ordersn ,b.productName AS productName, g.cancelDate,timePrem AS totalAmount,");
			sql.append(" (timePrem * e.feerate) AS charge, tf.receiveDate AS modifyDate,e.productGenera AS ProductGenera,e.insurename AS insureName, DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,insured.recognizeeIdentityId");
			sql.append(" ,e.productid FROM sdorders a,tradeinformation tf,sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,");
			sql.append(" sdproduct e,SDOrderItem f,sdinformationrisktype g,sdinformationinsured insured  WHERE a.orderSn = tf.ordid AND a.paySn LIKE 'BG%'");
			sql.append(" AND a.ordersn = b.ordersn  AND b.productId = e.productId AND f.ordersn=b.ordersn AND f.orderSn = g.orderSn AND g.appstatus >=2");
			sql.append(" AND p.os= b.orderSn AND (f.channelCode != '02' OR f.channelCode IS NULL)  ");
			sql.append(" and g.recognizeeSn = insured.recognizeeSn ");
			sql.append(parameter);
			sql.append(" ) m ORDER BY modifyDate desc ");
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT  a.channelsn AS ChannelSn,sdor.policyNo,a.ordersn,b.productName,sdor.cancelDate,FORMAT(sdor.timePrem,2) AS totalAmount,");
			sql.append(" FORMAT((timePrem * e.feerate),2) AS charge,tf.receiveDate AS modifyDate,e.productGenera AS ProductGenera,e.insurename AS insureName,");
			sql.append(" DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,insured.recognizeeIdentityId");
			sql.append(" ,e.productid FROM  sdinformation b, sdproduct e,sdinformationrisktype sdor");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid ,sdinformationinsured insured ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=e.ProductID ");
			sql.append(" AND a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='') ");
			sql.append(" and sdor.recognizeeSn = insured.recognizeeSn  ");
			sql.append(parameter);
			sql.append("  ORDER BY modifyDate desc ");
		}

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		//IPSeeker ipseeker = new IPSeeker();
		//IPLocation iplocation = new IPLocation();
		dt.insertColumn("KID");
		for (DataRow dr : dt) {
			/*String ip = null;
			if (null != dr.get("isMember")) {
				dr.set("isMember", "是");
			} else {
				dr.set("isMember", "否");
			}
			iplocation = null;
			if (null != dr.get("orderIP") && IsIPv4Address(dr.get("orderIP").toString())) {
				ip = dr.get("orderIP").toString();
				iplocation = ipseeker.getIPLocation(dr.get("orderIP").toString());
				dr.set("orderIP", iplocation.getCountry() + ":" + iplocation.getArea());
			}
			if (null != dr.get("alipayIP") && IsIPv4Address(dr.get("alipayIP").toString())) {
					iplocation = ipseeker.getIPLocation(dr.get("alipayIP").toString());
				dr.set("alipayIP", iplocation.getCountry() + ":" + iplocation.getArea());
			}*/
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			dr.set("KID",
					StringUtil.md5Hex(PubFun.getKeyValue() + dr.get("ordersn").toString()));
		}
		//ipseeker.closeFile();
		if(b2bDT!=null && b2bDT.getRowCount()>=1){
			dt.union(b2bDT);
		}
		int a = dt.getColCount();
		
		DataTable dtfmrisk = new QueryBuilder("SELECT riskcode,riskPeriod,CASE WHEN riskPeriod='L' THEN '长期' ELSE '短期' END AS riskPeriodValue  FROM fmrisk").executeDataTable();
		Map dtfmriskmap = dtfmrisk.toMapx("riskcode", "riskPeriodValue");
		
		if ((StringUtil.isNotEmpty(Markingkg)) && ("DL".equals(Markingkg) || "DM".equals(Markingkg))) {
			
			for (int i = dt.getRowCount() - 1; i >= 0; i--) {
				String productid = (String)dt.get(i, "productid");
				
				if("DL".equals(Markingkg) && !"长期".equals(dtfmriskmap.get(productid).toString())){
						dt.deleteRow(i);
						continue;	
				}else if("DM".equals(Markingkg) && !"短期".equals(dtfmriskmap.get(productid).toString())){
						dt.deleteRow(i);
						continue;	
				}
				
				String str = (String)dt.get(i, 11);
				if (StringUtil.isEmpty(str)) {
				}else if (str.length() >=6) {
					String mStr = str.substring(0, str.length() - 6) + "******";
					dt.set(i, 11,mStr);
				}else if(str.length() >=3 && str.length() <6){
					String mStr = str.substring(0, str.length() - 3) + "***";
					dt.set(i, 11,mStr);
				}else{
					
				}
				// 将健康险拆分为 长期、短期
				String str1 = (String)dt.get(i, "ProductGenera");
				
				if(StringUtil.isNotEmpty(str1) && "健康险".equals(str1)){
					dt.set(i, "ProductGenera",dtfmriskmap.get(productid).toString()+str1);
				}
				
			}
		}else{
			for (int i = 0; i < dt.getRowCount(); i++) {
				String str = (String)dt.get(i, 11);
				if (StringUtil.isEmpty(str)) {
				}else if (str.length() >=6) {
					String mStr = str.substring(0, str.length() - 6) + "******";
					dt.set(i, 11,mStr);
				}else if(str.length() >=3 && str.length() <6){
					String mStr = str.substring(0, str.length() - 3) + "***";
					dt.set(i, 11,mStr);
				}else{
					
				}
				// 将健康险拆分为 长期、短期
				String str1 = (String)dt.get(i, "ProductGenera");
				String productid = (String)dt.get(i, "productid");
				if(StringUtil.isNotEmpty(str1) && "健康险".equals(str1)){
					dt.set(i, "ProductGenera",dtfmriskmap.get(productid).toString()+str1);
				}
				
			}
		}
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: queryB2BData 
	* @Description: TODO(统计B2B订单数据) 
	* @return void    返回类型 
	* @author XXX
	 */
	public DataTable queryB2BData(DataGridAction dga,String channelSn){

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String Markingkg = dga.getParam("Markingkg");
		String channelsn = dga.getParam("contant");
		//if("all".equals(channelsn)) channelsn="";
		// 撤单标识  1:撤单  
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		if (!"1".equals(allDate)) {
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " and tf.receiveDate >= '" + startDate + "'";
			} else {
				parameter = " and tf.receiveDate >= '" + yesterday +"'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " and tf.receiveDate <='" + endDate +  tminutes+"'";
			} else {
				parameter = parameter + " and tf.receiveDate <='" + today + tminutes+"'";
			}
		
		}
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			parameter = parameter + " and s.ChannelSn in ("+channel+")";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			if("DL".equals(Markingkg) || "DM".equals(Markingkg)){
				parameter = parameter + " and e.EdorFlag like 'D%'";
			}else{
				parameter = parameter + " and e.EdorFlag like '" + Markingkg + "%'";
			}
			
			parameter = parameter + " and e.EdorFlag is not null";
		}
		
		StringBuffer sql = new StringBuffer();
		
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT s.ChannelSn AS ChannelSn, re.policyNo,s.id AS orderSn,s.productname AS productName,FORMAT(om.PaymentFee,2) AS totalAmount, FORMAT((om.PaymentFee*e.rate),2) AS charge,");
			sql.append(" tf.receiveDate AS modifyDate,");
			sql.append(" (SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,e.InsuranceCompany as insureName,DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, re.cancelDate cancelDate,insured.recognizeeIdentityId ");
			sql.append(" ,e.riskcode as productid FROM sdorders s,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om,sdinsured insured ");
			sql.append(" WHERE s.orderstatus IN ('2','9','10','13','14') AND s.id = tf.ordID AND re.orderSn = s.id  AND s.productid = e.RiskCode AND om.prop1 = re.id AND s.prop2 NOT LIKE 'BG%' and re.insuredSn=insured.InsuredId ");
			sql.append(parameter);
			sql.append(" order by modifyDate desc ");
		} else if("1".equals(cancelFlag)) {
			sql.append(" SELECT ChannelSn,policyNo,orderSn,productName,cancelDate,FORMAT(SUM(totalAmount),2) AS totalAmount,FORMAT(SUM(charge),2) AS charge,modifyDate,ProductGenera,insureName,startDate,recognizeeIdentityId,productid FROM (");
			sql.append(" SELECT s.ChannelSn AS ChannelSn,re.policyNo,s.id AS orderSn,s.productname AS productName,-om.PaymentFee AS totalAmount,FORMAT((-om.PaymentFee*e.rate),2) AS charge, tf.receiveDate AS modifyDate,");
			sql.append(" (SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,e.InsuranceCompany as insureName, ");
			sql.append(" DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, re.cancelDate cancelDate,insured.recognizeeIdentityId ");
			sql.append(" ,e.riskcode as productid FROM sdorders s,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om,sdinsured insured ");
			sql.append(" WHERE s.id = tf.ordID AND re.orderSn = s.id AND s.productid = e.RiskCode AND om.prop1 = re.id AND re.appStatus>=2 ");
			sql.append(" AND s.prop2 NOT LIKE 'BG%'  AND (re.changeStatus IS NULL OR re.changeStatus='') and re.insuredSn=insured.InsuredId "+parameter);
			sql.append(" UNION ");
			sql.append(" SELECT s.ChannelSn AS ChannelSn,re.policyNo,s.id AS orderSn,s.productname AS productName,-om.PaymentFee AS totalAmount,FORMAT((-om.PaymentFee*e.rate),2) AS charge, tf.receiveDate AS modifyDate,");
			sql.append(" (SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,e.InsuranceCompany as insureName, ");
			sql.append(" DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, re.cancelDate cancelDate,insured.recognizeeIdentityId ");
			sql.append(" ,e.riskcode as productid FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om,sdinsured insured");
			sql.append(" WHERE s.id = tf.ordID AND re.orderSn = s.id AND p.os= s.id AND s.productid = e.RiskCode AND om.prop1 = re.id AND re.appStatus>=2 and re.insuredSn=insured.InsuredId ");
			sql.append(" AND s.prop2 LIKE 'BG%' "+parameter+" ) m GROUP BY policyNo");
			sql.append(" order by modifyDate desc ");
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT s.ChannelSn AS ChannelSn,re.policyNo,s.id AS orderSn,s.productname AS productName,re.cancelDate cancelDate,FORMAT(om.PaymentFee,2) AS totalAmount, FORMAT((om.PaymentFee*e.rate),2) AS charge,");
			sql.append(" tf.receiveDate AS modifyDate,(SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,e.InsuranceCompany AS insureName,DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate,insured.recognizeeIdentityId ");
			sql.append(" ,e.riskcode as productid FROM sdorders s,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om,sdinsured insured WHERE s.id = tf.ordID AND re.orderSn = s.id");
			sql.append(" AND s.productid = e.RiskCode AND om.prop1 = re.id AND s.orderstatus IN ('2','9','10','13','14')");
			sql.append(" AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='') and re.insuredSn=insured.InsuredId");
			sql.append(parameter);
			sql.append(" order by modifyDate desc ");
		}

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable("B2B");
		dt.insertColumn("KID");
		for (DataRow dr : dt) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			dr.set("KID",
					StringUtil.md5Hex(PubFun.getKeyValue() + dr.get("ordersn").toString()));
		}
		//dga.bindData(dt);
		
	   return dt;
		
	}
	/**
	 * 获得淘宝订单统计的数据
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String Markingkg = dga.getParam("Markingkg");
		String parameter = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select  b.ordersn as ordersn ,b.productName as productName,c.applicantName as applicantName,");
		sql.append("(select count(1) from sdinformationinsured si where si.ordersn=b.ordersn ) as pocount,a.totalAmount as totalAmount,");
		sql.append("FORMAT((a.totalAmount*e.feerate),2) as charge,a.modifyDate as modifyDate,e.productGenera as ProductGenera,e.insurename as insureName,");
		sql.append("a.memberId as isMember ");
		sql.append("from sdorders a, sdinformation b, SDInformationAppnt c, sdproduct e ");
		sql.append("where a.ordersn = b.ordersn and b.informationsn = c.informationsn and b.productId = e.productId and a.orderStatus='7'");
		sql.append(" and a.channelsn='tb'  " );
		if (!"1".equals(allDate)) {
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " and DATE_FORMAT(a.modifyDate,'%Y-%m-%d')>='" + startDate + "'";
			} else {
				parameter = " and DATE_FORMAT(a.modifyDate,'%Y-%m-%d')>='" + yesterday + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " and DATE_FORMAT(a.modifyDate,'%Y-%m-%d')<='" + endDate + "'";
			} else {
				parameter = parameter + " and DATE_FORMAT(a.modifyDate,'%Y-%m-%d')<='" + today + "'";
			}
			sql.append(parameter);
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			sql.append(" and e.ProductType='" + Markingkg + "'");
			sql.append(" and e.ProductGenera is not null");
		}
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		dt.insertColumn("KID");
		for (DataRow dr : dt) {
			if (null != dr.get("isMember")) {
				dr.set("isMember", "是");
			} else {
				dr.set("isMember", "否");
			}
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			dr.set("KID",
					StringUtil.md5Hex(PubFun.getKeyValue() + dr.get("ordersn").toString()));
		}
		dga.bindData(dt);
	}
	/*
	 * 获取保险种类的下拉菜单
	 */
	private static Map getMarkingkg() {
		String sql = "select substring(CodeValue,1,1) as CodeValue, CodeName as CodeName from zdcode where CodeType='ProductType' and ParentCode='ProductType' and CodeValue like '%00' order by CodeValue";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Mapx map = new Mapx();
		for (DataRow dr : dt) {
			map.put(dr.get("CodeValue").toString(), dr.get("CodeName").toString());
		}
		return map;
	}
	
	/*
	 * 获取保险种类的下拉菜单 健康险拆分 长期/短期
	 */
	private static Map getMarkingkgb() {
		String sql = "select substring(CodeValue,1,1) as CodeValue, CodeName as CodeName from zdcode where CodeType='ProductType' and ParentCode='ProductType' and CodeValue like '%00' order by CodeValue";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Mapx map = new Mapx();
		for (DataRow dr : dt) {
			if("D".equals(dr.get("CodeValue").toString())){
				map.put("DL", "长期健康险");
				map.put("DM", "短期健康险");
			}else{
				map.put(dr.get("CodeValue").toString(), dr.get("CodeName").toString());
			}
		}
		return map;
	}

	/*
	 * 获取淘宝刷单渠道
	 */
	private static Map getSdChannel() {
		String sql = "select CodeValue as CodeValue, CodeName as CodeName from zdcode where CodeType='tb.tbsd' and ParentCode='tb.tbsd' order by CodeValue";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		Mapx map = new Mapx();
		for (DataRow dr : dt) {
			map.put(dr.get("CodeValue").toString(), dr.get("CodeName").toString());
		}
		return map;
	}
	private static boolean IsIPv4Address(String ip) {
		String patternstr = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
		Pattern pattern = Pattern.compile(patternstr);
		Matcher matcher = pattern.matcher(ip);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	

}
