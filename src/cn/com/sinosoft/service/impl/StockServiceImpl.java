package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.StockDao;
import cn.com.sinosoft.entity.Stock;
import cn.com.sinosoft.service.StockService;

@Service
public class StockServiceImpl extends BaseServiceImpl<Stock, String> implements StockService {
	@Resource
	StockDao stockDao;

	@Resource
	public void setStockDao(StockDao stockDao) {
		super.setBaseDao(stockDao);
	}

	public List<Stock> getStockList() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		return stockDao.findByQBs(qbs, "id", "asc");
	}
	
	/**
	 * 根据库存分类名称获取库存记录
	 * @author zhangjinquan
	 * @since 2012-11-01
	 * @param String stockName 礼品库存名称
	 */
	public Stock getStockByName(String stockName)
	{
		return stockDao.getStockByName(stockName);
	}

}
