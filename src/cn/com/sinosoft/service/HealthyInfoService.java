package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.HealthyInfo;

public interface HealthyInfoService extends BaseService<HealthyInfo,String>{

	public List<HealthyInfo> findByComAndProduct(String productId, String comCode);

}
