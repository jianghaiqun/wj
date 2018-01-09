package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.TradeInformation;

public interface TradeInformationDao extends BaseDao<TradeInformation, String> {
	public TradeInformation getTradeInformationByOrdId(String ordId);

	public TradeInformation getTradeInformationByOrdSn(String ordSn);

	public TradeInformation isPayFinnish(String ordSn);

}
