package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.SDHealthyInfoDao;
import cn.com.sinosoft.entity.SDHealthyInfo;
import cn.com.sinosoft.service.SDHealthyInfoService;

@Service
public class SDHealthyInfoServiceImpl extends BaseServiceImpl<SDHealthyInfo,String> implements SDHealthyInfoService{
	@Resource
	private SDHealthyInfoDao sdhealthyInfoDao;

	@Resource
	public void setHealthyInfoDao(SDHealthyInfoDao sdhealthyInfoDao) {
		super.setBaseDao(sdhealthyInfoDao);
	}

	@Override
	public List<SDHealthyInfo> findByComAndProduct(String productId,
			String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("productId","=",productId));
		qbs.add(createQB("insuranceCompany","=",comCode));
		return sdhealthyInfoDao.findByQBs(qbs, "showOrder", "asc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
