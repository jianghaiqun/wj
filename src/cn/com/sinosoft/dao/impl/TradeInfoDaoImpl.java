package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import com.sinosoft.framework.data.QueryBuilder;

import cn.com.sinosoft.dao.TradeInfoDao;
import cn.com.sinosoft.entity.TradeInfo;

@Repository
public class TradeInfoDaoImpl extends BaseDaoImpl<TradeInfo, String> implements
		TradeInfoDao {

	/**
	 * 根据商户订单号查出订单号
	 * 
	 * @param paySn
	 *            商户订单号
	 * @return 订单号 多个订单号以","隔开
	 */
	@Override
	public String getOrderSnByPaySn(String paySn) {
		String orderSn = new QueryBuilder(
				"select a.orderSn from TradeInfo a where a.paySn=?", paySn)
				.executeString();
		return orderSn;
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
		String hql = "from TradeInfo as tradeInfo where tradeInfo.paySn=?";
		return (TradeInfo) getSession().createQuery(hql).setParameter(0, paySn).uniqueResult();
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
		String hql = "from TradeInfo as tradeInfo where tradeInfo.orderSn='"+orderSn + "' and tradeInfo.totalAmnout='"+ totalAmnout +"'";
		return (TradeInfo) getSession().createQuery(hql).uniqueResult();
	}
	
	/**
	 * 根据订单号和交易金额查出交易信息缓存信息
	 * 
	 * @param orderSn
	 *            订单号.
	 * @param totalAmnout
	 *            交易金额.
	 * @param payType
	 * 			  支付类型.
	 * @return 交易信息缓存信息
	 */
	@Override
	public TradeInfo getTradeInfoByOrder(String orderSn, String totalAmnout,String payType) {
		String hql = "from TradeInfo as tradeInfo where tradeInfo.orderSn='"+orderSn + "' and tradeInfo.totalAmnout='"+ totalAmnout +"' and tradeInfo.payType='"+payType+"'";
		return (TradeInfo) getSession().createQuery(hql).uniqueResult();
	}
}
