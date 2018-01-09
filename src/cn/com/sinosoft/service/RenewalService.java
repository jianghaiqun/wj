package cn.com.sinosoft.service;

import java.util.Map;

public interface RenewalService {

	/**
	 * 续保查询
	 * @param map 保单号：policyNo
	 * @return
	 */
	public Map<String, Object> queryRenewal(Map<String, String> map);
	/**
	 * 续保
	 * @param map
	 * @return
	 */
	public Map<String, Object> renewal(Map<String, String> map);
}
