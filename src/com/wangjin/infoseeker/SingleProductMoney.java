package com.wangjin.infoseeker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class SingleProductMoney extends Page {

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
		// map.put("searchyear",String.valueOf(now));
		return map;
	}

	/**
	 * 单一产品保费统计
	 * @param dga
	 */
	public void dg6DataBind(DataGridAction dga) {
		// 获得统计的年份
		String year = dga.getParam("year");
		String jianshu = dga.getParam("jianshu");
		if (year == null || "".equals(year)) {
			year = PubFun.getCurrentYear();
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT RiskCode,RiskName FROM fmrisk");
		DataTable dt_codevalue= querybuilder.executeDataTable();
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		// 定义列名数组
		String[] columnNames = { "one", "two", "three", "four", "five", "six", "seven", "eight", "night", "ten", "eleven", "twele", "project", "total","rank" };
		datatable.insertColumns(columnNames);
		//纵向总计
		BigDecimal bd_total=new BigDecimal("0");
		Object[] object_total={bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total,bd_total};
		//遍历所有险种
		if("jianshu1".equals(jianshu))
		{
		for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.ym AS mon, IFNULL(b.val,0) AS val,IFNULL(b.num, 0) AS num FROM ( SELECT DATE_FORMAT(sdtype.createdate, '%m') AS mon, IFNULL(SUM(sdtype.amnt), 0)" +
					"AS val,IFNULL(SUM(sdtype.mult), 0) AS num from sdinformationrisktype sdtype  " +
					"LEFT JOIN sdinformation sd on sdtype.orderSn=sd.orderSn where productid='"+dt_codevalue.get(i, 0)+"' " +
							" and  sdtype.createdate LIKE '%"+year+"%' GROUP BY mon ORDER BY mon  ) AS b " +
									"RIGHT JOIN ( SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION " +
									"SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION " +
									"SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION " +
									"SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION " +
									"SELECT '12' ym FROM DUAL ) AS a ON a.ym=b.mon");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
			Object[] object=new Object[15];
			BigDecimal total=new BigDecimal("0");
			for (int j = 0; j < dt.getRowCount(); j++) {
				object[j]=objToBigDec(dt.get(j,2)).stripTrailingZeros().toPlainString();
				total=total.add(objToBigDec(String.valueOf(object[j])));
				object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
			}
			object[12]=dt_codevalue.get(i,1);
			object[13]=objToBigDec(total.stripTrailingZeros()).toPlainString();
			object[14]=total;
			datatable.insertRow(object);
			object_total[13]=objToBigDec(object_total[13]).add(total);
		}
		object_total[12]="总计";
		datatable.insertRow(object_total);
		dga.bindData(datatable);
	}
		else
		{
			for (int i = 0; i < dt_codevalue.getRowCount(); i++) {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT a.ym AS mon, IFNULL(b.val,0) AS val,IFNULL(b.num, 0) AS num FROM ( SELECT DATE_FORMAT(sdtype.createdate, '%m') AS mon, IFNULL(SUM(sdtype.amnt), 0)" +
						"AS val,IFNULL(SUM(sdtype.mult), 0) AS num from sdinformationrisktype sdtype  " +
						"LEFT JOIN sdinformation sd on sdtype.orderSn=sd.orderSn where productid='"+dt_codevalue.get(i, 0)+"' " +
								" and  sdtype.createdate LIKE '%"+year+"%' GROUP BY mon ORDER BY mon  ) AS b " +
										"RIGHT JOIN ( SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION " +
										"SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION " +
										"SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION " +
										"SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION " +
										"SELECT '12' ym FROM DUAL ) AS a ON a.ym=b.mon");
				QueryBuilder qb = new QueryBuilder(sql.toString());
				DataTable dt = qb.executeDataTable();
				Object[] object=new Object[15];
				BigDecimal total=new BigDecimal("0");
				for (int j = 0; j < dt.getRowCount(); j++) {
					object[j]=objToBigDec(dt.get(j,1)).stripTrailingZeros().toPlainString();
					total=total.add(objToBigDec(String.valueOf(object[j])));
					object_total[j]=(objToBigDec(object_total[j])).add(objToBigDec(object[j]));
				}
				object[12]=dt_codevalue.get(i,1);
				object[13]=objToBigDec(total.stripTrailingZeros()).toPlainString();
				object[14]=total;
				datatable.insertRow(object);
				object_total[13]=objToBigDec(object_total[13]).add(total);
		}
			object_total[12]="总计";
			datatable.insertRow(object_total);
			dga.bindData(datatable);
		}
		
	}
	/**
	 * 产品分类总销量及总件数
	 * @param dga
	 */
	public void dg7DataBind(DataGridAction dga) {
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
			year = PubFun.getCurrentYear();
		}
		String common="";
		if("amnt".equals(countflag)){
			common="SUM(sdtype.timeprem)";
		}else{
		    common="COUNT(sdtype.id)";
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT RiskCode,RiskName FROM fmrisk");
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
			sql.append("SELECT a.ym AS mon, FORMAT((IFNULL(b.val, 0)-IFNULL(f.val, 0)),2) AS val FROM ( SELECT DATE_FORMAT(sdtype.insureDate, '%m') AS mon, IFNULL("+common+", 0)" +
					"AS val from sdinformationrisktype sdtype   " +
					"LEFT JOIN sdinformation sd on sdtype.orderSn=sd.orderSn  LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn left join tradeinformation tf on sdo.ordersn=tf.ordid where productid='"+dt_codevalue.get(i, 0)+"' " +
							" and  (sdtype.appstatus='2' or sdtype.appstatus='1' or sdtype.appstatus='3' or sdtype.appstatus='4' )  "+channelsn+" and  tf.receiveDate LIKE '%"+year+"%' GROUP BY mon ORDER BY mon  ) AS b " +
									"RIGHT JOIN ( SELECT '01' ym FROM DUAL UNION SELECT '02' ym FROM DUAL UNION " +
									"SELECT '03' ym FROM DUAL UNION SELECT '04' ym FROM DUAL UNION SELECT '05' ym FROM DUAL UNION " +
									"SELECT '06' ym FROM DUAL UNION SELECT '07' ym FROM DUAL UNION SELECT '08' ym FROM DUAL UNION " +
									"SELECT '09' ym FROM DUAL UNION SELECT '10' ym FROM DUAL UNION SELECT '11' ym FROM DUAL UNION " +
									"SELECT '12' ym FROM DUAL ) AS a ON a.ym=b.mon LEFT JOIN ");
			sql.append("( SELECT DATE_FORMAT(sdtype.cancelDate, '%m') AS mon, IFNULL("+common+", 0)" +
					"AS val from sdinformationrisktype sdtype    " +
					"LEFT JOIN sdinformation sd on sdtype.orderSn=sd.orderSn LEFT JOIN sdorders  sdo ON sd.orderSn=sdo.orderSn where productid='"+dt_codevalue.get(i, 0)+"' " +
							" and (sdtype.appstatus='2') "+channelsn+" and  sdtype.cancelDate LIKE '%"+year+"%' GROUP BY mon ORDER BY mon  ) AS f " +
									" on a.ym=f.mon");
			
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
	private  BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}

	private  BigDecimal divBigDec(BigDecimal a, BigDecimal b) {
		if (b.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		}
	}

	public static void main(String[] args) {
		// Object a = 5.17898124E8;
		// Object b = 1.6122574E8;
		// System.out.println(new BigDecimal(a.toString().trim().replaceAll(",",
		// "")).setScale(2, BigDecimal.ROUND_HALF_DOWN));
		// System.out.println(new BigDecimal(b.toString().trim().replaceAll(",",
		// "")).toPlainString());
		// System.out.println(new BigDecimal(0).subtract(new
		// BigDecimal(1.1873791E+8)).divide(new
		// BigDecimal(1.1873791E+8),2,BigDecimal.ROUND_HALF_UP));

//		System.out.println(new BigDecimal("0").add(new BigDecimal("161155740.00")));
	}
}
