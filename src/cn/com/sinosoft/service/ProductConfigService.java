package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.ProductConfig;

public interface ProductConfigService extends BaseService<ProductConfig,String> {

	public List<ProductConfig> findPCByRiskCode(String riskCode,String comCode);

}
