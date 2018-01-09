package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.InsuredCompanyReturnData;

public interface InsuredCompanyReturnDataService extends
		BaseService<InsuredCompanyReturnData, String> {

	public InsuredCompanyReturnData getInsuredCompanyReturnDataByOrderSn(
			String OrderSn);
	
	
}
