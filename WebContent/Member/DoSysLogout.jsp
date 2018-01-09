<%@page import="com.sinosoft.framework.User"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	User.setUserName("");
	User.setLogin(false);
	User.setManager(false);
	User.setMember(false);
	User.destory();
	String flag = "Y";
	
	StringBuffer sb = new StringBuffer();
	sb.append("<form name='form'>");
	sb.append("用户名：<input type='text' id='name' size='10'/>");
	sb.append("密码：<input type='password' id='code' size='10'/>&nbsp;");
	sb.append("<input type='button' class='btn' value='登录' onClick='doSysLogin()'/>&nbsp;");
	response.getWriter().write(flag + "&" + sb.toString());
%>