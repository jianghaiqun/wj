package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.TradeInformationDao;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.TradeInformationService;

@Service
public class TradeInformationServiceImpl extends BaseServiceImpl<TradeInformation, String> implements TradeInformationService {
	@Resource
	TradeInformationDao tradeInformationDao;

	@Resource
	public void setTradeInformationDao(TradeInformationDao tradeInformationDao) {
		super.setBaseDao(tradeInformationDao);
	}

	@Override
	public TradeInformation getTradeInformationByOrdId(String ordId) {
		// TODO Auto-generated method stub
		return tradeInformationDao.getTradeInformationByOrdId(ordId);

	}

	@Override
	public TradeInformation getTradeInformationByOrdSn(String ordSn) {
		// TODO Auto-generated method stub
		return tradeInformationDao.getTradeInformationByOrdSn(ordSn);
	}

	public TradeInformation isPayFinnish(String ordSn) {
		return tradeInformationDao.isPayFinnish(ordSn);
	}
}
