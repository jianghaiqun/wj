<%@page import="com.sinosoft.platform.Application"%>
<%@page import="com.sinosoft.cms.pub.SiteUtil"%>
<%@page import="com.sinosoft.framework.*"%>
<% 
	//String pathAlias=SiteUtil.getAlias(Application.getCurrentSiteID()) ;
	//	if(pathAlias==null){
	//		pathAlias="tjfh";
	//}
	//String path = pathAlias + "/index." + SiteUtil.getExtensionType(Application.getCurrentSiteID());;
	session.invalidate();
	//response.sendRedirect((Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + path).replaceAll("//", "/"));// + Config.getLoginPage());
	response.sendRedirect(Config.getContextPath() + Config.getLoginPage());
%>