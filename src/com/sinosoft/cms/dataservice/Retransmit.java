package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.HttpClientUtil;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.servlets.TBContInsureServlet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.TBSDAction;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.orders.CancelCont;
import com.sinosoft.platform.pub.NoUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Retransmit extends Page {
	/** 
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx init(Mapx params) {
		params.put("CompanyIDList",
				HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}
	/**
	 * 查询订单初始日期
	 * 
	 * @param params
	 * @return
	 */
	public void queryOrder() {
		String OrderSn = $V("OrderSn");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.startDate,a.endDate,a.ensureLimit,a.ensureLimitType,(SELECT a2.tbTradeSeriNo FROM sdorders a2 WHERE a2.orderSn = a.orderSn ) tbTradeSeriNo ,"
				+ " a.insuranceCompany  ,(SELECT a3.channelsn FROM sdorders a3 WHERE  a3.ordersn = a.ordersn) channelsn FROM sdinformation a WHERE a.ordersn = ?");
		QueryBuilder qb = new QueryBuilder(sql.toString(), OrderSn);
		DataTable dt = qb.executeDataTable();
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = "";
		String endDate = "";
		String ensureLimit = "";
		String ensureLimitType = "";
		//add by wangej 增加查询条件淘宝渠道订单号，保险公司编码根据订单号显示
		String tbTradeSeriNo = "";
		String insuranceCompany = "";
		String channelsn = "";
		String Massage = "";
		if(dt.getRowCount()>0){
			try {
				 startDate= sdf_1.format(sdf_1.parse(dt.getString(0, 0)));
				 endDate= sdf_1.format(sdf_1.parse(dt.getString(0, 1)));
				 ensureLimit = dt.getString(0, 2);
				 ensureLimitType = dt.getString(0, 3);
				 tbTradeSeriNo = StringUtil.noNull(dt.getString(0, 4), "");
				 insuranceCompany = StringUtil.noNull(dt.getString(0, 5), "");
				 channelsn = StringUtil.noNull(dt.getString(0, 6), "");
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			String mxbChannelSns = new QueryBuilder( " SELECT GROUP_CONCAT(ChannelCode) FROM ChannelInfo WHERE ParentInnerChanelCode='00006'" ).executeString();
			if(mxbChannelSns.indexOf(channelsn)!=-1 && channelsn !="b2b_qyer"){
				Massage = "美行保订单需在美行保后台操作!";
			}
			Response.setMessage(startDate + "&" + endDate + "&" + ensureLimit
					+ "&" + ensureLimitType + "&" + tbTradeSeriNo + "&" + insuranceCompany  + "&" + Massage);
		}else{
			Response.setMessage("&&&&&&");
		}
		
	}
	
	/**
	 * 重新发送报文
	 */
	public void teamSend() {
		String OrderSn = "";
		try {
			// 保险公司ID
			String CompanyID = $V("CompanyID");
			// 订单号
			OrderSn = $V("OrderSn");
			
			String svalidateA = $V("svalidateA") + " 00:00:00";
			String svalidateB = $V("svalidateB") + " 00:00:00";
			String evaliDate = $V("evaliDate") + " 23:59:59";
			
			if(!svalidateA.equalsIgnoreCase(svalidateB)){
			// 更新电子保单路径
			Transaction ts = new Transaction();
			QueryBuilder qb =new QueryBuilder();
			qb.setSQL("UPDATE sdinformation SET startDate=? ,endDate=?  WHERE ordersn=?  ");
			qb.add(svalidateB);
			qb.add(evaliDate);
			qb.add(OrderSn);
			ts.add(qb);
			QueryBuilder qb_baodan =new QueryBuilder();
			qb_baodan.setSQL("UPDATE sdinformationrisktype SET svaliDate=? ,evaliDate=?  WHERE ordersn=?  and (appstatus='0' or appstatus='' or appstatus is null) ");
			qb_baodan.add(svalidateB);
			qb_baodan.add(evaliDate);
			qb_baodan.add(OrderSn);
			ts.add(qb_baodan);
			ts.commit();
			}

			if (StringUtil.isEmpty(CompanyID) || StringUtil.isEmpty(OrderSn)) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保险公司或者订单号为空!");
				return;
			}
			String sql0 = "select orderstatus from sdorders where orderSn = ? ";
			DataTable dt0 = new QueryBuilder(sql0, OrderSn).executeDataTable();
			if (dt0 == null || dt0.getRowCount() != 1
					|| !"7".equals(dt0.getString(0, 0))) {
				Response.setStatus(0);
				Response.setMessage("发送失败，此订单不存在或者未支付!");
				return;
			}

			String sql = "select appStatus,riskCode from SDInformationRiskType where orderSn = ? ";
			DataTable dt = new QueryBuilder(sql, OrderSn).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保单表数据不存在记录!");
				return;
			}

			int j = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				String appStatus = dt.getString(i, 0);
				if ("1".equals(appStatus)) {
					j++;
				}
				if (!dt.getString(i, 1).startsWith(CompanyID)) {
					Response.setStatus(0);
					Response.setMessage("发送失败，此订单对应保险公司与页面选择不一致，此订单不能直接发送!");
					return;
				}
			}
			if (j == dt.getRowCount()) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保单表数据都已承保成功!");
				return;
			}

			// 判断是否核保
			String uwSql = "select count(1) from zdcode where CodeType='UWCheckClassName' "
					+ "and ParentCode='UWCheckClassName' and CodeValue=?";
			int uwFlag = new QueryBuilder(uwSql, CompanyID).executeInt();
			// 核保时
			if (uwFlag > 0) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck.moreUWCheckRetransmit(OrderSn);
				// 核保结果标记
				String tPassFlag = tMap.get("passFlag").toString();
				String tMessage = "";
				// 核保不通过的情况
				if ("0".equals(tPassFlag)) {
					// 取得错误信息
					List<Map<String, String>> tList = (List<Map<String, String>>) tMap
							.get("result");
					for (Map<String, String> m : tList) {
						tMessage = tMessage + m.get("RecognizeeName") + ","
								+ m.get("rtnMessage") + ";<br/>";
					}
					Response.setStatus(0);
					Response.setMessage(tMessage);
					return;
				}
			}

			String url = Config.interfaceMap.getString("team_refund_interface");
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderSn", OrderSn);
			String res = httpClientUtil.doPost(url, params);
			Map<String, Object> checkResult = HttpClientUtil.parserToMap(res);
			String sql1 = "select appStatus,insureMsg,policyno,recognizeeSn from SDInformationRiskType where orderSn = ? ";
			DataTable dt1 = new QueryBuilder(sql1, OrderSn).executeDataTable();
			String error = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				String appStatus = dt1.getString(i, 0);
				if (!"1".equals(appStatus)) {
					error += dt1.getString(i, 1) + ",";
				}
			}
			if (StringUtil.isNotEmpty(error)) {
				Response.setStatus(0);
				Response.setMessage("承保失败，原因：" + error);
			} else {
				Response.setStatus(1);
				Response.setMessage("团险承保成功!");
		 
			}

		} catch (Exception e) {
			e.printStackTrace();
			Response.setStatus(0);
			Response.setMessage("发送失败! 异常原因：" + e.getMessage());
		} 
	}

	/**
	 * 根据渠道订单号查询订单编号
	 * 
	 * @param params
	 * @return
	 */
	public void queryOrderSn() {
		String tbTradeSeriNo = $V("tbTradeSeriNo");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT orderSn FROM sdorders WHERE tbTradeSeriNo = ?");
		try {
			QueryBuilder qb = new QueryBuilder(sql.toString(),tbTradeSeriNo);
			DataTable dt = qb.executeDataTable();
			String orderSn = "";
			if (dt.getRowCount() > 0) {
				orderSn = dt.getString(0, 0);
				Response.setMessage(orderSn);
				Response.setStatus(1);
			}else{
				Response.setStatus(0);
				Response.setMessage("无订单数据");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(-1);
			Response.setMessage("系统异常");
		}
	}
	/**
	 * 重新发送报文
	 */
	public void send() {
		String OrderSn = "";
		try {
			// 保险公司ID
			String CompanyID = $V("CompanyID");
			// 订单号
			OrderSn = $V("OrderSn");
			
			String svalidateA = $V("svalidateA") + " 00:00:00";
			String svalidateB = $V("svalidateB") + " 00:00:00";
			String evaliDate = $V("evaliDate") + " 23:59:59";
			
			if(!svalidateA.equalsIgnoreCase(svalidateB)){
			// 更新电子保单路径
			Transaction ts = new Transaction();
			QueryBuilder qb =new QueryBuilder();
			qb.setSQL("UPDATE sdinformation SET startDate=? ,endDate=?  WHERE ordersn=?  ");
			qb.add(svalidateB);
			qb.add(evaliDate);
			qb.add(OrderSn);
			ts.add(qb);
			QueryBuilder qb_baodan =new QueryBuilder();
			qb_baodan.setSQL("UPDATE sdinformationrisktype SET svaliDate=? ,evaliDate=?  WHERE ordersn=?  and (appstatus='0' or appstatus='' or appstatus is null) ");
			qb_baodan.add(svalidateB);
			qb_baodan.add(evaliDate);
			qb_baodan.add(OrderSn);
			ts.add(qb_baodan);
			ts.commit();
			}

			if (StringUtil.isEmpty(CompanyID) || StringUtil.isEmpty(OrderSn)) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保险公司或者订单号为空!");
				return;
			}
			String sql0 = "select orderstatus from sdorders where orderSn = ? ";
			DataTable dt0 = new QueryBuilder(sql0, OrderSn).executeDataTable();
			if (dt0 == null || dt0.getRowCount() != 1
					|| !"7".equals(dt0.getString(0, 0))) {
				Response.setStatus(0);
				Response.setMessage("发送失败，此订单不存在或者未支付!");
				return;
			}

			String sql = "select appStatus,riskCode from SDInformationRiskType where orderSn = ? ";
			DataTable dt = new QueryBuilder(sql, OrderSn).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保单表数据不存在记录!");
				return;
			}

			int j = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				String appStatus = dt.getString(i, 0);
				if ("1".equals(appStatus)) {
					j++;
				}
				if (!dt.getString(i, 1).startsWith(CompanyID)) {
					Response.setStatus(0);
					Response.setMessage("发送失败，此订单对应保险公司与页面选择不一致，此订单不能直接发送!");
					return;
				}
			}
			if (j == dt.getRowCount()) {
				Response.setStatus(0);
				Response.setMessage("发送失败，保单表数据都已承保成功!");
				return;
			}

			// 判断是否核保
			String uwSql = "select count(1) from zdcode where CodeType='UWCheckClassName' "
					+ "and ParentCode='UWCheckClassName' and CodeValue=?";
			int uwFlag = new QueryBuilder(uwSql, CompanyID).executeInt();
			// 核保时
			if (uwFlag > 0) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck.moreUWCheckRetransmit(OrderSn);
				// 核保结果标记
				String tPassFlag = tMap.get("passFlag").toString();
				String tMessage = "";
				// 核保不通过的情况
				if ("0".equals(tPassFlag)) {
					// 取得错误信息
					List<Map<String, String>> tList = (List<Map<String, String>>) tMap
							.get("result");
					for (Map<String, String> m : tList) {
						tMessage = tMessage + m.get("RecognizeeName") + ","
								+ m.get("rtnMessage") + ";<br/>";
					}
					Response.setStatus(0);
					Response.setMessage(tMessage);
					return;
				}
			}

			// 调用经代通
			InsureTransferNew itn = new InsureTransferNew();
			itn.callInsTransInterface(CompanyID, OrderSn, null);
			String sql1 = "select appStatus,insureMsg,policyno,recognizeeSn from SDInformationRiskType where orderSn = ? ";
			DataTable dt1 = new QueryBuilder(sql1, OrderSn).executeDataTable();
			String error = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				String appStatus = dt1.getString(i, 0);
				if (!"1".equals(appStatus)) {
					error += dt1.getString(i, 1) + ",";
				}
			}
			if (StringUtil.isNotEmpty(error)) {
				Response.setStatus(0);
				Response.setMessage("承保失败，原因：" + error);
			} else {
				Response.setStatus(1);
				Response.setMessage("承保成功!");
				if(StringUtil.isNotEmpty(OrderSn)&&OrderSn.startsWith("TB")){
					Transaction trans = new Transaction();
					String PolicyNo = dt1.getString(0, 2);
					String recognizeeSn = dt1.getString(0, 3);
					dealJdriskagencyData( OrderSn,   recognizeeSn,  PolicyNo , trans);
					trans.commit();
				}
				TBContInsureServlet tb = new TBContInsureServlet();
				tb.callFCContService(OrderSn);
				//TODO
				//快钱渠道撤单调用接口
				String select = "select channelsn from sdorders where OrderSn ='" + OrderSn + "'";
				String channelsn = (String) new QueryBuilder(select).executeDataTable().get(0, 0);
				if (StringUtil.isNotEmpty(channelsn)) {
					if (Constant.BILL_KQ_CHANNELSN.equals(channelsn)) {
						try {
							// 发送消息队列到消费者
							Map<String, String> dataSendMap = new HashMap<String, String>();
							dataSendMap.put("type", "1");
							dataSendMap.put("orderSn", OrderSn);
							dataSendMap.put("operator", "dealBillCallback");
							ProducerMQ mq1 = new ProducerMQ();
							mq1.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("发送失败! 异常原因：" + e.getMessage());
		}finally{
			//调用订单推送功能
			try {
				sendB2bSDBalanceDetail(OrderSn);
			} catch (Exception e) {
				logger.error("dealB2bBalanceInfo接口出现异常=========" +OrderSn + e.getMessage(), e);
			}
		}
	}
	
	private void sendB2bSDBalanceDetail(String ordersn ) throws Exception{
		if(StringUtil.isEmpty(ordersn)){
			return;
		}
		String sql = "SELECT sds.channelsn,sds.memberid,sri.createdate,sri.riskcode as productId,sri.payprice,sri.recognizeesn as policyno,sri.svalidate AS startdate,sri.evalidate AS enddate " +
				"FROM SDInformationRiskType AS sri,sdorders AS sds WHERE sri.orderSn = ? AND sri.orderSn =sds.orderSn  AND sds.channelsn IN ('b2b_app','b2b_html5')";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(ordersn);
		DataTable dt_lcx = qb.executeDataTable();
		if (dt_lcx.getRowCount() > 0) {
			CancelCont cc = new CancelCont();
			for (int i = 0; i < dt_lcx.getRowCount(); i++) {
				String channelsn = dt_lcx.getString(i, "channelsn");
				String userName = dt_lcx.getString(i, "memberid");
				String createDate = dt_lcx.getString(i, "createdate");
				String productId = dt_lcx.getString(i, "productId");
				String payprice = dt_lcx.getString(i, "payprice");
				String policyno = dt_lcx.getString(i, "policyno");
//				String startDate = dt_lcx.getString(i, "startdate");
//				String endDate = dt_lcx.getString(i, "enddate");
				String format = "yyyy-MM-dd HH:mm:ss";
				Date date = DateUtil.strToDate(createDate, format);
				String startDate= "";
				String endDate= "";
				cc.dealB2bBalanceInfo(channelsn, userName, ordersn, date, productId, payprice, policyno, startDate, endDate);
			}
		}
		
	}
	
	/**
	 * 淘宝数据补齐
	 */
	public void TBComplete() {
		String OrderSn = $V("OrderSn");
		if (OrderSn.indexOf("TB") <0) {
			Response.setLogInfo(0, "只有淘宝核保数据成功，才可以使用此项功能！");
			return;
		}
		String sql = "select activitySn,totalAmount from sdorders where ordersn = ? ";
		DataTable dt = new QueryBuilder(sql, OrderSn).executeDataTable();
		if(dt==null ||dt.getRowCount()==0 ){
			Response.setStatus(0);
			Response.setMessage("核保数据为空,请到jdt更新下核保数据!");
			return;
		}
		
		if(dealInsuranceData(OrderSn)){
			Response.setStatus(1);
			Response.setMessage("出单数据更新成功!");
			return;	
	
		};
		
	}
	/**
	 * 淘宝数据删除
	 */
	public void TBDelete() {
		String OrderSn = $V("OrderSn");
		if (OrderSn.indexOf("TB") <0) {
			Response.setLogInfo(0, "请填写淘宝订单号！");
			return;
		}
		String sql = "SELECT orderStatus FROM sdorders WHERE ordersn= ?";
		DataTable dt = new QueryBuilder(sql, OrderSn).executeDataTable();
		if(dt==null ||dt.getRowCount()==0 ){
			Response.setStatus(0);
			Response.setMessage("数据库没有查到该订单！");
			return;
		}
		String sql_sdi = "SELECT * FROM sdinformationrisktype WHERE ordersn= ?";
		DataTable dt_sdi = new QueryBuilder(sql_sdi, OrderSn).executeDataTable();
		if(!(dt_sdi==null ||dt_sdi.getRowCount()==0 )){
			Response.setStatus(0);
			Response.setMessage("该订单不符合删除后补充数据");
			return;
		}
		if(dealRelaData(OrderSn)){
			Response.setStatus(1);
			Response.setMessage("出单数据删除成功!");
			return;	
	
		};
		
	}
	public boolean dealRelaData(String  ordersn) {
		Transaction trans = new Transaction();
		String sql_sel = "select * from  sdinformationinsured WHERE ordersn= ?";
		QueryBuilder QB_sel=new QueryBuilder(sql_sel,ordersn);
		DataTable dt_sel = QB_sel.executeDataTable();
		String informationsn="";
		String recognizeeSn="";
		if(dt_sel!=null&&dt_sel.getRowCount()!=0){
			 informationsn = dt_sel.getString(0, "informationsn");
			 recognizeeSn = dt_sel.getString(0, "recognizeeSn");
		}
		
		String sql1 = "delete  FROM sdorders WHERE ordersn= ?";
		String sql2 = "delete  FROM sdinformationrisktype WHERE ordersn= ?";
		String sql3 = "delete  FROM tradeinformation WHERE ordid= ?";
		String sql4 = "delete  FROM sdorderitem WHERE ordersn= ?";
		String sql5 = "delete  FROM sdinformationinsured WHERE ordersn= ?";
		String sql6 = "delete  FROM sdinformationappnt WHERE informationsn= ?";
		String sql7 = "delete  FROM tradesummaryinfo WHERE ordersns= ?";
		String sql8 = "delete  FROM tradeinfo WHERE ordersn= ?";
		String sql9 = "delete  FROM sdorderitemoth WHERE ordersn= ?";
		String sql10 = "delete  FROM sdinformationduty WHERE ordersn= ?";
		String sql11 = "delete  FROM sdinformationbnf WHERE recognizeeSn= ?";
		String sql12 = "delete  FROM sdinformationproperty WHERE recognizeeSn= ?";
		
		QueryBuilder QB1=new QueryBuilder(sql1,ordersn);
		QueryBuilder QB2=new QueryBuilder(sql2,ordersn);
		QueryBuilder QB3=new QueryBuilder(sql3,ordersn);
		QueryBuilder QB4=new QueryBuilder(sql4,ordersn);
		QueryBuilder QB5=new QueryBuilder(sql5,ordersn);
		QueryBuilder QB7=new QueryBuilder(sql7,ordersn);
		QueryBuilder QB8=new QueryBuilder(sql8,ordersn);
		QueryBuilder QB9=new QueryBuilder(sql9,ordersn);
		QueryBuilder QB10=new QueryBuilder(sql10,ordersn);
		if(StringUtil.isNotEmpty(informationsn)){
			QueryBuilder QB6=new QueryBuilder(sql6,informationsn);
			trans.add(QB6);
		}	
		if(StringUtil.isNotEmpty(recognizeeSn)){
			QueryBuilder QB11=new QueryBuilder(sql11,recognizeeSn);
			QueryBuilder QB12=new QueryBuilder(sql12,recognizeeSn);
			trans.add(QB11);
			trans.add(QB12);
		}
		trans.add(QB1);
		trans.add(QB2);
		trans.add(QB3);
		trans.add(QB4);
		trans.add(QB5);
		trans.add(QB7);
		trans.add(QB8);
		trans.add(QB9);
		trans.add(QB10);
		
		
		if(trans.commit()){
			return true;
		}
		return false;
		
	}
	public boolean dealInsuranceData(String  ordersn) {
		String sql = "select activitySn,totalAmount from sdorders where ordersn = ? ";
		DataTable dt = new QueryBuilder(sql, ordersn).executeDataTable();
		String originalPrice = ""; 
		if (dt != null && dt.getRowCount() > 0) {
			originalPrice = dt.getString(0, 1);
		}
		BigDecimal oriPrice = new BigDecimal(originalPrice);
		String sql_all = " select b.InsureSerial,b.InsureCode ,b.PayAppNo ,b.PayAccNo,"
				+ "b.ApplyPolicyNo,b.OrderNo,b.PolicyNo,b.totalModalPremium,"
				+ "b.RecognizeeSerial,b.TranCode,b.MakeDate,b.MakeTime,b.validateCode from JDTradeInformationData a , jdriskagencydata b  where 1=1 and a.tbserialno = b.outserial and a.serialNo=? ";
		QueryBuilder qb = new QueryBuilder(sql_all,ordersn);
		DataTable  dt_all= qb.executeDataTable();
		Map<String, String> priceMap = new HashMap<String, String>();
		Transaction trans = new Transaction();
		dealRiskTypeData( trans,  ordersn, oriPrice ,dt_all, priceMap);
		dealOrderData( trans,  ordersn,   oriPrice , dt_all, priceMap);	
		dealTradeData( trans,  ordersn,  oriPrice ,dt_all, priceMap);	
		if(trans.commit()){
			return true;
		}
		return false;
		
	}
	public boolean dealRiskTypeData(Transaction trans,String  ordersn,BigDecimal oriPrice ,DataTable dt,Map<String, String> priceMap) {
	
		
		if (dt != null && dt.getRowCount() != 0) {
				String ApplyPolicyNo = dt.getString(0, "ApplyPolicyNo");
				String PolicyNo = dt.getString(0, "PolicyNo");
				String validateCode = dt.getString(0, "validateCode");
				String totalModalPremium = dt.getString(0, "totalModalPremium");
				String recognizeeSn = dt.getString(0, "RecognizeeSerial");
				String TrandCode = dt.getString(0, "TranCode");
				String MakeDate = dt.getString(0, "MakeDate");
				String MakeTime = dt.getString(0, "MakeTime");
				String insuredate =PubFun.getCurrentDate() + " " + PubFun.getCurrentTime();
				String insureMsg="出单失败";
				String appStatus="0";
				String activityValue = "";
				if("Underwriting-YES".equals(TrandCode)){
					insureMsg="出单成功";
					appStatus="1";
					insuredate=MakeDate + " " + MakeTime;
				}
				String sql = "SELECT policyNo,appStatus,insureDate,recognizeeSn FROM sdinformationrisktype WHERE ordersn = ? ";
				DataTable dt_risktype = new QueryBuilder(sql, ordersn).executeDataTable();
				if(dt_risktype != null && dt_risktype.getRowCount() != 0 ){
					
					String policyNocms = dt_risktype.getString(0, "policyNo");
					String appStatuscms = dt_risktype.getString(0, "appStatus");
					String insureDatecms = dt_risktype.getString(0, "insureDate");
					String recognizeeSncms = dt_risktype.getString(0, "recognizeeSn");
					if("1".equals(appStatuscms)){
						insuredate=insureDatecms;
						PolicyNo=policyNocms;
						insureMsg="出单成功！";
						appStatus="1";
						if(!"Underwriting-YES".equals(TrandCode)){
						dealJdriskagencyData( ordersn,   recognizeeSncms,  policyNocms , trans);
						}if("Underwriting-YES".equals(TrandCode)&& StringUtil.isEmpty(PolicyNo)){
						dealJdriskagencyData( ordersn,   recognizeeSncms,  policyNocms , trans);	
						}
					}
					
				}
				BigDecimal discoPrice = new BigDecimal(totalModalPremium);
				if (oriPrice.compareTo(discoPrice) > 0)  {
					activityValue = oriPrice.subtract(discoPrice).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				}
				String payPrice=totalModalPremium;
				StringBuffer riskTypeSQL = new StringBuffer();
				riskTypeSQL.append("UPDATE sdinformationrisktype ");
				riskTypeSQL.append("SET policyNo = ?, ");
				riskTypeSQL.append("applyPolicyNo = ?, ");
				riskTypeSQL.append("insureMsg = ?, ");
				riskTypeSQL.append("appStatus = ?, ");
				riskTypeSQL.append("activityValue = ?, ");
				riskTypeSQL.append("payPrice = ?, ");
				riskTypeSQL.append("insuredate = ?, ");
				riskTypeSQL.append("modifydate = ?, ");
				riskTypeSQL.append("validateCode = ? ");
				riskTypeSQL.append("WHERE orderSn = ? AND recognizeeSn = ? ");
				QueryBuilder riskTypeQB = new QueryBuilder();
				riskTypeQB.setSQL(riskTypeSQL.toString());
				riskTypeQB.add(PolicyNo);
				riskTypeQB.add(ApplyPolicyNo);
				riskTypeQB.add(insureMsg);
				riskTypeQB.add(appStatus);
				riskTypeQB.add(activityValue);
				riskTypeQB.add(payPrice);
				riskTypeQB.add(insuredate);
				riskTypeQB.add(insuredate);
				riskTypeQB.add(validateCode);
				riskTypeQB.add(ordersn);
				riskTypeQB.add(recognizeeSn);
				trans.add(riskTypeQB);

			}
		return true;
	}
	
	public boolean dealOrderData(Transaction trans,String  ordersn,BigDecimal oriPrice ,DataTable dt,Map<String, String> priceMap) {
		String payPrice = dt.getString(0, "totalModalPremium");
		String PayAccNo = dt.getString(0, "PayAccNo");
		String sumActivity="";
		String activityValue = "";
		BigDecimal discoPrice = new BigDecimal(payPrice);
		if (oriPrice.compareTo(discoPrice) > 0)  {
			activityValue = oriPrice.subtract(discoPrice).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			sumActivity = activityValue ;
		}
		String channelsn="tb_ht";
		//modified by yuzj for 淘宝蚂蚁的处理 @20160701 begin
		if(ordersn.startsWith("TBMY")){//淘宝蚂蚁的话渠道号更改为tb_my
			channelsn="tb_my";
		}else if(ordersn.startsWith("QN")){
			channelsn="qunar";
		}
		//modified by yuzj for 淘宝蚂蚁的处理 @20160701 end
		TBSDAction tbsdAction = new TBSDAction();
		if (tbsdAction.isTbsdInsured(ordersn)) {
			channelsn="tbsd";
		}
		
		StringBuffer orderSQL = new StringBuffer();
		orderSQL.append("UPDATE sdorders ");
		orderSQL.append("SET payAmount = ?, ");
		orderSQL.append("paySn = ?, ");
		orderSQL.append("orderStatus = '7', ");
		orderSQL.append("payType = 'tbzfb', ");
		orderSQL.append("payStatus = '2', ");
		orderSQL.append("payPrice = totalAmount, ");
		orderSQL.append("channelsn = ?, ");
		orderSQL.append("orderActivity = ?, ");
		orderSQL.append("payPrice = ?, ");
		orderSQL.append("sumActivity = ? ");
		orderSQL.append("WHERE orderSn = ? ");
		QueryBuilder orderQB = new QueryBuilder();
		orderQB.setSQL(orderSQL.toString());
		orderQB.add(payPrice);
		orderQB.add(PayAccNo);
		orderQB.add(channelsn);
		orderQB.add(activityValue);
		orderQB.add(payPrice);
		orderQB.add(sumActivity);
		orderQB.add(ordersn);
		trans.add(orderQB);
		priceMap.put("sumActivity", sumActivity);
		priceMap.put("TotalAmount", String.valueOf(oriPrice));
		priceMap.put("payPrice",String.valueOf(payPrice));
		return true;
	}
	public boolean dealItemData(Transaction trans,String  ordersn, String CompanyID,BigDecimal oriPrice ,DataTable dt) {

		return true;
	}
	public boolean dealTradeData(Transaction trans,String  ordersn,BigDecimal oriPrice ,DataTable dt,Map<String, String> priceMap) {
		
		String PayAppNo = dt.getString(0, "PayAppNo");
		String PayAccNo = dt.getString(0, "PayAccNo");
		String TrandCode = dt.getString(0, "TrandCode");
		String MakeDate = dt.getString(0, "MakeDate");
		String MakeTime = dt.getString(0, "MakeTime");
		String insuredate =PubFun.getCurrentDate() + " " + PubFun.getCurrentTime();
		if("Underwriting-YES".equals(TrandCode)){
			insuredate=MakeDate + " " + MakeTime;
		}
		StringBuffer tradeSQL = new StringBuffer();
		tradeSQL.append("UPDATE TradeInformation ");
		tradeSQL.append("SET ordAmt = ?, ");
		tradeSQL.append("tradeResult = ?, ");
		tradeSQL.append("tradeSeriNO = ?, ");
		tradeSQL.append("tradeBank = ?, ");
		tradeSQL.append("payStatus = ?, ");
		tradeSQL.append("sendDate = ?, ");
		tradeSQL.append("receiveDate = ?, ");
		tradeSQL.append("tradeCheckSeriNo = ?, ");
		tradeSQL.append("modifyDate = '"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"', ");
		tradeSQL.append("payType = 'tbzfb' ");
		tradeSQL.append("WHERE ordId = ? ");
		QueryBuilder tradeQB = new QueryBuilder();
		tradeQB.setSQL(tradeSQL.toString());
		tradeQB.add(oriPrice);
		tradeQB.add("0");
		tradeQB.add(PayAppNo);
		tradeQB.add("");
		tradeQB.add("1");
		tradeQB.add(insuredate);
		tradeQB.add(insuredate);
		tradeQB.add(PayAccNo);
		tradeQB.add(ordersn);
		trans.add(tradeQB);
		dealTradeSummary(PayAccNo,priceMap,ordersn,trans);
		return true;
	}
	public boolean dealTradeSummary(String TradeSeriNo, Map<String, String> priceMap, String OrderNumber,
			Transaction trans) {
		QueryBuilder tradesummaryinfocountQB = new QueryBuilder(
				" SELECT COUNT(1) FROM tradesummaryinfo WHERE PaySn=? ");
		tradesummaryinfocountQB.add(TradeSeriNo);
		int tradesummaryinfocount = tradesummaryinfocountQB.executeInt();
		if (tradesummaryinfocount <= 0) {
			QueryBuilder inserttradesummaryinfoQB = new QueryBuilder(
					" INSERT INTO tradesummaryinfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			inserttradesummaryinfoQB.add(NoUtil.getMaxNo("TradeSummaryID", 11));
			inserttradesummaryinfoQB.add(TradeSeriNo);
			inserttradesummaryinfoQB.add(TradeSeriNo);
			inserttradesummaryinfoQB.add("0");
			inserttradesummaryinfoQB.add(OrderNumber);
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add(priceMap.get("sumActivity"));
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add("tbzfb");
			QueryBuilder paytypeqb = new QueryBuilder(
					" SELECT description FROM paybase WHERE payType=? LIMIT 1 ");
			paytypeqb.add("tbzfb");
			inserttradesummaryinfoQB.add(paytypeqb.executeString());
			inserttradesummaryinfoQB.add(priceMap.get("TotalAmount"));
			inserttradesummaryinfoQB.add(priceMap.get("payPrice"));
			inserttradesummaryinfoQB.add(PubFun.getCurrent());
			inserttradesummaryinfoQB.add(PubFun.getCurrent());

			trans.add(inserttradesummaryinfoQB);
		} else {
			QueryBuilder upadtetradesummaryinfoQB = new QueryBuilder(
					" UPDATE tradesummaryinfo SET TradeResult=?,OrderSns=?,ActivitySumAmount=?,TotalAmount=?,TradeAmount=?,ModifyDate=?,PayType='tbzfb' WHERE paysn=? ");
			upadtetradesummaryinfoQB.add("0");
			upadtetradesummaryinfoQB.add(OrderNumber);
			upadtetradesummaryinfoQB.add(priceMap.get("sumActivity"));
			upadtetradesummaryinfoQB.add(priceMap.get("TotalAmount"));
			upadtetradesummaryinfoQB.add(priceMap.get("payPrice"));
			upadtetradesummaryinfoQB.add(PubFun.getCurrent());
			upadtetradesummaryinfoQB.add(TradeSeriNo);

			trans.add(upadtetradesummaryinfoQB);
		}
		return true;
	}
	public boolean dealJdriskagencyData(String orderSn,  String recognizeeSn, String PolicyNo ,Transaction trans) {
			StringBuffer riskTypeSQL = new StringBuffer();
			riskTypeSQL.append("UPDATE jdriskagencydata ");
			riskTypeSQL.append("SET policyNo = ?, ");
			riskTypeSQL.append("TranCode = 'Underwriting-YES' ");
			riskTypeSQL.append("WHERE RecognizeeSerial = ? ");
			QueryBuilder riskTypeQB = new QueryBuilder();
			riskTypeQB.setSQL(riskTypeSQL.toString());
			riskTypeQB.add(PolicyNo);
			riskTypeQB.add(recognizeeSn);
			trans.add(riskTypeQB);
		return true;
	}
	public boolean checkappsatsu ( String OrderSn){
		
	String sqlcheck = "select policyNo , appStatus from sdinformationriskType where ordersn = ? ";
	DataTable dtcheck = new QueryBuilder(sqlcheck, OrderSn).executeDataTable();

	if (sqlcheck == null || dtcheck.getRowCount() == 0) {
		Response.setStatus(0);
		Response.setMessage("保单表数据为空,更新失败！");
		return false;
	}
	String  policyNofrom = dtcheck.getString(0, 0);
	String appStatus = dtcheck.getString(0, 1);
	
	if (!"1".equals(appStatus)) {
		Response.setLogInfo(0, "保单表数据承保状态为为出单失败,更新失败！");
		return false;
	}

	if (StringUtil.isEmpty(policyNofrom)) {
		Response.setLogInfo(0, "保单表数据保单号为空,更新失败！！");
		return false;
	}
	
	return true;
	
	}
	public static void main(String[] args) {
		Retransmit rr =new Retransmit();
		String OrderSn="TB20150000005001";
		if(StringUtil.isNotEmpty(OrderSn)&&OrderSn.startsWith("TB")){
			Transaction trans = new Transaction();
			String PolicyNo = "TN00008010";
			String recognizeeSn = "tb20150000006595";
			rr.dealJdriskagencyData( OrderSn,   recognizeeSn,  PolicyNo , trans);
			trans.commit();
		}
	}
	
}
