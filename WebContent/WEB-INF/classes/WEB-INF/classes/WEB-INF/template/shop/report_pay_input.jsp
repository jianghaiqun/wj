<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
	String shouye = Config.getFrontServerContextPath();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>保险理赔【流程_查询_材料_报案】-开心保保险网</title>
<meta name="keywords" content="理赔流程，理赔查询，理赔材料，理赔案例，理赔报案"/>
<meta name="description" content="买保险理赔难？开心保网理赔频道为您解忧，提供理赔流程、理赔查询、快速报案、理赔材料等一站式协助理赔服务，免费热线：4009-789-789！"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_help.css"/>
<link rel="shortcut icon" href="favicon.ico"/>

<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head>
<body class="re-bg">
	<!-- 顶部 开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	
	<!-- 主体开始 -->
	<div class="fast-report">
      <div class="main-wrap">
        <div class="daohang">
          <span class="daohang_home"></span> 
          <a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>"><span class="orange">您的现在位置：首页</span></a>
          <span class="separator">&gt;</span>
          <a href="<%=Config.getValue("FrontServerContextPath")%>/lipei/" target="_self"><span class="separator1">理赔通道</span></a>
          <span class="separator">&gt;</span>
          <span class="separator1">保险公司理赔电话</span>
        </div>
        <div class="main">
          <h3 class="ks_titlse">保险公司理赔电话</h3>
		  <div class="content-wrap" id="phone_div">
		  	<ul class="clearfix phone-list">
		  	  <s:iterator id="cominfo" value="companyInfo">
		  	  	<li class="fl-l">
                  <div>
                    <p class="icon_C<s:property value='#cominfo.comCode'/> pro_logo"></p>
                    <p class="phone"><span><i class="claim-icon"></i><s:property value='#cominfo.comPhone'/></span></p>
                  </div>
                  <p class="name"><s:property value='#cominfo.comName'/></p>
                </li>
		  	  </s:iterator>
		  	</ul>
		  </div>
		</div>
      </div>
    </div>
    <!-- 主体结束 -->
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->

	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/claims.js"></script>
	
	<script>
		var valObj = new ZMCommon();
	</script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_help.js"></script>
</body>
</html>