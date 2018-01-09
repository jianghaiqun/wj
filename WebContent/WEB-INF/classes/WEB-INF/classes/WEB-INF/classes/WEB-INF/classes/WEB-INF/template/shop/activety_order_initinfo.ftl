<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  lang="ja">
<head>
<meta charset="utf-8">
<meta name="description" content="关注微信公众账号：kaixinbbx，SOS国内医疗援助服务限时免费抢">
<title>一向高冷的SOS国际救援中心也免费了，告诉我这不是真的……</title> 
<meta id="viewport" name="viewport" content="width=640,initial-scale=0.5,maximum-scale=0.5" />
<meta name="format-detection" content="telephone=no" />
<meta name="applicable-device" content="mobile">
<link rel="stylesheet" href="http://wap.kaixinbao.com/mobile/css/def_import.css" media="screen">
<link rel="stylesheet" href="${shopStaticPath}/shop/css/sos/wap_style.css" media="screen">
<script src="${shopStaticPath}/shop/js/sos/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
try{
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    WeixinJSBridge.call('hideToolbar');// 隐藏底部
	});
}catch (e) {
}
</script>
</head>

<body> <!--style="width: 640px; margin: 0 auto;"-->
<div class="header">
	<p><img class="imgH" src="${shopStaticPath}/shop/images/sos/img_header.jpg" /></p>
<!-- end header --></div>
	
<div class="content">
	<ul class="showList">
		<li><img src="${shopStaticPath}/shop/images/sos/icon_showlist_01.png" />急需联络就近医院，24小时呼叫响应！</li>
		<li><img src="${shopStaticPath}/shop/images/sos/icon_showlist_02.png" />医疗转院迫在眉睫，专业直升机、客机如期而至！</li>
		<li><img src="${shopStaticPath}/shop/images/sos/icon_showlist_03.png" />孤身异乡救治难，顶尖医护保驾护航！</li>
		<li><img src="${shopStaticPath}/shop/images/sos/icon_showlist_04.png" />钱到难时方恨少，额外补贴暖心极好！<br />……10项紧急救援服务，为你的任性撑腰，医疗援助来点硬货，出行保障绝不对付！</li>
	</ul>
	
	<div class="btnArea">
		<span id="btnFree">免费领取</span>
		<span id="btnShare">分享送福</span>
	</div>
<!-- end content --></div>

<div class="menuArea">
	<ul>
		<li><a href="activety_order!ruleinfo.action?channelSn=${channelSn}"><img src="${shopStaticPath}/shop/images/sos/icon_menuarea_01.gif" />活动规则</a></li>
		<li><a href="activety_order!forinfo.action?channelSn=${channelSn}"><img src="${shopStaticPath}/shop/images/sos/icon_menuarea_02.gif" />温馨提示</a></li>
		<li><a href="activety_order!serveinfo.action?channelSn=${channelSn}"><img src="${shopStaticPath}/shop/images/sos/icon_menuarea_03.gif" />服务条款</a></li>
	</ul>
</div>
<input id="channelSn" name="sdappnt.channelSn" type="hidden" value="${channelSn}"/>
<div id="shareBg"></div>
<#include "WEB-INF/template/shop/transmit.ftl">
<div id="shareArrow"><img src="${shopStaticPath}/shop/images/sos/share-arrow.png"></div>
<script type="text/javascript">
$(document).ready(function() {
	
	jQuery("#btnShare").click(function() {
		jQuery("body,html").animate({
            scrollTop:0
        }, 500);
		jQuery("#shareBg").show();
		jQuery("#shareArrow").show();
		
	});
	jQuery("#btnFree").click(function() {
		
		window.location.href = "activety_order!inputinfo.action?channelSn="+jQuery("#channelSn").val();
		
	});
	
	jQuery("#shareBg").click(function() {
	
		jQuery(this).hide();
		jQuery("#shareArrow").hide();
		
	});
	
	jQuery("#shareArrow").click(function() {

		jQuery("#shareBg").hide();	
		jQuery(this).hide();
		
	});
	
});
</script>
</body>
</html>