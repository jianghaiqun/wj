package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.TradeInfoDao;
import cn.com.sinosoft.entity.TradeInfo;
import cn.com.sinosoft.service.TradeInfoService;

@Service
public class TradeInfoServiceImpl extends BaseServiceImpl<TradeInfo, String> implements TradeInfoService {
	
	@Resource
	private TradeInfoDao tradeInfoDao;
	
	@Resource
	public void setBaseDao(TradeInfoDao traderInfoDao) {
		super.setBaseDao(traderInfoDao);
	}
	
	/**
	 * 根据商户订单号查出订单号
	 * 
	 * @param paySn
	 *            商户订单号
	 * @return 订单号 多个订单号以","隔开
	 */
	@Override
	public String getOrderSnByPaySn(String paySn) {
		return tradeInfoDao.getOrderSnByPaySn(paySn);
	}
	
	/**
	 * 根据商户订单号查出交易信息缓存表
	 * 
	 * @param paySn
	 *            商户订单号
	 * @return 交易信息缓存信息
	 */
	@Override
	public TradeInfo getTradeInfoByPaySn(String paySn) {
		return tradeInfoDao.getTradeInfoByPaySn(paySn);
	}
	
	/**
	 * 根据订单号和交易金额查出交易信息缓存信息
	 * 
	 * @param orderSn
	 *            订单号
	 * @param totalAmnout
	 *            交易金额
	 * @return 交易信息缓存信息
	 */
	@Override
	public TradeInfo getTradeInfoByOrder(String orderSn, String totalAmnout) {
		return tradeInfoDao.getTradeInfoByOrder(orderSn, totalAmnout);
	}

	/**
	 * 根据订单号和交易金额查出交易信息缓存信息
	 * 
	 * @param orderSn
	 *            订单号.
	 * @param totalAmnout
	 *            交易金额
	 * @param payType.
	 *            支付方式.
	 * @return 交易信息缓存信息
	 */
	@Override
	public TradeInfo getTradeInfoByOrder(String orderSn, String totalAmnout,String payType) {
		return tradeInfoDao.getTradeInfoByOrder(orderSn, totalAmnout,payType);
	}
	
}
