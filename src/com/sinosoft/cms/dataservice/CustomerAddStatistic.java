package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.wangjin.infoseeker.QueryUtil;

/**
 * ClassName: CustomerAddStatistic <br/>
 * Function: 客户新增数量. <br/>
 * date: 2016年4月25日 下午2:34:59 <br/>
 * @see CustomerAddStatisticNew
 * @author wwy
 * @version 
 */
@Deprecated
public class CustomerAddStatistic extends Page implements FieldNameMatchable{

	/**
	 * dg1DataBind:客户新增数量查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String beginDate = dga.getParams().getString("startDate")+" 00:00:00";
		String endDate = dga.getParams().getString("endDate")+" 23:59:59";
		String from = dga.getParams().getString("From");
		String Wedo = dga.getParams().getString("Wedo");
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
		QueryBuilder query_number = new QueryBuilder();
		// 投保人
		if ("1".equals(from)) {
			query_number.append("Select COUNT(1) as count, IFNULL((SELECT i.channelName FROM ChannelInfo i where i.channelcode = a.channelsn), '无') AS channalName, a.channelsn FROM ");
			query_number.append(" (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(" FROM sdorders s1 , SDInformationAppnt s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a where 1=1 ");
			query_number.append(parameter);
			query_number.append(" group by a.channelsn ");
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		// 被保人
		else if ("2".equals(from)) {
			query_number.append("Select COUNT(1) as count, IFNULL((SELECT i.channelName FROM ChannelInfo i where i.channelcode = a.channelsn), '无') AS channalName, a.channelsn FROM ");
			query_number.append(" (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(" FROM sdorders s1 , sdinformationinsured s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a where 1=1 ");
			query_number.append(parameter);
			query_number.append(" group by a.channelsn ");
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		else {
			query_number.append("Select COUNT(1) as count, IFNULL((SELECT i.channelName FROM ChannelInfo i where i.channelcode = a.channelsn), '无') AS channalName, a.channelsn FROM ");
			query_number.append(" (select channelsn, Wedo from ( select s1.channelsn as channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId as Wedo ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile as Wedo ");
			}
			else {
				query_number.append(" s2.applicantMail as Wedo ");
			}
			query_number.append(" FROM sdorders s1 , SDInformationAppnt s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ?) b ");
			
			query_number.append("union ");
			
			query_number.append(" select channelsn, Wedo from ( select s1.channelsn as channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId as Wedo");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile as Wedo ");
			}
			else {
				query_number.append(" s2.recognizeeMail as Wedo ");
			}
			query_number.append(" FROM sdorders s1 , sdinformationinsured s2,SDInformation s3,tradeinformation t4 ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ?) c ");
			
			query_number.append(" ) a where 1=1 ");
			query_number.append(parameter);
			query_number.append(" group by a.channelsn ");
			query_number.add(endDate);
			query_number.add(beginDate);
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		// System.out.println("客户增量统计：" + query_number.getSQL());
		DataTable dt_query_number = query_number.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.setTotal(query_number);
		dga.bindData(dt_query_number);
	}
	
	public static void dg2DataBind(DataGridAction dga) {

		String beginDate = dga.getParams().getString("startDate")+" 00:00:00";
		String endDate = dga.getParams().getString("endDate")+" 23:59:59";
		String from = dga.getParams().getString("From");
		String Wedo = dga.getParams().getString("Wedo");
		String channelsn = dga.getParam("contant");
		String parameter = "";
		
		if (StringUtil.isNotEmpty(channelsn)) {
			parameter = parameter + " and a.channelsn = '"+channelsn+"'";
		}
		QueryBuilder query_number = new QueryBuilder();
		// 投保人
		if ("1".equals(from)) {
			query_number.append("SELECT appnt.id, a.ordersn, appnt.applicantName AS NAME, appnt.applicantIdentityId AS IdentityId, appnt.applicantMobile AS Mobile, ");
			query_number.append("appnt.applicantIdentityTypeName AS IdentityTypeName, appnt.applicantMail AS Mail, appnt.createDate ");
			query_number.append(" FROM (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(", s3.informationSn, s1.ordersn");
			query_number.append(" FROM sdorders s1 , SDInformationAppnt s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(" HAVING MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a, SDInformationAppnt appnt where 1=1 ");
			query_number.append(parameter);
			if ("id".equals(Wedo)) {
				query_number.append(" AND appnt.applicantIdentityId = a.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" AND appnt.applicantMobile = a.applicantMobile ");
			}
			else {
				query_number.append(" AND appnt.applicantMail = a.applicantMail ");
			}
			query_number.append(" AND a.informationSn = appnt.informationSn ");
			query_number.append(" order by appnt.createDate desc ");
			query_number.add(endDate);
			query_number.add(beginDate);
			
		}
		// 被保人
		else if ("2".equals(from)) {
			query_number.append("SELECT insured.id, insured.ordersn, insured.recognizeeName AS NAME, insured.recognizeeIdentityId AS IdentityId, ");
			query_number.append("insured.recognizeeMobile AS Mobile, insured.recognizeeIdentityTypeName AS IdentityTypeName, insured.recognizeeMail AS Mail, insured.createDate ");
			query_number.append(" FROM (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(", s3.informationSn");
			query_number.append(" FROM sdorders s1 , sdinformationinsured s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a, sdinformationinsured insured where 1=1 ");
			query_number.append(parameter);
			if ("id".equals(Wedo)) {
				query_number.append(" AND insured.recognizeeIdentityId = a.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" AND insured.recognizeeMobile = a.recognizeeMobile ");
			}
			else {
				query_number.append(" AND insured.recognizeeMail = a.recognizeeMail ");
			}
			query_number.append(" AND insured.informationSn = a.informationSn ");
			query_number.append(" order by insured.createDate desc ");
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		else {
			query_number.append("SELECT ordersn, NAME, IdentityId, Mobile, IdentityTypeName, Mail, createDate FROM (");
			query_number.append("SELECT a.ordersn as ordersn, appnt.applicantName AS NAME, appnt.applicantIdentityId AS IdentityId, appnt.applicantMobile AS Mobile, ");
			query_number.append("appnt.applicantIdentityTypeName AS IdentityTypeName, appnt.applicantMail AS Mail, appnt.createDate as  createDate");
			query_number.append(" FROM (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(", s3.informationSn, s1.ordersn");
			query_number.append(" FROM sdorders s1 , SDInformationAppnt s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.applicantMobile ");
			}
			else {
				query_number.append(" s2.applicantMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a, SDInformationAppnt appnt where 1=1 ");
			query_number.append(parameter);
			if ("id".equals(Wedo)) {
				query_number.append(" AND appnt.applicantIdentityId = a.applicantIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" AND appnt.applicantMobile = a.applicantMobile ");
			}
			else {
				query_number.append(" AND appnt.applicantMail = a.applicantMail ");
			}
			query_number.append(" AND a.informationSn = appnt.informationSn ");
			
			query_number.append(" union ");
			
			query_number.append("SELECT insured.ordersn as ordersn, insured.recognizeeName AS NAME, insured.recognizeeIdentityId AS IdentityId, ");
			query_number.append("insured.recognizeeMobile AS Mobile, insured.recognizeeIdentityTypeName AS IdentityTypeName, insured.recognizeeMail AS Mail, insured.createDate as createDate ");
			query_number.append(" FROM (select s1.channelsn, ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(", s3.informationSn");
			query_number.append(" FROM sdorders s1 , sdinformationinsured s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' GROUP BY ");
			if ("id".equals(Wedo)) {
				query_number.append(" s2.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" s2.recognizeeMobile ");
			}
			else {
				query_number.append(" s2.recognizeeMail ");
			}
			query_number.append(" HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ");
			query_number.append(" ) a, sdinformationinsured insured where 1=1 ");
			query_number.append(parameter);
			if ("id".equals(Wedo)) {
				query_number.append(" AND insured.recognizeeIdentityId = a.recognizeeIdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" AND insured.recognizeeMobile = a.recognizeeMobile ");
			}
			else {
				query_number.append(" AND insured.recognizeeMail = a.recognizeeMail ");
			}
			query_number.append(" AND insured.informationSn = a.informationSn ");
			query_number.append(" ) aa ");
			query_number.append(" group by ");
			if ("id".equals(Wedo)) {
				query_number.append(" IdentityId ");
			}
			else if ("phone".equals(Wedo)) {
				query_number.append(" Mobile ");
			}
			else {
				query_number.append(" Mail ");
			}
			query_number.append(" order by createDate desc ");
			query_number.add(endDate);
			query_number.add(beginDate);
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		//System.out.println("客户增量详细：" + query_number.getSQL());
		
		DataTable dt_query_number = query_number.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.setTotal(query_number);
		dga.bindData(dt_query_number);
	}
	
	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getCurrentDate());
		params.put("endCreateDate", PubFun.getCurrentDate());
		return params;
	}

	@Override
	public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
		return matcher.setStartDatetime("startDate").setEndDatetime("endDate");
	}
}