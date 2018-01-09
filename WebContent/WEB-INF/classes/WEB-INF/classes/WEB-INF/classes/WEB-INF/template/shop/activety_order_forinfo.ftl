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

<body>

<div class="header">
	<p><img class="imgH" src="${shopStaticPath}/shop/images/sos/img_header.jpg" /></p>
	<p class="rtn"><span id="back">返回</span></p>
<!-- end header --></div>
	
<div class="content">
	<div class="showBox">
		<div class="showBorder">
			<p class="ttl">温馨提示：</p>
			<p><span class="txt">1.SOS境内援助服务是由中英人寿保险有限公司联手国际SOS医疗援助中心为客户提供的医疗援助服务及旅行援助服务。</span></p>
			<p><span class="txt">2.SOS服务生效时间及体验期限以您收到的短信中通知时间为准，该项服务指单次旅行不超过30天。</span></p>
			<p><span class="txt">3.特别提示您注意：SOS境内援助服务对服务内容和服务费用限额均有相关细则和除外责任，请依据您参加的国内或国际服务项目仔细阅读相关条款。如有疑问，欢迎致电国际SOS援助热线<b>4008980388</b>或中英人寿客服热线<b>95545</b>，如境内无法拨通请拨打<b>020-95545</b>。</span></p>
		</div>
	</div>
	<input id="channelSn" name="sdappnt.channelSn" type="hidden" value="${channelSn}"/>
	<div class="btnArea">
		<a href="#"><span id="btnRtn">返回</span></a>
	</div>
<!-- end content --></div>
<#include "WEB-INF/template/shop/transmit.ftl"> 
<script type="text/javascript">
  $('#back').click(function(e){
  		e.preventDefault();
  		window.location.href = "activety_order!initinfo.action?channelSn="+jQuery("#channelSn").val();
  });
  $('#btnRtn').click(function(e){
  		e.preventDefault();
  		window.location.href = "activety_order!initinfo.action?channelSn="+jQuery("#channelSn").val();
  });
</script>
</body>
</html>