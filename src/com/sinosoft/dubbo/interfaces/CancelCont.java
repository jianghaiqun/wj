package com.sinosoft.dubbo.interfaces;
/**
 * 
 * @author yuzaijiang
 * 撤单接口
 */
public interface CancelCont {
	public String callInsTransInterface(String comCode,String ordersn, String insuredSn,String riskTypeId);
	public boolean callProductInterface(String ordersn, String fee, String date, String flag,String policyNo,String riskTypeId,String returnIntegral,String returnActivity);
	public boolean changeSDOrders(String ordersn);
}
