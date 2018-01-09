
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.Config"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="cn.com.sinosoft.util.CookieUtil"%>
<%
	//cps用户ID
	String cpsUserId = Config.getValue("JTWUSERID");
	//产品编码
	String productId = request.getParameter("productId");
	//cps用户链接来源
	String cpsUserSource = "JTW";
	String url = "http://www.kaixinbao.com";
	if(StringUtil.isNotEmpty(productId)){
		 url=new QueryBuilder("select HtmlPath from sdproduct where productid=?",productId).executeString();
	}
	
	session.setAttribute("channel","CPS");
	
	CookieUtil.addCookie(response,"cpsUserId",cpsUserId,3600*24*30);
	CookieUtil.addCookie(response,"cpsUserSource",cpsUserSource,3600*24*30);
	
	StringBuffer SURL = new StringBuffer(url);
	//渠道分类
	SURL.append("?CPS");
	response.sendRedirect(SURL.toString());
%>
  
