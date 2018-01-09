package com.sinosoft.ibrms;

import java.util.Date;

import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.ibrms.bom.InteractionBOM;
import com.sinosoft.schema.ZDInterexpSchema;
import com.sinosoft.schema.ZDInterexpSet;

/**
 * 经验规则
 */
public class RuleExp {
	
	public static final String TYPE_LOGIN = "Login";//登陆
	public static final String TYPE_RELPY = "Reply";//回复
	public static final String TYPE_PUBLISH = "Publish";//发布内容
	public static final String TYPE_COMMENT = "Comment";//评论
	public static final String TYPE_SURVEY = "Survey";//问卷调查
	public static final String TYPE_SUBEMAIL = "SubEmail";//订阅Email
	public static final String TYPE_REGISTER = "Register";//注册
	public static final String TYPE_INFORMATION = "Information";//完善资料
	public static final String TYPE_MICROBLOG = "Microblog";//关注微博
	
	//运行规则之前从数据库获得BOM
	private static InteractionBOM getInteractionBOM(String MemberID){
		
		//查询数据库初始化bom
		ZDInterexpSchema tZDInterexpSchema  = new ZDInterexpSchema();
		tZDInterexpSchema.setUserName(MemberID);
		ZDInterexpSet tZDInterexpSet = tZDInterexpSchema.query();
		if(tZDInterexpSet.size()!=0){
			tZDInterexpSchema = tZDInterexpSet.get(0);
		}
		InteractionBOM tInteractionBOM = new InteractionBOM();

		if(tZDInterexpSchema.getOperateDate()!=null
				&&DateUtil.toDateTimeString(tZDInterexpSchema.getOperateDate()).substring(0, 10).equals(DateUtil.getCurrentDateTime().substring(0, 10))){
			
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
		}else{
			if(tZDInterexpSchema.getOperateDate()==null||!DateUtil.toDateTimeString(tZDInterexpSchema.getOperateDate()).substring(0, 4).equals(DateUtil.getCurrentDateTime().substring(0, 4))){
				tInteractionBOM.setLoginCountD(0);
				tInteractionBOM.setReplyCountD(0);
				tInteractionBOM.setPublishCountD(0);
				tInteractionBOM.setCommentCountD(0);
				tInteractionBOM.setSurveyCountD(0);
				tInteractionBOM.setReplyCountY(0);
				tInteractionBOM.setPublishCountY(0);
				tInteractionBOM.setCommentCountY(0);
				tInteractionBOM.setSurveyCountY(0);
			}else{
				tInteractionBOM.setLoginCountD(0);
				tInteractionBOM.setReplyCountD(0);//设置已经回复的次数
				tInteractionBOM.setPublishCountD(0);//设置已经发布内容的次数\
				tInteractionBOM.setCommentCountD(0);
				tInteractionBOM.setSurveyCountD(0);
			}
		}
		tInteractionBOM.setNewExp(0);
		return tInteractionBOM;
	}
	
	
	//运行完规则后保存数据
	private static boolean updateInterexp(InteractionBOM tInteractionBOM,String MemberID){
		ZDInterexpSchema tZDInterexpSchema  = new ZDInterexpSchema();
		tZDInterexpSchema.setUserName(MemberID);
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
		ZDInterexpSchema dZDInterexpSchema  = new ZDInterexpSchema();
		dZDInterexpSchema.setUserName(MemberID);
		ZDInterexpSet tZDInterexpSet = dZDInterexpSchema.query();
		if(tZDInterexpSet.size()!=0){
			return tZDInterexpSchema.update();
		}
		return tZDInterexpSchema.insert();
	}
	/**
	 * 
	 * @param userid 会员id
	 * @param sign   操作类型
	 * @param num    积分数量
	 * @param prizeType  抽奖类型
	 * @return
	 */
	public static int getExp(String MemberID,String sign,int num,String prizeType){
		InteractionBOM tInteractionBOM = new InteractionBOM();
		if(MemberID!=null&&!"".equals(MemberID)){
			tInteractionBOM = RuleExp.getInteractionBOM(MemberID);
		}
		if(RuleExp.TYPE_LOGIN.equals(sign)){
			tInteractionBOM.setIsLogin(1);
		}
		if(RuleExp.TYPE_RELPY.equals(sign)){
			tInteractionBOM.setIsReply(1);
		}
		if(RuleExp.TYPE_PUBLISH.equals(sign)){
			tInteractionBOM.setIsPublish(1);
		}
		if(RuleExp.TYPE_COMMENT.equals(sign)){
			tInteractionBOM.setIsComment(1);
		}
		if(RuleExp.TYPE_SURVEY.equals(sign)){
			tInteractionBOM.setIsSurvey(1);
		}
		if(RuleExp.TYPE_SUBEMAIL.equals(sign)){
			tInteractionBOM.setIsSubEmail(1);
		}
		if(RuleExp.TYPE_REGISTER.equals(sign)){
			tInteractionBOM.setIsRegister(1);
		}
		if(RuleExp.TYPE_INFORMATION.equals(sign)){
			tInteractionBOM.setIsInformation(1);
		}
		if(RuleExp.TYPE_MICROBLOG.equals(sign)){
			tInteractionBOM.setIsMicroblog(1);
		}
		if(num>0){
			tInteractionBOM.setIntegralNum(num);
		}
		if(prizeType!=null&&!"".equals(prizeType)){
			tInteractionBOM.setPrizeType(prizeType);
		}
		RuleInlet tRuleInlet = new RuleInlet(RuleSqlAssemble.module01,tInteractionBOM);
		tRuleInlet.excuteAppoint();
		if(MemberID!=null&&!"".equals(MemberID)){
			RuleExp.updateInterexp(tInteractionBOM,MemberID);
		}
		return tInteractionBOM.getNewExp();
	}
	
	public static void main(String[] args) {
		RuleExp.getExp("用户id",RuleExp.TYPE_LOGIN, 0,"抽奖类型");
	}

}
