package com.sinosoft.inter;

import java.util.Map;

import cn.com.sinosoft.entity.Member;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.ibrms.RuleExp;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;

/**
 * 横向经验接口 ============================================================================
 * 
 * KEY:SINOSOFT9A02B310F538BFE58B8F04583E341B6E ============================================================================
 */

public class ExperienceAction {

	/**
	 * 
	 * @param actionId
	 *            动作id.
	 * @param map
	 * 
	 * @return boolean
	 */
	public boolean deal(String actionId, Map<String, Object> data, String actionType) {
		if ("LOGIN".equals(actionType)) {// 登录
			login(actionId, data);
		} else if ("REPLY".equals(actionType)) {// 回复
			reply(actionId, data);
		} else if ("PUBLISH".equals(actionType)) {// 发布内容
			publish(actionId, data);
		} else if ("COMMENT".equals(actionType)) {// 评论
			comment(actionId, data);
		} else if ("SURVEY".equals(actionType)) {// 问卷调查
			survey(actionId, data);
		} else if ("SUBEMAIL".equals(actionType)) {// 订阅邮件
			subEmail(actionId, data);
		} else if ("REGISTER".equals(actionType)) {// 注册
			register(actionId, data);
		} else if ("INFORMATION".equals(actionType)) {// 完善资料
			information(actionId, data);
		} else if ("MICROBLOG".equals(actionType)) {// 关注微博
			microblog(actionId, data);
		} 
		else if ("INTTOEXP".equals(actionType)) {// 积分兑换经验
			inttoexp(actionId, data);
		}
//		else if ("PRIZE".equals(actionType)) {// 积分兑换奖品
//			prize(actionId, data);
//		}

		return true;
	}

//	private void prize(String actionId, Map<String, Object> data) {
//		Member member = (Member) data.get("Member");
//		memberSchema tmemberSchema = new memberSchema();
//		tmemberSchema.setid(member.getId());
//		tmemberSchema.fill();
//		if (tmemberSchema.getcurrentValidatePoint() > 0) {
//			String outInt=(String)data.get("Integral");
//			int newExp = RuleExp.getExp(member.getId(),"",Integer.parseInt(outInt),"");
//
//			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
//			tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
//			tSDExpCalendarSchema.setMemberId(member.getId());
//			tSDExpCalendarSchema.setExperience(-tInteractionBOM.getNewExp() + "");
//			tSDExpCalendarSchema.setSource(actionId);
//			tSDExpCalendarSchema.setManner("-");
//			tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
//			tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
//			tSDExpCalendarSchema.insert();
//
//			tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + tInteractionBOM.getNewExp());
//			tmemberSchema.update();
//		}
//		
//	}

	private void inttoexp(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		if (tmemberSchema.getcurrentValidatePoint() > 0) {
			String outInt=(String)data.get("Integral");
			int newExp = RuleExp.getExp(member.getId(),"",Integer.parseInt(outInt),"");

			SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
			tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
			tSDIntCalendarSchema.setMemberId(member.getId());
			tSDIntCalendarSchema.setIntegral(outInt);
			tSDIntCalendarSchema.setSource("10");
			tSDIntCalendarSchema.setManner("1");
			tSDIntCalendarSchema.setStatus("0");
			tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
			tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
			tSDIntCalendarSchema.insert();

			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
			tSDExpCalendarSchema.setMemberId(member.getId());
			tSDExpCalendarSchema.setExperience(newExp + "");
			tSDExpCalendarSchema.setSource("10");
			tSDExpCalendarSchema.setManner("0");
			tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
			tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
			tSDExpCalendarSchema.insert();

			tmemberSchema.setcurrentValidatePoint(tmemberSchema.getcurrentValidatePoint() - Integer.parseInt(outInt));
			tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
			tmemberSchema.update();
		}

	}

	private void microblog(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_MICROBLOG,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("8");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	private void information(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_INFORMATION,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("7");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();

	}

	private void register(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_REGISTER,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("6");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();

	}

	private void subEmail(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_SUBEMAIL,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("5");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	private void survey(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_SURVEY,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp+ "");
		tSDExpCalendarSchema.setSource("4");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	private void comment(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_COMMENT,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("3");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	private void publish(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_PUBLISH,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("2");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	private void reply(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_RELPY,0,"");
		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("1");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}

	// 登录
	private static void login(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int newExp = RuleExp.getExp(member.getId(),RuleExp.TYPE_LOGIN,0,"");

		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(member.getId());
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("0");
		tSDExpCalendarSchema.setManner("0");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();

	}
}