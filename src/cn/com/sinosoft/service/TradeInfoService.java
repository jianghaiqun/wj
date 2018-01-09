package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.TradeInfo;

public interface TradeInfoService extends BaseService<TradeInfo, String> {

	/**
	 * 根据商户订单号查出订单号
	 * 
	 * @param paySn
	 *            商户订单号
	 * @return 订单号 多个订单号以","隔开
	 */
	public String getOrderSnByPaySn(String paySn);

	/**
	 * 根据商户订单号查出交易信息缓存表
	 * 
	 * @param paySn
	 *            商户订单号
	 * @return 交易信息缓存信息
	 */
	public TradeInfo getTradeInfoByPaySn(String paySn);

	/**
	 * 根据订单号和交易金额查出交易信息缓存信息
	 * 
	 * @param orderSn
	 *            订单号
	 * @param totalAmnout
	 *            交易金额
	 * @return 交易信息缓存信息
	 */
	public TradeInfo getTradeInfoByOrder(String orderSn, String totalAmnout);
	
	/**
	 * 根据订单号和交易金额查出交易信息缓存信息
	 * 
	 * @param orderSn.
	 *            订单号
	 * @param totalAmnout.
	 *            交易金额
	 * @param payType.
	 *            交易金额
	 * @return 交易信息缓存信息
	 */
	public TradeInfo getTradeInfoByOrder(String orderSn, String totalAmnout,String payType);
}
