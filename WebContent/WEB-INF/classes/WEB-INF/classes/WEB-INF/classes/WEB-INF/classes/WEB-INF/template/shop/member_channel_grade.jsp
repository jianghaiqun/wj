<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8"/>
<title>会员等级</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/vip_tribe.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<style type="text/css">
.g-nav-main li a.nav_5{ color:#f08225; }
</style>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head>
<body class="re-bg"> 
	<!-- 顶部 开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<!-- 会员部落 位置-->
	<div class="weaper">
	    <div class="daohang">
	        <span class="daohang_home"></span>
	        <a href='<%=Config.getFrontServerContextPath()%>' target='_self' ><span class="orange">您的现在位置：首页</span></a>
	        <span class="separator">&gt;</span>
	        <span class="separator1">会员部落</span>
	    </div>
	</div>
	<!-- 会员部落 正文-->
    <div class="tribebner"   style="height:356px;background:url('<%=Config.getValue("StaticResourcePath")%>/images/member/bamer_02.jpg') center top;"></div>
    <div class="tribe-gradebox">
    	<div class="tribe-grade">
    	    <div class="tribe-bc-box">
		      <div class="tribe-bc f_mi tribe-gradek0">注册即可成为会员，享受会员<br />福利！</div>
		      <div class="tribe-bc f_mi tribe-gradek1">1.购买次数（有效订单数）：<s:property value="#request.showData.K1OrderNum" /><br />2.对应累积保费贡献：<s:property value="#request.showData.K1OrderPrem" /><br />3.有效性：永久<br /></div>
		      <div class="tribe-bc f_mi tribe-gradek2">1.购买次数（有效订单数）：<s:property value="#request.showData.K2OrderNum" /><br />2.对应累积保费贡献：<s:property value="#request.showData.K2OrderPrem" /><br />3.有效性：永久<br /></div>
		      <div class="tribe-bc f_mi tribe-gradekvip">1.VIP会员身份需提出申请，按会<br />员价值和特殊贡献赋予其身份。<br />2.有效性：一年<br /></div>
		    </div>
		    <div class="tribe_qa">
		      <h3 class="tribe-grade-tit f_mi">会员等级常见问题<em></em></h3>
		      <dl class="f_mi">
			      <dt class="border_radiusc_8">1、如何升级为vip会员？</dt>
			      <dd>VIP会员身份为手动设置，VIP会员身份需提出申请，咨询在线客服，按会员价值和特殊贡献赋予其身份。VIP会员享受开心保提供的特权服务。</dd>
			  </dl>
			  <dl class="f_mi">
			    <dt class="border_radiusc_8">2、什么是有效订单？</dt>
			    <dd>会员升级所需要的有效订单，是指购买并生效的订单。如一个订单内含多个保单，只要其中一个保单生效，该订单记为有效订单。全单退保、撤保的订单不记在有效订单内。24小时内购买多单的会员，计算为一个订单，判断依据同会员回购次数。</dd>
			  </dl>
		  </div>
   	   </div>
    </div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/artDialog.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/ZeroClipboard.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/vip_tribe.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/VerifyInput.js"></script>
</body>
</html>