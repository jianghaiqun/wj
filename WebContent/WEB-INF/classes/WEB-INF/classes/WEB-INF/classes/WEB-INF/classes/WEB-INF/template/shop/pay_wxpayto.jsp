<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="com.sinosoft.framework.Config"%>
<%@ page import="com.wxpay.wxap.ResponseHandler"%>
<%@ page import="com.wxpay.wxap.RequestHandler"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="com.wxpay.wxap.client.TenpayHttpClient"%>
<%@ page import="java.util.SortedMap"%>
<%@ page import="com.wxpay.wxap.util.Sha1Util"%>
<%@ page import="com.wxpay.wxap.util.TenpayUtil"%>
<%@ page import="com.wxpay.wxap.util.QRUtil"%>
<%@ page import="com.wxpay.wxap.util.MD5Util"%>
<%@ page import="java.io.BufferedWriter"%>
<%@ page import="java.io.BufferedOutputStream"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="com.google.gson.Gson"%>
<%
  String paySn = String.valueOf(request.getAttribute("paySn"));
  String orderSnS = String.valueOf(request.getAttribute("orderSnS"));
  String qrURL = QRUtil.getQrUrl(paySn);
  
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付</title>
<link
	href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=Config.getValue("StaticResourcePath")%>/style/pay_skip.css"
	rel="stylesheet" type="text/css" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/shop/css/new_shops.css?v=1216" />

<!--支付页面样式-->
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/shop/css/new_PayPromptSty.css?v=1216" />
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<div class="wrapper">
	<div class="line_logo">
		<a href="<%=Config.getValue("FrontServerContextPath")%>"> <img src="<%=Config.getValue("StaticResourcePath")%>/images/logo_03.gif"
			alt="开心保保险网，一站式服务，省钱更安心" title="开心保保险网，一站式服务，省钱更安心" width="447"
			height="72">
		</a>
	</div>
	<div class="up_line_log">
		<div class="sdong_up pay_end_up"></div>
	</div>
	<div class="clear"></div>
	</div>
	<div class="weixin_box">
		<div class="wrapper">
			<div class="weixindd">
			<table width="275px">
				<tr>
				<td width="34%" style=" text-align: right;">订单金额：</td>
				<td ><b>￥<s:property value="payPrice" /></b></td>
				</tr>
				<tr>
				<td style="vertical-align: top; text-align: right;">订单号：</td>
				<td><s:iterator value="#request.orderList " id="order" status="tlist">
					<s:if test="#tlist.index<=4">
						<s:property value="#order.orderSn" />
						<br>
					</s:if>
					<s:else>
						<s:if test="#tlist.last">
							......
						</s:if>
					</s:else>
				</s:iterator></td>
				</tr>
			</table>
			</div>
			<div class="QRcord">
				<img src="<%=qrURL%>" width="220px" height="220px" alt=""> <img
					src="<%=Config.getValue("StaticResourcePath")%>/style/shop/images/telsc.png"
					class="tels" alt=""
					style="opacity: 0; left: -150px; display: none;">
			</div>
			<div class="clear"></div>
		</div>
	</div>

    <input type="hidden" id="p_price" name="p_price" value="<s:property value="payPrice" />"/>
    <input type="hidden" id="paySn" name="paySn" value="<%=paySn%>"/>
    <input type="hidden" id="orderSnS" name="orderSnS" value="<%=orderSnS%>"/>
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>

</body>

<script type="text/javascript">
	jQuery('.QRcord').hover(function() {
		jQuery('.tels').css("display", "block").stop().animate({
			left : "+250px",
			opacity : 1
		}, "400", "swing")
	}, function() {
		jQuery('.tels').animate({
			left : "-150px",
			opacity : 0
		}, "400", "swing", function() {
			jQuery('.tels').css("display", "none");
		});
	});
	var paySn = jQuery("#paySn").val();
	var orderSnS = jQuery("#orderSnS").val();
	//添加循环事件，第一次访问20s后，第二次后每隔5s执行一次,共执行10次
	var tCount = 1;
	jQuery(function(){ 
		if(parseInt(tCount)<=30){
			if(tCount==1){
				setInterval(ajaxPayResult,10000);
				tCount = parseInt(tCount)+1;
			}else{
				setInterval(ajaxPayResult,2000);
				tCount = parseInt(tCount)+1;
			}
		}
	});
	function ajaxPayResult(){
		jQuery.ajax({
			url: sinosoft.base+"/shop/pay!checkPayResult.action?paySn="+paySn,
			dataType: "json",
			async: true,
			success: function(data) {
				var obj = eval(data);
				var flag = obj.state;
				if(flag=="Suc"){
					docallBack();
				}
			}
		});
	}
	function docallBack(){
		var p_price=jQuery("#p_price").val();
		window.location.href = sinosoft.base+"/shop/pay!doCallBack.action?paySn="+paySn+"&payType=wx&OrdId="+orderSnS+"&payPrice="+parseFloat(p_price);
	}
</script>
</html>