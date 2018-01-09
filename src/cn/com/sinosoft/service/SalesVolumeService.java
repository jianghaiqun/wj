/**
 * Project Name:wj-code
 * File Name:SalesVolumeService.java
 * Package Name:cn.com.sinosoft.service
 * Date:2017年5月11日上午11:17:23
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service;

/**
 * ClassName:SalesVolumeService <br/>
 * Function:TODO 销售统计. <br/>
 * Date:2017年5月11日 上午11:17:23 <br/>
 *
 * @author:guozc
 */
public interface SalesVolumeService {

	/**
	 * getSalesVolume:(查询产品销售统计). <br/>
	 * @author guozc
	 * @param productId 产品id
	 * @return
	 */
	Integer getSalesVolume(String productId);
	
}

