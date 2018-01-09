<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的推荐</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'> <span
				class="orange">您现在的位置：首页</span> </a> <span class="separator">></span> <a
				href='member_center!index.action' target='_self'>会员中心</a> <span
				class="separator">></span> <span class="separator1">会员中心首页</span>
		</div>
		<div class="member_con">
		<s:include value="/wwwroot/kxb/block/kxb_member_center_left.shtml"></s:include>
		<div class="member_right bor_sild  ">
			<h2 class="member_t_h2">我的推荐</h2>
					<input type="hidden" id="fullDegree"
						value="<%=session.getAttribute("fullDegree")%>">
			<div class="no_recommend">
				您还没有推荐任何人，很遗憾不能获得积分换礼品哦~ <a href='member_recommend!queryRecomm.action' class="go_recommends">去推荐</a>
			</div>
		</div>
	</div>
	</div>
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
</body>
</html>