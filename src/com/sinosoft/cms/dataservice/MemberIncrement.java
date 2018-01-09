package com.sinosoft.cms.dataservice;

import java.util.HashMap;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/** 
 * @ClassName: MemberMarketing
 * @Description: TODO(会员增量报表)
 * @author CongZN
 * @date 2014-12-23 下午02:23:29
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class MemberIncrement extends Page {

	/**
	 * 新增.
	 */
	private static HashMap<String, Integer>  xzkh;
	/**
	 * 当年.
	 */
	private static HashMap<String, Integer>  dnkh;
	/**
	 * 全年.
	 */
	private static HashMap<String, Integer>  qnkh;
	
	private static final String [] CHANNELARRAY_APPLICANT = new String[]{"wj","tb","cps","wap","count"};
	
	private static final String [] CHANNELARRAY_INSURED = new String[]{"wj","tb","cps","wap","b2b","count"};
	
	/**
	 * 展示列数
	 */
	private static final int NUMBERCOLUMNS = 4;
	
	/**
	 * @Title: dg1DataBind.
	 * @Description: TODO(会员增量报表).
	 * @param dga
	 *            void.
	 * @author CongZN.
	 */
	public static void dg1DataBind(DataGridAction dga) {
		
		String beginDate = dga.getParams().getString("BeginDate");
		String endDate = dga.getParams().getString("EndDate");
		String queryStatus = dga.getParams().getString("QueryStatus");
		
		xzkh = new HashMap<String, Integer>();
		dnkh = new HashMap<String, Integer>();
		qnkh = new HashMap<String, Integer>();
		
		int xzkhCount = 0;
		int dnkhCount = 0;
		int qnkhCount = 0;
		
		StringBuffer query_mainSql = new StringBuffer("");
		DataTable dt = null ;
		
		if("1".equals(queryStatus)){
			//投保人
			query_mainSql.append(" select s1.orderSn,IFNULL((SELECT getParentCode(s1.channelsn,'02') FROM DUAL),'无') channelsn,date_format(MIN(t4.receiveDate),'%Y%m%d') as receiveDate  from"); 
			query_mainSql.append(" sdorders s1 ,");
			query_mainSql.append(" SDInformationAppnt s2,");
			query_mainSql.append(" SDInformation s3,");
			query_mainSql.append(" tradeinformation t4  ");
			query_mainSql.append(" where s1.ordersn = s3.ordersn ");
			query_mainSql.append(" and s2.informationSn = s3.informationSn ");
			query_mainSql.append(" and s1.ordersn = t4.ordid ");
			query_mainSql.append(" and s1.orderStatus >= 7 ");
			query_mainSql.append(" and s1.orderStatus != 8 ");
//			query_mainSql.append(" and left(t4.receiveDate,10) <= '"+endDate+"' ");
			query_mainSql.append(" group by s2.applicantName,s2.applicantSexName,s2.applicantBirthday HAVING MIN(t4.receiveDate) <= '"+endDate+" 23:59:59' order by t4.receiveDate");
			
			QueryBuilder query_AppntList = new QueryBuilder(query_mainSql.toString());
			
			dt = query_AppntList.executeDataTable();
			
			
		}else if("2".equals(queryStatus)){
			//被保人
			query_mainSql.append(" select s1.orderSn,IFNULL((SELECT getParentCode(s1.channelsn,'02') FROM DUAL),'无') channelsn,date_format(MIN(t3.receiveDate),'%Y%m%d') as receiveDate  from ");
			query_mainSql.append(" sdorders s1 ,");
			query_mainSql.append(" sdinformationinsured s2,");
			query_mainSql.append(" tradeinformation t3  ");
			query_mainSql.append(" where s1.ordersn = s2.ordersn ");
			query_mainSql.append(" and s2.ordersn = t3.ordid ");
			query_mainSql.append(" and s1.ordersn = t3.ordid ");
			query_mainSql.append(" and s1.orderStatus >= '7' ");
			query_mainSql.append(" and s1.orderStatus!='8' ");
//			query_mainSql.append("and left(t3.receiveDate,10) <= '"+endDate+"'");
			query_mainSql.append(" group by s2.recognizeeName,s2.recognizeeSexName,s2.recognizeeBirthday HAVING MIN(t3.receiveDate) <= '"+endDate+" 23:59:59' order by t3.receiveDate");
			
			QueryBuilder query_AppntList = new QueryBuilder();
			query_AppntList.setSQL(query_mainSql.toString());
			dt = query_AppntList.executeDataTable();
			
			//查询旅行保被保人
			QueryBuilder query_AppntList_b2b = new QueryBuilder();
			query_AppntList_b2b.append(" SELECT s1.id AS orderSn,'b2b'  AS channelsn ,date_format(MIN(s3.receiveDate),'%Y%m%d') as receiveDate");
			query_AppntList_b2b.append(" FROM sdorders s1,sdinsured s2,sdtradeinformation s3 ");
			query_AppntList_b2b.append(" WHERE s1.id = s2.ordersn AND s1.id= s3.ordID ");
//			query_AppntList_b2b.append(" and left(s3.receiveDate,10) <= '"+endDate+"' ");
			query_AppntList_b2b.append(" group by s2.RecognizeeName,s2.RecognizeeSex,s2.RecognizeeBirthday HAVING MIN(s3.receiveDate) <= '"+endDate+" 23:59:59' order by s3.receiveDate");
			
			DataTable b2b_dt = query_AppntList_b2b.executeDataTable("B2B");
			dt.union(b2b_dt);
		}
		
	
		if("1".equals(queryStatus)){
			//投保人初始化
			for (int i = 0; i < CHANNELARRAY_APPLICANT.length-1; i++) {
				xzkh.put(CHANNELARRAY_APPLICANT[i], 0);
				dnkh.put(CHANNELARRAY_APPLICANT[i], 0);
				qnkh.put(CHANNELARRAY_APPLICANT[i], 0);
			}
		}else if("2".equals(queryStatus)){
			//被保人初始化
			for (int i = 0; i < CHANNELARRAY_INSURED.length; i++) {
				xzkh.put(CHANNELARRAY_INSURED[i], 0);
				dnkh.put(CHANNELARRAY_INSURED[i], 0);
				qnkh.put(CHANNELARRAY_INSURED[i], 0);
			}
		}
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			
			String channelsn = dt.getString(i, "channelsn");
			int payDate = dt.getInt(i, "receiveDate");
			String orderSn = dt.getString(i, "orderSn");
			
			if("wj".equals(channelsn.trim())){
				dataTotal(beginDate,endDate,channelsn,payDate,orderSn);
			}else if("tb".equals(channelsn)){
				dataTotal(beginDate,endDate,channelsn,payDate,orderSn);
			}else if("wap".equals(channelsn)){
				dataTotal(beginDate,endDate,channelsn,payDate,orderSn);
			}else if("cps".equals(channelsn)){
				dataTotal(beginDate,endDate,channelsn,payDate,orderSn);
			}else if("b2b".equals(channelsn)){
				dataTotal(beginDate,endDate,channelsn,payDate,orderSn);
			}
		}
		
		DataTable dt_result = new DataTable();
		
		dt_result.insertColumn("channel");
		dt_result.insertColumn("xz");
		dt_result.insertColumn("dn");
		dt_result.insertColumn("qn");
		
		Mapx mapx = HtmlUtil.codeToMapx("Sales.channel");
		if("1".equals(queryStatus)){
			mapx.put("count", "投保人合计");
			
			//投保人
			for (int i = 0; i < CHANNELARRAY_APPLICANT.length; i++) {
				Object[] rowValue = new Object[NUMBERCOLUMNS];
				if(!"count".equals(CHANNELARRAY_APPLICANT[i])){
					rowValue[0] =  CHANNELARRAY_APPLICANT[i];
					rowValue[1] =  xzkh.get(CHANNELARRAY_APPLICANT[i]);
					rowValue[2] =  dnkh.get(CHANNELARRAY_APPLICANT[i]);
					rowValue[3] =  qnkh.get(CHANNELARRAY_APPLICANT[i]);
					
					xzkhCount += xzkh.get(CHANNELARRAY_APPLICANT[i]);
					dnkhCount += dnkh.get(CHANNELARRAY_APPLICANT[i]);
					qnkhCount += qnkh.get(CHANNELARRAY_APPLICANT[i]);
				}else{
					rowValue[0] = CHANNELARRAY_APPLICANT[i];
					rowValue[1] = xzkhCount;
					rowValue[2] = dnkhCount;
					rowValue[3] = qnkhCount;
				}
				
				dt_result.insertRow(rowValue);
			}
			
		}else if("2".equals(queryStatus)){
			mapx.put("count", "被保人合计");
			
			//被保人
			for (int i = 0; i < CHANNELARRAY_INSURED.length; i++) {
				Object[] rowValue = new Object[NUMBERCOLUMNS];
				if(!"count".equals(CHANNELARRAY_INSURED[i])){
					rowValue[0] =  CHANNELARRAY_INSURED[i];
					rowValue[1] =  xzkh.get(CHANNELARRAY_INSURED[i]);
					rowValue[2] =  dnkh.get(CHANNELARRAY_INSURED[i]);
					rowValue[3] =  qnkh.get(CHANNELARRAY_INSURED[i]);
					
					xzkhCount += xzkh.get(CHANNELARRAY_INSURED[i]);
					dnkhCount += dnkh.get(CHANNELARRAY_INSURED[i]);
					qnkhCount += qnkh.get(CHANNELARRAY_INSURED[i]);
				}else{
					rowValue[0] = CHANNELARRAY_INSURED[i];
					rowValue[1] = xzkhCount;
					rowValue[2] = dnkhCount;
					rowValue[3] = qnkhCount;
				}
				
				dt_result.insertRow(rowValue);
			}
		}
		
		
		
		dt_result.decodeColumn("channel", mapx);
		dga.bindData(dt_result);
		
	}
	
	/**
	 * @Title: dataTotal.
	 * @Description: TODO().
	 * @param p_beginDate
	 * @param p_endDate void.
	 * @author CongZN.
	 */
	public static void dataTotal(String p_beginDate,String p_endDate,String p_channelsn,int p_payDate,String orderSn){
		int beninYear = 0;
		int EndYear = 0;
		
		int beninDate = 0;
		int endDate = 0;
		
		if(StringUtil.isNotEmpty(p_beginDate)){
			String [] beginDateArr = new String[3];
			beginDateArr = p_beginDate.split("-");
			beninDate = Integer.parseInt(beginDateArr[0]+beginDateArr[1]+beginDateArr[2]);
		}
		
		if(StringUtil.isNotEmpty(p_endDate)){
			String [] endDateArr = new String[3];
			endDateArr = p_endDate.split("-");
			beninYear = Integer.parseInt(endDateArr[0]+"0101");
			EndYear = Integer.parseInt(endDateArr[0]+"1231");
			endDate = Integer.parseInt(endDateArr[0]+endDateArr[1]+endDateArr[2]);
		}
		
		//新增
		if(p_payDate >= beninDate && p_payDate <= endDate){
			int tempChannelsn = xzkh.get(p_channelsn);
			xzkh.put(p_channelsn, tempChannelsn+1);
		}
		
		//当年
		if(p_payDate >= beninYear && p_payDate <= EndYear){
			int tempChannelsn = dnkh.get(p_channelsn);
			dnkh.put(p_channelsn, tempChannelsn+1);
		}
		
		//全年
		if(p_payDate <= EndYear){
			int tempChannelsn = qnkh.get(p_channelsn);
			qnkh.put(p_channelsn, tempChannelsn+1);
		}
		
	}

}