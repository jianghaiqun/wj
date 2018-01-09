package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.SDInformationProperty;

public interface SDInformationPropertyDao extends BaseDao<SDInformationProperty,String>{
	
	public SDInformationProperty getByInsuredId(String insuredId);
	
}
