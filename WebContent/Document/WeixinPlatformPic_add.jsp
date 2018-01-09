<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td width="89" height="10" ></td>
		<td ></td>
	</tr>
	<tr>
		<td align="right" >场景名称：</td>
		<td >
		<input name="sceneName" type="text" value="" id="sceneName" verify="场景名称|NotNull" size="30"/></td>
	</tr>
	<tr>
		<td align="right" >场景值：</td>
		<td width="249" >
		<input name="sceneId" type="text" value="" id="sceneId" verify="场景值|NotNull" size="30"/></td>
	</tr>
</table>
	</form>
</body>
</html>
