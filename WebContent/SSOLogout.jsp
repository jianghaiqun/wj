<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.zas.*"%>
<%@page import="com.sinosoft.zas.client.*"%>
<%@page import="com.sinosoft.framework.*"%>

<%
if(session.getAttribute(com.sinosoft.zas.Constant.UserSessionAttrName)!=null){
	UserData user = (UserData) session.getAttribute(com.sinosoft.zas.Constant.UserSessionAttrName);
	//PGTUtil.removePGT(user.getUserName());
	session.invalidate();
	String renew = ClientConfig.isNeedNewLogin() ? "&renew=" + ClientConfig.isNeedNewLogin() : "";
	response.sendRedirect(ClientConfig.getServerURL() + com.sinosoft.zas.Constant.LogoutPage + "?service=" + ZASFilter.getReferer(request) + renew);
}else{
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + Config.getValue("App.LoginPage"));
}
%>
