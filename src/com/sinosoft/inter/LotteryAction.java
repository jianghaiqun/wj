package com.sinosoft.inter;

import java.util.Map;

import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LotteryActSchema;

/**
 * 横向抽奖活动接口 ============================================================================
 * 
 * KEY:SINOSOFT9A02B310F538BFE58B8F04583E341B6E ============================================================================
 */

public class LotteryAction {

	/**
	 * 
	 * @param actionId
	 *            动作id.
	 * @param map
	 * 
	 * @return boolean
	 */
	public boolean deal(String actionId, Map<String, Object> data, String actType) {
			LotteryActSchema tLotteryActSchema = new LotteryActSchema();
			tLotteryActSchema.setid(NoUtil.getMaxNo("LotteryActID", 10));
			tLotteryActSchema.setorderNo((String) data.get("OrderNo"));
			tLotteryActSchema.setmemberId((String) data.get("MemberId"));
			tLotteryActSchema.setactCode(actType);
			tLotteryActSchema.setrecordType("RESULT");
			tLotteryActSchema.setuseType("N");
			if (!tLotteryActSchema.insert()) {
				return false;
			}

		return true;
	}
}