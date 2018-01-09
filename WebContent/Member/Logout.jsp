<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%
	String SiteID = request.getParameter("SiteID");
	session.invalidate();
	String Referer = request.getParameter("Referer");
	if (StringUtil.isEmpty(Referer)) {
		Referer = request.getHeader("referer");
	}
	response.sendRedirect(Referer);
%>