package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Reservation;

public interface ReservationService extends BaseService<Reservation,String>{
	public Pager findReservationsByPager(Pager p);
	public List<City> getSuperCitys();
	public List<City> getChilderCitys(String areaId);
	public String getAreaNameByareaId(String areaId);

}
