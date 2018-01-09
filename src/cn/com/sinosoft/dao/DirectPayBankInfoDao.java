/**
 * 
 */
package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.DirectPayBankInfo;

/**
 * @author gaohaijun
 */
public interface DirectPayBankInfoDao extends BaseDao<DirectPayBankInfo, String> {

	public DirectPayBankInfo getByOrderSn(String orderSn); 
}
