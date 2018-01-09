package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.InsuranceTestResult;
/**
 * 
* @ClassName: InsuranceTestResultService 
* @Description: TODO(保险测试结果) 
* @author zhangjing 
* @date 2014-3-3 
*
 */
public interface InsuranceTestResultService extends BaseService<InsuranceTestResult, String> {
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO(查询测试结果) 
	* @return List<InsuranceTestResult>    返回类型 
	* @author zhangjing
	 */
	public List<List<Map<String,String>>> searchInsuranceTestResultList(String userid) ;
}
