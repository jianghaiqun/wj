/**
 * Project Name:wj-code
 * File Name:RenewalInsuranceService.java
 * Package Name:cn.com.sinosoft.service
 * Date:2017年11月2日上午9:42:55
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service;

import java.util.Map;

import com.sinosoft.schema.ZhenAiRenewalSchema;

/**
 * ClassName:RenewalInsuranceService <br/>
 * Function:TODO 续保. <br/>
 * Date:2017年11月2日 上午9:42:55 <br/>
 *
 * @author:guozc
 */
public interface RenewalInsuranceService {

	/**
	 * initInsure:初始化投保信息录入页面. <br/>
	 * 
	 * @author guozc
	 * @param initInsureData
	 *            初始化数据
	 * @param productInfo
	 *            产品信息
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> initInsure(Map<String, Object> initInsureData, Map<String, Object> productInfo)
			throws Exception;

	ZhenAiRenewalSchema jsonInitZhenAiRenewal(Map<String, Object> jingDaiTongReturnMap, String policyNo, String errorMassage);
	
}
