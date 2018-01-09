package com.sinosoft.points;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分处理顶层类<BR>
 * 02-送积分行为；03-使用积分行为
 *
 */
public abstract class PointsRule {
	protected static final Logger logger = LoggerFactory.getLogger(PointsRule.class);

	/**
	 * 送积分处理 更新会员表积分、积分流水表明细
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> pointsDeal(String act, Map<String, Object> param, DataRow act_dr) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(IntegralConstant.STATUS, IntegralConstant.SUCCESS);

		// 会员Id
		String memId = String.valueOf(param.get(IntegralConstant.MEM_ID));
		String businessid = String.valueOf(param.get(IntegralConstant.BUSINESS_ID));

		if (StringUtil.isEmpty(memId)) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "会员Id为空，请传值");
			return result;

		}

		int dealCount = 0;
		QueryBuilder qb;
		do {
			memberSchema member = new memberSchema();
			member.setid(memId);
			if (!member.fill()) {
				result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				result.put(IntegralConstant.MESSAGE, "会员不存在");
				return result;
			}
	
			Transaction tran = new Transaction();
	
			SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
			tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
			tSDIntCalendarSchema.setMemberId(memId);
			tSDIntCalendarSchema.setIntegral(act_dr.getString("PointsNum"));
			tSDIntCalendarSchema.setSource(act_dr.getString("MemberAct"));// 积分来源
			tSDIntCalendarSchema.setManner("0");// 表示收入
			tSDIntCalendarSchema.setStatus("0");
			tSDIntCalendarSchema.setDescription(act_dr.getString("PointDes"));
			tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
			tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
			tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
			if (StringUtil.isNotEmpty(businessid)) {
				tSDIntCalendarSchema.setBusinessid(businessid);
			}
			tran.add(tSDIntCalendarSchema, Transaction.INSERT);
			
			if (StringUtil.isEmpty(member.getversion())) {
				qb = new QueryBuilder("update member set currentValidatePoint=?, version='1', modifyDate=? where id=? and (version is null or version='')");
				qb.add(member.getcurrentValidatePoint() + Integer.parseInt(act_dr.getString("PointsNum")));
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(member.getid());
			} else {
				qb = new QueryBuilder("update member set currentValidatePoint=?, version=version+1, modifyDate=? where id=? and version=?");
				qb.add(member.getcurrentValidatePoint() + Integer.parseInt(act_dr.getString("PointsNum")));
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(member.getid());
				qb.add(member.getversion());
			}
			tran.add(qb);
			
			if (tran.commitRoll()) {
				result.put(IntegralConstant.ACTION_POINTS, act_dr.getString("PointsNum"));
				break;
			}
			dealCount++;
			continue;
		} while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
		
		if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
			logger.error("积分赠送更新数据失败!更新sql:{} PointsRule--pointsDeal", qb.getSQL());
			result.put(IntegralConstant.MESSAGE, "更新数据失败，积分赠送失败！");
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
		}
		return result;
	}

}
