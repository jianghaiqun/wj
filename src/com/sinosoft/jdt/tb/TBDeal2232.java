package com.sinosoft.jdt.tb;

import java.util.HashMap;

import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
/**
 * 处理华安财险承保报文发送和返回报文解析等
 * 
 * @createDate 2013-7-15
 */
public class TBDeal2232 implements TBDealInterfaceNew {

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

			if ("pass".equals(passFlag)) {
				resultMap.put("appStatus", "1");// 标记成功
				resultMap.put("TRAN_CODE", resMap.get("TRAN_CODE"));
				resultMap.put("BK_SERIAL", resMap.get("BK_SERIAL"));
				resultMap.put("PA_RSLT_CODE", resMap.get("PA_RSLT_CODE"));
				resultMap.put("PA_RSLT_MESG", resMap.get("PA_RSLT_MESG"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				resultMap.put("policyPath", resMap.get("policyPath"));
				resultMap.put("inusred-name", resMap.get("inusred-name"));
				resultMap.put("certi-type", resMap.get("certi-type"));
				resultMap.put("gender", resMap.get("gender"));
				resultMap.put("certi-code", resMap.get("certi-code"));
				resultMap.put("birthday", resMap.get("birthday"));
			} else {
				resultMap.put("PA_RSLT_CODE", resMap.get("PA_RSLT_CODE"));
				resultMap.put("PA_RSLT_MESG", resMap.get("PA_RSLT_MESG"));
				resultMap.put("appStatus", "0");// 标记失败
			}
		} catch (Exception e) {
			resultMap.put("PA_RSLT_MESG", e.getMessage());
			resultMap.put("appStatus", "0");// 标记失败
		}
		return true;
	}

	@Override
	public boolean dealCancelData(HashMap<String, Object> resultMap,
			String comCode, String ordersn, String insuredSn) {
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

}
