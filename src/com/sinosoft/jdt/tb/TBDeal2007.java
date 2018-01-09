package com.sinosoft.jdt.tb;

import java.util.HashMap;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDOrderItemOthSchema;
/**
 * 处理平安承保报文发送和返回报文解析等
 * 
 * @createDate 2012-11-14
 */
public class TBDeal2007 implements TBDealInterfaceNew {

	@Override
	public boolean dealData(HashMap<String, Object> resultMap, String strManageCom, String strOrderSn,String insuredSn) {
		try {
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			boolean isB2b = PubFun.getChannelsnByOrdersn(strOrderSn);
			if(isB2b){
				resMap = parse.dealData("03", strManageCom, strOrderSn,insuredSn);
			}else{
				resMap = parse.dealData("01", strManageCom, strOrderSn,insuredSn);
			}
			String passFlag = resMap.get("passFlag").toString();
			resultMap.put("PA_RSLT_MESG", resMap.get("rtnMessage"));
			
			SDInformationSchema sdinformation = new SDInformationSchema();
			SDInformationSet informationSet = sdinformation.query(new QueryBuilder("where orderSn =?",strOrderSn));
			sdinformation = informationSet.get(0);
			SDOrderItemOthSchema sdOrderItemOth = UsersUWCheck.getSDOrderItemOth(strOrderSn, sdinformation.getinformationSn(),
					insuredSn);
			
			if ("pass".equals(passFlag)) {
				//更新订单重发流水号
				sdOrderItemOth.settpySn(getTpySn(String.valueOf(resMap.get("tpySn"))));
				resultMap.put("appStatus", "1");// 标记成功
				resultMap.put("applyPolicyNo", resMap.get("applyPolicyNo"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				resultMap.put("noticeNo", resMap.get("noticeNo"));
				resultMap.put("validateCode", resMap.get("validateCode"));
				resultMap.put("totalPremium", resMap.get("totalPremium"));
			} else {
				//更新订单重发流水号
				sdOrderItemOth.settpySn(getTpySn(String.valueOf(resMap.get("tpySn"))));
				resultMap.put("appStatus", "0");// 标记失败
			}
			
			sdOrderItemOth.update();
			
		} catch (Exception e) {
			resultMap.put("appStatus", "0");// 标记失败
		}
		return true;
	}

	@Override
	public boolean dealCancelData(HashMap<String, Object> resultMap,
			String comCode,String ordersn, String insuredSn) {
		try{
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			HashMap<String, Object> resMap = parse.dealData("02", comCode, ordersn,insuredSn);
			resultMap.put("passFlag", resMap.get("passFlag"));
			resultMap.put("rtnMessage", resMap.get("rtnMessage"));// 标记失败
		} catch (Exception e) {
			resultMap.put("rtnMessage", e.getMessage());
			resultMap.put("passFlag", "nopass");// 标记失败
		}
		return true;
	}
	
	/**
	 * 生成重发流水号
	 * @param insuredSn 被保人编号
	 * @return 重发流水号
	 */
	public String getTpySn (String tpySn) {
		if(tpySn.indexOf("-") > -1){
			String[] arr = tpySn.split("-");
			int sn = Integer.valueOf(arr[1]);
			sn = sn + 1;
			tpySn = arr[0] + "-" + String.valueOf(sn);
			
		}
		return tpySn;
	}

}
