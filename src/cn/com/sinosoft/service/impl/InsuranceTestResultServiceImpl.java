package cn.com.sinosoft.service.impl;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InsuranceTestResultDao;
import cn.com.sinosoft.entity.InsuranceTestResult;
import cn.com.sinosoft.service.InsuranceTestResultService;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

@Service
public class InsuranceTestResultServiceImpl extends BaseServiceImpl<InsuranceTestResult, String> implements InsuranceTestResultService {
	@Resource
	private InsuranceTestResultDao insurancetestresultdao;
	
	
	@Resource
	public void setInsurancetestdao(InsuranceTestResultDao insurancetestresultdao) {
		super.setBaseDao(insurancetestresultdao);
	}
	/**
	 * 
	* @Title: searchInsuranceTestList 
	* @Description: TODO(查询保险测试列表页) 
	* @return List<InsuranceTestResult>    返回类型 
	* @author zhangjing
	 */
	public List<List<Map<String,String>>> searchInsuranceTestResultList(String userid) {
		List<List<Map<String,String>>> list_list=new ArrayList<List<Map<String,String>>>();
		// 查询获得保险需求结果列表
		List<InsuranceTestResult> list = insurancetestresultdao.searchInsuranceTestList(userid);
		for (int i = 0; i < list.size(); i++) {
			List<Map<String,String>> list_map=new ArrayList<Map<String,String>>();
			InsuranceTestResult insurancetestresult=list.get(i);
			QueryBuilder qb=new QueryBuilder("SELECT  productid,productname,initprem,url,logolink FROM sdsearchrelaproduct WHERE productid in (?,?)",insurancetestresult.getProduct_id1(),insurancetestresult.getProduct_id2());
			DataTable dt=qb.executeDataTable();
			for (int j = 0; j < dt.getRowCount(); j++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("productid",dt.getString(j,0));
				map.put("productname",dt.getString(j,1));
				map.put("initprem",dt.getString(j,2));
				map.put("url",dt.getString(j,3));
				map.put("logolink",dt.getString(j,4));
				map.put("level",insurancetestresult.getLevel());
				list_map.add(map);
			}
			if(dt.getRowCount()==0){
				for (int j = 0; j < 2; j++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("productid","2007000000001");
					map.put("productname","2007000000001");
					map.put("initprem","2007000000001");
					map.put("url","2007000000001");
					map.put("logolink","2007000000001");
					map.put("level","0");
					list_map.add(map);
				}
			}
			list_list.add(list_map);
		}
		return list_list;
	}
}
