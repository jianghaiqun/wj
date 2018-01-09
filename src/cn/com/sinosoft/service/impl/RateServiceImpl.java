/**
 * @CopyRight:Sinosoft
 * @File:RateServiceImpl.java
 * @Package:cn.com.sinosoft.service.impl
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.CityDao;
import cn.com.sinosoft.dao.RateDao;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.PremiumRate;
import cn.com.sinosoft.service.RateService;

/**
 * @author LiuXin
 *
 */
@Service
public class RateServiceImpl extends BaseServiceImpl<PremiumRate,Long> implements RateService{
	@Resource
	private CityDao cityDao;
	@Resource
	private RateDao rateDao;
	@Resource
	public void setRateDao(RateDao rateDao) {
		super.setBaseDao(rateDao);
	}
	@Override
	public boolean saveRateBySql(String sql) {
		return rateDao.saveByRateSql(sql);
	}
	@Override
	public boolean deleteRateBySql(String sql) {
		return rateDao.deleteRateTempBySql(sql);
	}
	@Override
	public List<PremiumRate> findByQBs(List<QueryBuilder> qbs, String orderBy,
			String orderType) {
		return rateDao.findByQBs(qbs, orderBy, orderType);
	}
	/**
	 * 
	 * 查找城市信息
	 */
	public List<City> selectCity(){
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("placeType","=","01"));
		return cityDao.findByQBs(qbs, "id", "desc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
