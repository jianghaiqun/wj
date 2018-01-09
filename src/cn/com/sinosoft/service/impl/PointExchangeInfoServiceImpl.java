/**
 * 
 */
package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.PointExchangeInfoDao;
import cn.com.sinosoft.entity.PointExchangeInfo;
import cn.com.sinosoft.service.PointExchangeInfoService;

/**
 * @author Administrator
 *
 */
@Service
public class PointExchangeInfoServiceImpl extends BaseServiceImpl<PointExchangeInfo, String> implements PointExchangeInfoService {
	
	@Resource
	private PointExchangeInfoDao mPointExchangeInfoDao;
	
	@Resource
	public void setBaseDao(PointExchangeInfoDao tPointExchangeInfoDao) {
		super.setBaseDao(tPointExchangeInfoDao);
	}

	@Override
	public List<PointExchangeInfo> pointexchangelist(String orderSn) {
		
		return mPointExchangeInfoDao.pointexchangelist(orderSn);
		
	}
}
