/**
 * 
 */
package cn.com.sinosoft.service;

import java.util.Map;


/**
 * @author wangcaiyun
 *
 */
public interface ExchangeGoodsService {

	public Map<String, Object> doExchange(Map<String, String> param);
	
	public Map<String, Object> checkParam(String param);
	
	public Map<String, Object> orderSearch(String orderSn);
	
	public boolean notifyFulu(Map<String, String> param, String sign);
}
