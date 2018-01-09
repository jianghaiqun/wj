<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.document.ArticleList.initTransURL">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td valign="middle">
			<table width="400" height="10" align="center" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td align="center">文章原URL：</td>
					<td  ><input name="oldURL" verify="元素名称|NotNull"
						type="text" value="${ArticleURL}"  class="input1" id="oldURL" size="50" /></td>
				</tr>
				<tr>
					<td align="center">文章新URL：</td>
					<td  ><input name="newURL" verify="元素名称|NotNull"
						type="text" value=""   class="input1" id="newURL" size="50" /></td>
				</tr>
				<tr>
					<td colspan="3" align="center" height="10"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<input name="ArticleID" id="ArticleID" type="hidden" value="${ArticleID}">
	</form>
	</body>
	</html>
</z:init>
