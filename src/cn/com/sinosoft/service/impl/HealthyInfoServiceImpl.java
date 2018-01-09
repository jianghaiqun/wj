package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.HealthyInfoDao;
import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.service.HealthyInfoService;

@Service
public class HealthyInfoServiceImpl extends BaseServiceImpl<HealthyInfo,String> implements HealthyInfoService{
	@Resource
	private HealthyInfoDao healthyInfoDao;

	@Resource
	public void setHealthyInfoDao(HealthyInfoDao healthyInfoDao) {
		super.setBaseDao(healthyInfoDao);
	}

	@Override
	public List<HealthyInfo> findByComAndProduct(String productId,
			String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("productId","=",productId));
		qbs.add(createQB("insuranceCompany","=",comCode));
		return healthyInfoDao.findByQBs(qbs, "showOrder", "asc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
