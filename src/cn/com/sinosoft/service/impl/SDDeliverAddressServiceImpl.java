package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDDeliverAddressDao;
import cn.com.sinosoft.entity.SDDeliverAddress;
import cn.com.sinosoft.service.SDDeliverAddressService;

/**
 * Service实现类 - 邮寄地址
 *  
 */

@Service
public class SDDeliverAddressServiceImpl extends BaseServiceImpl<SDDeliverAddress, String> implements SDDeliverAddressService {
	@Resource
	private SDDeliverAddressDao mSDDeliverAddressDao;
	
	@Resource
	public void setBaseDao(SDDeliverAddressDao mSDDeliverAddressDaoDao) { 
		super.setBaseDao(mSDDeliverAddressDaoDao);
	}

	@Override
	public List<SDDeliverAddress> getSDDeliverAddressInfo(String memberId) {
		return mSDDeliverAddressDao.getSDDeliverAddressInfo(memberId);
	}
//
//	@Override
//	public SDDeliverAddress getSDDeliverAddressById(String addrId) {
//		return mSDDeliverAddressDao.getSDDeliverAddressById(addrId);
//	}
	
	
}