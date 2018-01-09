package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.GALog;
/**
 * Dao接口 - GA日志
 * ============================================================================
 * KEY:SINOSOFTF0640ED29F118E8C97B3B48248458ACA
 * ============================================================================
 */
public interface GALogDao extends BaseDao<GALog, String> {
	
	public GALog getGALogByOrderSn(String orderSn);
	public GALog getGALogByMemberId(String memberId);
}