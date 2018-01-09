package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.Stock;

/**
 * Dao接口 - 库存量分类
 * ============================================================================
 * KEY:SINOSOFTD05276A7E5664AF1D9750EF7CEB8D92E
 * ============================================================================
 */

public interface StockDao extends BaseDao<Stock, String> {

	/**
	 * 获取所有库存分类集合;
	 * 
	 * @return 所有顶级库存分类集合
	 * 
	 */
	public List<Stock> getAllStockList();
	
	/**
	 * 根据库存分类名称获取库存记录
	 * @author zhangjinquan
	 * @since 2012-11-01
	 * @param String stockName 礼品库存名称
	 */
	public Stock getStockByName(String stockName);

}
