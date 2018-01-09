<%@page import="com.sinosoft.cms.stat.VisitHandlerC"%>
<%
request.setCharacterEncoding("GBK");
response.setContentType("text/html;charset=GBK");
VisitHandlerC.deal(request, response);
%>
