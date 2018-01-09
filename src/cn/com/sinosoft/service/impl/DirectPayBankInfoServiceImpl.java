/**
 * 
 */
package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.DirectPayBankInfoDao;
import cn.com.sinosoft.entity.DirectPayBankInfo;
import cn.com.sinosoft.service.DirectPayBankInfoService;

/**
 * @author gaohaijun
 */
@Service
public class DirectPayBankInfoServiceImpl extends BaseServiceImpl<DirectPayBankInfo, String> implements DirectPayBankInfoService {

	@Resource
	private DirectPayBankInfoDao dao; 
	
	@Resource
	public void setBaseDao(DirectPayBankInfoDao dao) {
		super.setBaseDao(dao);
	}

	@Override
	public DirectPayBankInfo getByOrderSn(String orderSn) {
		return dao.getByOrderSn(orderSn);
	}
}
