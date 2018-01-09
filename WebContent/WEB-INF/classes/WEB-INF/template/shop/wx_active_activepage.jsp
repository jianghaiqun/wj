<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head> 
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title><s:property value="#request.title"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin_main.css?version=0902"/>
    <link media="screen and (min-width:1000px)" rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin_pc.css?version=0902"/>
    <style>
        body{ -webkit-touch-callout: none; -webkit-text-size-adjust: none; }
        img{
        	width: 100%;
        }
    </style>
</head> 

<body id="activity-detail">
       <div class="page-bizinfo">
           <div class="header">
           <h1 id="activity-name"><s:property value="#request.title" escape="true" /></h1>
           <p class="activity-info">
               <span class="activity-meta no-extra"><s:property value="#request.currentDate" /></span>&nbsp;&nbsp;&nbsp;
                                             <span class="text-ellipsis">开心保保险</span>
                           </p>
           </div>
       </div>
       <div class="page-content" lang="en">
           <div>
           <s:property value="#request.content" escape="false"/>
           </div>
       </div>
</body>
</html>

