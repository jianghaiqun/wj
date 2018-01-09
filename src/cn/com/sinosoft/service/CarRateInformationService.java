package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;

/**
 * @author LiuXin
 *
 */
public interface CarRateInformationService extends BaseService<CarRateInformation,String>{
	public List<City> getRegionCode();
	public List<FDInsCom> getCompanys();
	public List<CarRateInformation> findByQBs(List<QueryBuilder> qbs, String orderBy,String orderType);

}
