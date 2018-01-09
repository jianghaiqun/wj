<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.platform.GapGuarantee.dropdownMenu2">
		<form id="form2">
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="40%" height="10" align="right"></td>
					<td height="10"></td>
				</tr>
				<br></br>
				<tr>
					<td align="right">年龄范围</td>
					<td><z:select id="CodeName" name="CodeName" style="width:117px"
							verify="年龄范围|NotNull">${Memo}</z:select></td>
				</tr>
				<tr id="tr_CodeValue" style="display:">
					<td align="right">保险测评结果标题文案</td>
					<td><input id="CodeValue" name="CodeValue" type="text" class="input1"
						 verify="保险测评结果标题文案|NotNull" size=20 /></td>
				</tr>
				<input name="CodeType" id="CodeType" type="hidden" />
				<input name="CodeValueOld" type="hidden" id="CodeValueOld" />
				<input name="CodeNameOld" type="hidden" id="CodeNameOld" />
				<input name="MemoOld" type="hidden" id="MemoOld" />
			</table>
		</form>
	</z:init>
</body>
</html>