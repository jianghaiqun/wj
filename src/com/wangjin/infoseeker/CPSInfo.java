package com.wangjin.infoseeker;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.infoseeker.iptranse.IPSeeker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPSInfo extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), 1), "yyyy-MM-dd");
	private static String tomorrow = DateUtil.toString(DateUtil.addYear(new Date(), 1), "yyyy-MM-dd");

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initStaff(Mapx params) {
		Mapx map = new Mapx();
		map.put("orderOriginFlag", HtmlUtil.codeToOptions("cpsInfo.orderOriginFlag", "all","all"));
		map.put("OrderStatus", HtmlUtil.codeToOptions("orderstatus", true));
		map.put("yesterday", yesterday);
		map.put("today", today);
		map.put("tomorrow", tomorrow);
		return map;
	}

	/**
	 * 获得发布统计的数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String allDate = dga.getParam("allDate");
		String OrderStatus = dga.getParam("OrderStatus");
		String orderOriginFlag = dga.getParam("orderOriginFlag");
		if("all".equals(orderOriginFlag)) orderOriginFlag="";
		// 生效日期起期
		String svaliDate = dga.getParam("svaliDate");
		// 生效日期止期
		String evaliDate = dga.getParam("evaliDate");
		// 日期都是空的情况 为页面初始化,起始时间都置为昨天，截止时间都置为今天
		if (StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)
				&& StringUtil.isEmpty(svaliDate)
				&& StringUtil.isEmpty(evaliDate)) {
			startDate = yesterday;
			endDate = today;
			svaliDate = yesterday;
			evaliDate = today;
		}
		String parameter_orderOriginFlag = "";
		if(StringUtil.isNotEmpty(orderOriginFlag)){
			if((orderOriginFlag.indexOf("xb2b_ht")>0)
					||(orderOriginFlag.indexOf("xb2c_pc")>0)||(orderOriginFlag.indexOf("xb2c_wap")>0)){
				orderOriginFlag = orderOriginFlag.replaceAll("xb2b_ht", "b2b_ht").
						replaceAll("xb2c_pc", "b2c_pc").replaceAll("xb2c_wap", "b2c_wap");
			}

			String channel = QueryUtil.getChannelInfo(orderOriginFlag,"");
			parameter_orderOriginFlag = " EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = orders.channelsn and ChannelCode IN ("+channel+"))";
		} 
		String parameter_OrderStatus = "";
		// 记录生效日期的查询条件
		String parameter_vaildate = "";
		if (!"1".equals(allDate)) {
			if (null != OrderStatus) {
				if (StringUtil.isNotEmpty(OrderStatus)) {
					parameter_OrderStatus = " where orders.orderStatus= '"+OrderStatus.trim()+"'";
				}
			}
			
			if (StringUtil.isNotEmpty(orderOriginFlag)) {
				if (!"".equals(parameter_OrderStatus)) {
					parameter_orderOriginFlag = " AND " +parameter_orderOriginFlag;
				}else{
					parameter_orderOriginFlag = " WHERE " +parameter_orderOriginFlag;
				}
			}
			String parameter_vaildate_con = "";
			if (!"".equals(parameter_OrderStatus)
					|| !"".equals(parameter_orderOriginFlag)) {
				parameter_vaildate_con = " and ";

			} else {
				parameter_vaildate_con = " where ";
			}

			// 生效日期的查询条件
			if (StringUtil.isNotEmpty(svaliDate)) {
				parameter_vaildate += parameter_vaildate_con
						+ " DATE_FORMAT(info.startDate,'%Y-%m-%d') >= '"
						+ svaliDate + "'";
				parameter_vaildate_con = " and ";
			}
			if (StringUtil.isNotEmpty(evaliDate)) {
				parameter_vaildate += parameter_vaildate_con
						+ " DATE_FORMAT(info.startDate,'%Y-%m-%d') <= '"
						+ evaliDate + "'";
			}
			if (StringUtil.isNotEmpty(startDate)) {
				parameter_vaildate += " and DATE_FORMAT(et.selecteddate,'%Y-%m-%d')>='" + startDate + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter_vaildate += " and DATE_FORMAT(et.selecteddate,'%Y-%m-%d')<='" + endDate + "'";
			}
		}
		//新表SQL
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 start
		StringBuffer sql = new StringBuffer();
		sql.append(" select orders.channelsn 'ordersOrigin',info.startDate 'svaliDate', orders.ordersn 'ordersn',info.productName 'productName',FORMAT(orders.totalAmount,2) 'totalAmount',"
				+ " FORMAT((orders.totalAmount*(select a2.feerate from sdproduct a2 where info.productid=a2.productid)),2) 'charge',"
				+ " h.codeName AS orderstatusname ,DATE_FORMAT(orders.createDate,'%Y-%m-%d %H:%i:%s') 'orderdate',DATE_FORMAT(information.createdate,'%Y-%m-%d %H:%i:%s') 'paydate',"
				+ " cps.dt 'ordersType' ,orders.sumcoupon as sumcoupon ,orders.sumintegral as sumintegral,orders.payprice as payprice ");
		sql.append(" from SDOrders as orders left join SDOrderItem as orderItem on orders.orderSn = orderItem.orderSn left join SDInformation as info on orderItem.orderSn = info.orderSn ");
		sql.append(" left join SDInformationInsured infoInsured on info.informationSn = infoInsured.informationSn left join TradeInformation as information on (orders.ordersn = information.ordid)");
		sql.append(" left join zdcode h on  h.codevalue=orders.orderstatus  and h.CodeType='orderstatus' ");
		sql.append(" left join  cps on orders.ordersn=cps.on ");
		sql.append(" left join (SELECT CASE WHEN a.orderstatus IN ('0','1','2','3','4','5','6','8') THEN a.createdate ELSE b.receivedate END selecteddate ,a.orderstatus,a.createdate,b.receivedate,a.ordersn FROM sdorders a LEFT JOIN tradeinformation  b ON   a.ordersn=b.ordid) et ");
		sql.append(" on orders.ordersn= et.ordersn ");
		sql.append(parameter_OrderStatus);
		sql.append(parameter_orderOriginFlag);
		sql.append(parameter_vaildate);
		sql.append("group by ordersn");
		
		//modify by wangchangyang req20130419刘洋-CMS后台数据报表-回购率查询 end
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		IPSeeker ipseeker = new IPSeeker();
		dt.insertColumn("KID");
		dt.insertColumn("orderstatus");
		//实际支付金额
		dt.insertColumn("payamount");
		for (DataRow dr : dt) {
			// req20121128002:CPS 王昌旸  add start
			if (null != dr.get("ordersOrigin")) {
				/*if (dr.get("ordersType").equals("web")) {
					if ("cps_01".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "亿起发");
					} else if ("02".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "自建联盟");
					} else if ("03".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "金融界");
					} else if ("cps_04".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "59秒");
					} else if ("05".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "今题网");
					} else if ("06".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "我搜联盟");
					} else if ("07".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "QQ联合登陆");
					} else if ("08".equals(dr.get("ordersOrigin"))) {
						dr.set("ordersOrigin", "QQ彩贝登陆");
					}
				} else if (dr.get("ordersType").equals("cps")) {
					dr.set("ordersOrigin", "cps");
				} else {
					dr.set("ordersOrigin", "未知");
				}*/
				String channel = new QueryBuilder("SELECT ChannelName FROM `channelinfo` WHERE ChannelCode=?",String.valueOf(dr.get("ordersOrigin"))).executeString();
				
				dr.set("ordersOrigin", channel);
			}
			// req20121128002:CPS 王昌旸  add end
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			dr.set("KID",StringUtil.md5Hex(PubFun.getKeyValue() + String.valueOf(dr.get("ordersn"))));
			//实际支付金额=订单总金额-优惠券（或活动）金额-积分抵值金额
			if(dr.get("payprice")!=null){
			dr.set("payamount",dr.get("payprice").toString());
			}
		}
		ipseeker.closeFile();
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: changeBigDecimalData 
	* @Description: TODO(将金额转换为Bigdecimal，有异常记录订单信息) 
	* @return BigDecimal    返回类型 
	* @author zhangjing
	 */
	private BigDecimal changeBigDecimalData(Object obj,String ordersn){
		String value=String.valueOf(obj);
		if(value==null||value.length()==0||"null".equals(value.toLowerCase())){
			return new BigDecimal("0");
		}else{
			value=value.replaceAll(",","");
			//非数字类型的转换的异常捕捉，并返回0
			try {
				Double.parseDouble(value);
			} catch (Exception e) {
				logger.error("转换优惠金额数据类型时发生异常，订单号为："+ordersn + e.getMessage(), e);
				return new BigDecimal("0");
			}
			return new BigDecimal(value);
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
}
