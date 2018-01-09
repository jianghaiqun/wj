package com.sinosoft.jdt.tb;

import java.util.HashMap;

import com.sinosoft.platform.pub.NoUtil;

/**
 * 处理人保财险投保，不发送报文，直接返回成功标识和用于结算的临时保单号
 * @author GaoHaijun
 *
 */
public class TBDeal2005 implements TBDealInterfaceNew {

	@Override
	public boolean dealData(HashMap<String, Object> resultMap, String strManageCom, String strOrderSn,String insuredSn) {
		resultMap.put("BK_SERIAL", insuredSn);
		resultMap.put("PA_RSLT_CODE", "000000");
		resultMap.put("PA_RSLT_MESG", "交易成功");
		resultMap.put("appStatus", "1");// 标记成功
		resultMap.put("policyNo", "RBCX-WJ" + NoUtil.getMaxNo("RBCX-WJ-", 8));
		return true;
	}

	@Override
	public boolean dealCancelData(HashMap<String, Object> resultMap, String strManageCom, String ordersn, String insuredSn) {
		return false;
	}
	
}
