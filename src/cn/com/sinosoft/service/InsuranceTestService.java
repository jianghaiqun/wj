package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.InsuranceTest;
/**
 * 保险测试
 * @author zhangjing
 *
 */
public interface InsuranceTestService extends BaseService<InsuranceTest, String> {
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO(查询需求测试用户信息列表) 
	* @return List<Map<String,String>>    返回类型 
	* @author zhangjing
	 */
	public List<Map<String,String>> searchInsuranceTestList(String pageIndex);
	/**
	 * 
	* @Title: searchInsuranceTest 
	* @Description: TODO(查询需求测试用户信息) 
	* @return List<Map<String,String>>    返回类型 
	* @author zhangjing
	 */
	public List<Map<String,String>> searchInsuranceTest(String userid) ;
	/**
	 * 
	* @Title: searchInsuranceTestListBy 
	* @Description: TODO(分页条件查询) 
	* @return List<Map<String,String>>    返回类型 
	* @author zhangjing
	 */
	public Map<String,String> searchInsuranceTestListBy(String pageIndex,String param)  ;
}
