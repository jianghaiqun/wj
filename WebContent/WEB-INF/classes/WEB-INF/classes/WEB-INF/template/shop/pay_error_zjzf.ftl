<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css">
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper order-bg">
	<div class="order-mes-des">
		<img src="${staticPath}/style/shop/images/errors.gif" alt="" width="260px" height="260px" class="order-mes-img">
		<div class="order-mesbox order-mesbox2">
			<#if (wait =="true")!>
				<h2 class="mesbox-tit f_mi">您好！支付结果还在路上，请耐心等待或点击查看订单</h2>
            	<span class="mes-erro-des f_mi">请在订单中心随时查看订单结果，30分钟后仍未显示支付成功请重新下单， 如果遇到问题请联系一下客服MM吧~</span><div id="loading"><img src="${staticPath}/images/nloading.gif" width="20px" height="20px" alt="" /></div>
            	<a href="" id="toShouYe" class="bad-btnsf bad-btnsf2">返回首页</a>
            	<a href="${base}/shop/member_center!index.action" class="bad-btnsf">查看订单</a>
            <#else>
            	<h2 class="mesbox-tit f_mi">${ErrMsg}</h2>
            	<span class="mes-erro-des f_mi">如果遇到问题请联系一下客服MM吧~</span><div id="loading"><img src="${staticPath}/images/nloading.gif" width="20px" height="20px" alt="" /></div>
            	<a href="" id="toShouYe" class="bad-btnsf bad-btnsf2">返回首页</a>
            	<a href="${base}/shop/order_config_new!pay.action?orderSn=${OrdId}" class="bad-btnsf">继续支付</a>
			</#if>
            
         </div>
         <div class="clear"></div>
     </div>
     <div class="clear"></div>
</div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
</body>
</html>