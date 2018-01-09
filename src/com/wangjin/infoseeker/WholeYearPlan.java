package com.wangjin.infoseeker;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class WholeYearPlan extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	public static String b2bresource = "B2B";
	
	
	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initStaff(Mapx params) {
		Mapx map = new Mapx();
		map.put("today", today);
		return map;
	}

	/**
	 * 获得主站业绩总量统计的数据
	 * 
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings({ "rawtypes" })
	public JasperPrint dg1DataBind(Map param) throws JRException {
		
		//生成业务数据
		Map parameters = dealReport(param);
		ArrayList reportList = new ArrayList();
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/WholeYearPlanReport.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map dealReport(Map param){
		
		// 获得统计的年份
		String date = (String)param.get("startDate");
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		if(month.startsWith("0")){
			month = month.replaceAll("0", "");
		}
		String wherePart1= " AND tf.receiveDate >= '"+date+"'  AND tf.receiveDate <= '"+date+" 23:59:59'";
		String wherePart2= " AND tf.receiveDate >= '"+date.substring(0,7)+"-01'  AND tf.receiveDate <= '"+date.substring(0,7)+"-31 23:59:59'";
		String wherePart3= " AND tf.receiveDate like '"+year+"%' ";
		
		String channelwj = QueryUtil.getChannelInfo("wj","");
		String parameter1 = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channelwj+"))";
		//主站
		//String parameter1 = " AND a.channelsn='wj' AND  NOT  EXISTS (SELECT  `on`  FROM  cps  WHERE `on` = a.ordersn )";
		//淘宝
		//String parameter2 = " and a.channelsn = 'tb' ";
		String channeltb = QueryUtil.getChannelInfo("tb","");
		String parameter2 = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channeltb+"))";
		
		//分销
		//String parameter3 = " AND (a.channelsn='cps' OR (a.channelsn='wj' AND EXISTS (SELECT  `on`  FROM  cps  WHERE `on` = a.ordersn )))";
		String channelcps = QueryUtil.getChannelInfo("cps","");
		String parameter3 = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channelcps+"))";
		
		//wap
		//String parameter4 = " and a.channelsn = 'wap' ";
		String channelwap = QueryUtil.getChannelInfo("wap","");
		String parameter4 = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channelwap+"))";
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c.ProductGenera AS productGenera ,ROUND(SUM(sdor.timePrem),2) AS totalAmount,");
		sql.append(" ROUND(SUM(sdor.timePrem*c.feerate),2) AS charge  ");
		sql.append(" FROM  sdinformation b, sdproduct c,sdinformationrisktype sdor  LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn ");
		sql.append(" LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid  ");
		sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID  AND a.orderstatus >=7 AND a.orderstatus <> '8' ");
		sql.append(" AND (appstatus <2 OR appstatus IS NULL OR appstatus='')  ");
		
		String groupPart = " GROUP BY c.producttype";
		
		//旅行保
		String sqlB = "SELECT (SELECT CASE WHEN SUBSTRING(EdorFlag, 1, 1) = 'A' THEN '旅游险' "
				+ " WHEN SUBSTRING(EdorFlag, 1, 1) = 'B' THEN '意外险' END  FROM DUAL) AS ProductGenera,  "
				+ " ROUND(SUM(om.PaymentFee), 2) AS totalAmount,ROUND(SUM((om.PaymentFee * e.rate)), 2) AS charge"
				+ " FROM sdorders s,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om "
				+ " WHERE s.productid = e.RiskCode AND re.orderSn = s.id  AND om.prop1 = re.id AND s.id = tf.ordID "
				+ " AND s.orderstatus IN ('2', '9','10','13','14') AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='') ";
		String groupB = " GROUP BY ProductGenera";
		//主站
		QueryBuilder qb = new QueryBuilder(sql.toString()+wherePart1+parameter1+groupPart);
		QueryBuilder qb1 = new QueryBuilder(sql.toString()+wherePart2+parameter1+groupPart);
		QueryBuilder qb2 = new QueryBuilder(sql.toString()+wherePart3+parameter1+groupPart);
		DataTable dt = qb.executeDataTable();
		DataTable dt1 = qb1.executeDataTable();
		DataTable dt2 = qb2.executeDataTable();
		//淘宝
		QueryBuilder qb3 = new QueryBuilder(sql.toString()+wherePart1+parameter2+groupPart);
		QueryBuilder qb4 = new QueryBuilder(sql.toString()+wherePart2+parameter2+groupPart);
		QueryBuilder qb5 = new QueryBuilder(sql.toString()+wherePart3+parameter2+groupPart);
		DataTable dt3 = qb3.executeDataTable();
		DataTable dt4 = qb4.executeDataTable();
		DataTable dt5 = qb5.executeDataTable();
		//旅行保
		QueryBuilder qb6 = new QueryBuilder(sqlB.toString()+wherePart1+groupB);
		QueryBuilder qb7 = new QueryBuilder(sqlB.toString()+wherePart2+groupB);
		QueryBuilder qb8 = new QueryBuilder(sqlB.toString()+wherePart3+groupB);
		DataTable dt6 = qb6.executeDataTable(b2bresource);
		DataTable dt7 = qb7.executeDataTable(b2bresource);
		DataTable dt8 = qb8.executeDataTable(b2bresource);
		//分销
		QueryBuilder qb9 = new QueryBuilder(sql.toString()+wherePart1+parameter3+groupPart);
		QueryBuilder qb10 = new QueryBuilder(sql.toString()+wherePart2+parameter3+groupPart);
		QueryBuilder qb11 = new QueryBuilder(sql.toString()+wherePart3+parameter3+groupPart);
		DataTable dt9 = qb9.executeDataTable();
		DataTable dt10 = qb10.executeDataTable();
		DataTable dt11 = qb11.executeDataTable();
		
		//wap
		QueryBuilder qb12 = new QueryBuilder(sql.toString()+wherePart1+parameter4+groupPart);
		QueryBuilder qb13 = new QueryBuilder(sql.toString()+wherePart2+parameter4+groupPart);
		QueryBuilder qb14 = new QueryBuilder(sql.toString()+wherePart3+parameter4+groupPart);
		DataTable dt12 = qb12.executeDataTable();
		DataTable dt13 = qb13.executeDataTable();
		DataTable dt14 = qb14.executeDataTable();
		
		Map parameters = new HashMap();
		//当日
		
		//主站
		int row = dt.getRowCount();
		for(int i=0;i<row;i++){
			if("旅游险".equals(dt.getString(i, "productGenera"))){
				parameters.put("totalD1", toValidate(dt.getString(i, "totalAmount")));//主站旅游险当日保费
				parameters.put("chargeD1", toValidate(dt.getString(i, "charge")));//主站旅游险当日手续费
			}else if("意外险".equals(dt.getString(i, "productGenera"))){
				parameters.put("totalD2", toValidate(dt.getString(i, "totalAmount")));//主站意外险当日保费
				parameters.put("chargeD2", toValidate(dt.getString(i, "charge")));//主站意外险当日手续费
			}else if("健康险".equals(dt.getString(i, "productGenera"))){
				parameters.put("totalD3", toValidate(dt.getString(i, "totalAmount")));//主站健康险当日保费
				parameters.put("chargeD3", toValidate(dt.getString(i, "charge")));//主站健康险当日手续费
			}/*else if("车险".equals(dt.getString(i, "productGenera"))){
				parameters.put("totalD4", toValidate(dt.getString(i, "totalAmount")));
				parameters.put("chargeD4", toValidate(dt.getString(i, "charge")));
			}*/else{
				parameters.put("totalD5", toValidate(dt.getString(i, "totalAmount")));//主站其他险类当日保费
				parameters.put("chargeD5",toValidate(dt.getString(i, "charge")));//主站其他险类当日手续费
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalD1"))){
			parameters.put("totalD1", toValidate("0.00"));
			parameters.put("chargeD1", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD2"))){
			parameters.put("totalD2", toValidate("0.00"));
			parameters.put("chargeD2", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD3"))){
			parameters.put("totalD3", toValidate("0.00"));
			parameters.put("chargeD3", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD4"))){
			parameters.put("totalD4", toValidate("0.00"));
			parameters.put("chargeD4", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD5"))){
			parameters.put("totalD5", toValidate("0.00"));
			parameters.put("chargeD5", toValidate("0.00"));
		}
		
		// 淘宝
		int row3 = dt3.getRowCount();
		for (int i = 0; i < row3; i++) {
			if ("旅游险".equals(dt3.getString(i, "productGenera"))) {
				parameters.put("totalD6", toValidate(dt3.getString(i, "totalAmount")));
				parameters.put("chargeD6", toValidate(dt3.getString(i, "charge")));
			} else if ("意外险".equals(dt3.getString(i, "productGenera"))) {
				parameters.put("totalD7", toValidate(dt3.getString(i, "totalAmount")));
				parameters.put("chargeD7", toValidate(dt3.getString(i, "charge")));
			} else if ("健康险".equals(dt3.getString(i, "productGenera"))) {
				parameters.put("totalD8", toValidate(dt3.getString(i, "totalAmount")));
				parameters.put("chargeD8", toValidate(dt3.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalD6"))){
			parameters.put("totalD6", toValidate("0.00"));
			parameters.put("chargeD6", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD7"))){
			parameters.put("totalD7", toValidate("0.00"));
			parameters.put("chargeD7", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD8"))){
			parameters.put("totalD8", toValidate("0.00"));
			parameters.put("chargeD8", toValidate("0.00"));
		}
		
		// 旅行保
		int row6 = dt6.getRowCount();
		for (int i = 0; i < row6; i++) {
			if ("旅游险".equals(dt6.getString(i, "productGenera"))) {
				parameters.put("totalD9", toValidate(dt6.getString(i, "totalAmount")));
				parameters.put("chargeD9", toValidate(dt6.getString(i, "charge")));
			}
			if ("意外险".equals(dt6.getString(i, "productGenera"))) {
				parameters.put("totalD13", toValidate(dt6.getString(i, "totalAmount")));
				parameters.put("chargeD13", toValidate(dt6.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalD9"))){
			parameters.put("totalD9", toValidate("0.00"));
			parameters.put("chargeD9", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD13"))){
			parameters.put("totalD13", toValidate("0.00"));
			parameters.put("chargeD13", toValidate("0.00"));
		}
		
		// 分销
		int row9 = dt9.getRowCount();
		for (int i = 0; i < row9; i++) {
			if ("旅游险".equals(dt9.getString(i, "productGenera"))) {
				parameters.put("totalD10", toValidate(dt9.getString(i, "totalAmount")));
				parameters.put("chargeD10", toValidate(dt9.getString(i, "charge")));
			} else if ("意外险".equals(dt9.getString(i, "productGenera"))) {
				parameters.put("totalD11", toValidate(dt9.getString(i, "totalAmount")));
				parameters.put("chargeD11", toValidate(dt9.getString(i, "charge")));
			} else if ("健康险".equals(dt9.getString(i, "productGenera"))) {
				parameters.put("totalD12", toValidate(dt9.getString(i, "totalAmount")));
				parameters.put("chargeD12", toValidate(dt9.getString(i, "charge")));
			}/* else if ("车险".equals(dt9.getString(i, "productGenera"))) {
				parameters.put("totalD13", toValidate(dt9.getString(i, "totalAmount")));
				parameters.put("chargeD13", toValidate(dt9.getString(i, "charge")));
			} */
		}
		
		if(StringUtil.isEmpty(parameters.get("totalD10"))){
			parameters.put("totalD10", toValidate("0.00"));
			parameters.put("chargeD10", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD11"))){
			parameters.put("totalD11", toValidate("0.00"));
			parameters.put("chargeD11", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD12"))){
			parameters.put("totalD12", toValidate("0.00"));
			parameters.put("chargeD12", toValidate("0.00"));
		}
		/*if(StringUtil.isEmpty(parameters.get("totalD13"))){
			parameters.put("totalD13", toValidate("0.00"));
			parameters.put("chargeD13", toValidate("0.00"));
		}*/
		
		// wap
		int row12 = dt12.getRowCount();
		for (int i = 0; i < row12; i++) {
			if ("旅游险".equals(dt12.getString(i, "productGenera"))) {
				parameters.put("totalD14", toValidate(dt12.getString(i, "totalAmount")));
				parameters.put("chargeD14", toValidate(dt12.getString(i, "charge")));
			} else if ("意外险".equals(dt12.getString(i, "productGenera"))) {
				parameters.put("totalD15", toValidate(dt12.getString(i, "totalAmount")));
				parameters.put("chargeD15", toValidate(dt12.getString(i, "charge")));
			} else if ("健康险".equals(dt12.getString(i, "productGenera"))) {
				parameters.put("totalD4", toValidate(dt12.getString(i, "totalAmount")));
				parameters.put("chargeD4", toValidate(dt12.getString(i, "charge")));
			} 
		}
		
		if(StringUtil.isEmpty(parameters.get("totalD14"))){
			parameters.put("totalD14", toValidate("0.00"));
			parameters.put("chargeD14", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD15"))){
			parameters.put("totalD15", toValidate("0.00"));
			parameters.put("chargeD15", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalD4"))){
			parameters.put("totalD4", toValidate("0.00"));
			parameters.put("chargeD4", toValidate("0.00"));
		}
		
		//当月
				
		//主站
		int row1 = dt1.getRowCount();
		for(int j=0;j<row1;j++){
			if("旅游险".equals(dt1.getString(j, "productGenera"))){
				parameters.put("totalM1", toValidate(dt1.getString(j, "totalAmount")));
				parameters.put("chargeM1", toValidate(dt1.getString(j, "charge")));
			}else if("意外险".equals(dt1.getString(j, "productGenera"))){
				parameters.put("totalM2", toValidate(dt1.getString(j, "totalAmount")));
				parameters.put("chargeM2", toValidate(dt1.getString(j, "charge")));
			}else if("健康险".equals(dt1.getString(j, "productGenera"))){
				parameters.put("totalM3", toValidate(dt1.getString(j, "totalAmount")));
				parameters.put("chargeM3", toValidate(dt1.getString(j, "charge")));
			}else{
				parameters.put("totalM5", toValidate(dt1.getString(j, "totalAmount")));
				parameters.put("chargeM5", toValidate(dt1.getString(j, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalM1"))){
			parameters.put("totalM1", toValidate("0.00"));
			parameters.put("chargeM1", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM2"))){
			parameters.put("totalM2", toValidate("0.00"));
			parameters.put("chargeM2", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM3"))){
			parameters.put("totalM3", toValidate("0.00"));
			parameters.put("chargeM3", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM5"))){
			parameters.put("totalM5", toValidate("0.00"));
			parameters.put("chargeM5", toValidate("0.00"));
		}
		
		// 淘宝
		int row4 = dt4.getRowCount();
		for (int i = 0; i < row4; i++) {
			if ("旅游险".equals(dt4.getString(i, "productGenera"))) {
				parameters.put("totalM6", toValidate(dt4.getString(i, "totalAmount")));
				parameters.put("chargeM6", toValidate(dt4.getString(i, "charge")));
			} else if ("意外险".equals(dt4.getString(i, "productGenera"))) {
				parameters.put("totalM7", toValidate(dt4.getString(i, "totalAmount")));
				parameters.put("chargeM7",  toValidate(dt4.getString(i, "charge")));
			} else if ("健康险".equals(dt4.getString(i, "productGenera"))) {
				parameters.put("totalM8", toValidate(dt4.getString(i, "totalAmount")));
				parameters.put("chargeM8",  toValidate(dt4.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalM6"))){
			parameters.put("totalM6", toValidate("0.00"));
			parameters.put("chargeM6", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM7"))){
			parameters.put("totalM7", toValidate("0.00"));
			parameters.put("chargeM7", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM8"))){
			parameters.put("totalM8", toValidate("0.00"));
			parameters.put("chargeM8", toValidate("0.00"));
		}

		// 旅行保
		int row7 = dt7.getRowCount();
		for (int i = 0; i < row7; i++) {
			if ("旅游险".equals(dt7.getString(i, "productGenera"))) {
				parameters.put("totalM9", toValidate(dt7.getString(i, "totalAmount")));
				parameters.put("chargeM9",  toValidate(dt7.getString(i, "charge")));
			}else if ("意外险".equals(dt7.getString(i, "productGenera"))) {
				parameters.put("totalM13", toValidate(dt7.getString(i, "totalAmount")));
				parameters.put("chargeM13",  toValidate(dt7.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalM9"))){
			parameters.put("totalM9", toValidate("0.00"));
			parameters.put("chargeM9", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM13"))){
			parameters.put("totalM13", toValidate("0.00"));
			parameters.put("chargeM13", toValidate("0.00"));
		}

		
		// 分销
		int row10 = dt10.getRowCount();
		for (int i = 0; i < row10; i++) {
			if ("旅游险".equals(dt10.getString(i, "productGenera"))) {
				parameters.put("totalM10", toValidate(dt10.getString(i, "totalAmount")));
				parameters.put("chargeM10", toValidate(dt10.getString(i, "charge")));
			} else if ("意外险".equals(dt10.getString(i, "productGenera"))) {
				parameters.put("totalM11",toValidate(dt10.getString(i, "totalAmount")));
				parameters.put("chargeM11", toValidate(dt10.getString(i, "charge")));
			} else if ("健康险".equals(dt10.getString(i, "productGenera"))) {
				parameters.put("totalM12", toValidate(dt10.getString(i, "totalAmount")));
				parameters.put("chargeM12", toValidate(dt10.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalM10"))){
			parameters.put("totalM10", toValidate("0.00"));
			parameters.put("chargeM10", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM11"))){
			parameters.put("totalM11", toValidate("0.00"));
			parameters.put("chargeM11", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM12"))){
			parameters.put("totalM12", toValidate("0.00"));
			parameters.put("chargeM12", toValidate("0.00"));
		}
		
		// wap
		int row13 = dt13.getRowCount();
		for (int i = 0; i < row13; i++) {
			if ("旅游险".equals(dt13.getString(i, "productGenera"))) {
				parameters.put("totalM14", toValidate(dt13.getString(i, "totalAmount")));
				parameters.put("chargeM14", toValidate(dt13.getString(i, "charge")));
			} else if ("意外险".equals(dt13.getString(i, "productGenera"))) {
				parameters.put("totalM15", toValidate(dt13.getString(i, "totalAmount")));
				parameters.put("chargeM15", toValidate(dt13.getString(i, "charge")));
			} else if ("健康险".equals(dt13.getString(i, "productGenera"))) {
				parameters.put("totalM4", toValidate(dt13.getString(i, "totalAmount")));
				parameters.put("chargeM4", toValidate(dt13.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalM14"))){
			parameters.put("totalM14", toValidate("0.00"));
			parameters.put("chargeM14", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM15"))){
			parameters.put("totalM15", toValidate("0.00"));
			parameters.put("chargeM15", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalM4"))){
			parameters.put("totalM4", toValidate("0.00"));
			parameters.put("chargeM4", toValidate("0.00"));
		}
		//当年
		
		//主站
		int row2 = dt2.getRowCount();
		for(int k=0;k<row2;k++){
			if("旅游险".equals(dt2.getString(k, "productGenera"))){
				parameters.put("totalY1", toValidate(dt2.getString(k, "totalAmount")));
				parameters.put("chargeY1", toValidate(dt2.getString(k, "charge")));
			}else if("意外险".equals(dt2.getString(k, "productGenera"))){
				parameters.put("totalY2", toValidate(dt2.getString(k, "totalAmount")));
				parameters.put("chargeY2", toValidate(dt2.getString(k, "charge")));
			}else if("健康险".equals(dt2.getString(k, "productGenera"))){
				parameters.put("totalY3", toValidate(dt2.getString(k, "totalAmount")));
				parameters.put("chargeY3", toValidate(dt2.getString(k, "charge")));
			}/*else if("车险".equals(dt2.getString(k, "productGenera"))){
				parameters.put("totalY4", toValidate(dt2.getString(k, "totalAmount")));
				parameters.put("chargeY4", toValidate(dt2.getString(k, "charge")));
			}*/else{
				parameters.put("totalY5", toValidate(dt2.getString(k, "totalAmount")));
				parameters.put("chargeY5", toValidate(dt2.getString(k, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalY1"))){
			parameters.put("totalY1", toValidate("0.00"));
			parameters.put("chargeY1", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY2"))){
			parameters.put("totalY2", toValidate("0.00"));
			parameters.put("chargeY2", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY3"))){
			parameters.put("totalY3", toValidate("0.00"));
			parameters.put("chargeY3", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY5"))){
			parameters.put("totalY5", toValidate("0.00"));
			parameters.put("chargeY5", toValidate("0.00"));
		}
		
		// 淘宝
		int row5 = dt5.getRowCount();
		for (int i = 0; i < row5; i++) {
			if ("旅游险".equals(dt5.getString(i, "productGenera"))) {
				parameters.put("totalY6", toValidate(dt5.getString(i, "totalAmount")));
				parameters.put("chargeY6", toValidate(dt5.getString(i, "charge")));
			} else if ("意外险".equals(dt5.getString(i, "productGenera"))) {
				parameters.put("totalY7", toValidate(dt5.getString(i, "totalAmount")));
				parameters.put("chargeY7", toValidate(dt5.getString(i, "charge")));
			} else if ("健康险".equals(dt5.getString(i, "productGenera"))) {
				parameters.put("totalY8", toValidate(dt5.getString(i, "totalAmount")));
				parameters.put("chargeY8", toValidate(dt5.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalY6"))){
			parameters.put("totalY6", toValidate("0.00"));
			parameters.put("chargeY6", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY7"))){
			parameters.put("totalY7", toValidate("0.00"));
			parameters.put("chargeY7", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY8"))){
			parameters.put("totalY8", toValidate("0.00"));
			parameters.put("chargeY8", toValidate("0.00"));
		}
		
		// 旅行保
		int row8 = dt8.getRowCount();
		for (int i = 0; i < row8; i++) {
			if ("旅游险".equals(dt8.getString(i, "productGenera"))) {
				parameters.put("totalY9", toValidate(dt8.getString(i, "totalAmount")));
				parameters.put("chargeY9", toValidate(dt8.getString(i, "charge")));
			}else if ("意外险".equals(dt8.getString(i, "productGenera"))) {
				parameters.put("totalY13", toValidate(dt8.getString(i, "totalAmount")));
				parameters.put("chargeY13", toValidate(dt8.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalY9"))){
			parameters.put("totalY9", toValidate("0.00"));
			parameters.put("chargeY9", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY13"))){
			parameters.put("totalY13", toValidate("0.00"));
			parameters.put("chargeY13", toValidate("0.00"));
		}
		// 分销
		int row11 = dt11.getRowCount();
		for (int i = 0; i < row11; i++) {
			if ("旅游险".equals(dt11.getString(i, "productGenera"))) {
				parameters.put("totalY10", toValidate(dt11.getString(i, "totalAmount")));
				parameters.put("chargeY10", toValidate(dt11.getString(i, "charge")));
			} else if ("意外险".equals(dt11.getString(i, "productGenera"))) {
				parameters.put("totalY11", toValidate(dt11.getString(i, "totalAmount")));
				parameters.put("chargeY11", toValidate(dt11.getString(i, "charge")));
			} else if ("健康险".equals(dt11.getString(i, "productGenera"))) {
				parameters.put("totalY12", toValidate(dt11.getString(i, "totalAmount")));
				parameters.put("chargeY12", toValidate(dt11.getString(i, "charge")));
			} 
		}
		
		if(StringUtil.isEmpty(parameters.get("totalY10"))){
			parameters.put("totalY10", toValidate("0.00"));
			parameters.put("chargeY10", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY11"))){
			parameters.put("totalY11", toValidate("0.00"));
			parameters.put("chargeY11", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY12"))){
			parameters.put("totalY12", toValidate("0.00"));
			parameters.put("chargeY12", toValidate("0.00"));
		}
		
		// wap
		int row14 = dt14.getRowCount();
		for (int i = 0; i < row14; i++) {
			if ("旅游险".equals(dt14.getString(i, "productGenera"))) {
				parameters.put("totalY14", toValidate(dt14.getString(i, "totalAmount")));
				parameters.put("chargeY14", toValidate(dt14.getString(i, "charge")));
			} else if ("意外险".equals(dt14.getString(i, "productGenera"))) {
				parameters.put("totalY15", toValidate(dt14.getString(i, "totalAmount")));
				parameters.put("chargeY15", toValidate(dt14.getString(i, "charge")));
			} else if ("健康险".equals(dt14.getString(i, "productGenera"))) {
				parameters.put("totalY4", toValidate(dt14.getString(i, "totalAmount")));
				parameters.put("chargeY4", toValidate(dt14.getString(i, "charge")));
			}
		}
		
		if(StringUtil.isEmpty(parameters.get("totalY14"))){
			parameters.put("totalY14", toValidate("0.00"));
			parameters.put("chargeY14", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY15"))){
			parameters.put("totalY15", toValidate("0.00"));
			parameters.put("chargeY15", toValidate("0.00"));
		}
		if(StringUtil.isEmpty(parameters.get("totalY4"))){
			parameters.put("totalY4", toValidate("0.00"));
			parameters.put("chargeY4", toValidate("0.00"));
		}
		
		//计划保费、手续费
		QueryBuilder planQB = new QueryBuilder(" SELECT * FROM PlanAmountInfo WHERE PlanYear=? ",year);
		DataTable planDT = planQB.executeDataTable();
		BigDecimal zero = new BigDecimal("0.00");
		String allPremPC = "0.00";//PC累计保费
		String allPremWap = "0.00";//wap累计保费
		String allFeePC = "0.00";//PC累计手续费
		String allFeeWap = "0.00";//wap累计手续费
		if(planDT!=null && planDT.getRowCount()>=1){
			int len = planDT.getRowCount();
			StringBuffer sum_p = new StringBuffer("SUM(");
			StringBuffer sum_f = new StringBuffer("SUM(");
			for(int j=1;j<=Integer.parseInt(month);j++){
				sum_p.append("MonP"+j+"+");
				sum_f.append("MonF"+j+"+");
			}
			sum_p.deleteCharAt(sum_p.length()-1).append(")");
			sum_f.deleteCharAt(sum_f.length()-1).append(")");
			for(int k=0;k<len;k++){
				String risktype = planDT.getString(k, "RiskType");
				String pValue="";//当月保费计划
				String fValue="";//当月手续费计划
				String ypValue="";//当年保费计划
				String yfValue="";//当年手续费计划
				String mTotalPlanPrem="";//保费计划总和
				String mTotalPrem="";//实收保费
				String mTotalPremFee="";//手续费计划总和
				String mTotalFee="";//实收手续费
				//主站
				if("wj_A".equals(risktype)){
					//月计划
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WJPremPlanA", toValidate(pValue));
					}else{
						parameters.put("WJPremPlanA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("MWJFPA", toValidate(fValue));
					}else{
						parameters.put("MWJFPA", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWJPremPA", toValidate(ypValue));
					}else{
						parameters.put("YWJPremPA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWJFeePA", toValidate(yfValue));
					}else{
						parameters.put("YWJFeePA", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wj", "Prem", "A");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wj", "Fee", "A");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWJPPA", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWJFPA", toValidate(bTotalPlanFee.toString()));
				}
				if("wj_B".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WJPremPlanB", toValidate(pValue));
					}else{
						parameters.put("WJPremPlanB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("MWJFPB", toValidate(fValue));
					}else{
						parameters.put("MWJFPB", toValidate("0.00"));
					}
					
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWJPremPB", toValidate(ypValue));
					}else{
						parameters.put("YWJPremPB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWJFeePB", toValidate(yfValue));
					}else{
						parameters.put("YWJFeePB", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wj", "Prem", "B");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wj", "Fee", "B");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWJPPB", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWJFPB", toValidate(bTotalPlanFee.toString()));
				}
				if("wj_D".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WJPremPlanD", toValidate(pValue));
					}else{
						parameters.put("WJPremPlanD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("MWJFPD", toValidate(fValue));
					}else{
						parameters.put("MWJFPD", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWJPremPD", toValidate(ypValue));
					}else{
						parameters.put("YWJPremPD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWJFeePD", toValidate(yfValue));
					}else{
						parameters.put("YWJFeePD", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wj", "Prem", "D");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wj", "Fee", "D");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWJPPD", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWJFPD", toValidate(bTotalPlanFee.toString()));
				}
				//主站所有险类累计达成率
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 2)='wj'",year).executeString();
				mTotalPrem = getTotalAmount(date, "wj", "Prem", "");
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
				mTotalFee = getTotalAmount(date, "wj", "Fee", "");
				BigDecimal bTotalPlanPremAll = new BigDecimal("0.00");
				BigDecimal bTotalPlanFeeAll = new BigDecimal("0.00");
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2,BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2,BigDecimal.ROUND_HALF_UP);
				}
				//保费累计达成
				parameters.put("MSWJPP", toValidate(bTotalPlanPremAll.toString()));
				//手续费达成
				parameters.put("MSWJFP", toValidate(bTotalPlanFeeAll.toString()));
				//PC累计保费
				allPremPC =  new BigDecimal(allPremPC).add( new BigDecimal(mTotalPlanPrem)).toString();
				//PC累计手续费
				allFeePC =  new BigDecimal(allFeePC).add( new BigDecimal(mTotalFee)).toString();
				if("wj_O".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WJPremPlanO", toValidate(pValue));
					}else{
						parameters.put("WJPremPlanO", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("MWJFPO", toValidate(fValue));
					}else{
						parameters.put("MWJFPO", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWJPremPO", toValidate(ypValue));
					}else{
						parameters.put("YWJPremPO", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWJFeePO", toValidate(yfValue));
					}else{
						parameters.put("YWJFeePO", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wj", "Prem", "O");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wj", "Fee", "O");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWJPPO", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWJFPO", toValidate(bTotalPlanFee.toString()));
				}
				//淘宝
				if("tb_A".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("TBPremPlanA", toValidate(pValue));
					}else{
						parameters.put("TBPremPlanA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("TBFeePlanA", toValidate(fValue));
					}else{
						parameters.put("TBFeePlanA", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YTBPremPA", toValidate(ypValue));
					}else{
						parameters.put("YTBPremPA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YTBFeePA", toValidate(yfValue));
					}else{
						parameters.put("YTBFeePA", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "tb", "Prem", "A");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "tb", "Fee", "A");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSTBPPA", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSTBFPA", toValidate(bTotalPlanFee.toString()));
				}
				if("tb_B".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("TBPremPlanB", toValidate(pValue));
					}else{
						parameters.put("TBPremPlanB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("TBFeePlanB", toValidate(fValue));
					}else{
						parameters.put("TBFeePlanB", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YTBPremPB", toValidate(ypValue));
					}else{
						parameters.put("YTBPremPB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YTBFeePB", toValidate(yfValue));
					}else{
						parameters.put("YTBFeePB", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "tb", "Prem", "B");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "tb", "Fee", "B");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSTBPPB", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSTBFPB", toValidate(bTotalPlanFee.toString()));
				}
				if("tb_D".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("TBPremPlanD", toValidate(pValue));
					}else{
						parameters.put("TBPremPlanD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("TBFeePlanD", toValidate(fValue));
					}else{
						parameters.put("TBFeePlanD", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YTBPremPD", toValidate(ypValue));
					}else{
						parameters.put("YTBPremPD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YTBFeePD", toValidate(yfValue));
					}else{
						parameters.put("YTBFeePD", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "tb", "Prem", "D");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "tb", "Fee", "D");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSTBPPD", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSTBFPD", toValidate(bTotalPlanFee.toString()));
				}
				//主站所有险类累计达成率
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 2)='tb'",year).executeString();
				mTotalPrem = getTotalAmount(date, "tb", "Prem", "");
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
				mTotalFee = getTotalAmount(date, "tb", "Fee", "");
			    bTotalPlanPremAll = new BigDecimal("0.00");
			    bTotalPlanFeeAll = new BigDecimal("0.00");
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//保费累计达成
				parameters.put("MSTBPP", toValidate(bTotalPlanPremAll.toString()));
				//手续费达成
				parameters.put("MSTBFP", toValidate(bTotalPlanFeeAll.toString()));
				//PC累计保费
				allPremPC =  new BigDecimal(allPremPC).add( new BigDecimal(mTotalPlanPrem)).toString();
				//PC累计手续费
				allFeePC =  new BigDecimal(allFeePC).add( new BigDecimal(mTotalFee)).toString();
				if("cps_A".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("CPSPremPlanA", toValidate(pValue));
					}else{
						parameters.put("CPSPremPlanA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("CPSFeePlanA", toValidate(fValue));
					}else{
						parameters.put("CPSFeePlanA", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YCPremPA", toValidate(ypValue));
					}else{
						parameters.put("YCPremPA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YCFeePA", toValidate(yfValue));
					}else{
						parameters.put("YCFeePA", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "cps", "Prem", "A");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "cps", "Fee", "A");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSCPPA", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSCFPA", toValidate(bTotalPlanFee.toString()));
				}
				if("cps_B".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("CPSPremPlanB", toValidate(pValue));
					}else{
						parameters.put("CPSPremPlanB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("CPSFeePlanB", toValidate(fValue));
					}else{
						parameters.put("CPSFeePlanB", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YCPremPB", toValidate(ypValue));
					}else{
						parameters.put("YCPremPB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YCFeePB", toValidate(yfValue));
					}else{
						parameters.put("YCFeePB", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "cps", "Prem", "B");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "cps", "Fee", "B");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSCPPB", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSCFPB", toValidate(bTotalPlanFee.toString()));
				}
				if("cps_D".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("CPSPremPlanD", toValidate(pValue));
					}else{
						parameters.put("CPSPremPlanD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("CPSFeePlanD", toValidate(fValue));
					}else{
						parameters.put("CPSFeePlanD", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YCPremPD", toValidate(ypValue));
					}else{
						parameters.put("YCPremPD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YCFeePD", toValidate(yfValue));
					}else{
						parameters.put("YCFeePD", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "cps", "Prem", "D");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "cps", "Fee", "D");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSCPPD", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSCFPD", toValidate(bTotalPlanFee.toString()));
				}
				//旅行保
				if("b2b_A".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("B2BPremPlanA", toValidate(pValue));
					}else{
						parameters.put("B2BPremPlanA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("B2BFeePlanA", toValidate(fValue));
					}else{
						parameters.put("B2BFeePlanA", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YBPremPA", toValidate(ypValue));
					}else{
						parameters.put("YBPremPA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YBFeePA", toValidate(yfValue));
					}else{
						parameters.put("YBFeePA", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "b2b", "Prem", "A");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "b2b", "Fee", "A");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSBPPA", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSBFPA", toValidate(bTotalPlanFee.toString()));
				}
				if("b2b_B".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("B2BPremPlanB", toValidate(pValue));
					}else{
						parameters.put("B2BPremPlanB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("B2BFeePlanB", toValidate(fValue));
					}else{
						parameters.put("B2BFeePlanB", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YBPremPB", toValidate(ypValue));
					}else{
						parameters.put("YBPremPB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YBFeePB", toValidate(yfValue));
					}else{
						parameters.put("YBFeePB", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "b2b", "Prem", "B");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "b2b", "Fee", "B");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSBPPB", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSBFPB", toValidate(bTotalPlanFee.toString()));
				}
				//主站所有险类累计达成率
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND (SUBSTRING(RiskType, 1, 3)='b2b' or SUBSTRING(RiskType, 1, 3)='cps')",year).executeString();
				String mTotalPrem1 = getTotalAmount(date, "b2b", "Prem", "");
				String mTotalPrem2 = getTotalAmount(date, "cps", "Prem", "");
				mTotalPrem = new BigDecimal(mTotalPrem1).add(new BigDecimal(mTotalPrem2)).toString();
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
				//mTotalFee = getTotalAmount(date, "b2b,cps", "Fee", "");
				String mTotalFee1 = getTotalAmount(date, "b2b", "Fee", "");
				String mTotalFee2 = getTotalAmount(date, "cps", "Fee", "");
				mTotalFee = new BigDecimal(mTotalFee2).add(new BigDecimal(mTotalFee1)).toString();
			    bTotalPlanPremAll = new BigDecimal("0.00");
			    bTotalPlanFeeAll = new BigDecimal("0.00");
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//保费累计达成
				parameters.put("MSCPP", toValidate(bTotalPlanPremAll.toString()));
				//手续费达成
				parameters.put("MSCFP", toValidate(bTotalPlanFeeAll.toString()));
				//PC累计保费
				allPremPC =  new BigDecimal(allPremPC).add( new BigDecimal(mTotalPlanPrem)).toString();
				//PC累计手续费
				allFeePC =  new BigDecimal(allFeePC).add( new BigDecimal(mTotalFee)).toString();
				//wap站
				if("wap_A".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WAPPremPlanA", toValidate(pValue));
					}else{
						parameters.put("WAPPremPlanA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("WAPFeePlanA", toValidate(fValue));
					}else{
						parameters.put("WAPFeePlanA", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWPremPA", toValidate(ypValue));
					}else{
						parameters.put("YWPremPA", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWFeePA", toValidate(yfValue));
					}else{
						parameters.put("YWFeePA", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wap", "Prem", "A");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wap", "Fee", "A");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWPPA", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWFPA", toValidate(bTotalPlanFee.toString()));
				}
				if("wap_B".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WAPPremPlanB", toValidate(pValue));
					}else{
						parameters.put("WAPPremPlanB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("WAPFeePlanB", toValidate(fValue));
					}else{
						parameters.put("WAPFeePlanB", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWPremPB", toValidate(ypValue));
					}else{
						parameters.put("YWPremPB", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWFeePB", toValidate(yfValue));
					}else{
						parameters.put("YWFeePB", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wap", "Prem", "B");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wap", "Fee", "B");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWPPB", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWFPB", toValidate(bTotalPlanFee.toString()));
				}
				if("wap_D".equals(risktype)){
					pValue = planDT.getString(k, "MonP"+month);
					fValue = planDT.getString(k, "MonF"+month);
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("WAPPremPlanD", toValidate(pValue));
					}else{
						parameters.put("WAPPremPlanD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("WAPFeePlanD", toValidate(fValue));
					}else{
						parameters.put("WAPFeePlanD", toValidate("0.00"));
					}
					//年计划
					ypValue = planDT.getString(k, "AllYearP");
					yfValue = planDT.getString(k, "AllYearF");
					if(StringUtil.isNotEmpty(pValue)){
						parameters.put("YWPremPD", toValidate(ypValue));
					}else{
						parameters.put("YWPremPD", toValidate("0.00"));
					}
					if(StringUtil.isNotEmpty(fValue)){
						parameters.put("YWFeePD", toValidate(yfValue));
					}else{
						parameters.put("YWFeePD", toValidate("0.00"));
					}
					//累计达成率
					mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalPrem = getTotalAmount(date, "wap", "Prem", "D");
					mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND RiskType=?",year,risktype).executeString();
					mTotalFee = getTotalAmount(date, "wap", "Fee", "D");
					BigDecimal bTotalPlanPrem = new BigDecimal("0.00");
					BigDecimal bTotalPlanFee = new BigDecimal("0.00");
					if(StringUtil.isNotEmpty(mTotalPlanPrem)&&new BigDecimal(mTotalPlanPrem).compareTo(zero)!=0){
						bTotalPlanPrem = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
					}
					if(StringUtil.isNotEmpty(mTotalPremFee)&&new BigDecimal(mTotalPremFee).compareTo(zero)!=0){
						bTotalPlanFee = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
					}
					//保费累计达成
					parameters.put("MSWPPD", toValidate(bTotalPlanPrem.toString()));
					//手续费达成
					parameters.put("MSWFPD", toValidate(bTotalPlanFee.toString()));
				}
				//主站所有险类累计达成率
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)='wap' ",year).executeString();
				mTotalPrem = getTotalAmount(date, "wap", "Prem", "");
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)='wap'",year).executeString();
				mTotalFee = getTotalAmount(date, "wap", "Fee", "");
			    bTotalPlanPremAll = new BigDecimal("0.00");
			    bTotalPlanFeeAll = new BigDecimal("0.00");
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(mTotalPrem).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(mTotalFee).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//保费累计达成
				parameters.put("MSWPP", toValidate(bTotalPlanPremAll.toString()));
				//手续费达成
				parameters.put("MSWFP", toValidate(bTotalPlanFeeAll.toString()));
				//wap累计保费
				allPremWap =  new BigDecimal(allPremWap).add( new BigDecimal(mTotalPlanPrem)).toString();
				//wap累计手续费
				allFeeWap =  new BigDecimal(allFeeWap).add( new BigDecimal(mTotalFee)).toString();
				
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)!='wap' ",year).executeString();
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)!='wap'",year).executeString();
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(allPremPC).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(allFeePC).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//PC保费累计达成
				parameters.put("MPSCPP", toValidate(bTotalPlanPremAll.toString()));
				//PC手续费达成
				parameters.put("MPCSFP", toValidate(bTotalPlanFeeAll.toString()));
				
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)='wap' ",year).executeString();
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? AND SUBSTRING(RiskType, 1, 3)='wap'",year).executeString();
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = new BigDecimal(allPremWap).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = new BigDecimal(allFeeWap).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//wap保费累计达成
				parameters.put("MSPP", toValidate(bTotalPlanPremAll.toString()));
				//wap手续费达成
				parameters.put("MSFP", toValidate(bTotalPlanFeeAll.toString()));
				
				
				mTotalPlanPrem = new QueryBuilder(" SELECT "+sum_p+" FROM PlanAmountInfo Where PlanYear=?  ",year).executeString();
				mTotalPremFee = new QueryBuilder(" SELECT "+sum_f+" FROM PlanAmountInfo Where PlanYear=? ",year).executeString();
				if(StringUtil.isNotEmpty(mTotalPlanPrem)||!"0".equals(mTotalPlanPrem)){
					bTotalPlanPremAll = (new BigDecimal(allPremWap).add(new BigDecimal(allPremPC))).divide(new BigDecimal(mTotalPlanPrem),2, BigDecimal.ROUND_HALF_UP);
				}
				if(StringUtil.isNotEmpty(mTotalPremFee)||!"0".equals(mTotalPremFee)){
					bTotalPlanFeeAll = (new BigDecimal(allFeeWap).add(new BigDecimal(allFeePC))).divide(new BigDecimal(mTotalPremFee),2, BigDecimal.ROUND_HALF_UP);
				}
				//合计保费累计达成
				parameters.put("MSWPP", toValidate(bTotalPlanPremAll.toString()));
				//合计手续费达成
				parameters.put("MSWFP", toValidate(bTotalPlanFeeAll.toString()));
			}
		}else{
			
		}
		//parameters.put("plan_totalY1", toValidate("4200000"));
		//parameters.put("plan_totalY2", toValidate("5300000"));
		//parameters.put("plan_totalY3", toValidate("6000000"));
		/*parameters.put("plan_totalY4", toValidate("100"));*/
		//parameters.put("plan_chargeY1", toValidate("1260000"));
		//parameters.put("plan_chargeY2", toValidate("1590000"));
		//parameters.put("plan_chargeY3", toValidate("2283334"));
		/*parameters.put("plan_chargeY4", toValidate("100"));*/
		
		return parameters;
	}
	
	private static double toValidate(String temp){
		if(StringUtil.isEmpty(temp)){
			return 0.0f;
		}
		
		return Double.parseDouble(temp);
	}
	
	//获取总保费、手续费
	public static String getTotalAmount(String date,String channelsn,String cFlag,String productType){
		String parameter = "";
		String year = date.substring(0,4);
		
		if("b2b".equals(channelsn)){
			return getB2bTotalAmount(date,channelsn,cFlag,productType);
		}
		String startDate = year+"-01-01 00:00:00";
		String endDate = date+" 23:59:59";
		parameter += " and tf.receiveDate >='" + startDate + "'";
		parameter = parameter + " and tf.receiveDate <='" + endDate +"'";
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		if (StringUtil.isNotEmpty(productType)) {
			if(!"O".equals(productType)){
				parameter = parameter + " and c.ProductType='"+productType+"'";
			}else{
				parameter = parameter + " and c.ProductType not in('A','B','D')";
			}

		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT FORMAT(SUM(sdor.timePrem),2) AS totalAmount,FORMAT(SUM(sdor.timePrem*c.feerate),2) AS charge ");
		sql.append(" FROM sdinformation b, sdproduct c,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid");
		sql.append(" WHERE sdor.ordersn=b.orderSn AND b.productId=c.ProductID ");
		sql.append(" AND a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='')");
		sql.append(parameter);
		
		DataTable dt = new QueryBuilder(sql.toString()).executeDataTable();
		if("Prem".equals(cFlag)){
			if(dt!=null && dt.getRowCount()>=1){
				String amt = dt.getString(0, "totalAmount");
				if(StringUtil.isNotEmpty(amt)){
					return amt.replaceAll(",", "");
				}else{
					return "0.00";
				}
			}else{
				return "0.00";
			}
		}else if("Fee".equals(cFlag)){
			if(dt!=null && dt.getRowCount()>=1){
				String amt = dt.getString(0, "charge");
				if(StringUtil.isNotEmpty(amt)){
					return amt.replaceAll(",", "");
				}else{
					return "0.00";
				}
			}else{
				return "0.00";
			}
		}
		return "0.00";
	}
	//获取旅行保总保费、手续费
	public static String getB2bTotalAmount(String date,String channelsn,String cFlag,String productType){
		String parameter = "";
		String year = date.substring(0,4);
		String startDate = year+"-01-01 00:00:00";
		String endDate = date+" 23:59:59";
		parameter += " and tf.receiveDate >='" + startDate + "'";
		parameter = parameter + " and tf.receiveDate <='" + endDate +"'";
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			parameter = parameter + " and s.ChannelSn in ("+channel+")";
		}
		if (StringUtil.isNotEmpty(productType)) {
			if("O".equals(productType)){
				parameter = parameter + " and SUBSTRING(e.EdorFlag, 1, 1)="+productType;
			}else{
				parameter = parameter + " and SUBSTRING(e.EdorFlag, 1, 1) not in('A','B','D')";
			}

		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" FORMAT(SUM(om.PaymentFee),2) AS totalAmount,FORMAT(SUM(om.PaymentFee * e.rate), 2) AS charge ");
		sql.append(" FROM sdorders s,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om");
		sql.append(" WHERE s.productid = e.RiskCode AND re.orderSn = s.id ");
		sql.append(" AND om.prop1 = re.id AND s.id = tf.ordID");
		sql.append(" AND s.orderstatus IN ('2', '9','10','13','14') AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='')");
		sql.append(parameter);
		sql.append(" GROUP BY EdorFlag");
		DataTable dt = new QueryBuilder(sql.toString()).executeDataTable("B2B");
		if("Prem".equals(cFlag)){
			if(dt!=null && dt.getRowCount()>=1){
				String amt = dt.getString(0, "totalAmount");
				if(StringUtil.isNotEmpty(amt)){
					return amt.replaceAll(",", "");
				}else{
					return "0.00";
				}
			}else{
				return "0.00";
			}
		}else if("Fee".equals(cFlag)){
			if(dt!=null && dt.getRowCount()>=1){
				String amt = dt.getString(0, "charge");
				if(StringUtil.isNotEmpty(amt)){
					return amt.replaceAll(",", "");
				}else{
					return "0.00";
				}
			}else{
				return "0.00";
			}
		}
		return "0.00";
	}
	public static void main(String[] args) {     
        
		 File reportFile = new File("D:\\workspace1\\wj\\WebContent\\ReportTemplate\\WholeYearPlanReport.jasper");  
	     File reportPdfFile = new File("D:\\111.pdf");  
        Map<String,Object> parameters=new HashMap<String,Object>();  
       // parameters.put("resultsList", resultsList);  
        parameters.put("reportTitle", "This is a test report");  
        parameters.put("startDate", "2015-02-01");
        parameters = dealReport(parameters);
        try {  
           /* byte[] reportStream = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,new JREmptyDataSource());  
            FileOutputStream fw = new FileOutputStream(reportPdfFile);  
            fw.write(reportStream);  
            fw.close(); */ 
        	JasperPrint report = null; 
        	FileOutputStream output = null; 
        	report = JasperFillManager.fillReport(reportFile.getPath(),parameters); 
        	JRAbstractExporter exporter = new JExcelApiExporter(); 
        	output = new FileOutputStream("D:\\111.xls"); 
        	exporter.setParameter(JRExporterParameter.JASPER_PRINT, report); 
        	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output); 
        	exporter.exportReport(); 
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } 
		System.out.println(new Double(new java.text.DecimalFormat("#.00").format(123.45/11)).toString());
		System.out.println(new Double(new java.text.DecimalFormat("#.00").format(123.456)).doubleValue());
    }  
}
