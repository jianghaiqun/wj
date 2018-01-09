/**
 * 
 */
package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.PointExchangeInfo;

/**
 * @author Administrator
 *
 */
public interface PointExchangeInfoService extends BaseService<PointExchangeInfo, String> {

	//根据订单号获取积分兑换结果信息
	public List<PointExchangeInfo> pointexchangelist(String orderSn);
}
