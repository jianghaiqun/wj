/**
 * @CopyRight:Sinosoft
 * @Project:ZKING-PORTAL
 * @File:CityServiceImpl.java
 * @CreateTime:2012-6-13 上午8:33:48
 * @Package:cn.com.sinosoft.zking.service.impl
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
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.service.CityService;

/**
 * @author LiuXin
 * 
 */
@Service
public class CityServiceImpl extends BaseServiceImpl<City, String> implements
		CityService {
	@Resource
	private CityDao cityDao;

	@Resource
	public void setCityDao(CityDao cityDao) {
		super.setBaseDao(cityDao);
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
