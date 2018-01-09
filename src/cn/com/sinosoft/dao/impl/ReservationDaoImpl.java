package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.ReservationDao;
import cn.com.sinosoft.entity.Reservation;

@Repository
public class ReservationDaoImpl extends BaseDaoImpl<Reservation,String> implements ReservationDao{

}
