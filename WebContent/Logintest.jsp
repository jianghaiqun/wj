
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%@page import="com.sinosoft.framework.orm.*"%>
<%@page import="com.sinosoft.framework.controls.*"%>
<%@page import="com.sinosoft.framework.license.*"%>
<%@page import="com.sinosoft.schema.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%String user =request.getParameter("user");
	String pwd = request.getParameter("pwd");
	System.out.println(user+"==========");
	System.out.println(pwd+"==========");
	Login login=new Login();
	String userResult=login.ssoLoginNew(user);
	out.print(userResult);
%>