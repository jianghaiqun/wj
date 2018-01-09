package com.sinosoft.cms.dataservice;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.sinosoft.entity.Member;

import com.jcraft.jsch.Session;
import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.site.BadWord;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.member.Login;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCQuestionSchema;
import com.sinosoft.schema.SCQuestionSet;
import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.schema.SDExpCalendarSet;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;


public class CommentService extends Ajax {

	public static void init(Mapx params) {
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("CommentActionURL", Config.getValue("CommentActionURL"));
		params.put("CommentCountJS", Config.getValue("CommentCountJS"));
		params.put("CommentListJS", Config.getValue("CommentListJS"));
		params.put("CommentListPageJS", Config.getValue("CommentListPageJS"));
		params.put("front", Config.getValue("FrontServerContextPath"));
		params.put("ServerContext", Config.getValue("ServerContext"));
		params.put("StaticServerContext", Config.getValue("StaticResourcePath"));
		return;
	}

	public static void dg1DataBind(DataListAction dla) {
		String relaID = dla.getParam("RelaID"); 
		if (dla.getTotal() == 0) {
			dla.setTotal(new QueryBuilder("select count(*) from ZCComment where verifyflag='Y' and relaID = ?",Long.parseLong(relaID)));
		}
		DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc",Long.parseLong(relaID)).executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "AntiCount") == null) {
				dt.set(i, "AntiCount", 0);
			}
			if (dt.get(i, "SupporterCount") == null) {
				dt.set(i, "SupporterCount", 0);
			}
		}
		dla.bindData(dt);
	}
	
	public static String getPageBar(int total,int onePageCount, int pageIndex) {
		StringBuffer sb2 = new StringBuffer();
		int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
		pageIndex=pageIndex-1;
		if(pageIndex < 0){
			pageIndex = 0;
		}
		// 分页
		sb2.append("<div id='pagination'><ul>");
		
		if (pageIndex > 0) {
			sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='getPageByPageIndex(" + pageIndex + ","+total+","+onePageCount+");'><span>上一页</span></a></li>");
			sb2.append("<li><a href='javascript:void(0)' onClick='getPageByPageIndex(1,"+total+","+onePageCount+");'><span>"+ 1+"</span></a></li>");
		} else {
			sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='getPageByPageIndex(1,"+total+","+onePageCount+");'><span class='default'>上一页</span></a></li>");
			sb2.append("<li class='now'><a href='javascript:void(0)' onClick='getPageByPageIndex(1,"+total+","+onePageCount+");'><span>"+ 1+"</span></a></li>");
		}
		int j = 2;
		for (j = 2;j< (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)); j++) {
			if(pageCount>6){
				if (pageIndex >= pageCount - 4) {
					if (j >= pageCount - 3) {
						if (j == pageCount - 3) {
							sb2.append("<li class='omit'><span>...</span></li>");
						}
						if (j==(pageIndex+1)) {
							sb2.append("<li class='now'>");
						} else {
							sb2.append("<li>");
						}
						sb2.append("<a href='javascript:void(0)' onClick='getPageByPageIndex(" + j + ","+total+","+onePageCount+");'><span>"+j+"</span></a></li>");
						
					}
				} else if(pageIndex<3){
						if(j<5){
							if (j==(pageIndex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='getPageByPageIndex(" + j + ","+total+","+onePageCount+");'><span>"+ j+"</span></a></li>");
							if(j==4){						
								sb2.append("<li class='omit'><span>...</span></li>");
							}
						}
				} else {
					if(pageIndex>2 && pageCount>(pageIndex+1)){
						if(j>(pageIndex-1)&&j<(pageIndex+3)){
							if (j==(pageIndex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='getPageByPageIndex(" + j + ","+total+","+onePageCount+");'><span>"+j+"</span></a></li>");
						}
						if(j==(pageIndex+2)&&j<pageCount-2){
							sb2.append("<li class='omit'><span>...</span></li>");
						}
					}
				}
			}else if(pageCount<7){
				if (j==(pageIndex+1)) {
					sb2.append("<li class='now'>");
				} else {
					sb2.append("<li>");
				}
				sb2.append("<a href='javascript:void(0)' onClick='getPageByPageIndex(" + j + ","+total+","+onePageCount+");'><span>"+ j+"</span></a></li>");
			}
		}

		if (pageIndex + 1 == pageCount) {
			if (pageCount > 1) {
				sb2.append("<li class='now'><a href='javascript:void(0)' onClick='getPageByPageIndex(" + pageCount + ","+total+","+onePageCount+");'><span>"+pageCount+"</span></a></li>");
			}
			sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='getPageByPageIndex(" + pageCount + ","+total+","+onePageCount+");'><span class='default'>下一页</span></a></li>");
		} else {
			sb2.append("<li><a href='javascript:void(0)' onClick='getPageByPageIndex(" + pageCount + ","+total+","+onePageCount+");'><span>"+pageCount+"</span></a></li>");
			sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='getPageByPageIndex(" + (pageIndex + 2) +","+total+","+onePageCount+");'><span>下一页</span></a></li>");
		}
		
		sb2.append("</ul></div>");
		return sb2.toString();
	}

	public static DataTable listDataBind(Mapx params, DataRow parentDR) {
		String relaID = params.getString("RelaID");
		String count = params.getString("Count");
		if (StringUtil.isEmpty(count)) {
			count = 5 + "";
		}
		DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc",Long.parseLong(relaID)).executePagedDataTable(Integer.parseInt(count), 0);

		return dt;
	}

	public static void dealSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String relaID = request.getParameter("RelaID");
		String title = request.getParameter("Title");
		String content = request.getParameter("CmntContent");
		PrintWriter out = response.getWriter();
		String ids = (String)User.getValue(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.lengthEx(content) > 1000) {
			out.print("<script type='text/javascript'>alert('评论内容不能超过200个字！');window.history.go(-1);</script>");
			return;
		}
		if (StringUtil.isNotEmpty(content)) {
			String catalogID = request.getParameter("CatalogID");
			String catalogType = request.getParameter("CatalogType");
			String siteID = request.getParameter("SiteID");
//			String ip = request.getRemoteAddr();
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
			
			String anonymous = request.getParameter("CmntCheckbox");
			String parentID = request.getParameter("ParentID");
			String user = request.getParameter("CmntUserName");
			String password = request.getParameter("CmntPwd");
			Object authCode = User.getValue(Constant.DefaultAuthKey);
         	if (authCode == null || !authCode.equals(request.getParameter("VerifyCode"))) {
				out.print("<script type='text/javascript'>alert('验证码输入错误！');window.history.go(-1);</script>");
				return;
			}
         	
         	String id = (String) request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
         	String email = new QueryBuilder("select email from member where id = ? ",id).executeString();

         	if(id!=null&&!id.equals("")){
         		if(StringUtil.isEmpty(email)){
         			DataTable dt = new QueryBuilder("select * from orders where memberid = ? and exists (select 1 from zcarticle z1,zdcolumnvalue z2 where z1.id=z2.relaid and columncode='RiskCode' and  z1.id= ? and z2.textvalue = productid) and orderstatus = '7'",id,relaID).executeDataTable();	
             		if(dt.getRowCount()==0){
             			out.print("<script type='text/javascript'>alert('请先购买该款产品然后再添加评论');window.history.go(-1);</script>");
             			return;	
             		}
         		}
         		else if(!email.endsWith("@kaixinbao.com")){
         			DataTable dt = new QueryBuilder("select * from orders where memberid = ? and exists (select 1 from zcarticle z1,zdcolumnvalue z2 where z1.id=z2.relaid and columncode='RiskCode' and  z1.id= ? and z2.textvalue = productid) and orderstatus = '7'",id,relaID).executeDataTable();	
             		if(dt.getRowCount()==0){
             			out.print("<script type='text/javascript'>alert('请先购买该款产品然后再添加评论');window.history.go(-1);</script>");
             			return;	
             		}
         		}
         	}
            
			if (id!=null&&!id.equals("")) {
				if ("on".equals(anonymous)) {
					user = "匿名用户";
				} else {
					memberSchema memberSchemas = new memberSchema();
					memberSchemas.setid(id);
					memberSet memberSets= memberSchemas.query();
					memberSchema  memberSchemax  = memberSets.get(0);
					user = memberSchemax.getusername();
					//zhangjinquan 11180 req20121204001-评论需求-显示名称的存储处理 2012-12-11 start
					if (StringUtil.isEmpty(user))
					{
						user = memberSchemax.getmobileNO();
						if (StringUtil.isEmpty(user))
						{
							user = memberSchemax.getemail();
						}
					}
					//zhangjinquan 11180 req20121204001-评论需求-显示名称的存储处理 2012-12-11 end
					/*user = User.getUserName();
					if(user==null){
						out.print("<script type='text/javascript'>alert('请登录用户！');window.history.go(-1);</script>");
					}*/
				}
			} else {
				out.print("<script type='text/javascript'>alert('请先登录后再添加评论');window.history.go(-1);</script>");
				return;
				/*QueryBuilder qb = null;
				qb = new QueryBuilder("select count(*) from ZDMember where UserName=? and Password=?");
				qb.add(user);
				if(password!=null){
				qb.add(StringUtil.md5Hex(password).trim());
				}
                if (StringUtil.isEmpty(user) || StringUtil.isEmpty(password)) {
					out.print("<script type='text/javascript'>alert('请输入用户名和密码！');window.history.go(-1);</script>");
					return;
				}
				else if (qb.executeString().equals("0")) {
					out.print("<script type='text/javascript'>alert('用户名或密码输入不正确！');window.history.go(-1);</script>");
					return;
				} else if (qb.executeInt() > 0) {
					Login login = new Login();
					login.loginComment(request, response, user, password);
				}
				else if ("on".equals(anonymous)) {
					user = "匿名网友";
				}  */
			}
			if (StringUtil.isEmpty(title)) {
				title = "无";
			}

			ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(catalogID);
			boolean verifyFlag = false;
			if (catalogConfig != null) {
				if ("Y".equals(catalogConfig.getCommentVerify())) {
					verifyFlag = true;
				}
			}
			String CommentAddUser = "";
			String CommentAddTime = "";
			String CommentAddUserIp = "";
			String CommentContent = "";
			if(StringUtil.isNotEmpty(parentID)) {
				DataTable dt = new QueryBuilder("select * from ZCComment where ID = ?", Long.parseLong(parentID)).executeDataTable();
				
				if (dt.getRowCount() > 0) {
					CommentAddUser = dt.getString(0, "AddUser");
					CommentAddTime = dt.getString(0, "AddTime");
					CommentAddUserIp = dt.getString(0, "AddUserIP");
					CommentContent = dt.getString(0, "Content");
				}
			}

			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(NoUtil.getMaxID("CommentID"));
			comment.setCatalogID(catalogID);
			comment.setCatalogType(catalogType);
			comment.setRelaID(relaID);
			comment.setSiteID(siteID);
			comment.setTitle(StringUtil.htmlEncode(BadWord.filterBadWord(StringUtil.subStringEx(title,90))));
			if (parentID != "" && parentID != null) {
				comment.setContent("<div class=\"huifu\">" + CommentAddUser + " " + CommentAddTime + " IP:"
						+ CommentAddUserIp + "<br>" + CommentContent + "</div><br>" + StringUtil.htmlEncode(StringUtil.subStringEx(content,900)));
			} else {
				comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(content,900)));
			}
			comment.setAddUser(user);
			//===吴高强添加====
			comment.setProp1(id);
			comment.setProp2(content);
			//===吴高强添加====
			comment.setAddTime(new Date());
			comment.setAddUserIP(ip);
			if (verifyFlag) {
				comment.setVerifyFlag("X");
			} else {
				boolean hasBadWord = false;
				if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_1))) {
					hasBadWord = true;
				}
				if (!hasBadWord) {
					if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_2))) {
						hasBadWord = true;
					}
				}
				if (!hasBadWord) {
					if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, BadWord.TREELEVEL_3))) {
						hasBadWord = true;
					}
				}
				if (hasBadWord) {
					comment.setVerifyFlag("X");
				} else {
					comment.setVerifyFlag("Y");
				}
			}
			if (comment.insert()) {
				Map<String, Object> data = new HashMap<String, Object>();
				Member member=new Member();
				member.setId(id);
				data.put("Member", member);
				ActionUtil.dealAction("wj00042", data, request);
				if (verifyFlag) {
					SDExpCalendarSchema tSDExpCalendarSchema=new SDExpCalendarSchema();
					SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema.query(new QueryBuilder("where memberid ='"+id+"' "));
					SDExpCalendarSchema tSDExpCalendarSchema1=tSDExpCalendarSet.get(tSDExpCalendarSet.size()-1);
					String exp=tSDExpCalendarSchema1.getExperience();
					if(Long.parseLong(exp)==0){
					out.println("<script type='text/javascript'>alert('您的评论已经提交,请等待管理员审核');window.location='"
							+ request.getHeader("REFERER") + "';</script>");
					}else{
						out.println("<script type='text/javascript'>alert('您的评论已经提交,请等待管理员审核,恭喜您获得"+exp+"经验值!');window.location='"
								+ request.getHeader("REFERER") + "';</script>");
					}
				} else {
					SDExpCalendarSchema tSDExpCalendarSchema=new SDExpCalendarSchema();
					SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema.query(new QueryBuilder("where memberid ='"+id+"' "));
					SDExpCalendarSchema tSDExpCalendarSchema1=tSDExpCalendarSet.get(tSDExpCalendarSet.size()-1);
					String exp=tSDExpCalendarSchema1.getExperience();
					if(Long.parseLong(exp)==0){
						out.println("<script type='text/javascript'>alert('发表评论成功');window.location='"
								+ request.getHeader("REFERER") + "';</script>");
					}else{
						out.println("<script type='text/javascript'>alert('发表评论成功!恭喜您获得"+exp+"经验值!');window.location='"
								+ request.getHeader("REFERER") + "';</script>");
					}
				}
			} else {
				out.println("<script type='text/javascript'>alert('发表评论失败');window.location='"
						+ request.getHeader("REFERER") + "';</script>");
			}
		} else {
			out.print("<script type='text/javascript'>alert('提交的内容不能空');window.location='"
					+ request.getHeader("REFERER") + "';</script>");
		}
	}
}
