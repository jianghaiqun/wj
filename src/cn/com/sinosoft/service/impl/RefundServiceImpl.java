package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.RefundDao;
import cn.com.sinosoft.entity.Refund;
import cn.com.sinosoft.service.RefundService;
import cn.com.sinosoft.util.SerialNumberUtil;

/**
 * Service实现类 - 退款
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT33368CD07557B60629179EF8EDB49808
 * ============================================================================
 */

@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund, String> implements RefundService {
	
	@Resource
	private RefundDao refundDao;

	@Resource
	public void setBaseDao(RefundDao refundDao) {
		super.setBaseDao(refundDao);
	}
	
	public String getLastRefundSn() {
		return refundDao.getLastRefundSn();
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		return refundDao.getRefundByRefundSn(refundSn);
	}

	// 重写对象，保存时自动设置退款编号
	@Override
	public String save(Refund refund) {
		refund.setRefundSn(SerialNumberUtil.buildRefundSn());
		return super.save(refund);
	}

}