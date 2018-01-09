package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InsuranceDao;
import cn.com.sinosoft.entity.Insurance;
import cn.com.sinosoft.service.InsuranceService;

/**
 * @author LiuXin
 *
 */
@Service
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance,String> implements InsuranceService{
	@SuppressWarnings("unused")
	@Resource
	private InsuranceDao insuranceDao;

	@Resource
	public void setInsuranceDao(InsuranceDao insuranceDao) {
		super.setBaseDao(insuranceDao);
	}

}
