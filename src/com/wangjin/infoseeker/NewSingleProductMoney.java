package com.wangjin.infoseeker;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.iReportEntity.ReportEntity;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewSingleProductMoney extends Page {

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
	 * 产品分类总销量及总件数
	 * @param dga
	 * @return 
	 * @throws JRException 
	 */
	@SuppressWarnings("rawtypes")
	public JasperPrint dg7DataBind(Map param) throws JRException {
		// 获得统计的年份
		String year = (String)param.get("year");
		String countflag = (String)param.get("countflag");
		String channelsn = (String)param.get("contant");
		
		String cChannelSn = channelsn;
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}
		/*if("b2b".equals(channelsn)){
			return dg7B2BDataBind(param);
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
				memberCount = true;
			} else if ("2".equals(fromWap)) {// 除淘宝的会员
				fromInf = " AND EXISTS (SELECT 1 FROM member WHERE id = sd.memberId and (fromWap not like 'tb%' or fromWap is null or fromWap = ''))";
				memberCount = true;
			}
		}
		if (year == null || "".equals(year)) {
			int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			year = String.valueOf(now);
		}
		String common="";
		String projectTitle="";
		if("amnt".equals(countflag)){
			common="SUM(sdor.timeprem)";
			if("1".equals(cancelFlag)){
				common="SUM(-sdor.timeprem)";
			}
			projectTitle = "产品(保费)";
		}
		else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(sdor.orderSn))";
			projectTitle = "产品(订单数)";
		}
		else{
		    common="COUNT(sdor.id)";
		    projectTitle = "产品(件数)";
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
			String tRiskCode = dt_codevalue.getString(i, "RiskCode");
			if("0".equals(cancelFlag)){
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,sdinformationrisktype sdor   ");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" LEFT JOIN tradeinformation tf ON sdor.ordersn = tf.ordid");
				sql.append(" WHERE sd.paySn not like 'BG%'  "+channelsn+fromInf+" and sd.orderSn=sdor.orderSn and tf.receiveDate LIKE '"+year+"%' and sdor.Riskcode='"+tRiskCode+"'");
				sql.append(" AND sd.orderstatus >=7 AND sd.orderstatus <> '8'");
			}else if("1".equals(cancelFlag)){
				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders sd,tradeinformation tf,sdinformationrisktype sdor ");
				sql.append(" LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" WHERE tf.ordID = sdor.orderSn "+channelsn+fromInf+" AND sd.paySn not like 'BG%' and sd.orderSn=sdor.orderSn and tf.receiveDate LIKE '"+year+"%'  AND sdor.Riskcode='"+tRiskCode+"'");
				sql.append(" AND sdor.appstatus>=2 and (sdor.changeStatus IS NULL OR sdor.changeStatus='') GROUP BY mon");
				sql.append(" UNION ALL ");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon, IFNULL(COUNT(DISTINCT(sdor.orderSn)), 0) AS val ");
				sql.append(" FROM sdorders sd,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,tradeinformation tf,sdinformationrisktype sdor  LEFT JOIN fmrisk fm  ON sdor.RiskCode = fm.RiskCode");
				sql.append(" WHERE tf.ordID = sdor.orderSn "+channelsn+fromInf+" AND sd.paySn LIKE 'BG%' AND sd.orderSn=sdor.orderSn and tf.receiveDate LIKE '"+year+"%'  AND sdor.Riskcode='"+tRiskCode+"'");
				sql.append(" AND sdor.appstatus>=2 AND p.os= sd.orderSn  GROUP BY mon ) m");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable();
			DataTable b2bDT = null;
			// 不是会员统计 则统计b2b数据；会员统计不统计b2b数据
			if (!memberCount) {
				b2bDT = dg7B2BDataBind(param,cChannelSn,tRiskCode);
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
			dt = NewGlobalCount.dealDT1(dt, NewGlobalCount.mon);
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
	@SuppressWarnings("rawtypes")
	public DataTable dg7B2BDataBind(Map param,String channelsn,String riskcode) throws JRException {
		// 获得统计的年份
		String year = (String)param.get("year");
		String countflag = (String)param.get("countflag");
		// 撤单标识  1:撤单
	    String cancelFlag = (String)param.get("cancelFlag");
		if (year == null || "".equals(year)) {
			int now = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			year = String.valueOf(now);
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
			common="SUM(s.totalAmount)";
			if("1".equals(cancelFlag)){
				common="SUM(-om.PaymentFee)";
			}
			//projectTitle = "汇总(保费)";
		}else if("orderCount".equals(countflag)){
			common="COUNT(DISTINCT(s.id))";
			//projectTitle = "汇总(订单数)";
		}else{
		    common="SUM(s.peopleNum)";
		    //projectTitle = "汇总(件数)";
		}
		//QueryBuilder querybuilder=new QueryBuilder("SELECT RiskCode,RiskName FROM sdproduct");
		//DataTable dt_codevalue= querybuilder.executeDataTable("B2B");
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
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val   ");
				sql.append(" FROM sdorders s,sdtradeinformation tf ");
				sql.append(" WHERE s.orderstatus IN ('2','9','10','13','14') "+channelInfo+"  AND s.prop2 NOT LIKE 'BG%'  AND s.id = tf.ordID and tf.receiveDate like '"+year+"%' AND s.productid='"+riskcode+"'");
			}else if("1".equals(cancelFlag)){
				sql.append(" SELECT mon,SUM(val) AS val FROM (SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om ");
				sql.append(" WHERE om.prop1 = re.id "+channelInfo+" AND re.orderSn = s.id AND s.id = tf.ordID AND re.appStatus >=2 AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='')");
				sql.append(" and tf.receiveDate like '"+year+"%' AND s.productid='"+riskcode+"' GROUP BY mon");
				sql.append(" UNION");
				sql.append(" SELECT DATE_FORMAT(tf.receiveDate, '%m') AS mon,IFNULL("+common+", 0) AS val ");
				sql.append(" FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p ,sdinsuredcompanyreturndata re,sdtradeinformation tf,sdorderitem om ");
				sql.append(" WHERE om.prop1 = re.id "+channelInfo+" AND re.orderSn = s.id AND s.id = tf.ordID AND re.appStatus >=2 AND s.prop2 LIKE 'BG%'");
				sql.append(" AND p.os= s.id and tf.receiveDate like '"+year+"%' AND s.productid='"+riskcode+"' GROUP BY mon  ) m ");
			}
			sql.append(" GROUP BY mon  ORDER BY mon ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			DataTable dt = qb.executeDataTable(NewGlobalCount.b2bresource);
			return dt;
			/*dt = NewGlobalCount.dealDT1(dt, NewGlobalCount.mon);
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
	private  BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}

}
