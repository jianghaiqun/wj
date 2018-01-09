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
<script type="text/javascript" src="${shopStaticPath}/shop/js/sos/jquery-1.8.2.min.js"></script>
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
	<p class="rtn"><span id="back">返回</span></p>
<!-- end header --></div>
	
<div class="content">
	<div class="showBox">
		<div class="showBorder">
			<p class="ttl">感谢您的参与</p>
			<p class="intro">SOS国内医疗援助服务</p>
			<p><span class="intro">服务期限：</span><span class="txt">90天</span></p>
			<p><span class="intro">服务生效：</span><span class="txt">您的服务将于三个工作日内开通，请您关注短信通知。</span></p>
			<p class="intro">祝您生活愉快！</p>
		</div>
	</div>
	
	<div class="btnArea">
		<a href="http://mp.weixin.qq.com/s?__biz=MjM5OTIyMDYyMw==&mid=204747738&idx=1&sn=60e8d7fabf5361ddc2a707dead345404"><span id="btnFree">关注开心保</span></a>
		<span id="btnShare">SOS送朋友</span>
	</div>
<!-- end content --></div>
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
	
	jQuery("#shareBg").click(function() {
	
		jQuery(this).hide();
		jQuery("#shareArrow").hide();
		
	});
	
	jQuery("#shareArrow").click(function() {

		jQuery("#shareBg").hide();	
		jQuery(this).hide();
		
	});
	$('#back').click(function(e){
  		e.preventDefault();
  		window.location.href = "activety_order!initinfo.action?channelSn="+jQuery("#channelSn").val();
	});
	
});
</script>
</body>
</html>