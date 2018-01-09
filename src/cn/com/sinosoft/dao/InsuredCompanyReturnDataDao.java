package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.InsuredCompanyReturnData;

public interface InsuredCompanyReturnDataDao extends BaseDao<InsuredCompanyReturnData, String> {

	public  InsuredCompanyReturnData getInsuredCompanyReturnDataByOrderSn(String OrderSn);
	
	
}
