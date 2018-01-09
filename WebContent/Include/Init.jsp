<%@ page contentType="text/html; charset=utf-8"%> 
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%@page import="com.sinosoft.framework.cache.*"%>
<%@page import="com.sinosoft.framework.orm.*"%>
<%@page import="com.sinosoft.framework.controls.*"%>
<%@page import="com.sinosoft.schema.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%@page import="com.sinosoft.cms.site.*"%>
<%@page import="com.sinosoft.cms.document.*"%>
<%@page import="com.sinosoft.cms.dataservice.*"%>
<%@page import="com.sinosoft.cms.pub.*"%>
<%@page import="com.sinosoft.platform.pub.*"%>
<%@page import="com.sinosoft.bbs.ForumUtil"%>
<%
if(!com.sinosoft.framework.User.isLogin()||!com.sinosoft.framework.User.isManager()){
	out.println("<script>alert('用户会话己失效，请重新登陆!');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}else if(!com.sinosoft.platform.Priv.isValidURL(request.getServletPath())){
	out.println("<script>alert('您没有访问此页面的权限!请联系管理员.');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
