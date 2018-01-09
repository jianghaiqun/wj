/**
 * Project Name:wj-code
 * File Name:SalesVolumeServiceImpl.java
 * Package Name:cn.com.sinosoft.service.impl
 * Date:2017年5月11日上午11:20:01
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service.impl;

import org.springframework.stereotype.Service;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import cn.com.sinosoft.common.annotation.Cache;
import cn.com.sinosoft.service.SalesVolumeService;
import cn.com.sinosoft.util.RedisConsts;

/**
 * ClassName:SalesVolumeServiceImpl <br/>
 * Function:TODO 销售统计. <br/>
 * Date:2017年5月11日 上午11:20:01 <br/>
 *
 * @author:guozc
 */
@Service
public class SalesVolumeServiceImpl implements SalesVolumeService {
	
	@Cache(keyPrefix = RedisConsts.KEY_PREFIX_PRODUCTATTR,dataType =2 
			,mapKey = RedisConsts.MAPKEY_PRODUCTATTR_SALESVLUME)
	@Override
	public Integer getSalesVolume(String productId) {
		QueryBuilder qbsales = new QueryBuilder(
				"select a.SalesVolume,b.SplitRiskCode from sdsearchrelaproduct a,sdproduct b where a.productid=b.productid and a.ProductID = ?",
				productId);
		DataTable dtsales = qbsales.executeDataTable();
		int tSalesVolume = 0;
		if (dtsales != null && dtsales.getRowCount() >= 1) {
			String tSplitRiskCode = dtsales.getString(0, "SplitRiskCode");
			if (StringUtil.isNotEmpty(tSplitRiskCode)) {
				if (tSplitRiskCode.indexOf(",") != -1) {
					String[] src_plan = tSplitRiskCode.split(",");
					for (int j = 0; j < src_plan.length; j++) {
						if (src_plan[j].indexOf("-") != -1) {
							String[] src = src_plan[j].split("-");
							String cProductId = src[0];
							QueryBuilder qbchisales = new QueryBuilder(
									"select a.SalesVolume from sdsearchrelaproduct a where a.ProductID = ?", cProductId);
							DataTable dtchisales = qbchisales.executeDataTable();
							if (dtchisales != null && dtchisales.getRowCount() >= 1) {
								int cSalesVolume = dtchisales.getInt(0, "SalesVolume");
								tSalesVolume = tSalesVolume + cSalesVolume;
							}
						}
					}
				}
			} else {
				tSalesVolume = dtsales.getInt(0, "SalesVolume");
			}
		}
		return tSalesVolume;
	}

}

