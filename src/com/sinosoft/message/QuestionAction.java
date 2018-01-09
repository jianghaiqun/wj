package com.sinosoft.message;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.document.MessageCache;
import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCQuestionSchema;
import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.schema.SDExpCalendarSet;
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

public class QuestionAction extends ShopDispatchAction {

	public String save(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		String tTitle = URLDecoder.decode(request.getParameter("Title"), "UTF-8");
		String tContent = URLDecoder.decode(request.getParameter("Content"), "UTF-8");
		String tVerifyCode = URLDecoder.decode(request.getParameter("VerifyCode"), "UTF-8"); 
		// String aa=(String) request.getSession().getAttribute("loginMemberId");
		// 执行存储
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
	    String[] str = ip.split(",");
	    if(str!=null && str.length>1){
	    ip = str[0];
	    }
		SCQuestionSchema tSCQuestionSchema = new SCQuestionSchema();
		tSCQuestionSchema.setId(NoUtil.getMaxID("QuestionID") + "");
		String id = (String) request.getSession().getAttribute("loginMemberId");
		String memberid = "";
		if (StringUtil.isEmpty(id)) {
			map.put("test", "nologin");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		} else {
			if(!"tencent".equals(id)){
				memberid = id;
			}else{
				memberid = new QueryBuilder(" SELECT id FROM member where mBindInfoForLogin_id = ? limit 1 ",String.valueOf(request.getSession().getAttribute("loginBindId"))).executeString();
			}
			tSCQuestionSchema.setUserId(memberid);
		}
		if (StringUtil.isEmpty(tTitle)) {
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
		tSCQuestionSchema.setTitle(tTitle);
		tSCQuestionSchema.setContent(tContent);   
		tSCQuestionSchema.setState("00");
		tSCQuestionSchema.setaState("00");
		tSCQuestionSchema.setAddDate(new Date());
		tSCQuestionSchema.setType("00");// 需前台修改
		tSCQuestionSchema.setAddUserIP(ip);

		tSCQuestionSchema.setQuestionId("0000");
		boolean flag = tSCQuestionSchema.insert();
		logger.info("是否正确：{}", flag);

		if (flag) {
			ActionUtil actionUtil = new ActionUtil();
			Map<String, Object> data = new HashMap<String, Object>();
			Member member = new Member();
			member.setId((String) request.getSession().getAttribute("loginMemberId"));
			data.put("Member", member);

			memberSchema tmemberSchema = new memberSchema();
			tmemberSchema.setid(member.getId());
			tmemberSchema.fill();
			String temp = "";
			if (StringUtil.isNotEmpty(tmemberSchema.getusername())) {
				temp = tmemberSchema.getusername();
			} else if (StringUtil.isNotEmpty(tmemberSchema.getmobileNO())) {
				temp = tmemberSchema.getmobileNO();
			} else if (StringUtil.isNotEmpty(tmemberSchema.getemail())) {
				temp = tmemberSchema.getemail();
			}

			StringFormat sf = new StringFormat("标题为 ? 的文档已被创建。");
			sf.add("<font class='red'>" + tTitle + "</font>");
			String subject = sf.toString();
			Transaction trans = new Transaction();
			String addTime = DateUtil.getCurrentDateTime();
			sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 创建。");
			sf.add("<font class='red'>" + tTitle + "</font>");// ArticleTitle
			sf.add("<font class='red'>" + addTime + "</font>");
			sf.add("<font class='red'>" + temp + "</font>");
			String toMail = Config.getValue("MessageToGm");
			if (StringUtil.isNotEmpty(toMail)) {
				String[] toGM = spiltAdd(toMail);
				data.put("askUserName", temp);
				data.put("askAddTime", addTime);
				for(String user : toGM){
					data.put("receiverZdUserName", user);
					data.remove("Member");
					ActionUtil.sendMessage("wj00044", data);
					data.put("Member", member);
					ActionUtil.dealAction("wj00044", data, request);
				}
				MessageCache.addMessage(trans, subject, sf.toString(), toGM, "SYSTEM", false);
				trans.commit();
			}
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema.query(new QueryBuilder("where source='2' and memberid ='" + (String) request.getSession().getAttribute("loginMemberId") + "' "));
			map.put("test", "提交成功！");
			/*SDExpCalendarSchema tSDExpCalendarSchema1 = tSDExpCalendarSet.get(tSDExpCalendarSet.size() - 1);
			String exp = tSDExpCalendarSchema1.getExperience();
			if (Long.parseLong(exp) == 0) {
				map.put("test", "提交成功！");
			} else {
				map.put("test", "提交成功！恭喜您获得" + exp + "经验值");
			}*/
		} else {
			map.put("test", "Sorry，您提交的信息失败，请重新提交！");
		}
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}

	private static String[] spiltAdd(String temp) {
		String[] str = temp.split(",");

		return str;
	}
	// protected void service(HttpServletRequest request,
	// HttpServletResponse response, String method)
	// throws ServletException, IOException {
	// response.setContentType("text/xml");
	// String tContent = request.getParameter("Content");
	// String tTitle = request.getParameter("Title");
	// //执行存储
	// SCQuestionSchema tSCQuestionSchema=new SCQuestionSchema();
	// tSCQuestionSchema.setId(NoUtil.getMaxID("QuestionID")+"");
	// tSCQuestionSchema.setUserId("wade");//需前台修改
	// tSCQuestionSchema.setTitle(tTitle);
	// tSCQuestionSchema.setContent(tContent);
	// tSCQuestionSchema.setState("00");
	// tSCQuestionSchema.setaState("00");
	// tSCQuestionSchema.setAddDate(new Date());
	// tSCQuestionSchema.setType("00");//需前台修改
	// tSCQuestionSchema.setAddUserIP("11");//需前台修改
	//
	// tSCQuestionSchema.setQuestionId("11");
	// boolean flag = tSCQuestionSchema.insert();
	// System.out.println("是否正确："+flag);
	// if(flag){
	// response.sendRedirect("success.html") ;
	// }else{
	// response.sendRedirect("fail.html") ;
	// }
	// }

}
