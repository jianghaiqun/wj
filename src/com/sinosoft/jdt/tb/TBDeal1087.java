package com.sinosoft.jdt.tb;

import java.util.HashMap;

import com.sinosoft.jdt.ParseXMLToMapNew;
/**
 * 处理太平洋财险承保报文发送和返回报文解析等
 * @author zhangjinquan 11180
 * @createDate 2012-11-14
 */
public class TBDeal1087 implements TBDealInterfaceNew {

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
				resultMap.put("applyPolicyNo", resMap.get("applyPolicyNo"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				resultMap.put("totalPremium", resMap.get("totalPremium"));
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
			String strManageCom, String ordersn, String insuredSn) {
		// TODO Auto-generated method stub
		return false;
	}


}
