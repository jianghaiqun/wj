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
			<p class="ttl">活动规则：</p>
			<p><span class="intro">1.活动时间：</span><span class="txt setCol">4月22日-5月31日；</span></p>
			<p><span class="intro">2.领取规则：</span><span class="txt">每个电话号码限领取1份；</span></p>
			<p><span class="intro">3.帮家人和朋友领取：</span><span class="txt">可以为家人和朋友领取，相关信息核实后予以承保；</span></p>
			<p><span class="intro">4.承保说明：</span><span class="txt">本产品将由中英人寿承保，生效后会以短信息及邮件发送具体信息给您；</span></p>
			<p><span class="intro">5.活动区域：</span><span class="txt">本活动仅限北京/广东/四川/福建/山东/河北/河南/湖北/湖南/江苏/辽宁/黑龙江地区客户参加；</span></p>
			<p class="intro">6.领取成功后，可关注开心保保险微信，
了解更多信息。</p>
		</div>
	</div>
	
	<div class="btnArea">
		<a href="#"><span id="btnRtn">返回</span></a>
	</div>
	<input id="channelSn" name="sdappnt.channelSn" type="hidden" value="${channelSn}"/>
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