package com.sinosoft.jdt.tb;

import java.util.HashMap;

import com.sinosoft.jdt.ParseXMLToMapNew;

/**
 * 处理人保财险承保报文发送和返回报文解析等
 * @author wangcaiyun 
 *
 */
public class TBDeal2236 implements TBDealInterfaceNew {
	@Override
	public boolean dealData(HashMap<String, Object> resultMap, String strManageCom, String strOrderSn,String insuredSn)
	{
		try 
		{
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			HashMap<String, Object> resMap = parse.dealData("09", strManageCom, strOrderSn,insuredSn);
			String passFlag = resMap.get("passFlag").toString();
			
			if ("pass".equals(passFlag))
			{
				resultMap.put("appStatus", "1");// 标记成功
				resultMap.put("PA_RSLT_MESG", resMap.get("rtnMessage"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				resultMap.put("policyPath", resMap.get("policyUrl"));
			}
			else  
			{
				resultMap.put("PA_RSLT_MESG",  resMap.get("rtnMessage"));
				resultMap.put("appStatus", "0");// 标记失败
			}
		}
		catch (Exception e)
		{
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
