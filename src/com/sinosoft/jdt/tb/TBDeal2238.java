package com.sinosoft.jdt.tb;

import java.util.HashMap;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDOrderItemOthSchema;

/**
 * 处理易安财险承保报文发送和返回报文解析等
 * @author wangcaiyun 
 *
 */
public class TBDeal2238 implements TBDealInterfaceNew {
	@Override
	public boolean dealData(HashMap<String, Object> resultMap, String strManageCom, String strOrderSn,String insuredSn)
	{
		try 
		{
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			boolean isB2b = PubFun.getChannelsnByOrdersn(strOrderSn);
			if(isB2b){
				resMap = parse.dealData("03", strManageCom, strOrderSn,insuredSn);
			}else{
				resMap = parse.dealData("01", strManageCom, strOrderSn,insuredSn);
			}
			
			String passFlag = resMap.get("passFlag").toString();
			
			if ("pass".equals(passFlag))
			{
				resultMap.put("appStatus", "1");// 标记成功
				resultMap.put("PA_RSLT_MESG", resMap.get("rtnMessage"));
				resultMap.put("policyNo", resMap.get("policyNo"));
				resultMap.put("policyPath", resMap.get("policyPath"));
				
				String orderCode = String.valueOf(resMap.get("orderCode"));
				String orderExt = String.valueOf(resMap.get("orderExt"));
				QueryBuilder qb = new QueryBuilder("SELECT t.`informationSn` FROM sdinformationinsured t WHERE t.`insuredSn` =  ?");
    			qb.add(insuredSn);
    			String informationSn = qb.executeString();
				SDOrderItemOthSchema sdOrderItemOth = UsersUWCheck.getSDOrderItemOth(strOrderSn, informationSn,insuredSn);
				sdOrderItemOth.settpySn(orderCode);
				sdOrderItemOth.settpySysPaySn(orderExt);
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
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
