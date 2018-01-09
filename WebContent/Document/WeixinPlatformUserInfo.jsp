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
<z:init method="com.sinosoft.cms.document.WeixinPlatform.initUserShowDialog">
<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="89" height="10"></td>
			<td ></td>
		</tr>
		<tr>
			<td align="right">openid：</td>
			<td>${openid}</td>
		</tr>
		<tr>
			<td align="right">昵称：</td>
			<td width="249">${nickname}</td>
		</tr>
		<tr>
			<td align="right">性别：</td>
			<td width="249">${sex}</td>
		</tr>
		<tr>
			<td align="right">语种：</td>
			<td width="249">${language}</td>
		</tr>
		<tr>
			<td align="right">城市：</td>
			<td width="249">${city}</td>
		</tr>
		<tr>
			<td align="right">省份：</td>
			<td width="249">${province}</td>
		</tr>
		<tr>
			<td align="right">国家：</td>
			<td width="249">${country}</td>
		</tr>
		<tr>
			<td align="right">头像：</td>
			<td width="249"><a href="${headimgurl}" target="_blank">点击查看</td>
		</tr>
		<tr>
			<td align="right">关注时间：</td>
			<td width="249">${subscribe_time}</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td width="249">${remark}</td>
		</tr>
	</table>
</form>
</z:init>
</body>
</html>
