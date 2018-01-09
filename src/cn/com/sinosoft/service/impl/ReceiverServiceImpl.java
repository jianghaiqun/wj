package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ReceiverDao;
import cn.com.sinosoft.entity.Receiver;
import cn.com.sinosoft.service.ReceiverService;

/**
 * Service实现类 - 收货地址
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT0FBC86A60DF8465A166FAB21F537E740
 * ============================================================================
 */

@Service
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, String> implements ReceiverService {

	@Resource
	public void setBaseDao(ReceiverDao receiverDao) {
		super.setBaseDao(receiverDao);
	}

}