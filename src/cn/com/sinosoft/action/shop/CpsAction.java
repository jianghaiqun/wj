package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.util.AesUtilQN;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @ClassName: CpsAction 
 * @Description: TODO(CPS) 
 * @author CongZN 
 * @date 2015-1-14 下午02:40:54 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
@ParentPackage("shop")
public class CpsAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 合作商ID.
	 */
	private String source;
	/**
	 * 合作商子渠道.
	 */
	private String uid;
	/**
	 * 订单开始时间 格式：20141001235959.
	 */
	private String sdate;
	/**
	 * 订单结束时间.
	 */
	private String edate;
	/**
	 * 0表示成功 1表示异常.
	 */
	private String return_status;
	/**
	 * 信息.
	 */
	private String return_msg;
	/**
	 * 查询订单时间类型 1 创建时间 2 修改时间
	 */
	private String dtype;
	
	/**
	 * 盘石key值
	 */
	private String key;
	/**
	 * 订单号
	 */
	private String ordersn;
	
	/**
	 * @Title: cpsQueryOrders.
	 * @Description: TODO(查询订单).
	 * @return String.
	 * @author CongZN.
	 */
	public String cpsQueryOrders() {
		boolean state = true;
		Map<String,Object> result_Map = new HashMap<String, Object>();
		Map<String,Object> pack_result_Map = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty(source)){
			return_msg = "error:source is null code:1000";
			state = false;
		}else if(StringUtil.isEmpty(sdate)){
			return_msg = "error:sdate is null code:1001";
			state = false;
		}else if(StringUtil.isEmpty(edate)){
			return_msg = "error:edate is null code:1002";
			state = false;
		}
		
		if(state){
			if(StringUtil.isNotEmpty(isPartners(source,"1"))){
				String[] argArr = {source, sdate, edate, uid};
				logger.info("CPS查询接口参数输出:source:{}|sdate:{}|edate:{}|uid:{}", argArr);
				
				List<String> dataList = new ArrayList<String>();
				
				StringBuffer queryOrdersStr = new StringBuffer();
				queryOrdersStr.append(" SELECT s1.paysn,s3.pw,s3.wi FROM sdorders s1,tradeinformation s2,cps s3 ");
				queryOrdersStr.append(" WHERE s1.orderSn = s2.ordID ");
				queryOrdersStr.append(" AND s1.orderSn = s3.on ");
				queryOrdersStr.append(" AND s2.ordID = s3.on ");
				queryOrdersStr.append(" AND s1.orderStatus = 7 ");
				queryOrdersStr.append(" AND DATE_FORMAT(s2.receiveDate, '%Y%m%d%H%i%s') >= ? ");
				queryOrdersStr.append(" AND DATE_FORMAT(s2.receiveDate, '%Y%m%d%H%i%s') <= ? ");
				queryOrdersStr.append(" AND s1.paySn not like 'BG%' ");
				queryOrdersStr.append(" AND s3.cid = ? ");
				if(StringUtil.isNotEmpty(uid)){
					queryOrdersStr.append(" AND s3.wi = ? ");
				}
				queryOrdersStr.append(" GROUP BY s1.paysn ORDER BY s2.receiveDate DESC ");
				
				QueryBuilder query_Orders = new QueryBuilder(queryOrdersStr.toString());
				query_Orders.add(sdate);
				query_Orders.add(edate);
				query_Orders.add(source);
				if(StringUtil.isNotEmpty(uid)){
					query_Orders.add(uid);
				}
				
				DataTable dt_result = query_Orders.executeDataTable();
				Map<String,String> partnersInfo = CpsAction.queryPartnersInfo(source);
				
				for (int i = 0; i < dt_result.getRowCount(); i++) {
					Map<String,String> result_cpsInfo = new HashMap<String, String>();
					String paySn = dt_result.getString(i, "paysn");
					if(StringUtil.isEmpty(paySn)){
						continue;
					}
					result_cpsInfo.put("PartnersCode", source);
					result_cpsInfo.put("UID", dt_result.getString(i, "wi"));
					result_cpsInfo.put("Channel", dt_result.getString(i, "pw"));
					String dataStr = CpsAction.CpsParam(paySn,partnersInfo,result_cpsInfo,"query");
					dataList.add(dataStr);
				}
				
				result_Map.put("data", dataList);
				return_status = "0";
				return_msg = "success";
			}else{
				return_status = "1";
				return_msg = "error:Partners do not exist or not code:1003";
			}
		}else{
			return_status = "1";
		}
		
		result_Map.put("status", return_status);
		result_Map.put("msg", return_msg);
		pack_result_Map.put("datalist", result_Map);
		JSONObject object = JSONObject.fromObject(pack_result_Map);
		logger.info("CPS查询接口返回信息:{}", object.toString());
		return ajax(object.toString(), "text/html");
	}
	
	/**
	 * @Title: cpsQueryOrdersAll.
	 * @Description: TODO(查询全部订单).
	 * @return String.
	 * @author CongZN.
	 */
	public String cpsQueryOrdersAll() {
		boolean state = true;
		Map<String,Object> result_Map = new HashMap<String, Object>();
		Map<String,Object> pack_result_Map = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty(source)){
			return_msg = "error:source is null code:1000";
			state = false;
		}else if(StringUtil.isEmpty(sdate)){
			return_msg = "error:sdate is null code:1001";
			state = false;
		}else if(StringUtil.isEmpty(edate)){
			return_msg = "error:edate is null code:1002";
			state = false;
		}
		
		if(state){
			if(StringUtil.isNotEmpty(isPartners(source,"1"))){
				
				List<String> dataList = new ArrayList<String>();
				
				StringBuffer queryOrdersStr = new StringBuffer();
				queryOrdersStr.append(" SELECT s2.cid,s2.wi,s2.pw FROM sdorders s1,cps s2 ");
				queryOrdersStr.append(" WHERE ");
				queryOrdersStr.append(" s1.orderSn = s2.on ");
				queryOrdersStr.append(" AND s1.orderStatus in('5','7','13') AND s1.paySn not like 'BG%' ");
				queryOrdersStr.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') >= ? ");
				queryOrdersStr.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') <= ? ");
				
				queryOrdersStr.append(" AND s2.cid = ? ");
				if(StringUtil.isNotEmpty(uid)){
					queryOrdersStr.append(" AND s2.wi = ? ");
				}
				
				queryOrdersStr.append(" GROUP BY s2.cid,s2.wi ORDER BY s1.createdate DESC ");
				
				QueryBuilder query_Orders = new QueryBuilder(queryOrdersStr.toString());
				
				query_Orders.add(sdate);
				query_Orders.add(edate);
				query_Orders.add(source);
				
				if(StringUtil.isNotEmpty(uid)){
					query_Orders.add(uid);
				}
				
				DataTable dt_result = query_Orders.executeDataTable();
				Map<String,String> partnersInfo = CpsAction.queryPartnersInfo(source);
				
				for (int i = 0; i < dt_result.getRowCount(); i++) {
					String cid = dt_result.getString(i, "cid");
					String uid = dt_result.getString(i, "wi");
					String channel = dt_result.getString(i, "pw");
					String dataStr = null;
					if ("cps_mkj".equals(cid)) {
						JSONObject object = OrdersInfoNewForMKJ(cid,uid,channel, partnersInfo, "query",sdate,edate);
						dataStr  = AesUtilQN.encrypt(JSONObject.fromObject(object).toString(),partnersInfo.get("Key"));
					}else {						
						dataStr = OrdersInfoNew(cid,uid,channel, partnersInfo, "query",sdate,edate);
					}
					dataList.add(dataStr);
				}
				
				result_Map.put("data", dataList);
				return_status = "0";
				return_msg = "success";
			}else{
				return_status = "1";
				return_msg = "error:Partners do not exist or not code:1003";
			}
		}else{
			return_status = "1";
		}
		
		result_Map.put("status", return_status);
		result_Map.put("msg", return_msg);
		pack_result_Map.put("datalist", result_Map);
		JSONObject object = JSONObject.fromObject(pack_result_Map);
		
		return ajax(object.toString(), "text/html");
	}
	
	
	/**
	 * cpsQueryOrdersAes:返回加密结果. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public String cpsQueryOrdersAes() {

		boolean state = true;
		List<Map<String, Object>> result_Map = new ArrayList<Map<String, Object>>();
		Map<String, Object> pack_result_Map = new HashMap<String, Object>();

		if (StringUtil.isEmpty(source)) {
			return_msg = "error:source is null code:1000";
			state = false;
		} else if (StringUtil.isEmpty(sdate)) {
			return_msg = "error:sdate is null code:1001";
			state = false;
		} else if (StringUtil.isEmpty(edate)) {
			return_msg = "error:edate is null code:1002";
			state = false;
		}

		if (state) {
			if (StringUtil.isNotEmpty(isPartners(source, "1"))) {
				String[] argArr = { source, sdate, edate, uid };
				logger.info("AES CPS查询接口参数输出:source:{}|sdate:{}|edate:{}|uid:{}", argArr);

				StringBuffer queryOrdersStr = new StringBuffer();
				queryOrdersStr
						.append(" select o.ordersn as OrderId, c.wi as track_code, if(r.appStatus = '1', '4',if(r.appStatus in ('2','3','4'), '6','1')) as Status, ");
				queryOrdersStr
						.append(" r.PolicyNo, r.applyPolicyNo as ProposalNo, o.totalAmount as TotalPremium, DATE_FORMAT(r.svaliDate, '%Y%m%d%H%i%s') as InsBeginDate, r.insureDate as IssuedTime, ");
				queryOrdersStr
						.append(" r.riskCode as ProductCode, r.riskName as ProductName, DATE_FORMAT(r.evaliDate, '%Y%m%d%H%i%s') as Insenddate, s.ensureDisplay as Insperiod,s.chargeyear as chargeyear, ");
				queryOrdersStr
						.append(" r.amnt as Amount, o.totalAmount as Premium, a.applicantName as HolderName, a.applicantMail as HolderEmail, if(a.applicantIdentityType = '1', '1', '99') as HolderCardtype, ");
				queryOrdersStr
						.append(" a.applicantIdentityId as HolderCardno, a.applicantMobile as HolderMobile, DATE_FORMAT(r.svaliDate, '%Y%m%d%H%i%s') as EffectDate, i.recognizeeName as InsuredName, ");
				queryOrdersStr
						.append(" if(i.recognizeeIdentityType = '1', '1', '99') as InsuredType, i.recognizeeIdentityId as InsuredCardno, i.recognizeeBirthday as InsuredBirthday, ");
				queryOrdersStr
						.append(" if(i.recognizeeSexName = '男', '1', '0') as InsuredSex, i.recognizeeMobile as InsuredMobile, DATE_FORMAT(o.createDate, '%Y%m%d%H%i%s') as createDate, i.recognizeeAppntRelationName as insuredRelation ");
				queryOrdersStr
						.append(" from sdorders o, sdinformationrisktype r, sdinformation s, sdinformationappnt a, sdinformationinsured i, cps c ");
				queryOrdersStr
						.append(" where o.ordersn = r.ordersn and r.ordersn = s.ordersn and s.informationSn = a.informationSn and o.ordersn = i.ordersn and c.`on` = o.ordersn ");
				queryOrdersStr.append(" AND DATE_FORMAT(o.createDate, '%Y%m%d%H%i%s') >= ? ");
				queryOrdersStr.append(" AND DATE_FORMAT(o.createDate, '%Y%m%d%H%i%s') <= ? ");
				queryOrdersStr.append(" AND o.paySn not like 'BG%' ");
				queryOrdersStr.append(" AND c.cid = ? ");
				if (StringUtil.isNotEmpty(uid)) {
					queryOrdersStr.append(" AND c.wi = ? ");
				}
				queryOrdersStr.append(" ORDER BY o.createDate ");

				QueryBuilder query_Orders = new QueryBuilder(queryOrdersStr.toString());
				query_Orders.add(sdate);
				query_Orders.add(edate);
				query_Orders.add(source);
				if (StringUtil.isNotEmpty(uid)) {
					query_Orders.add(uid);
				}

				DataTable dt_result = query_Orders.executeDataTable();

				result_Map = dt_result.toList();
				return_status = "0";
				return_msg = "success";
			} else {
				return_status = "1";
				return_msg = "error:Partners do not exist or not code:1003";
			}
		} else {
			return_status = "1";
		}

		Map<String, Object> header = new HashMap<String, Object>();
		header.put("ResponseCode", return_status);
		header.put("ErrorMessage", return_msg);
		pack_result_Map.put("Header", header);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("Orders", result_Map);
		
		Map<String,String> partnersInfo = CpsAction.queryPartnersInfo(source);
		pack_result_Map.put("Body", AesUtilQN.encrypt(JSONObject.fromObject(body).toString(), partnersInfo.get("Key")));

		JSONObject object = JSONObject.fromObject(pack_result_Map);
		logger.info("CPS查询接口返回信息:{}", object.toString());
		return ajax(object.toString(), "text/html");
	}
	
	/**
	 * @Title: isPartners.
	 * @Description: TODO(合作商是否合作中).
	 * @param p_PartnersID 渠道(合作商ID)
	 * @param p_Type 1.是否合作中 2.合作商是否开启推送
	 * @return String 返回 CookieTime.
	 * @author CongZN.
	 */
	public static String isPartners(String p_PartnersID,String p_Type){
		
		StringBuffer querySql = new StringBuffer();
		querySql.append("select CookieTime from PartnersManage p where p.PartnersCode = ? ");
		if("1".equals(p_Type)){
			querySql.append("and p.State = 'Y'");
		}else if("2".equals(p_Type)){
			querySql.append("and p.SendState = 'Y'");
		}
		QueryBuilder query_Partners = new QueryBuilder(querySql.toString());
		query_Partners.add(p_PartnersID);
		return query_Partners.executeString();
	}
	
	/**
	 * @Title: queryOrders.
	 * @Description: TODO(通过订单查询CPS信息).
	 * @param p_OrderSn 
	 * @return boolean.
	 * @author CongZN.
	 */
	public static Map<String,String> queryOrders(String p_OrderSn){
		Map<String,String> result_Map = null;
		QueryBuilder query_OrderSn= new QueryBuilder("SELECT cid as partnersCode,wi as UID,pw as channel FROM cps WHERE cps.ps!='ts' and cps.on=?", p_OrderSn);
		DataTable result_cpsInfo = query_OrderSn.executeDataTable();
		for (int i = 0; i < result_cpsInfo.getRowCount(); i++) {
			result_Map = new HashMap<String, String>();
			result_Map.put("PartnersCode", result_cpsInfo.getString(i, "partnersCode"));
			result_Map.put("UID", result_cpsInfo.getString(i, "UID"));
			result_Map.put("Channel", result_cpsInfo.getString(i, "channel"));
		}
		return result_Map;
	}
	
	/**
	 * @Title: queryPartnersInfo.
	 * @Description: TODO(查询合作商信息).
	 * @param p_PartnersID
	 * @return Map<String,String>.
	 * @author CongZN.
	 */
	public static Map<String,String> queryPartnersInfo(String p_PartnersID){
		Map<String,String> result_Map = null;
		StringBuffer querySql = new StringBuffer();
		querySql.append("select * from PartnersManage p where p.PartnersCode = ? ");
		QueryBuilder query_Partners = new QueryBuilder(querySql.toString());
		query_Partners.add(p_PartnersID);
		DataTable dt_result = query_Partners.executeDataTable();
		
		for (int i = 0; i < dt_result.getRowCount(); i++) {
			result_Map = new HashMap<String, String>();
			result_Map.put("PartnersCode", dt_result.getString(i, "PartnersCode"));
			result_Map.put("CookieTime", dt_result.getString(i, "CookieTime"));
			result_Map.put("SendUrl", dt_result.getString(i, "SendUrl"));
			result_Map.put("SendState", dt_result.getString(i, "SendState"));
			result_Map.put("State", dt_result.getString(i, "State"));
			result_Map.put("Key", dt_result.getString(i, "prop1"));
		}
		
		return result_Map;
	}
	
	/**
	 * @Title: CpsParam.
	 * @Description: TODO(参数组装).
	 * @param p_PaySn
	 * @param p_PartnersInfo
	 * @param p_CpsInfo
	 * @param p_Type query:查询,send:推送
	 * @return String.
	 * @author CongZN.
	 */
	public static String CpsParam(String p_PaySn, Map<String,String> p_PartnersInfo,Map<String,String> p_CpsInfo,String p_Type){
		String queryTradeinfoSql = "select *,(select sum(totalAmount) from sdorders where paysn = ?) as tAmount from tradeinfo where paysn = ?";
		String queryOrderSql = "select s1.paySn,s1.payPrice as totalAmnout,s1.createdate as modifyDate,(select sum(totalAmount) from sdorders where paysn = ?) as tAmount from sdorders s1 where s1.paysn = ?";
		QueryBuilder query_Tradeinfo = new QueryBuilder(queryTradeinfoSql);
		query_Tradeinfo.add(p_PaySn);
		query_Tradeinfo.add(p_PaySn);
		
		DataTable result_Tradeinfo = query_Tradeinfo.executeDataTable();
		
		if(result_Tradeinfo.getRowCount() == 0){
			query_Tradeinfo = new QueryBuilder(queryOrderSql);
			query_Tradeinfo.add(p_PaySn);
			query_Tradeinfo.add(p_PaySn);
			result_Tradeinfo = query_Tradeinfo.executeDataTable();
		}
		
		Map<String,String> tradeinfoMap = new HashMap<String,String>();
		
		for (int i = 0; i < result_Tradeinfo.getRowCount(); i++) {
			tradeinfoMap.put("paySn", result_Tradeinfo.getString(i, "paySn"));
			tradeinfoMap.put("payPrice", result_Tradeinfo.getString(i, "totalAmnout"));
			tradeinfoMap.put("totalPrice", result_Tradeinfo.getString(i, "tAmount"));
			tradeinfoMap.put("payDate", result_Tradeinfo.getString(i, "modifyDate"));
		}
		
		return OrdersInfo(p_CpsInfo.get("Channel"),p_CpsInfo.get("UID"), p_PartnersInfo, tradeinfoMap,p_Type);
	}
	
	/**
	 * @Title: OrdersInfo.
	 * @Description: TODO(订单信息).
	 * @param p_Channel
	 * @param p_UID
	 * @param p_PartnersInfo
	 * @param tradeinfoMap
	 * @param p_Type
	 * @return String.
	 * @author CongZN.
	 */
	public static String OrdersInfo(String p_Channel,String p_UID,Map<String,String> p_PartnersInfo,Map<String,String> tradeinfoMap,String p_Type){
		
		Transaction ts = new Transaction();
		
		Map<String,Object> map_json=new LinkedHashMap<String,Object>();
		List<Object> list = new ArrayList<Object>();
		//合作商信息
		map_json.put("source",p_PartnersInfo.get("PartnersCode"));
		map_json.put("uid",p_UID);
		map_json.put("channel",p_Channel);
		String paySn = tradeinfoMap.get("paySn");
		map_json.put("paysn",paySn);
		map_json.put("paydate",tradeinfoMap.get("payDate"));
		map_json.put("totalprice",tradeinfoMap.get("totalPrice"));
		map_json.put("payprice",tradeinfoMap.get("payPrice"));
		
		StringBuffer queryOrderInfoSql = new StringBuffer();
		queryOrderInfoSql.append(" select s1.orderSn,totalAmount,payPrice,s1.createdate,");
		queryOrderInfoSql.append(" s2.productName AS  productName,s2.productId AS  productId,s2.chargeYear AS  chargeYear,");
		queryOrderInfoSql.append(" (select CodeName from zdcode where CodeType ='OrderStatus' and CodeValue = s1.orderStatus) as statusName,");
		// 保险期限
		queryOrderInfoSql.append("s2.ensureDisplay AS  ensureDisplay,");
		// 缴费年限
		queryOrderInfoSql.append("s2.chargeDisplay AS  chargeDisplay,");
		// 生效日期
		queryOrderInfoSql.append("s2.startDate AS  svaliDate,");
		// 支付时间
		queryOrderInfoSql.append("(SELECT createDate FROM tradeinformation  WHERE ordID=s1.ordersn) AS  paymentDate");
		queryOrderInfoSql.append(" from sdorders s1 ,sdinformation s2 where paysn = ? and s2.orderSn = s1.ordersn ");
		
		QueryBuilder query_Orderinfo = new QueryBuilder(queryOrderInfoSql.toString());
		query_Orderinfo.add(paySn);
		
		DataTable result_Orderinfo = query_Orderinfo.executeDataTable();
		Map<String, String> reMap = orderToPolicyNo(result_Orderinfo);
		for (int i = 0; i < result_Orderinfo.getRowCount(); i++) {
			Map<String,Object> map_order = new LinkedHashMap<String,Object>();
			String orderSn = result_Orderinfo.getString(i, "orderSn");
			
			if("send".equals(p_Type)){
				QueryBuilder update_Cps = new QueryBuilder("update cps s set s.ps = 'ts' where s.on=? ",orderSn);
				ts.add(update_Cps);
			}
			
			map_order.put("ordersn",result_Orderinfo.getString(i, "orderSn"));
			map_order.put("totalprice",result_Orderinfo.getString(i, "totalAmount"));
			map_order.put("payprice",result_Orderinfo.getString(i, "payPrice"));
			map_order.put("productname",result_Orderinfo.getString(i, "productName"));
			map_order.put("orderstatus",result_Orderinfo.getString(i, "statusName"));
			map_order.put("productid",result_Orderinfo.getString(i, "productId"));
			map_order.put("chargeYear",result_Orderinfo.getString(i, "chargeYear"));
			map_order.put("createdate",result_Orderinfo.getString(i, "createdate"));
			// 保险期限
			map_order.put("ensureDisplay",result_Orderinfo.getString(i, "ensureDisplay"));
			// 缴费年限
			map_order.put("chargeDisplay",result_Orderinfo.getString(i, "chargeDisplay"));
			// 生效日期
			map_order.put("svaliDate",result_Orderinfo.getString(i, "svaliDate"));
			// 支付时间
			map_order.put("paymentDate",result_Orderinfo.getString(i, "paymentDate"));
			// 保单号
			map_order.put("policyNo",reMap.get(result_Orderinfo.getString(i, "orderSn")));
			list.add(map_order);
			
		}
		map_json.put("orderlist",list);
		
		if("send".equals(p_Type) && !ts.commit()){
			logger.error("CPS 推送更新CPS 表 失败!");
		}
		
		JSONObject object = JSONObject.fromObject(map_json);
		return object.toString();
	}
	
	
	/**
	 * @Title: OrdersInfo.
	 * @Description: TODO(订单信息-接口对应 按订单查询).
	 * @param p_CID
	 * @param p_UID
	 * @param p_Channel
	 * @param p_PartnersInfo
	 * @param p_Type
	 * @param p_SDate
	 * @param p_EDate
	 * @return
	 */
	public static String OrdersInfoNew(String p_CID,String p_UID,String p_Channel,Map<String,String> p_PartnersInfo,
			String p_Type,String p_SDate,String p_EDate){
		
		Transaction ts = new Transaction();
		
		Map<String,Object> map_json=new LinkedHashMap<String,Object>();
		List<Object> list = new ArrayList<Object>();
		//合作商信息
		map_json.put("source",p_PartnersInfo.get("PartnersCode"));
		map_json.put("uid",p_UID);
		map_json.put("channel",p_Channel);
		
		StringBuffer queryOrderInfoSql = new StringBuffer();
		queryOrderInfoSql.append(" select orderSn,totalAmount,payPrice,createdate,");
		queryOrderInfoSql.append(" (SELECT productName FROM sdinformation where orderSn = s1.ordersn) as  productName,");
		queryOrderInfoSql.append(" (SELECT productId FROM sdinformation where orderSn = s1.ordersn) as  productId,");
		queryOrderInfoSql.append(" (select CodeName from zdcode where CodeType ='OrderStatus' and CodeValue = s1.orderStatus) as statusName");
		queryOrderInfoSql.append(" from sdorders s1,cps s2 ");
		queryOrderInfoSql.append(" where s1.ordersn = s2.on ");
		queryOrderInfoSql.append(" AND s1.orderStatus in('5','7','13') ");
		queryOrderInfoSql.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') >= ? ");
		queryOrderInfoSql.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') <= ? ");
		queryOrderInfoSql.append(" AND s1.paySn not like 'BG%' ");
		queryOrderInfoSql.append(" AND s2.cid = ? ");
		if(StringUtil.isNotEmpty(p_UID)){
			queryOrderInfoSql.append(" AND s2.wi = ? ");
		}
		queryOrderInfoSql.append(" ORDER BY s1.createdate desc ");
		
		QueryBuilder query_Orderinfo = new QueryBuilder(queryOrderInfoSql.toString());
		query_Orderinfo.add(p_SDate);
		query_Orderinfo.add(p_EDate);
		query_Orderinfo.add(p_CID);
		if(StringUtil.isNotEmpty(p_UID)){
			query_Orderinfo.add(p_UID);
		}
		
		DataTable result_Orderinfo = query_Orderinfo.executeDataTable();
		
		for (int i = 0; i < result_Orderinfo.getRowCount(); i++) {
			Map<String,Object> map_order = new LinkedHashMap<String,Object>();
			String orderSn = result_Orderinfo.getString(i, "orderSn");
			
			if("send".equals(p_Type)){
				QueryBuilder update_Cps = new QueryBuilder("update cps s set s.ps = 'ts' where s.on=? ",orderSn);
				ts.add(update_Cps);
			}
			
			map_order.put("ordersn",result_Orderinfo.getString(i, "orderSn"));
			map_order.put("totalprice",result_Orderinfo.getString(i, "totalAmount"));
			map_order.put("payprice",result_Orderinfo.getString(i, "payPrice"));
			map_order.put("productid",result_Orderinfo.getString(i, "productId"));
			map_order.put("productname",result_Orderinfo.getString(i, "productName"));
			map_order.put("orderstatus",result_Orderinfo.getString(i, "statusName"));
			map_order.put("createdate",result_Orderinfo.getString(i, "createdate"));
			list.add(map_order);
			
		}
		
		map_json.put("orderlist",list);
		
		if("send".equals(p_Type) && !ts.commit()){
			logger.error("CPS 推送更新CPS 表 失败!");
		}
		
		JSONObject object = JSONObject.fromObject(map_json);
		return object.toString();
	}
	
	/**
	 * @Title: OrdersInfo.
	 * @Description: TODO(订单信息-接口对应 按订单查询).
	 * @param p_CID
	 * @param p_UID
	 * @param p_Channel
	 * @param p_PartnersInfo
	 * @param p_Type
	 * @param p_SDate
	 * @param p_EDate
	 * @return
	 */
	public static JSONObject OrdersInfoNewForMKJ(String p_CID,String p_UID,String p_Channel,Map<String,String> p_PartnersInfo,
			String p_Type,String p_SDate,String p_EDate){
		
		Transaction ts = new Transaction();
		
		Map<String,Object> map_json=new LinkedHashMap<String,Object>();
		List<Object> list = new ArrayList<Object>();
		//合作商信息
		map_json.put("source",p_PartnersInfo.get("PartnersCode"));
		map_json.put("uid",p_UID);
		map_json.put("channel",p_Channel);
		
		StringBuffer queryOrderInfoSql = new StringBuffer();
		queryOrderInfoSql.append(" select s1.orderSn,totalAmount,payPrice,s1.createdate,s4.applicantName,s4.applicantMobile,s2.wi,");
		queryOrderInfoSql.append(" (SELECT productName FROM sdinformation where orderSn = s1.ordersn) as  productName,");
		queryOrderInfoSql.append(" (SELECT productId FROM sdinformation where orderSn = s1.ordersn) as  productId,");
		queryOrderInfoSql.append(" (select CodeName from zdcode where CodeType ='OrderStatus' and CodeValue = s1.orderStatus) as statusName");
		queryOrderInfoSql.append(" from sdorders s1,cps s2 ,sdinformation s3,sdinformationappnt s4 ");
		queryOrderInfoSql.append(" where s1.ordersn = s2.on ");
		queryOrderInfoSql.append(" AND s1.orderStatus in('5','7','13') ");
		queryOrderInfoSql.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') >= ? ");
		queryOrderInfoSql.append(" AND DATE_FORMAT(s1.createdate, '%Y%m%d%H%i%s') <= ? ");
		queryOrderInfoSql.append(" AND s1.paySn not like 'BG%' ");
		queryOrderInfoSql.append(" AND s2.cid = ? ");
		queryOrderInfoSql.append(" AND s1.orderSn = s3.orderSn ");
		queryOrderInfoSql.append(" AND s3.informationSn = s4.informationSn ");
		if(StringUtil.isNotEmpty(p_UID)){
			queryOrderInfoSql.append(" AND s2.wi = ? ");
		}
		queryOrderInfoSql.append(" ORDER BY s1.createdate desc ");
		
		QueryBuilder query_Orderinfo = new QueryBuilder(queryOrderInfoSql.toString());
		query_Orderinfo.add(p_SDate);
		query_Orderinfo.add(p_EDate);
		query_Orderinfo.add(p_CID);
		if(StringUtil.isNotEmpty(p_UID)){
			query_Orderinfo.add(p_UID);
		}
		
		DataTable result_Orderinfo = query_Orderinfo.executeDataTable();
		
		for (int i = 0; i < result_Orderinfo.getRowCount(); i++) {
			Map<String,Object> map_order = new LinkedHashMap<String,Object>();
			String orderSn = result_Orderinfo.getString(i, "orderSn");
			
			if("send".equals(p_Type)){
				QueryBuilder update_Cps = new QueryBuilder("update cps s set s.ps = 'ts' where s.on=? ",orderSn);
				ts.add(update_Cps);
			}
			
			map_order.put("ordersn",result_Orderinfo.getString(i, "orderSn"));
			map_order.put("totalprice",result_Orderinfo.getString(i, "totalAmount"));
			map_order.put("payprice",result_Orderinfo.getString(i, "payPrice"));
			map_order.put("productid",result_Orderinfo.getString(i, "productId"));
			map_order.put("productname",result_Orderinfo.getString(i, "productName"));
			map_order.put("orderstatus",result_Orderinfo.getString(i, "statusName"));
			map_order.put("createdate",result_Orderinfo.getString(i, "createdate"));
			map_order.put("applicantName",result_Orderinfo.getString(i, "applicantName"));
			map_order.put("applicantMobile",result_Orderinfo.getString(i, "applicantMobile"));
			map_order.put("uid",result_Orderinfo.getString(i, "wi"));
			list.add(map_order);
			
		}
		
		map_json.put("orderlist",list);
		
		if("send".equals(p_Type) && !ts.commit()){
			logger.error("CPS 推送更新CPS 表 失败!");
		}
		
		JSONObject object = JSONObject.fromObject(map_json);
		return object;
	}
	
	/**
	 * articleURL:获取url. <br/>
	 *
	 * @author wwy
	 * @param articleid
	 * @return
	 */
	public static String articleURL (String articleid) {
		
		QueryBuilder qb = new QueryBuilder("SELECT URL FROM zcarticle WHERE id = ?", articleid);
		
		return Config.getFrontServerContextPath() + "/" + qb.executeString();
	}
	
	
	/**
	 * @Title: psQueryOrders.
	 * Function:盘石查询订单接口<br/>
	 * Date:2016年12月26日 下午2:29:59 <br/>
	 * return json
	 * @author:wangtz 
	 */
	public String psQueryOrders(){
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(key)
					|| !"c7345811oXd".equals(key)) {
				if (StringUtils.isBlank(key)) {
					json.put("msg", "您的开心保密匙为空！详情请联系开心保客服或者拨打4009789789。");
				} else {
					json.put("msg", "您的开心保密匙未授权或者已失效！详情请联系开心保客服或者拨打4009789789。");
				}
				return ajax(json.toString(), "text/html");
			}
			//执行逻辑
			if (StringUtils.isEmpty(uid)) {
				json.put("msg", "用户id不能为空！");
				return ajax(json.toString(), "text/html");
			}
			if (StringUtils.isEmpty(ordersn) && StringUtils.isEmpty(sdate) && StringUtils.isEmpty(edate)) {
				json.put("msg", "订单号、与查询日期不能同时为空！");
				return ajax(json.toString(), "text/html");
			}
			if (StringUtils.isNotEmpty(sdate)) {
				sdate = DateUtil.toString(DateUtil.parse(sdate+"000000", "yyyyMMddHHmmss"),DateUtil.Format_DateTime);
			}
			if (StringUtils.isNotEmpty(edate)) {
				edate = DateUtil.toString(DateUtil.parse(edate+"235959", "yyyyMMddHHmmss"),DateUtil.Format_DateTime);
			}
			String sql_query = "SELECT" +
							   " s.ordersn,s.paysn,s.paytype,s.paystatus,s.orderstatus,s.payamount,s.productnum,c.pna,s.orderCoupon"+
							   ",c.wi code,'0.2' commRate, DATE_FORMAT(s.createdate,'%Y-%m-%d %H:%i:%s')AS orTime,s.productTotalPrice AS pTotalPrice"+
							   ",si.productId proNo,sp.ProductType AS pType"+
							   " FROM "+
							   " cps c,SDorders s "+
							   " LEFT JOIN sdinformation si ON si.orderSn = s.orderSn "+
							   " LEFT JOIN sdproduct sp ON sp.ProductID= si.productId where c.`ON` = s.orderSn AND s.paySn not like 'BG%' AND c.cid='" + uid + "'";
			if(StringUtils.isNotEmpty(ordersn)){
				sql_query = sql_query + " and s.ordersn='" + ordersn + "'";
			}
			if (StringUtils.isNotEmpty(sdate)) {
				sql_query = sql_query + " and s.createdate>='" + sdate + "'";
			}
			if (StringUtils.isNotEmpty(edate)) {
				sql_query = sql_query + " and s.createdate<='" + edate + "'";
			}
			DataTable dt = new QueryBuilder(sql_query).executeDataTable();
			json.put("data", dt.toList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajax(json.toString(), "text/html");
	}
	/**
	 * 取得订单号对应的n个保单号
	 * @param result_Orderinfo
	 * @return
	 */
	public static Map<String, String> orderToPolicyNo(DataTable result_Orderinfo) {
		// 返回订单号对应保单号
		Map<String, String> reMap = new HashMap<String, String>();
		StringBuffer ordersns = new StringBuffer();
		for (int i = 0; i < result_Orderinfo.getRowCount(); i++) {
			if (i == 0) {
				ordersns.append("'");
				ordersns.append(result_Orderinfo.getString(i, "orderSn"));
				ordersns.append("'");
			} else {
				ordersns.append(",'");
				ordersns.append(result_Orderinfo.getString(i, "orderSn"));
				ordersns.append("'");
			}
		}
		QueryBuilder query_policyNo = new QueryBuilder(
				"SELECT policyNo,ordersn FROM  sdinformationrisktype WHERE ordersn IN(");
		query_policyNo.append(ordersns.toString());
		query_policyNo.append(")");
		StringBuffer policyNos = null;
		DataTable result_policyNo = query_policyNo.executeDataTable();
		for (int i = 0; i < result_policyNo.getRowCount(); i++) {
			if (StringUtil.isNotEmpty(reMap.get(result_policyNo.getString(i,
					"ordersn")))) {
				policyNos = new StringBuffer();
				if (StringUtil.isNotEmpty(result_policyNo.getString(i,
						"policyNo"))
						&& reMap.get(result_policyNo.getString(i, "ordersn"))
								.indexOf(
										result_policyNo
												.getString(i, "policyNo")) < 0) {
					policyNos.append(reMap.get(result_policyNo.getString(i,
							"ordersn"))
							+ ","
							+ result_policyNo.getString(i, "policyNo"));
					reMap.put(result_policyNo.getString(i, "ordersn"),
							policyNos.toString());
				}

			} else {
				reMap.put(result_policyNo.getString(i, "ordersn"),
						result_policyNo.getString(i, "policyNo"));
			}

		}
		return reMap;

	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOrdersn() {
		return ordersn;
	}

	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}
	
	
}
