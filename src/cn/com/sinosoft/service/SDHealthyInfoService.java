package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDHealthyInfo;

public interface SDHealthyInfoService extends BaseService<SDHealthyInfo,String>{

	public List<SDHealthyInfo> findByComAndProduct(String productId, String comCode);

}
