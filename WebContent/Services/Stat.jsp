<%@page import="com.sinosoft.cms.stat.VisitHandler"%>
<%
request.setCharacterEncoding("GBK");
response.setContentType("text/html;charset=GBK");
VisitHandler.deal(request,response);
%>