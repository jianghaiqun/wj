package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.com.sinosoft.dao.InsuredCompanyReturnDataDao;
import cn.com.sinosoft.entity.InsuredCompanyReturnData;
import cn.com.sinosoft.service.InsuredCompanyReturnDataService;
@Service
public class InsuredCompanyReturnDataServiceImpl extends BaseServiceImpl<InsuredCompanyReturnData, String> implements InsuredCompanyReturnDataService {

	@Resource
	private InsuredCompanyReturnDataDao insuredCompanyReturnDataDao;

	@Resource
	public void setBaseDao(InsuredCompanyReturnDataDao insuredCompanyReturnDataDao) {
		super.setBaseDao(insuredCompanyReturnDataDao);
	}
	
	@Override
	public InsuredCompanyReturnData getInsuredCompanyReturnDataByOrderSn(
			String OrderSn) {
		
		return insuredCompanyReturnDataDao.getInsuredCompanyReturnDataByOrderSn(OrderSn);
	}

}
