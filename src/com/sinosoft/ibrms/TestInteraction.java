package com.sinosoft.ibrms;

import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.ibrms.bom.AbstractBOM;
import com.sinosoft.ibrms.bom.InteractionBOM;
import com.sinosoft.schema.ZDInterexpSchema;
import com.sinosoft.schema.ZDInterexpSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestInteraction {

	// 运行规则之前从数据库获得BOM
	public InteractionBOM getInteractionBOM(String UserName) {

		// 查询数据库初始化bom
		// 将数据赋值到BOM中，假设现在只有 回复，和发布内容
		ZDInterexpSchema tZDInterexpSchema = new ZDInterexpSchema();
		tZDInterexpSchema.setUserName(UserName);
		ZDInterexpSet tZDInterexpSet = tZDInterexpSchema.query();
		if (tZDInterexpSet.size() != 0) {
			tZDInterexpSchema = tZDInterexpSet.get(0);
		}
		InteractionBOM tInteractionBOM = new InteractionBOM();

		if (tZDInterexpSchema.getOperateDate() != null && DateUtil.toDateTimeString(tZDInterexpSchema.getOperateDate()).substring(0, 10).equals(DateUtil.getCurrentDateTime().substring(0, 10))) {

			tInteractionBOM.setLoginCountD(tZDInterexpSchema.getLoginCount());
			tInteractionBOM.setReplyCountD(tZDInterexpSchema.getReplyCountD());
			tInteractionBOM.setPublishCountD(tZDInterexpSchema.getPublishCountD());
			tInteractionBOM.setCommentCountD(tZDInterexpSchema.getCommentCountD());
			tInteractionBOM.setSurveyCountD(tZDInterexpSchema.getSurveyCountD());

			tInteractionBOM.setReplyCountY(tZDInterexpSchema.getReplyCountY());
			tInteractionBOM.setPublishCountY(tZDInterexpSchema.getPublishCountY());
			tInteractionBOM.setCommentCountY(tZDInterexpSchema.getCommentCountY());
			tInteractionBOM.setSurveyCountY(tZDInterexpSchema.getSurveyCountY());

			tInteractionBOM.setSubEmailCount(tZDInterexpSchema.getSubEmailCount());
			tInteractionBOM.setRegisterCount(tZDInterexpSchema.getRegisterCount());
			tInteractionBOM.setInformationCount(tZDInterexpSchema.getInformationCount());
			tInteractionBOM.setMicroblogCount(tZDInterexpSchema.getMicroblogCount());
		} else {
			if (tZDInterexpSchema.getOperateDate() == null || !DateUtil.toDateTimeString(tZDInterexpSchema.getOperateDate()).substring(0, 4).equals(DateUtil.getCurrentDateTime().substring(0, 4))) {
				tInteractionBOM.setLoginCountD(0);
				tInteractionBOM.setReplyCountD(0);
				tInteractionBOM.setPublishCountD(0);
				tInteractionBOM.setCommentCountD(0);
				tInteractionBOM.setSurveyCountD(0);
				tInteractionBOM.setReplyCountY(0);
				tInteractionBOM.setPublishCountY(0);
				tInteractionBOM.setCommentCountY(0);
				tInteractionBOM.setSurveyCountY(0);
			} else {
				tInteractionBOM.setLoginCountD(0);
				tInteractionBOM.setReplyCountD(0);// 设置已经回复的次数
				tInteractionBOM.setPublishCountD(0);// 设置已经发布内容的次数\
				tInteractionBOM.setCommentCountD(0);
				tInteractionBOM.setSurveyCountD(0);
			}
		}
		tInteractionBOM.setNewExp(0);
		return tInteractionBOM;
	}

	// 运行完规则后保存数据
	public boolean updateInterexp(InteractionBOM tInteractionBOM, String UserName) {
		ZDInterexpSchema tZDInterexpSchema = new ZDInterexpSchema();
		tZDInterexpSchema.setUserName(UserName);
		tZDInterexpSchema.setLoginCount(tInteractionBOM.getLoginCountD());
		tZDInterexpSchema.setReplyCountD(tInteractionBOM.getReplyCountD());
		tZDInterexpSchema.setPublishCountD(tInteractionBOM.getPublishCountD());
		tZDInterexpSchema.setCommentCountD(tInteractionBOM.getCommentCountD());
		tZDInterexpSchema.setSurveyCountD(tInteractionBOM.getSurveyCountD());
		tZDInterexpSchema.setReplyCountY(tInteractionBOM.getReplyCountY());
		tZDInterexpSchema.setPublishCountY(tInteractionBOM.getPublishCountY());
		tZDInterexpSchema.setCommentCountY(tInteractionBOM.getCommentCountY());
		tZDInterexpSchema.setSurveyCountY(tInteractionBOM.getSurveyCountY());
		tZDInterexpSchema.setSubEmailCount(tInteractionBOM.getSubEmailCount());
		tZDInterexpSchema.setRegisterCount(tInteractionBOM.getRegisterCount());
		tZDInterexpSchema.setInformationCount(tInteractionBOM.getInformationCount());
		tZDInterexpSchema.setMicroblogCount(tInteractionBOM.getMicroblogCount());
		tZDInterexpSchema.setOperateDate(new Date());
		ZDInterexpSchema dZDInterexpSchema = new ZDInterexpSchema();
		dZDInterexpSchema.setUserName(UserName);
		ZDInterexpSet tZDInterexpSet = dZDInterexpSchema.query();
		if (tZDInterexpSet.size() != 0) {
			return tZDInterexpSchema.update();
		}
		return tZDInterexpSchema.insert();
	}

	public static void main(String[] args) {

		TestInteraction tTestInteraction = new TestInteraction();
		InteractionBOM tInteractionBOM = new InteractionBOM();
		tInteractionBOM = tTestInteraction.getInteractionBOM("admin");
		tInteractionBOM.setIsReply(1);
		
		List<AbstractBOM> list = new ArrayList<AbstractBOM>();
		list.add(tInteractionBOM);
		
		
		
		List<String> listString = new ArrayList<String>();
		listString.add("D:\\rules\\qyrule\\00000000000000000039.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000040.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000041.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000042.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000043.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000044.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000045.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000047.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000048.drl");
		listString.add("D:\\rules\\qyrule\\00000000000000000049.drl");
		RuleInlet tRuleInlet = new RuleInlet(listString, list);
		tRuleInlet.excuteAppoint();
		tTestInteraction.updateInterexp(tInteractionBOM, "admin");
		System.exit(0);
	}
}
