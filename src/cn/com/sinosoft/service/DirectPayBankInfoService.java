/**
 * 
 */
package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.DirectPayBankInfo;

/**
 * @author gaohaijun
 */
public interface DirectPayBankInfoService extends BaseService<DirectPayBankInfo, String> {
	
	public DirectPayBankInfo getByOrderSn(String orderSn); 
}
