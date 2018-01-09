package com.sinosoft.points;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;

import java.util.HashMap;
import java.util.Map;

/**
 * 手机验证
 *
 */

public class PointsRule02 extends PointsRule {
	/**
	 * 重写积分赠送规则
	 * 
	 * 推荐人处理
	 */
	public Map<String, Object> pointsDeal(String act, Map<String, Object> param, DataRow act_dr) {
		
		DataTable act_dt = PointsCalculate.getRolePiontsList(IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER , null );
		if (act_dt.getRowCount() > 0) {
			act_dr = act_dt.get(0);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		Object MemId = param.get(IntegralConstant.MEM_ID);
		// 手机号
		String businessid = String.valueOf(param.get(IntegralConstant.MOBILENO));

		if (StringUtil.isEmpty(MemId)) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "会员Id为空.");
			return result;
		}

		// 如果存在推荐人，需要赠送推荐人推荐积分
		int PointsNum = Integer.parseInt(act_dr.getString("PointsNum"));

		if (PointsNum == 0) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "手机验证未设置积分规则");
			return result;
		}

		// 推荐好友注册送积分数
		memberSchema member = new memberSchema();
		member.setid(MemId.toString());

		if (!member.fill()) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "查询会员失败");
			return result;

		}

		if (StringUtil.isEmpty(member.getrecommendMemId())) {
			result.put(IntegralConstant.STATUS, IntegralConstant.SUCCESS);
			result.put(IntegralConstant.MESSAGE, "会员没有推荐人."+member.getid());
			return result;

		}
		// 查询手机号是否绑定过已送过积分
		if (StringUtil.isNotEmpty(businessid)) {
			int count = new QueryBuilder("select count(1) from SDIntCalendar where businessid = ?", businessid).executeInt();
			if (count > 0) {
				result.put(IntegralConstant.STATUS, IntegralConstant.SUCCESS);
				result.put(IntegralConstant.MESSAGE, "被推荐人（"+MemId+"）手机号("+businessid+")已绑定过并送过推荐人("+member.getrecommendMemId()+")积分，不再送积分.");
				return result;
			}
		}
		
		// 取的推荐好友注册获得积分的人数限制
		String recommRegisterCount = Config.getValue("RecommRegisterCount");
		// 有人数限制 判断是否超过限制，超过则不送推荐人积分
		if (StringUtil.isNotEmpty(recommRegisterCount)) {
			int limit = Integer.valueOf(recommRegisterCount);
			int count = new QueryBuilder(
					"select count(1) from SDIntCalendar where MemberId = ? and Source = ?",
					member.getrecommendMemId(),
					IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER)
					.executeInt();
			//int count = new QueryBuilder("select count(1) from member where recommendMemId = ? and recommendFlag = 'Y'", member.getrecommendMemId()).executeInt();
			// 推荐人推荐超过限制则不送推荐人积分
			if (count >= limit) {
				result.put(IntegralConstant.STATUS, IntegralConstant.SUCCESS);
				result.put(IntegralConstant.MESSAGE, "推荐人超过限制，不再送积分." + recommRegisterCount + " | " + member.getrecommendMemId());
				return result;
			}
		}

		int dealCount = 0;
		QueryBuilder qb;
		do {
			// 送推荐人积分
			Transaction tran = new Transaction();
			memberSchema recommendMember = new memberSchema();
			recommendMember.setid(member.getrecommendMemId());
	
			if (!recommendMember.fill()) {
				result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				result.put(IntegralConstant.MESSAGE, "查询推荐人失败");
				return result;
			}
			
			if (StringUtil.isEmpty(recommendMember.getversion())) {
				qb = new QueryBuilder("update member set currentValidatePoint=?, recommendRegPoints=?, recommendFlag='Y', version='1', modifyDate=? where id=? and (version is null or version='')");
				qb.add(recommendMember.getcurrentValidatePoint() + PointsNum);
				qb.add(recommendMember.getrecommendRegPoints() + PointsNum);
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(recommendMember.getid());
			} else {
				qb = new QueryBuilder("update member set currentValidatePoint=?, recommendRegPoints=?, recommendFlag='Y', version=version+1, modifyDate=? where id=? and version=?");
				qb.add(recommendMember.getcurrentValidatePoint() + PointsNum);
				qb.add(recommendMember.getrecommendRegPoints() + PointsNum);
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(recommendMember.getid());
				qb.add(recommendMember.getversion());
			}
			tran.add(qb);
			
			SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
			tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
			tSDIntCalendarSchema.setMemberId(recommendMember.getid());
			tSDIntCalendarSchema.setIntegral(act_dr.getString("PointsNum"));
			tSDIntCalendarSchema.setSource(IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER);// 积分来源
			// tSDIntCalendarSchema.setProp1(IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER);
			tSDIntCalendarSchema.setManner("0");// 表示收入
			tSDIntCalendarSchema.setStatus("0");
			tSDIntCalendarSchema.setDescription(act_dr.getString("PointDes") + " 帐号：" + businessid);
			tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
			tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
			tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
			if (StringUtil.isNotEmpty(businessid)) {
				tSDIntCalendarSchema.setBusinessid(businessid);
			}
			tran.add(tSDIntCalendarSchema, Transaction.INSERT);
	
			if (tran.commitRoll()) {
				result.put(IntegralConstant.ACTION_POINTS, act_dr.getString("PointsNum"));
				break;
			}
			dealCount++;
			continue;
		}while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
		if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
			logger.error("积分赠送更新数据失败!更新sql:{} PointsRule02--pointsDeal", qb.getSQL());
			result.put(IntegralConstant.MESSAGE, "更新数据失败，积分赠送失败！");
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
		}
		return result;
	}
}
