package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InsuranceTestResultDao;
import cn.com.sinosoft.entity.InsuranceTestResult;

@Repository
public class InsuranceTestResultDaoImpl extends BaseDaoImpl<InsuranceTestResult, String> implements InsuranceTestResultDao {
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO(查询保险测试列表页) 
	* @return List<InsuranceTestResult>    返回类型 
	* @author zhangjing
	 */
	@SuppressWarnings("unchecked")
	public List<InsuranceTestResult> searchInsuranceTestList(String userid) {
		String hql="from InsuranceTestResult where userid=? order by id ";
		List<InsuranceTestResult> list = getSession().createQuery(hql).setParameter(0,userid).list();
		return list;
	};
}
