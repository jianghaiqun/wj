<%
String Services = Config.getValue("ServicesContext");
String SiteID = request.getParameter("SiteID");
String ADID   = request.getParameter("ADID");
String URL   = request.getParameter("URL");
com.sinosoft.cms.stat.impl.LeafStat.dealADClick(SiteID,ADID);
%>
<%@page import="com.sinosoft.framework.Config"%>
<script>
window.location = "<%=URL%>";
</script>