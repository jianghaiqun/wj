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
 <script>
 function init() {
		if ($V("Plan") != null && $V("Plan") != "") {
			document.getElementById("PlanTR").style.display="";
		}
		if ($V("Occup") != null && $V("Occup") !="") {
			document.getElementById("OccupTR").style.display="";
		}
		if ($V("TextAge") != null && $V("TextAge") != "") {
			document.getElementById("TextAgeTR").style.display="";
		}
		if ($V("AppType") != null && $V("AppType") !="") {
			document.getElementById("AppTypeTR").style.display="";
		}
		if ($V("FeeYear") != null && $V("FeeYear") != "") {
			document.getElementById("FeeYearTR").style.display="";
		}
		if ($V("Sex") != null && $V("Sex") !="") {
			document.getElementById("SexTR").style.display="";
		}
		if ($V("Grade") != null && $V("Grade") !="") {
			document.getElementById("GradeTR").style.display="";
		}
		if ($V("AppLevel") != null && $V("AppLevel") != "") {
			document.getElementById("AppLevelTR").style.display="";
		}
		if ($V("MulPeople") != null && $V("MulPeople") !="") {
			document.getElementById("MulPeopleTR").style.display="";
		}
	}</script>

</head>
<body class="dialogBody" onLoad="init();">
	<form name="form2" id="form2">
		<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
			<z:init method="com.sinosoft.cms.dataservice.OrderImport.initDialog">
				<tr>
					<td colspan="2" align="center"><laber>产品名称：</laber>${productName} <input
						type="hidden" id="productID" value="${productID}" />
					</td>
				</tr>
				<tr><td></td><td></td></tr>
				<tr>
					<td align="right">保险期限：</td>
					<td><z:select name="Period" id="Period" style="width:100px;">${period}</z:select></td>
				</tr>
				<tr style="display:none" id="PlanTR">
					<td align="right">计划：</td>
					<td><z:select name="Plan" id="Plan" style="width:100px;">${plan}</z:select></td>
				</tr>
				<tr style="display:none" id="OccupTR">
					<td align="right">职业类别：</td>
					<td><z:select name="Occup" id="Occup" style="width:100px;">${occup}</z:select>
					&nbsp;&nbsp;<a href="${htmlPath}" target="_blank">帮助</a></td>
				</tr>
				<tr style="display:none" id="TextAgeTR">
					<td align="right">投保年龄：</td>
					<td><z:select name="TextAge" id="TextAge" style="width:100px;">${textAge}</z:select></td>
				</tr>
				<tr style="display:none" id="AppTypeTR">
					<td align="right">缴费方式：</td>
					<td><z:select name="AppType" id="AppType" style="width:100px;">${appType}</z:select></td>
				</tr>
				<tr style="display:none" id="FeeYearTR">
					<td align="right">缴费年期：</td>
					<td><z:select name="FeeYear" id="FeeYear" style="width:100px;">${feeYear}</z:select></td>
				</tr>
				<tr style="display:none" id="SexTR">
					<td align="right">性别：</td>
					<td><z:select name="Sex" id="Sex" style="width:100px;">${sex}</z:select></td>
				</tr>
				
				<tr style="display:none" id="GradeTR">
					<td align="right">产品级别：</td>
					<td><z:select name="Grade" id="Grade" style="width:100px;">${grade}</z:select></td>
				</tr>
				<tr style="display:none" id="AppLevelTR">
					<td align="right">投保档次：</td>
					<td><z:select name="AppLevel" id="AppLevel" style="width:100px;">${appLevel}</z:select></td>
				</tr>
				<tr style="display:none" id="MulPeopleTR">
					<td align="right">保险责任多类人群：</td>
					<td><z:select name="MulPeople" id="MulPeople" style="width:100px;">${mulPeople}</z:select></td>
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