package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.CarRateInformationDao;
import cn.com.sinosoft.dao.CityDao;
import cn.com.sinosoft.dao.FDInsComDao;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.service.CarRateInformationService;

/**
 * @author LiuXin
 *
 */
@Service
public class CarRateInformationServiceImpl extends BaseServiceImpl<CarRateInformation,String> implements CarRateInformationService{
	@Resource
	private CarRateInformationDao carRIFDao;

	@Resource
	public void setCarRIFDao(CarRateInformationDao carRIFDao) {
		super.setBaseDao(carRIFDao);
	}

	@Resource
	private FDInsComDao fdInsComDao;
	@Resource
	private CityDao cityDao;
	

	@Override
	public List<CarRateInformation> findByQBs(List<QueryBuilder> qbs,
			String orderBy, String orderType) {
		return carRIFDao.findByQBs(qbs, orderBy, orderType);
	}

	@Override
	public List<City> getRegionCode() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("placeType","=","01"));
		return cityDao.findByQBs(qbs, "id", "asc");
	}

	@Override
	public List<FDInsCom> getCompanys() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("insClass","=","01"));
		return fdInsComDao.findByQBs(qbs, "supplierCode", "desc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
