<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付确认</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/payment.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/register.js"></script>
</head>
<body class="paymentGateway">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="paymentGatewayDetail">
			<form action="${base}/shop/payment!gateway.action" method="post">
				<@s.token />
				<input type="hidden" name="paymentType" value="deposit" />
				<input type="hidden" name="amountPayable" value="${amountPayable}" />
				<input type="hidden" name="paymentConfig.id" value="${(paymentConfig.id)!}" />
				<input type="hidden" name="order.id" value="${(order.id)!}" />
				<p>
					尊敬的<strong class="green">${loginMember.username}</strong>，
					您选择的支付方式为：<strong>支付宝支付</strong>
					<#if order != null><a href="${base}/shop/order!view.action?id=${order.id}">[查看订单详情]</a></#if>
				</p>
				<p>
					支付总金额：<strong class="red">${(amountPayable + paymentFee)?string(orderUnitCurrencyFormat)}</strong>
				</p>
				<div class="buttonArea">
					<input type="submit" class="formButton" value="确认支付" />
				</div>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>