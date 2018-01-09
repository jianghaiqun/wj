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
<z:init method="com.sinosoft.cms.site.LinkTeam.initEditDialog">
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="3" cellspacing="0">
	<tr>
		<td width="12" colspan="3" height="10"></td>
	</tr>
	<tr>
		<td width="12"></td>
		<td align="right">链接包名称：</td>
		<td>
			<input name="TeamName" type="text" value="${TeamName}" style="width:130px" class="input1" id="TeamName" verify="链接包名称|NotNull" size=15 />
			<input type="hidden" id="cid" name ="cid" value="${ID}" />
		</td>
	</tr>
</table>
</form>
</body>
</z:init>
</html>
