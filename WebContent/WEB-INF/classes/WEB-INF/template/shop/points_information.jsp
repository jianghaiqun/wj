<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="积分商城-礼品详细页" />
	<meta name="description" content="积分商城-礼品详细页" />
	<title>积分商城-礼品详细页-<%=request.getAttribute("siteName") %></title>
	<style>

	.g-nav-main li a.nav_4{ color:#f08225; }
</style>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_detail.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_integral.css" />
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="detail">
	<!-- 公共头部 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 公共头部 -->
	<div class="clear"></div>
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> 
			<a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>"><span class="orange">您的现在位置：首页</span></a>
			<a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>/jifen/"><span class="separator">&gt;</span><span class="separator1">积分商城</span></a>
			<span class="separator">&gt;</span><span class="separator1">礼品详细页</span>
		</div>
		<div class="content_head">
			<div class="cp_titile_con">
				<h1><s:property value="#request.mgiftclassfy.GiftTitle"/></h1>
				<span class="cp_title_adapt"><s:property value="#request.mgiftclassfy.GiftName"/></span>
			</div>
			<div class="jfcp_con">
				<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#request.mgiftclassfy.LogoUrl"/>"  width="250px" height="190px" alt="">
			</div>
			<div class="cp_descon  cp_desconup">
				<div class="bxshop-mes">
						<dl class="bxshop-mes-dl">
							<dt>库存数量：</dt>
							<dd><s:property value="#request.mgiftclassfy.LastNum"/>份</dd>
							<dt>价    格：</dt>
							<dd><s:property value="#request.mgiftclassfy.Points"/>积分</dd>						
						</dl>	
				</div>
				<div class="bxshop_csf">
						充值号码： <input type="text" id="mobile" class="int_text"><em class="int_error" style="display:none"></em>
				</div>
					<div class="clear h10"></div>
				<div class="button_b clearfix" id="button_b clearfix">
					<span onClick="doPoints();" class="button_gotos" id="ExchangeButton">立即兑换</span>
					<input type="hidden"  id="presentID" value="<s:property value="#request.mgiftclassfy.id"/>">
					<input type="hidden"  id="mgiftclassfy_type" value="<s:property value="#request.mgiftclassfy.type"/>">
					<input type="hidden"  id="PointsExchangeType" value="<s:property value="#request.mgiftclassfy.PointsExchangeType"/>">
				</div>
				<div class="clear "></div>
			</div>
		</div>
		<div class="content">
			<div class="content_left_up">
				<div class="cp_contents">
					<div class="c1"><em class="l_icon"></em>礼品详情</div>
					<div class="ws_tip"><s:property value="#request.mgiftclassfy.metaDescription" escape="false"/></div>
				</div>
			</div>
			<div class="content_right_up">
				<div class="l_boxss">
					<div class="c1"><em class="l_icon"></em>推荐礼品</div>
					<ul id="recgiftlist" class="new_shopli">
						<!-- 推荐内容 -->
					</ul>
				</div>
				<div class="l_boxss">
					<div class="c1"><em class="l_icon"></em>热销礼品</div>
					<div id="hotgiftlist">
					</div>
				</div>
				<div class="l_boxss kf_box">
					<a href="<%=Config.getValue("FrontServerContextPath")%>/about/xszn/index.shtml#xszn5"><img src="<%=Config.getValue("StaticResourcePath")%>/images/jifen/imgs_03.gif" alt="" /></a>
				</div>
				<div class="l_boxss kf_box">
         			 <a onclick="return(VL_FileDL(this));return false;" id="qqwap2" exturl="http://www.kaixinbao.com/xiaoneng" vlpageid="xiaoneng" href="javascript:void(0);" rel="nofollow"><img width="220" height="48" title="投保遇到问题？马上咨询在线客服" alt="投保遇到问题？马上咨询在线客服" src="<%=Config.getValue("FrontServerContextPath")%>/images/jifen/imgs2_06.gif"></a>
           		</div>
			</div>
			<div class="clear h20"></div>
			<div class="c1"><em class="l_icon"></em>积分说明</div>
			<img src="<%=Config.getValue("StaticResourcePath")%>/images/jifen/jf_mes_14.gif" alt="">
			<div class="clear h20"></div>
		</div>
	</div>

	<!-- 公共底部 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 公共底部 -->
	<!-- 公共js -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<!-- 公共js -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/new_jifen.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/pointsinfo.js"></script>
</body>
<script type="text/javascript">
document.getElementById('mobile').focus();
</script>
</html>
