package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InsuredCompanyReturnDataDao;
import cn.com.sinosoft.entity.InsuredCompanyReturnData;
@Repository
public class InsuredCompanyReturnDataDaoImpl extends
		BaseDaoImpl<InsuredCompanyReturnData, String> implements
		InsuredCompanyReturnDataDao {

	@Override
	public InsuredCompanyReturnData getInsuredCompanyReturnDataByOrderSn(
			String OrderSn) {
		String hql = "  from InsuredCompanyReturnData  where  orderSn = ? ";
		InsuredCompanyReturnData tInsuredCompanyReturnData = (InsuredCompanyReturnData) getSession()
				.createQuery(hql).setParameter(0, OrderSn).uniqueResult();

		return tInsuredCompanyReturnData;
	}

}
