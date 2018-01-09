package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.RefundBaseDao;
import cn.com.sinosoft.dao.TradeInformationDao;
import cn.com.sinosoft.entity.RefundBase;
import cn.com.sinosoft.service.RefundBaseService;
@Service
public class RefundBaseServiceImpl extends BaseServiceImpl<RefundBase, String>
		implements RefundBaseService {

	@Resource
	RefundBaseDao refundBaseDao;
	
	@Resource
	public void setBaseDao(RefundBaseDao refundBaseDao) {
		super.setBaseDao(refundBaseDao);
	}

	@Override
	public RefundBase getRefundBaseByReturnType(String returnType) {
		// TODO Auto-generated method stub
		return  refundBaseDao.getRefundBaseByReturnType(returnType);
	}

	
}
