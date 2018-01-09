package cn.com.sinosoft.service;

public interface CpsProductBuyService {

	/**
	 * 保存订单
	 * @param orderSn 订单号
	 * @return
	 */
	public boolean saveOrder(String orderSn);
}
