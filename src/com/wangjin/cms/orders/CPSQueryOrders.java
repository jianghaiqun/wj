package com.wangjin.cms.orders;

import cn.com.sinosoft.util.ArithUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDUserRoleSchema;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CPSQueryOrders extends Page {
	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	private static String tminutes = " 23:59:59";
	
	// 得到订单信息
	public void orderInquery(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String Markingkg = dga.getParam("Markingkg");
		String channel = dga.getParam("channel");
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and a.createdate >= '" + startDate + "'";
			parameter2 += " and c.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and a.createdate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and c.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and SUBSTRING(d.EdorFlag, 1, 1)='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";

			parameter2 += " and f.realname like '%" + IntermediaryMember + "%'";
		}
		String b2b_base = Config.getValue("B2BDataBase");
		sql.append(" SELECT ordersn,policyno,productname,totalamount,charge,paydate,svaliDate,ProductGenera,InsureName,channelSn,IntermediaryMember FROM (");
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT a.ordersn,b.policyno,c.productname,b.timePrem as totalamount, ");
			sql.append(" FORMAT((b.timePrem*(SELECT a2.feerate FROM sdproduct a2 WHERE c.productid=a2.productid)),2) 'charge',");
			sql.append(" DATE_FORMAT(d.receiveDate,'%Y-%m-%d %H:%i:%s') 'paydate',b.svaliDate,e.ProductGenera,e.InsureName, ");
			sql.append(" '网站联盟' AS channelSn,g.IntermediaryMember");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap','cps_drlm_wap')" + parameter1);
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT a.id AS ordersn,b.policyno,a.productname,g.prop2 as totalamount,FORMAT((g.prop2*(SELECT a2.rate FROM "
					+ b2b_base + ".sdproduct a2 WHERE a.productid=a2.id)),2) 'charge',");
			sql.append(" DATE_FORMAT(c.receiveDate,'%Y-%m-%d %H:%i:%s') 'paydate',a.Effective AS svaliDate,");
			sql.append(" (SELECT CodeName FROM "
					+ b2b_base
					+ ".ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(d.EdorFlag, 1, 1)) AS ProductGenera,");
			sql.append(" a.InsuranceCompany AS InsureName,'美行保' AS channelSn,f.realname AS IntermediaryMember");
			sql.append(" FROM " + b2b_base + ".sdorders a LEFT JOIN " + b2b_base
					+ ".zduser e ON a.MemberId = e.username LEFT JOIN " + b2b_base + ".zduser f ON f.username = e.prop9,"
					+ b2b_base + ".sdinsuredcompanyreturndata b," + b2b_base + ".sdtradeinformation c,");
			sql.append(" "
					+ b2b_base
					+ ".sdproduct d,"
					+ b2b_base
					+ ".sdorderitem g WHERE a.id = b.ordersn and b.insuredSn = g.InsuredId AND a.id=c.ordid AND a.ProductId = d.Id AND b.appStatus = '1' "
					+ parameter2);
		}

		sql.append(") m ORDER BY paydate DESC ");
		logger.info("sql_CPSOrderInfo==={}", sql.toString());
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;
		if (channel.indexOf("b2b") != -1) {
			dt = qb.executePagedDataTable("B2B", dga.getPageSize(), dga.getPageIndex());
			dga.setTotal("B2B", qb);
		}
		else {
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dga.setTotal(qb);
		}

		dga.bindData(dt);

	}
	
	
	// 得到订单信息
	public void orderInqueryNew(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String Markingkg = dga.getParam("Markingkg");
		String channel = dga.getParam("channel");
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and a.createdate >= '" + startDate + "'";
			parameter2 += " and tf.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and a.createdate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and tf.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and e.ProductType='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";

			String sqluser = "SELECT e.UserName FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username WHERE  f.RealName LIKE '%" + IntermediaryMember + "%' ";
			QueryBuilder queryBuilder = new QueryBuilder(sqluser);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			String sql_provideuser = "";
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				sql_provideuser += "'" + dataTable.getString(i, 0).toString() + "',";
			}
			if(!StringUtil.isEmpty(sql_provideuser)){
				sql_provideuser = sql_provideuser.substring(0, sql_provideuser.length() - 1);
				parameter2 += " and a.MemberId in (" + sql_provideuser + ")";
			}else{
				parameter2 += " and a.MemberId in ('')";
			}
				
		}
		
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT ordersn,policyno,productname,totalamount,charge,paydate,svaliDate,ProductGenera,InsureName,channelSn,IntermediaryMember FROM (");
			sql.append(" SELECT a.ordersn,b.policyno,c.productname,b.timePrem as totalamount, ");
			sql.append(" FORMAT((b.timePrem*(SELECT a2.feerate FROM sdproduct a2 WHERE c.productid=a2.productid)),2) 'charge',");
			sql.append(" DATE_FORMAT(d.receiveDate,'%Y-%m-%d %H:%i:%s') 'paydate',b.svaliDate,e.ProductGenera,e.InsureName, ");
			sql.append(" '网站联盟' AS channelSn,g.IntermediaryMember");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap','cps_drlm_wap')" + parameter1);
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT MemberId,ordersn,policyno,productname,totalamount,charge,paydate,svaliDate,ProductGenera,InsureName,channelSn,IntermediaryMember FROM (");
			sql.append("SELECT a.MemberId,b.ordersn AS ordersn, sdor.policyNo, b.productName AS productName, ROUND(sdor.timeprem, 2) AS totalAmount,"
					 + " b.productId AS charge,tf.receiveDate AS paydate,"
					 + " DATE_FORMAT(b.startDate, '%Y-%m-%d') AS svaliDate,'' AS ProductGenera,"
					 + " '' AS insureName,'美行保' AS ChannelSn,'' AS IntermediaryMember "
					 + " FROM sdinformationrisktype sdor,sdorders a,sdinformation b,sdproduct e,SDOrderItem f"
					 + " LEFT JOIN tradeinformation tf ON tf.ordid = f.ordersn "
					 + " WHERE sdor.ordersn = a.ordersn "
					 + " AND a.ordersn = b.ordersn "
					 + " AND sdor.appStatus='1' "
					 + " AND f.ordersn = b.ordersn "
					 + " AND b.productId = e.productId "
					 + " AND a.channelsn in ('b2b_ht','b2c_pc','b2c_wap') "
					 + parameter2);
		}

		sql.append(") m ORDER BY paydate DESC ");
		logger.info("sql_CPSOrderInfo==={}", sql.toString());
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;
		dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		if(!StringUtil.isEmpty(dt)&&dt.getRowCount()>0&&channel.indexOf("b2b") != -1){
			String sqlproduct = "SELECT riskcode,CONCAT(IFNULL(rate,''),',',IFNULL(InsuranceCompany,''),',',(SELECT IFNULL(CodeName,'') FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1))) AS vvalue FROM sdproduct";
			if (StringUtil.isNotEmpty(Markingkg)) {
				sqlproduct = sqlproduct + " where SUBSTRING(EdorFlag, 1, 1)='" + Markingkg + "'";
			}
			QueryBuilder queryBuilder = new QueryBuilder(sqlproduct);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			Mapx toMapx = dataTable.toMapx("riskcode", "vvalue");
			
			String sqluser = "SELECT e.UserName AS UserName ,f.RealName AS RealName FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username  ";
			if (StringUtil.isNotEmpty(IntermediaryMember)) {
				sqluser = sqluser + " where f.RealName like '%" + IntermediaryMember + "%' ";
			}
			QueryBuilder queryBuilderuser = new QueryBuilder(sqluser);
			DataTable dataTableuser = queryBuilderuser.executeDataTable("B2B");
			Mapx toMapxuser = dataTableuser.toMapx("UserName", "RealName");
			
			String vvalue = "";
			String charge = "";
			String ProductGenera = "";
			String InsureName = "";
			String IntermediaryMemberu = "";
			for(int i=0;i<dt.getRowCount();i++){
				vvalue = "";
				charge = "";
				ProductGenera = "";
				InsureName = "";
				// 根据productid 获取sdproduct表中费率字段计算
				if(!StringUtil.isEmpty(dt.getString(i,"charge"))){
					vvalue = toMapx.get(dt.getString(i,"charge")).toString();
					String[] vvalues = vvalue.split(",",-1);
					if(!StringUtil.isEmpty(vvalues[0])){
						double totalamount = Double.valueOf(dt.getString(i,"totalamount"));
						double feeRate = Double.valueOf(vvalues[0]);
						charge = new DecimalFormat("0.00").format(ArithUtil.mul(totalamount, feeRate));
					}
					if(!StringUtil.isEmpty(vvalues[1])){
						InsureName = vvalues[1];
					}
					if(!StringUtil.isEmpty(vvalues[2])){
						ProductGenera = vvalues[2];
					}
				}
				
				if(!StringUtil.isEmpty(dt.getString(i,"MemberId"))){
					vvalue="";
					if(!StringUtil.isEmpty(toMapxuser.get(dt.getString(i,"MemberId")))){
						vvalue = toMapxuser.get(dt.getString(i,"MemberId")).toString();
					}
					IntermediaryMemberu = vvalue;
				}

				dt.set(i, "charge", charge);
				dt.set(i, "InsureName", InsureName);
				dt.set(i, "ProductGenera", ProductGenera);
				dt.set(i, "IntermediaryMember", IntermediaryMemberu);
			}
		}
		dga.bindData(dt);
	}


	public void orderCount() {

		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String Markingkg = $V("Markingkg");
		String channel = $V("channel");
		String IntermediaryMember = $V("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and a.createdate >= '" + startDate + "'";
			parameter2 += " and c.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and a.createdate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and c.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and SUBSTRING(d.EdorFlag, 1, 1)='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";

			parameter2 += " and f.realname like '%" + IntermediaryMember + "%'";
		}
		String b2b_base = Config.getValue("B2BDataBase");
		sql.append(" SELECT FORMAT(sum(totalamount),2) as totalamount,FORMAT(sum(charge),2) as charge FROM (");
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT b.timePrem as totalamount, ");
			sql.append(" FORMAT((b.timePrem*(SELECT a2.feerate FROM sdproduct a2 WHERE c.productid=a2.productid)),2) 'charge'");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap','cps_drlm_wap')" + parameter1);
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT g.prop2 as totalamount,FORMAT((g.prop2*(SELECT a2.rate FROM " + b2b_base
					+ ".sdproduct a2 WHERE a.productid=a2.id)),2) 'charge'");
			sql.append(" FROM " + b2b_base + ".sdorders a LEFT JOIN " + b2b_base
					+ ".zduser e ON a.MemberId = e.username LEFT JOIN " + b2b_base + ".zduser f ON f.username = e.prop9,"
					+ b2b_base + ".sdinsuredcompanyreturndata b," + b2b_base + ".sdtradeinformation c,");
			sql.append(" "
					+ b2b_base
					+ ".sdproduct d,"
					+ b2b_base
					+ ".sdorderitem g WHERE a.id = b.ordersn and g.InsuredId = b.insuredSn AND a.id=c.ordid AND a.ProductId = d.Id AND b.appStatus = '1' "
					+ parameter2);
		}

		sql.append(") m ");
		logger.info("sql_CPSOrderCount==={}", sql.toString());
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;

		if (channel.indexOf("b2b") != -1)
			dt = qb.executeDataTable("B2B");
		else
			dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			Response.setMessage(dt.getString(0, 0) + "&" + dt.getString(0, 1));
		} else {
			Response.setMessage("0.00&0.00");
		}

	}
	
	

	public void orderCountNew() {

		String startDate = $V("startDate");
		String endDate = $V("endDate");
		String Markingkg = $V("Markingkg");
		String channel = $V("channel");
		String IntermediaryMember = $V("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and a.createdate >= '" + startDate + "'";
			parameter2 += " and tf.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and a.createdate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and tf.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and e.ProductType='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";

			String sqluser = "SELECT e.UserName FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username WHERE  f.RealName LIKE '%" + IntermediaryMember + "%' ";
			QueryBuilder queryBuilder = new QueryBuilder(sqluser);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			String sql_provideuser = "";
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				sql_provideuser += "'" + dataTable.getString(i, 0).toString() + "',";
			}
			if(!StringUtil.isEmpty(sql_provideuser)){
				sql_provideuser = sql_provideuser.substring(0, sql_provideuser.length() - 1);
				parameter2 += " and a.MemberId in (" + sql_provideuser + ")";
			}else{
				parameter2 += " and a.MemberId in ('')";
			}
				
			

		}
		String b2b_base = Config.getValue("B2BDataBase");
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT FORMAT(sum(totalamount),2) as totalamount,FORMAT(sum(charge),2) as charge FROM (");
			sql.append(" SELECT b.timePrem as totalamount, ");
			sql.append(" FORMAT((b.timePrem*(SELECT a2.feerate FROM sdproduct a2 WHERE c.productid=a2.productid)),2) 'charge'");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap','cps_drlm_wap')" + parameter1);
		}
		if (channel.indexOf("b2b") != -1) {			
			sql.append(" SELECT totalAmount,charge FROM (");
			sql.append("SELECT ROUND(sdor.timeprem, 2) AS totalAmount,"
				 + " b.productId AS charge "
				 + " FROM sdinformationrisktype sdor,sdorders a,sdinformation b,sdproduct e,SDOrderItem f"
				 + " LEFT JOIN tradeinformation tf ON tf.ordid = f.ordersn "
				 + " WHERE sdor.ordersn = a.ordersn "
				 + " AND a.ordersn = b.ordersn "
				 + " AND sdor.appStatus='1' "
				 + " AND b.productId = e.productId "
				 + " AND f.ordersn = b.ordersn "
				 + " AND a.channelsn in ('b2b_ht','b2c_pc','b2c_wap') "
				 + parameter2);}

		sql.append(") m ");
		logger.info("sql_CPSOrderCount==={}", sql.toString());
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;

		dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			if (channel.indexOf("cps") != -1) {
				Response.setMessage(dt.getString(0, 0) + "&" + dt.getString(0, 1));
			}
			if(channel.indexOf("b2b") != -1){
				String sqlproduct = "SELECT riskcode,rate AS vvalue FROM sdproduct";
				if (StringUtil.isNotEmpty(Markingkg)) {
					sqlproduct = sqlproduct + " where SUBSTRING(EdorFlag, 1, 1)='" + Markingkg + "'";
				}
				QueryBuilder queryBuilder = new QueryBuilder(sqlproduct);

				DataTable dataTable = queryBuilder.executeDataTable("B2B");
				Mapx toMapx = dataTable.toMapx("riskcode", "vvalue");
				String vvalue = "";
				String charge = "";
				double num1 = 0;
				double num2 = 0;
				for(int i=0;i<dt.getRowCount();i++){
					DataRow dataRow = dt.get(i);
					vvalue = "";
					charge = "0";
					double totalamount = Double.valueOf(dataRow.getString("totalamount"));
					if(!StringUtil.isEmpty(dataRow.getString("charge"))){
						vvalue = toMapx.get(dataRow.getString("charge")).toString();
						double feeRate = Double.valueOf(vvalue);
						charge = new DecimalFormat("0.00").format(ArithUtil.mul(totalamount, feeRate));
					}
					num1 = ArithUtil.add(num1, totalamount);
					num2 = ArithUtil.add(num2, Double.valueOf(charge));
				}
				Response.setMessage(new DecimalFormat("0.00").format(num1) + "&" + new DecimalFormat("0.00").format(num2));
			}
		} else {
			Response.setMessage("0.00&0.00");
		}

	}
	
	//分销管理-汇总统计
	public void orderStatStaff(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String Markingkg = dga.getParam("Markingkg");
		String channel = dga.getParam("channel");
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and d.receiveDate >= '" + startDate + "'";
			parameter2 += " and c.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and d.receiveDate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and c.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and SUBSTRING(d.EdorFlag, 1, 1)='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";

			parameter2 += " and f.realname like '%" + IntermediaryMember + "%'";
		}
		String b2b_base = Config.getValue("B2BDataBase");
		sql.append(" SELECT NAME,FORMAT(totalamount,2) as totalamount,Company,Mobile,Email,channelSn,IntermediaryMember FROM (");
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT g.NAME,sum(b.timePrem) as totalamount,g.Company,g.Mobile,g.Email,'网站联盟' AS channelSn,g.IntermediaryMember");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap')" + parameter1
					+ " GROUP BY g.`UserCode`");
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT e.UserName AS NAME,sum(g.prop2) as totalamount,e.CompanyName AS Company,e.Mobile,e.Email,'美行保' AS channelSn,f.realname AS IntermediaryMember");
			sql.append(" FROM " + b2b_base + ".sdorders a LEFT JOIN " + b2b_base
					+ ".zduser e ON a.MemberId = e.username LEFT JOIN " + b2b_base + ".zduser f ON f.username = e.prop9,"
					+ b2b_base + ".sdinsuredcompanyreturndata b," + b2b_base + ".sdtradeinformation c,");
			sql.append(" "
					+ b2b_base
					+ ".sdproduct d,"
					+ b2b_base
					+ ".sdorderitem g WHERE a.id = b.ordersn AND g.InsuredId = b.insuredSn AND a.id=c.ordid AND a.ProductId = d.Id AND b.appStatus = '1' "
					+ parameter2 + " GROUP BY e.UserName");
		}

		sql.append(") m ORDER BY NAME");
		logger.info("sql_CPSOrderStaff==={}", sql.toString());

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;
		if (channel.indexOf("b2b") != -1) {
			dt = qb.executePagedDataTable("B2B", dga.getPageSize(), dga.getPageIndex());
			dga.setTotal("B2B", qb);
		}

		else {
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dga.setTotal(qb);

		}

		dga.bindData(dt);

	}
	
	
	//分销管理-汇总统计
	public void orderStatStaffNew(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String Markingkg = dga.getParam("Markingkg");
		String channel = dga.getParam("channel");
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String parameter1 = "";
		String parameter2 = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			parameter1 += " and d.receiveDate >= '" + startDate + "'";
			parameter2 += " and tf.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter1 = parameter1 + " and d.receiveDate <='" + endDate + tminutes + "'";
			parameter2 = parameter2 + " and tf.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(Markingkg)) {
			parameter1 = parameter1 + " and e.ProductType='" + Markingkg + "'";
			parameter1 = parameter1 + " and e.ProductGenera is not null";

			parameter2 = parameter2 + " and e.ProductType='" + Markingkg + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			parameter1 += " and g.IntermediaryMember like '%" + IntermediaryMember + "%'";
			
			String sqluser = "SELECT e.UserName FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username WHERE  f.RealName LIKE '%" + IntermediaryMember + "%' ";
			QueryBuilder queryBuilder = new QueryBuilder(sqluser);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			String sql_provideuser = "";
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				sql_provideuser += "'" + dataTable.getString(i, 0).toString() + "',";
			}
			if(!StringUtil.isEmpty(sql_provideuser)){
				sql_provideuser = sql_provideuser.substring(0, sql_provideuser.length() - 1);
				parameter2 += " and a.MemberId in (" + sql_provideuser + ")";
			}else{
				parameter2 += " and a.MemberId in ('')";
			}
			
		}
		
		
		String b2b_base = Config.getValue("B2BDataBase");
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT NAME,FORMAT(totalamount,2) as totalamount,Company,Mobile,Email,channelSn,IntermediaryMember FROM (");
			sql.append(" SELECT g.NAME,sum(b.timePrem) as totalamount,g.Company,g.Mobile,g.Email,'网站联盟' AS channelSn,g.IntermediaryMember");
			sql.append(" FROM sdorders a,sdinformationrisktype b,SDInformation c,TradeInformation d,sdproduct e,cps f ");
			sql.append(" LEFT JOIN " + Config.getValue("CPSDataBase") + ".sduser g ON f.`cid` = g.usercode ");
			sql.append(" WHERE a.ordersn = c.ordersn AND a.ordersn = b.ordersn  AND d.ordID = b.ordersn ");
			sql.append(" AND b.riskcode = e.productid AND f.`on` = b.ordersn ");
			sql.append(" AND b.appStatus = '1' AND a.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap')" + parameter1
					+ " GROUP BY g.`UserCode`");
			sql.append(") m ORDER BY NAME");
		}
		
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT NAME,totalAmount,ChannelSn,Company,Mobile,Email,IntermediaryMember  FROM (");
			sql.append("SELECT a.MemberId AS NAME ,"
					 + " ROUND(SUM(sdor.timeprem), 2) AS totalAmount,'美行保' AS ChannelSn, "
					 + " '' as Company,'' as Mobile,'' as Email,'' as IntermediaryMember "
					 + " FROM sdinformationrisktype sdor,sdorders a,sdinformation b,sdproduct e,SDOrderItem f"
					 + " LEFT JOIN tradeinformation tf ON tf.ordid = f.ordersn "
					 + " WHERE sdor.ordersn = a.ordersn "
					 + " AND a.ordersn = b.ordersn "
					 + " AND sdor.appStatus='1' "
					 + " AND f.ordersn = b.ordersn "
					 + " AND b.productId = e.productId "
					 + " AND a.channelsn in ('b2b_ht','b2c_pc','b2c_wap') "
					 + parameter2 + " GROUP BY a.memberid");
			sql.append(") m");
		}
		logger.info("sql_CPSOrderStaff==={}", sql.toString());

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = null;
		if (channel.indexOf("b2b") != -1) {
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dga.setTotal(qb);
			if(!StringUtil.isEmpty(dt)&&dt.getRowCount()>0&&channel.indexOf("b2b") != -1){
				String sqluser = "SELECT e.UserName AS NAME,CONCAT(IFNULL(e.CompanyName,''),',',IFNULL(e.Mobile,''),',',IFNULL(e.Email,''),',',IFNULL(f.realname,'')) as vvalue FROM  zduser e LEFT JOIN zduser f ON f.username = e.prop9 ";
				if (StringUtil.isNotEmpty(IntermediaryMember)) {
					sqluser += " WHERE f.RealName like '%" + IntermediaryMember + "%'";
				}
				QueryBuilder queryBuilder = new QueryBuilder(sqluser);
				DataTable dataTable = queryBuilder.executeDataTable("B2B");
				Mapx toMapx = dataTable.toMapx("NAME", "vvalue");
				String vvalue = "";
				String Company = "";
				String Mobile = "";
				String Email = "";
				String IntermediaryMemberu = "";
				for(int i=0;i<dt.getRowCount();i++){
					DataRow dataRow = dt.get(i);
					vvalue = "";
					Company = "";
					Mobile = "";
					Email = "";
					IntermediaryMemberu = "";
					if(!StringUtil.isEmpty(toMapx.get(dataRow.getString("NAME")))){
						vvalue = toMapx.get(dataRow.getString("NAME")).toString();
						String[] vvalues = vvalue.split(",",-1);
						if(!StringUtil.isEmpty(vvalues[0])){
							Company = vvalues[0];
						}
						if(!StringUtil.isEmpty(vvalues[1])){
							Mobile = vvalues[1];
						}
						if(!StringUtil.isEmpty(vvalues[2])){
							Email = vvalues[2];
						}
						if(!StringUtil.isEmpty(vvalues[3])){
							IntermediaryMemberu = vvalues[3];
						}
					}
					dt.set(i, "Company", Company);
					dt.set(i, "Mobile", Mobile);
					dt.set(i, "Email", Email);
					dt.set(i, "IntermediaryMember", IntermediaryMemberu);
				}
				
			}
		}
		else {
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dga.setTotal(qb);
		}
		dga.bindData(dt);

	}


	/**
	 * ordersAnly:分销管理数据分析 <br/>
	 * 
	 * @author ciyuanshuang
	 * @param dga
	 * @throws Exception
	 */
	public void ordersAnly(DataGridAction dga) throws Exception {

		String anlySql = getAnlySql(dga);
		if (StringUtil.isEmpty(anlySql)) {
			return;
		}
		String channel = dga.getParam("channel");
		QueryBuilder qb = new QueryBuilder(anlySql);

		DataTable dt = null;
		if (channel.indexOf("b2b") != -1)
			dt = qb.executePagedDataTable("B2B", dga.getPageSize(), dga.getPageIndex());
		else
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		String isExportExcel = dga.getParam("_isExportExcel");
		if (StringUtil.isNotEmpty(isExportExcel)) {
			StringBuffer sumSql = new StringBuffer();
			// 为了和之前的记录列数对应
			sumSql.append(" SELECT '合计' userid,'' orderSn,'' productName, ROUND(IFNULL(SUM(a.productPrice),0),2) sumproductPrice,'' Rate,");
			sumSql.append(" ROUND(IFNULL(SUM(a.income),0),2)  sumincome,ROUND(IFNULL(SUM(a.oldprice),0),2)  sumoldprice,");
			sumSql.append(" ROUND(IFNULL(SUM(a.nowprice),0),2)  sumnowprice,'' hzfrate,'' discount,'' balance,'' remark,'' extractrate,");
			sumSql.append(" ROUND(IFNULL(SUM(a.extractaccount),0),2)  sumextractaccount, ");
			sumSql.append(" ROUND(IFNULL(SUM(a.totalcost),0),2)  sumtotalcost,ROUND(IFNULL(SUM(a.profit),0),2)  sumprofit FROM ( ");
			sumSql.append(anlySql);
			sumSql.append(" ) a ");
			DataTable sumDt = null;
			QueryBuilder sumQb = new QueryBuilder(sumSql.toString());
			if (channel.indexOf("b2b") != -1)
				sumDt = sumQb.executeDataTable("B2B");
			else
				sumDt = sumQb.executeDataTable();
			DataRow row = sumDt.get(0);
			dt.insertRow(row);
		}
		if (channel.indexOf("b2b") != -1)
			dga.setTotal("B2B", qb);
		else
			dga.setTotal(qb);
		
		dga.bindData(dt);
	}
	
	
	/**
	 * ordersAnly:分销管理数据分析 <br/>
	 * 
	 * @author ciyuanshuang
	 * @param dga
	 * @throws Exception
	 */
	public void ordersAnlyNew(DataGridAction dga) throws Exception {

		String anlySql = getAnlySqlNew(dga);
		if (StringUtil.isEmpty(anlySql)) {
			return;
		}
		String channel = dga.getParam("channel");
		QueryBuilder qb = new QueryBuilder(anlySql);

		DataTable dt = null;
		dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		String sumincome = "0";
		String sumextractaccount = "0";
		String sumtotalcost = "0";
		String sumprofit = "0";
		
		String sumproductprice = "0";
		String sumoldprice = "0";
		String sumnowprice = "0";
		
		if(!StringUtil.isEmpty(dt)&&dt.getRowCount()>0&&channel.indexOf("b2b") != -1){
			// 获取产品费率 等
			String sqlproduct = "SELECT CONCAT(a.branchinnercode,',',a.riskcode) as NAME , CONCAT(IFNULL(a.PayRate,''),',',IFNULL(a.BalanceRate,''),',',IFNULL(b.Premium,''),',',IFNULL(b.Rate,'')) as vvalue  FROM zdbranchproductnew a LEFT JOIN sdproduct b ON a.riskcode = b.riskcode ";
			QueryBuilder queryBuilder = new QueryBuilder(sqlproduct);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			Mapx toMapxproduct = dataTable.toMapx("NAME", "vvalue");
			
			// 获取用户 机构
			String IntermediaryMember = dga.getParam("IntermediaryMember");
			ZDUserRoleSchema role = new ZDUserRoleSchema();
			role.setUserName(User.getUserName());
			role.setRoleCode("mediamanager");
			if (role.fill()) {
				IntermediaryMember = User.getRealName();
			}
			
			String sqluser = "SELECT e.UserName AS UserName ,e.BranchInnerCode AS BranchInnerCode FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username ";
			if (StringUtil.isNotEmpty(IntermediaryMember)) {
				sqluser += " where f.RealName like '%" + IntermediaryMember + "%'";
			}
			QueryBuilder queryBuilderuser = new QueryBuilder(sqluser);
			DataTable dataTableuser = queryBuilderuser.executeDataTable("B2B");
			Mapx toMapxuser = dataTableuser.toMapx("UserName", "BranchInnerCode");
			
			// 获取机构性质
			String sqlzdbranch = "SELECT BranchInnerCode,TYPE FROM zdbranch ";
			QueryBuilder queryBuilderzdbranch = new QueryBuilder(sqlzdbranch);
			DataTable dataTablezdbranch = queryBuilderzdbranch.executeDataTable("B2B");
			Mapx toMapxzdbranch = dataTablezdbranch.toMapx("BranchInnerCode", "TYPE");
			
			//准备数据
			String BranchInnerCode="";
			String productId="";
			String productdetail="";
			String zdbranchType="";
			String Rate = "1";
			String income = "0.00";
			String hzfrate = "0.00";
			String discount = "0.00";
			String balance = "0.00";
			String extractaccount = "0.00";
			String totalcost = "0.00";
			String profit = "0.00";
			
			for(int i=0;i<dt.getRowCount();i++){
				BranchInnerCode="";
				productId="";
				productdetail="";
				zdbranchType="";
				Rate = "1";
				income = "0.00";
				hzfrate = "0.00";
				discount = "0.00";
				balance = "0.00";
				extractaccount = "0.00";
				totalcost = "0.00";
				profit = "0.00";
				
				//根据用户 获取机构
				if(!StringUtil.isEmpty(toMapxuser.get(dt.getString(i, "userid")))){
					BranchInnerCode=toMapxuser.get(dt.getString(i, "userid")).toString();
					productId = dt.getString(i, "productId");
					//根据机构产品 获取费率等
					if(!StringUtil.isEmpty(toMapxproduct.get(BranchInnerCode+","+productId))){
						productdetail=toMapxproduct.get(BranchInnerCode+","+productId).toString();
						//PayRate BalanceRate Premium Rate
						String[] productdetails = productdetail.split(",",-1);
						if(!StringUtil.isEmpty(productdetails[3])){
							Rate = new DecimalFormat("0.00").format(Double.valueOf(productdetails[3]));
							income = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(Rate)*0.94);
						}
						if(!StringUtil.isEmpty(productdetails[0])&&!StringUtil.isEmpty(productdetails[1])&&!StringUtil.isEmpty(productdetails[3])){
							double num1 = Double.valueOf(dt.getString(i, "oldprice"))*(1-Double.valueOf(productdetails[0]));
							double num2 = Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(productdetails[1]);
							double num3 = Double.valueOf(dt.getString(i, "oldprice"))*0.05;
							totalcost = new DecimalFormat("0.00").format(num1+num2+num3);
						}
						
						profit = new DecimalFormat("0.00").format(Double.valueOf(income)-Double.valueOf(totalcost));
						
						if(!StringUtil.isEmpty(toMapxzdbranch.get(BranchInnerCode))){
							zdbranchType=toMapxzdbranch.get(BranchInnerCode).toString();
							if("2".equals(zdbranchType.substring(0, 1))){
								if(!StringUtil.isEmpty(productdetails[0])){
									hzfrate = productdetails[0];
									discount=new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*(1-Double.valueOf(productdetails[0])));
								}
								balance="0.00";
							}else{
								if(!StringUtil.isEmpty(toMapxproduct.get(BranchInnerCode.substring(0, 8)+","+productId))){
									String BalanceRate = toMapxproduct.get(BranchInnerCode.substring(0, 8)+","+productId).toString();
									String[] BalanceRates = BalanceRate.split(",",-1);
									if(!StringUtil.isEmpty(BalanceRates[1])){
										hzfrate = BalanceRates[1];
										balance = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(hzfrate));
									}
								}
								discount="0.00";
							}
							
						}
					}

				}
				extractaccount = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*0.05);
				
				sumincome = new DecimalFormat("0.00").format(Double.valueOf(sumincome) + Double.valueOf(income));
				sumextractaccount = new DecimalFormat("0.00").format(Double.valueOf(sumextractaccount) + Double.valueOf(extractaccount));
				sumtotalcost = new DecimalFormat("0.00").format(Double.valueOf(sumtotalcost) + Double.valueOf(totalcost));
				sumprofit = new DecimalFormat("0.00").format(Double.valueOf(sumprofit) + Double.valueOf(profit));
				sumproductprice = new DecimalFormat("0.00").format(Double.valueOf(sumproductprice) + Double.valueOf(dt.getString(i, "productPrice")));
				sumoldprice = new DecimalFormat("0.00").format(Double.valueOf(sumoldprice) + Double.valueOf(dt.getString(i, "oldprice")));
				sumnowprice = new DecimalFormat("0.00").format(Double.valueOf(sumnowprice) + Double.valueOf(dt.getString(i, "nowprice")));
				
				dt.set(i, "Rate", Rate);
				dt.set(i, "income", income);
				dt.set(i, "hzfrate", hzfrate);
				dt.set(i, "discount", discount);
				dt.set(i, "balance", balance);
				dt.set(i, "extractaccount", extractaccount);
				dt.set(i, "totalcost", totalcost);
				dt.set(i, "profit", profit);
			}
			
		}	
		
		String isExportExcel = dga.getParam("_isExportExcel");
		if (StringUtil.isNotEmpty(isExportExcel)) {
			StringBuffer sumSql = new StringBuffer();
			// 为了和之前的记录列数对应
			if (channel.indexOf("b2b") != -1){
				sumSql.append(" SELECT '' productId,'合计' userid,'' orderSn,'' productName, ROUND(IFNULL(SUM(a.productPrice),0),2) sumproductPrice,'' Rate,");
				sumSql.append(" '"+sumincome+"' as  sumincome,ROUND(IFNULL(SUM(a.oldprice),0),2)  sumoldprice,");
				sumSql.append(" ROUND(IFNULL(SUM(a.nowprice),0),2)  sumnowprice,'' hzfrate,'' discount,'' balance,'' remark,'' extractrate,");
				sumSql.append(" '"+sumextractaccount+"' as  sumextractaccount, ");
				sumSql.append(" '"+sumtotalcost+"' as sumtotalcost,'"+sumprofit+"' as  sumprofit FROM ( ");
				sumSql.append(anlySql);
				sumSql.append(" ) a ");
			}else{
				sumSql.append(" SELECT '合计' userid,'' orderSn,'' productName, ROUND(IFNULL(SUM(a.productPrice),0),2) sumproductPrice,'' Rate,");
				sumSql.append(" ROUND(IFNULL(SUM(a.income),0),2)  sumincome,ROUND(IFNULL(SUM(a.oldprice),0),2)  sumoldprice,");
				sumSql.append(" ROUND(IFNULL(SUM(a.nowprice),0),2)  sumnowprice,'' hzfrate,'' discount,'' balance,'' remark,'' extractrate,");
				sumSql.append(" ROUND(IFNULL(SUM(a.extractaccount),0),2)  sumextractaccount, ");
				sumSql.append(" ROUND(IFNULL(SUM(a.totalcost),0),2)  sumtotalcost,ROUND(IFNULL(SUM(a.profit),0),2)  sumprofit FROM ( ");
				sumSql.append(anlySql);
				sumSql.append(" ) a ");
			}
			DataTable sumDt = null;
			QueryBuilder sumQb = new QueryBuilder(sumSql.toString());
			sumDt = sumQb.executeDataTable();
			DataRow row = sumDt.get(0);
			dt.insertRow(row);
		}
		if(channel.indexOf("b2b") != -1){
			if(dt.getRowCount() == 0){
				dt.insertRow((Object[]) null);
			}
			dt.insertColumn("ssumincome", sumincome);
			dt.insertColumn("ssumextractaccount", sumextractaccount);
			dt.insertColumn("ssumtotalcost", sumtotalcost);
			dt.insertColumn("ssumprofit", sumprofit);
			dt.insertColumn("ssumproductprice", sumproductprice);
			dt.insertColumn("ssumoldprice", sumoldprice);
			dt.insertColumn("ssumnowprice", sumnowprice);
			
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * ordersAnlySum:分销管理数据分析数据汇总. <br/>
	 * 
	 * @author ciyuanshuang
	 * @param dga
	 */
	public void ordersAnlySum() {

		DataGridAction dga = new DataGridAction();
		dga.setParams(Current.getRequest());
		String anlySql = getAnlySql(dga);
		if (StringUtil.isEmpty(anlySql)) {
			return;
		}
		String channel = dga.getParam("channel");
		StringBuffer sumSql = new StringBuffer();
		sumSql.append(" SELECT ROUND(IFNULL(SUM(a.productPrice),0),2) sumproductPrice,"
				+ " ROUND(IFNULL(SUM(a.income),0),2)  sumincome,ROUND(IFNULL(SUM(a.oldprice),0),2)  sumoldprice,");
		sumSql.append(" ROUND(IFNULL(SUM(a.nowprice),0),2)  sumnowprice,ROUND(IFNULL(SUM(a.extractaccount),0),2)  sumextractaccount, ");
		sumSql.append(" ROUND(IFNULL(SUM(a.totalcost),0),2)  sumtotalcost,ROUND(IFNULL(SUM(a.profit),0),2)  sumprofit FROM ( ");
		sumSql.append(anlySql);
		sumSql.append(" ) a ");
		QueryBuilder sumQb = new QueryBuilder(sumSql.toString());
		DataTable sumDt = null;

		if (channel.indexOf("b2b") != -1)
			sumDt = sumQb.executeDataTable("B2B");
		else
			sumDt = sumQb.executeDataTable();
		Response.put("Table", sumDt);
	}
	
	/**
	 * ordersAnlySum:分销管理数据分析数据汇总. <br/>
	 * 
	 * @author ciyuanshuang
	 * @param dga
	 */
	public void ordersAnlySumNew() {

		DataGridAction dga = new DataGridAction();
		dga.setParams(Current.getRequest());
		String anlySql = getAnlySqlNew(dga);
		if (StringUtil.isEmpty(anlySql)) {
			return;
		}
		String channel = dga.getParam("channel");
		QueryBuilder qb = new QueryBuilder(anlySql);
		DataTable dt = null;
		dt = qb.executeDataTable();
		
		String sumincome = "0";
		String sumextractaccount = "0";
		String sumtotalcost = "0";
		String sumprofit = "0";
		
		String sumproductprice = "0";
		String sumoldprice = "0";
		String sumnowprice = "0";
		
		if(!StringUtil.isEmpty(dt)&&dt.getRowCount()>0&&channel.indexOf("b2b") != -1){
			// 获取产品费率 等
			String sqlproduct = "SELECT CONCAT(a.branchinnercode,',',a.riskcode) as NAME , CONCAT(IFNULL(a.PayRate,''),',',IFNULL(a.BalanceRate,''),',',IFNULL(b.Premium,''),',',IFNULL(b.Rate,'')) as vvalue  FROM zdbranchproductnew a LEFT JOIN sdproduct b ON a.riskcode = b.riskcode ";
			QueryBuilder queryBuilder = new QueryBuilder(sqlproduct);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			Mapx toMapxproduct = dataTable.toMapx("NAME", "vvalue");
			
			// 获取用户 机构
			String IntermediaryMember = dga.getParam("IntermediaryMember");
			ZDUserRoleSchema role = new ZDUserRoleSchema();
			role.setUserName(User.getUserName());
			role.setRoleCode("mediamanager");
			if (role.fill()) {
				IntermediaryMember = User.getRealName();
			}
			
			String sqluser = "SELECT e.UserName AS UserName ,e.BranchInnerCode AS BranchInnerCode FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username ";
			if (StringUtil.isNotEmpty(IntermediaryMember)) {
				sqluser += " where f.RealName like '%" + IntermediaryMember + "%'";
			}
			QueryBuilder queryBuilderuser = new QueryBuilder(sqluser);
			DataTable dataTableuser = queryBuilderuser.executeDataTable("B2B");
			Mapx toMapxuser = dataTableuser.toMapx("UserName", "BranchInnerCode");
			
			// 获取机构性质
			String sqlzdbranch = "SELECT BranchInnerCode,TYPE FROM zdbranch ";
			QueryBuilder queryBuilderzdbranch = new QueryBuilder(sqlzdbranch);
			DataTable dataTablezdbranch = queryBuilderzdbranch.executeDataTable("B2B");
			Mapx toMapxzdbranch = dataTablezdbranch.toMapx("BranchInnerCode", "TYPE");
			
			//准备数据
			String BranchInnerCode="";
			String productId="";
			String productdetail="";
			String zdbranchType="";
			String Rate = "1";
			String income = "0.00";
			String hzfrate = "0.00";
			String discount = "0.00";
			String balance = "0.00";
			String extractaccount = "0.00";
			String totalcost = "0.00";
			String profit = "0.00";
			
			for(int i=0;i<dt.getRowCount();i++){
				BranchInnerCode="";
				productId="";
				productdetail="";
				zdbranchType="";
				Rate = "1";
				income = "0.00";
				hzfrate = "0.00";
				discount = "0.00";
				balance = "0.00";
				extractaccount = "0.00";
				totalcost = "0.00";
				profit = "0.00";
				
				//根据用户 获取机构
				if(!StringUtil.isEmpty(toMapxuser.get(dt.getString(i, "userid")))){
					BranchInnerCode=toMapxuser.get(dt.getString(i, "userid")).toString();
					productId = dt.getString(i, "productId");
					//根据机构产品 获取费率等
					if(!StringUtil.isEmpty(toMapxproduct.get(BranchInnerCode+","+productId))){
						productdetail=toMapxproduct.get(BranchInnerCode+","+productId).toString();
						//PayRate BalanceRate Premium Rate
						String[] productdetails = productdetail.split(",",-1);
						if(!StringUtil.isEmpty(productdetails[3])){
							Rate = new DecimalFormat("0.00").format(Double.valueOf(productdetails[3]));
							income = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(Rate)*0.94);
						}
						if(!StringUtil.isEmpty(productdetails[0])&&!StringUtil.isEmpty(productdetails[1])&&!StringUtil.isEmpty(productdetails[3])){
							double num1 = Double.valueOf(dt.getString(i, "oldprice"))*(1-Double.valueOf(productdetails[0]));
							double num2 = Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(productdetails[1]);
							double num3 = Double.valueOf(dt.getString(i, "oldprice"))*0.05;
							totalcost = new DecimalFormat("0.00").format(num1+num2+num3);
						}
						
						profit = new DecimalFormat("0.00").format(Double.valueOf(income)-Double.valueOf(totalcost));
						
						if(!StringUtil.isEmpty(toMapxzdbranch.get(BranchInnerCode))){
							zdbranchType=toMapxzdbranch.get(BranchInnerCode).toString();
							if("2".equals(zdbranchType.substring(0, 1))){
								if(!StringUtil.isEmpty(productdetails[0])){
									hzfrate = productdetails[0];
									discount=new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*(1-Double.valueOf(productdetails[0])));
								}
								balance="0.00";
							}else{
								if(!StringUtil.isEmpty(toMapxproduct.get(BranchInnerCode.substring(0, 8)+","+productId))){
									String BalanceRate = toMapxproduct.get(BranchInnerCode.substring(0, 8)+","+productId).toString();
									String[] BalanceRates = BalanceRate.split(",",-1);
									if(!StringUtil.isEmpty(BalanceRates[1])){
										hzfrate = BalanceRates[1];
										balance = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*Double.valueOf(hzfrate));
									}
								}
								discount="0.00";
							}
							
						}
					}

				}
				extractaccount = new DecimalFormat("0.00").format(Double.valueOf(dt.getString(i, "oldprice"))*0.05);
				
				sumincome = new DecimalFormat("0.00").format(Double.valueOf(sumincome) + Double.valueOf(income));
				sumextractaccount = new DecimalFormat("0.00").format(Double.valueOf(sumextractaccount) + Double.valueOf(extractaccount));
				sumtotalcost = new DecimalFormat("0.00").format(Double.valueOf(sumtotalcost) + Double.valueOf(totalcost));
				sumprofit = new DecimalFormat("0.00").format(Double.valueOf(sumprofit) + Double.valueOf(profit));
				sumproductprice = new DecimalFormat("0.00").format(Double.valueOf(sumproductprice) + Double.valueOf(dt.getString(i, "productPrice")));
				sumoldprice = new DecimalFormat("0.00").format(Double.valueOf(sumoldprice) + Double.valueOf(dt.getString(i, "oldprice")));
				sumnowprice = new DecimalFormat("0.00").format(Double.valueOf(sumnowprice) + Double.valueOf(dt.getString(i, "nowprice")));
				
			}
			
		}	

		DataTable sumDt = new DataTable();
		DataColumn[] types = new DataColumn[7];
		types[0] = new DataColumn("sumproductPrice", DataColumn.STRING);
		types[1] = new DataColumn("sumincome", DataColumn.STRING);
		types[2] = new DataColumn("sumoldprice", DataColumn.STRING);
		types[3] = new DataColumn("sumnowprice", DataColumn.STRING);
		types[4] = new DataColumn("sumextractaccount", DataColumn.STRING);
		types[5] = new DataColumn("sumtotalcost", DataColumn.STRING);
		types[6] = new DataColumn("sumprofit", DataColumn.STRING);
		String [] values = {sumproductprice,sumincome,sumoldprice,sumnowprice,sumextractaccount,sumtotalcost,sumprofit};
		DataRow dr = new DataRow(types, values);
		sumDt.insertRow(dr);
		Response.put("Table", sumDt);
	}

	private String getAnlySqlNew(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		// 渠道至少是美行保 网站联盟中的一个
		String channel = dga.getParam("channel");
		if (StringUtil.isEmpty(channel)) {
			return "";
		}
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String cpsParam = "";
		String b2bParam = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			cpsParam += " and h.receiveDate >= '" + startDate + "'";
			b2bParam += " and tf.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			cpsParam = cpsParam + " and h.receiveDate <='" + endDate + tminutes + "'";
			b2bParam = b2bParam + " and tf.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			cpsParam += " and a.IntermediaryMember like '%" + IntermediaryMember + "%'";
			
			String sqluser = "SELECT e.UserName FROM  zduser e LEFT JOIN zduser f ON  e.prop9=f.username WHERE  f.RealName LIKE '%" + IntermediaryMember + "%' ";
			QueryBuilder queryBuilder = new QueryBuilder(sqluser);
			DataTable dataTable = queryBuilder.executeDataTable("B2B");
			String sql_provideuser = "";
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				sql_provideuser += "'" + dataTable.getString(i, 0).toString() + "',";
			}
			if(!StringUtil.isEmpty(sql_provideuser)){
				sql_provideuser = sql_provideuser.substring(0, sql_provideuser.length() - 1);
				b2bParam += " and a.MemberId in (" + sql_provideuser + ")";
			}else{
				b2bParam += " and a.MemberId in ('')";
			}
			
		}
		String cpsSource = Config.getValue("CPSDataBase");
		// 是否选择了cps
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT t.*,productPrice * hzfrate balance, ");
			sql.append(" ROUND(productPrice * hzfrate + productPrice * 0.05,2) totalcost, ");
			sql.append(" ROUND(productPrice * rate * 0.94- productPrice * hzfrate + productPrice * 0.05,2) profit  ");
			sql.append("  FROM ( SELECT  a.usercode userid,b.on orderSn,c.productName,c.productPrice,d.FeeRate rate, ");
			sql.append(" ROUND((c.productPrice * d.FeeRate * 0.94),2) income,c.productPrice oldprice, ");
			sql.append(" SUM(f.payPrice) nowprice, ");
			sql.append(" IFNULL((SELECT Commission FROM " + cpsSource
					+ ".sdproductcommission WHERE usercode = a.usercode AND productid = c.productid), ");
			sql.append(" (SELECT commissionrates FROM " + cpsSource
					+ ".ZDSetProductInformation WHERE productid = c.productid)) hzfrate, ");
			sql.append(" '' discount,'' remark,'5%' extractrate,ROUND((c.productPrice * 0.05), 2) extractaccount ");
			sql.append(" FROM " + cpsSource
					+ ".sduser a,cps b,sdinformation c,sdproduct d,sdinformationrisktype f,sdorders g,TradeInformation h ");
			sql.append(" WHERE f.appStatus = 1 AND  a.usercode = b.cid AND b.on = c.orderSn AND c.productId = d.ProductID  ");
			sql.append("  AND f.riskCode = d.ProductID AND b.on = f.orderSn  AND g.orderSn = f.orderSn AND g.orderSn = c.orderSn AND h.ordID = f.orderSn ");
			sql.append(" AND g.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap')  ").append(cpsParam);
			sql.append(" GROUP BY b.on ) t ");
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append("SELECT   b.productId AS productId,a.memberid AS userid,  b.ordersn AS ordersn,  b.productName AS productName,  "
					 + "b.productPrice AS productPrice,  '' AS Rate,  '' AS income,   ROUND(sum(sdor.productPrice), 2) AS oldprice, CASE WHEN ROUND(SUM(sdor.payPrice), 2) IS NULL THEN ROUND(SUM(sdor.timePrem), 2) ELSE ROUND(SUM(sdor.payPrice), 2) END AS nowprice,  "
					 + "'' AS hzfrate,  '' AS discount,  '' AS balance,  '' remark,  '5%' extractrate,  '' AS  extractaccount,"
					 + "  '' AS  totalcost,  '' AS  profit  "
					 + " FROM  sdinformationrisktype sdor,  sdorders a,  sdinformation b,  "
					 + " sdproduct e,  SDOrderItem f   LEFT JOIN tradeinformation tf  "
					 + " ON tf.ordid = f.ordersn      "
					 + " WHERE sdor.ordersn = a.ordersn   AND a.ordersn = b.ordersn   "
					 + " AND sdor.appStatus = '1'   AND f.ordersn = b.ordersn   "
					 + " AND b.productId = e.productId   "
					 + " AND a.channelsn IN ('b2b_ht', 'b2c_pc','b2c_wap') ");
			sql.append(b2bParam);
			sql.append(" group by a.ordersn ");
		}
		return sql.toString();
	}
	
	
	private String getAnlySql(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		// 渠道至少是美行保 网站联盟中的一个
		String channel = dga.getParam("channel");
		if (StringUtil.isEmpty(channel)) {
			return "";
		}
		String IntermediaryMember = dga.getParam("IntermediaryMember");
		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			IntermediaryMember = User.getRealName();
		}
		String cpsParam = "";
		String b2bParam = "";
		StringBuffer sql = new StringBuffer();

		if (StringUtil.isNotEmpty(startDate)) {
			cpsParam += " and h.receiveDate >= '" + startDate + "'";
			b2bParam += " and h.receiveDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			cpsParam = cpsParam + " and h.receiveDate <='" + endDate + tminutes + "'";
			b2bParam = b2bParam + " and h.receiveDate <='" + endDate + tminutes + "'";
		}
		if (StringUtil.isNotEmpty(IntermediaryMember)) {
			cpsParam += " and a.IntermediaryMember like '%" + IntermediaryMember + "%'";
			b2bParam += " and e.realname like '%" + IntermediaryMember + "%'";
		}
		String cpsSource = Config.getValue("CPSDataBase");
		String b2bSource = Config.getValue("B2BDataBase");
		// 是否选择了cps
		if (channel.indexOf("cps") != -1) {
			sql.append(" SELECT t.*,productPrice * hzfrate balance, ");
			sql.append(" ROUND(productPrice * hzfrate + productPrice * 0.05,2) totalcost, ");
			sql.append(" ROUND(productPrice * rate * 0.94- productPrice * hzfrate + productPrice * 0.05,2) profit  ");
			sql.append("  FROM ( SELECT  a.usercode userid,b.on orderSn,c.productName,c.productPrice,d.FeeRate rate, ");
			sql.append(" ROUND((c.productPrice * d.FeeRate * 0.94),2) income,c.productPrice oldprice, ");
			sql.append(" SUM(f.payPrice) nowprice, ");
			sql.append(" IFNULL((SELECT Commission FROM " + cpsSource
					+ ".sdproductcommission WHERE usercode = a.usercode AND productid = c.productid), ");
			sql.append(" (SELECT commissionrates FROM " + cpsSource
					+ ".ZDSetProductInformation WHERE productid = c.productid)) hzfrate, ");
			sql.append(" '' discount,'' remark,'5%' extractrate,ROUND((c.productPrice * 0.05), 2) extractaccount ");
			sql.append(" FROM " + cpsSource
					+ ".sduser a,cps b,sdinformation c,sdproduct d,sdinformationrisktype f,sdorders g,TradeInformation h ");
			sql.append(" WHERE f.appStatus = 1 AND  a.usercode = b.cid AND b.on = c.orderSn AND c.productId = d.ProductID  ");
			sql.append("  AND f.riskCode = d.ProductID AND b.on = f.orderSn  AND g.orderSn = f.orderSn AND g.orderSn = c.orderSn AND h.ordID = f.orderSn ");
			sql.append(" AND g.channelsn IN ('cps_swpt','wap_cps','cps_swpt_wap')  ").append(cpsParam);
			sql.append(" GROUP BY b.on ) t ");
		}
		if (channel.indexOf("b2b") != -1) {
			sql.append(" SELECT a.username userid,b.id orderSn,b.productName,SUM(c.`Premium`) productPrice,c.Rate, ");
			sql.append(" ROUND(SUM(g.`Prop2`) * c.Rate * 0.94, 2) income, ");
			sql.append(" ROUND(SUM(g.`Prop2`),2) oldprice,ROUND(SUM(g.`Prop3`),2) nowprice, ");
			sql.append(" IF(LEFT((SELECT TYPE FROM "
					+ b2bSource
					+ ".zdbranch WHERE branchinnercode = b.`BranchInnerCode`),1) =2,d.PayRate,"
					+ " (SELECT BalanceRate FROM "
					+ b2bSource
					+ ".zdbranchproduct WHERE BranchInnerCode = LEFT(b.`BranchInnerCode`,8) AND riskcode = b.`ProductId` )) hzfrate, ");
			sql.append(" ROUND(IF(LEFT((SELECT TYPE FROM "
					+ b2bSource
					+ ".zdbranch WHERE branchinnercode = b.`BranchInnerCode`),1) =2, SUM(g.`Prop2`)*(1-d.PayRate),'0.00'),2) discount, ");
			sql.append(" ROUND(IF(LEFT((SELECT TYPE FROM "
					+ b2bSource
					+ ".zdbranch WHERE branchinnercode = b.`BranchInnerCode`),1) =1, SUM(g.`Prop2`)*(SELECT BalanceRate FROM "
					+ b2bSource
					+ ".zdbranchproduct WHERE BranchInnerCode = LEFT(b.`BranchInnerCode`,8) AND riskcode = b.`ProductId` ),'0.00'),2) balance, ");
			sql.append(" '' remark,'5%' extractrate, ROUND(SUM(g.`Prop2`) * 0.05, 2) extractaccount, ");
			sql.append(" ROUND(SUM(g.`Prop2`)*(1-d.PayRate)+SUM(g.`Prop2`)*d.BalanceRate+SUM(g.`Prop2`)*0.05,2) totalcost, ");
			sql.append(" ROUND(SUM(g.`Prop2`)*c.Rate*0.94-(SUM(g.`Prop2`)*(1-d.PayRate)+SUM(g.`Prop2`)*d.BalanceRate+SUM(g.`Prop2`)*0.05),2) profit ");
			sql.append(" FROM " + b2bSource + ".sdorders b LEFT JOIN " + b2bSource
					+ ".zdbranchproduct d ON d.`BranchInnerCode` = b.`BranchInnerCode` AND d.`RiskCode` = b.`ProductId` "
					+ " LEFT JOIN " + b2bSource + ".zduser a ON b.MemberId = a.username LEFT JOIN " + b2bSource
					+ ".zduser e ON e.username = a.prop9, "
					+ b2bSource + ".sdproduct c," + b2bSource + ".sdinsuredcompanyreturndata f ," + b2bSource
					+ ".sdorderitem g," + b2bSource + ".sdtradeinformation h");
			sql.append(" WHERE b.ProductId = c.riskcode AND f.`orderSn` = b.`Id`  AND f.insuredSn = g.InsuredId  ");
			sql.append(" AND g.`InsuredId` = f.`insuredSn` AND f.`appStatus` = '1' ");
			sql.append(" AND b.id = h.ordid   ");
			sql.append(b2bParam);
			sql.append(" GROUP BY b.id  ");
		}
		return sql.toString();
	}


	public static Mapx init(Mapx params) {

		ZDUserRoleSchema role = new ZDUserRoleSchema();
		role.setUserName(User.getUserName());
		role.setRoleCode("mediamanager");
		if (role.fill()) {
			params.put("mediamanager", "Y");
		}
		params.put("Markingkg", HtmlUtil.mapxToOptions(getMarkingkg(), null, true));// 类别
		params.put("yesterday", yesterday);
		params.put("today", today);
		return params;
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

	private static String getFormat(String format, Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
