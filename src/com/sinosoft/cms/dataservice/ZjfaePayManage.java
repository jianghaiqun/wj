package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.util.AesUtilQN;
import cn.com.sinosoft.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.infoseeker.toConcealUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
/**
 * ClassName: ZjfaePayManage <br/>
 * Function: 浙金支付管理. <br/>
 * date: 2017年1月11日 下午4:18:03 <br/>
 *
 * @author wwy
 * @version 
 */
public class ZjfaePayManage extends Page {
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {
		params.put("startDate", com.sinosoft.lis.pubfun.PubFun.getPrevMonthDay(DateUtil.getCurrentDate("yyyy-MM-dd")));
		params.put("endDate", DateUtil.getCurrentDate("yyyy-MM-dd"));
		return params;
	}
	
	/**
	 * dg1DataBind:浙金支付结果. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String startTradeDate = (String) dga.getParams().get("startDate");
		String endTradeDate = (String) dga.getParams().get("endDate");
		String orderSn = (String) dga.getParams().get("orderSn");
		String tradeId = (String) dga.getParams().get("tradeId");
		String insuredname = (String) dga.getParams().get("insuredname");
		String idNO = (String) dga.getParams().get("idNO");
		String mobile = (String) dga.getParams().get("mobile");
		String refundflag = (String) dga.getParams().get("refundflag");
		QueryBuilder qb = new QueryBuilder("select z.ordersn, r.recognizeeSn, z.SubPayPrice, i.recognizeeName, i.recognizeeIdentityId, r.svaliDate, r.evaliDate, r.cancelDate "
				+ ", IF(z.refundflag = '00', '#AAAAAA', 'FFFFFF') as color, i.recognizeeMobile, r.appStatus "
				+ " from zjfaetradeinfo z, sdinformationrisktype r, sdinformationinsured i "
				+ " where z.SubOrderId = r.recognizeeSn and i.recognizeeSn = r.recognizeeSn "
				+ " and z.TradeFlag = '00'");
		if (StringUtil.isNotEmpty(startTradeDate)) {
			qb.append(" and z.TradeDate >= '" + startTradeDate + " 00:00:00'");
		}
		if (StringUtil.isNotEmpty(endTradeDate)) {
			qb.append(" and z.TradeDate <= '" + endTradeDate + " 23:59:59'");
		}
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and r.orderSn = '" + orderSn + "'");
		}
		if (StringUtil.isNotEmpty(tradeId)) {
			qb.append(" and z.tradeId = '" + tradeId + "'");
		}
		if (StringUtil.isNotEmpty(insuredname)) {
			qb.append(" and i.recognizeeName like '%" + insuredname + "%'");
		}
		if (StringUtil.isNotEmpty(idNO)) {
			qb.append(" and i.recognizeeIdentityId = '" + idNO + "'");
		}
		if (StringUtil.isNotEmpty(mobile)) {
			qb.append(" and i.recognizeeMobile = '" + mobile + "'");
		}
		if (StringUtil.isNotEmpty(refundflag)) {
			if ("1".equals(refundflag)) {
				qb.append(" and z.refundflag <> '00'");
			} else {
				qb.append(" and z.refundflag = '00'");
			}
		}
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by z.TradeDate desc ");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = (String)dt.get(i, 4);
			if (StringUtil.isEmpty(str)) {
			}else if (str.length() >=6) {
				String mStr = str.substring(0, str.length() - 6) + "******";
				dt.set(i, 4,mStr);
			}else if(str.length() >=3 && str.length() <6){
				String mStr = str.substring(0, str.length() - 3) + "***";
				dt.set(i, 4,mStr);
			}else{
				
			}
			dt.set(i, 9,toConcealUtil.toConceal((String)dt.get(i, 9),4));
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * refund:浙金支付退款. <br/>
	 *
	 * @author wwy
	 */
	public void refund() {
		String recognizeeSns = $V("IDs");
		QueryBuilder qb = new QueryBuilder(" select count(1) from sdorders o, sdinformationrisktype r where o.ordersn = r.ordersn "
				+ " and find_in_set('" + recognizeeSns + "', r.recognizeeSn) and r.appstatus = '1' ");
		int count = qb.executeInt();
		if (count > 0) {
			Response.setLogInfo(1, "选择的订单中存在承保状态！");
			return;
		}
		Map<String, Object> result = zjfaeRefund(recognizeeSns, Constant.CHANNELSN_ZJFAE);
		
		if ("0".equals(result.get("status"))) {
			Response.setLogInfo(1, "退款成功！");
			return;
		} else {
			Response.setLogInfo(1, "退款失败！");
			return;
		}
	}

	/**
	 * zjfaeRefund:调用退款接口. <br/>
	 *
	 * @author wwy
	 * @param policyNo
	 * @param channelsn
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> zjfaeRefund (String recognizeeSn, String channelsn) {
		ObjectMapper mapper = new ObjectMapper();
		
		//String url = "http://172.100.101.199:8082/FrontTrade/zjfae/zjfaeRefund";
		//String url = "http://120.27.192.221:8080/FrontTrade/zjfae/zjfaeRefund";
		String url = Config.getValue("ZjfaeRefund");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("recognizeeSn", recognizeeSn);
		param.put("channelSn", channelsn);
		String params = AesUtilQN.encrypt(JSON.toJSONString(param), Constant.CHANNELSN_ZJFAE);
		try {
			String result = HttpClientUtil.doPost(url, params);
			
			logger.info("浙金支付退款返回结果：{}", result);
			return mapper.readValue(result, HashMap.class);
		} catch (Exception e) {
			logger.error("浙金支付退款异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		ZjfaePayManage c = new ZjfaePayManage();
		
		c.zjfaeRefund("PEAK20163201Q000E00042", "zjfae");
	}
}
