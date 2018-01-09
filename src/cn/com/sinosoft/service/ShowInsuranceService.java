package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.ShowInsurance;

/**
 * @author LiuXin
 *
 */
public interface ShowInsuranceService extends BaseService<ShowInsurance,String>{
	public List<ShowInsurance> getMinPremium(String sql);
	public List<ShowInsurance> findByQBs(String sericalNo);
}
