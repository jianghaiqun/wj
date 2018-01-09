package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Stock;

public interface StockService extends BaseService<Stock, String> {
	/**
	 * 获取库存分类树集合;
	 * 
	 * @return 库存分类树集合
	 * 
	 */
	public List<Stock> getStockList();
	/**
	 * 根据库存分类名称获取库存记录
	 * @author zhangjinquan
	 * @since 2012-11-01
	 * @param String stockName 礼品库存名称
	 */
	public Stock getStockByName(String stockName);
}
