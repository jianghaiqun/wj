package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.SDInsuredHealth;

public interface SDInsuredHealthService extends BaseService<SDInsuredHealth,String> {

	public List<SDInsuredHealth> createShowInformation(List<HealthyInfo> healthList);
	public List<SDInsuredHealth> getInfoByOrderHealthySn(String order_healthySn);

}
