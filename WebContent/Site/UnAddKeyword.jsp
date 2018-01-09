<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<form id="unForm_Add">
		<table width="100%" height="123" cellpadding="3" cellspacing="0">
			<tr>
				<td width="12" colspan="3" height="10"></td>
			</tr>
			<tr>
				<td width="12"></td>
				<td align="right">关键词：</td>
				<td><input name="Keyword_Add" type="text" value="" class="input1" id="Keyword_Add" verify="关键词|NotNull" size=25 /> 
					<input name="ID" type="hidden" id="ID" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="hidden" id="level_Add" value = "0"/></td>
			</tr>
			<tr>
				<td height="5" align="right"></td>
				<td height="5"></td>
			</tr>
</table>
</form>
</body>
</html>