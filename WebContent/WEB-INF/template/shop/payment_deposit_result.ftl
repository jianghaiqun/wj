<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
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
<body class="paymentResult">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="paymentResultDetail">
			<#if paymentResult == "success">
				<span class="icon success"> </span>预存款支付成功，支付总金额<span class="red">${(amountPayable + paymentFee)?string(orderUnitCurrencyFormat)}</span>元
				<div class="buttonArea">
					<input type="submit" class="formButton" onclick="location.href='${base}/'" value="确 定" />
				</div>
			<#else>
				<span class="icon error"> </span>预存款余额不足！您的预存款余额为<span class="red">${loginMember.deposit?string(orderUnitCurrencyFormat)}</span>
				<div class="buttonArea">
					<input type="submit" class="formButton" onclick="location.href='${base}/shop/deposit!recharge.action'" value="预存款充值" />
				</div>
			</#if>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>