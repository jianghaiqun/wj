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
<style type="text/css">
</style>
</head>
<z:init method="com.sinosoft.cms.site.Notice.initDialog">
	<body class="dialogBody">
		<div style="display: none">
			<iframe name="formTarget" src="javascript:void(0)"></iframe>
		</div>
		<form id="myform" method="post" target="formTarget">
			<input name="ID" type="hidden" id="ID" value="${ID}" />
			<table width="100%" cellpadding="2" cellspacing="0" id="checktable">
				<tr>
					<td width="27%"></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">链接名称：</td>
					<td width="73%"><input name="Name" type="text" class="input1"
						id="Name" value="${Name}" verify="NotNull" style="width: 200px" />
					</td>
				</tr>
				<tr>
					<td align="right">链接URL：</td>
					<td><input name="URL" type="text" class="input1" id="URL"
						value="${URL}" verify="NotNull" style="width: 200px" />
					</td>
				</tr>
				<tr>
					<td align="right">类型：</td>
					<td>${NoticeType}</td>

				</tr>
				<tr>
					<td align="right">链接目标：</td>
					<td>${NoticeLink}</td>
				</tr>
			</table>
		</form>
	</body>
</z:init>
</html>
