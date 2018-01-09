<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@page import="com.sinosoft.framework.Config"%>
<%@ taglib uri="controls" prefix="z"%>
<%
long siteID = Application.getCurrentSiteID();
long aritcleID = Long.parseLong(request.getParameter("ArticleID"));
ZCArticleSchema article = new ZCArticleSchema();
article.setID(aritcleID);
if(article.fill()){
	
	StringBuffer navHTML = new StringBuffer();
	DataTable dt = new QueryBuilder("select NavName,NavLink from SDMarketNav where MarketID ='"+aritcleID+"'").executeDataTable();
	if(dt.getRowCount()>0){
		for(int i=0;i<dt.getRowCount();i++){
			navHTML.append("<li><a href=\""+dt.getString(i, "NavLink")+"\">"+dt.getString(i, "NavName")+"</a></li>");
		}
	}
	System.out.print(navHTML);
	String path = "YXHDedit.jsp?aritcleID="+aritcleID+"&navHTML="+navHTML;
	/* if((int)article.getStatus()!=Article.STATUS_PUBLISHED){
		path="../Services/PreviewDoc.jsp?ArticleID="+aritcleID+"&currentPage="+currentPage;
	} else if("4".equals(article.getType())){
		path = article.getRedirectURL();
	}else{
	  	long catalogID = article.getCatalogID();
		String isSingle = CatalogUtil.getSingleFlag(catalogID);
		if("Y".equals(isSingle)){
		  	path = Config.getValue("Statical.TargetDir")+"/"+SiteUtil.getAlias(siteID)+"/"+CatalogUtil.getPath(catalogID);
		} else {
		  	path = Config.getValue("Statical.TargetDir")+"/"+SiteUtil.getAlias(siteID)+"/"+PubFun.getArticleURL(article);
		}
		String realPath = (Config.getContextRealPath()+path).replaceAll("/+","/");
		File f = new File(realPath);
		if(!f.exists()){
			path="../Services/PreviewDoc.jsp?ArticleID="+aritcleID+"&currentPage="+currentPage;
		}else{
			path = (Config.getContextPath()+path+"?"+System.currentTimeMillis()).replaceAll("/+", "/");;
		}
  } */
	response.sendRedirect(path);
}else{
	out.println("没有找到id为"+aritcleID+"的文章");
}
%>


