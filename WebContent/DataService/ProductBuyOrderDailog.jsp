<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title> 
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_header.css"/>
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/shop/css/new_shops.css"/>

<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/skins/default.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.shop_day{ background:url(../images/day_07.gif) no-repeat 98% 6px;}
</style>
<script type="text/javascript" src="../Framework/Main.js"></script>

</head>
<body class="dialogBody" >
	<form name="form2" id="form2">
		<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
			<z:init method="com.sinosoft.cms.dataservice.OrderImport.initDialog1">
				<tr height="10px">
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">要修改的订单号：</td>
					<td><input type="text" id="ordersn" name="ordersn" style="width:120px;"/></td>
				</tr>

				<tr>
					<td align="right">购买订单来源：</td>
					<td><z:select name="CmsBuySource" id="CmsBuySource" style="width:100px;">${CmsBuySource}</z:select></td>
				</tr>
				<tr id="buyForCustomerFlagTR">
					<td align="right">支付类型：</td>
					<td>
						<z:select id="buyForCustomerFlag" name="buyForCustomerFlag" style="width:100px;">
							<select name="select">
								<option value=""></option>
								<option value="Y">撤单重出</option>
								<option value="N">代客支付</option>
							</select>
						</z:select>
					</td>
				</tr>
				<tr id="oldOrdersnTR">
					<td align="right">原始订单号：</td>
					<td>
						<input type="text" id="oldOrdersn" name="oldOrdersn" style="width:120px;">
					</td>
				</tr>
			</z:init>
		</table>
	</form>
</body>
</html>