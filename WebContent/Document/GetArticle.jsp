?<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.net.*"%>
<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%@page import="com.sinosoft.framework.cache.*"%>
<%@page import="com.sinosoft.framework.orm.*"%>
<%@page import="com.sinosoft.framework.controls.*"%>
<%@page import="com.sinosoft.schema.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%@page import="com.sinosoft.cms.site.*"%>
<%@page import="com.sinosoft.cms.document.*"%>
<%@page import="com.sinosoft.cms.dataservice.*"%>
<%@page import="com.sinosoft.cms.pub.*"%>
<%@page import="com.sinosoft.platform.pub.*"%>
<%
    String title = request.getParameter("Title");
	String articleID = request.getParameter("ArticleID");
	ZCArticleSchema article = new ZCArticleSchema();
	if (StringUtil.isNotEmpty(articleID) && StringUtil.isDigit(articleID) ) {
		article.setID(articleID);
		article.fill();
		ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());

		ZCSiteSchema site = SiteUtil.getSchema(article.getSiteID() + "");

    	long id = article.getID();
		String path =  PubFun.getArticleURL(site,catalog,article);
		System.out.println(path);
    	out.write("getArticleParams('"+id+"','"+path+"')");
	} else {
		try {
			if(StringUtil.isNotEmpty(title)){
				title  = URLDecoder.decode(title,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}
			
		ZCArticleSet set = article.query(new QueryBuilder("where title=? and catalogid=233",title));
		if(set!=null && set.size()>0){
		  	article = set.get(0);
			ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());

			ZCSiteSchema site = SiteUtil.getSchema(article.getSiteID() + "");

	    	long id = article.getID();
			String path =  PubFun.getArticleURL(site,catalog,article);
			System.out.println(path);
	    	out.write("getArticleParams('"+id+"','"+path+"')");
		}else{
			out.write("getArticleParams('','')");
		}
	}
%>