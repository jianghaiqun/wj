package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.ProductPeriod;

public interface ProductPeriodService extends BaseService<ProductPeriod,String> {
	public String getStartPeriod(String comCode,String riskCode);
	public String getEndPeriod(String comCode, String riskCode);
}
