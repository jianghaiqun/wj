package com.wangjin.infoseeker;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
public class PolicyInfo extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	private static String tminutes = " 23:59:59";

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initStaff(Mapx params) {
		Mapx map = new Mapx();
		map.put("Markingkg", HtmlUtil.mapxToOptions(getMarkingkgb(), null, true));// 类别
		map.put("company", HtmlUtil.codeToOptions("SupplierCode", true));// 保险公司
		map.put("yesterday", yesterday);
		map.put("today", today);
		map.put("contant", HtmlUtil.codeToOptions("Sales.channel",false));
		return map;
	}

	/**
	 * 保单详情统计数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String Markingkg = dga.getParam("Markingkg");
		String channelsn = dga.getParam("contant");
		String channelsnb2b = channelsn;
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		String orderSn = dga.getParam("orderSn");
		String productName = dga.getParam("productName");
		String company = dga.getParam("company");
		String applicantName = dga.getParam("applicantName");
		String applicantMobile = dga.getParam("applicantMobile");
		String applicantMail = dga.getParam("applicantMail");
		String applicantIdentityId = dga.getParam("applicantIdentityId");
		// 投保时间
		String insureDateStart = dga.getParam("insureDateStart");
		String insureDateEnd = dga.getParam("insureDateEnd");
		String firstAppTimeFlag = dga.getParam("firstAppTimeFlag"); // 首次投保时间区分
		String nextToLastAppTimeFlag = dga.getParam("nextToLastAppTimeFlag"); // 倒数第二次投保时间区分
		String lastAppTimeFlag = dga.getParam("lastAppTimeFlag"); // 最后一次投保时间区分
		String show = dga.getParam("show");
		
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
		if (StringUtil.isNotEmpty(orderSn)) {
			parameter = parameter +" and a.ordersn ='" + orderSn + "'";
		}
		if (StringUtil.isNotEmpty(productName)) {
			parameter = parameter +" and b.productName ='" + productName + "'";
		}
		if (StringUtil.isNotEmpty(company)) {
			parameter = parameter +" and b.insuranceCompany ='" + company + "'";
		}
		if (StringUtil.isNotEmpty(applicantName)) {
			parameter = parameter +" and g.applicantName ='" + applicantName + "'";
		}
		if (StringUtil.isNotEmpty(applicantMobile)) {
			parameter = parameter +" and g.applicantMobile ='" + applicantMobile + "'";
		}
		if (StringUtil.isNotEmpty(applicantMail)) {
			parameter = parameter +" and g.applicantMail ='" + applicantMail + "'";
		}
		if (StringUtil.isNotEmpty(applicantIdentityId)) {
			parameter = parameter +" and g.applicantIdentityId ='" + applicantIdentityId + "'";
		}
		if (StringUtil.isNotEmpty(insureDateStart)) {
			parameter = parameter +" and DATE_FORMAT(sdor.insureDate,'%Y-%m-%d') >= '" + insureDateStart + "'";
		}
		if (StringUtil.isNotEmpty(insureDateEnd)) {
			parameter = parameter +" and DATE_FORMAT(sdor.insureDate,'%Y-%m-%d') <= '" + insureDateEnd + "'";
		}
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT  (SELECT ChannelName FROM channelinfo WHERE ChannelCode=a.channelsn) AS ChannelSn, ");
			sql.append(" (SELECT concat(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id = a.memberid) AS MemberID,");
			sql.append(" sdor.policyNo,b.ordersn AS orderSn ,b.productName AS productName,b.ensureDisplay, ");
			sql.append(" g.applicantName,g.applicantIdentityId,g.applicantSexName,g.applicantBirthday,g.applicantMobile,g.applicantMail,");
			sql.append(" h.recognizeeAppntRelationName,h.recognizeeName,h.recognizeeIdentityId,h.recognizeeSexName,h.recognizeeBirthday,h.recognizeeMobile,h.recognizeeMail, ");
			sql.append(" (SELECT NAME FROM occupation WHERE CODE =h.recognizeeoccupation3 AND insuranceCompany=b.insuranceCompany) Occu,");
			sql.append(" h.destinationCountryText,sdor.payPrice, " );
			sql.append(" FORMAT(sdor.timeprem,2) AS totalAmount,tf.receiveDate AS modifyDate, ");
			sql.append(" DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,DATE_FORMAT(b.endDate,'%Y-%m-%d') AS endDate,sdor.cancelDate AS cancelDate, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='SupplierCode' and CodeValue=b.insuranceCompany) AS company,e.ProductGenera as risktype,e.productid, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=sdor.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=a.orderStatus) AS orderStatusName, a.paySn, sdor.insureDate"
					+ ",  CASE b.chargeYear WHEN NULL THEN '' ELSE IFNULL (  b.chargeYearName ,(SELECT factordisplayvalue FROM FEMRiskFactorListB X WHERE  x.riskcode=b.productId AND x.factorvalue=b.chargeYear AND x.factortype='FeeYear' LIMIT 1)) END AS chargeYearName ");
			sql.append(" FROM sdinformationrisktype sdor,sdorders a,sdinformation b,sdinformationappnt g,sdinformationinsured h,sdproduct e,SDOrderItem f ");
			sql.append(" LEFT JOIN tradeinformation tf ON tf.ordid=f.ordersn ");
			sql.append(" WHERE sdor.ordersn=a.ordersn AND a.ordersn = b.ordersn AND b.productId = e.productId AND a.orderStatus >=7 AND a.orderStatus<>'8'   AND f.ordersn=b.ordersn ");
			sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) AND g.informationsn = b.informationsn AND h.informationsn = b.informationsn AND h.recognizeesn = sdor.recognizeesn  ");
			sql.append(parameter);
			sql.append(" ORDER BY tf.receiveDate");
		} else if("1".equals(cancelFlag)){
			sql.append(" SELECT  (SELECT ChannelName FROM channelinfo WHERE ChannelCode=a.channelsn) AS ChannelSn, ");
			sql.append(" (SELECT concat(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id = a.memberid) AS MemberID,  ");
			sql.append(" sdor.policyNo,a.ordersn,b.productName,b.ensureDisplay,g.applicantName,g.applicantIdentityId,g.applicantSexName,g.applicantBirthday,g.applicantMobile,g.applicantMail, ");
			sql.append(" h.recognizeeAppntRelationName,h.recognizeeName,h.recognizeeIdentityId,h.recognizeeSexName,h.recognizeeBirthday,h.recognizeeMobile,h.recognizeeMail, ");
			sql.append("sdor.cancelDate,FORMAT(sdor.timePrem,2) AS totalAmount, ");
			sql.append(" tf.receiveDate AS modifyDate, ");
			sql.append(" (SELECT NAME FROM occupation WHERE CODE =h.recognizeeoccupation3 AND insuranceCompany=b.insuranceCompany) Occu, ");
			sql.append(" h.destinationCountryText,sdor.payPrice, " );
			sql.append(" DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,DATE_FORMAT(b.endDate,'%Y-%m-%d') AS endDate, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='SupplierCode' and CodeValue=b.insuranceCompany) AS company,e.ProductGenera as risktype,e.productid, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=sdor.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=a.orderStatus) AS orderStatusName, a.paySn, sdor.insureDate "
					+ ", CASE b.chargeYear WHEN NULL THEN '' ELSE IFNULL (  b.chargeYearName ,(SELECT factordisplayvalue FROM FEMRiskFactorListB X WHERE  x.riskcode=b.productId AND x.factorvalue=b.chargeYear AND x.factortype='FeeYear' LIMIT 1 )) END AS chargeYearName ");
			sql.append(" FROM  sdinformation b,sdinformationappnt g,sdinformationinsured h, sdproduct e,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=e.ProductID ");
			sql.append(" AND sdor.appstatus >=2 AND a.paySn NOT LIKE 'BG%'  AND (sdor.changeStatus IS NULL OR sdor.changeStatus='') ");
			sql.append(" AND g.informationsn = b.informationsn AND h.informationsn = b.informationsn AND h.recognizeesn = sdor.recognizeesn ");
			sql.append(parameter);
			sql.append(" UNION ALL ");
			sql.append(" SELECT  (SELECT ChannelName FROM channelinfo WHERE ChannelCode=a.channelsn) AS ChannelSn, ");
			sql.append(" (SELECT concat(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id = a.memberid) AS MemberID,  ");
			sql.append(" sdor.policyNo,a.ordersn,b.productName,sdor.cancelDate,FORMAT(sdor.timePrem,2) AS totalAmount, ");
			sql.append(" tf.receiveDate AS modifyDate,b.ensureDisplay, ");
			sql.append(" g.applicantName,g.applicantIdentityId,g.applicantSexName,g.applicantBirthday,g.applicantMobile,g.applicantMail, ");
			sql.append(" h.recognizeeAppntRelationName,h.recognizeeName,h.recognizeeIdentityId,h.recognizeeSexName,h.recognizeeBirthday,h.recognizeeMobile,h.recognizeeMail, ");
			sql.append(" (SELECT NAME FROM occupation WHERE CODE =h.recognizeeoccupation3 AND insuranceCompany=b.insuranceCompany) Occu, ");
			sql.append(" h.destinationCountryText,sdor.payPrice, " );
			sql.append(" DATE_FORMAT(b.startDate,'%Y-%m-%d') AS startDate,DATE_FORMAT(b.endDate,'%Y-%m-%d') AS endDate, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='SupplierCode' and CodeValue=b.insuranceCompany) AS company,e.ProductGenera as risktype,e.productid, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=sdor.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=a.orderStatus) AS orderStatusName, a.paySn, sdor.insureDate "
					+ ",CASE b.chargeYear WHEN NULL THEN '' ELSE IFNULL (  b.chargeYearName ,(SELECT factordisplayvalue FROM FEMRiskFactorListB X WHERE  x.riskcode=b.productId AND x.factorvalue=b.chargeYear AND x.factortype='FeeYear' LIMIT 1)) END AS chargeYearName ");
			sql.append(" FROM  sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdinformationappnt g,sdinformationinsured h, sdproduct e,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND p.os= b.orderSn AND b.productId=e.ProductID ");
			sql.append(" AND sdor.appstatus >=2 AND a.paySn LIKE 'BG%' ");
			sql.append(" AND g.informationsn = b.informationsn AND h.informationsn = b.informationsn AND h.recognizeesn = sdor.recognizeesn ");
			sql.append(parameter);
		}

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		if ("Y".equals(firstAppTimeFlag) || "Y".equals(nextToLastAppTimeFlag) || "Y".equals(lastAppTimeFlag)) {
			// 投保人的首次投保时间、倒数第二次投保时间、最后一次投保时间
			String sql13 = "SELECT MAX(idt.insureDate) AS lastAppTime, MIN(idt.insureDate) AS firstAppTime "
					+ "FROM (SELECT rt.insureDate FROM sdinformationrisktype rt, sdinformationappnt app "
					+ "WHERE rt.informationSn = app.informationSn AND rt.insureDate <> '' AND rt.insureDate IS NOT NULL "
					+ "AND app.applicantIdentityId = ? GROUP BY ordersn) AS idt";
			
			String sql1 = "SELECT MIN(idt.insureDate) AS firstAppTime "
				+ "FROM (SELECT rt.insureDate FROM sdinformationrisktype rt, sdinformationappnt app "
				+ "WHERE rt.informationSn = app.informationSn AND rt.insureDate <> '' AND rt.insureDate IS NOT NULL "
				+ "AND app.applicantIdentityId = ? GROUP BY ordersn) AS idt";
			
			String sql2 = "SELECT idt.insureDate AS nextToLastAppTime "
					+ "FROM (SELECT rt.insureDate FROM sdinformationrisktype rt, sdinformationappnt app "
					+ "WHERE rt.informationSn = app.informationSn AND rt.insureDate <> '' AND rt.insureDate IS NOT NULL "
					+ "AND app.applicantIdentityId = ? GROUP BY ordersn) AS idt ORDER BY idt.insureDate DESC LIMIT 1,1";

			String sql3 = "SELECT MAX(idt.insureDate) AS lastAppTime "
				+ "FROM (SELECT rt.insureDate FROM sdinformationrisktype rt, sdinformationappnt app "
				+ "WHERE rt.informationSn = app.informationSn AND rt.insureDate <> '' AND rt.insureDate IS NOT NULL "
				+ "AND app.applicantIdentityId = ? GROUP BY ordersn) AS idt";
			
			List<String> lastAppTimeList = new ArrayList<String>();
			List<String> firstAppTimeList = new ArrayList<String>();
			List<String> nextToLastAppTimeList = new ArrayList<String>();
			
			for (int i = 0; i < dt.getRowCount(); i++) {
				if ("Y".equals(firstAppTimeFlag) && "Y".equals(lastAppTimeFlag)) {
					DataTable dt13 = new QueryBuilder(sql13, dt.getString(i, "applicantIdentityId")).executeDataTable();
					firstAppTimeList.add(dt13.getString(0, "firstAppTime"));
					lastAppTimeList.add(dt13.getString(0, "lastAppTime"));
				} else {
					if ("Y".equals(firstAppTimeFlag)) {
						DataTable dt1 = new QueryBuilder(sql1, dt.getString(i, "applicantIdentityId")).executeDataTable();
						firstAppTimeList.add(dt1.getString(0, "firstAppTime"));
					} else {
						firstAppTimeList.add("");
					}
					if ("Y".equals(lastAppTimeFlag)) {
						DataTable dt3 = new QueryBuilder(sql3, dt.getString(i, "applicantIdentityId")).executeDataTable();
						lastAppTimeList.add(dt3.getString(0, "lastAppTime"));
					} else {
						lastAppTimeList.add("");
					}
				}
				
				if ("Y".equals(nextToLastAppTimeFlag)) {
					DataTable dt2 = new QueryBuilder(sql2, dt.getString(i, "applicantIdentityId")).executeDataTable();
					if (dt2.getRowCount() > 0) {
						nextToLastAppTimeList.add(dt2.getString(0, "nextToLastAppTime"));
					} else {
						nextToLastAppTimeList.add("");
					}
				} else {
					nextToLastAppTimeList.add("");
				}
			}
			dt.insertColumn("lastAppTime", lastAppTimeList.toArray());
			dt.insertColumn("firstAppTime", firstAppTimeList.toArray());
			dt.insertColumn("nextToLastAppTime", nextToLastAppTimeList.toArray());
		} else {
			String [] arr = new String[dt.getRowCount()];
			dt.insertColumn("lastAppTime", arr);
			dt.insertColumn("firstAppTime", arr);
			dt.insertColumn("nextToLastAppTime", arr);
		}
		
		if (b2bDT != null && b2bDT.getRowCount() >= 1) {
			dt.union(b2bDT);
		}
		if (!"1".equals(show)) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String memberid = (String)dt.get(i, 1);
				String mobileNo = (String)dt.get(i, 10);
				if (StringUtil.isNotEmpty(memberid) && StringUtil.isNotEmpty(mobileNo) && memberid.length() > 11) {
					if (mobileNo.equals(memberid.substring(memberid.length()-11, memberid.length()))) {
						String mStr = mobileNo.substring(0, mobileNo.length() - 8) + "******";
						dt.set(i, 1,mStr);
					}
				}
				String str = (String)dt.get(i, 7);
				if (StringUtil.isEmpty(str)) {
				}else if (str.length() >=6) {
					String mStr = str.substring(0, str.length() - 6) + "******";
					dt.set(i, 7,mStr);
				}else if(str.length() >=3 && str.length() <6){
					String mStr = str.substring(0, str.length() - 3) + "***";
					dt.set(i, 7,mStr);
				}else{
					
				}
				
				dt.set(i, 10,toConcealUtil.toConceal((String)dt.get(i, 10),4));
				
				String str1 = (String)dt.get(i, 14);
				if (StringUtil.isEmpty(str1)) {
				}else if (str1.length() >=6) {
					String mStr = str1.substring(0, str1.length() - 6) + "******";
					dt.set(i, 14,mStr);
				}else if(str1.length() >=3 && str1.length() <6){
					String mStr = str1.substring(0, str1.length() - 3) + "***";
					dt.set(i, 14,mStr);
				}else{
					
				}
				
				dt.set(i, 17,toConcealUtil.toConceal((String)dt.get(i, 17),4));
			}
		}
		

		// 健康险拆分 为 长期 短期
		DataTable dtfmrisk = new QueryBuilder("SELECT riskcode,riskPeriod,CASE WHEN riskPeriod='L' THEN '长期' ELSE '短期' END AS riskPeriodValue  FROM fmrisk ").executeDataTable();
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
				
				// 将健康险拆分为 长期、短期
				String str1 = (String)dt.get(i, "risktype");
				
				if(StringUtil.isNotEmpty(str1) && "健康险".equals(str1)){
					dt.set(i, "risktype",dtfmriskmap.get(productid).toString()+str1);
				}
				
			}
		}else{
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 将健康险拆分为 长期、短期
				String str1 = (String)dt.get(i, "risktype");
				String productid = (String)dt.get(i, "productid");
				if(StringUtil.isNotEmpty(str1) && "健康险".equals(str1)){
					dt.set(i, "risktype",dtfmriskmap.get(productid).toString()+str1);
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
		String orderSn = dga.getParam("orderSn");
		String productName = dga.getParam("productName");
		String company = dga.getParam("company");
		String applicantName = dga.getParam("applicantName");
		String applicantMobile = dga.getParam("applicantMobile");
		String applicantMail = dga.getParam("applicantMail");
		String applicantIdentityId = dga.getParam("applicantIdentityId");
		//if("all".equals(channelsn)) channelsn="";
		// 撤单标识  1:撤单  
		String cancelFlag = dga.getParam("cancelFlag");
		// 投保时间
		String insureDateStart = dga.getParam("insureDateStart");
		String insureDateEnd = dga.getParam("insureDateEnd");
		
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
		if (StringUtil.isNotEmpty(orderSn)) {
			parameter = parameter +" and s.id ='" + orderSn + "'";
		}
		if (StringUtil.isNotEmpty(productName)) {
			parameter = parameter +" and s.productname ='" + productName + "'";
		}
		if (StringUtil.isNotEmpty(company)) {
			parameter = parameter +" and s.InsuranceCompanySn ='" + company + "'";
		}
		if (StringUtil.isNotEmpty(applicantName)) {
			parameter = parameter +" and app.applicantName ='" + applicantName + "'";
		}
		if (StringUtil.isNotEmpty(applicantMobile)) {
			parameter = parameter +" and app.applicantMobile ='" + applicantMobile + "'";
		}
		if (StringUtil.isNotEmpty(applicantMail)) {
			parameter = parameter +" and app.applicantMail ='" + applicantMail + "'";
		}
		if (StringUtil.isNotEmpty(applicantIdentityId)) {
			parameter = parameter +" and app.applicantIdentityId ='" + applicantIdentityId + "'";
		}
		if (StringUtil.isNotEmpty(insureDateStart)) {
			parameter = parameter +" and DATE_FORMAT(re.currentDate,'%Y-%m-%d') >= '" + insureDateStart + "'";
		}
		if (StringUtil.isNotEmpty(insureDateEnd)) {
			parameter = parameter +" and DATE_FORMAT(re.currentDate,'%Y-%m-%d') <= '" + insureDateEnd + "'";
		}
		StringBuffer sql = new StringBuffer();
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT s.ChannelSn AS ChannelSn, '' AS MemberID,re.policyNo,s.id AS orderSn,s.productname AS productName,s.Prop3 AS ensureDisplay,");
			sql.append(" app.applicantName,app.applicantIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=app.applicantSex) AS applicantSexName,app.applicantBirthday,app.applicantMobile,app.applicantMail,");
			sql.append(" (SELECT max(codeName) FROM dictionary WHERE codetype='RelationShip' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.RelationShip) AS recognizeeAppntRelationName,");
			sql.append(" ins.recognizeeName,ins.recognizeeIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.recognizeeSex) AS recognizeeSexName,ins.recognizeeBirthday,ins.recognizeeMobile,ins.recognizeeMail, ");
			sql.append(" '' AS Occu,s.Destination destinationCountryText,om.Prop3 AS payPrice,FORMAT(om.PaymentFee,2) AS totalAmount, ");
			sql.append(" tf.receiveDate AS modifyDate, ");
			sql.append(" DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, DATE_FORMAT(s.Fail,'%Y-%m-%d') AS endDate,re.cancelDate cancelDate,s.InsuranceCompany, ");
			sql.append(" (select codename from zdcode where CodeType='QueryProductType' and CodeValue=SUBSTRING(e.EdorFlag,1,1)) as risktype, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=re.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=s.OrderStatus) AS orderStatusName, s.Prop2 AS paySn ");
			sql.append(" ,re.currentDate as insureDate, '' AS lastAppTime, '' AS firstAppTime, '' AS nextToLastAppTime ,'' AS chargeYearName,e.riskcode as productid ");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om, ");
			sql.append(" sdappnt app,sdinsured ins ");
			sql.append(" WHERE s.orderstatus IN ('2','9','10','13','14') AND s.id = tf.ordID AND re.orderSn = s.id  AND s.productid = e.RiskCode ");
			sql.append(" AND om.prop1 = re.id ");
			sql.append(" AND s.appntId = app.id AND ins.ordersn = s.id AND ins.insuredId = om.insuredid ");
			sql.append(parameter);
		} else if("1".equals(cancelFlag)){
			sql.append(" SELECT s.ChannelSn AS ChannelSn,'' AS MemberID,re.policyNo,s.id AS orderSn, ");
			sql.append(" s.productname AS productName,re.cancelDate cancelDate, ");
			sql.append(" FORMAT(om.PaymentFee,2) AS totalAmount, ");
			sql.append(" tf.receiveDate AS modifyDate,s.Prop3 AS ensureDisplay, ");
			sql.append(" app.applicantName,app.applicantIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=app.applicantSex) AS applicantSexName,app.applicantBirthday,app.applicantMobile,app.applicantMail, ");
			sql.append(" (SELECT max(codeName) FROM dictionary WHERE codetype='RelationShip' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.RelationShip) AS recognizeeAppntRelationName,");
			sql.append(" ins.recognizeeName,ins.recognizeeIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.recognizeeSex) AS recognizeeSexName,ins.recognizeeBirthday,ins.recognizeeMobile,ins.recognizeeMail, ");
			sql.append(" '' AS Occu,s.Destination destinationCountryText,om.Prop3 AS payPrice,DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, DATE_FORMAT(s.Fail,'%Y-%m-%d') AS endDate,s.InsuranceCompany, ");
			sql.append(" (select codename from zdcode where CodeType='QueryProductType' and CodeValue=SUBSTRING(e.EdorFlag,1,1)) as risktype, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=re.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=s.OrderStatus) AS orderStatusName, s.Prop2 AS paySn ");
			sql.append(" ,re.currentDate as insureDate, '' AS lastAppTime, '' AS firstAppTime, '' AS nextToLastAppTime ,'' AS chargeYearName,e.riskcode as productid ");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om  ");
			sql.append(" ,sdappnt app,sdinsured ins ");
			sql.append(" WHERE s.id = tf.ordID AND re.orderSn = s.id ");
			sql.append(" AND s.productid = e.RiskCode AND om.prop1 = re.id ");
			sql.append(" AND re.appStatus >=2 AND s.prop2 NOT LIKE 'BG%'  AND (re.changeStatus IS NULL OR re.changeStatus='') ");
			sql.append(" AND s.appntId = app.id AND ins.ordersn = s.id AND ins.insuredId = om.insuredid ");
			sql.append(parameter);
			sql.append(" UNION ALL ");
			sql.append(" SELECT s.ChannelSn AS ChannelSn,'' AS MemberID,re.policyNo,s.id AS orderSn, ");
			sql.append(" s.productname AS productName,re.cancelDate cancelDate, ");
			sql.append(" FORMAT(om.PaymentFee,2) AS totalAmount, ");
			sql.append(" tf.receiveDate AS modifyDate,s.Prop3 AS ensureDisplay, ");
			sql.append(" app.applicantName,app.applicantIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=app.applicantSex) AS applicantSexName,app.applicantBirthday,app.applicantMobile,app.applicantMail, ");
			sql.append(" (SELECT max(codeName) FROM dictionary WHERE codetype='RelationShip' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.RelationShip) AS recognizeeAppntRelationName,");
			sql.append(" ins.recognizeeName,ins.recognizeeIdentityId,(SELECT codeName FROM dictionary WHERE codetype='Sex' AND InsuranceCode=s.InsuranceCompanySn AND CodeValue=ins.recognizeeSex) AS recognizeeSexName,ins.recognizeeBirthday,ins.recognizeeMobile,ins.recognizeeMail, ");
			sql.append(" '' AS Occu,s.Destination destinationCountryText,om.Prop3 AS payPrice,DATE_FORMAT(s.effective,'%Y-%m-%d') AS startDate, DATE_FORMAT(s.Fail,'%Y-%m-%d') AS endDate,s.InsuranceCompany, ");
			sql.append(" (select codename from zdcode where CodeType='QueryProductType' and CodeValue=SUBSTRING(e.EdorFlag,1,1)) as risktype, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='AppStatus' and CodeValue=re.appStatus) AS appStatusName, ");
			sql.append(" (SELECT CodeName FROM zdcode WHERE CodeType='orderstatus' and CodeValue=s.OrderStatus) AS orderStatusName, s.Prop2 AS paySn ");
			sql.append(" ,re.currentDate as insureDate, '' AS lastAppTime, '' AS firstAppTime, '' AS nextToLastAppTime ,'' AS chargeYearName,e.riskcode as productid ");
			sql.append(" FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdtradeinformation tf,sdinsuredcompanyreturndata re,sdproduct e,sdorderitem om  ");
			sql.append(" ,sdappnt app,sdinsured ins ");
			sql.append(" WHERE s.id = tf.ordID AND p.os= s.id AND re.orderSn = s.id ");
			sql.append(" AND s.productid = e.RiskCode AND om.prop1 = re.id  ");
			sql.append(" AND re.appStatus >=2 AND s.prop2 LIKE 'BG%' ");
			sql.append(" AND s.appntId = app.id AND ins.ordersn = s.id AND ins.insuredId = om.insuredid ");
			sql.append(parameter);
		}

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable("B2B");
		for (DataRow dr : dt) {
			dr.set("ChannelSn",String.valueOf(new QueryBuilder(" SELECT ChannelName FROM channelinfo WHERE ChannelCode=? ",dr.getString("ChannelSn")).executeString()));
			dr.set("ensureDisplay",String.valueOf(dr.getString("ensureDisplay")).replaceAll("D", "天").replaceAll("M", "月").replaceAll("Y", "年"));
		}
	   return dt;
		
	}
	/**
	 * 会员回购统计数据
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate")+" 23:59:59";
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}
		
		String parameter = "";
		
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
		}
		//回购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(DISTINCT a.orderSn) orderSn,COUNT(DISTINCT a.memberid) memberID,FORMAT(IFNULL(SUM(sdor.timePrem),'0'),2) totalAmount ");
		sql.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
		sql.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
		sql.append(" AND a.channelsn!='b2b_app' "); 
		sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
		/*sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
		sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
		sql.append(" AND NOT EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
		sql.append(" AND NOT EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");*/
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(parameter);
		sql.append(" AND a.memberid IN ( ");
		sql.append(" SELECT a.memberid ");
		sql.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
		sql.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
		sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
		sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
		sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
		sql.append(" AND NOT EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
		sql.append(" AND NOT EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
		sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql.append(parameter);
		sql.append(")");
		
		
		
		
		//订单数据
		StringBuffer sql1 = new StringBuffer(); 
		sql1.append(" SELECT COUNT(DISTINCT a.orderSn) orderSn,COUNT(DISTINCT a.memberid) memberID,FORMAT(IFNULL(SUM(sdor.timePrem),'0.00'),2) totalAmount ");
		sql1.append(" FROM SDOrderItem f ,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid  ");
		sql1.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
		sql1.append(" AND a.channelsn!='b2b_app' "); 
		sql1.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) "); 
		sql1.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
		sql1.append(parameter);

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		QueryBuilder qb1 = new QueryBuilder(sql1.toString());
		DataTable dt1 = qb1.executeDataTable();
		String memberCount = computePercent(dt.getString(0, "memberID"),dt1.getString(0, "memberID"));
		String orderCount = computePercent(dt.getString(0, "orderSn"),dt1.getString(0, "orderSn"));
		String amountCount = computePercent(dt.getString(0, "totalAmount"),dt1.getString(0, "totalAmount"));
		
		DataTable dt2 = new DataTable();
		Object[] catalogRow = {"回购", dt.getString(0, "memberID"), dt.getString(0, "orderSn"),dt.getString(0, "totalAmount"),"0"};
		Object[] catalogRow1 = {"主站", dt1.getString(0, "memberID"), dt1.getString(0, "orderSn"),dt1.getString(0, "totalAmount"),"1"};
		Object[] catalogRow2 = {"回购率", memberCount, orderCount,amountCount,"2"};
		dt2.insertRow(catalogRow,0);
		dt2.insertRow(catalogRow1,1);
		dt2.insertRow(catalogRow2,2);
		//回购率计算
		dga.bindData(dt2);
	}
	/**
	 * 计算y在z中所占百分比
	 * @param y
	 * @param z
	 * @return 百分比的值
	 */
	private String computePercent(String a, String b) {

		BigDecimal y = new BigDecimal(a.replaceAll(",", ""));
		BigDecimal z = new BigDecimal(b.replaceAll(",", ""));
		if (y.compareTo(new BigDecimal("0.00")) == 0) {
			return "-";
		} else {
			// 接受百分比的值
			String baifenbi = "";
			BigDecimal baiy = y.multiply(new BigDecimal("1.00"));
			BigDecimal baiz = z.multiply(new BigDecimal("1.00"));
			BigDecimal fen = baiy.divide(baiz,5,BigDecimal.ROUND_HALF_UP);
			// 00.00% 百分比格式，后面不足2位的用0补齐
			DecimalFormat df1 = new DecimalFormat("00.00%");
			baifenbi = df1.format(fen);
			return baifenbi;
		}
	}
	/*
	 * 获取保险种类的下拉菜单
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
}
