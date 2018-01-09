package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.MyTrailNonAutoDao;
import cn.com.sinosoft.dao.TrailProductDao;
import cn.com.sinosoft.entity.MyTrailNonAuto;
import cn.com.sinosoft.entity.TrailProduct;
import cn.com.sinosoft.service.MyTrailNonAutoService;
/**
 * @author LiuXin
 *
 */
@Service
public class MyTrailNonAutoServiceImpl extends BaseServiceImpl<MyTrailNonAuto,String> implements MyTrailNonAutoService{
	@Resource
	private MyTrailNonAutoDao myTrailAutoDao;
	@Resource
	private TrailProductDao trailProductDao;

	@Resource
	public void setMyTrailAutoDao(MyTrailNonAutoDao myTrailAutoDao) {
		super.setBaseDao(myTrailAutoDao);
	}

	@Override
	public List<MyTrailNonAuto> getMyNonAutoTailByMemberId(String memberId) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("memeberId","=",memberId));
		return myTrailAutoDao.findByQBs(qbs, "id", "desc");
	}
	
	@Override
	public List<TrailProduct> getTrailProductBySerialNo(String serialNumber) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("serialNumber","=",serialNumber));
		return trailProductDao.findByQBs(qbs, "id", "desc");
	}

	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
