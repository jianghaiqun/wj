package com.wangjin.infoseeker;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class GlobalCount extends Page {

	/**
	 * 初始化查询2012年到至今的年份
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> initStaff(Mapx<String, String> params) {
		Mapx<String, String> map = new Mapx<String, String>();
		StringBuffer sb = new StringBuffer("");

		int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
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
	 */
	public void dg1DataBind(DataGridAction dga) {
		// 获得统计的年份
		String year = dga.getParam("year");
		String channelsn = dga.getParam("contant");
		if(StringUtil.isNotEmpty(channelsn)){
			channelsn=" and sd.channelsn='"+channelsn.trim()+"'";
		}else{
			channelsn="";
		}
		if (year == null || "".equals(year)) {
			int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			year = String.valueOf(now);
		}
		//年份集合
		List<String> list=new ArrayList<String>();
		for (int i = 1; i<13; i++) {
			if(i<10){
				list.add("0"+String.valueOf(i));
			}else{
				list.add(String.valueOf(i));
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ym as mon,FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0)),2) as val ,(IFNULL(b.num, 0)-IFNULL(f.num, 0)) as num ,IFNULL(IF(IFNULL(b.num, 0)<IFNULL(f.num, 0),-FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0))/(IFNULL(b.num, 0)-IFNULL(f.num, 0)),2) ,FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0))/(IFNULL(b.num, 0)-IFNULL(f.num, 0)),2)) ,0) AS vg from ");
		sql.append("(SELECT DATE_FORMAT(tf.receiveDate, '%m')  AS mon, IFNULL(SUM(timeprem), 0) AS val, COUNT(sdor.id) AS num  FROM sdinformationrisktype sdor LEFT JOIN sdorders  sd ON  sdor.orderSn=sd.orderSn   left join tradeinformation  tf on sd.ordersn= tf.ordid WHERE  tf.receiveDate LIKE '%"+year+"%' AND (sdor.appstatus='2' or sdor.appstatus='1' or sdor.appstatus='3' or sdor.appstatus='4')"+channelsn+" GROUP BY mon ORDER BY mon) AS b RIGHT JOIN (SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION SELECT '12' ym FROM DUAL) AS a ON a.ym = b.mon  LEFT JOIN ");
		sql.append("(SELECT DATE_FORMAT(sdor.cancelDate, '%m')  AS mon, IFNULL(SUM(timeprem), 0) AS val, COUNT(sdor.id) AS num FROM sdinformationrisktype sdor LEFT JOIN sdorders  sd ON  sdor.orderSn=sd.orderSn WHERE  sdor.cancelDate LIKE '%"+year+"%' AND (sdor.appstatus='2') "+channelsn+" GROUP BY mon ORDER BY mon) as f on a.ym=f.mon");
	
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total" };
		datatable.insertColumns(columnNames);
		int row = dt.getRowCount();
		// 添加环比增长
		Object[] totaldata = new Object[14];
		totaldata[12] = "总收入环比增长";
		totaldata[13] = "--";
		BigDecimal totalVal = new BigDecimal("0");
		BigDecimal totalNum = new BigDecimal("0");
		// 遍历前3行的数据
		for (int k = 0; k < 3; k++) {
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
					project = "总收入";
					break;
				case 1:
					col = "num";
					project = "件数";
					break;
				case 2:
					col = "vg";
					project = "件均保费";
					break;
				default:
					break;
				}
				if (i <= row - 1) {
					if (k == 1) {
						val = objToBigDec(String.valueOf(dt.getInt(i, col)));
						// 累加合计
						total = total.add(objToBigDec(dt.getInt(i, col)));
						totalNum=total;
					} else {
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
					if(k==2){
						if("0.00".equals(String.valueOf(totalNum)) || "0".equals(String.valueOf(totalNum))){
						total=objToBigDec(0);
						}else{
						total=totalVal.divide(totalNum,3,BigDecimal.ROUND_HALF_DOWN);
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
		dga.bindData(datatable);
	}
	/**
	 * 产品分类总销量及总件数
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		// 获得统计的年份
		String year = dga.getParam("year");
		String countflag = dga.getParam("countflag");
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)){
			channelsn=" and sdo.channelsn='"+channelsn.trim()+"'";
		}else{
			channelsn="";
		}
		if (year == null || "".equals(year)) {
			int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			year = String.valueOf(now);
		}
		String common="";
		if("amnt".equals(countflag)){
			common="SUM(sdor.timeprem)";
			
		}else{
		    common="COUNT(sdor.id)";
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode zd WHERE zd.codetype='ProductType' AND zd.parentcode='ProductType' AND codevalue LIKE '%00%'");
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
			sql.append("select a.ym,FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0)),2) as val  from ");
			sql.append("(SELECT fm.BelongFlag, zd.codename, DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val FROM  sdinformationrisktype sdor   LEFT JOIN sdinformation sd ON sd.orderSn=sdor.orderSn  LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn left join tradeinformation tf on sdo.ordersn=tf.ordid LEFT JOIN fmrisk fm ON sd.ProductId = fm.riskcode LEFT JOIN zdcode zd ON zd.codevalue = fm.BelongFlag WHERE tf.receiveDate LIKE '%"+year+"%' AND zd.codetype = 'ProductType' AND zd.parentcode = 'ProductType' AND (sdor.appstatus='2' or sdor.appstatus='1' or sdor.appstatus='3' or sdor.appstatus='4')  "+channelsn+" AND SUBSTRING(BelongFlag,1,1) = '"+dt_codevalue.get(i, 0).toString().subSequence(0, 1)+"' GROUP BY mon ORDER BY mon) AS b RIGHT JOIN (SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION SELECT '12' ym FROM DUAL) AS a ON a.ym = b.mon  LEFT JOIN");
			sql.append("(SELECT fm.BelongFlag, zd.codename, DATE_FORMAT(sdor.cancelDate, '%m') AS mon, IFNULL("+common+", 0) AS val FROM  sdinformationrisktype sdor   LEFT JOIN sdinformation sd ON sd.orderSn=sdor.orderSn  LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn left join tradeinformation tf on sdo.ordersn=tf.ordid LEFT JOIN fmrisk fm ON sd.ProductId = fm.riskcode LEFT JOIN zdcode zd ON zd.codevalue = fm.BelongFlag WHERE sdor.cancelDate LIKE '%"+year+"%' AND zd.codetype = 'ProductType' AND zd.parentcode = 'ProductType' AND sdor.appstatus='2' "+channelsn+" AND SUBSTRING(BelongFlag,1,1) = '"+dt_codevalue.get(i, 0).toString().subSequence(0, 1)+"' GROUP BY mon ORDER BY mon) AS f on a.ym=f.mon");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
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
		dga.bindData(datatable);
	}
	/**
	 * 保险公司的总销量及总件数
	 * @param dga
	 */
	public void dg3DataBind(DataGridAction dga) {
		// 获得统计的年份
		String year = dga.getParam("year");
		String countflag = dga.getParam("countflag");
		String channelsn = dga.getParam("contant");
		if(StringUtil.isNotEmpty(channelsn)){
			channelsn=" and sdo.channelsn='"+channelsn.trim()+"'";
		}else{
			channelsn="";
		}
		if (year == null || "".equals(year)) {
			int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			year = String.valueOf(now);
		}
		String common="";
		if("amnt".equals(countflag)){
			common="SUM(sd.timeprem)";
			
		}else{
		    common="COUNT(sd.id)";
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
			sql.append("select a.ym as mon ,FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0)),2) as val from ");
			sql.append("(SELECT zd.codename, DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val FROM sdinformationrisktype sd LEFT JOIN sdinformation sf  ON sf.orderSn=sd.orderSn  LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn  left join tradeinformation tf on sdo.ordersn=tf.ordid LEFT JOIN zdcode zd ON sf.insurancecompany LIKE '%"+dt_codevalue.get(i, 0)+"%' AND sf.insurancecompany = zd.codevalue WHERE tf.receiveDate LIKE '%"+year+"%'  "+channelsn+" AND zd.codetype = 'suppliercode' AND zd.parentcode = 'suppliercode' AND (sd.appstatus = '2' or sd.appstatus = '1' or sd.appstatus = '3' or sd.appstatus = '4' ) GROUP BY mon ORDER BY mon) AS b RIGHT JOIN (SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION SELECT '12' ym FROM DUAL) AS a ON a.ym = b.mon LEFT JOIN");
			sql.append("(SELECT zd.codename, DATE_FORMAT(sd.canceldate, '%m') AS mon, IFNULL("+common+", 0) AS val FROM sdinformationrisktype sd LEFT JOIN sdinformation sf  ON sf.orderSn=sd.orderSn  LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn   LEFT JOIN zdcode zd ON sf.insurancecompany LIKE '%"+dt_codevalue.get(i, 0)+"%' AND sf.insurancecompany = zd.codevalue WHERE sd.cancelDate LIKE '%"+year+"%'  "+channelsn+" AND zd.codetype = 'suppliercode' AND zd.parentcode = 'suppliercode' AND sd.appstatus = '2' GROUP BY mon ORDER BY mon) AS  f on a.ym=f.mon ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
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
		dga.bindData(datatable);
	}
	private static  BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}

	@SuppressWarnings("unused")
	private  BigDecimal divBigDec(BigDecimal a, BigDecimal b) {
		if (b.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		}
	}
}
