package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.GALog;
/**
 * Service接口 - GA日志
 * ============================================================================
 * KEY:SINOSOFT6293041B8DFA2A13E8B33FEC71AA8B5D
 * ============================================================================
 */
public interface GALogService extends BaseService<GALog, String> {
	public GALog getGALogByOrderSn(String orderSn);
	public GALog getGALogByMemberId(String memberId);
}
