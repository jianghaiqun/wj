package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.ProductConfigDao;
import cn.com.sinosoft.entity.ProductConfig;
import cn.com.sinosoft.service.ProductConfigService;

@Service
public class ProductConfigServiceImpl extends BaseServiceImpl<ProductConfig,String> implements ProductConfigService{
	@Resource
	private ProductConfigDao productConfigDao;

	@Resource
	public void setProductConfigDao(ProductConfigDao productConfigDao) {
		super.setBaseDao(productConfigDao);
	}

	@Override
	public List<ProductConfig> findPCByRiskCode(String riskCode,String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("riskCode","=",riskCode));
		qbs.add(createQB("comCode","=",comCode));
		return productConfigDao.findByQBs(qbs, "id", "desc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
