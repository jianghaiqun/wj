package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.CityDao;
import cn.com.sinosoft.dao.FDInsComDao;
import cn.com.sinosoft.dao.SDtargetInformationDao;
import cn.com.sinosoft.dao.ShowInsuranceDao;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.entity.ShowInsurance;
import cn.com.sinosoft.service.MyTrialService;

/**
 * @author LiuXin
 *
 */
@Service
public class MyTrialServiceImpl implements MyTrialService{
	@Resource
	private SDtargetInformationDao sdInformationDao;
	@Resource 
	private ShowInsuranceDao showInsuranceDao;
	@Resource
	private CityDao cityDao;
	@Resource
	private FDInsComDao fdInsComDao;
	
	
	@Override
	public List<SDtargetInformation> getsSDInformations(String memberId,SDtargetInformation sdInformation) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("memberId","=",memberId));
		if(sdInformation!=null){
			if(sdInformation.getRegionCode()!=null&&!"".equals(sdInformation.getRegionCode())){
				qbs.add(createQB("regionCode","=",sdInformation.getRegionCode()));
			}
			if(sdInformation.getCarBrand()!=null&&!"".equals(sdInformation.getCarBrand())){
				qbs.add(createQB("carBrand","like",sdInformation.getCarBrand()));
			}
		}
		return sdInformationDao.findByQBs(qbs, "createDate", "desc");
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

	@Override
	public List<ShowInsurance> getShowInsurances(String serialNumber) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("serialNumber","=",serialNumber));
		return showInsuranceDao.findByQBs(qbs, "id", "desc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
