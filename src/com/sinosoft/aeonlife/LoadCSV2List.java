/**
 * Project Name:wj
 * File Name:LoadCsv.java
 * Package Name:com.sinosoft.aeonlife.utils
 * Date:2016年6月13日上午10:08:02
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife;

import com.sinosoft.aeonlife.utils.ReadCSVUtils;
import com.sinosoft.aeonlife.utils.SftpCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * ClassName:LoadCsv <br/>
 * Function:TODO 读取csv文件中的数据. <br/>
 * Date:2016年6月13日 上午10:08:02 <br/>
 *
 * @author:chouweigao
 */
public class LoadCSV2List {
	private static final Logger logger = LoggerFactory.getLogger(LoadCSV2List.class);
	/**
	 * 
	 * policyReq_Res:(投保申请和结果都有的保单才做保存处理). <br/>
	 *
	 * @author chouweigao
	 * @param reqpath
	 * @param respath
	 * @param channels
	 */
	public static void policyReq_Res(String reqpath, String respath, String channels) {

		// 获得保单请求文件
		Map<String, List<String>> reqmap = ReadCSVUtils.loadCSV(reqpath);
		// 获得保单结果文件
		Map<String, List<String>> resmap = ReadCSVUtils.loadCSV(respath);

		// 申请和结果文件比对
		if (reqmap != null && resmap != null) {
			List<String> listereq = reqmap.get(SftpCommon.STR_POLICYREQ);
			List<String> listeres = resmap.get(SftpCommon.STR_POLICYRESULT);
			Map<String, Object> maps = CompareCSV.getResultPolicyReq(listereq, listeres, channels);
			if (maps != null) {
				// 保存订单
				AeonlifeService ass = new AeonlifeService();
				ass.executeSave(maps);
			}
		}
	}
	
	/**
	 * 
	 * refundRes:(申请退保和资产退保). <br/>
	 *
	 * @author chouweigao
	 * @param path
	 * @param flag
	 * @param channels
	 */
	public static void refundRes(String path ,String flag,String channels){
		Map<String, List<String>> map = ReadCSVUtils.loadCSVFromZip(path);
		if (map != null) {
			List<String> listValue = map.get(flag);
			
			// LogUtil.error("flag:" + flag);
			// LogUtil.error("listRefunds:" + JSON.toJSONString(listValue));
			// 没有数据判断
			if (null == listValue) {
				logger.warn("申请退保和资产退保没有数据！");
				return;
			}
			// 申请退保的文件
			Map<String, Object> maps = CompareCSV.getRefund(listValue, flag, channels);
			if (maps != null) {
				AeonlifeService ass = new AeonlifeService();
				ass.executeUpdate(maps);
			}
			// 资产同步文件
			if (flag.equals(SftpCommon.STR_POLICYVALUE)) {
				maps = CompareCSV.getPolicyValue(listValue,  channels);
				if (maps != null) {
					AeonlifeService ass = new AeonlifeService();
					ass.executePolicyValue(maps);
				}
			}
		}
	}
	
	/**
	 * 
	 * refundRes:(百年爆品退保文件处理). <br/>
	 *
	 * @author yuzaijiang
	 * @param path 文件全路径
	 * @param flag 
	 * @param channels
	 */
	public static void refundResForBaoPin(String path ,String flag,String channels){
		List<String> dataCol = ReadCSVUtils.loadTxt(path);
		if (dataCol != null && dataCol.size()>=3) {
			// 申请退保的文件
			AeonlifeService ass = new AeonlifeService();
			ass.executeSurrend4BaoPin(dataCol);
		}
	}
	
}
