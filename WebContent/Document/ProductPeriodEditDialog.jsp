<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>被保人关系配置</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	<z:init method="com.sinosoft.product.ProductInfo.initRelaition">
	<form id="form2">
	<table width="100%" height="50%" border="0">
		<tr>
			<td valign="middle">
			<table width="400" height="30" align="left" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td width="300" height="25"></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">产品编码：</td>
					<td  >${Id}<input name="Id" type="hidden" id="Id" value="${Id}"/><input name="ComCode" type="hidden" id="ComCode" value="${ComCode}"/></td>
					
				</tr>
				<tr>
					<td align="right">产品名称：</td>
					<td  >${ProductName}</td>
				</tr>
				<tr>
					<td align="right">保险起期（如不配置则默认为1天）：</td>
					<td  ><input name="StartDate" type="text" id="StartDate"/></td>
				</tr>
				<tr>
					<td align="right">保险止期（如不配置则默认730天）：</td>
					<td  ><input name="EndDate" type="text" id="EndDate"/></td>
				</tr>
				<tr>
				</tr>
			</table>
			</td>
		</tr>
	</table>
    </form>
    </z:init>
	</body>
	</html>
