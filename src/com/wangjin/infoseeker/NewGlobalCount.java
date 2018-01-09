package com.wangjin.infoseeker;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.iReportEntity.ReportEntity;

public class NewGlobalCount extends Page {

	public static String[] mon = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};//统计月份 
	public static String b2bresource = "B2B";
	public static char c='0';
	/**
	 * 初始化查询2012年到至今的年份
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> initStaff(Mapx<String, String> params) {
		Mapx<String, String> map = new Mapx<String, String>();
		StringBuffer sb = new StringBuffer("");
		int now = Integer.parseInt(PubFun.getCurrentYear());
		for (int j = 2012; j < now + 1; j++) {
			if (j == now) {
				sb.append(SelectTag.getOptionHtml(String.valueOf(j) + " 年", String.valueOf(j), true));
			} else {
				sb.append(SelectTag.getOptionHtml(String.valueOf(j) + " 年", String.valueOf(j), false));
			}
		}
		map.put("year", sb.toString());
		map.put("contant", HtmlUtil.codeToOptions("Sales.channel",false));
		return map;
	}

	/**
	 * 获得主站业绩总量统计的数据
	 * 
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("unchecked")
	public JasperPrint dg1DataBind(Map param) throws JRException {
		// 获得统计的年份
		String year = (String)param.get("year");
		String channelsn = (String)param.get("contant");
		String channelsnb2b = channelsn;
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}
		
		// 会员渠道
		String fromWap = (String)param.get("fromWap");
/*		if("b2b".equals(channelsn)){
			return dg1B2BDataBind(param);
		}*/
		
		// 撤单标识  1:撤单  2:对冲
		String cancelFlag = (String)param.get("cancelFlag");
		String channelInf="";
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			channelInf = " AND EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = sd.channelsn and ChannelCode IN ("+channel+"))";

		}else{
			channelInf="";
		}
		String fromInf = "";
		// 是否是会员统计  false:不是 true:是
		boolean memberCount = false;
		if (StringUtil.isNotEmpty(fromWap)) {
			// 淘宝的会员
			if ("1".equals(fromWap)) {
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and fromWap like 'tb%')";
				memberCount = true;
			} else if ("2".equals(fromWap)) {// 除淘宝的会员
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and (fromWap not like 'tb%' or fromWap is null or fromWap = ''))";
				memberCount = true;
			}
		}
		
		DataTable b2bDT = null;
		// 不是会员统计 则统计b2b数据；会员统计不统计b2b数据
		if (!memberCount) {
			b2bDT = dg1B2BDataBind(param,channelsnb2b);
		}
		
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		//年份集合
		List<String> list=new ArrayList<String>();
		for (int i = 1; i<13; i++) {
			if(i<10){
				list.add(StringUtil.leftPad(String.valueOf(i), c, 2));
			}else{
				list.add(String.valueOf(i));
			}
		}
		StringBuffer sql = new StringBuffer();
		if("0".equals(cancelFlag)){
			sql.append(" SELECT   DATE_FORMAT(tf.receiveDate, '%m') AS mon,  IFNULL(SUM(timeprem), 0) AS val,IFNULL(ROUND(SUM(timeprem*c.FeeRate),2), 0) AS cha,COUNT(sdor.id) AS num,  COUNT(DISTINCT (sdor.orderSn)) AS orderNum  ");
			sql.append(" FROM  sdinformation b,sdproduct c,sdinformationrisktype sdor   LEFT JOIN sdorders sd ON sdor.ordersn = sd.ordersn LEFT JOIN tradeinformation tf ON sd.ordersn = tf.ordid   WHERE b.ordersn = sdor.ordersn AND c.productid=b.productid "+channelInf+" and tf.receiveDate LIKE '"+year+"%'  ");
			sql.append(" AND sd.orderstatus >=7 AND sd.orderstatus <> '8' and sd.paySn not like 'BG%' " + fromInf);
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT mon,SUM(val) AS val,sum(cha) as cha,SUM(num) AS num,SUM(orderNum) AS orderNum FROM ( ");
			sql.append(" SELECT   DATE_FORMAT(tf.receiveDate, '%m') AS mon,  IFNULL(SUM(-timeprem), 0) AS val,IFNULL(ROUND(SUM(timeprem*c.FeeRate),2), 0) AS cha, ");
			sql.append(" COUNT(sdor.id) AS num,  COUNT(DISTINCT (sdor.orderSn)) AS orderNum   FROM  sdinformation b,sdproduct c,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders sd ON sdor.ordersn = sd.ordersn");
			sql.append(" LEFT JOIN tradeinformation tf ON sd.ordersn = tf.ordid  where b.ordersn = sdor.ordersn AND c.productid=b.productid AND sd.paySn NOT LIKE 'BG%' ");
			sql.append(" AND (sdor.changeStatus IS NULL OR sdor.changeStatus='') AND appstatus >=2 ");
			sql.append(" "+channelInf+fromInf+ " and  tf.receiveDate LIKE '"+year+"%' GROUP BY mon");
			sql.append(" UNION ALL");
			sql.append(" SELECT   DATE_FORMAT(tf.receiveDate, '%m') AS mon,  IFNULL(SUM(-timeprem), 0) AS val,IFNULL(ROUND(SUM(timeprem*c.FeeRate),2), 0) AS cha,");
			sql.append(" COUNT(sdor.id) AS num,  COUNT(DISTINCT (sdor.orderSn)) AS orderNum   FROM  sdinformation b,sdproduct c,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders sd ON sdor.ordersn = sd.ordersn LEFT JOIN tradeinformation tf ON sd.ordersn = tf.ordid,");
			sql.append(" ( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p");
			sql.append(" where b.ordersn = sdor.ordersn AND c.productid=b.productid AND sd.paySn LIKE 'BG%' AND appstatus >=2 ");
			sql.append(" "+channelInf+fromInf+" and  tf.receiveDate LIKE '"+year+"%' AND p.os= sd.orderSn GROUP BY mon");
			sql.append(" ) m ");
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT   DATE_FORMAT(tf.receiveDate, '%m') AS mon,  IFNULL(SUM(timeprem), 0) AS val,IFNULL(ROUND(SUM(timeprem*c.FeeRate),2), 0) AS cha,");
			sql.append(" COUNT(sdor.id) AS num,  COUNT(DISTINCT (sdor.orderSn)) AS orderNum   FROM  sdinformation b,sdproduct c,sdinformationrisktype sdor");
			sql.append(" LEFT JOIN sdorders sd ON sdor.ordersn = sd.ordersn LEFT JOIN tradeinformation tf ON sd.ordersn = tf.ordid");
			sql.append(" where b.ordersn = sdor.ordersn AND c.productid=b.productid AND sd.orderstatus >=7 AND sd.orderstatus <> '8'");
			sql.append(" "+channelInf+fromInf+" and  tf.receiveDate LIKE '"+year+"%' ");
			sql.append(" AND (appstatus <2 OR appstatus IS NULL OR appstatus='') ");
		}
		sql.append(" GROUP BY mon  ORDER BY mon ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		if(b2bDT!=null && b2bDT.getRowCount()>=1){
			//dt.union(b2bDT);
			int b2bLen = b2bDT.getRowCount();
			int tLen = dt.getRowCount();
			for(int i=0;i<b2bLen;i++){
				DataRow b2brow = b2bDT.get(i);
				boolean tflag = false;
				for(int j=0;j<tLen;j++){
					DataRow trow = dt.get(j);
					if(b2brow.getString("mon").equals(trow.getString("mon"))){
						tflag = true;
						trow.set("mon",trow.getString("mon"));
						trow.set("val",new BigDecimal(b2brow.getString("val").replace(",", "")).add(new BigDecimal(trow.getString("val").replace(",", ""))));
						trow.set("cha",new BigDecimal(b2brow.getString("cha").replace(",", "")).add(new BigDecimal(trow.getString("cha").replace(",", ""))));
						trow.set("num",new BigDecimal(b2brow.getString("num").replace(",", "")).add(new BigDecimal(trow.getString("num").replace(",", ""))));
						trow.set("orderNum",Integer.parseInt(b2brow.getString("orderNum").replace(",", ""))+Integer.parseInt(trow.getString("orderNum").replace(",", "")));	
					}
				}
				if(!tflag){
					dt.insertRow(b2brow);
				}
				
			}
		}
		dt = dealDT(dt,mon);
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		int row = dt.getRowCount();
		// 添加环比增长
		Object[] totaldata = new Object[14];
		totaldata[12] = "总保费环比增长";
		totaldata[13] = "--";
		BigDecimal totalVal = new BigDecimal("0");
		BigDecimal totalNum = new BigDecimal("0");
		BigDecimal totalNum1 = new BigDecimal("0");
		// 遍历前5行的数据
		for (int k = 0; k < 6; k++) {
			// 横向总计
			BigDecimal total = new BigDecimal("0");
			// 遍历13列的数据
			Object[] object = new Object[14];
			for (int i = 0; i < 14; i++) {
				String col = "";
				String project = "";
				BigDecimal val = new BigDecimal("0");
				switch (k) {
				case 0:
					col = "val";
					project = "总保费";
					break;
				case 1:
					col = "cha";
					project = "总手续费";
					break;
				case 2:
					col = "num";
					project = "件数";
					break;
				case 3:
					col = "vg";
					project = "件均保费";
					break;
				case 4:
					col = "orderNum";
					project = "订单数";
					break;
				case 5:
					col = "orderVg";
					project = "订单平均保费";
					break;
				default:
					break;
				}
				if (i <= row - 1) {
					if (k == 1) {
						val = objToBigDec(String.valueOf(dt.getString(i, col)));
					}
					if (k == 2) {
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(0, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						totalNum=total;
					}
					else if (k == 3){
						val = objToBigDec(String.valueOf(dt.getString(i, col)));
					}
					else if (k == 4){
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(0, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						totalNum1=total;
					}
					else {
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						if(k==0){
							totalVal=total;	
						}
					}
				}
				object[i] = val.toPlainString();
				if (i == 12) {
					object[i] = project;
				}
				if (i == 13) {
					if(k==3){
						if("0.00".equals(String.valueOf(totalNum)) || "0".equals(String.valueOf(totalNum))){
						total=objToBigDec(0);
						}else{
						total=totalVal.divide(totalNum,3,BigDecimal.ROUND_HALF_DOWN);
						}
					}
					if(k==5){
						if("0.00".equals(String.valueOf(totalNum)) || "0".equals(String.valueOf(totalNum))){
						total=objToBigDec(0);
						}else{
						total=totalVal.divide(totalNum1,3,BigDecimal.ROUND_HALF_DOWN);
						}
					}
					object[i] = total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				}
				// 环比增长
				if ("val".equals(col) && i < 12) {
					if (i - 1 < 0) {
						totaldata[i] = "--";
					} else {
						if ("0".equals(String.valueOf(object[i - 1])) || "0.00".equals(String.valueOf(object[i - 1]))) {
							totaldata[i] = "0%";
						} else {
							BigDecimal val1 = new BigDecimal(String.valueOf(object[i - 1]));
							BigDecimal val2 = new BigDecimal(String.valueOf(object[i].toString()));
							BigDecimal bdsub = val2.subtract(val1);
							totaldata[i] = bdsub.divide(val1, 2, BigDecimal.ROUND_HALF_UP);
						}
					}
				}
			}
			datatable.insertRow(object);
		}
		// 总收入环比增长
		datatable.insertRow(totaldata);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount_dg1DataBind.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
	}
	/**
	 * 获得主站业绩总量统计的数据(B2B)
	 * 
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("rawtypes")
	public DataTable dg1B2BDataBind(Map param,String channelsn) throws JRException {
		// 获得统计的年份
		String year = (String)param.get("year");
		// 撤单标识  1:撤单
		String cancelFlag = (String)param.get("cancelFlag");
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		//年份集合
		List<String> list=new ArrayList<String>();
		for (int i = 1; i<13; i++) {
			if(i<10){
				list.add(StringUtil.leftPad(String.valueOf(i), c, 2));
			}else{
				list.add(String.valueOf(i));
			}
		}
		String channelInfo="";
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			channelInfo = channelInfo + " and s.ChannelSn in ("+channel+")";
		}
		StringBuffer sql = new StringBuffer();
		if("0".equals(cancelFlag)){
			sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL(SUM(s.totalAmount), 0) AS val,IFNULL(SUM(s.totalAmount*c.rate), 0) AS cha,SUM(s.peopleNum+0) AS num,COUNT(DISTINCT s.id) AS orderNum   ");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdproduct c  ");
			sql.append(" WHERE c.riskcode=s.productid "+channelInfo+" and s.orderstatus IN ('2', '9','10','13','14') AND s.prop2 NOT LIKE 'BG%' AND s.id = tf.ordID and tf.receiveDate like '"+year+"%'");
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT mon,SUM(val) AS val,SUM(cha) AS cha,SUM(num) AS num,SUM(orderNum) AS orderNum FROM ( ");
			sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL(SUM(-om.PaymentFee), 0) AS val,IFNULL(SUM(-om.PaymentFee*c.rate), 0) AS cha,  ");
			sql.append(" COUNT(re.id) AS num,COUNT(DISTINCT s.id) AS orderNum   FROM sdorders s,sdproduct c,sdtradeinformation tf,sdinsuredcompanyreturndata re,");
			sql.append(" sdorderitem om   WHERE c.riskcode=s.productid and om.prop1 = re.id AND s.id = tf.ordID AND re.orderSn = s.id");
			sql.append(" AND re.appStatus>=2 AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='')");
			sql.append(" "+channelInfo+" and tf.receiveDate like '"+year+"%' GROUP BY mon  ");
			sql.append("  UNION ALL");
			sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL(SUM(-om.PaymentFee), 0) AS val,IFNULL(SUM(-om.PaymentFee*c.rate), 0) AS cha,");
			sql.append(" COUNT(re.id) AS num,COUNT(DISTINCT s.id) AS orderNum   FROM sdorders s,(SELECT MAX(newordersn) os FROM policychangeinfo GROUP BY initOrderSn) p,sdproduct c,sdtradeinformation tf,sdinsuredcompanyreturndata re,");
			sql.append(" sdorderitem om   WHERE c.riskcode=s.productid and om.prop1 = re.id AND s.id = tf.ordID AND re.orderSn = s.id ");
			sql.append(" AND p.os = s.id AND re.appStatus>=2 AND s.prop2 LIKE 'BG%' ");
			sql.append(" "+channelInfo+" and tf.receiveDate like '"+year+"%' GROUP BY mon) m");
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL(SUM(om.PaymentFee), 0) AS val,IFNULL(SUM(om.PaymentFee*c.rate), 0) AS cha,");
			sql.append(" COUNT(re.id) AS num,COUNT(DISTINCT s.id) AS orderNum   FROM sdorders s,sdproduct c,sdtradeinformation tf,sdinsuredcompanyreturndata re,");
			sql.append(" sdorderitem om   WHERE c.riskcode=s.productid and om.prop1 = re.id AND s.id = tf.ordID AND re.orderSn = s.id");
			sql.append(" AND s.orderstatus IN ('2', '9','10','13','14') AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='')");
			sql.append(" "+channelInfo+" and tf.receiveDate like '"+year+"%'");
		}
		sql.append(" GROUP BY mon  ORDER BY mon ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable(b2bresource);
		return dt;
		/*dt = dealDT(dt,mon);
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		int row = dt.getRowCount();
		// 添加环比增长
		Object[] totaldata = new Object[14];
		totaldata[12] = "总保费环比增长";
		totaldata[13] = "--";
		BigDecimal totalVal = new BigDecimal("0");
		BigDecimal totalNum = new BigDecimal("0");
		BigDecimal totalNum1 = new BigDecimal("0");
		// 遍历前5行的数据
		for (int k = 0; k < 6; k++) {
			// 横向总计
			BigDecimal total = new BigDecimal("0");
			// 遍历13列的数据
			Object[] object = new Object[14];
			for (int i = 0; i < 14; i++) {
				String col = "";
				String project = "";
				BigDecimal val = new BigDecimal("0");
				switch (k) {
				case 0:
					col = "val";
					project = "总保费";
					break;
				case 1:
					col = "cha";
					project = "总手续费";
					break;
				case 2:
					col = "num";
					project = "件数";
					break;
				case 3:
					col = "vg";
					project = "件均保费";
					break;
				case 4:
					col = "orderNum";
					project = "订单数";
					break;
				case 5:
					col = "orderVg";
					project = "订单平均保费";
					break;
				default:
					break;
				}
				if (i <= row - 1) {
					if (k == 2) {
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(0, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						totalNum=total;
					}
					else if (k == 4){
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(0, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						totalNum1=total;
					}
					else {
						val = objToBigDec(String.valueOf(dt.getString(i, col))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
						// 累加合计
						total = total.add(objToBigDec(dt.getString(i, col)));
						if(k==0){
							totalVal=total;	
						}
					}
				}
				object[i] = val.toPlainString();
				if (i == 12) {
					object[i] = project;
				}
				if (i == 13) {
					if(k==3){
						if("0.00".equals(String.valueOf(totalNum)) || "0".equals(String.valueOf(totalNum))){
						total=objToBigDec(0);
						}else{
						total=totalVal.divide(totalNum,3,BigDecimal.ROUND_HALF_DOWN);
						}
					}
					if(k==5){
						if("0.00".equals(String.valueOf(totalNum)) || "0".equals(String.valueOf(totalNum))){
						total=objToBigDec(0);
						}else{
						total=totalVal.divide(totalNum1,3,BigDecimal.ROUND_HALF_DOWN);
						}
					}
					object[i] = total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				}
				// 环比增长
				if ("val".equals(col) && i < 12) {
					if (i - 1 < 0) {
						totaldata[i] = "--";
					} else {
						if ("0".equals(String.valueOf(object[i - 1])) || "0.00".equals(String.valueOf(object[i - 1]))) {
							totaldata[i] = "0%";
						} else {
							BigDecimal val1 = new BigDecimal(String.valueOf(object[i - 1]));
							BigDecimal val2 = new BigDecimal(String.valueOf(object[i].toString()));
							BigDecimal bdsub = val2.subtract(val1);
							totaldata[i] = bdsub.divide(val1, 2, BigDecimal.ROUND_HALF_UP);
						}
					}
				}
			}
			datatable.insertRow(object);
		}
		// 总收入环比增长
		datatable.insertRow(totaldata);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount_dg1DataBind.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);*/
		
	}
	/**
	 * 产品分类总销量及总件数
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("unchecked")
	public JasperPrint dg2DataBind(Map param) throws JRException {
		// 获得统计的年份
		String year = (String) param.get("year");
		String countflag = (String) param.get("countflag");
		String channelsn = (String)param.get("contant");
		
		// 会员渠道
		String fromWap = (String)param.get("fromWap");
        String cChannelSn = channelsn;
        
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}
		
		// 撤单标识  1:撤单 
	    String cancelFlag = (String)param.get("cancelFlag");
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			channelsn = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = sd.channelsn and ChannelCode IN ("+channel+"))";

		}else{
			channelsn="";
		}
		String fromInf = "";
		// 是否是会员统计  false:不是 true:是
		boolean memberCount = false;
		if (StringUtil.isNotEmpty(fromWap)) {
			// 淘宝的会员
			if ("1".equals(fromWap)) {
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and fromWap like 'tb%')";
				memberCount = true;
			} else if ("2".equals(fromWap)) {// 除淘宝的会员
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and (fromWap not like 'tb%' or fromWap is null or fromWap = ''))";
				memberCount = true;
			}
		}
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		String common="";
		String projectTitle="";
		if("amnt".equals(countflag)){
			common="SUM(sdor.timeprem)";
			if("1".equals(cancelFlag)){
				common="SUM(-sdor.timeprem)";
			}
			projectTitle = "汇总(保费)";
		}else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(sdor.orderSn))";
			projectTitle = "汇总(订单数)";
		}else{
		    common="COUNT(sdor.id)";
		    projectTitle = "汇总(件数)";
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode zd WHERE zd.codetype='MainProductType' AND zd.parentcode='MainProductType' ");
		DataTable dt_codevalue= querybuilder.executeDataTable();
		for (DataRow dr : dt_codevalue) {
			if("D".equals(dr.get("CodeValue").toString())){
				dt_codevalue.deleteRow(dr);
				dt_codevalue.insertRow(new Object[] { "DL", "长期健康险"});
				dt_codevalue.insertRow(new Object[] { "DM", "短期健康险"});
			}
		}
		
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		//纵向总计
		BigDecimal bd_total=new BigDecimal("0");
		Object[] object_total={bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total};
		//遍历所有险种
		for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
			StringBuffer sql = new StringBuffer();
			String tRiskType=dt_codevalue.getString(i, "codevalue");
			if("0".equals(cancelFlag)){

				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,sdinformationrisktype sdor ");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.erisktype  LEFT JOIN tradeinformation tf ON sdor.ordersn = tf.ordid");
				sql.append(" WHERE sd.paySn not like 'BG%' "+channelsn+fromInf+" and sd.orderSn=sdor.orderSn and tf.receiveDate LIKE '"+year+"%'  AND zd.codetype = 'MainProductType'  AND zd.parentcode = 'MainProductType'");
				sql.append(" AND sd.orderstatus >=7 AND sd.orderstatus <> '8'  ");
				if("DL".equals(tRiskType)){
					sql.append(" AND erisktype = 'D' AND riskperiod='L' ");
				}else if("DM".equals(tRiskType)) {
					sql.append(" AND erisktype = 'D' AND riskperiod='M' ");
				}else{
					sql.append(" AND erisktype = '"+tRiskType+"' ");
				}
				

			}else if("1".equals(cancelFlag)){
				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,tradeinformation tf,sdinformationrisktype sdor  LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode ");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.erisktype WHERE  tf.ordID = sdor.orderSn  AND  sd.paySn NOT LIKE 'BG%' AND sd.orderSn=sdor.orderSn");
				sql.append(" AND zd.codetype = 'MainProductType'  AND zd.parentcode = 'MainProductType'  ");
				sql.append(" "+channelsn+fromInf+" AND tf.receiveDate LIKE '"+year+"%'");
				sql.append(" AND sdor.appstatus>=2 and (sdor.changeStatus IS NULL OR sdor.changeStatus='')  ");
				if("DL".equals(tRiskType)){
					sql.append(" AND fm.erisktype = 'D' AND fm.riskperiod='L' ");
				}else if("DM".equals(tRiskType)) {
					sql.append(" AND fm.erisktype = 'D' AND fm.riskperiod='M' ");
				}else{
					sql.append(" AND fm.erisktype = '"+tRiskType+"' ");
				}
				sql.append(" GROUP BY mon");
				sql.append(" UNION ALL");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,tradeinformation tf,sdinformationrisktype sdor  LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.erisktype WHERE  tf.ordID = sdor.orderSn AND  sd.paySn LIKE 'BG%' AND sd.orderSn=sdor.orderSn ");
				sql.append(" AND zd.codetype = 'MainProductType'  AND zd.parentcode = 'MainProductType' AND sdor.appstatus>=2  AND p.os= sd.orderSn");
				sql.append(" "+channelsn+fromInf+" AND  tf.receiveDate LIKE '"+year+"%' ");
				if("DL".equals(tRiskType)){
					sql.append(" AND fm.erisktype = 'D' AND fm.riskperiod='L' ");
				}else if("DM".equals(tRiskType)) {
					sql.append(" AND fm.erisktype = 'D' AND fm.riskperiod='M' ");
				}else{
					sql.append(" AND fm.erisktype = '"+tRiskType+"' ");
				}
				
				sql.append(" GROUP BY mon) m");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
			DataTable b2bDT = null;
			// 不是会员统计 则统计b2b数据；会员统计不统计b2b数据
			if (!memberCount) {
				b2bDT = dg2B2BDataBind(param,cChannelSn,tRiskType);
			}
			if(b2bDT!=null && b2bDT.getRowCount()>=1){
				//dt.union(b2bDT);
				int b2bLen = b2bDT.getRowCount();
				int tLen = dt.getRowCount();
				for(int k=0;k<b2bLen;k++){
					DataRow b2brow = b2bDT.get(k);
					boolean tflag = false;
					for(int j=0;j<tLen;j++){
						DataRow trow = dt.get(j);
						if(b2brow.getString("mon").equals(trow.getString("mon"))){
							tflag = true;
							trow.set("mon",trow.getString("mon"));
							trow.set("val",Double.parseDouble(b2brow.getString("val").replace(",", ""))+Double.parseDouble(trow.getString("val").replace(",", "")));
						}
					}
					if(!tflag){
						dt.insertRow(b2brow);
					}
					
				}
			}
			dt = dealDT1(dt, mon);
			Object[] object=new Object[14];
			BigDecimal total=new BigDecimal("0");
			for (int j = 0; j < dt.getRowCount(); j++) {
				object[j]=objToBigDec(dt.get(j,1)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
				total=total.add(objToBigDec(String.valueOf(object[j])));
				object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
			}
			object[12]=dt_codevalue.get(i,1);
			object[13]=total.setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
			datatable.insertRow(object);
			object_total[13]=objToBigDec(object_total[13]).add(total).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
		}
		object_total[12]="总计";
		datatable.insertRow(object_total);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		parameters.put("projectTitle", projectTitle);
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
	}
	/**
	 * 产品分类总销量及总件数(B2B)
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("unchecked")
	public DataTable dg2B2BDataBind(Map param,String channelsn,String risktype) throws JRException {
		// 获得统计的年份
		String year = (String) param.get("year");
		String countflag = (String) param.get("countflag");
		// 撤单标识  1:撤单
	    String cancelFlag = (String)param.get("cancelFlag");
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		String channelInfo="";
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			channelInfo = channelInfo + " and s.ChannelSn in ("+channel+")";
		}
		String common="";
		//String projectTitle="";
		if("amnt".equals(countflag)){
			common="SUM(om.PaymentFee)";
			if("1".equals(cancelFlag)){
				common="SUM(-om.PaymentFee)";
			}
			//projectTitle = "汇总(保费)";
		}else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(s.id))";
			//projectTitle = "汇总(订单数)";
		}else{
		    common="COUNT(re.ID)";
		    //projectTitle = "汇总(件数)";
		}
		//QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode zd WHERE zd.codetype='MainProductType' AND zd.parentcode='MainProductType' ");
		//DataTable dt_codevalue= querybuilder.executeDataTable(b2bresource);
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		//纵向总计
		//BigDecimal bd_total=new BigDecimal("0");
		//Object[] object_total={bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total};
		//遍历所有险种
		//for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
			StringBuffer sql = new StringBuffer();
			if("0".equals(cancelFlag)){
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val   ");
				sql.append(" FROM sdorders s,sdinsuredcompanyreturndata re,sdorderitem om,sdtradeinformation tf,sdproduct e  ");
				sql.append(" WHERE om.prop1 = re.id and re.orderSn = s.id and s.orderstatus IN ('2','9','10','13','14')  "+channelInfo+" AND s.prop2 NOT LIKE 'BG%' AND s.id = tf.ordID and tf.receiveDate like '"+year+"%' AND e.riskcode = s.productid AND SUBSTRING(EdorFlag,1,1)='"+risktype+"'");
			}else{
				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om,sdproduct e ");
				sql.append(" WHERE om.prop1 = re.id "+channelInfo+"  AND e.riskcode = s.productid AND s.id = tf.ordID  " );
				sql.append(" AND re.appStatus>=2 AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='') ");
				sql.append(" AND re.orderSn = s.id and tf.receiveDate like '"+year+"%' AND SUBSTRING(EdorFlag,1,1)='"+risktype+"'");
				sql.append(" GROUP BY mon");
				sql.append(" UNION ALL ");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om,sdproduct e  ");
				sql.append(" WHERE om.prop1 = re.id  "+channelInfo+" AND e.riskcode = s.productid AND p.os= s.id AND s.id = tf.ordID AND re.appStatus>=2 AND s.prop2 LIKE 'BG%'");
				sql.append(" AND re.orderSn = s.id and tf.receiveDate like '"+year+"%' AND SUBSTRING(EdorFlag,1,1)='"+risktype+"'");
				sql.append(" GROUP BY mon) m");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable(b2bresource);
			return dt;
			/*dt = dealDT1(dt, mon);
			Object[] object=new Object[14];
			BigDecimal total=new BigDecimal("0");
			for (int j = 0; j < dt.getRowCount(); j++) {
				object[j]=objToBigDec(dt.get(j,1)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
				total=total.add(objToBigDec(String.valueOf(object[j])));
				object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
			}
			object[12]=dt_codevalue.get(i,1);
			object[13]=total.setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
			datatable.insertRow(object);
			object_total[13]=objToBigDec(object_total[13]).add(total).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
		//}
		object_total[12]="总计";
		datatable.insertRow(object_total);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		parameters.put("projectTitle", projectTitle);
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);*/
		
	}
	/**
	 * 保险公司的总销量及总件数
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("rawtypes")
	public JasperPrint dg3DataBind(Map param) throws JRException {
		// 获得统计的年份
		String year = (String) param.get("year");
		String countflag = (String) param.get("countflag");
		String channelsn = (String)param.get("contant");
		
		 String cChannelSn = channelsn;
		 
		if (StringUtil.isNotEmpty(channelsn)
				&& ((channelsn.indexOf("xb2b_ht") > 0)
						|| (channelsn.indexOf("xb2c_pc") > 0) || (channelsn
						.indexOf("xb2c_wap") > 0))) {
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht")
					.replaceAll("xb2c_pc", "b2c_pc")
					.replaceAll("xb2c_wap", "b2c_wap");
		}
			
		/*if("b2b".equals(channelsn)){
			return dg3B2BDataBind(param);
		}*/
		// 撤单标识  1:撤单
	    String cancelFlag = (String)param.get("cancelFlag");
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			channelsn = " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = sd.channelsn and ChannelCode IN ("+channel+"))";
		}else{
			channelsn="";
		}
		// 会员渠道
		String fromWap = (String)param.get("fromWap");
		String fromInf = "";
		// 是否是会员统计  false:不是 true:是
		boolean memberCount = false;
		if (StringUtil.isNotEmpty(fromWap)) {
			// 淘宝的会员
			if ("1".equals(fromWap)) {
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and fromWap like 'tb%')";
				memberCount= true;
			} else if ("2".equals(fromWap)) {// 除淘宝的会员
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and (fromWap not like 'tb%' or fromWap is null or fromWap = ''))";
				memberCount= true;
			}
		}
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		String common="";
		String projectTitle="";
		if("amnt".equals(countflag)){
			common="SUM(sdor.timeprem)";
			if("1".equals(cancelFlag)){
				common="SUM(-sdor.timeprem)";
			}
			projectTitle="公司汇总(保费)";
		}else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(sdor.orderSn))";
			projectTitle="公司汇总(订单数)";
		}
		else{
		    common="COUNT(sdor.id)";
		    projectTitle="公司汇总(件数)";
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode WHERE codetype='suppliercode' AND parentcode='suppliercode'");
		DataTable dt_codevalue= querybuilder.executeDataTable();
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		//纵向总计
		BigDecimal bd_total=new BigDecimal("0");
		Object[] object_total={bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total};
		//遍历所有险种
		for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
			StringBuffer sql = new StringBuffer();
			String tComCode = dt_codevalue.getString(i, "codevalue");
			if("0".equals(cancelFlag)){

				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,sdinformationrisktype sdor   ");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.SupplierCode AND fm.SupplierCode='"+tComCode+"'  LEFT JOIN tradeinformation tf ON sdor.ordersn = tf.ordid");
				sql.append(" WHERE sd.paySn not like 'BG%' "+channelsn+fromInf+" and sd.orderSn=sdor.orderSn and tf.receiveDate LIKE '"+year+"%'  AND zd.codetype = 'suppliercode'  AND zd.parentcode = 'suppliercode'");
				sql.append(" AND sd.orderstatus >=7 AND sd.orderstatus <> '8' ");
			}else if("1".equals(cancelFlag)){

				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,tradeinformation tf,sdinformationrisktype sdor  ");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.SupplierCode AND fm.SupplierCode='"+tComCode+"' ");
				sql.append(" WHERE tf.ordID = sdor.orderSn  "+channelsn+fromInf+" AND sd.paySn not like 'BG%' and sd.orderSn=sdor.orderSn and tf.receiveDate like '"+year+"%'  AND zd.codetype = 'suppliercode'  AND zd.parentcode = 'suppliercode'");
				sql.append(" AND sdor.appstatus>=2 and (sdor.changeStatus IS NULL OR sdor.changeStatus='')  GROUP BY mon  ");
				sql.append(" UNION ALL ");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,tradeinformation tf,sdinformationrisktype sdor");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN zdcode zd ON zd.codevalue = fm.SupplierCode AND fm.SupplierCode='"+tComCode+"' ");
				sql.append(" WHERE tf.ordID = sdor.orderSn "+channelsn+fromInf+" AND  sd.paySn like 'BG%' and sd.orderSn=sdor.orderSn AND p.os= sd.orderSn");
				sql.append(" and tf.receiveDate like '"+year+"%' AND zd.codetype = 'suppliercode'  AND zd.parentcode = 'suppliercode' AND sdor.appstatus>=2 ");
				sql.append(" GROUP BY mon  ) m");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
		    QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
			DataTable b2bDT = null;
			// 不是会员统计 则统计b2b数据；会员统计不统计b2b数据
			if (!memberCount) {
				b2bDT = dg3B2BDataBind(param,cChannelSn,tComCode);
			}
			if(b2bDT!=null && b2bDT.getRowCount()>=1){
				//dt.union(b2bDT);
				int b2bLen = b2bDT.getRowCount();
				int tLen = dt.getRowCount();
				for(int k=0;k<b2bLen;k++){
					DataRow b2brow = b2bDT.get(k);
					boolean tflag = false;
					for(int j=0;j<tLen;j++){
						DataRow trow = dt.get(j);
						if(b2brow.getString("mon").equals(trow.getString("mon"))){
							tflag = true;
							trow.set("mon",trow.getString("mon"));
							trow.set("val",Double.parseDouble(b2brow.getString("val").replace(",", ""))+Double.parseDouble(trow.getString("val").replace(",", "")));
						}
					}
					if(!tflag){
						dt.insertRow(b2brow);
					}
					
				}
			}
			dt = dealDT1(dt, mon);
			Object[] object=new Object[14];
			BigDecimal total=new BigDecimal("0");
			for (int j = 0; j < dt.getRowCount(); j++) {
				object[j]=objToBigDec(dt.get(j,1)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
				total=total.add(objToBigDec(String.valueOf(object[j])));
				object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
			}
			object[12]=dt_codevalue.get(i,1);
			object[13]=total.setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
			datatable.insertRow(object);
			object_total[13]=objToBigDec(object_total[13]).add(total).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
		}
		object_total[12]="总计";
		datatable.insertRow(object_total);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		parameters.put("projectTitle", projectTitle);
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
	}
	/**
	 * 保险公司的总销量及总件数(B2B)
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("rawtypes")
	public DataTable dg3B2BDataBind(Map param,String channelsn,String comcode) throws JRException {
		// 获得统计的年份
		String year = (String) param.get("year");
		String countflag = (String) param.get("countflag");
		// 撤单标识  1:撤单
	    String cancelFlag = (String)param.get("cancelFlag");
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		String channelInfo="";
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			channelInfo = channelInfo + " and s.ChannelSn in ("+channel+")";
		}
		String common="";
		//String projectTitle="";
		if("amnt".equals(countflag)){
			common="SUM(om.PaymentFee)";
			if("1".equals(cancelFlag)){
				common="SUM(-om.PaymentFee)";
			}
			//projectTitle = "汇总(保费)";
		}else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(s.id))";
			//projectTitle = "汇总(订单数)";
		}else{
		    common="COUNT(re.ID)";
		    //projectTitle = "汇总(件数)";
		}
		//QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode WHERE codetype='suppliercode' AND parentcode='suppliercode'");
		//DataTable dt_codevalue= querybuilder.executeDataTable(b2bresource);
		// 定义初始化DataTable
		//DataTable datatable = new DataTable();
		// 定义列名数组
		//String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		//datatable.insertColumns(columnNames);
		//纵向总计
		//BigDecimal bd_total=new BigDecimal("0");
		//Object[] object_total={bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total};
		//遍历所有险种
		//for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
			StringBuffer sql = new StringBuffer();
			if("0".equals(cancelFlag)){
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,sdinsuredcompanyreturndata re,sdorderitem om,sdtradeinformation tf ");
				sql.append(" WHERE om.prop1 = re.id AND re.orderSn = s.id and s.orderstatus IN ('2','9','10','13','14') "+channelInfo+" AND s.id = tf.ordID and tf.receiveDate like '"+year+"%' AND s.prop2 NOT LIKE 'BG%' AND s.InsuranceCompanySn='"+comcode+"'");
			}else if("1".equals(cancelFlag)){
				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om ");
				sql.append(" WHERE om.prop1 = re.id "+channelInfo+" AND s.id = tf.ordID AND re.orderSn = s.id  AND re.appStatus>=2 AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='') ");
				sql.append(" and tf.receiveDate like '"+year+"%' AND s.InsuranceCompanySn='"+comcode+"' GROUP BY mon ");
				sql.append(" UNION ALL");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val");
				sql.append(" FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om");
				sql.append(" WHERE om.prop1 = re.id "+channelInfo+" AND re.orderSn = s.id AND s.id = tf.ordID AND p.os= s.id AND re.appStatus>=2 AND s.prop2 LIKE 'BG%'");
				sql.append(" and tf.receiveDate like '"+year+"%' AND s.InsuranceCompanySn='"+comcode+"' GROUP BY mon) m ");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
		    QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable(b2bresource);
			return dt;
			/*dt = dealDT1(dt, mon);
			Object[] object=new Object[14];
			BigDecimal total=new BigDecimal("0");
			for (int j = 0; j < dt.getRowCount(); j++) {
				object[j]=objToBigDec(dt.get(j,1)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
				total=total.add(objToBigDec(String.valueOf(object[j])));
				object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
			}
			object[12]=dt_codevalue.get(i,1);
			object[13]=total.setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
			datatable.insertRow(object);
			object_total[13]=objToBigDec(object_total[13]).add(total).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
		*/
			//}
		/*object_total[12]="总计";
		datatable.insertRow(object_total);
		//dga.bindData(datatable);
		
		//IREPROT数据封装
		long serialNo = 1;
		ArrayList<ReportEntity> reportList = new ArrayList<ReportEntity>();
		if(datatable.getRowCount()>0){
			for(int j = 0 ; j<datatable.getRowCount();j++){
				ReportEntity pe = new ReportEntity();
				pe.setSerialNo(serialNo);
				pe.setOne(datatable.getString(j, 0));
				pe.setTwo(datatable.getString(j, 1));
				pe.setThree(datatable.getString(j, 2));
				pe.setFour(datatable.getString(j, 3));
				pe.setFive(datatable.getString(j, 4));
				pe.setSix(datatable.getString(j, 5));
				pe.setSeven(datatable.getString(j, 6));
				pe.setEight(datatable.getString(j, 7));
				pe.setNight(datatable.getString(j, 8));
				pe.setTen(datatable.getString(j, 9));
				pe.setEleven(datatable.getString(j, 10));
				pe.setTwele(datatable.getString(j, 11));
				pe.setProject(datatable.getString(j, 12));
				pe.setTotal(datatable.getString(j, 13));
				reportList.add(pe);
				serialNo = serialNo +1l;
			}
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
		String path = Config.getContextRealPath();
		String modulepath = path + "ReportTemplate/";
		File reportFile = new File(modulepath+"/GlobalCount.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		Map parameters = new HashMap();
		parameters.put("projectTitle", projectTitle);
		return JasperFillManager.fillReport(jasperReport, parameters, dataSource);*/
		
	}
	private static  BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}

	public static  BigDecimal divBigDec(BigDecimal a, BigDecimal b) {
		if (b.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		}
	}
	public static DataTable dealDT(DataTable dt,String[] mon){
		DataTable newDT = new DataTable();
		Object[] object=null;
		String[] columnNames = { "mon", "val","cha", "num", "orderNum","vg","orderVg"};
		newDT.insertColumns(columnNames);
		int len = dt.getRowCount();
		int aLen = mon.length;
		for(int j=0;j<aLen;j++){
			boolean contantsFlag = false;
			for(int i=0;i<len;i++){
				if(dt.getString(i, "mon").equals(mon[j])){
					object = new Object[7];
					object[0]=dt.getString(i, "mon");
					object[1]=dt.getString(i, "val");
					object[2]=dt.getString(i, "cha");
					object[3]=dt.getString(i, "num");
					object[4]=dt.getString(i, "orderNum");
					object[5]=divBigDec(new BigDecimal(dt.getString(i, "val")),new BigDecimal(dt.getString(i, "num")));
					object[6]=divBigDec(new BigDecimal(dt.getString(i, "val")),new BigDecimal(dt.getString(i, "orderNum")));
					newDT.insertRow(object);
					contantsFlag = true;
					break;
				}
			}
			if(!contantsFlag){
				object = new Object[7];
				object[0]=mon[j];
				object[1]="0.00";
				object[2]="0.00";
				object[3]="0";
				object[4]="0";
				object[5]="0.00";
				object[6]="0.00";
				newDT.insertRow(object);
			}
		}
		return newDT;
	}
	public static DataTable dealDT1(DataTable dt,String[] mon){
		DataTable newDT = new DataTable();
		Object[] object=null;
		String[] columnNames = { "mon", "val"};
		newDT.insertColumns(columnNames);
		int len = dt.getRowCount();
		int aLen = mon.length;
		for(int j=0;j<aLen;j++){
			boolean contantsFlag = false;
			for(int i=0;i<len;i++){
				if(dt.getString(i, "mon").equals(mon[j])){
					object = new Object[2];
					object[0]=dt.getString(i, "mon");
					object[1]=dt.getString(i, "val");
					newDT.insertRow(object);
					contantsFlag = true;
					break;
				}
			}
			if(!contantsFlag){
				object = new Object[2];
				object[0]=mon[j];
				object[1]="0.00";
				newDT.insertRow(object);
			}
		}
		return newDT;
	}
	public static void main(String[] args) {
/*		BigDecimal total1 = new BigDecimal("0");
		total1=objToBigDec(0.00);
		BigDecimal total2 = new BigDecimal("0");
		total2=objToBigDec(340.00);
		System.out.println(total1.add(total2));*/
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT   DATE_FORMAT(tf.receiveDate, '%m') AS mon,  IFNULL(SUM(timeprem), 0) AS val,COUNT(sdor.id) AS num,  COUNT(DISTINCT (sdor.orderSn)) AS orderNum  ");
		sql.append(" FROM  sdinformationrisktype sdor   LEFT JOIN sdorders sd ON sdor.ordersn = sd.ordersn LEFT JOIN tradeinformation tf ON sd.ordersn = tf.ordid   WHERE tf.receiveDate LIKE '2014%'  ");
		sql.append("AND (  appstatus = '2'  OR appstatus = '1'  OR appstatus = '3' OR appstatus = '4' ) GROUP BY mon  ORDER BY mon ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		dealDT(dt,mon);
		
	}
}
