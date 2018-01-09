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
			<form action="${base}/shop/payment!paymentconfig.action" method="post">
				<@s.token />
				<input type="hidden" name="paymentType" value="deposit" />
				<input type="hidden" name="amountPayable" value="${amountPayable}" />
				<input type="hidden" name="paymentConfig.id" value="${(paymentConfig.id)!}" />
				<input type="hidden" name="order.id" value="${(order.id)!}" />
				<tr>
					<th>
						<支付前最终页，包含网银列表、第三方支付按钮>支付类型:
					</th>
					<td>
						
							<#list allPaymentConfigType as list>
								
								<input type="radio" name="rad" value="${list}" />
									${action.getText("PaymentConfigType." + list)}
								
							</#list>
						
						
					</td>
				</tr>
				<tr>
					<th>
						支付银行列表:
					</th>
							<td>
							<#list paymentConfigs as list>
							
								<input type="checkbox" name="ids" value="${list.id}" />
							    <img src="${base}${list.logo}" >
							</#list>
							   </td>
				</tr>
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