package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.ProductPeriodDao;
import cn.com.sinosoft.entity.ProductPeriod;
import cn.com.sinosoft.service.ProductPeriodService;


@Service
public class ProductPeriodServiceImpl extends BaseServiceImpl<ProductPeriod,String> implements ProductPeriodService{
	@Resource
	private ProductPeriodDao productPeriodDao;
	@Resource
	public void setProductPeriodDao(ProductPeriodDao productPeriodDao) {
		super.setBaseDao(productPeriodDao);
	}
	
	@Override
	public String getStartPeriod(String comCode, String riskCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("comCode","=",comCode));
		qbs.add(createQB("riskCode","=",riskCode));
		List<ProductPeriod> list = productPeriodDao.findByQBs(qbs, "id", "desc");
		if(list!=null && list.size()>0){
			return list.get(0).getStartPeriod();
		}else{
			return null;
		}
	}

	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	@Override
	public String getEndPeriod(String comCode, String riskCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("comCode","=",comCode));
		qbs.add(createQB("riskCode","=",riskCode));
		List<ProductPeriod> list = productPeriodDao.findByQBs(qbs, "id", "desc");
		if(list!=null && list.size()>0){
			return list.get(0).getEndPeriod();
		}else{
			return null;
		}
	}
}
