<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.message.CatalogTypeUI.initDialog">
	<form id="form2">
	<table width="100%"  border="0" align="center"
		cellpadding="4" cellspacing="" bordercolor="#DEDEDC"
		style="border-collapse:collapse;">
		<tr>
			<td width="100" height="10"></td>
			<td><input name="ColumnID" id="ColumnID" type="hidden"
				value="${ID}" /></td>
		</tr>
		<tr>
			<td align="right">类型名称：</td>
			<td><input name="TypeName" id="TypeName" type="text"
				verify="类型名称|NotNull" value="${TypeName}" /></td>
		</tr>
		<tr>
			<td align="right">类型代码：</td>
			<td><input name="TypeCode" id="TypeCode" type="text"
				verify="类型代码|NotNull" value="${TypeCode}" /></td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
