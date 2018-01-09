<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title><s:property value="title"/></title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<style type="text/css">
.g-nav-main li a.nav_2{ color:#f08225; }
</style>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head>
<body class="re-bg"> 
	<!-- 顶部 开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->

<!-- 导购轮播 -->
    <div class="weaper">
   	    <div class="daohang">
			<span class="daohang_home"></span> 
			<a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>"><span class="orange">您的现在位置：首页</span></a>
			<span class="separator">&gt;</span>
			<span class="separator1">保险快选</span>
		</div>
	    <div class="g-guide">
	        <!-- 当前第N页 -->
	        <input type="hidden" value="<s:property value='pageNo'/>" id="g-guidenum">
	        <!-- 百分比 -->
	        <input type="hidden" value="25" id="g-guideb">
	        <!-- 是否为最后一页 -->
	        <input type="hidden" value="<s:property value='lastPageFlag'/>" id="g-guidelase">
	        <div class="g-guide-tit f_mi alphatm">
	            <div class="g-guide-h"></div>
	            <p><s:property value='descProp1'/></p>
	        </div>
	        <div class="g-guide-img">
               	<ul class="g-guide-uls">
                  	<s:iterator id="guide" value="result">
                    	<li style="left:-1500px;">
	                     	<a href="<s:property value='#guide.nextLink'/>" target="<s:property value='#guide.target'/>"
	                     		exturl="http://www.kaixinbao.com/DGGuide" vlpageid="DGGuide" onclick="javascript:void(0);return(VL_FileDL(this));return false;">
	                     		<img src="<s:property value='#guide.Image'/>" alt="<s:property value='#guide.Name'/>">
	                     		<span class="g-guide-name"><s:property value='#guide.Name'/><em class="infes_12"></em></span>
	                     	</a>
                    	</li>
                  	</s:iterator>
               	</ul>
	        </div>
	        <div class="g-guide-next">
	            <div class="gg-next guides_06"></div><span class="gg-next-g select"><em class="gg-next-b" style="width:25%" ></em></span>
	            <div class="gg-next  guides_08"></div>
	        </div>
	        <div class="g-guide-nest">
	            <a href="<s:property value='backStep'/>"
	            	exturl="http://www.kaixinbao.com/DGBackS" vlpageid="DGBackS" onclick="javascript:void(0);return(VL_FileDL(this));return false;">
	            	返回上一步</a>
	        </div>
	    </div>
	</div>

	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->

	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_base.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/header1.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/login.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_guide.js"></script>

</body>
</html>