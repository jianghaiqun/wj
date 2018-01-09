package com.sinosoft.jdt.tb;

import java.util.HashMap;

import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
/**
 * 处理合众承保报文发送和返回报文解析等
 * @author ncc
 */
public class TBDeal2258 implements TBDealInterfaceNew {

	@Override
	public boolean dealData(HashMap<String, Object> resultMap, String strManageCom, String strOrderSn,String insuredSn)
	{
		try
		{
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			resMap = parse.dealData("09", strManageCom, strOrderSn,insuredSn);
			
			String passFlag = resMap.get("passFlag").toString();
			if ("pass".equals(passFlag))
			{
				resultMap.put("appStatus", "1");// 标记成功
				resultMap.put("PA_RSLT_MESG", resMap.get("rtnMessage"));
				resultMap.put("policyPath", resMap.get("policyUrl"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				
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
		return false;
	}


}
