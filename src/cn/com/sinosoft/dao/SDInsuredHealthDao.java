package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDInsuredHealth;

public interface SDInsuredHealthDao extends BaseDao<SDInsuredHealth,String>{
	public List<SDInsuredHealth> getInfoByOrderHealthySn(String order_healthySn);

}
