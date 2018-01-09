<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会员中心-生效中保单</title>
	<!-- 会员中心公共CSS --> 
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
	<body class="up-bg-qh">
		<!-- 顶部 开始 1-->
		<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
		<!-- 顶部 结束 -->
		<div class="wrapper">
			<div class="daohang">
				<span class="daohang_home"></span>
				<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
				<span class="separator">></span>
				<a href='member_center!index.action' target='_self'>会员中心</a>
				<span class="separator">></span><span class="separator1">生效中保单</span>
			</div>
			<div class="member_con">
				<!-- 会员中心左侧菜单导航 -->
				<jsp:include page="member_center_left.jsp"></jsp:include>
				<div class="member_right bor_sild">
				 	<div class="member_boxs">
				 	    <!-- 生效中保单列表 start -->
				 	    <div class="member-htit">
				            <span class="member-titsc">生效中保单</span>
				        </div>
		                <div class="member_tip_p">
						电子保单和纸质保单具有相同的的法律效力<br>
						电子保单为PDF文件格式，如果您无法阅读，请<a href="http://www.jisupdf.com" target="_balnk">点击这里</a>下载并安装该软件
					    </div>
	                    <div id="member_ordertable">
					 	    <jsp:include page="order_query_newlist_risktype.jsp"></jsp:include>
					    </div>
	                    <!-- 生效中保单列表  end -->
				 	</div>
				 </div>
			</div>
		</div>
		<!-- 底部开始 -->
		<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
		<!-- 底部结束 -->
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
		<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
	</body>
</html>