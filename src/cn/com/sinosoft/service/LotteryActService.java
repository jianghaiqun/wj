package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.LotteryAct;

/**
 * Service接口 - 活动抽奖
 * ============================================================================
 * 
 * ============================================================================
 */

public interface LotteryActService extends BaseService<LotteryAct, String> {

	/**
	 *获取已得奖的记录
	 * 
	 * @param ActCode
	 *            活动编码
	 * 
	 * @param type
	 *            中奖状态
	 * 
	 * @param awards
	 *            奖项
	 * 
	 * @param RecordType
	 *            记录类型
	 * 
	 * @return List<LotteryAct>
	 * 
	 */

	public List<LotteryAct> getListByCondition(String actCode, String type, String awards, String recordType);

	/**
	 *获取抽奖资格
	 * 
	 * 
	 * @param memberId
	 *            会员id
	 * 
	 * @param recordType
	 *            记录类型
	 * @param actCode
	 * 
	 * @return List<LotteryAct>
	 * 
	 */

	public List<LotteryAct> getListByMemberId(String memberId, String recordType, String useType, String actCode);

	/**
	 *获取中奖名单
	 * 
	 * 
	 * @param type
	 *            使用状态
	 * @param recordType
	 *            记录类型
	 * @param actCode
	 *            活动类型
	 * 
	 * @return List<LotteryAct>
	 * 
	 */

	public List<LotteryAct> getListByWin(String recordType, String type, String actCode);

	public List<LotteryAct> getListByAllUse(String recordType, String useType, String actCode);
	/**
	 *获取抽奖名单
	 * 
	 * 
	 * @param actCode
	 *            活动类型
	 * 
	 * @return List<LotteryAct>
	 * 
	 */

	public List<LotteryAct> getListByAllActCode(String actCode);
	/**
	 *获取中奖名单（经验值）
	 * 
	 * 
	 * @param type
	 *            使用状态
	 * @param recordType
	 *            记录类型
	 * 
	 * @return List<LotteryAct>
	 * 
	 */

	public List<LotteryAct> getListByWin(String recordType, String type);

}