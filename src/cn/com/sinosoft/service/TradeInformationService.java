package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.TradeInformation;

public interface TradeInformationService extends BaseService<TradeInformation, String> {

	public TradeInformation getTradeInformationByOrdId(String ordId);

	public TradeInformation getTradeInformationByOrdSn(String ordSn);

	public TradeInformation isPayFinnish(String ordSn);
}
