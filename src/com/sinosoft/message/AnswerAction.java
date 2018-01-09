package com.sinosoft.message;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.document.MessageCache;
import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCAnswerSchema;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.memberSchema;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 周翔
 * @Date 2012-5-24
 * @Mail zhouxiang@sinosoft.com
 */

public class AnswerAction extends ShopDispatchAction {
	
	public String save(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String tQuestionTitle=URLDecoder.decode(request.getParameter("QuestionTitle"), "UTF-8");
		String tContent=URLDecoder.decode(request.getParameter("Content"), "UTF-8");
		String tQuestionId=URLDecoder.decode(request.getParameter("QuestionId"), "UTF-8");
		String tVerifyCode=URLDecoder.decode(request.getParameter("VerifyCode"), "UTF-8"); 
		//String tTitle = request.getParameter("Title");
		if(StringUtil.isEmpty(tContent)){
			map.put("test", "问题还没有写，不能提交哟！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}
		Object authCode = request.getSession().getAttribute("CommentYZM");
		if ((authCode == null) || !authCode.equals(tVerifyCode))
		{
			map.put("test", "验证码没有写对哟！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}
		//执行存储
		SCAnswerSchema tSCAnswerSchema=new SCAnswerSchema();
		tSCAnswerSchema.setId(NoUtil.getMaxID("AnswerID")+"");
		tSCAnswerSchema.setQuestionId(tQuestionId);
		tSCAnswerSchema.setQuestionTitle(tQuestionTitle);
		if(StringUtil.isEmpty((String) request.getSession().getAttribute("loginMemberId"))){
			map.put("test", "nologin");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}else{
			tSCAnswerSchema.setUserId((String) request.getSession().getAttribute("loginMemberId"));
		}
		tSCAnswerSchema.setAddUserIP(request.getRemoteAddr());
		tSCAnswerSchema.setContent(tContent);
		tSCAnswerSchema.setAddDate(new Date());
		tSCAnswerSchema.setaState("00");
		boolean flag = tSCAnswerSchema.insert();
		logger.info("是否正确："+flag);

		if(flag){
			Map<String, Object> data = new HashMap<String, Object>();
			Member member=new Member();
			member.setId((String) request.getSession().getAttribute("loginMemberId"));
			
			
			
			memberSchema tmemberSchema=new memberSchema();
			tmemberSchema.setid(member.getId());
			tmemberSchema.fill();
			String temp="";
			if(StringUtil.isNotEmpty(tmemberSchema.getusername())){
				temp=tmemberSchema.getusername();
			}
			else if(StringUtil.isNotEmpty(tmemberSchema.getmobileNO())){
				temp=tmemberSchema.getmobileNO();
			}
			else if(StringUtil.isNotEmpty(tmemberSchema.getemail())){
				temp=tmemberSchema.getemail();
			}
	
			StringFormat sf = new StringFormat("标题为 ? 的文档已被回复。");
			sf.add("<font class='red'>" + tQuestionTitle + "</font>");
			String subject = sf.toString();
			Transaction trans = new Transaction();
			String addTime=DateUtil.getCurrentDateTime();
			sf = new StringFormat("标题为 ? 的文档，己于 ? 由 ? 回复。");
			sf.add("<font class='red'>" + tQuestionTitle + "</font>");//ArticleTitle
			sf.add("<font class='red'>" + addTime + "</font>");
			sf.add("<font class='red'>" + temp + "</font>");
			ZDConfigSchema tZDConfigSchema=new ZDConfigSchema();
			tZDConfigSchema.setType("MessageToGm");
			tZDConfigSchema.fill();
			if(StringUtil.isNotEmpty(tZDConfigSchema.getValue())){
				String[] toGM=spiltAdd(tZDConfigSchema.getValue());
				data.put("replyUserName", temp);
				data.put("replyAddTime", addTime);
				for(String user : toGM){
					data.put("receiverZdUserName", user);
					data.remove("Member");
					ActionUtil.sendMessage("wj00043", data);
					data.put("Member", member);
					ActionUtil.dealAction("wj00043", data, request);
				}
				MessageCache.addMessage(trans, subject, sf.toString(), toGM, "SYSTEM",
						false);
			trans.commit();
			}
			map.put("test", "提交成功！");
			
			/*String exp=tSDExpCalendarSchema1.getExperience();
			if(Long.parseLong(exp)==0){
				map.put("test", "提交成功！");
			}else{
				map.put("test", "提交成功！恭喜您获得"+exp+"经验值");
			}*/
			//return "提交成功！";
		}else{
			map.put("test", "提交失败！");
			//return "提交失败！";
		}
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}
	private static String[] spiltAdd(String temp){
		String[] str=temp.split(",");
		
		return str;
	}
	
	
}
