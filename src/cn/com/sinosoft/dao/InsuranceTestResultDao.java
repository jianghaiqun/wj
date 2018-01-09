package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.InsuranceTestResult;

public interface InsuranceTestResultDao extends BaseDao<InsuranceTestResult, String> {
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO(查询保险测试列表页) 
	* @return List<InsuranceTestResult>    返回类型 
	* @author zhangjing
	 */
	public  List<InsuranceTestResult> searchInsuranceTestList(String userid);
	
}
