package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.InsuranceTest;

public interface InsuranceTestDao extends BaseDao<InsuranceTest, String> {
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO( 查询保险测试结果列表) 
	* @return List<InsuranceTest>    返回类型 
	* @author zhangjing
	 */
	public List<InsuranceTest> searchInsuranceTestList(String page);
	/**
	 * 
	* @Title: searchInsuranceTest 
	* @Description: TODO(查询保险测试结果) 
	* @return List<InsuranceTest>    返回类型 
	* @author zhangjing
	 */
	public InsuranceTest searchInsuranceTest(String userid);
	/**
	 * 
	* @Title: searchInsuranceTestListBy 
	* @Description: TODO(分页条件查询) 
	* @return List<InsuranceTest>    返回类型 
	* @author zhangjing
	 */
	public List<InsuranceTest> searchInsuranceTestListBy(String page,String wheresql);
}
