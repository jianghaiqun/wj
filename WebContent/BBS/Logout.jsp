<%@ page import="com.sinosoft.bbs.Forum"%>
<%
	String SiteID = request.getParameter("SiteID");
	Forum.logout(request,response);
	response.sendRedirect("Index.jsp?SiteID="+SiteID);

%>