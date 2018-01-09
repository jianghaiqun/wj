package com.wangjin.infoseeker;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.wangjin.infoseeker.iptranse.IPLocation;
import com.wangjin.infoseeker.iptranse.IPSeeker;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TotalInfo extends Page {

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
		map.put("sdChannel", HtmlUtil.mapxToOptions(getSdChannel(), null, true));// 渠道
		map.put("today", today);
		map.put("allDate", "0");
		map.put("contant", HtmlUtil.codeToOptions("Sales.channel",false));
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
	/**
	 * 获得按险种统计的数据
	 *  
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		//String allDate = dga.getParam("allDate");//统计全部标记 1表示统计全部
		String channelsn = dga.getParam("contant");
		String channelsnb2b = channelsn;
		String sdChannel = dga.getParam("sdChannel");
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		/*if("b2b".equals(channelsn)){
			dg1DataBindB2B(dga);
			return;
		}*/
		DataTable b2bDT = dg1DataBindB2B(dga,channelsnb2b);
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
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		if("tbsd".equals(channelsn) && StringUtil.isNotEmpty(sdChannel)){//选择淘宝刷单渠道该条件才生效
			if("tbsd".equals(sdChannel)){//淘宝刷单
				parameter = parameter +" and a.ordersn like 'TB%' and a.ordersn not like 'TBMY%'";
			}else if("mysd".equals(sdChannel)){//蚂蚁刷单
				parameter = parameter +" and a.ordersn like 'TBMY%'";
			}
		}
		StringBuffer sql = new StringBuffer();
		QueryBuilder qb = null;
		String financProductsIds = Config.getValue("LicaiBaoxianProducts");//理财产品ids
		String sdproductTable = "(SELECT CASE WHEN (aa.producttype='D' AND bb.riskperiod='L') THEN CONCAT('长期',aa.ProductGenera) "
				+ " WHEN (aa.producttype='D' AND bb.riskperiod='M') THEN CONCAT('短期',aa.ProductGenera) "
				+ " ELSE aa.ProductGenera END  AS ProductGenera,"
				+ " aa.feerate,aa.productid,"
				+ " CASE WHEN aa.producttype='D' THEN CONCAT(aa.producttype,bb.riskperiod) "
				+ " ELSE aa.producttype END  AS producttype "
				+ " FROM sdproduct aa LEFT JOIN fmrisk bb ON aa.productid = bb.riskcode )";
		
		if ("0".equals(cancelFlag)) {
			sql.append(" select count(1) as count,");
			sql.append(" c.ProductGenera as productGenera ,FORMAT(sum(a.totalAmount),2) as totalAmount,FORMAT(sum(a.totalAmount*c.feerate),2) as charge ,FORMAT(CASE WHEN COUNT(1)!=0 THEN sum(a.totalAmount*c.feerate)/COUNT(1) ELSE 0 END ,2) AS AVG ");
			sql.append(" from sdorders a,sdinformation b, "+sdproductTable+" c ,SDOrderItem d");
			sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid");
			sql.append(" where a.paySn not like 'BG%' and a.ordersn =b.ordersn and b.productid = c.productid and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null)  and a.orderStatus >=7 AND a.orderStatus<>'8' ");
			sql.append(" and c.productid not in (");
			sql.append(financProductsIds);
			sql.append(") ");
			sql.append(parameter);
			sql.append(" group by c.producttype");
			sql.append(" UNION ALL");
			sql.append(" select count(1) as count,");
			sql.append(" '理财险' as productGenera ,FORMAT(sum(a.totalAmount),2) as totalAmount,FORMAT(sum(a.totalAmount*c.feerate),2) as charge ,FORMAT(CASE WHEN COUNT(1)!=0 THEN sum(a.totalAmount*c.feerate)/COUNT(1) ELSE 0 END ,2) AS AVG ");
			sql.append(" from sdorders a,sdinformation b, "+sdproductTable+" c ,SDOrderItem d");
			sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid");
			sql.append(" where a.paySn not like 'BG%' and a.ordersn =b.ordersn and b.productid = c.productid and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null)  and a.orderStatus >=7 AND a.orderStatus<>'8' ");
			sql.append(" and c.productid in (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(parameter);
			sql.append(" group by c.producttype");
			qb = new QueryBuilder(sql.toString());
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT  SUM(COUNT) AS COUNT,productGenera,FORMAT(SUM(-(totalamount)),2) AS totalamount,FORMAT(SUM(-(charge)),2) AS charge,FORMAT(SUM(-(AVG)),2) AS AVG FROM ");
			sql.append(" (SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT,");
			sql.append(" c.ProductGenera AS productGenera,c.producttype,SUM((sdor.timePrem)) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG  ");
			sql.append(" FROM  sdinformation b, "+sdproductTable+" c,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid  ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND appstatus >=2 AND a.paySn NOT LIKE 'BG%' AND (sdor.changeStatus IS NULL OR sdor.changeStatus='')");
			sql.append("AND c.productid NOT IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(parameter);
			sql.append(" GROUP BY c.producttype ");
			sql.append(" UNION ALL");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT,");
			sql.append(" '理财险' AS productGenera,'licaixian' as producttype,SUM((sdor.timePrem)) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG  ");
			sql.append(" FROM  sdinformation b, "+sdproductTable+" c,fmrisk fmkb,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid  ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND fmkb.RiskCode = c.ProductID AND (appstatus =2 or appstatus = 4) AND a.paySn NOT LIKE 'BG%' AND (sdor.changeStatus IS NULL OR sdor.changeStatus='')");
			sql.append(" AND c.productid IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(" AND IFNULL(fmkb.Hesitation,10) >= (SELECT TIMESTAMPDIFF(DAY, sdor.insureDate, sdor.cancelDate)) ");
			sql.append(parameter);
			sql.append(" GROUP BY c.producttype ");
			sql.append(" UNION ALL");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT,");
			sql.append(" c.ProductGenera AS productGenera, c.producttype,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG");
			sql.append(" FROM  sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p, "+sdproductTable+" c,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND a.paySn LIKE 'BG%'");
			sql.append(" and p.os= b.orderSn AND appstatus >=2 ");
			sql.append(" and c.productid NOT IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(parameter);
			sql.append(" GROUP BY c.producttype");
			sql.append(" UNION ALL");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT,");
			sql.append(" '理财险' AS productGenera, 'licaixian' as producttype,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG");
			sql.append(" FROM  sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p, "+sdproductTable+" c,fmrisk fmkb,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND fmkb.RiskCode = c.ProductID AND a.paySn LIKE 'BG%'");
			sql.append(" and p.os= b.orderSn AND (appstatus =2 or appstatus = 4) ");
			sql.append(" and c.productid IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(" AND IFNULL(fmkb.Hesitation,10) >= (SELECT TIMESTAMPDIFF(DAY, sdor.insureDate, sdor.cancelDate)) ");
			sql.append(parameter);
			sql.append(" GROUP BY c.producttype");
			sql.append(") a GROUP BY producttype");
			qb = new QueryBuilder(sql.toString());
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT SUM(COUNT) AS COUNT, productGenera,FORMAT(SUM(totalAmount),2) AS totalamount,FORMAT(SUM(charge),2) AS charge,FORMAT(SUM(AVG),2) AS AVG FROM");
			sql.append(" (SELECT  COUNT(DISTINCT sdor.orderSn) AS COUNT,");
			sql.append(" c.ProductGenera AS productGenera ,c.producttype, SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge , ");
			sql.append(" CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AS AVG FROM  sdinformation b, "+sdproductTable+" c,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID ");
			sql.append(" AND a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='')");
			sql.append(" AND c.productid NOT IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(parameter);
			sql.append(" group by c.producttype");
			sql.append(" UNION ALL");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT, CASE WHEN c.productid in(");
			sql.append(financProductsIds);
			sql.append(") THEN '理财险' ELSE c.ProductGenera END AS productGenera , 'licaixian' as producttype,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge , ");
			sql.append(" CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AS AVG FROM  sdinformation b, "+sdproductTable+" c,fmrisk fmkb,sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND fmkb.RiskCode = c.ProductID ");
			sql.append(" AND a.orderstatus >=7 AND a.orderstatus <> '8' AND (((appstatus = 2 or appstatus = 4) and IFNULL(fmkb.Hesitation,10) < (SELECT TIMESTAMPDIFF(DAY, sdor.insureDate, sdor.cancelDate)) ) or (appstatus = 1 or appstatus = 3 or appstatus is null or appstatus = ''))");
			sql.append(" AND c.productid IN (");
			sql.append(financProductsIds);
			sql.append(" ) ");
			sql.append(parameter);
			sql.append(" group by c.producttype) a GROUP BY a.producttype");
			qb = new QueryBuilder(sql.toString());
		}
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
					if(b2brow.getString("productGenera").equals(trow.getString("productGenera"))){
						tflag = true;
						trow.set("COUNT",Integer.parseInt(b2brow.getString("COUNT").replace(",", ""))+Integer.parseInt(trow.getString("COUNT").replace(",", "")));
						trow.set("productGenera",trow.getString("productGenera"));
						trow.set("totalAmount",new DecimalFormat("#####0.00").format(Double.parseDouble(b2brow.getString("totalAmount").replace(",", ""))+Double.parseDouble(trow.getString("totalAmount").replace(",", ""))));
						trow.set("charge",new DecimalFormat("#####0.00").format(Double.parseDouble(b2brow.getString("charge").replace(",", ""))+Double.parseDouble(trow.getString("charge").replace(",", ""))));	
						trow.set("AVG",new BigDecimal(trow.getString("charge").replace(",", "")).divide(new BigDecimal(trow.getString("COUNT").replace(",", "")),2,BigDecimal.ROUND_HALF_UP));
					}
				}
				if(!tflag){
					dt.insertRow(b2brow);
				}
				
			}
		}
		// 添加总计
		BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : dt) {
			sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
			sum2 = sum2.add(objToBigDec(dr.get("charge")));
			count_all = count_all + Integer.valueOf(dr.get("count").toString());
		}
		Object[] catalogRow = { count_all, "总计", sum1, sum2, divBigDec(sum2, new BigDecimal(count_all)) };
		dt.insertRow(catalogRow);
		dga.bindData(dt);
	}
	/**
	 * 获得按险种统计的数据(B2B订单数据)
	 * 
	 * @param dga
	 */
	public DataTable dg1DataBindB2B(DataGridAction dga,String channelSn) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String channelsn = dga.getParam("contant");
		//String allDate = dga.getParam("allDate");//统计全部标记 1表示统计全部
		// 撤单标识  1:撤单  2:对冲
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		if (StringUtil.isNotEmpty(startDate)) {
			parameter = " and tf.receiveDate >='" + startDate + "'";
		} else {
			parameter = " and tf.receiveDate >='" + yesterday + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter = parameter + " and tf.receiveDate <='" + endDate + tminutes+"'";
		} else {
			parameter = parameter + " and tf.receiveDate <='" + today + tminutes+"'";
		}
		
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			parameter = parameter + " and s.ChannelSn in ("+channel+")";
		}
		StringBuffer sql = new StringBuffer();
		QueryBuilder qb = null;
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT COUNT(s.id) AS COUNT,(SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera, ");
			sql.append(" FORMAT(SUM(s.totalAmount), 2) AS totalAmount,FORMAT(SUM((s.totalAmount * e.rate)), 2) AS charge,FORMAT(AVG((s.totalAmount * e.rate)), 2) AS AVG");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdproduct e  ");
			sql.append(" WHERE s.orderstatus IN ('2', '9','10','13','14') AND s.id = tf.ordID AND s.productid = e.RiskCode AND s.prop2 NOT LIKE 'BG%'");
			sql.append(parameter);
			sql.append(" GROUP BY ProductGenera order by ProductGenera desc");
			qb = new QueryBuilder(sql.toString());
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT SUM(COUNT) AS COUNT,ProductGenera,FORMAT(SUM(-totalAmount),2) AS totalAmount,FORMAT(SUM(-charge),2) AS charge,FORMAT(SUM(-AVG),2) AS AVG FROM");
			sql.append(" (SELECT COUNT(DISTINCT(re.orderSn)) AS COUNT,(SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,");
			sql.append(" SUM(om.PaymentFee) AS totalAmount,SUM(om.PaymentFee * e.rate) AS charge,AVG(om.PaymentFee * e.rate) AS AVG ");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om ");
			sql.append(" WHERE om.prop1 = re.id AND re.appStatus>=2 AND s.id = tf.ordID ");
			sql.append(" AND s.productid = e.RiskCode AND re.orderSn = s.id AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='')");
			sql.append(parameter);
			sql.append(" GROUP BY ProductGenera ");
			sql.append(" UNION ALL");
			sql.append(" SELECT COUNT(DISTINCT(re.orderSn)) AS COUNT,(SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera,");
			sql.append(" SUM(om.PaymentFee) AS totalAmount,SUM(om.PaymentFee * e.rate) AS charge,AVG(om.PaymentFee * e.rate) AS AVG");
			sql.append(" FROM sdorders s,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om WHERE om.prop1 = re.id");
			sql.append(" AND re.appStatus>=2 AND s.id = tf.ordID AND p.os= s.id AND s.productid = e.RiskCode AND re.orderSn = s.id AND s.prop2 LIKE 'BG%'");
			sql.append(parameter);
			sql.append(" GROUP BY ProductGenera) m GROUP BY ProductGenera order by ProductGenera desc");
			qb = new QueryBuilder(sql.toString());
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT COUNT(DISTINCT(re.orderSn)) AS COUNT,(SELECT CodeName FROM ZDCode WHERE CodeType='QueryProductType' AND CodeValue = SUBSTRING(EdorFlag, 1, 1)) AS ProductGenera, ");
			sql.append(" FORMAT(SUM(om.PaymentFee),2) AS totalAmount,FORMAT(SUM(om.PaymentFee * e.rate), 2) AS charge,FORMAT(AVG(om.PaymentFee * e.rate), 2) AS AVG ");
			sql.append(" FROM sdorders s,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om");
			sql.append(" WHERE s.productid = e.RiskCode AND re.orderSn = s.id ");
			sql.append(" AND om.prop1 = re.id AND s.id = tf.ordID");
			sql.append(" AND s.orderstatus IN ('2', '9','10','13','14') AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='')");
			sql.append(parameter);
			sql.append(" GROUP BY ProductGenera order by ProductGenera desc");
			qb = new QueryBuilder(sql.toString());
		}
		DataTable dt = qb.executeDataTable("B2B");
		return dt;
		// 添加总计
		/*BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : dt) {
			sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
			sum2 = sum2.add(objToBigDec(dr.get("charge")));
			count_all = count_all + Integer.valueOf(dr.get("count").toString());
		}
		Object[] catalogRow = { count_all, "总计", sum1, sum2, divBigDec(sum2, new BigDecimal(count_all)) };
		dt.insertRow(catalogRow);*/
		//dga.bindData(dt);
	}
	/**
	 * 获得按保险公司统计的数据
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		//String allDate = dga.getParam("allDate");//统计全部标记 1表示统计全部
		String channelsn = dga.getParam("contant");
		String channelsnb2b = channelsn;
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		/*if("b2b".equals(channelsn)){
			dg2DataBindB2B(dga);
			return;
		}*/
		// 撤单标识  1:撤单  2:对冲
		DataTable b2bDT = dg2DataBindB2B(dga,channelsnb2b);
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
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		StringBuffer sql = new StringBuffer();
		QueryBuilder qb = null;
		if ("0".equals(cancelFlag)) {
			sql.append(" select  count(1) as count,c.Remark6 as Remark6,c.insurename as insuranceCompany ,FORMAT(sum(a.totalAmount),2) as totalAmount,FORMAT(sum(a.totalAmount*c.feerate),2) as charge ,FORMAT(CASE WHEN COUNT(1)!=0 THEN SUM(a.totalAmount*c.feerate)/COUNT(1) ELSE 0 END ,2) AS AVG ");
			sql.append(" from sdorders a,sdinformation b, sdproduct c ,SDOrderItem d ");
			sql.append(" left join tradeinformation tf on tf.ordid=d.ordersn  ");
			sql.append(" where a.paySn not like 'BG%' and a.ordersn =b.ordersn and b.productid = c.productid and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null) ");
			sql.append(" and a.orderStatus >=7 AND a.orderStatus<>'8' and c.insurename!='' ");
			sql.append(parameter);
			sql.append(" group by b.insuranceCompany order by SUM(a.totalAmount),COUNT(1)");
			qb = new QueryBuilder(sql.toString());
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT SUM(COUNT) AS COUNT,Remark6,insuranceCompany,FORMAT(SUM(-totalAmount),2) AS totalAmount,FORMAT(SUM(-charge),2) AS charge,FORMAT(SUM(-AVG),2) AS AVG FROM (");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT, c.Remark6 as Remark6,c.insurename AS insuranceCompany ,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,");
			sql.append(" CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG   FROM  sdinformation b, sdproduct c,sdinformationrisktype sdor   ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID ");
			sql.append(" AND c.insurename!='' AND appstatus >=2 ");
			sql.append(" AND a.paySn NOT LIKE 'BG%' AND (sdor.changeStatus IS NULL OR sdor.changeStatus='') ");
			sql.append(parameter);
			sql.append(" GROUP BY b.insuranceCompany ");
			sql.append(" UNION ALL");
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT,c.Remark6 as Remark6, c.insurename AS insuranceCompany ,SUM(sdor.timePrem) AS totalAmount,SUM(sdor.timePrem*c.feerate) AS charge ,");
			sql.append(" CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END AVG FROM  sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p, sdproduct c,sdinformationrisktype sdor");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND p.os= b.orderSn AND c.insurename!='' AND appstatus >=2 AND a.paySn LIKE 'BG%' ");
			sql.append(parameter);
			sql.append(" GROUP BY b.insuranceCompany ) m GROUP BY insuranceCompany  ORDER BY SUM(totalAmount),COUNT(1)");
			qb = new QueryBuilder(sql.toString());
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT   COUNT(DISTINCT sdor.orderSn) AS COUNT, c.Remark6 as Remark6,c.insurename AS insuranceCompany ,");
			sql.append(" FORMAT(SUM(sdor.timePrem),2) AS totalAmount,FORMAT(SUM(sdor.timePrem*c.feerate),2) AS charge ,");
			sql.append(" FORMAT(CASE WHEN COUNT(DISTINCT sdor.orderSn)!=0 THEN SUM(sdor.timePrem*c.feerate)/COUNT(DISTINCT sdor.orderSn) ELSE 0 END ,2) AS AVG  FROM  sdinformation b, sdproduct c,sdinformationrisktype sdor ");
			sql.append(" LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID ");
			sql.append(" AND c.insurename!='' AND a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='')  ");
			sql.append(parameter);
			sql.append(" GROUP BY b.insuranceCompany ORDER BY SUM(a.totalAmount),COUNT(1)");
			qb = new QueryBuilder(sql.toString());
		}
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 end
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
					if(b2brow.getString("Remark6").equals(trow.getString("Remark6"))){
						tflag = true;
						trow.set("COUNT",Integer.parseInt(b2brow.getString("COUNT").replace(",", ""))+Integer.parseInt(trow.getString("COUNT").replace(",", "")));
						trow.set("Remark6",trow.getString("Remark6"));
						trow.set("insuranceCompany",trow.getString("insuranceCompany"));
						trow.set("totalAmount",Double.parseDouble(b2brow.getString("totalAmount").replace(",", ""))+Double.parseDouble(trow.getString("totalAmount").replace(",", "")));
						trow.set("charge",Double.parseDouble(b2brow.getString("charge").replace(",", ""))+Double.parseDouble(trow.getString("charge").replace(",", "")));	
						trow.set("AVG",new BigDecimal(trow.getString("charge").replace(",", "")).divide(new BigDecimal(trow.getString("COUNT").replace(",", "")),2,BigDecimal.ROUND_HALF_UP));
					}
				}
				if(!tflag){
					dt.insertRow(b2brow);
				}
				
			}
		}
		// 添加总计
		BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : dt) {
			sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
			sum2 = sum2.add(objToBigDec(dr.get("charge")));
			count_all = count_all + Integer.valueOf(dr.get("count").toString());
		}
		Object[] catalogRow = { count_all, "","总计", sum1, sum2, divBigDec(sum2, new BigDecimal(count_all)) };
		dt.insertRow(catalogRow);
		dga.bindData(dt);
	}
	/**
	 * 获得按保险公司统计的数据(B2B订单数据)
	 * 
	 * @param dga
	 */
	public DataTable dg2DataBindB2B(DataGridAction dga,String channelSn) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		//String allDate = dga.getParam("allDate");//统计全部标记 1表示统计全部
		// 撤单标识  1:撤单  2:对冲
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		if (StringUtil.isNotEmpty(startDate)) {
			parameter = " and tf.receiveDate >='" + startDate + "'";
		} else {
			parameter = " and tf.receiveDate >='" + yesterday + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter = parameter + " and tf.receiveDate <='" + endDate + tminutes+"'";
		} else {
			parameter = parameter + " and tf.receiveDate <='" + today + tminutes+"'";
		}
		String channelsn = dga.getParam("contant");
		if(StringUtil.isNotEmpty(channelsn)){
			String channel = QueryUtil.getChannelInfo(channelsn, "b2b");
			if(StringUtil.isEmpty(channel)){
				return null;
			}
			parameter = parameter + " and s.ChannelSn in ("+channel+")";
		}
		StringBuffer sql = new StringBuffer();
		QueryBuilder qb = null;
		if ("0".equals(cancelFlag)) {
			sql.append(" SELECT COUNT(1) AS count,e.InsuranceCompanySn as Remark6,e.InsuranceCompany AS insuranceCompany,FORMAT(SUM(s.totalAmount), 2) AS totalAmount,FORMAT(SUM((s.totalAmount * e.rate)), 2) AS charge,FORMAT(AVG((s.totalAmount * e.rate)), 2) AS avg ");
			sql.append(" FROM sdorders s, sdtradeinformation tf,sdproduct e ");
			sql.append(" WHERE s.orderstatus IN ('2', '9','10','13','14') AND s.id = tf.ordID AND s.productid = e.RiskCode AND s.prop2 NOT LIKE 'BG%'");
			sql.append(parameter);
			sql.append(" GROUP BY insuranceCompany");
			qb = new QueryBuilder(sql.toString());
		}else if("1".equals(cancelFlag)){
			sql.append(" SELECT  SUM(COUNT) AS COUNT,Remark6,insuranceCompany,FORMAT(SUM(-totalAmount),2) AS totalAmount,FORMAT(SUM(-charge),2) AS charge,FORMAT(SUM(-AVG),2) AS AVG FROM ( ");
			sql.append(" SELECT COUNT(DISTINCT re.orderSn) AS COUNT, e.InsuranceCompanySn as Remark6,e.InsuranceCompany AS insuranceCompany,SUM(om.PaymentFee) AS totalAmount,SUM(om.PaymentFee * e.rate) AS charge, ");
			sql.append(" AVG(om.PaymentFee * e.rate) AS AVG  FROM sdorders s,sdtradeinformation tf,sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om WHERE om.prop1 = re.id");
			sql.append(" AND s.id = tf.ordID  AND s.productid = e.RiskCode AND re.orderSn = s.id AND re.appStatus>=2 AND s.prop2 NOT LIKE 'BG%' AND (re.changeStatus IS NULL OR re.changeStatus='')");
			sql.append(parameter);
			sql.append(" GROUP BY insuranceCompany ");
			sql.append(" UNION ALL");
			sql.append(" SELECT COUNT(re.orderSn) AS COUNT,e.InsuranceCompanySn as Remark6,e.InsuranceCompany AS insuranceCompany,SUM(om.PaymentFee) AS totalAmount,SUM(om.PaymentFee * e.rate) AS charge, ");
			sql.append(" AVG(om.PaymentFee * e.rate) AS AVG  FROM sdorders s ,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p,sdtradeinformation tf,");
			sql.append(" sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om WHERE om.prop1 = re.id AND s.id = tf.ordID  AND s.productid = e.RiskCode AND re.orderSn = s.id");
			sql.append(" and p.os= s.id AND re.appStatus>=2 AND s.prop2 LIKE 'BG%' ");
			sql.append(parameter);
			sql.append("  GROUP BY insuranceCompany ) m GROUP BY insuranceCompany ");
			qb = new QueryBuilder(sql.toString());
		}else if("2".equals(cancelFlag)){
			sql.append(" SELECT COUNT(DISTINCT re.orderSn) AS COUNT,e.InsuranceCompanySn as Remark6,e.InsuranceCompany AS insuranceCompany,");
			sql.append(" FORMAT(SUM(om.PaymentFee),2) AS totalAmount,FORMAT(SUM(om.PaymentFee * e.rate), 2) AS charge,");
			sql.append(" FORMAT(AVG(om.PaymentFee * e.rate), 2) AS AVG  FROM sdorders s,sdtradeinformation tf,");
			sql.append(" sdproduct e,sdinsuredcompanyreturndata re,sdorderitem om WHERE om.prop1 = re.id");
			sql.append(" AND s.orderstatus IN ('2', '9','10','13','14') AND (re.appStatus <2 OR re.appStatus IS NULL OR re.appStatus='')  AND s.id = tf.ordID ");
			sql.append(" AND s.productid = e.RiskCode AND re.orderSn = s.id ");
			sql.append(parameter);
			sql.append(" GROUP BY insuranceCompany");
			qb = new QueryBuilder(sql.toString());
		}
		DataTable dt = qb.executeDataTable("B2B");
		return dt;
		/*// 添加总计
		BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : dt) {
			sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
			sum2 = sum2.add(objToBigDec(dr.get("charge")));
			count_all = count_all + Integer.valueOf(dr.get("count").toString());
		}
		Object[] catalogRow = { count_all, "总计", sum1, sum2, divBigDec(sum2, new BigDecimal(count_all)) };
		dt.insertRow(catalogRow);*/
		//dga.bindData(dt);
	}
	/**
	 * 获得按城市统计的数据
	 * 
	 * @param dga
	 */
	public void dg3DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		//String allDate = dga.getParam("allDate");//统计全部标记 1表示统计全部
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		// 撤单标识  1:撤单
		String cancelFlag = dga.getParam("cancelFlag");
		String parameter = "";
		if (StringUtil.isNotEmpty(startDate)) {
			parameter = " and tf.receiveDate >='" + startDate + "'";
		} else {
			parameter = " and tf.receiveDate >='" + yesterday + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter = parameter + " and tf.receiveDate <='" + endDate +tminutes+ "'";
		} else {
			parameter = parameter + " and tf.receiveDate <='" + today +tminutes+ "'";
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = orders.channelsn and ChannelCode IN ("+channel+"))";

		}
		//原表SQL
		/*String sql = "select ipseeker.orderIP 'ip',FORMAT(orders.productTotalPrice,2) 'productTotalPrice',FORMAT((orders.productTotalPrice*a2.feerate),2) 'charge' from Orders orders left join sdproduct a2 on (orders.productid=a2.productid) left join ipseeker ipseeker on orders.ordersn = ipseeker.orderSn where orders.orderstatus='7' and orders.insurancecompany!='百年人寿'"
			+ parameter;*/
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 start
		StringBuffer sql = new StringBuffer();
		QueryBuilder qb = null;
		if (!"1".equals(cancelFlag)) {
			sql.append("select ipseeker.orderIP 'ip',FORMAT(orders.totalAmount,2) 'totalAmount',FORMAT((orders.totalAmount*a2.feerate),2) 'charge' from SDOrders orders left join SDInformation info on (orders.ordersn = info.ordersn) left join sdproduct a2 on (info.productid=a2.productid) left join ipseeker ipseeker on orders.ordersn = ipseeker.orderSn left join SDOrderItem d on orders.ordersn=d.ordersn ");
			sql.append(" left join tradeinformation tf on orders.ordersn=tf.ordid ");
			sql.append(" where orders.paySn not like 'BG%' and orders.orderStatus >=7 AND orders.orderStatus<>'8' and (d.channelCode != '02' or d.channelCode is null) and orders.ordersn ");
			sql.append(parameter);
			qb = new QueryBuilder(sql.toString());
		}else{
			sql.append(" SELECT ip,FORMAT(SUM(-totalAmount),2) AS totalAmount,FORMAT(SUM(-charge),2) AS charge FROM (");
			sql.append(" SELECT ipseeker.orderIP 'ip',sdor.timePrem AS 'totalAmount',sdor.timePrem*c.feerate AS 'charge',orders.ordersn ");
			sql.append(" FROM  sdinformation b, sdproduct c,ipseeker ipseeker,sdinformationrisktype sdor");
			sql.append(" LEFT JOIN sdorders orders ON sdor.ordersn = orders.ordersn LEFT JOIN tradeinformation tf ON orders.ordersn = tf.ordid ");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND orders.ordersn = ipseeker.orderSn AND appstatus >=2 ");
			sql.append(" AND orders.paySn NOT LIKE 'BG%' AND (sdor.changeStatus IS NULL OR sdor.changeStatus='') ");
			sql.append(parameter);
			sql.append(" GROUP BY orders.ordersn");
			sql.append(" UNION ALL ");
			sql.append(" SELECT ipseeker.orderIP 'ip',sdor.timePrem AS 'totalAmount',sdor.timePrem*c.feerate AS 'charge',orders.ordersn ");
			sql.append(" FROM  sdinformation b,( SELECT MAX(newordersn) os FROM  policychangeinfo  GROUP BY initOrderSn ) p, sdproduct c,ipseeker ipseeker,sdinformationrisktype sdor  ");
			sql.append(" LEFT JOIN sdorders orders ON sdor.ordersn = orders.ordersn LEFT JOIN tradeinformation tf ON orders.ordersn = tf.ordid");
			sql.append(" WHERE  sdor.ordersn=b.orderSn AND b.productId=c.ProductID AND p.os= b.orderSn AND orders.ordersn = ipseeker.orderSn AND appstatus >=2 AND orders.paySn LIKE 'BG%'");
			sql.append(parameter);
			sql.append(" GROUP BY orders.ordersn ) m GROUP BY ordersn ");
			qb = new QueryBuilder(sql.toString());
		}
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 end
		DataTable dt = qb.executeDataTable();
		IPSeeker ipseeker = new IPSeeker();
		IPLocation iplocation = new IPLocation();

		DataTable dt3 = new DataTable();
		DataColumn city = new DataColumn("city", DataColumn.STRING);
		DataColumn count = new DataColumn("count", DataColumn.INTEGER);
		DataColumn totalAmount = new DataColumn("totalAmount", DataColumn.DECIMAL);
		DataColumn charge = new DataColumn("charge", DataColumn.DECIMAL);
		DataColumn avg = new DataColumn("avg", DataColumn.DECIMAL);
		dt3.insertColumn(city);
		dt3.insertColumn(count);
		dt3.insertColumn(totalAmount);
		dt3.insertColumn(charge);
		dt3.insertColumn(avg);
		for (DataRow dr : dt) {
			iplocation = null;
			if(StringUtil.isNotEmpty(dr.get("ip")+"")){
				String ip[] = dr.get("ip").toString().split(",");
				if(IsIPv4Address(ip[0])){
					iplocation = ipseeker.getIPLocation(ip[0]);
					int index = getcityindex(getcityname(iplocation.getCountry()), dt3);
					if (index >= 0) {
						dt3.set(index, "count", Integer.valueOf(dt3.get(index, "count").toString()) + 1);
						dt3.set(index,
								"totalAmount",
								objToBigDec(dt3.get(index, "totalAmount")).add(
										objToBigDec(dr.get("totalAmount"))));
						dt3.set(index, "charge", objToBigDec(dt3.get(index, "charge")).add(objToBigDec(dr.get("charge"))));
					} else {
						Object[] catalogRow = { getcityname(iplocation.getCountry()), 1,
								objToBigDec(dr.get("totalAmount")), objToBigDec(dr.get("charge")), new BigDecimal(0) };
						dt3.insertRow(catalogRow);
					}
				}
			}
		}
		ipseeker.closeFile();
		dt3.sort("count");
		// 去掉20条数据之后的数据
		if (dt3.getRowCount() > 20) {
			for (int i = 20; i < dt3.getRowCount(); i++)
				dt3.deleteRow(i);
		}
		// 添加总计
		BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		int count_all = 0;
		for (DataRow dr : dt3) {
			dr.set("avg", divBigDec(objToBigDec(dr.get("charge")), objToBigDec(dr.get("count"))));
			sum1 = sum1.add(objToBigDec(dr.get("totalAmount")));
			sum2 = sum2.add(objToBigDec(dr.get("charge")));
			count_all = count_all + Integer.valueOf(dr.get("count").toString());
		}
		Object[] catalogRow = { "总计", count_all, sum1, sum2, divBigDec(sum2, new BigDecimal(count_all)) };
		dt3.insertRow(catalogRow);
		dga.bindData(dt3);
	}
	/**
	 * 获得按险种统计的数据
	 * 
	 * @param dga
	 */
	public void dg6DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		QueryBuilder qb = new QueryBuilder(" DROP TEMPORARY TABLE IF EXISTS tmp_table ");
		qb.executeNoQuery();
		qb = new QueryBuilder(" CREATE TEMPORARY TABLE tmp_table(memberid VARCHAR(32),totalAmount VARCHAR(100),createDate VARCHAR(32)) ");
		qb.executeNoQuery();
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
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		StringBuffer sql = new StringBuffer();
		if ("0".equals(cancelFlag)) {
			sql.append(" INSERT INTO tmp_table(memberid,totalAmount,createDate) SELECT a.memberid,a.totalAmount,DATE_FORMAT(tf.receiveDate,'%Y-%m-%d') createDate");
			sql.append(" from sdorders a,SDOrderItem d");
			sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid");
			sql.append(" where a.paySn not like 'BG%' and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null)  and a.orderStatus >=7 AND a.orderStatus<>'8' ");
			sql.append(parameter);
			qb = new QueryBuilder(sql.toString());
		}else if("2".equals(cancelFlag)){
			sql.append(" INSERT INTO tmp_table(memberid,totalAmount,createDate) SELECT a.memberid,sdor.timeprem,DATE_FORMAT(tf.receiveDate,'%Y-%m-%d') createDate");
			sql.append(" FROM sdinformationrisktype sdor LEFT JOIN sdorders a ON sdor.ordersn = a.ordersn LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid");
			sql.append(" WHERE a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='')");
			sql.append(parameter);
			qb = new QueryBuilder(sql.toString());
		}
		qb.executeNoQuery();
		QueryBuilder qb1 = new QueryBuilder(" SELECT CodeValue,CodeName,Prop1,Prop2 FROM zdcode WHERE CodeType='AmountRange' ORDER BY CodeOrder ");
		DataTable dt1 = qb1.executeDataTable();
		int Len1 = dt1.getRowCount();
		DataTable dt = new DataTable();
		int memberCount1 = 0;
		BigDecimal totalAmountSum1 = new BigDecimal("0.00");
		for(int i=0;i<Len1;i++){
			QueryBuilder qb2 = new QueryBuilder(" SELECT COUNT(DISTINCT memberid),FORMAT(IFNULL(SUM(totalAmount),0),2) FROM tmp_table   GROUP BY createDate,memberid HAVING SUM(totalAmount)>="+dt1.getString(i, "Prop1")+" AND SUM(totalAmount)<"+dt1.getString(i, "Prop2")+" ");
			DataTable dt2 = qb2.executeDataTable();
			int memberCount = 0;
			BigDecimal totalAmountSum = new BigDecimal("0.00");
			if(dt2!=null && dt2.getRowCount()>=1){
				for(int k=0;k<dt2.getRowCount();k++){
					memberCount = memberCount+Integer.parseInt(dt2.getString(k, 0).replaceAll(",", ""));
					totalAmountSum = totalAmountSum.add(new BigDecimal(dt2.getString(k, 1).replaceAll(",", "")));
					memberCount1 = memberCount1+Integer.parseInt(dt2.getString(k, 0).replaceAll(",", ""));
					totalAmountSum1 = totalAmountSum1.add(new BigDecimal(dt2.getString(k, 1).replaceAll(",", "")));
				}
			}
			Object[] catalogRow = {dt1.getString(i, "CodeName"),memberCount,totalAmountSum,dt1.getString(i, "CodeValue")};
			dt.insertRow(catalogRow);
		}
		Object[] catalogRow = {"合计",memberCount1,totalAmountSum1,"Range"};
		dt.insertRow(catalogRow);
		qb = new QueryBuilder(" DROP TEMPORARY TABLE IF EXISTS tmp_table ");
		qb.executeNoQuery();
		dga.bindData(dt);
	}
	public static void dg6DataBind_UerList(DataGridAction dga) {
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");
		String cPrice = dga.getParams().getString("cPrice");
		QueryBuilder qb5 = new QueryBuilder(" SELECT Prop1,Prop2 FROM zdcode WHERE CodeType='AmountRange' AND CodeValue=? ",cPrice);
		DataTable dt5 = qb5.executeDataTable();
		
		String parameter = "";
		String channelsn = dga.getParam("contant");
		// 撤单标识  1:撤单  2:对冲
		String cancelFlag = dga.getParam("cancelFlag");
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
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT email,mobileNo,realName,id,createDate,loginDate,SUM(orderCount) orderCount,FORMAT(SUM(totalAmount),2) totalAmount FROM ( ");
		if ("0".equals(cancelFlag)) {
			sql.append(" select m.email,mobileNo,realName,m.id,m.createDate,m.loginDate,count(distinct DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')) orderCount,sum(a.totalAmount) totalAmount from member m,sdorders a,SDOrderItem d");
			sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid");
			sql.append(" where m.id=a.memberid and a.paySn not like 'BG%' and a.ordersn = d.ordersn and (d.channelCode != '02' or d.channelCode is null)  and a.orderStatus >=7 AND a.orderStatus<>'8' ");
			sql.append(parameter);
			sql.append(" GROUP By m.id ,DATE_FORMAT(tf.receiveDate, '%Y-%m-%d') HAVING ");
			sql.append(" SUM(a.totalAmount) >="+dt5.getString(0, "Prop1")+" AND SUM(a.totalAmount) <"+dt5.getString(0, "Prop2"));
		}else if("2".equals(cancelFlag)){
			sql.append(" select m.email,mobileNo,realName,m.id,m.createDate,m.loginDate,count(distinct DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')) orderCount,FORMAT(sum(sdor.timeprem),2) totalAmount From ");
			sql.append(" member m,sdinformationrisktype sdor,sdorders a LEFT JOIN tradeinformation tf ON a.ordersn = tf.ordid");
			sql.append(" WHERE m.id=a.memberid and sdor.ordersn = a.ordersn and a.orderstatus >=7 AND a.orderstatus <> '8' AND (appstatus <2 OR appstatus IS NULL OR appstatus='')");
			sql.append(parameter);
			sql.append(" GROUP By m.id,DATE_FORMAT(tf.receiveDate, '%Y-%m-%d') having sum(sdor.timeprem) >="+dt5.getString(0, "Prop1")+" and sum(sdor.timeprem) <"+dt5.getString(0, "Prop2"));
		}
		sql.append(" ) info GROUP BY info.id ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	public static void dg7DataBind_UerList(DataGridAction dga) {
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate")+" 23:59:59";
		String cPrice = dga.getParams().getString("cPrice");//回购标记
		String parameter = "";
		String channelsn = dga.getParam("contant");
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		StringBuffer sql = new StringBuffer();
		//回购数据
		if("0".equals(cPrice)){
			sql.append(" select m.*,'' days from member m WHERE exists (SELECT 1 ");
			sql.append(" FROM SDOrderItem f,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
			sql.append(" WHERE m.id=a.memberid and a.paySn NOT LIKE 'BG%' AND f.ordersn=a.ordersn AND a.orderStatus >=7 AND a.orderStatus<>'8' AND a.memberid IS NOT NULL AND a.memberid !='' ");
			sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
			sql.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
			sql.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
			sql.append(" AND NOT EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
			sql.append(" AND NOT EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
			sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
			sql.append(" AND a.orderstatus >=7 AND a.orderstatus!=8");
			sql.append(parameter);
			sql.append(" ) ");
		}else if("1".equals(cPrice)){
			//订单数据
			sql.append(" select m.*,'' days from member m WHERE exists (SELECT 1 ");
			sql.append(" FROM SDOrderItem f,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid  ");
			sql.append(" WHERE m.id=a.memberid and a.paySn NOT LIKE 'BG%' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
			sql.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
			sql.append(" AND tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' ");
			sql.append(" AND a.orderstatus >=7 AND a.orderstatus!=8");
			sql.append(parameter);		
			sql.append(" ) ");
		}
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		for(int k=0;k<dt.getRowCount();k++){
			String memberid = dt.getString(k, "id");
			String ordermindate = new QueryBuilder("SELECT DATE_FORMAT(MIN(a.createdate),'%Y-%m-%d') FROM sdorders a LEFT JOIN tradeinformation tf ON a.ordersn=tf.ordid WHERE tf.receiveDate <='"+endDate+"'  AND tf.receiveDate >='"+startDate+"' AND a.memberid='"+memberid+"'").executeString();
			String ordermaxdate = new QueryBuilder("SELECT DATE_FORMAT(MAX(b.createdate),'%Y-%m-%d') FROM sdorders b WHERE b.createdate<=DATE_ADD('"+startDate+"', INTERVAL -30 DAY) AND b.memberid='"+memberid+"'").executeString();
			String regdate  = new QueryBuilder("SELECT DATE_FORMAT(b.createdate,'%Y-%m-%d') FROM member b WHERE b.id = '"+memberid+"' AND b.createdate<=DATE_ADD('"+startDate+"', INTERVAL -30 DAY)").executeString();
			
			FDate fDate = new FDate();
			Date a1 = fDate.getDate(ordermindate);
			Date a2 = fDate.getDate(ordermaxdate);
			Date a3 = fDate.getDate(regdate);
			int days = 1;
			if("0".equals(cPrice)){
				try {
					if(StringUtil.isEmpty(a2) || StringUtil.isEmpty(a3)){
						if(!StringUtil.isEmpty(a2)){
							days =com.sinosoft.sms.messageinterface.pubfun.PubFun.daysBetween(a2, a1);
						}else if(!StringUtil.isEmpty(a3)){
							days =com.sinosoft.sms.messageinterface.pubfun.PubFun.daysBetween(a3, a1);
						}
					}else{
						if(a2.getTime()>=a3.getTime()){
							days =com.sinosoft.sms.messageinterface.pubfun.PubFun.daysBetween(a2, a1);
						}else{
							days =com.sinosoft.sms.messageinterface.pubfun.PubFun.daysBetween(a3, a1);
						}
					}

				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				dt.get(k).set("days", days);
			}else{
				dt.get(k).set("days", "");
			}

		}
		dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		// 是否启用
		DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?",
				"isAccountEnabled").executeDataTable();
		dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
		// 等级
		DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
		dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
		dga.bindData(dt);
	}
	private BigDecimal divBigDec(BigDecimal a, BigDecimal b) {
		if (b.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
		}
	}

	private BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
		}
	}

	private int getcityindex(String city, DataTable dt) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (city.equals(dt.get(i).getString("city"))) {
				return i;
			}
		}
		return -1;
	}

	private String getcityname(String address) {
		int sheng = address.indexOf("省");
		int shi = address.indexOf("市");
		if (shi > 0) {
			return StringUtils.substring(address, sheng + 1, shi);
		} else {
			return address;
		}
	}

	/**
	 * 获得发布统计的数据
	 * 
	 * @param dga
	 */
	public void dg4DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		String parameter = "";
		if (!"1".equals(allDate)) {
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')>='" + startDate + "'";
			} else {
				parameter = " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')>='" + yesterday + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')<='" + endDate + "'";
			} else {
				parameter = parameter + " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')<='" + today + "'";
			}
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = orders.channelsn and ChannelCode IN ("+channel+"))";

		}
		//原表SQL
		/*String sql = "select sum(case when t.count>=2 then 1 else 0 end) as 'dayu2',sum(case when t.count>=3 then 1 else 0 end) as 'dayu3',sum(case when t.count>=5 then 1 else 0 end) as 'dayu5',sum(case when t.count>=10 then 1 else 0 end) as 'dayu10' from (select count(1) count,orders.memberId  from Orders orders where orders.orderstatus='7' and orders.insurancecompany!='百年人寿'"
			+ parameter + " and orders.memberId is not null group by orders.memberId  ) t";*/
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 start
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(case when t.memberId is null then 1 else 0 end) as 'dayu0',");
		sql.append("sum(case when t.count=1 then 1 else 0 end) as 'dayu1',");
		sql.append("sum(case when t.count>=2 then 1 else 0 end) as 'dayu2',");
		sql.append("sum(case when t.count>=3 then 1 else 0 end) as 'dayu3',");
		sql.append("sum(case when t.count>=5 then 1 else 0 end) as 'dayu5',");
		sql.append("sum(case when t.count>=10 then 1 else 0 end) as 'dayu10' ");
		sql.append("from member m left join ");
		sql.append("(select count(1) count,orders.memberId as 'memberId' ");
		sql.append("from SDOrders orders left join SDInformation info on (orders.ordersn = info.ordersn) right join member m on (orders.memberid = m.id) left join SDOrderItem d on orders.ordersn=d.ordersn ");
		sql.append(" left join tradeinformation tf on d.ordersn=tf.ordid ");
		sql.append(" where  orders.orderstatus='7' and (d.channelCode != '02' or d.channelCode is null) and orders.ordersn ");
		sql.append(parameter);
		sql.append(" and orders.memberId is not null and  orders.memberId<>'' and m.id is not null and m.id<>'' group by orders.memberId ) t on (m.id=t.memberId)");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		StringBuffer sql1 = new StringBuffer();
		sql1.append("select count(orders.memberId) count,FORMAT(sum(orders.totalAmount),2) 'totalAmount' ,FORMAT(sum(orders.totalAmount*a2.feerate),2) 'charge' ");
		sql1.append("from SDOrders orders left join SDInformation info on (orders.ordersn = info.ordersn) left join sdproduct a2 on (info.productid=a2.productid) join member m on (orders.memberid = m.id)   " );
		sql1.append(" left join tradeinformation tf on orders.ordersn=tf.ordid ");	
		sql1.append(" where  orders.orderstatus='7' and a2.InsureName!='百年人寿' ");
		sql1.append(parameter);
		sql1.append(" and orders.memberId is not null and  orders.memberId<>'' and m.id is not null and m.id<>'' group by orders.memberId ");
		QueryBuilder qb1 = new QueryBuilder(sql1.toString());
		DataTable dt1 = qb1.executeDataTable();
		
		//计算订单数
		int orderCount1 = 0;
		int orderCount2 = 0;
		int orderCount3 = 0;
		int orderCount5 = 0;
		int orderCount10 = 0;
		//计算保费
		BigDecimal totalAmount1 = new BigDecimal("0.00");
		BigDecimal totalAmount2 = new BigDecimal("0.00");
		BigDecimal totalAmount3 = new BigDecimal("0.00");
		BigDecimal totalAmount5 = new BigDecimal("0.00");
		BigDecimal totalAmount10 = new BigDecimal("0.00");
		//计算手续费
		BigDecimal charge1 = new BigDecimal("0.00");
		BigDecimal charge2 = new BigDecimal("0.00");
		BigDecimal charge3 = new BigDecimal("0.00");
		BigDecimal charge5 = new BigDecimal("0.00");
		BigDecimal charge10 = new BigDecimal("0.00");
		//计算会员数
		int memberCount0 = Integer.parseInt(nullToZero(dt.getString(0, "dayu0")));
		int memberCount1 = Integer.parseInt(nullToZero(dt.getString(0, "dayu1")));
		int memberCount2 = Integer.parseInt(nullToZero(dt.getString(0, "dayu2")));
		int memberCount3 = Integer.parseInt(nullToZero(dt.getString(0, "dayu3")));
		int memberCount5 = Integer.parseInt(nullToZero(dt.getString(0, "dayu5")));
		int memberCount10 = Integer.parseInt(nullToZero(dt.getString(0, "dayu10")));
		
		for (int i = 0; i < dt1.getRowCount(); i++) {
			int count = dt1.getInt(i, "count");
			if(count == 1){
				totalAmount1 = objToBigDec(dt1.getString(i, "totalAmount")).add(totalAmount1);
				charge1 = objToBigDec(dt1.getString(i, "charge")).add(charge1);
				orderCount1 = orderCount1 + dt1.getInt(i, "count");
			}
			if(count >= 10){
				totalAmount10 = objToBigDec(dt1.getString(i, "totalAmount")).add(totalAmount10);
				charge10 = objToBigDec(dt1.getString(i, "charge")).add(charge10);
				orderCount10 = orderCount10 + dt1.getInt(i, "count");
			}
			if(count >= 5){
				totalAmount5 = objToBigDec(dt1.getString(i, "totalAmount")).add(totalAmount5);
				charge5 = objToBigDec(dt1.getString(i, "charge")).add(charge5);
				orderCount5 = orderCount5 + dt1.getInt(i, "count");
			}
			if(count >= 3){
				totalAmount3 = objToBigDec(dt1.getString(i, "totalAmount")).add(totalAmount3);
				charge3 = objToBigDec(dt1.getString(i, "charge")).add(charge3);
				orderCount3 = orderCount3 + dt1.getInt(i, "count");
			}
			if(count >= 2){
				totalAmount2 = objToBigDec(dt1.getString(i, "totalAmount")).add(totalAmount2);
				charge2 = objToBigDec(dt1.getString(i, "charge")).add(charge2);
				orderCount2 = orderCount2 + dt1.getInt(i, "count");
			}
		}
		
		//计算单据利润
		BigDecimal avg1 = new BigDecimal("0.00");
		if(orderCount1!=0){
			avg1 = charge1.divide((new BigDecimal(orderCount1)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg2 = new BigDecimal("0.00");
		if(orderCount2!=0){
			avg2 = charge2.divide((new BigDecimal(orderCount2)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg3 = new BigDecimal("0.00");
		if(orderCount3!=0){
			avg3 = charge3.divide((new BigDecimal(orderCount3)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg5 = new BigDecimal("0.00");
		if(orderCount5!=0){
			avg5 = charge5.divide((new BigDecimal(orderCount5)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg10 = new BigDecimal("0.00");
		if(orderCount10!=0){
			avg10 = charge10.divide((new BigDecimal(orderCount10)),2,BigDecimal.ROUND_HALF_UP);
		}
		
		DataTable dt2 = new DataTable();
		DataColumn type = new DataColumn("type", DataColumn.STRING);
		DataColumn count = new DataColumn("memberCount", DataColumn.STRING);
		DataColumn scale = new DataColumn("scale", DataColumn.STRING);
		DataColumn orderCount = new DataColumn("orderCount", DataColumn.STRING);
		DataColumn totalAmount = new DataColumn("totalAmount", DataColumn.STRING);
		DataColumn charge = new DataColumn("charge", DataColumn.STRING);
		DataColumn avg = new DataColumn("avg", DataColumn.STRING);
		
		
		int memberCountTotal = memberCount0 + memberCount1 + memberCount2;
		
		dt2.insertColumn(type);
		dt2.insertColumn(count);
		dt2.insertColumn(scale);
		dt2.insertColumn(orderCount);
		dt2.insertColumn(totalAmount);
		dt2.insertColumn(charge);
		dt2.insertColumn(avg);
		Object[] catalogRow1 = { new String("= 0"), memberCount0 , computePercent(memberCount0,memberCountTotal) , "-" , "-" , "-" , "-" };
		dt2.insertRow(catalogRow1);
		Object[] catalogRow2 = { new String("= 1"), memberCount1 , computePercent(memberCount1,memberCountTotal) , orderCount1 , totalAmount1 , charge1 , avg1 };
		dt2.insertRow(catalogRow2);
		Object[] catalogRow3 = { new String(">= 2"), memberCount2 , computePercent(memberCount2,memberCountTotal) , orderCount2 , totalAmount2 , charge2 , avg2 };
		dt2.insertRow(catalogRow3);
		Object[] catalogRow4 = { new String(">= 3"), memberCount3 , computePercent(memberCount3,memberCountTotal) , orderCount3 , totalAmount3 , charge3 , avg3 };
		dt2.insertRow(catalogRow4);
		Object[] catalogRow5 = { new String(">= 5"), memberCount5 , computePercent(memberCount5,memberCountTotal) , orderCount5 , totalAmount5 , charge5 , avg5 };
		dt2.insertRow(catalogRow5);
		Object[] catalogRow6 = { new String(">= 10"), memberCount10 , computePercent(memberCount10,memberCountTotal) , orderCount10 , totalAmount10 , charge10 , avg10 };
		dt2.insertRow(catalogRow6);
		dga.bindData(dt2);
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 end
	}
	/**
	 * 回购率
	 * 
	 * @param dga
	 */
	public void dg5DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String channelsn = dga.getParam("contant");
		
		if(StringUtil.isNotEmpty(channelsn)&&((channelsn.indexOf("xb2b_ht")>0)
				||(channelsn.indexOf("xb2c_pc")>0)||(channelsn.indexOf("xb2c_wap")>0))){
			channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht").
					replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
		}

		String parameter = "";
		if (!"1".equals(allDate)) {
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')>='" + startDate + "'";
			} else {
				parameter = " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')>='" + yesterday + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')<='" + endDate + "'";
			} else {
				parameter = parameter + " and DATE_FORMAT(tf.receiveDate,'%Y-%m-%d')<='" + today + "'";
			}
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			String channel = QueryUtil.getChannelInfo(channelsn,"");
			parameter = parameter + " and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";

		}
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) count,sum(t.totalAmount) as totalAmount,sum(t.charge) as charge,sum(t.pocount) as pocount from ");
		sql.append("(select a.memberid,sum(a.totalAmount) as totalAmount,sum(a.totalAmount*d.feerate) as charge , count(e.id) as pocount  ");
		sql.append("from sdorders a,sdinformation b,sdproduct d ,sdinformationinsured e, SDOrderItem  c");
		sql.append(" left join tradeinformation tf on c.ordersn=tf.ordid ");
		sql.append("where a.orderstatus=7 and a.ordersn=b.ordersn and a.ordersn = c.ordersn and e.ordersn=b.ordersn and b.productid=d.productid ");
		sql.append(" and (c.channelCode != '02' or c.channelCode is null) and memberid is not null and memberid<>'' ");
		sql.append(parameter);
		sql.append("  group by a.memberId ,DATE_FORMAT(a.modifyDate,'%Y-%m-%d') ) t group by t.memberid ");
		
		QueryBuilder qb1 = new QueryBuilder(sql.toString());
		DataTable dt1 = qb1.executeDataTable();
		QueryBuilder qb = new QueryBuilder("select count(1) from member ");
		DataTable dt = qb.executeDataTable();
		int memberCountTotal  = dt.getInt(0, 0);//会员总数
		//订单数
		int orderCount1 = 0;
		int orderCount2 = 0;
		int orderCount3 = 0;
		int orderCount5 = 0;
		int orderCount10 = 0;
		//计算保费
		double totalAmount1 = 0.0;
		double totalAmount2 = 0.0;
		double totalAmount3 = 0.0;
		double totalAmount5 = 0.0;
		double totalAmount10 = 0.0;
		//计算手续费
		double charge1 = 0.0;
		double charge2 = 0.0;
		double charge3 = 0.0;
		double charge5 = 0.0;
		double charge10 = 0.0;
		//计算会员数
		int memberCount1 = 0;
		int memberCount2 = 0;
		int memberCount3 = 0;
		int memberCount5 = 0;
		int memberCount10 = 0;
		int frequency  = 0;
		if(dt1!=null && dt1.getRowCount()>0){
			for(int i=0;i<dt1.getRowCount();i++){
				frequency = dt1.getInt(i, 0);
				if(frequency==1){
					memberCount1 = memberCount1+1;
					totalAmount1 = totalAmount1 + dt1.getDouble(i, 1);
					charge1 = charge1 + dt1.getDouble(i, 2);
					orderCount1 = orderCount1 + dt1.getInt(i, 3);
				}else{
					if(frequency>=2){
						memberCount2 = memberCount2+1;
						totalAmount2 = totalAmount2 + dt1.getDouble(i, 1);
						charge2 = charge2 + dt1.getDouble(i, 2);
						orderCount2 = orderCount2 + dt1.getInt(i, 3);
					}
					if(frequency>=3){
						memberCount3 = memberCount3+1;	
						totalAmount3 = totalAmount3 + dt1.getDouble(i, 1);
						charge3 = charge3 + dt1.getDouble(i, 2);
						orderCount3 = orderCount3 + dt1.getInt(i, 3);
					}
					if(frequency>=5){
						memberCount5 = memberCount5+1;
						totalAmount5 = totalAmount5 + dt1.getDouble(i, 1);
						charge5 = charge5 + dt1.getDouble(i, 2);
						orderCount5 = orderCount5 + dt1.getInt(i, 3);
					}
					if(frequency>=10){
						memberCount10 = memberCount10+1;
						totalAmount10 = totalAmount10 + dt1.getDouble(i, 1);
						charge10 = charge10 + dt1.getDouble(i, 2);
						orderCount10 = orderCount10 + dt1.getInt(i, 3);
					}
				}
			}
		}
		int memberCount0 = memberCountTotal-memberCount1-memberCount2-memberCount3-memberCount5-memberCount10;
		
		//计算单据利润
		BigDecimal avg1 = new BigDecimal("0.00");
		if(orderCount1!=0){
			avg1 = new BigDecimal(charge1).divide((new BigDecimal(orderCount1)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg2 = new BigDecimal("0.00");
		if(orderCount2!=0){
			avg2 = new BigDecimal(charge2).divide((new BigDecimal(orderCount2)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg3 = new BigDecimal("0.00");
		if(orderCount3!=0){
			avg3 = new BigDecimal(charge3).divide((new BigDecimal(orderCount3)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg5 = new BigDecimal("0.00");
		if(orderCount5!=0){
			avg5 = new BigDecimal(charge5).divide((new BigDecimal(orderCount5)),2,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal avg10 = new BigDecimal("0.00");
		if(orderCount10!=0){
			avg10 = new BigDecimal(charge10).divide((new BigDecimal(orderCount10)),2,BigDecimal.ROUND_HALF_UP);
		}
		
		DataTable dtshow = new DataTable();
		DataColumn type = new DataColumn("type", DataColumn.STRING);
		DataColumn count = new DataColumn("memberCount", DataColumn.STRING);
		DataColumn scale = new DataColumn("scale", DataColumn.STRING);
		DataColumn orderCount = new DataColumn("orderCount", DataColumn.STRING);
		DataColumn totalAmount = new DataColumn("totalAmount", DataColumn.STRING);
		DataColumn charge = new DataColumn("charge", DataColumn.STRING);
		DataColumn avg = new DataColumn("avg", DataColumn.STRING);
		
		dtshow.insertColumn(type);
		dtshow.insertColumn(count);
		dtshow.insertColumn(scale);
		dtshow.insertColumn(orderCount);
		dtshow.insertColumn(totalAmount);
		dtshow.insertColumn(charge);
		dtshow.insertColumn(avg);
		Object[] catalogRow1 = { new String("= 0"), memberCount0 , computePercent(memberCount0,memberCountTotal) , "-" , "-" , "-" , "-" };
		dtshow.insertRow(catalogRow1);
		Object[] catalogRow2 = { new String("= 1"), memberCount1 , computePercent(memberCount1,memberCountTotal) , orderCount1 , PubFun.format(totalAmount1) , PubFun.format(charge1) , avg1 };
		dtshow.insertRow(catalogRow2);
		Object[] catalogRow3 = { new String(">= 2"), memberCount2 , computePercent(memberCount2,memberCountTotal) , orderCount2 , PubFun.format(totalAmount2) , PubFun.format(charge2) , avg2 };
		dtshow.insertRow(catalogRow3);
		Object[] catalogRow4 = { new String(">= 3"), memberCount3 , computePercent(memberCount3,memberCountTotal) , orderCount3 , PubFun.format(totalAmount3) , PubFun.format(charge3) , avg3 };
		dtshow.insertRow(catalogRow4);
		Object[] catalogRow5 = { new String(">= 5"), memberCount5 , computePercent(memberCount5,memberCountTotal) , orderCount5 , PubFun.format(totalAmount5) , PubFun.format(charge5) , avg5 };
		dtshow.insertRow(catalogRow5);
		Object[] catalogRow6 = { new String(">= 10"), memberCount10 , computePercent(memberCount10,memberCountTotal) , orderCount10 , PubFun.format(totalAmount10) , PubFun.format(charge10) , avg10 };
		dtshow.insertRow(catalogRow6);
		dga.bindData(dtshow);
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 end
	}
	private String nullToZero(String count) {
		if (null == count || StringUtils.isBlank(count)) {
			return "0";
		} else {
			return count;
		}
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
	
	/**
	 * 计算y在z中所占百分比
	 * @param y
	 * @param z
	 * @return 百分比的值
	 */
	private String computePercent(int y,int z){
	   if(y == 0){
		   return "-";
	   }else{
		   //接受百分比的值
		   String baifenbi="";
		   double baiy=y*1.0;
		   double baiz=z*1.0;
		   double fen=baiy/baiz;
		   //00.00%   百分比格式，后面不足2位的用0补齐 
		   DecimalFormat df1 = new DecimalFormat("00.00%");
		   baifenbi= df1.format(fen);  
		   return baifenbi;
	   }
	}
	
	public static void main(String[] args) {
		/*QueryBuilder qb1 = new QueryBuilder(" CREATE TEMPORARY TABLE tmp_table(memberid VARCHAR(32),totalAmount VARCHAR(100)) ");
		qb1.executeNoQuery();
		qb1 = new QueryBuilder(" INSERT INTO tmp_table(memberid,totalAmount) SELECT memberid,totalAmount FROM sdorders a,SDOrderItem d LEFT JOIN tradeinformation tf ON d.ordersn=tf.ordid WHERE a.paySn NOT LIKE 'BG%' AND a.ordersn = d.ordersn AND (d.channelCode != '02' OR d.channelCode IS NULL)  AND a.orderStatus >=7 AND a.orderStatus<>'8' ");
		qb1.executeNoQuery();
		qb1=new QueryBuilder("SELECT COUNT(DISTINCT memberid),SUM(totalAmount) FROM tmp_table WHERE totalAmount BETWEEN 0 AND 100 ");
		DataTable dt1 = qb1.executeDataTable();*/

	}
}
