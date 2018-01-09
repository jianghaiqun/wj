package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformationProperty;

public interface SDInformationPropertyService extends BaseService<SDInformationProperty,String>{

	public List<InsuredShow> getcreateShowOneProperty(SDInformationProperty sdp,String comCode,String productId);
	
	public SDInformationProperty getByInsuredId(String insuredId);
}
