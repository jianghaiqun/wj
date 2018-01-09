/**
 * 承保返回处理接口，目前只有一个dealData接口方法，只是为了实现映射调用
 * @author zhangjinquan 11180
 * @creaeDate 2012-11-14
 */
package com.sinosoft.jdt.tb;

import java.util.HashMap;

public interface TBDealInterfaceNew
{
	/**
	 * 处理承保
	 * @param resultMap
	 * @param strManageCom
	 * @param strOrderSn
	 * @return
	 */
	public boolean dealData(HashMap <String, Object> resultMap, String strManageCom, String strOrderSn ,String insuredSn);
	
	
	
	/**
	 * 
	 * @param resultMap
	 * @param strManageCom
	 * @param strOrderSn
	 * @return
	 */
	public boolean dealCancelData(HashMap <String, Object> resultMap, String strManageCom,String ordersn, String insuredSn);
}
