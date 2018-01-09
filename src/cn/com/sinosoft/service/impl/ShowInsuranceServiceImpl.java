package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.ShowInsuranceDao;
import cn.com.sinosoft.entity.ShowInsurance;
import cn.com.sinosoft.service.ShowInsuranceService;

/**
 * @author LiuXin
 *
 */
@Service
public class ShowInsuranceServiceImpl extends BaseServiceImpl<ShowInsurance,String> implements ShowInsuranceService{
	@Resource
	private ShowInsuranceDao siDao;

	@Resource
	public void setSiDao(ShowInsuranceDao siDao) {
		super.setBaseDao(siDao);
	}

	/**
	 * 得到各个套餐最低保费
	 */
	@Override
	public List<ShowInsurance> getMinPremium(String sql) {
		return siDao.getMinPremium(sql);
	}

	@Override
	public List<ShowInsurance> findByQBs(String sericalNo) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("serialNumber","=",sericalNo));
		return siDao.findByQBs(qbs, "id", "asc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
}
