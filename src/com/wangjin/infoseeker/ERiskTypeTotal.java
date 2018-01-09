/**简短介绍 
  * <p>Date        : 2015-01-13</p> 
  * <p>Module      : 销售统计 </p> 
  * <p>Description:  主站每日品类销售数据统计</p> 
  * <p>Remark      : </p> 
  * @author jiaomengying
  * @version  
  * <p>------------------------------------------------------------</p> 
  * <p>   修改历史</p> 
  * <p>   序号  		 日期                		修改人   				 修改原因</p> 
  * <p>    1        2013-01-13     	jiaomengying        1860req20141229001-主站每日品类销售数据     </p> 
  */ 
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
import java.util.Date;

public class ERiskTypeTotal extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	private static String tminutes = " 23:59:59";


	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initStaff(Mapx params) {
		Mapx map = new Mapx();
		map.put("yesterday", yesterday);
		map.put("today", today);
		map.put("allDate", "0");
		map.put("contant", HtmlUtil.codeToOptions("Sales.channel",false));
		return map;
	}

	/**
	 * 获得按险种统计的数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		// 撤单标识  1:撤单  2:对冲
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		if (StringUtil.isNotEmpty(startDate)) {
			parameter += " and tf.receiveDate >='" + startDate + "'";
		} else {
			parameter += " and tf.receiveDate >='" + yesterday + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter = parameter + " and tf.receiveDate <='" + endDate + tminutes+"'";
		} else {
			parameter = parameter + " and tf.receiveDate <='" + today + tminutes+"'";
		}
		QueryBuilder querybuilder=new QueryBuilder("SELECT codevalue,codename FROM zdcode zd WHERE zd.codetype='MainProductType' AND zd.parentcode='MainProductType' ");
		DataTable dt_codevalue= querybuilder.executeDataTable();
		// 定义初始化DataTable
		DataTable datatable = new DataTable();
		String[] objColumns = { "rowNo","COUNT","productGenera", "totalAmount", "charge", "AVG"};
		datatable.insertColumns(objColumns);
		int count = 1;
		for(int i = 0;i<dt_codevalue.getRowCount();i++){
			StringBuffer sql = new StringBuffer();
			QueryBuilder qb = null;
			if ("0".equals(cancelFlag)) {
				sql.append(" select '' as rowNo,count(1) as count, z.codename as productGenera ,FORMAT(sum(a.totalAmount),2) as totalAmount,FORMAT(sum(a.totalAmount*c.feerate),2) as charge ,FORMAT(avg(a.totalAmount*c.feerate),2) as avg ");
				sql.append(" from sdorders a,sdinformation b,fmrisk f LEFT JOIN zdcode z ON z.codevalue = f.BelongFlag AND z.codetype='ProductType', sdproduct c ,SDOrderItem d");
				sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid");
				sql.append(" where a.paySn not like 'BG%' and a.ordersn =b.ordersn and b.productid = c.productid and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null)  and a.orderStatus >=7 AND a.orderStatus<>'8' ");
				sql.append(" AND f.riskcode = b.productid AND c.ProductType='"+dt_codevalue.getString(i, "codevalue")+"' and a.channelsn='wj' and  not  EXISTS (SELECT  `on`  FROM  cps  WHERE `on` = a.ordersn )"+parameter);
				sql.append(" GROUP BY f.BelongFlag");
				qb = new QueryBuilder(sql.toString());
			}else if("1".equals(cancelFlag)){
				sql.append(" SELECT  rowNo,SUM(COUNT) AS COUNT,productGenera,FORMAT(SUM(-(totalamount)),2) AS totalamount,FORMAT(SUM(-(charge)),2) AS charge,FORMAT(SUM(-(AVG)),2) AS AVG FROM ");
				sql.append(" (SELECT   '' as rowNo,f.BelongFlag,COUNT(DISTINCT sdor.orderSn) AS COUNT, z.codename AS productGenera ,c.producttype,SUM((sdor.timePrem)) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,AVG(sdor.timePrem*c.feerate) AS AVG ");
				sql.append(" FROM  sdinformation b,fmrisk f LEFT JOIN zdcode z ON z.codevalue = f.BelongFlag AND z.codetype='ProductType', sdproduct c,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid  ");
				sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND appstatus >=2 AND a.paySn NOT LIKE 'BG%' AND (sdor.changeStatus IS NULL OR sdor.changeStatus='')");
				sql.append(" AND f.riskcode = b.productid AND c.ProductType='"+dt_codevalue.getString(i, "codevalue")+"' and a.channelsn='wj' and  not  EXISTS (SELECT  `on`  FROM  cps  WHERE `on` = a.ordersn ) "+parameter + " GROUP BY f.BelongFlag ");
				sql.append(" UNION ALL");
				sql.append(" SELECT   '' as rowNo,f.BelongFlag,COUNT(DISTINCT sdor.orderSn) AS COUNT, z.codename AS productGenera , c.producttype,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,AVG(sdor.timePrem*c.feerate) AS AVG");
				sql.append(" FROM  sdinformation b,fmrisk f LEFT JOIN zdcode z ON z.codevalue = f.BelongFlag AND z.codetype='ProductType',( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p, sdproduct c,sdinformationrisktype sdor ");
				sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND a.paySn LIKE 'BG%'");
				sql.append(" AND f.riskcode = b.productid AND c.ProductType='"+dt_codevalue.getString(i, "codevalue")+"' "+ " and p.os= b.orderSn AND appstatus >=2 and a.channelsn='wj' and  not  EXISTS (SELECT  `on`  FROM  cps  WHERE `on` = a.ordersn ) "+parameter);
				sql.append(" GROUP BY f.BelongFlag) a GROUP BY BelongFlag");
				qb = new QueryBuilder(sql.toString());
			}
			DataTable dt = qb.executeDataTable();
			BigDecimal sum1 = new BigDecimal(0);
			BigDecimal sum2 = new BigDecimal(0);
			int partCount_all = 0;
			if(dt!=null && dt.getRowCount()>0){
				for (DataRow dr : dt) {
					sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
					sum2 = sum2.add(objToBigDec(dr.get("charge")));
					partCount_all = partCount_all + Integer.valueOf(dr.get("count").toString());
				}
				Object[] objRow = { count,"<b>"+partCount_all+"</b>","<span style=\"text-align:left; display: block;\"><b>"
				+dt_codevalue.getString(i, "codename")+"</b></span>", "<b>"+sum1+"</b>", "<b>"+sum2+"</b>", "<b>"+divBigDec(sum2, new BigDecimal(partCount_all))+"</b>"  };
				count++;
				datatable.insertRow(objRow);
				for (DataRow drNew : dt) {
					datatable.insertRow(drNew);
				}
			}
		}
		
		// 添加总计
		BigDecimal sum3 = new BigDecimal(0);
		BigDecimal sum4 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : datatable) {
			String index1 = dr.get(1).toString();
			String index2 = dr.get(3).toString();
			String index3 = dr.get(4).toString();
			if(index1.indexOf("<b>")!=-1){
				index1 = index1.substring(index1.indexOf("<b>")+3,index1.indexOf("</b>"));
				index2 = index2.substring(index2.indexOf("<b>")+3,index2.indexOf("</b>"));
				index3 = index3.substring(index3.indexOf("<b>")+3,index3.indexOf("</b>"));
			}
			sum3 = sum3.add(objToBigDec(index2));
			sum4 = sum4.add(objToBigDec(index3));
			count_all = count_all + Integer.valueOf(index1);
		}
		Object[] catalogRow = { "","<b>"+count_all/2+"</b>", "<b>总计</b>", "<b>"+sum3.divide(new BigDecimal(2))+"</b>", "<b>"+
		sum4.divide(new BigDecimal(2))+"</b>", "<b>"+divBigDec(sum4.divide(new BigDecimal(2)), new BigDecimal(count_all/2))+"</b>" };
		datatable.insertRow(catalogRow);
		dga.bindData(datatable);
	}
	
	private BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}
	
	private BigDecimal divBigDec(BigDecimal a, BigDecimal b) {
		if (b.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		}
	}

}
