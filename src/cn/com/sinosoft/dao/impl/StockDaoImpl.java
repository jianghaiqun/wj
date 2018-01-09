package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.StockDao;
import cn.com.sinosoft.entity.GALog;
import cn.com.sinosoft.entity.Stock;

/**
 * Dao实现类 - 库存量
 * ============================================================================
 * KEY:SINOSOFT9F75E1059319B1C5FEFD644E19AE7400
 * ============================================================================
 */

@Repository
public class StockDaoImpl extends BaseDaoImpl<Stock, String> implements StockDao {

	@SuppressWarnings("unchecked")
	public List<Stock> getAllStockList() {
		String hql = "from Stock stock";
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * 根据库存分类名称获取库存记录
	 * @author zhangjinquan
	 * @since 2012-11-01
	 * @param String stockName 礼品库存名称
	 */
	public Stock getStockByName(String stockName)
	{
		String hql = "from Stock where name=?";
		return (Stock)(getSession().createQuery(hql).setParameter(0, stockName).uniqueResult());
	}

}