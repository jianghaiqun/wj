package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.InsuredHealth;

public interface InsuredHealthService extends BaseService<InsuredHealth,String> {

	public List<InsuredHealth> createShowInformation(List<HealthyInfo> healthList);

}
