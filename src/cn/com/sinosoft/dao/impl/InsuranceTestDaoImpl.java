package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InsuranceTestDao;
import cn.com.sinosoft.entity.InsuranceTest;

@Repository
public class InsuranceTestDaoImpl extends BaseDaoImpl<InsuranceTest, String> implements InsuranceTestDao {
	/**
	 * 查询保险测试结果列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsuranceTest> searchInsuranceTestList(String page) {
		String hql = "from InsuranceTest order by  createdate desc ";
		Query query=getSession().createQuery(hql);
		query.setFirstResult((Integer.parseInt(page) - 1)* 5);
		query.setMaxResults(5);  
		List<InsuranceTest> list=query.list();
		return list;
	};
	@SuppressWarnings("unchecked")
	public List<InsuranceTest> searchInsuranceTestListBy(String page,String wheresql) {
		String hql = "from InsuranceTest "+wheresql+" order by  createdate desc ";
		Query query=getSession().createQuery(hql);
		query.setFirstResult((Integer.parseInt(page) - 1)* 5);
		query.setMaxResults(5);  
		List<InsuranceTest> list=query.list();
		String total_hql = " from InsuranceTest  "+wheresql;
		String total=getSession().createQuery(total_hql).list().size()+"";
		InsuranceTest insurancetest=new InsuranceTest();
		insurancetest.setMemo(total);
		list.add(insurancetest);
		return list;
	};
	/**
	 * 查询保险测试结果
	 */
	public InsuranceTest searchInsuranceTest(String userid) {
		String hql = "from InsuranceTest where id=?  ";
		InsuranceTest insurancetest=(InsuranceTest) getSession().createQuery(hql).setParameter(0,userid).uniqueResult();
		return insurancetest;
	};
}
