package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FDInsCom;

public interface CompareService<T> extends BaseService<T,String>{
	public Map<String,Object> getNonAutoTrial(String[] riskCode ,CompareInfoServiceStub.FEMRiskCompareProperties[] fcps);
	public CompareInfoServiceStub.FEMRiskCompareProperties[] getCompareInformation(String eriskType);
	public FDInsCom getCompanyInformation(String riskCode);
	public Map<String,Object> createProductDuty(List<Map<String, Object>> compareIF);
}
