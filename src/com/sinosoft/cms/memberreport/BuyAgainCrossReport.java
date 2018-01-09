package com.sinosoft.cms.memberreport;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BuyAgainCrossReport extends Page implements FieldNameMatchable{

	/**
	 * 初始化交叉复购查询页面.
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		params.put("startDate", fmt.format(date));
		params.put("endDate", fmt.format(date));
		return params;
	}
	
	/**
	 * dg1DataBind:交叉复购查询. <br/>
	 *
	 * @author guanyulong
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String channelsn = dga.getParam("contant");

		String parameter = "";
		
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        
        String uuid = CommonUtil.getUUID();
		// 插入临时表
		dealTempData(startDate, endDate, uuid);
		
		//交叉复购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT a.ordersn , a.memberid, FORMAT(IFNULL(a.timePrem, '0'), 2) AS totalAmount, a.sdorid, ");
		sql.append(" (SELECT pro.ProductGenera  FROM sdproduct pro WHERE a.productid = pro.ProductID)ProductNow ");
		sql.append(" ,(SELECT pro2.ProductGenera FROM	dataprecipitationorder o, sdproduct pro2 WHERE o.receiveDate < a.receiveDate  ");
		sql.append(" AND pro2.ProductID = o.productId AND o.memberId = a.memberId AND o.orderStatus IN ('7', '10', '12', '14') ORDER BY o.orderCreateDate DESC	LIMIT 1) ProductBefor, a.fromWap " );
		sql.append(" FROM dataprecipitationorder a, dataprecipitationorder_1 b ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') and a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append(" AND a.memberid = b.memberid AND b.inDealFlag = '" + uuid +"' ");
		sql.append(" AND EXISTS(SELECT 1 FROM dataprecipitationorder o WHERE o.memberid = a.memberId and o.orderCreateDate > b.minOrderCreateDate_30 and o.orderCreateDate < b.minOrderCreateDate) ");
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(parameter);
		sql.append(" AND a.receiveDate2 >= '" + startDate + "' AND a.receiveDate2 <= '" + endDate + "' ");
		sql.append(" ORDER BY ProductNow,ProductBefor,a.sdorid DESC ");
		
		//订单数据
		StringBuffer sql1 = new StringBuffer();
        sql1.append(" SELECT COUNT(DISTINCT a.memberid) membercount, COUNT(DISTINCT a.orderSn) ordercount, COUNT(DISTINCT a.sdorid) AS policycount,  ");
        sql1.append(" FORMAT(IFNULL(SUM(a.timePrem), '0'),2) AS totalAmount, COUNT(DISTINCT a.fromwap) AS memchannel  ");
        sql1.append(" FROM dataprecipitationorder a ");
        sql1.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
        sql1.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
        sql1.append(parameter);
        sql1.append(" AND a.receiveDate2 <='" + endDate + "'  AND a.receiveDate2 >='" + startDate + "' ");

		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		QueryBuilder qb1 = new QueryBuilder(sql1.toString());
		DataTable dt1 = qb1.executeDataTable();
		int orderCountAmount = 0;
		int policyCountAmount = 0;
		int row=dt.getRowCount();
		int orderNum=0;
		int sdorNum=0;
		double totalAmount=0.0;
		double amountTotalAmount = 0.0;
		String rowName="";
		String sdor="";
		String memberid="";
		String orderid="";
		String sdorid="";
		LinkedHashMap<String, Object> Map = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> channel = new LinkedHashMap<String, String>();
		List<String> memberAmount = new ArrayList<String>();
		List<String> member = new ArrayList<String>();
		List<String> fromWap = new ArrayList<String>();
		for(int i=0;i<row;i++){
			if(!dt.get(i, "ProductNow").equals(dt.get(i, "ProductBefor"))){//去除非交叉
				if(rowName.equals(dt.get(i, "ProductBefor").toString()+" → "+ dt.get(i, "ProductNow").toString())){
					checkrepeat(member,dt.get(i, "memberid").toString());  
                    checkrepeat(fromWap,dt.get(i, "fromWap").toString());  
					if(!sdor.equals(dt.get(i, "sdorid").toString())){
						sdorid=sdorid+"_" +dt.get(i, "sdorid").toString();
						++sdorNum;
						++policyCountAmount;
					}
					sdor=dt.get(i, "sdorid").toString();
					if(orderid.indexOf(dt.get(i, "ordersn").toString()) <=-1){
						++orderNum;
						orderid=orderid+ "_" +dt.get(i, "ordersn").toString();
						++orderCountAmount;
					}
					totalAmount=doubleAdd(totalAmount,Double.parseDouble((dt.get(i, "totalAmount").toString()).replace(",", "")));
					amountTotalAmount=doubleAdd(Double.parseDouble((dt.get(i, "totalAmount").toString()).replace(",", "")),amountTotalAmount);
					data.put("ordersn", String.valueOf(orderNum));
					data.put("member", String.valueOf(member.size()));
					data.put("sdor", String.valueOf(orderid.split("_").length));//订单
					data.put("sdor2", String.valueOf(sdorNum));//保单
					data.put("totalAmount",  String.valueOf(totalAmount));
					memberid=member.toString();
					memberid=memberid.replace(memberid.substring(0,1),"").replace(memberid.substring(memberid.length()-1,memberid.length()),"");//memberid去括号
					memberid=memberid.replace(",","_");
					memberid=memberid.replace(" ","");
					data.put("memberid",  memberid);
					data.put("sdorid",  sdorid);
					data.put("orderid",  orderid);
					data.put("fromWap",  String.valueOf(fromWap.size()));
				}else{
					member.clear();
					fromWap.clear();
					data.clear();
					orderid=dt.get(i, "ordersn").toString();
					sdorid=dt.get(i, "sdorid").toString();
					rowName=dt.get(i, "ProductBefor").toString()+" → "+ dt.get(i, "ProductNow").toString();
					orderNum=1;
					++orderCountAmount;
					sdorNum=1;
					++policyCountAmount;
					member.add(dt.get(i, "memberid").toString());
					fromWap.add(dt.get(i, "fromWap").toString());
					totalAmount=Double.parseDouble((dt.get(i, "totalAmount").toString()).replace(",", ""));
					amountTotalAmount=doubleAdd(Double.parseDouble((dt.get(i, "totalAmount").toString()).replace(",", "")),amountTotalAmount);
					data.put("ordersn", String.valueOf(orderNum));
					data.put("member", String.valueOf(member.size()));
					data.put("sdor", String.valueOf(orderid.split("_").length));//订单
					data.put("sdor2", String.valueOf(sdorNum));//保单
					data.put("totalAmount",  String.valueOf(totalAmount));
					memberid=member.toString();
					memberid=memberid.replace(memberid.substring(0,1),"").replace(memberid.substring(memberid.length()-1,memberid.length()),"");//memberid去括号
					data.put("memberid",  memberid);
					data.put("sdorid",  sdorid);
					data.put("orderid",  orderid);
					data.put("fromWap",  String.valueOf(fromWap.size()));
				}
				Map.put(rowName,new LinkedHashMap<String, String>(data));
				//获取会员总数
				memberAmount=getMemberAmount(memberAmount,dt.get(i, "memberid").toString(),channel,dt.get(i, "fromWap").toString());
			}
		}
		DataTable dt2 = new DataTable();
        int pagerow=0;
		for (String key : Map.keySet()) {
			data.clear();
			data=(LinkedHashMap<String, String>) Map.get(key);
			Object[] catalogRow = {key, data.get("member"),data.get("sdor"),data.get("totalAmount"),data.get("sdor2"), "0"
					,data.get("memberid").toString()  ,data.get("orderid").toString()  ,data.get("sdorid").toString() ,data.get("fromWap").toString()};
			dt2.insertRow(catalogRow,pagerow);
			++pagerow;
		}
		if(pagerow==0){
			Object[] catalogRow = {"无交叉复购", "0","0","0",data.get("sdor"), "4","","","","0"};
			dt2.insertRow(catalogRow,pagerow);
			++pagerow;
		}
		Object[] catalogRow1 = {"总数", dt1.getString(0, "membercount"), dt1.getString(0, "ordercount"),dt1.getString(0, "totalAmount"), dt1.getString(0, "policycount"), "1","","","",dt1.getString(0, "memchannel")};
		dt2.insertRow(catalogRow1,pagerow);
		++pagerow;
		Object[] catalogRow2 = {"交叉复购会员去重总数", String.valueOf(memberAmount.size()), "-","-", "-", "3","","","",String.valueOf(delMore(channel).size())};
		dt2.insertRow(catalogRow2,pagerow);
		++pagerow;
		String memberCount = computePercent(String.valueOf(memberAmount.size()),dt1.getString(0, "membercount"));
		String orderCount = computePercent(String.valueOf(orderCountAmount),dt1.getString(0, "ordercount"));
		String amountCount = computePercent(String.valueOf(amountTotalAmount),dt1.getString(0, "totalAmount"));
		String policyCount = computePercent(String.valueOf(policyCountAmount),dt1.getString(0, "policycount"));
		String channelCount = computePercent(String.valueOf(delMore(channel).size()),dt1.getString(0, "memchannel"));
		Object[] catalogRow3 = {"交叉复购率", memberCount, orderCount, amountCount, policyCount, "2","","","",channelCount};
		dt2.insertRow(catalogRow3,pagerow);
		//交叉复购率计算
        // 删除临时表数据
        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
        deleteTempData.executeNoQuery();
		dga.bindData(dt2);
	}

	/**
	 * initDialog:弹出窗口初始化. <br/>
	 *
	 * @author wwy
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		params.put("contant", params.getString("contant"));
		return params;
	}

	/**
	 * dg1DataBind_Mdialog:会员数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Mdialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		String members = "'" + (dga.getParams().getString("members").toString()).replace("_","','")+"'"   ;
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		
        if (StringUtil.isEmpty(channelsn)) {
        	return;
        } else {
        	parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
        }
        String uuid = CommonUtil.getUUID();	
		//交叉复购数据
		StringBuffer sql = new StringBuffer();
		if ("0".equals(type)) {
			// 插入临时表
			dealTempData(startDate, endDate, uuid);
			sql.append(" SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip  ");
			sql.append(" ,DATEDIFF( z.mindate, (SELECT  o.createdate  FROM sdorders o  WHERE o.createdate < z.mindate  AND o.memberId = z.memberId  ");
			sql.append("  AND o.orderStatus IN ('7', '10', '12', '14')  ORDER BY o.createdate DESC   LIMIT 1)) AS bigthanmindate, ");
			sql.append("  DATEDIFF( (SELECT  o.createdate  FROM sdorders o  WHERE o.createdate > z.mindate  AND o.memberId = z.memberId  ");
			sql.append(" AND o.orderStatus IN ('7', '10', '12', '14')  ORDER BY o.createdate DESC   LIMIT 1) ,z.mindate )AS littlethanmindate, z.mindate , '' AS days  "); 
			sql.append(" FROM member w, (SELECT a.memberid, b.minOrderCreateDate AS mindate " );
			sql.append(" FROM dataprecipitationorder a,dataprecipitationorder_1 b where b.memberid = a.memberid and b.inDealFlag = '" + uuid + "' and a.memberid in("+ members +")  ");
		} else {
			sql.append(" SELECT w.*, IF(w.vipflag = 'Y', 'vip', w.grade) as gradevip, '' AS maxdate ");
			sql.append(" ,'' AS regdate,'' AS days ");
			sql.append(" FROM member w, (SELECT a.memberid ");
			sql.append(" FROM dataprecipitationorder a ");
			sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		}
		sql.append(" AND a.receiveDate2 <='"+endDate+"' AND a.receiveDate2 >='"+startDate+"' ");
		sql.append(parameter);
		sql.append(" GROUP BY a.memberid) z WHERE w.id = z.memberid ORDER BY w.logindate desc");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		String mindate="",bigthanmindate="",littlethanmindate="";
		if ("0".equals(type)) {
			for(int i=0;i<dt.getRowCount();i++){
				mindate = dt.getString(i, "mindate");
				littlethanmindate = dt.getString(i, "littlethanmindate");
				bigthanmindate = dt.getString(i, "bigthanmindate");
				if (StringUtil.isNotEmpty(mindate)) {
					if(StringUtil.isNotEmpty(littlethanmindate)){
						if(Integer.parseInt(littlethanmindate)>30){
							dt.set(i, "days", bigthanmindate);
						}else{
							dt.set(i, "days", littlethanmindate);
						}
					}else{
						if(Integer.parseInt(bigthanmindate)>30){
							dt.set(i, "days", bigthanmindate);
						}else{
						    dt.set(i, "days", "1");
						}
					}
				}else{
					dt.set(i, "days", "1");
				}
			}
		}
		
		dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		dt.decodeColumn("registerType", HtmlUtil.codeToMapx("Member.registerType"));
		// 是否启用
		DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?",
				"isAccountEnabled").executeDataTable();
		dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
		// 等级
		DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
		dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
		
		if ("0".equals(type)) {
	        // 删除临时表数据
	        QueryBuilder deleteTempData = new QueryBuilder("delete from dataprecipitationorder_1 where inDealFlag = ?", uuid);
	        deleteTempData.executeNoQuery();
		}
		dga.bindData(dt);
	}

	/**
	 * dg1DataBind_Odialog:订单数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Odialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		String orders = "'" + (dga.getParams().getString("orders").toString()).replace("_","','")+"'"   ;
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		if (StringUtil.isNotEmpty(channelsn)) {
			parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn,a.applicantName,a.startdate,a.receiveDate,a.productname,a.planname,COUNT(a.sdorId) AS insuredNum, ");
		sql.append("ROUND(SUM(a.timePrem), 2) AS premSum,a.payPrice,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID, ");
		sql.append("a.orderStatusName,a.couponsn,IFNULL(ROUND(SUM(a.couponValue), 2),'0.00') AS couponAmount,IFNULL(ROUND(SUM(a.integralValue), 2),'0.00') AS integralSum, ");
		sql.append("IFNULL(ROUND(SUM(a.activityValue), 2),'0.00') AS activitySum,a.couponValue,a.activitySn,a.activityValue,a.integralValue, ");
		sql.append("(SELECT	channelname FROM channelinfo c WHERE c.channelcode = a.channelsn) AS orderchannel, ");
		sql.append("(SELECT channelname	FROM channelinfo c WHERE c.channelcode = a.fromWap) AS memberchannel,p.ProductGenera");
		sql.append(" FROM dataprecipitationorder a left join sdproduct p ON a.productid = p.ProductID ");
		sql.append(" WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		if ("0".equals(type)) {
			 sql.append(" AND a.orderSn in ("+orders+")");
		}
		sql.append(" AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		sql.append(" AND a.receiveDate2 <='"+endDate+"' AND a.receiveDate2 >='"+startDate+"' ");
		sql.append(parameter);
		sql.append(" GROUP BY a.ordersn order by a.receiveDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	/**
	 * dg1DataBind_Rdialog:保单数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Rdialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		String sdors = "'" + (dga.getParams().getString("sdors").toString()).replace("_","','")+"'"   ;
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		if (StringUtil.isNotEmpty(channelsn)) {
			parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
		}
		
		//交叉复购数据
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ordersn, a.recognizeeSn, a.productname, a.policyNo, a.payPrice, a.orderStatusName, a.applicantName, a.recognizeeName, a.recognizeeIdentityId, ");
		sql.append("a.startDate,a.endDate,CONCAT(IFNULL(a.memberMail, ''),'/',IFNULL(a.memberMobile, '')) AS MID ");
		sql.append("FROM dataprecipitationorder a  ");
		sql.append("WHERE a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		sql.append("AND EXISTS (SELECT 1 FROM member WHERE id = a.memberid) ");
		if ("0".equals(type)) {
			sql.append(" AND a.sdorId in("+sdors+")");
		}
		sql.append(" AND a.receiveDate2 <='"+endDate+"'  AND a.receiveDate2 >='"+startDate+"' ");
		sql.append(parameter);
		// 按照登录时间逆序排序，讲最后登录的放到最上面
		sql.append(" GROUP BY a.sdorId order by a.receiveDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}

	/**
	 * dg1DataBind_Sdialog:会员来源数据. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind_Sdialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		String members = "'" + (dga.getParams().getString("members").toString()).replace("_","','")+"'"   ;
		// 统计时间
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		
		String channelsn = dga.getParams().getString("contant");
		String parameter = "";
		if (StringUtil.isNotEmpty(channelsn)) {
			parameter = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
		}
		
		//交叉复购数据
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.channelname AS source,	w.fromWap, count(DISTINCT a.memberId) AS memberNum ");
		sql.append(" FROM dataprecipitationorder a LEFT JOIN channelinfo c on a.fromWap = c.channelcode, member w ");
		sql.append(" WHERE w.id = a.memberId AND a.orderStatus IN ('7', '10', '12', '14') AND a.appStatus <= '1' AND a.memberid IS NOT NULL AND a.memberid != '' ");
		if ("0".equals(type)) {
			sql.append("AND  a.memberid in("+ members +")  ");
		}
		sql.append(" AND a.receiveDate2 <='"+endDate+"' AND a.receiveDate2 >='"+startDate+"' ");
		sql.append(parameter);
		// 按照登录时间逆序排序，讲最后登录的放到最上面
		sql.append(" group by w.fromWap ORDER BY w.logindate desc");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dga.bindData(dt);
	}
	
	/**
	 * 计算a在b中所占百分比
	 * @param a
	 * @param b
	 * @return 百分比的值
	 */
	private static String computePercent(String a, String b) {

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
			DecimalFormat df1 = new DecimalFormat("0.00%");
			baifenbi = df1.format(fen);
			return baifenbi;
		}
	}
	
	
	/**
	 * 计算会员总数
	 * @param memberAmount
	 * @param memberid
	 * @return 会员总数
	 */
	private static List<String> getMemberAmount(List<String> memberAmount,String memberid ,LinkedHashMap<String, String> channel,String channelName) {
		int member_Amount=0; 
		if(memberAmount.size()==0){
			memberAmount.add(memberid);
			channel.put(memberid, channelName);
			++member_Amount;
		}else{
			for(int x=0;x<memberAmount.size();x++){
				if(memberAmount.get(x).equals(memberid)){
					++member_Amount;
				}
			}
		}
		if(member_Amount==0){
			memberAmount.add(memberid);
			channel.put(memberid, channelName);
		}
		member_Amount=0;
			return memberAmount;
		}
	
	/**
	 * 交叉复购会员数，渠道数
	 * @param checkit
	 * @param checiString
	 * @return 会员数，渠道数
	 */
	 public static List<String> checkrepeat(List<String> checkit ,String checiString){
		 int checkit_X=0;
			if(!checkit.equals(checiString)){
				for(int x=0;x<checkit.size();x++){
					if(checkit.get(x).equals(checiString)){
						++checkit_X;
					}
				}
				if(checkit_X==0){
					checkit.add(checiString);
				}
				
				checkit_X=0;
			}
		 return checkit;
		 
	 }
	 
		/**
		 * 交叉复购渠道总数去重
		 * 
		 */
	 public static HashMap<String, String> delMore(HashMap<String, String> hm){
         ArrayList<String> list = new ArrayList<String>();        //用于存储map集合中不重复的值
         ArrayList<String> list2 = new ArrayList<String>();        //用于存储map集合中剩余的键
         
         Set<Entry<String, String>> entrySet = hm.entrySet();
         Iterator<Entry<String, String>> it = entrySet.iterator();
         while(it.hasNext()){
                 Entry<String, String> en = it.next();
                 //判断list集合里是否包含map集合中值
                 if(!list.contains(en.getValue())){
                         list.add(en.getValue());
                         list2.add(en.getKey());
                 }else{
                         it.remove();
                 }
         }
         return hm;
	 }
	 
	 /*
	  * double 加法
	  */
		public static double doubleAdd(double v1, double v2) {
			  BigDecimal b1 = new BigDecimal(Double.toString(v1));
			  BigDecimal b2 = new BigDecimal(Double.toString(v2));
			  return b1.add(b2).doubleValue();
			 }

	    
    /**
     * dealTempData:插入临时数据. <br/>
     *
     * @author wwy
     * @param startDate
     * @param endDate
     * @param uuid
     * @param parameter
     */
    private static void dealTempData(String startDate, String endDate, String uuid){
    	 // 插入临时表
		String tempSql = "INSERT INTO dataprecipitationorder_1 (id,memberId,minOrderCreateDate,minOrderCreateDate_30,inDealFlag) "
				+ " SELECT REPLACE (UUID(), '-', ''), a.memberId,	DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'), "
				+ " DATE_ADD(DATE_FORMAT(MIN(a.orderCreateDate),'%Y-%m-%d'),INTERVAL - 30 DAY),	'" + uuid + "'"
				+ " FROM dataprecipitationorder a WHERE	a.memberid IS NOT NULL AND a.memberid != ''	AND a.receiveDate2 >= '" + startDate + "'"
				+ " AND a.receiveDate2 <= '" + endDate + "'" + "GROUP BY a.memberId";
		QueryBuilder tempQb = new QueryBuilder(tempSql);
		tempQb.executeNoQuery();
    }

	@Override
	public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
		return matcher.setStartDatetime("startDate").setEndDatetime("endDate");
	}
 }