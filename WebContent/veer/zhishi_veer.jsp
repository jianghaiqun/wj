<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.*,com.sinosoft.framework.utility.StringUtil,com.sinosoft.cms.pub.PublicHead"%>
<%
	String frontServerContextPath = Config.getFrontServerContextPath();
	String ContextRealPath = Config.getContextRealPath();
	String url = request.getParameter("url");
	String title = "";
	String oldUrl = url;
	String state = "";
	if(StringUtil.isEmpty(url) || url.indexOf("index.shtml") != -1){
		title = PublicHead.queryIndexTitle("zhishi/");
		PublicHead.getListMkMd(url);
		url = "/wwwroot/kxb/zhishi/index.shtml";
		state = "1";
	}else if(url.indexOf("index_") != -1){
		title = PublicHead.queryListTitle("zhishi/"+url);
		PublicHead.getListMkMd(url);
		url = "/wwwroot/kxb/zhishi/"+url;
		state = "2";
	}else if(url.indexOf(".shtml") == -1){
		title = PublicHead.queryListTitle("zhishi/"+url);
		PublicHead.getListMkMd(url);
		url = "/wwwroot/kxb/zhishi/"+url+"index.shtml";
		state = "2";
	}else{
		PublicHead.getDetailMkMd(url);
		title = PublicHead.queryDetailTitle(url);
		url = "/wwwroot/kxb/zhishi/"+url;
	}
	String tempUrl = ContextRealPath + url;
	File file = new File(tempUrl);
	String htmlState = "";
	//tempUrl 物理路径+文件路径.
	if (!file.exists()) {
		htmlState = "404";
		response.setStatus(404);//设置状态.
	}
	
%>

<%@page import="java.io.File"%><html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<!-- 知识1.0.1 -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="keywords" content="<%=PublicHead.MetaKeywords.get(oldUrl) %>" />
	<meta name="description" content="<%=PublicHead.MetaDescription.get(oldUrl) %>" />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=frontServerContextPath %>/style/redesign/re_header.css" />
	<%
		if("404".equals(htmlState)){
			%>
			<link rel="stylesheet" type="text/css" href="<%=frontServerContextPath %>/style/commonality.css"/>
						<style type="text/css">
							.error_404box{ width:980px; margin:0 auto; margin-top:30px;}
							.error_img{ width:169px; background:url(<%=frontServerContextPath %>/images/404bg_03.gif)  no-repeat center right; height:366px; margin-left:177px; float:left;}
							.n_footwrap{ margin-top:0px;}
							.error_des{ width:400px; float:left; display:inline; padding-top:120px; padding-left:60px; overflow:hidden;}
							.error_des p{ font-size:18px; font-family:"微软雅黑",Arial, Helvetica, sans-serifl;}
							.error_list{ color:#616060; font-size:12px; margin-top:15px;}
							.error_list li{ height:20px; line-height:20px;}
								.error_list a{ color:#FA8504 !important;}
								.error_list a:hover{ color:#FA8504 !important; text-decoration: underline;}
							.error_list em{ color:#FA8504;}
			</style>
			
		<%
		}else{
			%>
			<%
			//知识首页
		if("1".equals(state)){
				%>
				<link rel="stylesheet" type="text/css" href="<%=frontServerContextPath %>/style/new_news.css" />
				<link rel="canonical" href="<%=frontServerContextPath %>/zhishi/" />
		<% 	
			//知识列表页
		}else if("2".equals(state)){
			%>
				<link href="<%=frontServerContextPath %>/style/new_information.css" rel="stylesheet" type="text/css" />
				<link rel="canonical" href="<%=frontServerContextPath %>/zhishi/<%=oldUrl %>" />
			<%
		}else{
			%>
				<link href="<%=frontServerContextPath %>/style/new_information.css" rel="stylesheet" type="text/css" />
			<%
		}
		}
	%>
	
	<%
	if("404".equals(htmlState)){
		%>
		<title>页面没有找到</title>
		<%
	}else{
		%>
		<title><%=title%></title>
		<%
	}
	%>
</head>
<body>
<%
com.sinosoft.cms.pub.PublicHead.getTop("221",out);
%>
<%
	if("404".equals(htmlState)){
		%>
			<jsp:include page="404.jsp"></jsp:include>
		<%
	}else{
		%>
			<jsp:include page="<%=url %>"></jsp:include>
		<%
	}
	%>
<%
com.sinosoft.cms.pub.PublicHead.getBottom("221",out);
%>
</body>
</html>
  
