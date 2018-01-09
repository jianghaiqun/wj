package cn.com.sinosoft.action.car;

import cn.com.sinosoft.action.shop.BaseShopAction;
import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.document.MessageCache;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCAnswerSchema;
import com.sinosoft.schema.ZDConfigSchema;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 车险问答
 * @author heyang
 *
 */
public class CarAnswer extends BaseShopAction{
	private static final long serialVersionUID = 1L;
	//回答内容
	private String Content;
	//问答id
	private String QuestionId;
	//问答题目
	private String QuestionTitle;
	//验证码
	private String VerifyCode;
	/**
	 * 提交答案
	 * @return
	 */
	public String save(){
		// 返回前台数据
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Content = java.net.URLDecoder.decode(Content, "UTF-8");
			QuestionTitle = java.net.URLDecoder.decode(QuestionTitle, "UTF-8");
			if(StringUtil.isEmpty(Content)){
				map.put("test", "noCantent");
				JSONObject jsonObject = JSONObject.fromObject(map);
				return ajax(jsonObject.toString(), "text/html");
			}
			Object authCode = getSession("CommentYZM");
			if ((authCode == null) || !authCode.equals(VerifyCode))
			{
				map.put("test", "noyzm");
				JSONObject jsonObject = JSONObject.fromObject(map);
				return ajax(jsonObject.toString(), "text/html");
			}
			//执行存储
			SCAnswerSchema tSCAnswerSchema=new SCAnswerSchema();
			tSCAnswerSchema.setId(NoUtil.getMaxID("AnswerID")+"");
			tSCAnswerSchema.setQuestionId(QuestionId);
			tSCAnswerSchema.setQuestionTitle(QuestionTitle);
			tSCAnswerSchema.setUserId("000000");
			tSCAnswerSchema.setAddUserIP(getRequest().getRemoteAddr());
			tSCAnswerSchema.setContent(Content);
			tSCAnswerSchema.setAddDate(new Date());
			tSCAnswerSchema.setaState("00");
			boolean flag = tSCAnswerSchema.insert();
			if(flag){
				Map<String, Object> data = new HashMap<String, Object>();
				Member member=new Member();
				member.setId("000000");
				String temp="匿名用户";
				StringFormat sf = new StringFormat("标题为 ? 的文档已被回复。");
				sf.add("<font class='red'>" + QuestionTitle + "</font>");
				String subject = sf.toString();
				Transaction trans = new Transaction();
				String addTime=DateUtil.getCurrentDateTime();
				sf = new StringFormat("标题为 ? 的文档，己于 ? 由 ? 回复。");
				sf.add("<font class='red'>" + QuestionTitle + "</font>");//ArticleTitle
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
						ActionUtil.dealAction("wj00043", data, getRequest());
					}
					MessageCache.addMessage(trans, subject, sf.toString(), toGM, "SYSTEM",false);
					trans.commit();
				}
				map.put("test", "yes");
			}else{
				map.put("test", "no");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	private static String[] spiltAdd(String temp){
		String[] str=temp.split(",");
		
		return str;
	}
	
	
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getQuestionId() {
		return QuestionId;
	}

	public void setQuestionId(String questionId) {
		QuestionId = questionId;
	}

	public String getQuestionTitle() {
		return QuestionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		QuestionTitle = questionTitle;
	}

	public String getVerifyCode() {
		return VerifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		VerifyCode = verifyCode;
	}


}
