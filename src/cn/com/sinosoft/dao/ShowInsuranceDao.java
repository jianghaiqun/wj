package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.ShowInsurance;

/**
 * @author LiuXin
 *
 */
public interface ShowInsuranceDao extends BaseDao<ShowInsurance,String>{
	public List<ShowInsurance> getMinPremium(String sql);
}
