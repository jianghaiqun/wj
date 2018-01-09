package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.CityDao;
import cn.com.sinosoft.dao.ReservationDao;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Reservation;
import cn.com.sinosoft.service.ReservationService;

@Service
public class ReservationServiceImpl extends BaseServiceImpl<Reservation, String> implements
		ReservationService {
	@Resource
	private ReservationDao reservationDao;
	@Resource
	private CityDao cityDao;

	@Resource
	public void setYuyueDao(ReservationDao reservationDao) {
		super.setBaseDao(reservationDao);
	}

	@Override
	public Pager findReservationsByPager(Pager p) {
		p.setOrderBy("createDate");
		p.setOrderType(OrderType.desc);
		p.setPageSize(15);
		return reservationDao.findByPagerQbs(p);
	}

	@Override
	public List<City> getSuperCitys() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("placeType","=","01"));
		return cityDao.findByQBs(qbs, "placeCode", "desc");
	}
	
	@Override
	public List<City> getChilderCitys(String areaId) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("placeType","=","02"));
		qbs.add(createQB("upplaceCode","=",areaId));
		return cityDao.findByQBs(qbs, "placeCode", "desc");
	}

	@Override
	public String getAreaNameByareaId(String areaId) {
		City c = cityDao.get(areaId);
		if(c!=null){
			return c.getPlaceName();
		}
		return "";
	}

	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
