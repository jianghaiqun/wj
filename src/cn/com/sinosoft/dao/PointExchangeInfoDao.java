/**
 * 
 */
package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.PointExchangeInfo;

/**
 * @author Administrator
 *
 */
public interface PointExchangeInfoDao extends BaseDao<PointExchangeInfo, String> {
	
	public List<PointExchangeInfo> pointexchangelist(String orderSn);

}
